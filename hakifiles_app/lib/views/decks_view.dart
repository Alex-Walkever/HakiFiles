import 'dart:async';

import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/cards/index.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/router/index.dart';

class DecksView extends StatefulWidget {
  const DecksView({super.key});

  @override
  State<DecksView> createState() => _DecksViewState();
}

class _DecksViewState extends State<DecksView> {
  Timer? _debounce;

  @override
  void initState() {
    super.initState();
    _onInitwaitForAuth(context);
  }

  _onInitwaitForAuth(BuildContext context) {
    if (_debounce?.isActive ?? false) _debounce?.cancel();
    _debounce = Timer(Duration(milliseconds: 500), () async {
      Provider.of<DecksProvider>(
        context,
        listen: false,
      ).getDecks(Provider.of<AuthProvider>(context, listen: false).user);
    });
  }

  @override
  Widget build(BuildContext context) {
    final decksProvider = Provider.of<DecksProvider>(context);
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 30, vertical: 20),
      child: Column(
        children: [
          Expanded(
            child: ListView(
              children: [
                if (decksProvider.userDecks.isNotEmpty)
                  _SectionView(
                    title: 'Owns decks',
                    decks: decksProvider.userDecks,
                  ),
                _SectionView(title: 'Last created'),
                _SectionView(title: 'Most liked'),
                _SectionView(title: 'Most views'),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class _SectionView extends StatefulWidget {
  const _SectionView({required this.title, this.decks});

  final String title;
  final List<Deck>? decks;

  @override
  State<_SectionView> createState() => _SectionViewState();
}

class _SectionViewState extends State<_SectionView> {
  final ScrollController _scrollController = ScrollController();

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;
    return SizedBox(
      height: 300,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Title(color: colors.primary, child: Text(widget.title)),
          Divider(),
          SizedBox(height: 20),
          if (widget.decks != null)
            Expanded(
              child: Scrollbar(
                controller: _scrollController,
                thumbVisibility: true,
                scrollbarOrientation: ScrollbarOrientation.bottom,
                child: ListView.builder(
                  controller: _scrollController,
                  scrollDirection: Axis.horizontal,
                  itemCount: widget.decks!.length,
                  itemBuilder: (context, index) {
                    Deck deck = widget.decks![index];
                    return DeckCard(
                      deck: deck,
                      width: 300,
                      onPressed:
                          () => NavigationService.navigateTo(
                            '${HakiRouter.decksRoute}/${deck.id}',
                          ),
                    );
                  },
                ),
              ),
            ),
          SizedBox(height: 20),
        ],
      ),
    );
  }
}
