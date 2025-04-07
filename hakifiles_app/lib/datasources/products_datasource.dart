import 'package:flutter/material.dart';
import 'package:hakifiles_app/models/index.dart';

class ProductsDatasource extends DataTableSource {
  final List<Product> products;

  ProductsDatasource({required this.products});

  @override
  DataRow? getRow(int index) {
    final product = products[index];
    return DataRow.byIndex(
      index: index,
      cells: [
        DataCell(Text(product.name)),
        DataCell(Text(product.code)),
        DataCell(Text(product.releaseDate)),
        DataCell(Text(product.amountOfCards.toString())),
        DataCell(Text(product.block.toString())),
      ],
    );
  }

  @override
  bool get isRowCountApproximate => false;

  @override
  int get rowCount => products.length;

  @override
  int get selectedRowCount => 0;
}
