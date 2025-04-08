import 'package:flutter/material.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/tools/index.dart';
import 'package:hakifiles_app/views/index.dart';
import 'package:provider/provider.dart';

class CardView extends StatefulWidget {
  const CardView({super.key, required this.cardId});

  final String cardId;

  @override
  State<CardView> createState() => _CardViewState();
}

class _CardViewState extends State<CardView> {
  @override
  void initState() {
    super.initState();
    Provider.of<CardsProvider>(context, listen: false).getCard(widget.cardId);
  }

  @override
  Widget build(BuildContext context) {
    final cardsProvider = Provider.of<CardsProvider>(context);
    if (cardsProvider.isLoading) {
      return LoadingView();
    }
    if (cardsProvider.cardInfo == null) {
      return NoPageFoundView();
    }
    var imageWidget = getImageWidget(
      img: cardsProvider.cardInfo!.image,
      height: minCardImage.height,
      width: minCardImage.width,
    );

    return Container(
      margin: EdgeInsets.all(12),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Spacer(),
          Expanded(child: Container(color: Colors.green, child: imageWidget)),
          if (cardsProvider.characterCard != null)
            Expanded(child: _CharacterBody()),
          if (cardsProvider.leaderCard != null) Expanded(child: _LeaderBody()),
          if (cardsProvider.eventStageCard != null)
            Expanded(child: _EventStageBody()),
          Spacer(),
        ],
      ),
    );
  }
}

class _CharacterBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container();
  }
}

class _LeaderBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final cardsProvider = Provider.of<CardsProvider>(context);
    final cardinfo = cardsProvider.cardInfo!;
    final leaderCard = cardsProvider.leaderCard!;
    return Container(
      color: Colors.white,
      height: minCardImage.height,
      // padding: EdgeInsets.all(10),
      child: ListView(
        // mainAxisAlignment: MainAxisAlignment.center,
        // crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Divider(thickness: 10),
          Text('${leaderCard.name} / ${cardinfo.cardId}'),
          Text(
            '${cardinfo.category} / ${cardinfo.colorCards} / ${leaderCard.life} Life',
          ),
          Divider(),
          Text('${leaderCard.power} Power / ${leaderCard.attribute}'),
          Divider(),
          Text(leaderCard.effects),
          Divider(),
          Text('${leaderCard.type}'),
          Divider(),
          Text('Block ${cardinfo.block}'),
          Text('Tournament Status ${cardinfo.tournamentStatus}'),
          Text('Times used in decks: ${cardinfo.cardUsage}'),
          Divider(),
          Text(cardinfo.product),
        ],
      ),
    );
  }
}

class _EventStageBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container();
  }
}
