import 'package:flutter/material.dart';
import 'package:hakifiles_app/router/index.dart';
import 'package:hakifiles_app/shared/widget/index.dart';

class NavBar extends StatelessWidget {
  const NavBar({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      height: 75,
      decoration: buildBoxDecoration(),
      child: Row(
        children: [
          Spacer(),
          //Logo
          Logo(),
          SizedBox(width: 10),
          //SearchBar
          Container(color: Colors.blue, height: 50, width: 300),
          SizedBox(width: 10),
          //Cards
          CustomNavigationButton(title: 'Cards', url: HakiRouter.cardsRoute),
          SizedBox(width: 10),
          //Decks
          CustomNavigationButton(title: 'Decks', url: HakiRouter.decksRoute),
          SizedBox(width: 10),
          //St decks
          CustomNavigationButton(
            title: 'Starter Decks',
            url: HakiRouter.stDecksRoute,
          ),
          SizedBox(width: 10),
          Spacer(),
          //auth
          CustomNavigationButton(
            title: 'Login / Register',
            url: HakiRouter.loginRoute,
          ),
          SizedBox(width: 10),
          //theme change
          Container(color: Colors.cyan, height: 50, width: 50),
          SizedBox(width: 10),
        ],
      ),
    );
  }

  BoxDecoration buildBoxDecoration() => BoxDecoration();
}
