import 'package:hakifiles_app/models/entities/user.dart';

class AuthResponse {
  final User user;
  final String jwt;

  AuthResponse({required this.user, required this.jwt});

  factory AuthResponse.fromJson(Map<String, dynamic> json) =>
      AuthResponse(user: User.fromJson(json["user"]), jwt: json["jwt"]);

  Map<String, dynamic> toJson() => {"user": user.toJson(), "jwt": jwt};
}
