import 'package:dio/dio.dart';
import 'package:hakifiles_app/Services/index.dart';

class HakifilesApi {
  static final Dio _dio = Dio();

  static void configureDio() {
    _dio.options.baseUrl = 'http://localhost:8090/api';
    _dio.options.headers = {'Authorization': LocalStorage.getToken() ?? ''};
  }

  static Future httpGet(String path) async {
    try {
      final resp = await _dio.get(path);
      return resp.data;
    } on DioException catch (e) {
      throw ('Error at get: ${e.message}');
    }
  }
}
