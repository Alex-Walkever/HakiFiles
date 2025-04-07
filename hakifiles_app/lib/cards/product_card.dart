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
    this.width,
    this.onPressed,
  });

  final String name;
  final String code;
  final String releaseDate;
  final int amountOfCards;
  final int block;
  final String? img;
  final double? width;
  final Function? onPressed;

  @override
  Widget build(BuildContext context) {
    double imgSize = 120;
    final image =
        (img == null)
            ? Image(
              image: AssetImage('images/no-image.jpg'),
              width: imgSize,
              height: imgSize,
            )
            : FadeInImage.assetNetwork(
              placeholder: 'loader.gif',
              image: img!,
              width: imgSize,
              height: imgSize,
            );
    return InkWell(
      onTap: () => onPressed != null ? onPressed!() : null,
      child: MouseRegion(
        cursor: SystemMouseCursors.click,
        child: Container(
          margin: EdgeInsets.symmetric(vertical: 4),
          padding: EdgeInsets.all(8),
          width: width,
          decoration: buildBoxDecoration(),
          child: Row(
            children: [
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 5),
                child: image,
              ),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text('$name *$code*'),
                    Divider(),
                    Text("Released: $releaseDate"),
                    Divider(),
                    Text(
                      "Cards: ${amountOfCards.toString()}      Block: ${block.toString()}",
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  BoxDecoration buildBoxDecoration() => BoxDecoration(
    color: Colors.white,
    borderRadius: BorderRadius.circular(5),
    boxShadow: [BoxShadow(color: Colors.black.withAlpha(13), blurRadius: 5)],
  );
}
