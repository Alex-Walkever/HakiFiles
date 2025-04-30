import 'package:flutter/material.dart';
import 'package:hakifiles_app/shared/index.dart';

class HomeLayout extends StatelessWidget {
  const HomeLayout({super.key, required this.child});

  final Widget child;

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;
    return Scaffold(
      body: SafeArea(
        child: ListView(
          children: <Widget>[
            NavBar(),
            SizedBox(height: size.height - 75, width: size.width, child: child),
          ],
        ),
      ),
    );
  }
}
