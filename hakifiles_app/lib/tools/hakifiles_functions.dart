import 'package:flutter/material.dart';

getImageWidget({String? img, double? width, double? height}) {
  return (img == null)
      ? Image(
        image: AssetImage('images/no-image.jpg'),
        width: width,
        height: height,
      )
      : FadeInImage.assetNetwork(
        placeholder: 'loader.gif',
        image: img,
        width: width,
        height: height,
      );
}
