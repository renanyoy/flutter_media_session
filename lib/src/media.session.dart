import 'dart:async';

import './messages.g.dart';

class MediaSession extends MediaSessionProtocol {
  MediaSession() : super();
}

class MediaControlCenter extends MediaCommandCenterProtocol {
  final _messageCtrl = StreamController<MediaCommand>.broadcast();
  StreamSubscription listenCommand(
    void Function(MediaCommand command)? onData, {
    Function? onError,
    void Function()? onDone,
    bool? cancelOnError,
  }) => _messageCtrl.stream.listen(
    onData,
    onError: onError,
    onDone: onDone,
    cancelOnError: cancelOnError,
  );

  @override
  void command(MediaCommand command) {
    _messageCtrl.add(command);
  }

  MediaControlCenter() {
    MediaCommandCenterProtocol.setUp(this);
  }
}
