import 'package:hakifiles_app/models/index.dart';

class CardInfoCategoryResponse {
  CardInfo cardInfo;
  CharacterCard? characterCard;
  EventStageCard? eventStageCard;
  LeaderCard? leaderCard;

  CardInfoCategoryResponse({
    required this.cardInfo,
    this.characterCard,
    this.eventStageCard,
    this.leaderCard,
  });

  factory CardInfoCategoryResponse.fromJson(Map<String, dynamic> json) {
    CharacterCard? characterCard;
    EventStageCard? eventStageCard;
    LeaderCard? leaderCard;
    CardInfo cardInfo = CardInfo.fromJson(json["cardInfo"]);

    if (cardInfo.category == 'CHARACTER') {
      characterCard = CharacterCard.fromJson(json["characterCard"]);
    } else if (cardInfo.category == 'EVENT') {
      eventStageCard = EventStageCard.fromJson(json["eventCard"]);
    } else if (cardInfo.category == 'LEADER') {
      leaderCard = LeaderCard.fromJson(json["leaderCard"]);
    } else if (cardInfo.category == 'STAGE') {
      eventStageCard = EventStageCard.fromJson(json["stageCard"]);
    }

    return CardInfoCategoryResponse(
      cardInfo: cardInfo,
      characterCard: characterCard,
      eventStageCard: eventStageCard,
      leaderCard: leaderCard,
    );
  }
}
