import 'dart:async';

import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/cards/index.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/tools/index.dart';

class CreateDeckDialog extends StatefulWidget {
  const CreateDeckDialog({super.key});

  @override
  State<CreateDeckDialog> createState() => _CreateDeckDialogState();
}

class _CreateDeckDialogState extends State<CreateDeckDialog> {
  List<CardInfo> cards = <CardInfo>[];
  Timer? _debounce;
  final ScrollController _scrollController = ScrollController();
  final TextEditingController _leaderTextController = TextEditingController();

  @override
  void dispose() {
    _debounce?.cancel();
    _scrollController.dispose();
    _leaderTextController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<CreateDeckFormProvider>(
      create: (BuildContext context) => CreateDeckFormProvider(),
      child: Builder(
        builder: (BuildContext context) {
          final Size size = MediaQuery.of(context).size;
          final CreateDeckFormProvider createDeckFormProvider =
              Provider.of<CreateDeckFormProvider>(context);
          return SizedBox(
            width: size.width * 0.5,
            child: Column(
              children: <Widget>[
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Spacer(),
                    Text('Create new deck'),
                    Spacer(),
                    IconButton(
                      onPressed: () => NavigationService.pop(),
                      icon: Icon(Icons.close),
                    ),
                    SizedBox(width: 5),
                  ],
                ),
                Form(
                  key: createDeckFormProvider.formKey,
                  child: Column(
                    children: <Widget>[
                      TextFormField(
                        onChanged:
                            (String value) =>
                                createDeckFormProvider.name = value,
                        validator: (String? value) {
                          if (value == null || value.isEmpty) {
                            return 'Enter the name of the deck';
                          }
                          if (value.length < 4) {
                            return 'The name must contain 4 letters';
                          }
                          return null;
                        },
                        decoration: CustomInputs.authInputDecoration(
                          hint: 'Name of the deck',
                          label: 'Deck name',
                          icon: Icons.people,
                        ),
                      ),
                      SizedBox(height: 20),
                      TextFormField(
                        onChanged:
                            (String value) =>
                                createDeckFormProvider.description = value,
                        validator: (String? value) {
                          if (value != null) {
                            if (value.length > 2500) {
                              return 'The description can be higher than 2500';
                            }
                          }
                          return null;
                        },
                        decoration: CustomInputs.authInputDecoration(
                          hint: 'Description of the deck',
                          label: 'Description',
                          icon: Icons.description,
                        ),
                      ),
                      SizedBox(height: 20),
                      TextFormField(
                        onChanged:
                            (String value) =>
                                createDeckFormProvider.youtubeLink = value,
                        validator: (String? value) {
                          return null;
                        },
                        decoration: CustomInputs.authInputDecoration(
                          hint: 'Gameplay of the deck',
                          label: 'Youtube link',
                          icon: Icons.video_collection,
                        ),
                      ),
                      SizedBox(height: 20),
                      TextFormField(
                        enabled: false,
                        controller: _leaderTextController,
                        validator: (String? value) {
                          if (value == null || value.isEmpty) {
                            return 'Select a leader';
                          }
                          createDeckFormProvider.leader = value;
                          return null;
                        },
                        decoration: CustomInputs.authInputDecoration(
                          hint: '',
                          label: 'Leader ID',
                          icon: Icons.waving_hand_rounded,
                        ),
                      ),
                      SearchBox(
                        hint: 'Pick a leader',
                        onChanged:
                            (String value) => _onSearchChange(context, value),
                      ),
                      SizedBox(height: 20),

                      if (cards.isNotEmpty) ...<Widget>[
                        Container(
                          height: 300,
                          margin: EdgeInsets.symmetric(horizontal: 5),
                          child: Scrollbar(
                            thumbVisibility: true,
                            scrollbarOrientation: ScrollbarOrientation.bottom,
                            controller: _scrollController,
                            child: ListView.builder(
                              controller: _scrollController,
                              itemCount: cards.length,
                              scrollDirection: Axis.horizontal,
                              itemBuilder: (BuildContext context, int index) {
                                final CardInfo card = cards[index];
                                return Container(
                                  padding: EdgeInsets.symmetric(
                                    horizontal: 5,
                                    vertical: 15,
                                  ),
                                  child: CardImageNavigator(
                                    onPressed: () {
                                      _leaderTextController.text = card.cardId;
                                    },
                                    img: card.image,
                                  ),
                                );
                              },
                            ),
                          ),
                        ),
                        SizedBox(height: 20),
                      ],
                      Row(
                        children: <Widget>[
                          Text('The deck is private: '),
                          Switch(
                            value: createDeckFormProvider.isPrivate,
                            onChanged:
                                (bool value) => setState(() {
                                  createDeckFormProvider.isPrivate = value;
                                }),
                          ),
                        ],
                      ),
                      SizedBox(height: 20),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[
                          OutlinedButton(
                            onPressed: () => NavigationService.pop(),
                            child: Text('Cancel'),
                          ),
                          SizedBox(width: 30),
                          OutlinedButton(
                            onPressed: () {
                              final bool validForm =
                                  createDeckFormProvider.validateForm();
                              if (!validForm) return;
                              final int userId =
                                  Provider.of<AuthProvider>(
                                    context,
                                    listen: false,
                                  ).user!.userId;
                              final CreateDeckDto dto = CreateDeckDto(
                                name: createDeckFormProvider.name,
                                description: createDeckFormProvider.description,
                                youtubeLink: createDeckFormProvider.youtubeLink,
                                leader: createDeckFormProvider.leader,
                                userId: userId,
                                isPrivate: createDeckFormProvider.isPrivate,
                              );
                              Provider.of<SingleDeckProvider>(
                                context,
                                listen: false,
                              ).createDeck(dto);
                            },
                            child: Text('Create'),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }

  _onSearchChange(BuildContext context, String query) {
    if (_debounce?.isActive ?? false) _debounce?.cancel();
    _debounce = Timer(Duration(milliseconds: 500), () async {
      if (query.isNotEmpty) {
        final List<CardInfo> newCards = await Provider.of<CardsProvider>(
          context,
          listen: false,
        ).getLeadersByName(query);
        setState(() {
          cards = newCards;
        });
      }
    });
  }
}
