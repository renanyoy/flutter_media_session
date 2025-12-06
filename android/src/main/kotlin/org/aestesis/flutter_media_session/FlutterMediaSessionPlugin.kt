package org.aestesis.flutter_media_session

import MediaCommand
import MediaItem
import MediaNotificationHandler
import MediaPlayer
import MediaSessionProtocol
import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession as MS
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

// TODO: https://developer.android.com/media/media3

@UnstableApi
class FlutterMediaSessionPlugin :
    FlutterPlugin,
    MethodCallHandler, MediaSessionProtocol {
    private lateinit var message: MediaNotificationHandler
    private var mediaSession:MS
    private lateinit var context: Context
    private var player:MediaPlayer = MediaPlayer()
    init {
        mediaSession = MS.Builder(context, player).build()
    }
    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        MediaSessionProtocol.setUp(flutterPluginBinding.binaryMessenger,this)
        message = MediaNotificationHandler(flutterPluginBinding.binaryMessenger)
    }

    override fun onMethodCall(
        call: MethodCall,
        result: Result,
    ) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        MediaSessionProtocol.setUp(binding.binaryMessenger,null)
    }

    override fun setMedia(item: MediaItem) {
        println("setMedia")
        // TODO("Not yet implemented")
    }

    override fun setActiveCommands(commands: List<MediaCommand>) {
        println("setActiveCommands")
        // TODO("Not yet implemented")
    }

    /*
    fun createMediaMetadata(
        id: String?,
        title: String?,
        album: String?,
        artist: String?,
        duration: Double?,
        artUri: String?,
    ): MediaMetadataCompat? {
        val builder: MediaMetadataCompat.Builder = Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
        if (album != null) builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
        if (artist != null) builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
        if (duration != null) builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, (duration*1000).toLong())
        if (artUri != null) {
            builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, artUri)
        }
        val mediaMetadata: MediaMetadataCompat? = builder.build()
        mediaMetadataCache.put(id, mediaMetadata)
        return mediaMetadata
    }
    */
}

