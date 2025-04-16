import 'package:flutter/material.dart';

class LoginFormProvider extends ChangeNotifier {
  GlobalKey<FormState> formKey = GlobalKey<FormState>();

  String nameOrEmail = '';
  String password = '';

  bool validateForm() {
    return formKey.currentState!.validate();
  }
}
