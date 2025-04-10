import 'package:flutter/material.dart';

class RegisterFormProvider extends ChangeNotifier {
  GlobalKey<FormState> formKey = GlobalKey<FormState>();

  String name = '';
  String password = '';
  String email = '';

  validateForm() {
    return formKey.currentState!.validate();
  }
}
