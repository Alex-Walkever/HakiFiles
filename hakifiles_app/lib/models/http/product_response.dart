import 'package:hakifiles_app/models/index.dart';

class ProductResponse {
  final List<Product> products;

  ProductResponse({required this.products});

  factory ProductResponse.fromJson(List<dynamic> json) => ProductResponse(
    products:
        List<Product>.from(
          json.map((e) {
            return Product.fromJson(e);
          }),
        ).toList(),
  );
}
