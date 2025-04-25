import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';
import 'package:hakifiles_app/tools/index.dart';

class SingleDeckProvider extends ChangeNotifier {
  static Deck? currentDeck;
  static User? user;
  static Map<CardInfoCategory, int> leaderList = <CardInfoCategory, int>{};
  static Map<CardInfoCategory, int> characterList = <CardInfoCategory, int>{};
  static Map<CardInfoCategory, int> eventList = <CardInfoCategory, int>{};
  static Map<CardInfoCategory, int> stageList = <CardInfoCategory, int>{};

  createDeck(CreateDeckDto dto) {
    final Map<String, dynamic> data = dto.toJson();
    _cleanUp();
    HakifilesApi.httpPost(HakiRouter.decksRoute, data)
        .then((dynamic json) {
          _getDetails(json);
          NavigationService.navigateToAndRemove(
            '${HakiRouter.decksRoute}/${currentDeck!.id}',
          );
        })
        .catchError((dynamic e) {});
  }

  deckDetails(String deckId) async {
    _cleanUp();
    await HakifilesApi.httpGet(
      '${HakiRouter.decksRoute}/$deckId',
    ).then((dynamic json) => _getDetails(json));
  }

  addCardToDeck(CardInfoCategory cardInfoCategory, int amount) async {
    if (currentDeck != null) {
      List<Map<String, dynamic>> data = <Map<String, dynamic>>[
        <String, dynamic>{
          'cardId': cardInfoCategory.cardInfo.cardId,
          'amount': amount,
        },
      ];
      await HakifilesApi.httpPut(
        '${HakiRouter.decksRoute}/${currentDeck!.id}',
        data,
      ).then((dynamic value) {
        if (cardInfoCategory.cardInfo.category == 'CHARACTER') {
          if (characterList.containsKey(cardInfoCategory)) {
            characterList[cardInfoCategory] =
                characterList[cardInfoCategory]! + amount;
            if (characterList[cardInfoCategory]! <= 0) {
              characterList.remove(cardInfoCategory);
            }
          } else {
            characterList[cardInfoCategory] = amount;
          }
        }
        if (cardInfoCategory.cardInfo.category == 'STAGE') {
          if (stageList.containsKey(cardInfoCategory)) {
            stageList[cardInfoCategory] = stageList[cardInfoCategory]! + amount;
            if (stageList[cardInfoCategory]! <= 0) {
              stageList.remove(cardInfoCategory);
            }
          } else {
            stageList[cardInfoCategory] = amount;
          }
        }
        if (cardInfoCategory.cardInfo.category == 'EVENT') {
          if (eventList.containsKey(cardInfoCategory)) {
            eventList[cardInfoCategory] = eventList[cardInfoCategory]! + amount;
            if (eventList[cardInfoCategory]! <= 0) {
              eventList.remove(cardInfoCategory);
            }
          } else {
            eventList[cardInfoCategory] = amount;
          }
        }
        notifyListeners();
      });
    }
  }

  int getTotalCards() {
    int total = 1;
    total += getTotalCharacters();
    total += getTotalEvents();
    total += getTotalStages();
    return total;
  }

  int getTotalCharacters() {
    int total = 0;
    characterList.forEach((CardInfoCategory key, int value) => total += value);
    return total;
  }

  int getTotalEvents() {
    int total = 0;
    eventList.forEach((CardInfoCategory key, int value) => total += value);
    return total;
  }

  int getTotalStages() {
    int total = 0;
    stageList.forEach((CardInfoCategory key, int value) => total += value);
    return total;
  }

