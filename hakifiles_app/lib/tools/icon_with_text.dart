import 'package:flutter/material.dart';

class IconWithText extends StatelessWidget {
  const IconWithText({
    super.key,
    required this.text,
    this.icon,
    this.onPressed,
    this.tooltip,
  });

  final String text;
  final String? tooltip;
  final IconData? icon;
  final Function? onPressed;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        IconButton(
          icon: Icon(icon),
          onPressed: onPressed != null ? () => onPressed!() : null,
          tooltip: tooltip,
        ),
        SizedBox(width: 1),
        Text(text),
      ],
    );
  }
}
