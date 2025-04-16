class Game {
  int games;
  int wins;
  int looses;
  String leaderId;

  Game({
    required this.games,
    required this.wins,
    required this.looses,
    required this.leaderId,
  });

  double getWinRate() {
    return wins / games;
  }

  factory Game.fromJson(Map<String, dynamic> json) => Game(
    games: json["games"],
    wins: json["wins"],
    looses: json["looses"],
    leaderId: json["leaderId"],
  );
}
