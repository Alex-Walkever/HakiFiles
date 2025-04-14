import 'dart:async';

import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/cards/index.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/tools/index.dart';

class CreateDeckModal extends StatefulWidget {
  const CreateDeckModal({super.key});

  @override
  State<CreateDeckModal> createState() => _CreateDeckModalState();
}

class _CreateDeckModalState extends State<CreateDeckModal> {
  List<CardInfo> cards = [];
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
    return ChangeNotifierProvider(
      create: (context) => CreateDeckFormProvider(),
      child: Builder(
        builder: (context) {
          final size = MediaQuery.of(context).size;
          final createDeckFormProvider = Provider.of<CreateDeckFormProvider>(
            context,
          );
          return SizedBox(
            width: size.width * 0.5,
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
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
                    children: [
                      TextFormField(
                        onChanged:
                            (value) => createDeckFormProvider.name = value,
                        validator: (value) {
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
                            (value) =>
                                createDeckFormProvider.description = value,
                        validator: (value) {
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
                            (value) =>
                                createDeckFormProvider.youtubeLink = value,
                        validator: (value) {
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
                        validator: (value) {
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
                        onChanged: (value) => _onSearchChange(context, value),
                      ),
                      SizedBox(height: 20),

                      if (cards.isNotEmpty) ...[
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
                              itemBuilder: (context, index) {
                                final card = cards[index];
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
                        children: [
                          Text('The deck is private: '),
                          Switch(
                            value: createDeckFormProvider.isPrivate,
                            onChanged:
                                (value) => setState(() {
                                  createDeckFormProvider.isPrivate = value;
                                }),
                          ),
                        ],
                      ),
                      SizedBox(height: 20),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          OutlinedButton(
                            onPressed: () => NavigationService.pop(),
                            child: Text('Cancel'),
                          ),
                          SizedBox(width: 30),
                          OutlinedButton(
                            onPressed: () {
                              final validForm =
                                  createDeckFormProvider.validateForm();
                              if (!validForm) return;
                              final userId =
                                  Provider.of<AuthProvider>(
                                    context,
                                    listen: false,
                                  ).user!.userId;
                              final dto = CreateDeckDto(
                                name: createDeckFormProvider.name,
                                description: createDeckFormProvider.description,
                                youtubeLink: createDeckFormProvider.youtubeLink,
                                leader: createDeckFormProvider.leader,
                                userId: userId,
                                isPrivate: createDeckFormProvider.isPrivate,
                              );
                              Provider.of<DecksProvider>(
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
        final newCards = await Provider.of<CardsProvider>(
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
