import 'package:flutter/material.dart';

class NavigationService {
  static GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();
  static navigateTo(String routeName) {
    return navigatorKey.currentState!.pushNamed(routeName);
  }

  static replaceTo(String routeName) {
    return navigatorKey.currentState!.pushReplacementNamed(routeName);
  }

  static navigateToAndRemove(String routeName) {
    return navigatorKey.currentState!.pushNamedAndRemoveUntil(
      routeName,
      ModalRoute.withName('/'),
    );
  }

  static pop() {
    navigatorKey.currentState!.pop();
  }

  static showDialogInWeb(Widget widget) {
    showDialog(
      barrierDismissible: false,
      context: navigatorKey.currentContext!,
      builder: (context) => Dialog(child: widget),
    );
  }
}
