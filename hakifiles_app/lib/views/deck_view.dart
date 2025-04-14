import 'dart:math';

import 'package:flutter/material.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/tools/index.dart';
import 'package:hakifiles_app/views/index.dart';

class DeckView extends StatefulWidget {
  const DeckView({super.key, required this.deckId});

  final String deckId;

  @override
  State<DeckView> createState() => _DeckViewState();
}

class _DeckViewState extends State<DeckView> {
  @override
  void initState() {
    super.initState();
    if (DecksProvider.currentDeck == null ||
        DecksProvider.currentDeck!.id != widget.deckId) {
      Provider.of<DecksProvider>(
        context,
        listen: false,
      ).deckDetails(widget.deckId);
    }
  }

  @override
  Widget build(BuildContext context) {
    Provider.of<DecksProvider>(context, listen: true);
    if (DecksProvider.currentDeck == null || DecksProvider.user == null) {
      return NoPageFoundView();
    }
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
      child: Column(
        children: [
          _DeckDescription(),
          SizedBox(height: 20),
          _GeneralTools(),
          // SizedBox(height: 20),
          Expanded(child: _DeckViewBody()),
        ],
      ),
    );
  }
}

class _DeckViewBody extends StatefulWidget {
  const _DeckViewBody();

  @override
  State<_DeckViewBody> createState() => _DeckViewBodyState();
}

class _DeckViewBodyState extends State<_DeckViewBody> {
  String image = DecksProvider.currentDeck!.leader.image;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final characters = DecksProvider.characterList;
    final events = DecksProvider.eventList;
    final stages = DecksProvider.stageList;
    final leaders = DecksProvider.leaderList;

    final size = MediaQuery.of(context).size;
    final imageWidget = getImageWidget(img: image, width: 400, height: 600);

    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        if (size.width > 1000) ...[
          SizedBox(width: 400, child: imageWidget),
          SizedBox(width: 20),
        ],
        Expanded(
          child: ListView(
            children: [
              _CustomGridView(
                title: 'LEADERS',
                list: leaders,
                width: size.width,
                onEnter: _setImage,
              ),
              if (characters.isNotEmpty) ...[
                SizedBox(height: 10),
                _CustomGridView(
                  title: 'CHARACTERS',
                  list: characters,
                  width: size.width,
                  onEnter: _setImage,
                ),
              ],

              if (events.isNotEmpty) ...[
                SizedBox(height: 10),
                _CustomGridView(
                  title: 'EVENTS',
                  list: events,
                  width: size.width,
                  onEnter: _setImage,
                ),
              ],

              if (stages.isNotEmpty) ...[
                SizedBox(height: 10),
                _CustomGridView(
                  title: 'STAGES',
                  list: stages,
                  width: size.width,
                  onEnter: _setImage,
                ),
              ],
            ],
          ),
        ),
      ],
    );
  }

  _setImage(String img) {
    setState(() {
      image = img;
    });
  }
}

class _CustomGridView extends StatelessWidget {
  const _CustomGridView({
    required this.title,
    required this.list,
    required this.width,
    this.onEnter,
  });

  final String title;
  final List<CardInfoCategory> list;
  final double width;
  final Function(String)? onEnter;

  @override
  Widget build(BuildContext context) {
    final axisCounts = max((width / minCardImage.width).toInt(), 1);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(title),
        Divider(),
        SizedBox(height: 10),
        SizedBox(
          child: GridView.builder(
            physics: NeverScrollableScrollPhysics(),
            shrinkWrap: true,
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: axisCounts,
              childAspectRatio: minCardImage.aspectRatio,
              crossAxisSpacing: 10,
              mainAxisSpacing: 10,
            ),
            itemCount: list.length,
            itemBuilder: (context, index) {
              final card = list[index];

              return MouseRegion(
                onEnter: (event) {
                  if (onEnter != null) {
                    onEnter!(card.cardInfo.image);
                  }
                },
                child: getImageWidget(img: card.cardInfo.image),
              );
            },
          ),
        ),
      ],
    );
  }
}

class _GeneralTools extends StatelessWidget {
  const _GeneralTools();

  @override
  Widget build(BuildContext context) {
    final deck = DecksProvider.currentDeck!;
    return Row(
      children: [
        LinkText(text: 'Export'),
        LinkText(text: 'Import'),
        LinkText(text: 'Add game'),
        if (deck.youtubeLink != null && deck.youtubeLink!.isNotEmpty)
          LinkText(text: 'Youtube'),
        LinkText(text: 'Get proxies'),
      ],
    );
  }
}

class _DeckDescription extends StatelessWidget {
  const _DeckDescription();

  @override
  Widget build(BuildContext context) {
    final user = DecksProvider.user!;
    final deck = DecksProvider.currentDeck!;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(user.name),
        SizedBox(height: 10),
        Text(deck.name),
        if (deck.description != null && deck.description!.isNotEmpty) ...[
          SizedBox(height: 10),
          Text(deck.description!),
        ],
        Row(
          children: [
            IconWithText(
              text: deck.views.toString(),
              icon: Icons.remove_red_eye_outlined,
              tooltip: 'Views',
            ),
            SizedBox(width: 10),
            IconWithText(
              text: deck.likes.toString(),
              icon: Icons.heart_broken,
              tooltip: 'Likes',
              onPressed: () {
                print('press like');
              },
            ),
            SizedBox(width: 20),
            Text(
              lastTimeModify(
                deck.publishedOn,
                deck.updatedOn ?? deck.publishedOn,
              ),
            ),
          ],
        ),
      ],
    );
  }
}
