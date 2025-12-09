import android.os.Looper
import android.os.Looper.getMainLooper
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import androidx.media3.common.AudioAttributes
import androidx.media3.common.DeviceInfo
import androidx.media3.common.DeviceInfo.PLAYBACK_TYPE_LOCAL
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Player.MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED
import androidx.media3.common.Timeline
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.text.CueGroup
import androidx.media3.common.util.Size
import androidx.media3.common.util.UnstableApi
import java.lang.Boolean.FALSE

@UnstableApi
class FlutterMediaSessionPlayer(onNotification: (MediaNotification) -> Unit) : Player {
    var onNotification: (MediaNotification) -> Unit = {}
    var listeners: ArrayList<Player.Listener> = ArrayList()
    var mMediaItem: MediaItem? = null
    var playing: Boolean = FALSE

    init {
        this.onNotification = onNotification
    }
    override fun getApplicationLooper(): Looper = getMainLooper()

    override fun addListener(listener: Player.Listener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: Player.Listener) {
        listeners.remove(listener)
    }

    override fun setMediaItems(mediaItems: List<MediaItem>) {
    }

    override fun setMediaItems(
        mediaItems: List<MediaItem>,
        resetPosition: Boolean,
    ) {
    }

    override fun setMediaItems(
        mediaItems: List<MediaItem>,
        startIndex: Int,
        startPositionMs: Long,
    ) {
    }

    override fun setMediaItem(mediaItem: MediaItem) {
        this.mMediaItem = mediaItem
        for (listener in listeners) {
            listener.onMediaItemTransition(mediaItem, MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED)
        }
    }

    override fun setMediaItem(
        mediaItem: MediaItem,
        startPositionMs: Long,
    ) {
    }

    override fun setMediaItem(
        mediaItem: MediaItem,
        resetPosition: Boolean,
    ) {
    }

    override fun addMediaItem(mediaItem: MediaItem) {
    }

    override fun addMediaItem(
        index: Int,
        mediaItem: MediaItem,
    ) {
    }

    override fun addMediaItems(mediaItems: List<MediaItem>) {
    }

    override fun addMediaItems(
        index: Int,
        mediaItems: List<MediaItem>,
    ) {
    }

    override fun moveMediaItem(
        currentIndex: Int,
        newIndex: Int,
    ) {
    }

    override fun moveMediaItems(
        fromIndex: Int,
        toIndex: Int,
        newIndex: Int,
    ) {
    }

    override fun replaceMediaItem(
        index: Int,
        mediaItem: MediaItem,
    ) {
    }

    override fun replaceMediaItems(
        fromIndex: Int,
        toIndex: Int,
        mediaItems: List<MediaItem>,
    ) {
    }

    override fun removeMediaItem(index: Int) {
        mMediaItem = null
    }

    override fun removeMediaItems(
        fromIndex: Int,
        toIndex: Int,
    ) {
        mMediaItem = null
    }

    override fun clearMediaItems() {
        mMediaItem = null
    }

    override fun isCommandAvailable(command: Int): Boolean = true

    override fun canAdvertiseSession(): Boolean = true

    override fun getAvailableCommands(): Player.Commands = Player.Commands.Builder().build()

    override fun prepare() {
    }

    override fun getPlaybackState(): Int = Player.STATE_READY

    override fun getPlaybackSuppressionReason(): Int = Player.PLAYBACK_SUPPRESSION_REASON_NONE

    override fun isPlaying(): Boolean {
        return playing;
    }

    override fun getPlayerError(): PlaybackException = PlaybackException("none", null, 0)

    override fun play() {
        playing = true
        onNotification.invoke(MediaNotification(MediaCommand.PLAY))
    }

    override fun pause() {
        playing = false
        onNotification.invoke(MediaNotification(MediaCommand.PAUSE))
    }

    override fun setPlayWhenReady(playWhenReady: Boolean) {
    }

    override fun getPlayWhenReady(): Boolean = false

    override fun setRepeatMode(repeatMode: Int) {
    }

    override fun getRepeatMode(): Int = Player.REPEAT_MODE_OFF

    override fun setShuffleModeEnabled(shuffleModeEnabled: Boolean) {
    }

    override fun getShuffleModeEnabled(): Boolean = false

    override fun isLoading(): Boolean = false

    override fun seekToDefaultPosition() {
    }

    override fun seekToDefaultPosition(mediaItemIndex: Int) {
    }

    override fun seekTo(positionMs: Long) {
    }

    override fun seekTo(
        mediaItemIndex: Int,
        positionMs: Long,
    ) {
    }

    override fun getSeekBackIncrement(): Long = 0

    override fun seekBack() {
    }

    override fun getSeekForwardIncrement(): Long = 0

    override fun seekForward() {
    }

    override fun hasPreviousMediaItem(): Boolean = false

    override fun seekToPreviousMediaItem() {
        onNotification.invoke(MediaNotification(MediaCommand.PREVIOUS_TRACK))
    }

    override fun getMaxSeekToPreviousPosition(): Long = 0

    override fun seekToPrevious() {
        onNotification.invoke(MediaNotification(MediaCommand.PREVIOUS_TRACK))
    }

    override fun hasNextMediaItem(): Boolean = false

    override fun seekToNextMediaItem() {
        onNotification.invoke(MediaNotification(MediaCommand.NEXT_TRACK))
    }

