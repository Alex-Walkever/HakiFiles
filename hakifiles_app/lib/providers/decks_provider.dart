import 'package:flutter/material.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';

class DecksProvider extends ChangeNotifier {
  List<Deck> userDecks = <Deck>[];
  List<Deck> recentDecks = <Deck>[];
  List<Deck> likedDecks = <Deck>[];
  List<Deck> viewDecks = <Deck>[];

  getDecks(User? currentUser) {
    if (currentUser != null) {
      HakifilesApi.httpGet(
        '${HakiRouter.decksRoute}/user/${currentUser.name}',
      ).then((dynamic json) {
        userDecks = decksFromJson(json);
        notifyListeners();
      });
    }
  }
}
