import 'package:flutter/material.dart';

class IconWithText extends StatelessWidget {
  const IconWithText({super.key, required this.text, this.icon});

  final String text;
  final IconData? icon;

  @override
  Widget build(BuildContext context) {
    return Row(children: [Icon(icon), SizedBox(width: 5), Text(text)]);
  }
}
