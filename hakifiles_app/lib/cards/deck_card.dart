import 'package:flutter/material.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/tools/index.dart';

class DeckCard extends StatelessWidget {
  const DeckCard({
    super.key,
    required this.width,
    required this.deck,
    this.onPressed,
  });

  final Deck deck;
  final double width;
  final Function? onPressed;

  @override
  Widget build(BuildContext context) {
    final image = getImageProvider(
      img: (deck.background!.isNotEmpty) ? deck.background : null,
    );
    return GestureDetector(
      onTap: () => onPressed != null ? onPressed!() : null,
      child: MouseRegion(
        cursor: SystemMouseCursors.click,
        child: Container(
          width: width,
          padding: EdgeInsets.all(10),
          margin: EdgeInsets.all(8),
          child: Stack(
            children: [
              Container(
                decoration: buildBoxDecoration(image),
                child: Container(
                  decoration: BoxDecoration(
                    gradient: RadialGradient(
                      colors: [
                        Colors.white.withAlpha(125),
                        Colors.black.withAlpha(170),
                      ],
                      radius: 0.7,
                    ),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10, horizontal: 15),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    FittedBox(
                      fit: BoxFit.contain,
                      child: Title(
                        color: Colors.white,
                        child: Text(
                          deck.name,
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                    Divider(),
                    Text(
                      'By ${deck.username}',
                      style: TextStyle(color: Colors.white),
                    ),
                    SizedBox(height: 20),
                    Text(
                      'Win Rate ${deck.getWinRates().toString()}',
                      style: TextStyle(color: Colors.white),
                    ),
                    Spacer(),
                    Row(
                      children: [
                        IconWithText(
                          text: deck.views.toString(),
                          icon: Icons.remove_red_eye_outlined,
                          tooltip: 'Views',
                          color: Colors.white,
                        ),
                        IconWithText(
                          text: deck.likes.toString(),
                          icon: Icons.heart_broken,
                          tooltip: 'Likes',
                          color: Colors.white,
                        ),
                        SizedBox(width: 10),
                        Flexible(
                          child: Text(
                            lastTimeModify(
                              deck.publishedOn,
                              deck.updatedOn ?? deck.publishedOn,
                            ),
                            style: TextStyle(color: Colors.white),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  BoxDecoration buildBoxDecoration(ImageProvider<Object> image) =>
      BoxDecoration(
        image: DecorationImage(
          image: image,
          // scale: 1.8,
          // fit: BoxFit.none,
          fit: BoxFit.fitWidth,
          alignment: Alignment(0.2, -0.7),
        ),
      );
}
