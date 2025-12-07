package org.aestesis.flutter_media_session

import MediaCommand
import MediaItem
import MediaNotificationHandler
import MyPlayer
import MediaSessionProtocol
import android.content.Context
import androidx.core.net.toUri
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.MediaItem as MediaItem3
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommands
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

// TODO: https://developer.android.com/media/media3
// https://developer.android.com/media/media3/session/control-playback

@UnstableApi
class FlutterMediaSessionPlugin :
    FlutterPlugin,
    MethodCallHandler, MediaSessionProtocol, MediaSession.Callback {
    private lateinit var message: MediaNotificationHandler
    private var mediaSession: MediaSession
    private lateinit var context: Context
    private var player:MyPlayer = MyPlayer()
    init {
        mediaSession = MediaSession.Builder(context, player).setId("flutter_media_session").setCallback(this).build()
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
        val metab = MediaMetadata.Builder()
        if (item.title != null) {
            metab.setTitle(item.title)
        }
        if(item.artist != null) {
            metab.setArtist(item.artist)
        }
        if(item.album != null) {
            metab.setAlbumTitle(item.album)
        }
        if(item.duration != null) {
            metab.setDurationMs((item.duration*1000).toLong())
        }
        if(item.artUri != null) {
            val uri = item.artUri.toUri()
            metab.setArtworkUri(uri)
        }
        val mi = MediaItem3.Builder().setMediaMetadata(metab.build()).build()
        player.setMediaItem(mi)
        if(item.position != null) {
            player.seekTo((item.position*1000).toLong())
        }
    }

    override fun setActiveCommands(commands: List<MediaCommand>) {
        println("setActiveCommands")
        val sc = SessionCommands.Builder().build()
        val pc = Player.Commands.Builder().build()
        for (c in mediaSession.connectedControllers) {
            mediaSession.setAvailableCommands(c, sc,pc)
        }
    }

    override  fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo
    ): MediaSession.ConnectionResult {
        println("onConnect")
        val sc = SessionCommands.Builder().build()
        val pc = Player.Commands.Builder().build()
        return MediaSession.ConnectionResult.accept(sc, pc)
    }
}

