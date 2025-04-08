// To parse this JSON data, do
//
//     final leaderCard = leaderCardFromJson(jsonString);

import 'dart:convert';

LeaderCard leaderCardFromJson(String str) =>
    LeaderCard.fromJson(json.decode(str));

String leaderCardToJson(LeaderCard data) => json.encode(data.toJson());

class LeaderCard {
  final String name;
  final List<String> type;
  final String effects;
  final int life;
  final int power;
  final List<String> attribute;
  final String cardId;

  LeaderCard({
    required this.name,
    required this.type,
    required this.effects,
    required this.life,
    required this.power,
    required this.attribute,
    required this.cardId,
  });

  factory LeaderCard.fromJson(Map<String, dynamic> json) => LeaderCard(
    name: json["name"],
    type: List<String>.from(json["type"].map((x) => x)),
    effects: json["effects"],
    life: json["life"],
    power: json["power"],
    attribute: List<String>.from(json["attribute"].map((x) => x)),
    cardId: json["cardId"],
  );

  Map<String, dynamic> toJson() => {
    "name": name,
    "type": List<dynamic>.from(type.map((x) => x)),
    "effects": effects,
    "life": life,
    "power": power,
    "attribute": List<dynamic>.from(attribute.map((x) => x)),
    "cardId": cardId,
  };
}
