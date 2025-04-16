import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';

class SingleDeckProvider extends ChangeNotifier {
  static Deck? currentDeck;
  static User? user;
  static List<CardInfoCategory> leaderList = [];
  static List<CardInfoCategory> characterList = [];
  static List<CardInfoCategory> eventList = [];
  static List<CardInfoCategory> stageList = [];

  createDeck(CreateDeckDto dto) {
    final data = dto.toJson();
    _cleanUp();
    HakifilesApi.httpPost(HakiRouter.decksRoute, data)
        .then((json) {
          _getDetails(json);
          NavigationService.navigateToAndRemove(
            '${HakiRouter.decksRoute}/${currentDeck!.id}',
          );
        })
        .catchError((e) {});
  }

  deckDetails(String deckId) async {
    _cleanUp();
    await HakifilesApi.httpGet(
      '${HakiRouter.decksRoute}/$deckId',
    ).then((json) => _getDetails(json));
  }

  _cleanUp() {
    currentDeck = null;
    user = null;
    leaderList = [];
    characterList = [];
    eventList = [];
    stageList = [];
  }

  _getDetails(Map<String, dynamic> json) {
    currentDeck = Deck.fromJson(json);

    HakifilesApi.httpPost('${HakiRouter.cardsRoute}/deck-list', {
      "list": currentDeck!.list,
    }).then((deckJson) {
      final deckList = deckListFromJson(deckJson);
      for (CardInfoCategory card in deckList) {
        if (card.cardInfo.category == "CHARACTER") {
          characterList.add(card);
        }
        if (card.cardInfo.category == "STAGE") {
          stageList.add(card);
        }
        if (card.cardInfo.category == "EVENT") {
          eventList.add(card);
        }
      }
      HakifilesApi.httpGet(
        '${HakiRouter.cardsRoute}/${currentDeck!.leader.cardId}',
      ).then((leaderJson) {
        leaderList.add(CardInfoCategory.fromJson(leaderJson));
        HakifilesApi.httpGet(
          '/user/details/${currentDeck!.userId.toString()}',
        ).then((userJson) {
          user = User.fromJson(userJson);
          notifyListeners();
        });
      });
    });
  }
}
