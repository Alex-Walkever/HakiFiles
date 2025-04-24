import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';

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
