
import 'flutter_media_session_platform_interface.dart';

class FlutterMediaSession {
  Future<String?> getPlatformVersion() {
    return FlutterMediaSessionPlatform.instance.getPlatformVersion();
  }
}
