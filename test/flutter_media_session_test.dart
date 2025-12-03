import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_media_session/flutter_media_session.dart';
import 'package:flutter_media_session/flutter_media_session_platform_interface.dart';
import 'package:flutter_media_session/flutter_media_session_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterMediaSessionPlatform
    with MockPlatformInterfaceMixin
    implements FlutterMediaSessionPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterMediaSessionPlatform initialPlatform = FlutterMediaSessionPlatform.instance;

  test('$MethodChannelFlutterMediaSession is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterMediaSession>());
  });

  test('getPlatformVersion', () async {
    FlutterMediaSession flutterMediaSessionPlugin = FlutterMediaSession();
    MockFlutterMediaSessionPlatform fakePlatform = MockFlutterMediaSessionPlatform();
    FlutterMediaSessionPlatform.instance = fakePlatform;

    expect(await flutterMediaSessionPlugin.getPlatformVersion(), '42');
  });
}
