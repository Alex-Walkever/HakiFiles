import 'package:flutter/material.dart';
import 'package:hakifiles_app/datasources/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:provider/provider.dart';

class CardsView extends StatefulWidget {
  const CardsView({super.key});

  @override
  State<CardsView> createState() => _CardsViewState();
}

class _CardsViewState extends State<CardsView> {
  @override
  void initState() {
    super.initState();
    Provider.of<ProductsProvider>(context, listen: false).getProducts();
  }

  @override
  Widget build(BuildContext context) {
    final productsProvider = Provider.of<ProductsProvider>(context);
    final productDatasoure = ProductsDatasource(
      products: productsProvider.products,
    );
    return ListView(
      children: [
        PaginatedDataTable(
          columns: [
            DataColumn(label: Text('Name')),
            DataColumn(label: Text('Code')),
            DataColumn(label: Text('Release Date')),
            DataColumn(label: Text('Total of cards')),
            DataColumn(label: Text('Block')),
          ],
          source: productDatasoure,
        ),
      ],
    );
  }
}
