# Flutter Media Session

Symply manage audio session, now playing media and remote commands

```dart
    import 'package:flutter_media_session/flutter_media_session.dart'

    // when initializing your app, set audio session type
    MediaSession.setAudioSession(.music);

    // when playing item
    MediaSession.setActiveCommands([.play, .pause]);
    final media = MediaItem(title: 'Simplicity', artist: 'Joe Simple', duration: 180, playing: true);
    MediaSession.setMedia(media);
    final subscribtion = MediaSession.listen((MediaCommand command) {
        // react on remote commands
    });

    // update item
    MediaSession.setMedia(media.copyWith(position:5));
```

*duration and position are exprimed in seconds*

