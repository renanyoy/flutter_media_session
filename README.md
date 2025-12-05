# Flutter Media Session

Symply manage audio session, now playing media and remote commands

### Simple usage

```dart
    import 'package:flutter_media_session/flutter_media_session.dart'

    // when initializing your app, set audio session type
    MediaSession.setSession(.music);

    // when playing item
    MediaSession.setCommands([.play, .pause]);

    final media = MediaItem(title: 'Simplicity', artist: 'Joe Simple', duration: 180, playing: true);
    MediaSession.setMedia(media);

    final subscribtion = MediaSession.listen((MediaCommand command) {
        // react on remote commands
    });

    // update item
    MediaSession.setMedia(media.copyWith(position:5));
```

*duration and position are expressed in seconds*

### Cover image

Join an artUri URI to your MediaItem description
```dart
    // local file
    MediaSession.setMedia(MediaItem(title: 'my art', artUri: 'file://my/local/file/image.jpg'));

    // web url
    MediaSession.setMedia(MediaItem(title: 'my art', artUri: 'https://my.web.site/image.jpg'));
```

If you like this plugin, you can [pay me a coffee](https://ko-fi.com/aestesis).

Contact: renan@aestesis.org

![Cover](<flutter.media.session.jpg> "FMS")

*(AI generated image)*


