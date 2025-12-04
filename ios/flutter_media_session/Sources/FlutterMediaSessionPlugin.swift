import Flutter
import MediaPlayer
import UIKit

public class FlutterMediaSessionPlugin: NSObject, FlutterPlugin, MediaSessionProtocol {
  static let instance: FlutterMediaSessionPlugin = FlutterMediaSessionPlugin()
  static var message: MediaCommandCenter?
  public static func register(with registrar: FlutterPluginRegistrar) {
    let messenger = registrar.messenger()
    MediaSessionProtocolSetup.setUp(binaryMessenger: messenger, api: instance)
    message = MediaCommandCenter(binaryMessenger: messenger)
  }

  override init() {
    super.init()
    let center = MPRemoteCommandCenter.shared()
    center.playCommand.addTarget(handler: { e in
      FlutterMediaSessionPlugin.message?.command(command: .play, completion: { _ in })
      return .success
    })
  }

  func setActiveCommands(commands: [MediaCommand]) throws {
    let center = MPRemoteCommandCenter.shared()
    center.stopCommand.isEnabled = commands.contains(.stop)
    center.playCommand.isEnabled = commands.contains(.play)
    center.pauseCommand.isEnabled = commands.contains(.pause)
    center.togglePlayPauseCommand.isEnabled = commands.contains(.togglePlayPause)
  }
  func setMedia(item: MediaItem) throws {
    var info: [String: Any] = [:]
    if item.title != nil {
      info[MPMediaItemPropertyTitle] = item.title!
    }
    if item.artist != nil {
      info[MPMediaItemPropertyArtist] = item.artist!
    }
    if item.artUri != nil {
      let url = URL(string: item.artUri!)
      if let url = url {
        if url.isFileURL {
          if let img: UIImage = UIImage(contentsOfFile: url.path(percentEncoded: false)) {
            let mi = MPMediaItemArtwork(
              boundsSize: img.size, requestHandler: { _ in return img })
            info[MPMediaItemPropertyArtwork] = mi
          }
        } else {
          // TODO:
        }
      }
    }
    if item.position != nil {
      info[MPMediaItemPropertyPlaybackDuration] = info.position!
    }
    if item.duration != nil {
      info[MPMediaItemPropertyPlaybackDuration] = info.duration!
    }

    MPNowPlayingInfoCenter.default().nowPlayingInfo = info
    if let playing = item.playing {
      MPNowPlayingInfoCenter.default().playbackState = playing ? .playing : .paused
    } else {
      MPNowPlayingInfoCenter.default().playbackState = .stopped
    }
  }

}
