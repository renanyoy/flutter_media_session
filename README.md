# Flutter Media Session

Symply manage audio session, now playing media and remote commands

### Simple usage
```dart
    import 'package:flutter_media_session/flutter_media_session.dart'

    // when initializing your app, set audio session type
    await MediaSession.setSession(.music);

    // when playing item
    await MediaSession.setCommands([.play, .pause]);

    final media = MediaItem(title: 'Simplicity', artist: 'Joe Simple', duration: 180, playing: true);
    await MediaSession.setMedia(media);

    final subscribtion = MediaSession.listen((MediaNotification notif) {
        switch(notif.command) {
            case .play:
            // ...
            break;
            case .pause:
            // ...
            break;            
            default:
            break;
        }
    });

    // update item
    await MediaSession.setMedia(media.copyWith(position:5));
```
_**duration** and **position** are expressed in seconds_

### Cover image
Join an **artUri** URI to your MediaItem description
```dart
    // local file
    await MediaSession.setMedia(MediaItem(title: 'my art', artUri: 'file://my/local/file/image.jpg'));
    // you can use Uri.file(path).toString() to get a well formated Uri string

    // web url
    await MediaSession.setMedia(MediaItem(title: 'my art', artUri: 'https://my.web.site/image.jpg'));
```

### Commands with value
```dart
    final subscribtion = MediaSession.listen((MediaNotification notif) {
        switch(notif.command) {
            case .position:
            final double positionInSeconds = notif.value;
            // ...
            break;
            case .repeat:
            final MediaRepeatType repeatType = notif.value;
            // ...
            break;
            default:
            break;
        }
    });
    
```
If you like this plugin, you can [pay me a coffee](https://ko-fi.com/aestesis).

Contact: renan@aestesis.org

![Cover](<flutter.media.session.jpg> "FMS")

*(AI generated image)*


