// To parse this JSON data, do
//
//     final cardInfo = cardInfoFromJson(jsonString);

import 'dart:convert';

List<CardInfo> cardInfoFromJson(String str) =>
    List<CardInfo>.from(json.decode(str).map((x) => CardInfo.fromJson(x)));

String cardInfoToJson(List<CardInfo> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class CardInfo {
  final int id;
  final dynamic image;
  final String cardId;
  final String category;
  final int alternateArt;
  final String product;
  final String productCode;
  final String series;
  final String rarity;
  final List<String> colorCards;
  final int block;
  final String tournamentStatus;
  final int cardUsage;

  CardInfo({
    required this.id,
    required this.image,
    required this.cardId,
    required this.category,
    required this.alternateArt,
    required this.product,
    required this.productCode,
    required this.series,
    required this.rarity,
    required this.colorCards,
    required this.block,
    required this.tournamentStatus,
    required this.cardUsage,
  });

  factory CardInfo.fromJson(Map<String, dynamic> json) => CardInfo(
    id: json["id"],
    image: json["image"],
    cardId: json["cardId"],
    category: json["category"],
    alternateArt: json["alternateArt"],
    product: json["product"],
    productCode: json["productCode"],
    series: json["series"],
    rarity: json["rarity"],
    colorCards: List<String>.from(json["colorCards"] as List),
    block: json["block"],
    tournamentStatus: json["tournamentStatus"],
    cardUsage: json["cardUsage"],
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "image": image,
    "cardId": cardId,
    "category": category,
    "alternateArt": alternateArt,
    "product": product,
    "productCode": productCode,
    "series": series,
    "rarity": rarity,
    "colorCards": colorCards,
    "block": block,
    "tournamentStatus": tournamentStatus,
    "cardUsage": cardUsage,
  };
}
