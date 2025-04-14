import 'package:flutter/material.dart';
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
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(DecksProvider.user?.name ?? ''),
              if (DecksProvider.currentDeck?.description != null &&
                  DecksProvider.currentDeck!.description!.isNotEmpty)
                Text(DecksProvider.currentDeck!.description!),
              Row(
                children: [
                  IconWithText(
                    text: DecksProvider.currentDeck!.views.toString(),
                    icon: Icons.remove_red_eye_outlined,
                  ),
                  SizedBox(width: 10),
                  IconWithText(
                    text: DecksProvider.currentDeck!.likes.toString(),
                    icon: Icons.heart_broken,
                  ),
                ],
              ),
            ],
          ),
        ],
      ),
    );
  }
}
