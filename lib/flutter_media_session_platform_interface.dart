import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_media_session_method_channel.dart';

abstract class FlutterMediaSessionPlatform extends PlatformInterface {
  FlutterMediaSessionPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterMediaSessionPlatform _instance =
      MethodChannelFlutterMediaSession();
  static FlutterMediaSessionPlatform get instance => _instance;

  static set instance(FlutterMediaSessionPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
