import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/dialogs/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/router/index.dart';
import 'package:hakifiles_app/shared/widget/index.dart';

class NavBar extends StatelessWidget {
  const NavBar({super.key});

  @override
  Widget build(BuildContext context) {
    final AuthProvider authProvider = Provider.of<AuthProvider>(context);
    final ThemeProvider themeProvider = Provider.of<ThemeProvider>(context);
    return Container(
      width: double.infinity,
      height: 75,
      decoration: buildBoxDecoration(),
      child: Row(
        children: <Widget>[
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
          if (authProvider.authStatus ==
              AuthStatus.notAuthenticated) ...<Widget>[
            CustomNavigationButton(
              title: 'Login / Register',
              url: HakiRouter.loginRoute,
            ),
          ],
          if (authProvider.authStatus == AuthStatus.authenticated) ...<Widget>[
            OutlinedButton(
              onPressed: () {
                NavigationService.showDialogInWeb(CreateDeckDialog());
              },
              child: Text('Crear deck'),
            ),
            SizedBox(width: 20),
            Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Text('Welcome ${authProvider.user!.name}'),
                OutlinedButton(
                  onPressed: () => authProvider.logout(),
                  child: Text('Logout'),
                ),
              ],
            ),
          ],
          SizedBox(width: 10),
          //theme change
          IconButton(
            onPressed:
                () => themeProvider.changeTheme(
                  themeProvider.isDark ? ThemeData.light() : ThemeData.dark(),
                  !themeProvider.isDark,
                ),
            icon: Icon(
              themeProvider.isDark
                  ? Icons.light_mode_outlined
                  : Icons.dark_mode_outlined,
            ),
          ),
          SizedBox(width: 10),
        ],
      ),
    );
  }

  BoxDecoration buildBoxDecoration() =>
      BoxDecoration(color: Colors.transparent);
}
