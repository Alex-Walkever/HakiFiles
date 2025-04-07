import 'package:flutter/material.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';

class CardInfoProvider extends ChangeNotifier {
  List<CardInfo> cardsInfo = [];
  bool isLoading = true;

  getCardsInfo(String product) async {
    final response = await HakifilesApi.httpGet(
      '/cards/product?product=$product',
    );

    final cardInfoResponse = CardInfoResponse.fromJson(response);

    cardsInfo = [...cardInfoResponse.cardInfoList];
    isLoading = false;

    notifyListeners();
  }
}
