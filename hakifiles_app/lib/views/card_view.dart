import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/dialogs/index.dart';
import 'package:hakifiles_app/models/entities/card_info.dart';
import 'package:hakifiles_app/models/entities/character_card.dart';
import 'package:hakifiles_app/models/entities/event_stage_card.dart';
import 'package:hakifiles_app/models/entities/leader_card.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/tools/index.dart';
import 'package:hakifiles_app/views/index.dart';

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
    final CardsProvider cardsProvider = Provider.of<CardsProvider>(context);
    final AuthProvider authProvider = Provider.of<AuthProvider>(context);
    if (cardsProvider.isLoading) {
      return LoadingView();
    }
    if (cardsProvider.cardInfo == null) {
      return NoPageFoundView();
    }
    Widget imageWidget = getImageWidget(
      img: cardsProvider.cardInfo!.image,
      height: minCardImage.height,
      width: minCardImage.width,
    );

    return Container(
      margin: EdgeInsets.all(12),
      child: Column(
        children: <Widget>[
          Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Spacer(),
              Expanded(
                child: Container(
                  decoration: buildBoxDecoration(
                    cardsProvider.cardInfo!.colorCards,
                  ),
                  child: imageWidget,
                ),
              ),
              if (cardsProvider.characterCard != null)
                Expanded(child: _CharacterBody()),
              if (cardsProvider.leaderCard != null)
                Expanded(child: _LeaderBody()),
              if (cardsProvider.eventStageCard != null)
                Expanded(child: _EventStageBody()),
              SizedBox(width: 20),
              Spacer(),
            ],
          ),
          SizedBox(height: 10),
          if (authProvider.authStatus == AuthStatus.authenticated)
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                if (cardsProvider.cardInfo!.category != 'LEADER')
                  IconWithText(
                    text: 'Add card to deck',
                    icon: Icons.add,
                    onPressed:
                        () => NavigationService.showDialogInWeb(
                          AddCardDialog(card: cardsProvider.cardInfo!),
                        ),
                  ),
                if (cardsProvider.cardInfo!.category == 'LEADER')
                  IconWithText(
                    text: 'Create a deck with this leader',
                    icon: Icons.add,
                    onPressed:
                        () => NavigationService.showDialogInWeb(
                          CreateDeckDialog(
                            cardId: cardsProvider.cardInfo!.cardId,
                          ),
                        ),
                  ),
              ],
            ),
        ],
      ),
    );
  }

  BoxDecoration buildBoxDecoration(List<String> colors) =>
      BoxDecoration(gradient: getGradient(colors));
}

class _CharacterBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final CardsProvider cardsProvider = Provider.of<CardsProvider>(context);
    final CardInfo cardinfo = cardsProvider.cardInfo!;
    final CharacterCard characterCard = cardsProvider.characterCard!;
    return SelectionArea(
      child: Container(
        color: Colors.white,
        height: minCardImage.height,
        child: ListView(
          children: <Widget>[
            Divider(thickness: 10),
            Text('${characterCard.name} / ${cardinfo.cardId}'),
            Text(
              '${cardinfo.category} / ${cardinfo.colorCards} / ${characterCard.cost} Cost',
            ),
            Divider(),
            Text('${characterCard.power} Power / ${characterCard.attribute}'),
            if (characterCard.counterPower != 0)
              Text('+${characterCard.counterPower.toString()} Counter'),
            if (characterCard.effects.isNotEmpty) ...<Widget>[
              Divider(),
              Text(characterCard.effects),
            ],
            if (characterCard.triggerEffect.isNotEmpty) ...<Widget>[
              SizedBox(height: 10),
              Text(characterCard.triggerEffect),
            ],
            Divider(),
            Text('${characterCard.type}'),
            Divider(),
            Text('Block ${cardinfo.block}'),
            Text('Tournament Status ${cardinfo.tournamentStatus}'),
            Text('Times used in decks: ${cardinfo.cardUsage}'),
            Divider(),
            Text(cardinfo.product),
          ],
        ),
      ),
    );
  }
}

class _LeaderBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final CardsProvider cardsProvider = Provider.of<CardsProvider>(context);
    final CardInfo cardinfo = cardsProvider.cardInfo!;
    final LeaderCard leaderCard = cardsProvider.leaderCard!;
    return SelectionArea(
      child: Container(
        color: Colors.white,
        height: minCardImage.height,
        child: ListView(
          children: <Widget>[
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
      ),
    );
  }
}

class _EventStageBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final CardsProvider cardsProvider = Provider.of<CardsProvider>(context);
    final CardInfo cardinfo = cardsProvider.cardInfo!;
    final EventStageCard eventStageCard = cardsProvider.eventStageCard!;
    return SelectionArea(
      child: Container(
        color: Colors.white,
        height: minCardImage.height,
        child: ListView(
          children: <Widget>[
            Divider(thickness: 10),
            Text('${eventStageCard.name} / ${cardinfo.cardId}'),
            Text(
              '${cardinfo.category} / ${cardinfo.colorCards} / ${eventStageCard.cost} Cost',
            ),
            Divider(),
            Text(eventStageCard.effects),
            if (eventStageCard.triggerEffect.isNotEmpty) ...[
              SizedBox(height: 10),
              Text(eventStageCard.triggerEffect),
            ],
            Divider(),
            Text('${eventStageCard.type}'),
            Divider(),
            Text('Block ${cardinfo.block}'),
            Text('Tournament Status ${cardinfo.tournamentStatus}'),
            Text('Times used in decks: ${cardinfo.cardUsage}'),
            Divider(),
            Text(cardinfo.product),
          ],
        ),
      ),
    );
  }
}
