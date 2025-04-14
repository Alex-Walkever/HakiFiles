import 'dart:math';

import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/cards/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/router/index.dart';
import 'package:hakifiles_app/tools/hakifiles_variables.dart';
import 'package:hakifiles_app/views/index.dart';

class CardsView extends StatefulWidget {
  const CardsView({super.key, required this.product});

  final String product;

  @override
  State<CardsView> createState() => _CardsViewState();
}

class _CardsViewState extends State<CardsView> {
  @override
  void initState() {
    super.initState();
    final product = widget.product.replaceAll('-', ' ');
    Provider.of<CardsProvider>(context, listen: false).getCardsInfo(product);
  }

  @override
  Widget build(BuildContext context) {
    final cards = Provider.of<CardsProvider>(context).cardsInfo;
    if (cards.isEmpty) {
      return NoPageFoundView();
    }
    final size = MediaQuery.of(context).size;

    final axisCounts = max(
      ((size.width * 0.9) / minCardImage.width).toInt(),
      1,
    );

    return Row(
      children: [
        SizedBox(width: size.width > 388 ? size.width * 0.05 : null),
        Expanded(
          child: GridView.count(
            physics: ClampingScrollPhysics(),
            crossAxisCount: axisCounts,
            crossAxisSpacing: 10,
            mainAxisSpacing: 10,
            padding: EdgeInsets.all(10),
            childAspectRatio: minCardImage.aspectRatio,
            children: List.generate(cards.length, (index) {
              final card = cards[index];
              return CardImageNavigator(
                img: card.image,
                onPressed:
                    () => NavigationService.navigateTo(
                      '${HakiRouter.cardRoute}/${card.cardId}',
                    ),
              );
            }),
          ),
        ),
        SizedBox(width: size.width > 388 ? size.width * 0.05 : null),
      ],
    );
  }
}
