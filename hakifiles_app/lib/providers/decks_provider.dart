import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';

class DecksProvider extends ChangeNotifier {
  static Deck? currentDeck;
  static User? user;

  createDeck(CreateDeckDto dto) {
    final data = dto.toJson();
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
    currentDeck = null;
    await HakifilesApi.httpGet(
      '${HakiRouter.decksRoute}/$deckId',
    ).then((json) => _getDetails(json));
  }

  _getDetails(Map<String, dynamic> json) {
    currentDeck = Deck.fromJson(json);
    HakifilesApi.httpGet(
      '/user/details/${currentDeck!.userId.toString()}',
    ).then((userJson) {
      user = User.fromJson(userJson);
      notifyListeners();
    });
  }
}
