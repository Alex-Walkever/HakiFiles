import 'package:flutter/material.dart';

class CreateDeckFormProvider extends ChangeNotifier {
  GlobalKey<FormState> formKey = GlobalKey<FormState>();

  String name = '';
  String? description = '';
  String? youtubeLink;
  String leader = '';
  bool isPrivate = true;

  bool validateForm() {
    return formKey.currentState!.validate();
  }
}
