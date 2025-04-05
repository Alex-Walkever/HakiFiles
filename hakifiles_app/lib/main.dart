import 'package:flutter/material.dart';
import 'package:flutter_web_plugins/url_strategy.dart';
import 'package:hakifiles_app/Services/index.dart';

import 'package:hakifiles_app/layouts/index.dart';

import 'package:hakifiles_app/router/index.dart';

void main() {
  usePathUrlStrategy();
  HakiRouter.configureRoutes();
  runApp(const MyApp());
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
