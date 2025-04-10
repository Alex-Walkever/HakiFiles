import 'package:hakifiles_app/models/entities/authority.dart';

class User {
  final int userId;
  final String name;
  final String password;
  final String email;
  final List<dynamic> deckList;
  final List<Authority> authorities;

  User({
    required this.userId,
    required this.name,
    required this.password,
    required this.email,
    required this.deckList,
    required this.authorities,
  });

  factory User.fromJson(Map<String, dynamic> json) => User(
    userId: json["userId"],
    name: json["name"],
    password: json["password"],
    email: json["email"],
    deckList: List<dynamic>.from(json["deckList"].map((x) => x)),
    authorities: List<Authority>.from(
      json["authorities"].map((x) => Authority.fromJson(x)),
    ),
  );

  Map<String, dynamic> toJson() => {
    "userId": userId,
    "name": name,
    "password": password,
    "email": email,
    "deckList": List<dynamic>.from(deckList.map((x) => x)),
    "authorities": List<dynamic>.from(authorities.map((x) => x.toJson())),
  };
}
