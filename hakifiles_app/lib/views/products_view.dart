import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/cards/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/router/index.dart';
import 'package:hakifiles_app/variables/hakifiles_variables.dart';
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
    final size = MediaQuery.of(context).size;
    final axisCounts = (size.width / minSizeWidget.width).toInt();
    return GridView.count(
      crossAxisCount: axisCounts,
      childAspectRatio: 1 / .4,
      physics: ClampingScrollPhysics(),
      children: List.generate(products.length, (index) {
        final product = products[index];
        final productName = product.name.replaceAll(' ', '-');
        return Row(
          children: [
            ProductCard(
              name: product.name,
              code: product.code,
              releaseDate: product.releaseDate,
              amountOfCards: product.amountOfCards,
              block: product.block,
              img: product.img,
              width: minSizeWidget.width,
              onPressed:
                  () => NavigationService.navigateTo(
                    '${HakiRouter.cardsRoute}/$productName',
                  ),
            ),
          ],
        );
      }),
    );
  }
}
