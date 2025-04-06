import 'package:flutter/material.dart';

import 'package:flutter_web_plugins/url_strategy.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';

import 'package:hakifiles_app/layouts/index.dart';
import 'package:hakifiles_app/providers/index.dart';

import 'package:hakifiles_app/router/index.dart';
import 'package:provider/provider.dart';

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
      providers: [
        ChangeNotifierProvider(create: (context) => ProductsProvider()),
      ],
      child: MyApp(),
    );
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'HakiFiles',
      initialRoute: HakiRouter.rootRoute,
      onGenerateRoute: HakiRouter.router.generator,
      navigatorKey: NavigationService.navigatorKey,
      builder: (context, child) {
        return HomeLayout(child: child!);
      },
    );
  }
}
