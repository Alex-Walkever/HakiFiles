import 'package:flutter/material.dart';

class NotificationsService {
  static GlobalKey<ScaffoldMessengerState> messengerKey =
      GlobalKey<ScaffoldMessengerState>();

  static showSnacknar(String message) {
    final snackBar = SnackBar(content: Text(message));

    messengerKey.currentState!.showSnackBar(snackBar);
  }
}
