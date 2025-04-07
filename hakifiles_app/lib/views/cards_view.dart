import 'package:flutter/material.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:provider/provider.dart';

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
    Provider.of<CardInfoProvider>(context, listen: false).getCardsInfo(product);
  }

  @override
  Widget build(BuildContext context) {
    final cards = Provider.of<CardInfoProvider>(context).cardsInfo;

    return Container(color: Colors.orange);
  }
}
