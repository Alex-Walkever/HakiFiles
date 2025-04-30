import 'package:flutter/material.dart';

import 'package:flutter_web_plugins/url_strategy.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';

import 'package:hakifiles_app/layouts/index.dart';
import 'package:hakifiles_app/providers/index.dart';

import 'package:hakifiles_app/router/index.dart';
import 'package:hakifiles_app/theme/index.dart';
import 'package:provider/single_child_widget.dart';

void main() async {
  usePathUrlStrategy();
  HakiRouter.configureRoutes();
  await LocalStorage.configurePrefs();
  HakifilesApi.configureDio();
  runApp(const AppState());
}

class AppState extends StatelessWidget {
  const AppState({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: <SingleChildWidget>[
        ChangeNotifierProvider<AuthProvider>(
          lazy: false,
          create: (BuildContext context) => AuthProvider(),
        ),
        ChangeNotifierProvider<ThemeProvider>(
          lazy: false,
          create: (BuildContext context) => ThemeProvider(),
        ),
        ChangeNotifierProvider<ProductsProvider>(
          create: (BuildContext context) => ProductsProvider(),
        ),
        ChangeNotifierProvider<CardsProvider>(
          create: (BuildContext context) => CardsProvider(),
        ),
        ChangeNotifierProvider<DecksProvider>(
          create: (BuildContext context) => DecksProvider(),
        ),
        ChangeNotifierProvider<SingleDeckProvider>(
          create: (BuildContext context) => SingleDeckProvider(),
        ),
      ],
      child: MyApp(),
    );
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    final ThemeProvider themeProvider = Provider.of<ThemeProvider>(context);
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: themeProvider.theme,
      title: 'HakiFiles',
      initialRoute: HakiRouter.rootRoute,
      onGenerateRoute: HakiRouter.router.generator,
      navigatorKey: NavigationService.navigatorKey,
      scaffoldMessengerKey: NotificationsService.messengerKey,
      scrollBehavior: CustomScrollBehavior(),
      builder: (BuildContext context, Widget? child) {
        if (child != null) {
          return HomeLayout(child: child);
        }
        return SplashLayout();
      },
    );
  }
}
