import 'dart:async';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
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
    if (SingleDeckProvider.currentDeck == null ||
        SingleDeckProvider.currentDeck!.id != widget.deckId) {
      Provider.of<SingleDeckProvider>(
        context,
        listen: false,
      ).deckDetails(widget.deckId);
    }
  }

  @override
  Widget build(BuildContext context) {
    Provider.of<SingleDeckProvider>(context, listen: true);
    if (SingleDeckProvider.currentDeck == null ||
        SingleDeckProvider.user == null) {
      return NoPageFoundView();
    }
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
      child: Column(
        children: <Widget>[
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
  String image = SingleDeckProvider.currentDeck!.leader.image;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final Map<CardInfoCategory, int> characters =
        SingleDeckProvider.characterList;
    final Map<CardInfoCategory, int> events = SingleDeckProvider.eventList;
    final Map<CardInfoCategory, int> stages = SingleDeckProvider.stageList;
    final Map<CardInfoCategory, int> leaders = SingleDeckProvider.leaderList;

    final Size size = MediaQuery.of(context).size;
    final Widget imageWidget = getImageWidget(
      img: image,
      width: 400,
      height: 600,
    );

    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        if (size.width > 1000) ...<Widget>[
          SizedBox(width: 400, child: imageWidget),
          SizedBox(width: 20),
        ],
        Expanded(
          child: ListView(
            physics: ClampingScrollPhysics(),
            children: <Widget>[
              _CustomGridView(
                title: 'LEADERS',
                list: leaders,
                width: size.width,
                onEnter: _setImage,
              ),
              if (characters.isNotEmpty) ...<Widget>[
                SizedBox(height: 10),
                _CustomGridView(
                  title: 'CHARACTERS',
                  list: characters,
                  width: size.width,
                  onEnter: _setImage,
                ),
              ],

              if (events.isNotEmpty) ...<Widget>[
                SizedBox(height: 10),
                _CustomGridView(
                  title: 'EVENTS',
                  list: events,
                  width: size.width,
                  onEnter: _setImage,
                ),
              ],

              if (stages.isNotEmpty) ...<Widget>[
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
  final Map<CardInfoCategory, int> list;
  final double width;
  final Function(String)? onEnter;

  @override
  Widget build(BuildContext context) {
    final int axisCounts = max((width / minCardImage.width).toInt(), 1);

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
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
            itemBuilder: (BuildContext context, int index) {
              final CardInfoCategory card = list.keys.elementAt(index);

              return MouseRegion(
                onEnter: (PointerEnterEvent event) {
                  if (onEnter != null) {
                    onEnter!(card.cardInfo.image);
                  }
                },
                child: Stack(
                  children: <Widget>[
                    getImageWidget(img: card.cardInfo.image),
                    if (card.cardInfo.category != 'LEADER') ...<Widget>[
                      Align(
                        alignment: Alignment(0.9, -0.8),
                        child: IconButton(
                          onPressed: () {
                            Provider.of<SingleDeckProvider>(
                              context,
                              listen: false,
                            ).addCardToDeck(card, 1);
                          },
                          icon: Icon(Icons.plus_one),
                          style: ButtonStyle(
                            backgroundColor: WidgetStatePropertyAll<Color>(
                              Colors.white,
                            ),
                            fixedSize: WidgetStatePropertyAll<Size>(
                              Size(50, 50),
                            ),
                          ),
                        ),
                      ),
                      Align(
                        alignment: Alignment(0.9, -0.2),
                        child: IconButton(
                          onPressed: () {
                            Provider.of<SingleDeckProvider>(
                              context,
                              listen: false,
                            ).addCardToDeck(card, -1);
                          },
                          icon: Icon(Icons.exposure_minus_1),
                          style: ButtonStyle(
                            backgroundColor: WidgetStatePropertyAll<Color>(
                              Colors.white,
                            ),
                            fixedSize: WidgetStatePropertyAll<Size>(
                              Size(50, 50),
                            ),
                          ),
                        ),
                      ),
                    ],
                  ],
                ),
              );
            },
          ),
        ),
      ],
    );
  }
}

class _GeneralTools extends StatefulWidget {
  const _GeneralTools();

  @override
  State<_GeneralTools> createState() => _GeneralToolsState();
}

class _GeneralToolsState extends State<_GeneralTools> {
  Timer? _debounce;
  final FocusNode _focusNode = FocusNode();
  final OverlayPortalController _tooltipController = OverlayPortalController();
  List<CardInfoCategory> cards = <CardInfoCategory>[];
  final GlobalKey _key = GlobalKey();
  RenderBox? renderBox;
  Offset position = Offset.zero;
  bool isFocused = false;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _getWidgetPosition();
    });
  }

  @override
  void dispose() {
    _debounce?.cancel();
    _focusNode.dispose();
    super.dispose();
  }

  void _getWidgetPosition() {
    renderBox = _key.currentContext?.findRenderObject() as RenderBox?;
    if (renderBox != null) {
      position = renderBox!.localToGlobal(Offset.zero);
    }
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    final Deck deck = SingleDeckProvider.currentDeck!;
    return Row(
      children: <Widget>[
        LinkText(text: 'Export'),
        LinkText(text: 'Import'),
        LinkText(text: 'Add game'),
        if (deck.youtubeLink != null && deck.youtubeLink!.isNotEmpty)
          LinkText(text: 'Youtube'),
        LinkText(text: 'Get proxies'),
        Spacer(),
        Focus(
          autofocus: false,
          onFocusChange: (bool value) {
            setState(() {
              if (!isFocused) {
                if (value) {
                  _tooltipController.show();
                } else {
                  _tooltipController.hide();
                }
              }
            });
          },
          child: Column(
            children: <Widget>[
              SearchBox(
                width: 200,
                hint: 'Find and add cards...',
                onChanged: (String value) => _onSearchChange(context, value),
                key: _key,
              ),
            ],
          ),
        ),
        if (cards.isNotEmpty)
          OverlayPortal(
            controller: _tooltipController,
            overlayChildBuilder: (BuildContext context) {
              final ColorScheme colors = Theme.of(context).colorScheme;
              return Positioned(
                top: position.dy - 40,
                left: position.dx + 40,
                child: Container(
                  color: colors.surface,

                  width: 170,
                  height: 400,
                  child: ListView.builder(
                    physics: ClampingScrollPhysics(),
                    itemCount: cards.length,
                    itemBuilder: (BuildContext context, int index) {
                      final CardInfoCategory card = cards[index];
                      final String name = getNameFromCategory(card);
                      final int cost = getCostFromCategory(card);
                      return GestureDetector(
                        onTap: () => _addCard(context, card),
                        child: MouseRegion(
                          onEnter: (PointerEnterEvent event) {
                            setState(() {
                              isFocused = true;
                            });
                          },
                          onExit: (PointerExitEvent event) {
                            setState(() {
                              isFocused = false;
                            });
                          },
                          cursor: SystemMouseCursors.click,
                          child: Container(
                            decoration: BoxDecoration(
                              color: colors.surface,
                              border: Border.all(color: colors.primary),
                              borderRadius: BorderRadius.all(
                                Radius.circular(5),
                              ),
                            ),
                            child: Text(
                              '$name - Cost $cost [${card.cardInfo.cardId}]',
                            ),
                          ),
                        ),
                      );
                    },
                  ),
                ),
              );
            },
          ),
        SizedBox(width: 50),
      ],
    );
  }

  _addCard(BuildContext context, CardInfoCategory cardInfoCategory) {
    Provider.of<SingleDeckProvider>(
      context,
      listen: false,
    ).addCardToDeck(cardInfoCategory, 1);
    setState(() {
      isFocused = false;
      _tooltipController.hide();
    });
  }

  _onSearchChange(BuildContext context, String query) {
    if (_debounce?.isActive ?? false) _debounce?.cancel();
    _debounce = Timer(Duration(milliseconds: 500), () async {
      if (query.isNotEmpty) {
        Map<String, String> finalQuery = <String, String>{};
        finalQuery['name'] = query;
        finalQuery['category'] = 'STAGE,CHARACTER,EVENT';
        finalQuery['color'] = cleanUpList(
          SingleDeckProvider.leaderList.keys.first.cardInfo.colorCards,
        );
        finalQuery['status'] = 'LEGAL';
        final List<CardInfoCategory> newCards =
            await Provider.of<CardsProvider>(
              context,
              listen: false,
            ).getCardsByDynamicSearch(finalQuery);
        setState(() {
          cards = newCards;
          _tooltipController.show();
          isFocused = true;
        });
      }
    });
  }
}

class _DeckDescription extends StatelessWidget {
  const _DeckDescription();

  @override
  Widget build(BuildContext context) {
    final User user = SingleDeckProvider.user!;
    final Deck deck = SingleDeckProvider.currentDeck!;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text(user.name),
        SizedBox(height: 10),
        Text(deck.name),
        if (deck.description != null &&
            deck.description!.isNotEmpty) ...<Widget>[
          SizedBox(height: 10),
          Text(deck.description!),
        ],
        Row(
          children: <Widget>[
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
              onPressed: () {},
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
