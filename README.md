# Flutter Media Session

Symply manage audio session, now playing media and remote commands

```dart
    import 'package:flutter_media_session/flutter_media_session.dart'

    // when initializing your app, set audio session type
    MediaSession.setAudioSession(.music);

    // when playing item
    MediaSession.setActiveCommands([.play, .pause]);
    MediaSession.setMedia(
      MediaItem(title: 'Simplicity', artist: 'Joe Simple', playing: true),
    );
    final subscribtion = MediaSession.listen((MediaCommand command) {
        // react on remote commands
    });
```


