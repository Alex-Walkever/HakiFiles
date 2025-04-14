import 'package:email_validator/email_validator.dart';
import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';

enum AuthStatus { checking, authenticated, notAuthenticated }

class AuthProvider extends ChangeNotifier {
  AuthStatus authStatus = AuthStatus.checking;
  User? user;

  AuthProvider() {
    isAuthenticated();
  }

  login(String emailUsername, String password) {
    final data = {"password": password};
    if (EmailValidator.validate(emailUsername)) {
      data["email"] = emailUsername;
    } else {
      data["name"] = emailUsername;
    }
    HakifilesApi.httpPost(
      '/user/login',
      data,
    ).then((json) => _login(json)).catchError((e) {});
  }

  register({
    required String email,
    required String name,
    required String password,
  }) {
    final data = {"email": email, "name": name, "password": password};
    HakifilesApi.httpPost(
      '/user/register',
      data,
    ).then((json) => _login(json)).catchError((e) {});
  }

  Future<bool> isAuthenticated() async {
    final token = LocalStorage.getToken();
    if (token == null) {
      authStatus = AuthStatus.notAuthenticated;
      notifyListeners();
      return false;
    }
    try {
      final response = await HakifilesApi.httpGet('/user/auth');
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
    notifyListeners();
  }

  _login(Map<String, dynamic> json) {
    final authResponse = AuthResponse.fromJson(json);
    user = authResponse.user;
    authStatus = AuthStatus.authenticated;
    LocalStorage.setToken(authResponse.jwt);
    NavigationService.navigateTo(HakiRouter.rootRoute);
    HakifilesApi.configureDio();
    notifyListeners();
  }
}