  DeckBuildingErrors validateDeck() {
    DeckBuildingErrors validateDeck = DeckBuildingErrors();
    int totalCards = 1;
    CardInfoCategory leader = leaderList.keys.first;

    characterList.forEach((CardInfoCategory key, int value) {
      if (value > 4) {
        validateDeck.noMore4Copies +=
            ' - ${key.characterCard!.name} has ${value - 4} more cards\n';
      }
      if (key.cardInfo.tournamentStatus.compareTo('LEGAL') != 0) {
        validateDeck.noBannedCards += ' - ${key.characterCard!.name}\n';
      }
      if (!cleanUpList(
        leader.cardInfo.colorCards,
      ).contains(key.cardInfo.colorCards.first)) {
        validateDeck.noSameColor += ' - ${key.characterCard!.name}\n';
      }
      totalCards += value;
    });

    stageList.forEach((CardInfoCategory key, int value) {
      if (value > 4) {
        validateDeck.noMore4Copies +=
            ' - ${key.eventStageCard!.name} has ${value - 4} more cards\n';
      }
      if (key.cardInfo.tournamentStatus.compareTo('LEGAL') != 0) {
        validateDeck.noBannedCards += ' - ${key.eventStageCard!.name}\n';
      }
      if (!cleanUpList(
        leader.cardInfo.colorCards,
      ).contains(key.cardInfo.colorCards.first)) {
        validateDeck.noSameColor += ' - ${key.eventStageCard!.name}\n';
      }
      totalCards += value;
    });

    eventList.forEach((CardInfoCategory key, int value) {
      if (value > 4) {
        validateDeck.noMore4Copies +=
            ' - ${key.eventStageCard!.name} has ${value - 4} more cards\n';
      }
      if (key.cardInfo.tournamentStatus.compareTo('LEGAL') != 0) {
        validateDeck.noBannedCards += ' - ${key.eventStageCard!.name}\n';
      }
      if (!cleanUpList(
        leader.cardInfo.colorCards,
      ).contains(key.cardInfo.colorCards.first)) {
        validateDeck.noSameColor += ' - ${key.eventStageCard!.name}\n';
      }
      totalCards += value;
    });

    if (totalCards < 51) {
      validateDeck.deckTotal = 'Have ${51 - totalCards} less cards in the deck';
    } else if (totalCards > 51) {
      validateDeck.deckTotal = 'Have ${totalCards - 51} more cards in the deck';
    } else {
      validateDeck.deckTotal = '';
    }
    validateDeck.validateErrors();
    return validateDeck;
  }

  _cleanUp() {
    currentDeck = null;
    user = null;
    leaderList = <CardInfoCategory, int>{};
    characterList = <CardInfoCategory, int>{};
    eventList = <CardInfoCategory, int>{};
    stageList = <CardInfoCategory, int>{};
  }

  _getDetails(Map<String, dynamic> json) {
    currentDeck = Deck.fromJson(json);

    HakifilesApi.httpPost(
      '${HakiRouter.cardsRoute}/deck-list',
      <String, dynamic>{'list': currentDeck!.list},
    ).then((dynamic deckJson) {
      final List<CardInfoCategory> deckList = deckListFromJson(deckJson);
      for (CardInfoCategory card in deckList) {
        if (card.cardInfo.category == 'CHARACTER') {
          if (characterList.containsKey(card)) {
            characterList[card] = characterList[card]! + 1;
          } else {
            characterList[card] = 1;
          }
        }
        if (card.cardInfo.category == 'STAGE') {
          if (stageList.containsKey(card)) {
            stageList[card] = stageList[card]! + 1;
          } else {
            stageList[card] = 1;
          }
        }
        if (card.cardInfo.category == 'EVENT') {
          if (eventList.containsKey(card)) {
            eventList[card] = eventList[card]! + 1;
          } else {
            eventList[card] = 1;
          }
        }
      }
      HakifilesApi.httpGet(
        '${HakiRouter.cardsRoute}/${currentDeck!.leader.cardId}',
      ).then((dynamic leaderJson) {
        leaderList[CardInfoCategory.fromJson(leaderJson)] = 1;
        HakifilesApi.httpGet(
          '/user/details/${currentDeck!.userId.toString()}',
        ).then((dynamic userJson) {
          user = User.fromJson(userJson);
          notifyListeners();
        });
      });
    });
  }
}
