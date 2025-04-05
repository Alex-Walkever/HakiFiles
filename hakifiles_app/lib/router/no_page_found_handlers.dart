import 'package:fluro/fluro.dart';
import 'package:hakifiles_app/views/index.dart';

class NoPageFoundHandlers {
  static Handler noPageFound = Handler(
    handlerFunc: (context, parameters) {
      return NoPageFoundView();
    },
  );
}
