import 'package:flutter/material.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/router/index.dart';

class DecksProvider extends ChangeNotifier {
  List<Deck> userDecks = [];
  List<Deck> recentDecks = [];
  List<Deck> likedDecks = [];
  List<Deck> viewDecks = [];

  getDecks(User? currentUser) {
    if (currentUser != null) {
      HakifilesApi.httpGet(
        '${HakiRouter.decksRoute}/user/${currentUser.name}',
      ).then((json) {
        userDecks = decksFromJson(json);
        notifyListeners();
      });
    }
  }
}