    override fun seekToNext() {
        onNotification.invoke(MediaNotification(MediaCommand.NEXT_TRACK))
    }

    override fun setPlaybackParameters(playbackParameters: PlaybackParameters) {
    }

    override fun setPlaybackSpeed(speed: Float) {
    }

    override fun getPlaybackParameters(): PlaybackParameters = PlaybackParameters.DEFAULT

    override fun stop() {
        playing = false
        onNotification.invoke(MediaNotification(MediaCommand.STOP))
    }

    override fun release() {
    }

    override fun getCurrentTracks(): Tracks = Tracks.EMPTY

    override fun getTrackSelectionParameters(): TrackSelectionParameters = TrackSelectionParameters.Builder().build()

    override fun setTrackSelectionParameters(parameters: TrackSelectionParameters) {
    }

    override fun getMediaMetadata(): MediaMetadata = MediaMetadata.Builder().build()

    override fun getPlaylistMetadata(): MediaMetadata = MediaMetadata.Builder().build()

    override fun setPlaylistMetadata(mediaMetadata: MediaMetadata) {
    }

    override fun getCurrentManifest(): Any = 0

    override fun getCurrentTimeline(): Timeline = Timeline.EMPTY

    override fun getCurrentPeriodIndex(): Int = 0

    @Deprecated("Deprecated in Java")
    override fun getCurrentWindowIndex(): Int = 0

    override fun getCurrentMediaItemIndex(): Int = 0

    @Deprecated("Deprecated in Java")
    override fun getNextWindowIndex(): Int = 0

    override fun getNextMediaItemIndex(): Int = 0

    @Deprecated("Deprecated in Java")
    override fun getPreviousWindowIndex(): Int = 0

    override fun getPreviousMediaItemIndex(): Int = 0

    override fun getCurrentMediaItem(): MediaItem = MediaItem.Builder().build()

    override fun getMediaItemCount(): Int = 0

    override fun getMediaItemAt(index: Int): MediaItem = MediaItem.Builder().build()

    override fun getDuration(): Long = 0

    override fun getCurrentPosition(): Long = 0

    override fun getBufferedPosition(): Long = 0

    override fun getBufferedPercentage(): Int = 0

    override fun getTotalBufferedDuration(): Long = 0

    @Deprecated("Deprecated in Java")
    override fun isCurrentWindowDynamic(): Boolean = false

    override fun isCurrentMediaItemDynamic(): Boolean = true

    @Deprecated("Deprecated in Java")
    override fun isCurrentWindowLive(): Boolean = true

    override fun isCurrentMediaItemLive(): Boolean = true

    override fun getCurrentLiveOffset(): Long = 0

    @Deprecated("Deprecated in Java")
    override fun isCurrentWindowSeekable(): Boolean = false

    override fun isCurrentMediaItemSeekable(): Boolean = false

    override fun isPlayingAd(): Boolean = false

    override fun getCurrentAdGroupIndex(): Int {
        return -1
    }

    override fun getCurrentAdIndexInAdGroup(): Int {
        return 0
    }

    override fun getContentDuration(): Long = 0

    override fun getContentPosition(): Long = 0

    override fun getContentBufferedPosition(): Long = 0

    override fun getAudioAttributes(): AudioAttributes = AudioAttributes.Builder().build()

    override fun setVolume(volume: Float) {
    }

    override fun getVolume(): Float = 0.5f

    override fun clearVideoSurface() {
    }

    override fun clearVideoSurface(surface: Surface?) {
    }

    override fun setVideoSurface(surface: Surface?) {
    }

    override fun setVideoSurfaceHolder(surfaceHolder: SurfaceHolder?) {
    }

    override fun clearVideoSurfaceHolder(surfaceHolder: SurfaceHolder?) {
    }

    override fun setVideoSurfaceView(surfaceView: SurfaceView?) {
    }

    override fun clearVideoSurfaceView(surfaceView: SurfaceView?) {
    }

    override fun setVideoTextureView(textureView: TextureView?) {
    }

    override fun clearVideoTextureView(textureView: TextureView?) {
    }

    override fun getVideoSize(): VideoSize = VideoSize.UNKNOWN

    @UnstableApi
    override fun getSurfaceSize(): Size = Size(0, 0)

    override fun getCurrentCues(): CueGroup = CueGroup.EMPTY_TIME_ZERO

    override fun getDeviceInfo(): DeviceInfo = DeviceInfo.Builder(PLAYBACK_TYPE_LOCAL).build()

    override fun getDeviceVolume(): Int = 0

    override fun isDeviceMuted(): Boolean = false

    @Deprecated("Deprecated in Java")
    override fun setDeviceVolume(volume: Int) {
    }

    override fun setDeviceVolume(
        volume: Int,
        flags: Int,
    ) {
    }

    @Deprecated("Deprecated in Java")
    override fun increaseDeviceVolume() {
    }

    override fun increaseDeviceVolume(flags: Int) {
    }

    @Deprecated("Deprecated in Java")
    override fun decreaseDeviceVolume() {
    }

    override fun decreaseDeviceVolume(flags: Int) {
    }

    @Deprecated("Deprecated in Java")
    override fun setDeviceMuted(muted: Boolean) {
    }

    override fun setDeviceMuted(
        muted: Boolean,
        flags: Int,
    ) {
    }

    override fun setAudioAttributes(
        audioAttributes: AudioAttributes,
        handleAudioFocus: Boolean,
    ) {
    }
}
