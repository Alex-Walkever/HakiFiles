class DeckBuildingErrors {
  String deckTotal = '';
  String noMore4Copies = '';
  String noBannedCards = '';
  String noSameColor = '';
  bool isLegal = false;

  validateTotal(int totalCards) {}

  validateErrors() {
    if (deckTotal.isEmpty &&
        noMore4Copies.isEmpty &&
        noBannedCards.isEmpty &&
        noSameColor.isEmpty) {
      isLegal = true;
    } else {
      isLegal = false;
    }
  }

  @override
  String toString() {
    String str = '';
    if (deckTotal.isNotEmpty) {
      str += '$deckTotal\n';
    } else {
      str += 'Exactly 51 cards\n';
    }
    if (noMore4Copies.isNotEmpty) {
      str += 'The follow cards has 4 more copies:\n$noMore4Copies\n';
    } else {
      str += 'The deck not contain more than 4 copies\n';
    }
    if (noSameColor.isNotEmpty) {
      str += 'The follow cards is not the same color:\n$noSameColor\n';
    } else {
      str += 'All cards share color with the leader\n';
    }
    if (noBannedCards.isNotEmpty) {
      str += 'The follow cards are banned:\n$noBannedCards';
    } else {
      str += 'All cards is legal to play';
    }
    return str;
  }
}
