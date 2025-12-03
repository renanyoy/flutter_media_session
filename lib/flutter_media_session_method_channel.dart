import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_media_session_platform_interface.dart';

/// An implementation of [FlutterMediaSessionPlatform] that uses method channels.
class MethodChannelFlutterMediaSession extends FlutterMediaSessionPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_media_session');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>(
      'getPlatformVersion',
    );
    return version;
  }
}
