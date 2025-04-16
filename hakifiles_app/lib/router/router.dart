import 'package:fluro/fluro.dart';

import 'index.dart';

class HakiRouter {
  static final FluroRouter router = FluroRouter();

  //Public routes
  static String rootRoute = '/';
  static String cardsRoute = '/cards';
  static String cardsByProduct = '/cards/:product';
  static String cardRoute = '/card';
  static String cardByCardId = '/card/:cardId';
  static String decksRoute = '/decks';
  static String deckDetailRoute = '/decks/:deckId';
  static String stDecksRoute = '/st-decks';
  static String loginRoute = '/login';
  static String registerRoute = '/register';

  //Admin only routes
  //User only routes
  //404
  static String noPageFound = '/404';

  static configureRoutes() {
    //Public
    //home
    router.define(
      rootRoute,
      handler: PublicHandlers.root,
      transitionType: TransitionType.none,
    );
    //cards
    router.define(
      cardsRoute,
      handler: PublicHandlers.cards,
      transitionType: TransitionType.none,
    );
    router.define(
      cardsByProduct,
      handler: PublicHandlers.cardsByProducts,
      transitionType: TransitionType.none,
    );
    router.define(
      cardByCardId,
      handler: PublicHandlers.cardsByCardId,
      transitionType: TransitionType.none,
    );
    //decks
    router.define(
      decksRoute,
      handler: PublicHandlers.decks,
      transitionType: TransitionType.none,
    );
    router.define(
      stDecksRoute,
      handler: PublicHandlers.stDecks,
      transitionType: TransitionType.none,
    );
    router.define(
      deckDetailRoute,
      handler: PublicHandlers.deckDetails,
      transitionType: TransitionType.none,
    );
    //Auth
    router.define(
      loginRoute,
      handler: PublicHandlers.login,
      transitionType: TransitionType.none,
    );
    router.define(
      registerRoute,
      handler: PublicHandlers.register,
      transitionType: TransitionType.none,
    );

    //404
    router.notFoundHandler = NoPageFoundHandlers.noPageFound;
  }
}
