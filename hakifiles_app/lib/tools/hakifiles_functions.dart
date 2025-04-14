import 'package:flutter/material.dart';
import 'package:hakifiles_app/models/entities/card_info_category.dart';
import 'package:hakifiles_app/tools/index.dart';
import 'package:intl/intl.dart';

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

String lastTimeModify(DateTime publishOn, DateTime updatedOn) {
  DateFormat format = DateFormat('d, MMMM, yyyy');
  if (publishOn == updatedOn) {
    return 'Published on: ${format.format(publishOn).toString()}';
  }
  return '';
}

int sortByCategory(CardInfoCategory a, CardInfoCategory b) {
  if (a.cardInfo.category == b.cardInfo.category) {
    return 1;
  }
  return 0;
}
