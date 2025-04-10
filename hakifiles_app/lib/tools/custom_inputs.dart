import 'package:flutter/material.dart';

class CustomInputs {
  static InputDecoration authInputDecoration({
    required String hint,
    required String label,
    required IconData icon,
  }) => InputDecoration(
    // border: OutlineInputBorder(
    //   borderSide: BorderSide(color: Colors.white.withAlpha(55)),
    // ),
    // enabledBorder: OutlineInputBorder(
    //   borderSide: BorderSide(color: Colors.white.withAlpha(55)),
    // ),
    hintText: hint,
    labelText: label,
    prefixIcon: Icon(icon),
    // hintStyle: TextStyle(color: Colors.grey),
    // labelStyle: TextStyle(color: Colors.grey),
  );

  static InputDecoration searchInputDecoration({
    required String hint,
    required IconData icon,
  }) {
    return InputDecoration(
      border: InputBorder.none,
      enabledBorder: InputBorder.none,
      hintText: hint,
      prefixIcon: Icon(icon, color: Colors.grey),
      labelStyle: TextStyle(color: Colors.grey),
      hintStyle: TextStyle(color: Colors.grey),
    );
  }

  static InputDecoration formInputDecoration({
    required String hint,
    required String label,
    required IconData icon,
  }) => InputDecoration(
    border: OutlineInputBorder(
      borderSide: BorderSide(color: Colors.indigo.withAlpha(55)),
    ),
    enabledBorder: OutlineInputBorder(
      borderSide: BorderSide(color: Colors.indigo.withAlpha(55)),
    ),
    hintText: hint,
    labelText: label,
    prefixIcon: Icon(icon, color: Colors.grey),
    hintStyle: TextStyle(color: Colors.grey),
    labelStyle: TextStyle(color: Colors.grey),
  );
}
