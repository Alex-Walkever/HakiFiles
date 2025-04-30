import 'package:flutter/material.dart';
import 'package:hakifiles_app/models/entities/card_info_category.dart';
import 'package:hakifiles_app/tools/index.dart';
import 'package:intl/intl.dart';

Widget getImageWidget({String? img, double? width, double? height}) {
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

ImageProvider<Object> getImageProvider({String? img}) {
  return (img == null) ? AssetImage('images/no-image.jpg') : NetworkImage(img);
}

getGradient(List<String> strColors) {
  List<Color> colors = <Color>[];
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

String createQueryString(Map<String, String> query) {
  String url = '';
  query.forEach((String key, String value) {
    url += '$key=$value&';
  });
  return url;
}

String cleanUpList(List<String> list) {
  String clean = list.toString();
  clean = clean.replaceAll(' ', '');
  clean = clean.replaceAll('[', '');
  clean = clean.replaceAll(']', '');
  return clean;
}

String getNameFromCategory(CardInfoCategory card) {
  if (card.cardInfo.category == 'CHARACTER') {
    return card.characterCard!.name;
  } else if (card.cardInfo.category == 'STAGE' ||
      card.cardInfo.category == 'EVENT') {
    return card.eventStageCard!.name;
  }
  return card.leaderCard!.name;
}

int getCostFromCategory(CardInfoCategory card) {
  if (card.cardInfo.category == 'CHARACTER') {
    return card.characterCard!.cost;
  } else if (card.cardInfo.category == 'STAGE' ||
      card.cardInfo.category == 'EVENT') {
    return card.eventStageCard!.cost;
  }
  return card.leaderCard!.life;
}
