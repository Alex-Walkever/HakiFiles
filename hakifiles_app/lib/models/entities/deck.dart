// To parse this JSON data, do
//
//     final deckCreatedResponse = deckCreatedResponseFromJson(jsonString);

import 'package:hakifiles_app/models/index.dart';

List<Deck> decksFromJson(List<dynamic> json) =>
    List<Deck>.from(json.map((e) => Deck.fromJson(e)).toList());

class Deck {
  final String id;
  final String name;
  final String? description;
  final String? youtubeLink;
  final List<String>? list;
  final CardInfo leader;
  final int userId;
  final String username;
  final List<Game>? games;
  final DateTime publishedOn;
  final DateTime? updatedOn;
  final bool private;
  final int views;
  final int likes;
  final String? background;
  final List<String> consideringList;

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
    required this.background,
    required this.consideringList,
    required this.username,
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
    background: json["backgroundImage"] ?? '',
    consideringList: List.castFrom<dynamic, String>(
      json["consideringList"] ?? {},
    ),
    username: json["username"],
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

  double getWinRates() {
    double totalWins = 0;
    double totalGames = 0;
    if (games == null || games!.isEmpty) {
      return 0;
    }
    for (Game element in games!) {
      totalWins += element.wins;
      totalGames += element.games;
    }
    return totalWins / totalGames;
  }

  @override
  String toString() {
    return 'name: $name';
  }
}
