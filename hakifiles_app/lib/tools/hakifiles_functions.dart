import 'package:flutter/material.dart';
import 'package:hakifiles_app/tools/index.dart';

getImageWidget({String? img, double? width, double? height}) {
  return (img == null)
      ? Image(
        image: AssetImage('images/no-image.jpg'),
        width: width,
        height: height,
      )
      : FadeInImage.assetNetwork(
        placeholder: 'images/loader.gif',
        image: img,
        width: width,
        height: height,
      );
}

getGradient(List<String> strColors) {
  List<Color> colors = [];
  colors.add(colorMap[strColors.first.toLowerCase()]!);
  if (strColors.length == 2) {
    colors.add(colorMap[strColors[1].toLowerCase()]!);
  } else {
    colors.add(colorMap[strColors.first.toLowerCase()]!);
  }
  return LinearGradient(colors: colors);
}
