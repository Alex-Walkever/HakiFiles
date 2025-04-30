import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';

class ThemeProvider extends ChangeNotifier {
  ThemeData theme = ThemeData.light();
  bool isDark = false;

  ThemeProvider() {
    _checkTheme();
  }

  changeTheme(ThemeData newTheme, bool darkMode) {
    isDark = darkMode;
    theme = newTheme;
    LocalStorage.setTheme(isDark);
    notifyListeners();
  }

  _checkTheme() {
    isDark = LocalStorage.getTheme() ?? false;
    theme = isDark ? ThemeData.dark() : ThemeData.light();
  }
}
