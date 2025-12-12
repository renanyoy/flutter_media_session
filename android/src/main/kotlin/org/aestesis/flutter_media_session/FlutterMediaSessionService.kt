package org.aestesis.flutter_media_session

import FlutterMediaSessionPlayer
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommands
import androidx.media3.common.MediaItem as MediaItem3

class FlutterMediaSessionService : MediaSessionService(), MediaSession.Callback, Handler.Callback {
    private var mediaSession: MediaSession? = null

    //private var handler : Handler? = null
    val player: FlutterMediaSessionPlayer?
        @OptIn(UnstableApi::class) get() = mediaSession?.player as FlutterMediaSessionPlayer?

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        val player = FlutterMediaSessionPlayer()
        mediaSession = MediaSession.Builder(this, player).setCallback(this).build()
        handler = Handler(applicationContext.mainLooper, this)
        val m = Message()
        m.what = 66
        handler?.sendMessage(m)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        handler = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        println("FlutterMediaSessionPlugin onBind $intent")
        return super.onBind(intent)
    }

    // MediaSession.Callback /////////////////////////////////////////////////////////////////////
    @OptIn(UnstableApi::class)
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
    // ////////////////////////////////////////////////////////////////////////////////////////////

    // Flutter ///////////////////////////////////////////////////////////////////////////////////
    @OptIn(UnstableApi::class)
    fun setMedia(item: MediaItem) {
        println("setMedia")
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
        if (item.duration != null) {
            meta.setDurationMs((item.duration * 1000).toLong())
        }
        if (item.playing != null) {
            player?.playing = item.playing
        }
        meta.setIsBrowsable(false)
        meta.setIsPlayable(true)
        val mi = MediaItem3.Builder().setMediaMetadata(meta.build()).build()
        player?.setMediaItem(mi)
        if (item.position != null) {
            player?.seekTo((item.position * 1000).toLong())
        }
    }

    @OptIn(UnstableApi::class)
    fun setCommands(commands: HashSet<MediaCommand>) {
        println("FlutterMediaSessionPlugin setActiveCommands")
        if (mediaSession == null) return
        val sc = SessionCommands.Builder()
        val pc = Player.Commands.Builder()
        for (c in commands) {
            when (c) {
                MediaCommand.PAUSE -> {
                    pc.add(Player.COMMAND_PLAY_PAUSE)
                }
                MediaCommand.PLAY -> {
                    pc.add(Player.COMMAND_PLAY_PAUSE)
                }
                MediaCommand.STOP -> {
                    pc.add(Player.COMMAND_STOP)
                }
                MediaCommand.TOGGLE_PLAY_PAUSE -> {
                    pc.add(Player.COMMAND_PLAY_PAUSE)
                }
                MediaCommand.NEXT_TRACK -> {
                    pc.add(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
                }
                MediaCommand.PREVIOUS_TRACK -> {
                    pc.add(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
                }
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
        for (c in mediaSession!!.connectedControllers) {
            mediaSession!!.setAvailableCommands(c, sc.build(), pc.build())
        }
    }

    override fun handleMessage(m: Message): Boolean {
        when (m.what) {
            0 -> {
                setMedia(m.obj as MediaItem)
                return true
            }

            1 -> {
                @Suppress("UNCHECKED_CAST") setCommands(m.obj as HashSet<MediaCommand>)
                return true
            }
        }
        return false
    }
}
