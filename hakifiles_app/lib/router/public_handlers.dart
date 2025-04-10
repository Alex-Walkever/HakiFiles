import 'package:fluro/fluro.dart';
import 'package:hakifiles_app/providers/index.dart';
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
  static Handler cardsByCardId = Handler(
    handlerFunc: (context, parameters) {
      if (parameters['cardId'] != null) {
        return CardView(cardId: parameters['cardId']!.first);
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
      final authProvider = Provider.of<AuthProvider>(context!);
      if (authProvider.authStatus == AuthStatus.notAuthenticated) {
        return AuthView(child: LoginView());
      }
      return HomeView();
    },
  );
  static Handler register = Handler(
    handlerFunc: (context, parameters) {
      final authProvider = Provider.of<AuthProvider>(context!);
      if (authProvider.authStatus == AuthStatus.notAuthenticated) {
        return AuthView(child: RegisterView());
      }
      return HomeView();
    },
  );
}
