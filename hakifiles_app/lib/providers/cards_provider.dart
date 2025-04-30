import 'package:flutter/material.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';
import 'package:hakifiles_app/tools/index.dart';

class CardsProvider extends ChangeNotifier {
  List<CardInfo> cardsInfo = <CardInfo>[];
  CardInfo? cardInfo;
  CharacterCard? characterCard;
  LeaderCard? leaderCard;
  EventStageCard? eventStageCard;
  bool isLoading = true;

  getCardsInfo(String product) async {
    _cleanUp();
    final dynamic response = await HakifilesApi.httpGet(
      '${HakiRouter.cardsRoute}/product?product=$product',
    );

    final CardInfoResponse cardInfoResponse = CardInfoResponse.fromJson(
      response,
    );

    cardsInfo = <CardInfo>[...cardInfoResponse.cardInfoList];
    isLoading = false;

    notifyListeners();
  }

  getCard(String cardId) async {
    _cleanUp();
    final dynamic response = await HakifilesApi.httpGet(
      '${HakiRouter.cardsRoute}/$cardId',
    );
    final CardInfoCategory cardInfoCategoryResponse = CardInfoCategory.fromJson(
      response,
    );
    cardInfo = cardInfoCategoryResponse.cardInfo;
    characterCard = cardInfoCategoryResponse.characterCard;
    leaderCard = cardInfoCategoryResponse.leaderCard;
    eventStageCard = cardInfoCategoryResponse.eventStageCard;

    isLoading = false;

    notifyListeners();
  }

  Future<List<CardInfo>> getLeadersByName(String leadersName) async {
    _cleanUp();
    final dynamic response = await HakifilesApi.httpGet(
      '${HakiRouter.cardsRoute}/search/leaders/$leadersName',
    );

    final CardInfoResponse cardInfoResponse = CardInfoResponse.fromJson(
      response,
    );

    cardsInfo = <CardInfo>[...cardInfoResponse.cardInfoList];
    isLoading = false;

    notifyListeners();
    return cardsInfo;
  }

  Future<List<CardInfoCategory>> getCardsByDynamicSearch(
    Map<String, String> query,
  ) async {
    final String queryString = createQueryString(query);
    final dynamic response = await HakifilesApi.httpGet(
      '${HakiRouter.cardsRoute}/search?$queryString',
    );

    final List<CardInfoCategory> cardInfoResponse = deckListFromJson(response);

    notifyListeners();
    return cardInfoResponse;
  }

  _cleanUp() {
    cardInfo = null;
    characterCard = null;
    leaderCard = null;
    eventStageCard = null;
    isLoading = true;
  }
}
