import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/router/index.dart';

class Logo extends StatelessWidget {
  const Logo({super.key});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () => NavigationService.navigateTo(HakiRouter.rootRoute),
      child: MouseRegion(
        cursor: SystemMouseCursors.click,
        child: Image(
          image: AssetImage('images/logo.png'),
          height: 50,
          width: 100,
        ),
      ),
    );
  }
}
