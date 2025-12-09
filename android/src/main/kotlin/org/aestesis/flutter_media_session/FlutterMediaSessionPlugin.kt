package org.aestesis.flutter_media_session

import FlutterMediaSessionPlayer
import MediaCommand
import MediaItem
import MediaNotificationHandler
import MediaSessionProtocol
import android.content.Context
import androidx.core.net.toUri
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionCommands
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import androidx.media3.common.MediaItem as MediaItem3

// TODO: https://developer.android.com/media/media3
// https://developer.android.com/media/media3/session/control-playback

@UnstableApi
class FlutterMediaSessionPlugin :
    FlutterPlugin,
    MethodCallHandler,
    MediaSessionProtocol,
    MediaSession.Callback {
    private lateinit var message: MediaNotificationHandler
    private lateinit var ms: androidx.media3.session.MediaSession
    private val player: FlutterMediaSessionPlayer = FlutterMediaSessionPlayer({ it ->
        message.notify(it, {})
    })
    private lateinit var context: Context

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        MediaSessionProtocol.setUp(flutterPluginBinding.binaryMessenger, this)
        message = MediaNotificationHandler(flutterPluginBinding.binaryMessenger)
        ms = androidx.media3.session.MediaSession.Builder(context,player). build()
        println("FlutterMediaSessionPlugin attached")
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        MediaSessionProtocol.setUp(binding.binaryMessenger, null)
    }

    override fun setMedia(item: MediaItem) {
        println("FlutterMediaSessionPlugin setMedia")
        val meta = MediaMetadata.Builder()
        if (item.title != null) {
            meta.setTitle(item.title)
        }
        if (item.artist != null) {
            meta.setArtist(item.artist)
        }
        if (item.album != null) {
            meta.setAlbumTitle(item.album)
        }
        if (item.duration != null) {
            meta.setDurationMs((item.duration * 1000).toLong())
        }
        if (item.artUri != null) {
            val uri = item.artUri.toUri()
            meta.setArtworkUri(uri)
        }
        if(item.duration != null) {
            meta.setDurationMs((item.duration *1000).toLong())
        }
        if(item.playing != null) {
            player.playing = item.playing
        }
        meta.setIsBrowsable(false)
        meta.setIsPlayable(true)
        val mi = MediaItem3.Builder().setMediaMetadata(meta.build()).build()
        player.setMediaItem(mi)
        if (item.position != null) {
            player.seekTo((item.position * 1000).toLong())
        }
    }

    override fun setActiveCommands(commands: List<MediaCommand>) {
        println("FlutterMediaSessionPlugin setActiveCommands")
        val sc = SessionCommands.Builder()
        val pc = Player.Commands.Builder()
        for (c in commands) {
            when(c) {
                MediaCommand.PAUSE -> pc.add(Player.COMMAND_PLAY_PAUSE)
                MediaCommand.PLAY -> pc.add(Player.COMMAND_PLAY_PAUSE)
                MediaCommand.STOP -> pc.add(Player.COMMAND_STOP)
                MediaCommand.TOGGLE_PLAY_PAUSE -> pc.add(Player.COMMAND_PLAY_PAUSE)
                MediaCommand.NEXT_TRACK -> pc.add(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
                MediaCommand.PREVIOUS_TRACK -> pc.add(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
                MediaCommand.REPEAT_MODE -> {}
                MediaCommand.SHUFFLE_MODE -> {}
                MediaCommand.PLAY_BACK_RATE -> {}
                MediaCommand.SEEK_BACKWARD -> {}
                MediaCommand.SEEK_FORWARD -> {}
                MediaCommand.SKIP_BACKWARD -> {}
                MediaCommand.SKIP_FORWARD -> {}
                MediaCommand.POSITION -> {}
                MediaCommand.RATING -> {}
                MediaCommand.LIKE -> {}
                MediaCommand.DISLIKE -> {}
                MediaCommand.BOOKMARK -> {}
            }
        }
        for (c in ms.connectedControllers) {
            ms.setAvailableCommands(c, sc.build(), pc.build())
        }
    }

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
    ): MediaSession.ConnectionResult {
        println("onConnect")
        val sc = SessionCommands.Builder()
        val pc = Player.Commands.Builder()
        pc.add(Player.COMMAND_PLAY_PAUSE)
        return MediaSession.ConnectionResult.accept(sc.build(), pc.build())
    }

    override fun onMethodCall(
        call: MethodCall,
        result: Result,
    ) {
        result.notImplemented()
    }
}
