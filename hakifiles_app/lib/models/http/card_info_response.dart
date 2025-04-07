import 'package:hakifiles_app/models/index.dart';

class CardInfoResponse {
  final List<CardInfo> cardInfoList;

  CardInfoResponse({required this.cardInfoList});

  factory CardInfoResponse.fromJson(List<dynamic> json) => CardInfoResponse(
    cardInfoList:
        List<CardInfo>.from(
          json.map((e) {
            return CardInfo.fromJson(e);
          }),
        ).toList(),
  );
}
