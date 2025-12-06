package org.aestesis.flutter_media_session

import MediaCommand
import MediaItem
import MediaNotificationHandler
import MediaSessionProtocol
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

// TODO: https://developer.android.com/media/media3

class FlutterMediaSessionPlugin :
    FlutterPlugin,
    MethodCallHandler, MediaSessionProtocol {
    private lateinit var message: MediaNotificationHandler
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
}
