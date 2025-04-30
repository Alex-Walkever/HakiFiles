import 'package:email_validator/email_validator.dart';
import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';

enum AuthStatus { checking, authenticated, notAuthenticated }

class AuthProvider extends ChangeNotifier {
  AuthStatus authStatus = AuthStatus.checking;
  User? user;

  String? error;

  AuthProvider() {
    isAuthenticated();
  }

  login(String emailUsername, String password) {
    final Map<String, String> data = <String, String>{'password': password};
    if (EmailValidator.validate(emailUsername)) {
      data['email'] = emailUsername;
    } else {
      data['name'] = emailUsername;
    }
    HakifilesApi.httpPost(
      '/user/login',
      data,
    ).then((dynamic json) => _login(json)).catchError((dynamic e) {
      error = 'Username or password is wrong';
      notifyListeners();
    });
  }

  register({
    required String email,
    required String name,
    required String password,
  }) {
    final Map<String, String> data = <String, String>{
      'email': email,
      'name': name,
      'password': password,
    };
    HakifilesApi.httpPost(
      '/user/register',
      data,
    ).then((dynamic json) => _login(json)).catchError((dynamic e) {
      error = 'Username or email is already register';
      notifyListeners();
    });
  }

  Future<bool> isAuthenticated() async {
    final String? token = LocalStorage.getToken();
    if (token == null) {
      authStatus = AuthStatus.notAuthenticated;
      notifyListeners();
      return false;
    }
    try {
      final dynamic response = await HakifilesApi.httpGet('/user/auth');
      user = User.fromJson(response);
      authStatus = AuthStatus.authenticated;
      notifyListeners();
    } catch (e) {
      logout();
      return false;
    }
    return true;
  }

  logout() {
    LocalStorage.removeToken();
    authStatus = AuthStatus.notAuthenticated;
    HakifilesApi.configureDio();
    error = null;
    notifyListeners();
  }

  _login(Map<String, dynamic> json) {
    final AuthResponse authResponse = AuthResponse.fromJson(json);
    user = authResponse.user;
    error = null;
    authStatus = AuthStatus.authenticated;
    LocalStorage.setToken(authResponse.jwt);
    NavigationService.pop();
    HakifilesApi.configureDio();
    notifyListeners();
  }
}
