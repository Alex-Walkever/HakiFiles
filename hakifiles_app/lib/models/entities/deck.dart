// To parse this JSON data, do
//
//     final deckCreatedResponse = deckCreatedResponseFromJson(jsonString);

import 'dart:convert';

import 'package:hakifiles_app/models/index.dart';

Deck deckCreatedResponseFromJson(String str) => Deck.fromJson(json.decode(str));

String deckCreatedResponseToJson(Deck data) => json.encode(data.toJson());

class Deck {
  final String id;
  final String name;
  final String? description;
  final String? youtubeLink;
  final List<String>? list;
  final CardInfo leader;
  final int userId;
  final List<Game>? games;
  final DateTime publishedOn;
  final DateTime? updatedOn;
  final bool private;
  final int views;
  final int likes;

  Deck({
    required this.id,
    required this.name,
    required this.description,
    required this.youtubeLink,
    required this.list,
    required this.leader,
    required this.userId,
    required this.games,
    required this.publishedOn,
    required this.updatedOn,
    required this.private,
    required this.views,
    required this.likes,
  });

  factory Deck.fromJson(Map<String, dynamic> json) => Deck(
    id: json["id"],
    name: json["name"],
    description: json["description"] ?? '',
    youtubeLink: json["youtubeLink"] ?? '',
    list: List.castFrom<dynamic, String>(json["list"] ?? {}),
    leader: CardInfo.fromJson(json["leader"]),
    userId: json["userId"],
    games: List.castFrom<dynamic, Game>(json["games"] ?? []),
    publishedOn: DateTime.parse(json["publishedOn"]),
    updatedOn: DateTime.parse(json["updatedOn"] ?? json["publishedOn"]),
    private: json["private"],
    views: json["views"] ?? 0,
    likes: json["likes"] ?? 0,
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "name": name,
    "description": description,
    "youtubeLink": youtubeLink,
    "list": list,
    "leader": leader.toJson(),
    "userId": userId,
    "games": games,
    "publishedOn": publishedOn.toIso8601String(),
    "updatedOn": updatedOn!.toIso8601String(),
    "private": private,
  };
}
