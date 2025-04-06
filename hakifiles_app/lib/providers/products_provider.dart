import 'package:flutter/material.dart';
import 'package:hakifiles_app/api/hakifiles_api.dart';
import 'package:hakifiles_app/models/index.dart';

class ProductsProvider extends ChangeNotifier {
  List<Product> products = [];
  bool isLoading = true;

  ProductsProvider() {
    getProducts();
  }

  getProducts() async {
    final response = await HakifilesApi.httpGet('/product');

    final productsResponse = ProductResponse.fromJson(response);

    products = [...productsResponse.products];
    isLoading = false;

    notifyListeners();
  }
}
