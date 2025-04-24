import 'package:hakifiles_app/models/index.dart';

List<CardInfoCategory> deckListFromJson(List<dynamic> str) =>
    List<CardInfoCategory>.from(
      str.map((dynamic e) => CardInfoCategory.fromJson(e)).toList(),
    );

class CardInfoCategory {
  CardInfo cardInfo;
  CharacterCard? characterCard;
  EventStageCard? eventStageCard;
  LeaderCard? leaderCard;

  CardInfoCategory({
    required this.cardInfo,
    this.characterCard,
    this.eventStageCard,
    this.leaderCard,
  });
  @override
  bool operator ==(Object other) {
    return other is CardInfoCategory &&
        other.cardInfo.cardId == cardInfo.cardId &&
        other.cardInfo.productCode == cardInfo.productCode;
  }

  @override
  int get hashCode => Object.hash(cardInfo.cardId, cardInfo.productCode);

  factory CardInfoCategory.fromJson(Map<String, dynamic> json) {
    CharacterCard? characterCard;
    EventStageCard? eventStageCard;
    LeaderCard? leaderCard;
    CardInfo cardInfo = CardInfo.fromJson(json['cardInfo']);

    if (cardInfo.category == 'CHARACTER') {
      characterCard = CharacterCard.fromJson(json['characterCard']);
    } else if (cardInfo.category == 'EVENT') {
      eventStageCard = EventStageCard.fromJson(json['eventCard']);
    } else if (cardInfo.category == 'LEADER') {
      leaderCard = LeaderCard.fromJson(json['leaderCard']);
    } else if (cardInfo.category == 'STAGE') {
      eventStageCard = EventStageCard.fromJson(json['stageCard']);
    }

    return CardInfoCategory(
      cardInfo: cardInfo,
      characterCard: characterCard,
      eventStageCard: eventStageCard,
      leaderCard: leaderCard,
    );
  }
}
