import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';

class CustomNavigationButton extends StatelessWidget {
  const CustomNavigationButton({
    super.key,
    required this.title,
    required this.url,
    // required this.color,
  });
  final String title;
  final String url;
  // final Colors color;
  @override
  Widget build(BuildContext context) {
    return OutlinedButton(
      onPressed: () => NavigationService.navigateTo(url),
      child: Text(title, style: TextStyle(fontSize: 18)),
    );
  }
}
