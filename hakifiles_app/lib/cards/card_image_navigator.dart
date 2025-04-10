import 'package:flutter/material.dart';
import 'package:hakifiles_app/tools/hakifiles_functions.dart';

class CardImageNavigator extends StatelessWidget {
  const CardImageNavigator({super.key, this.img, required this.onPressed});

  final String? img;
  final Function onPressed;

  @override
  Widget build(BuildContext context) {
    final image = getImageWidget(img: img);

    return GestureDetector(
      onTap: () => onPressed(),
      child: MouseRegion(
        cursor: SystemMouseCursors.click,
        child: Container(child: image),
      ),
    );
  }
}
