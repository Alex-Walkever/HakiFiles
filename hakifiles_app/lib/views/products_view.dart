import 'package:flutter/material.dart';
import 'package:hakifiles_app/cards/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:provider/provider.dart';

class ProductsView extends StatefulWidget {
  const ProductsView({super.key});

  @override
  State<ProductsView> createState() => _ProductsViewState();
}

class _ProductsViewState extends State<ProductsView> {
  @override
  void initState() {
    super.initState();
    Provider.of<ProductsProvider>(context, listen: false).getProducts();
  }

  @override
  Widget build(BuildContext context) {
    final products = Provider.of<ProductsProvider>(context).products;
    return Container(
      height: double.infinity,
      width: 400,
      constraints: BoxConstraints(maxWidth: 400),
      child: ListView(
        physics: ClampingScrollPhysics(),
        children: List.generate(products.length, (index) {
          final product = products[index];
          return ProductCard(
            name: product.name,
            code: product.code,
            releaseDate: product.releaseDate,
            amountOfCards: product.amountOfCards,
            block: product.block,
            img: product.img,
          );
        }),
      ),
    );
  }
}
