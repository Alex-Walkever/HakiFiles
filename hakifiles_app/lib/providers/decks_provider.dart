import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';

class DecksProvider extends ChangeNotifier {
  List<Deck> userDecks = <Deck>[];
  List<Deck> recentDecks = <Deck>[];
  List<Deck> likedDecks = <Deck>[];
  List<Deck> viewDecks = <Deck>[];

  getUserDecks(User? currentUser) {
    if (currentUser != null) {
      HakifilesApi.httpGet(
        '${HakiRouter.decksRoute}/user/${currentUser.name}',
      ).then((dynamic json) {
        userDecks = decksFromJson(json);
        notifyListeners();
      });
    }
  }

  addCardToDeck(String cardId, int amount, Deck deck) async {
    List<Map<String, dynamic>> data = <Map<String, dynamic>>[
      <String, dynamic>{'cardId': cardId, 'amount': amount},
    ];
    await HakifilesApi.httpPut(
      '${HakiRouter.decksRoute}/${deck.id}',
      data,
    ).then((dynamic value) {
      NotificationsService.showSnacknar('Added to ${deck.name}');
      notifyListeners();
    });
  }
}
