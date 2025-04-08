// To parse this JSON data, do
//
//     final eventCard = eventCardFromJson(jsonString);

import 'dart:convert';

EventStageCard eventStageCardFromJson(String str) =>
    EventStageCard.fromJson(json.decode(str));

String eventStageCardToJson(EventStageCard data) => json.encode(data.toJson());

class EventStageCard {
  final String name;
  final List<String> type;
  final String effects;
  final String triggerEffect;
  final int cost;
  final String cardId;

  EventStageCard({
    required this.name,
    required this.type,
    required this.effects,
    required this.triggerEffect,
    required this.cost,
    required this.cardId,
  });

  factory EventStageCard.fromJson(Map<String, dynamic> json) => EventStageCard(
    name: json["name"],
    type: List<String>.from(json["type"].map((x) => x)),
    effects: json["effects"],
    triggerEffect: json["triggerEffect"],
    cost: json["cost"],
    cardId: json["cardId"],
  );

  Map<String, dynamic> toJson() => {
    "name": name,
    "type": List<dynamic>.from(type.map((x) => x)),
    "effects": effects,
    "triggerEffect": triggerEffect,
    "cost": cost,
    "cardId": cardId,
  };
}
