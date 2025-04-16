import 'package:dio/dio.dart';
import 'package:hakifiles_app/Services/index.dart';

class HakifilesApi {
  static final Dio _dio = Dio();

  static void configureDio() {
    _dio.options.baseUrl = 'http://localhost:8090/api';
    String? token = LocalStorage.getToken();
    if (token != null) {
      _dio.options.headers = {'Authorization': 'Bearer $token'};
    }
  }

  static Future httpGet(String path) async {
    try {
      final resp = await _dio.get(path);
      return resp.data;
    } on DioException catch (e) {
      throw ('Error at get: ${e.message}');
    }
  }

  static Future httpGetWithBody(String path, Map<String, dynamic> data) async {
    try {
      final resp = await _dio.get(path, data: data);
      return resp.data;
    } on DioException catch (e) {
      throw ('Error at get: ${e.message}');
    }
  }

  static Future httpPost(String path, Map<String, dynamic> data) async {
    try {
      final resp = await _dio.post(path, data: data);
      return resp.data;
    } on DioException catch (e) {
      throw ('Error at post: ${e.response}');
    }
  }
}
