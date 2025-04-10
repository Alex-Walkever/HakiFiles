class Authority {
  final int roleId;
  final String authority;

  Authority({required this.roleId, required this.authority});

  factory Authority.fromJson(Map<String, dynamic> json) =>
      Authority(roleId: json["roleId"], authority: json["authority"]);

  Map<String, dynamic> toJson() => {"roleId": roleId, "authority": authority};
}
