import 'dart:async';

import 'package:audio_session/audio_session.dart' as session;

import './messages.g.dart';

//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
class MediaSession {
  static final _session = MediaSessionProtocol();
  static final _center = _NoticationHandler();
  static Future<void> setCommands(List<MediaCommand> commands) =>
      _session.setActiveCommands(commands);
  static Future<void> setMedia(MediaItem item) => _session.setMedia(item);
  static StreamSubscription<MediaNotification> listen(
    void Function(MediaNotification notification)? onData, {
    Function? onError,
    void Function()? onDone,
    bool? cancelOnError,
  }) => _center._messageCtrl.stream.listen(
    onData,
    onError: onError,
    onDone: onDone,
    cancelOnError: cancelOnError,
  );
  static Future<void> setSession(AudioSession type, {bool? active}) async {
    final audio = await session.AudioSession.instance;
    if (active != null) {
      audio.setActive(active);
    }
    switch (type) {
      case AudioSession.music:
        return await audio.configure(session.AudioSessionConfiguration.music());
      case AudioSession.speech:
        return await audio.configure(
          session.AudioSessionConfiguration.speech(),
        );
    }
  }

  static Future<void> setActive(bool active) async {
    final audio = await session.AudioSession.instance;
    audio.setActive(active);
  }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
enum AudioSession { music, speech }

//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
class _NoticationHandler extends MediaNotificationHandler {
  final _messageCtrl = StreamController<MediaNotification>.broadcast();
  _NoticationHandler() {
    MediaNotificationHandler.setUp(this);
  }
  @override
  void notification(MediaNotification notification) {
    _messageCtrl.add(notification);
  }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////
extension MediaItemCopyExt on MediaItem {
  MediaItem copyWith({
    String? title,
    String? artist,
    String? artUri,
    double? position,
    double? duration,
    bool? playing,
  }) => MediaItem(
    title: title ?? this.title,
    artist: artist ?? this.artist,
    artUri: artUri ?? this.artUri,
    position: position ?? this.position,
    duration: duration ?? this.duration,
    playing: playing ?? this.playing,
  );
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
