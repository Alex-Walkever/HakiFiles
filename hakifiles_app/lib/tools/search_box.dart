import 'package:flutter/material.dart';
import 'package:hakifiles_app/tools/index.dart';

class SearchBox extends StatelessWidget {
  const SearchBox({
    super.key,
    this.height,
    this.width,
    required this.hint,
    this.onChanged,
  });
  final double? height;
  final double? width;
  final String hint;
  final Function(String)? onChanged;
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: width,
      height: height,
      child: TextField(
        onChanged: onChanged,
        decoration: CustomInputs.searchInputDecoration(
          hint: hint,
          icon: Icons.search,
        ),
      ),
    );
  }
}
