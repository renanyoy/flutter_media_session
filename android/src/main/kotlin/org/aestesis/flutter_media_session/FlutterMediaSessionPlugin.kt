package org.aestesis.flutter_media_session

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import androidx.media3.common.util.UnstableApi
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

// TODO: https://developer.android.com/media/media3
// https://developer.android.com/media/media3/session/control-playback

var handler:Handler? = null

@UnstableApi
class FlutterMediaSessionPlugin :
    FlutterPlugin,
    MethodCallHandler,
    MediaSessionProtocol {
    private lateinit var message: MediaNotificationHandler
    private lateinit var context: Context
    //private lateinit var handler:Handler

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext
        //handler = Handler(context.applicationContext.mainLooper)
        MediaSessionProtocol.setUp(binding.binaryMessenger, this)
        message = MediaNotificationHandler(binding.binaryMessenger)
        val name = context.startService(Intent(context, FlutterMediaSessionService::class.java))
        println("FlutterMediaSessionPlugin.onAttachedToEngine() service: $name")
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        MediaSessionProtocol.setUp(binding.binaryMessenger, null)
        context.stopService(Intent(context, FlutterMediaSessionService::class.java))
    }

    override fun onMethodCall(
        call: MethodCall,
        result: Result,
    ) {}

    override fun setMedia(item: MediaItem) {
        val m = Message()
        m.what = 0
        m.obj = item
        val result = handler?.sendMessage(m)
        println("setMedia(): $result")
    }

    override fun setActiveCommands(commands: List<MediaCommand>) {
        val m = Message()
        m.what = 1
        m.obj = HashSet<MediaCommand>(commands)
        val result = handler?.sendMessage(m)
        println("setActiveCommands(): $result")
    }
}
