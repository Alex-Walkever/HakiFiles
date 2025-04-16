class CreateDeckDto {
  CreateDeckDto({
    required this.name,
    this.description,
    this.youtubeLink,
    required this.leader,
    required this.userId,
    required this.isPrivate,
  });
  String name;
  String? description;
  String? youtubeLink;
  String leader;
  int userId;
  bool isPrivate;

  Map<String, dynamic> toJson() => {
    "name": name,
    "description": description,
    "youtubeLink": youtubeLink,
    "leader": leader,
    "userId": userId,
    "isPrivate": isPrivate,
  };
}
