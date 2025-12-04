import 'dart:async';

import './messages.g.dart';

class MediaSession {
  static final _session = MediaSessionProtocol();
  static final _center = _CommandCenter();
  static Future<void> setActiveCommands(List<MediaCommand> commands) =>
      _session.setActiveCommands(commands);
  static Future<void> setMedia(MediaItem item) => _session.setMedia(item);
  static StreamSubscription<MediaCommand> listen(
    void Function(MediaCommand command)? onData, {
    Function? onError,
    void Function()? onDone,
    bool? cancelOnError,
  }) => _center._messageCtrl.stream.listen(
    onData,
    onError: onError,
    onDone: onDone,
    cancelOnError: cancelOnError,
  );
}

class _CommandCenter extends MediaCommandCenter {
  final _messageCtrl = StreamController<MediaCommand>.broadcast();
  _CommandCenter() {
    MediaCommandCenter.setUp(this);
  }
  @override
  void command(MediaCommand command) {
    _messageCtrl.add(command);
  }
}
