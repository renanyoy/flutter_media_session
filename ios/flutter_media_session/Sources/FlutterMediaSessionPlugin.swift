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
    center.pauseCommand.addTarget(handler: { e in
      return not(command: .pause)
    })
    center.playCommand.addTarget(handler: { e in
      return not(command: .play)
    })
    center.stopCommand.addTarget(handler: { e in
      return not(command: .stop)
    })
    center.togglePlayPauseCommand.addTarget(handler: { e in
      return not(command: .togglePlayPause)
    })
    center.nextTrackCommand.addTarget(handler: { e in
      return not(command: .nextTrack)
    })
    center.previousTrackCommand.addTarget(handler: { e in
      return not(command: .previousTrack)
    })
    center.changeRepeatModeCommand.addTarget(handler: { e in
      guard let r = e as? MPChangeRepeatModeCommandEvent else { return .commandFailed }
      var type: MediaRepeatType = .none
      switch r.repeatType {
      case .one:
        type = .one
      case .all:
        type = .all
      default:
        type = .none
      }
      return not(command: .changeRepeatMode, value: type)
    })
    center.changeShuffleModeCommand.addTarget(handler: { e in
      guard let s = e as? MPChangeShuffleModeCommandEvent else { return .commandFailed }
      var type: MediaShuffleType = .none
      switch s.shuffleType {
      case .items:
        type = .items
      case .collections:
        type = .collections
      default:
        type = .none
      }
      return not(command: .changeShuffleMode, value: type)
    })
    center.changePlaybackRateCommand.addTarget(handler: { e in
      guard let r = e as? MPChangePlaybackRateCommandEvent else { return .commandFailed }
      return not(
        command: .changePlayBackRate,
        value: r.playbackRate)
    })
    center.changePlaybackPositionCommand.addTarget(handler: { e in
      guard let p = e as? MPChangePlaybackPositionCommandEvent else { return .commandFailed }
      return not(
        command: .changePlaybackPosition,
        value: p.positionTime)
    })
    center.ratingCommand.addTarget(handler: { e in
      return not(command: .rating)
    })
    center.likeCommand.addTarget(handler: { e in
      return not(command: .like)
    })
    center.dislikeCommand.addTarget(handler: { e in
      return not(command: .dislike)
    })
    center.bookmarkCommand.addTarget(handler: { e in
      return not(command: .bookmark)
    })
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
    center.changePlaybackRateCommand.supportedPlaybackRates = [0.5, 0.75, 1, 1.25, 1.5]
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
      let u = URL(string: item.artUri!)
      if let url = u {
        if url.isFileURL {
          if let img: UIImage = UIImage(contentsOfFile: url.path(percentEncoded: false)) {
            let mi = MPMediaItemArtwork(
              boundsSize: img.size, requestHandler: { _ in return img })
            info[MPMediaItemPropertyArtwork] = mi
          }
        } else {
          if let data = try? Data(contentsOf: url), let img: UIImage = UIImage(data: data) {
            let mi = MPMediaItemArtwork(boundsSize: img.size, requestHandler: { _ in return img })
            info[MPMediaItemPropertyArtwork] = mi
          }
        }
      }
    }
    if item.position != nil {
      info[MPMediaItemPropertyPlaybackDuration] = item.position!
    }
    if item.duration != nil {
      info[MPMediaItemPropertyPlaybackDuration] = item.duration!
    }

    MPNowPlayingInfoCenter.default().nowPlayingInfo = info
    if let playing = item.playing {
      MPNowPlayingInfoCenter.default().playbackState = playing ? .playing : .paused
    } else {
      MPNowPlayingInfoCenter.default().playbackState = .stopped
    }
  }

}

func not(command: MediaCommand, value: Any? = nil) -> MPRemoteCommandHandlerStatus {
  FlutterMediaSessionPlugin.message?.notification(
    notification: MediaNotification(command: command, value: value),
    completion: { r in
      if let e = r as? PigeonError {
        print("\(e)")
      }
    })
  return .success
}
