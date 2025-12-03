import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_media_session_method_channel.dart';

abstract class FlutterMediaSessionPlatform extends PlatformInterface {
  /// Constructs a FlutterMediaSessionPlatform.
  FlutterMediaSessionPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterMediaSessionPlatform _instance = MethodChannelFlutterMediaSession();

  /// The default instance of [FlutterMediaSessionPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterMediaSession].
  static FlutterMediaSessionPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterMediaSessionPlatform] when
  /// they register themselves.
  static set instance(FlutterMediaSessionPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
