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
    let handler: (MPRemoteCommandEvent) -> MPRemoteCommandHandlerStatus = { e in
      FlutterMediaSessionPlugin.message?.notification(
        command: notification(e), completion: { _ in })
      return .success
    }
    center.pauseCommand.addTarget(handler: handler)
    center.playCommand.addTarget(handler: handler)
    center.stopCommand.addTarget(hendler: handler)
    center.togglePlayPauseCommand.addTarget(handler: handler)
    center.nextTrackCommand.addTarget(handler: handler)
    center.previousTrackCommand.addTarget(handler: handler)
    center.changeRepeatModeCommand.addTarget(handler: handler)
    center.changeShuffleModeCommand.addTarget(handler: handler)
    center.changePlaybackRateCommand.addTarget(handler: handler)
    center.changePlaybackPositionCommand.addTarget(handler: handler)
    center.ratingCommand.addTarget(handler: handler)
    center.likeCommand.addTarget(handler: { e in 
      FlutterMediaSessionPlugin.message?.notification(
        command: MediaNotification(command: .like), completion: { _ in })
      return .success
    })
    center.dislikeCommand.addTarget(handler: { e in 
      FlutterMediaSessionPlugin.message?.notification(
        command: MediaNotification(command: .dislike), completion: { _ in })
      return .success
    })
    center.bookmarkCommand.addTarget(handler: { e in 
      FlutterMediaSessionPlugin.message?.notification(
        command: MediaNotification(command: .bookmark), completion: { _ in })
      return .success
    })
  }

  func notification(e: MPRemoteCommand) -> MediaNotification {
    switch e {
    case let e as MPPauseCommand:
      return MediaNotification(command: .pause)
    case let e as MPPlayCommand:
      return MediaNotification(command: .play)
    case let e as MPStopCommand:
      return MediaNotification(command: .stop)
    case let e as MPTogglePlayPauseCommand:
      return MediaNotification(command: .togglePlayPause)
    case let e as MPNextTrackCommand:
      return MediaNotification(command: .nextTrack)
    case let e as MPPreviousTrackCommand:
      return MediaNotification(command: .previousTrack)
    case let e as MPChangeRepeatModeCommand:
      var type: MediaRepeatType = .none
      switch e.currentRepeatType {
      case .one:
        type = .one
      case .all:
        type = .all
      default:
        type = .none
      }
      return MediaNotification(command: .changeRepeatMode, value: type)
    case let e as MPChangeShuffleModeCommand:
      var type: MediaShuffleType = .none
      switch e.currentShuffleType {
      case .items:
        type = .items
      case .collections:
        type = .collections
      default:
        type = .none
      }
      return MediaNotification(command: .changeShuffleMode, value: type)
    case let e as MPChangePlaybackRateCommand:
      return MediaNotification(command: .changePlayBackRate)
    case let e as MPChangePlaybackPositionCommand:
      return MediaNotification(command: .changePlaybackPosition, value: e.timestamp)
    case let e as MPRatingCommand:
      return MediaNotification(command: .rating)
    }
    throw "not implemented"
  }

  func setActiveCommands(commands: [MediaCommand]) throws {
    let center = MPRemoteCommandCenter.shared()
    center.pauseCommand.isEnabled = commands.contains(.pause)
    center.playCommand.isEnabled = commands.contains(.play)
    center.stopCommand.isEnabled = commands.contains(.stop)
    center.togglePlayPauseCommand.isEnabled = commands.contains(.togglePlayPause)
    center.nextTrackCommand.isEnabled = commands.contains(.nextTrack)
    center.previousTrackCommand.isEnabled = commands.contains(.previousTrack)
    center.changeRepeatModeCommand.isEnabled = commands.contains(.changeRepeatMode)
    center.changeShuffleModeCommand.isEnabled = commands.contains(.changeShuffleMode)
    center.changePlaybackRateCommand.isEnabled = commands.contains(.changePlayBackRate)
    center.changePlaybackRateCommand.supportedPlaybackRates[0.5, 0.75, 1, 1.25, 1.5]
    center.changePlaybackPositionCommand.isEnabled = commands.contains(.changePlaybackPosition)
    center.ratingCommand.isEnabled = commands.contains(.rating)
    center.ratingCommand.maximumRating = 5
    center.ratingCommand.minimumRating = 1
    center.likeCommand.isEnabled = commands.contains(.like)
    center.dislikeCommand.isEnabled = commands.contains(.dislike)
    center.bookmarkCommand.isEnabled = commands.contains(.bookmark)
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
          if let data = try? Data(contentsOf: url!), let img: UIImage = UIImage(data: data) {
            let mi = MPMediaItemArtwork(boundsSize: img.size, requestHandler: { _ in return img })
            info[MPMediaItemPropertyArtwork] = mi
          }
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
