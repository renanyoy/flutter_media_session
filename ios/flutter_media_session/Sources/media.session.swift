import MediaPlayer

class MediaSession {
    let command: MPRemoteCommandCenter = MPRemoteCommandCenter.shared()
    let info: MPNowPlayingInfoCenter = MPNowPlayingInfoCenter.default()
    func post() {        
        let item: MPContentItem = MPContentItem()
    }
}
