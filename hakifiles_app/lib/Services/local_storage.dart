import 'package:shared_preferences/shared_preferences.dart';

class LocalStorage {
  static late SharedPreferences _preferences;

  static Future<void> configurePrefs() async {
    _preferences = await SharedPreferences.getInstance();
  }

  static String? getToken() {
    return _preferences.getString('token');
  }

  static setToken(String token) {
    _preferences.setString('token', token);
  }

  static removeToken() {
    _preferences.remove('token');
  }
}
