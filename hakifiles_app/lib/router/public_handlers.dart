import 'package:fluro/fluro.dart';
import 'package:hakifiles_app/views/index.dart';

class PublicHandlers {
  static Handler root = Handler(
    handlerFunc: (context, parameters) {
      return HomeView();
    },
  );
  static Handler cards = Handler(
    handlerFunc: (context, parameters) {
      return ProductsView();
    },
  );
  static Handler cardsByProducts = Handler(
    handlerFunc: (context, parameters) {
      if (parameters['product'] != null) {
        return CardsView(product: parameters['product']!.first);
      }

      return ProductsView();
    },
  );
  static Handler decks = Handler(
    handlerFunc: (context, parameters) {
      return DecksView();
    },
  );
  static Handler stDecks = Handler(
    handlerFunc: (context, parameters) {
      return DecksView();
    },
  );
  static Handler login = Handler(
    handlerFunc: (context, parameters) {
      return LoginView();
    },
  );
}
