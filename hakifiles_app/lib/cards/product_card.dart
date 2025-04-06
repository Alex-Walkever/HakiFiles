import 'package:flutter/material.dart';

class ProductCard extends StatelessWidget {
  const ProductCard({
    super.key,
    required this.name,
    required this.code,
    required this.releaseDate,
    required this.amountOfCards,
    required this.block,
    this.img,
  });

  final String name;
  final String code;
  final String releaseDate;
  final int amountOfCards;
  final int block;
  final String? img;

  @override
  Widget build(BuildContext context) {
    final image =
        (img == null)
            ? Image(
              image: AssetImage('images/no-image.jpg'),
              width: 247,
              height: 247,
            )
            : FadeInImage.assetNetwork(
              placeholder: 'loader.gif',
              image: img!,
              width: 247,
              height: 247,
            );
    return Container(
      // color: Colors.indigo,
      width: 400,
      margin: EdgeInsets.all(8),
      padding: EdgeInsets.all(8),
      decoration: buildBoxDecoration(),
      child: Row(
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: image,
          ),
          SizedBox(width: 30),
          Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(name),
              Divider(),
              Text(code),
              Divider(),
              Text("Release Date: $releaseDate"),
              Divider(),
              Text("Cards: ${amountOfCards.toString()}"),
              Divider(),
              Text("Block: ${block.toString()}"),
            ],
          ),
        ],
      ),
    );
  }

  BoxDecoration buildBoxDecoration() => BoxDecoration(
    color: Colors.white,
    borderRadius: BorderRadius.circular(5),
    boxShadow: [BoxShadow(color: Colors.black.withAlpha(13), blurRadius: 5)],
  );
}
