import 'package:flutter/material.dart';

class IconWithText extends StatelessWidget {
  const IconWithText({
    super.key,
    required this.text,
    this.icon,
    this.onPressed,
    this.tooltip,
    this.color,
  });

  final String text;
  final String? tooltip;
  final IconData? icon;
  final Function? onPressed;
  final Color? color;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        IconButton(
          icon: Icon(icon),
          onPressed: onPressed != null ? () => onPressed!() : null,
          tooltip: tooltip,
          style: ButtonStyle(iconColor: WidgetStatePropertyAll(color)),
        ),
        SizedBox(width: 1),
        Text(text, style: TextStyle(color: color)),
      ],
    );
  }
}
