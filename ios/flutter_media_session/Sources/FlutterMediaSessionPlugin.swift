import Flutter
import MediaPlayer
import UIKit

public class FlutterMediaSessionPlugin: NSObject, FlutterPlugin, MediaSessionProtocol {
  static let instance: FlutterMediaSessionPlugin = FlutterMediaSessionPlugin()
  static var message: MediaCommandCenterProtocolProtocol?
  public static func register(with registrar: FlutterPluginRegistrar) {
    let messenger = registrar.messenger()
    MediaSessionProtocolSetup.setUp(binaryMessenger: messenger, api: instance)
    message = MediaCommandCenterProtocolProtocol(binaryMessenger: messenger)
  }

  func setMedia(item: MediaItem) throws {
    var info: [String: Any] = [:]
    if item.title != nil {
      info[MPMediaItemPropertyTitle] = item.title!
      print("setMedia(title:\(item.title))")
    }
    if item.artist != nil {
      info[MPMediaItemPropertyArtist] = item.artist!
      print("setMedia(artist:\(item.artist))")
    }
    if item.artUri != nil {
      let url = URL(string: item.artUri!)
      if let url = url {
        if url.isFileURL {
          if let img: UIImage = UIImage(contentsOfFile: url.path(percentEncoded: false)) {
            let mi = MPMediaItemArtwork(
              boundsSize: img.size, requestHandler: { _ in return img })
            info[MPMediaItemPropertyArtwork] = mi
            print("setMedia(Artwork:\(img.size))")
          }
        } else {
          // TODO:
        }
      }
    }
    MPNowPlayingInfoCenter.default().nowPlayingInfo = info
    if let playing = item.playing {
      MPNowPlayingInfoCenter.default().playbackState = playing ? .playing : .paused
    } else {
      MPNowPlayingInfoCenter.default().playbackState = .stopped
    }
  }

  func enableCommands() throws {
    let center = MPRemoteCommandCenter.shared()
    center.togglePlayPauseCommand.isEnabled = true
    center.togglePlayPauseCommand.addTarget { e in return .success }
  }
}
