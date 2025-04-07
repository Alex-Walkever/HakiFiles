// To parse this JSON data, do
//
//     final products = productsFromJson(jsonString);

import 'dart:convert';

List<Product> productsFromJson(String str) =>
    List<Product>.from(json.decode(str).map((x) => Product.fromJson(x)));

String productsToJson(List<Product> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class Product {
  int id;
  String code;
  String name;
  String releaseDate;
  int amountOfCards;
  int block;
  String? img;

  Product({
    required this.id,
    required this.code,
    required this.name,
    required this.releaseDate,
    required this.amountOfCards,
    required this.block,
    this.img,
  });

  factory Product.fromJson(Map<String, dynamic> json) => Product(
    id: json["id"],
    code: json["code"],
    name: json["name"],
    releaseDate: json["releaseDate"],
    amountOfCards: json["amountOfCards"],
    block: json["block"],
    img: json["img"],
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "code": code,
    "name": name,
    "releaseDate": releaseDate,
    "amountOfCards": amountOfCards,
    "block": block,
    "img": img,
  };
}
