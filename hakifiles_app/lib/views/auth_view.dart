import 'package:flutter/material.dart';

class AuthView extends StatelessWidget {
  const AuthView({super.key, required this.child});

  final Widget child;

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    return Row(
      children: [
        if (size.width > 900) ...[
          Expanded(child: _BackgroundBody()),
          _DesktopBody(size: size, child: child),
        ],
        if (size.width <= 900)
          Expanded(
            child: Container(
              alignment: Alignment.center,
              margin: EdgeInsets.only(top: 175),
              child: child,
            ),
          ),
      ],
    );
  }
}

class _DesktopBody extends StatelessWidget {
  const _DesktopBody({required this.size, required this.child});

  final Size size;
  final Widget child;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: double.infinity,
      width: 900,
      // color: Colors.black,
      child: Column(
        children: [
          SizedBox(height: size.height * 0.2),
          Expanded(child: child),
          SizedBox(height: size.height * 0.2),
        ],
      ),
    );
  }
}

class _BackgroundBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    return Container(
      decoration: buildBoxDecoration(),
      child: Center(
        child: Image(
          image: AssetImage('images/logo.png'),
          width: size.width * 0.4,
          height: size.height * 0.4,
          alignment: Alignment.center,
        ),
      ),
    );
  }

  BoxDecoration buildBoxDecoration() => BoxDecoration(
    image: DecorationImage(
      image: AssetImage('images/auth-background.png'),
      fit: BoxFit.cover,
    ),
  );
}
