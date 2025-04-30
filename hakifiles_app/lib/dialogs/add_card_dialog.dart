import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/models/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/tools/index.dart';

typedef DeckEntry = DropdownMenuEntry<Deck>;

class AddCardDialog extends StatefulWidget {
  const AddCardDialog({super.key, required this.card});

  final CardInfo card;

  @override
  State<AddCardDialog> createState() => _AddCardDialogState();
}

class _AddCardDialogState extends State<AddCardDialog> {
  int? _ammount = 1;
  Deck? _selectedDeck;

  @override
  void initState() {
    super.initState();
    Provider.of<DecksProvider>(
      context,
      listen: false,
    ).getUserDecks(Provider.of<AuthProvider>(context, listen: false).user!);
  }

  @override
  Widget build(BuildContext context) {
    DecksProvider decksProvider = Provider.of<DecksProvider>(context);
    List<Deck> userDecks = decksProvider.userDecks;
    Widget imageWidget = getImageWidget(
      img: widget.card.image,
      height: minCardImage.height,
      width: minCardImage.width,
    );
    if (userDecks.isNotEmpty && _selectedDeck == null) {
      _selectedDeck = userDecks.first;
    }
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(25),
        image: DecorationImage(
          fit: BoxFit.fill,
          image: AssetImage('images/add-card-background.png'),
        ),
      ),
      width: 400,
      child: Column(
        children: <Widget>[
          Container(
            padding: EdgeInsets.only(right: 10, top: 10),
            alignment: Alignment.centerRight,
            child: IconButton(
              onPressed: NavigationService.pop,
              icon: Icon(Icons.clear, color: Colors.white),
            ),
          ),
          Padding(padding: const EdgeInsets.all(10), child: imageWidget),
          Text(
            'How many do you want to add?',
            style: TextStyle(backgroundColor: Colors.white),
          ),
          SizedBox(height: 10),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[_option(1), _option(2), _option(3), _option(4)],
          ),
          SizedBox(height: 20),
          if (userDecks.isEmpty)
            Text(
              'Create a deck before adding cards',
              style: TextStyle(backgroundColor: Colors.white),
            ),
          if (userDecks.isNotEmpty)
            Container(
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(2),
              ),
              child: DropdownMenu<Deck>(
                requestFocusOnTap: false,
                width: 200,
                initialSelection: userDecks.first,
                onSelected: (Deck? value) {
                  setState(() {
                    _selectedDeck = value;
                  });
                },
                dropdownMenuEntries: _getDeckEntries(userDecks),
              ),
            ),
          SizedBox(height: 20),
          if (_selectedDeck != null && _ammount != null)
            OutlinedButton(
              style: ButtonStyle(
                backgroundColor: WidgetStatePropertyAll<Color?>(Colors.white),
              ),
              onPressed: () {
                decksProvider.addCardToDeck(
                  widget.card.cardId,
                  _ammount!,
                  _selectedDeck!,
                );
                NavigationService.pop();
              },
              child: Text('Add to deck'),
            ),
        ],
      ),
    );
  }

  Expanded _option(int option) {
    return Expanded(
      child: Container(
        decoration: BoxDecoration(
          color: Colors.white,
          border: Border.all(width: 1),
          borderRadius: BorderRadius.circular(50),
        ),
        child: ListTile(
          title: Text(option.toString()),
          leading: Radio<int>(
            value: option,
            groupValue: _ammount,
            onChanged: (int? value) {
              setState(() {
                _ammount = value;
              });
            },
          ),
        ),
      ),
    );
  }

  List<DeckEntry> _getDeckEntries(List<Deck> userDecks) {
    return UnmodifiableListView<DeckEntry>(
      userDecks.map<DeckEntry>(
        (Deck deck) => DeckEntry(value: deck, label: deck.name),
      ),
    );
  }
}
