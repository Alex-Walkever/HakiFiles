// To parse this JSON data, do
//
//     final characterCard = characterCardFromJson(jsonString);

import 'dart:convert';

CharacterCard characterCardFromJson(String str) =>
    CharacterCard.fromJson(json.decode(str));

String characterCardToJson(CharacterCard data) => json.encode(data.toJson());

class CharacterCard {
  final String name;
  final List<String> type;
  final String effects;
  final String triggerEffect;
  final int cost;
  final int power;
  final int counterPower;
  final List<String> attribute;
  final String cardId;

  CharacterCard({
    required this.name,
    required this.type,
    required this.effects,
    required this.triggerEffect,
    required this.cost,
    required this.power,
    required this.counterPower,
    required this.attribute,
    required this.cardId,
  });

  factory CharacterCard.fromJson(Map<String, dynamic> json) => CharacterCard(
    name: json["name"],
    type: List<String>.from(json["type"].map((x) => x)),
    effects: json["effects"],
    triggerEffect: json["triggerEffect"],
    cost: json["cost"],
    power: json["power"],
    counterPower: json["counterPower"],
    attribute: List<String>.from(json["attribute"].map((x) => x)),
    cardId: json["cardId"],
  );

  Map<String, dynamic> toJson() => {
    "name": name,
    "type": List<dynamic>.from(type.map((x) => x)),
    "effects": effects,
    "triggerEffect": triggerEffect,
    "cost": cost,
    "power": power,
    "counterPower": counterPower,
    "attribute": List<dynamic>.from(attribute.map((x) => x)),
    "cardId": cardId,
  };
}
