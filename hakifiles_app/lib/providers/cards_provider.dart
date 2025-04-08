import 'package:flutter/material.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';

class CardsProvider extends ChangeNotifier {
  List<CardInfo> cardsInfo = [];
  CardInfo? cardInfo;
  CharacterCard? characterCard;
  LeaderCard? leaderCard;
  EventStageCard? eventStageCard;
  bool isLoading = true;

  getCardsInfo(String product) async {
    _cleanUp();
    final response = await HakifilesApi.httpGet(
      '${HakiRouter.cardsRoute}/product?product=$product',
    );

    final cardInfoResponse = CardInfoResponse.fromJson(response);

    cardsInfo = [...cardInfoResponse.cardInfoList];
    isLoading = false;

    notifyListeners();
  }

  getCard(String cardId) async {
    _cleanUp();
    final response = await HakifilesApi.httpGet(
      '${HakiRouter.cardsRoute}/$cardId',
    );
    final cardInfoCategoryResponse = CardInfoCategoryResponse.fromJson(
      response,
    );
    cardInfo = cardInfoCategoryResponse.cardInfo;
    characterCard = cardInfoCategoryResponse.characterCard;
    leaderCard = cardInfoCategoryResponse.leaderCard;
    eventStageCard = cardInfoCategoryResponse.eventStageCard;

    isLoading = false;

    notifyListeners();
  }

  _cleanUp() {
    cardInfo = null;
    characterCard = null;
    leaderCard = null;
    eventStageCard = null;
    isLoading = true;
  }
}
