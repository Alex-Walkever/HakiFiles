import 'package:flutter/material.dart';
import 'package:hakifiles_app/shared/index.dart';

class HomeLayout extends StatelessWidget {
  const HomeLayout({super.key, required this.child});

  final Widget child;

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    return Scaffold(
      // appBar: AppBar(toolbarHeight: 75, flexibleSpace: NavBar()),
      body: SafeArea(
        child: ListView(
          children: [
            NavBar(),
            SizedBox(height: size.height - 75, width: size.width, child: child),
          ],
        ),
      ),
    );
  }
}
