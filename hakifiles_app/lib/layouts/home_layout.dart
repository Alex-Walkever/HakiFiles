import 'package:flutter/material.dart';
import 'package:hakifiles_app/shared/index.dart';

class HomeLayout extends StatefulWidget {
  const HomeLayout({super.key, required this.child});

  final Widget child;

  @override
  State<HomeLayout> createState() => _HomeLayoutState();
}

class _HomeLayoutState extends State<HomeLayout> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(children: [NavBar(), Expanded(child: widget.child)]),
    );
  }
}
