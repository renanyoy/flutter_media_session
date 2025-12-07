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

@UnstableApi
class MyPlayer : Player {
    var onNotification: (MediaNotification) -> Unit = {}
    var mediaItem: MediaItem? = null
    var listeners: ArrayList<Player.Listener> = ArrayList()
    override fun getApplicationLooper(): Looper {
        return getMainLooper()
    }

    override fun addListener(listener: Player.Listener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: Player.Listener) {
        listeners.remove(listener)
    }

    override fun setMediaItems(mediaItems: List<MediaItem>) {
        TODO("Not yet implemented")
    }

    override fun setMediaItems(
        mediaItems: List<MediaItem>,
        resetPosition: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun setMediaItems(
        mediaItems: List<MediaItem>,
        startIndex: Int,
        startPositionMs: Long
    ) {
        TODO("Not yet implemented")
    }

    override fun setMediaItem(mediaItem: MediaItem) {
        this.mediaItem = mediaItem
        for (listener in listeners) {
            listener.onMediaItemTransition(mediaItem, MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED)
        }
    }

    override fun setMediaItem(
        mediaItem: MediaItem,
        startPositionMs: Long
    ) {
        TODO("Not yet implemented")
    }

    override fun setMediaItem(
        mediaItem: MediaItem,
        resetPosition: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun addMediaItem(mediaItem: MediaItem) {
        TODO("Not yet implemented")
    }

    override fun addMediaItem(index: Int, mediaItem: MediaItem) {
        TODO("Not yet implemented")
    }

    override fun addMediaItems(mediaItems: List<MediaItem>) {
        TODO("Not yet implemented")
    }

    override fun addMediaItems(
        index: Int,
        mediaItems: List<MediaItem>
    ) {
        TODO("Not yet implemented")
    }

    override fun moveMediaItem(currentIndex: Int, newIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun moveMediaItems(fromIndex: Int, toIndex: Int, newIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun replaceMediaItem(index: Int, mediaItem: MediaItem) {
        TODO("Not yet implemented")
    }

    override fun replaceMediaItems(
        fromIndex: Int,
        toIndex: Int,
        mediaItems: List<MediaItem>
    ) {
        TODO("Not yet implemented")
    }

    override fun removeMediaItem(index: Int) {
        TODO("Not yet implemented")
    }

    override fun removeMediaItems(fromIndex: Int, toIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun clearMediaItems() {
        TODO("Not yet implemented")
    }

    override fun isCommandAvailable(command: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun canAdvertiseSession(): Boolean {
        return true
    }

    override fun getAvailableCommands(): Player.Commands {
        TODO("Not yet implemented")
    }

    override fun prepare() {
        TODO("Not yet implemented")
    }

    override fun getPlaybackState(): Int {
        TODO("Not yet implemented")
    }

    override fun getPlaybackSuppressionReason(): Int {
        TODO("Not yet implemented")
    }

    override fun isPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPlayerError(): PlaybackException {
        TODO("Not yet implemented")
    }

    override fun play() {
        onNotification.invoke(MediaNotification(MediaCommand.PLAY))
    }

    override fun pause() {
        onNotification.invoke(MediaNotification(MediaCommand.PAUSE))
    }

    override fun setPlayWhenReady(playWhenReady: Boolean) {
    }

    override fun getPlayWhenReady(): Boolean {
        return false
    }

    override fun setRepeatMode(repeatMode: Int) {
        TODO("Not yet implemented")
    }

    override fun getRepeatMode(): Int {
        TODO("Not yet implemented")
    }

    override fun setShuffleModeEnabled(shuffleModeEnabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getShuffleModeEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isLoading(): Boolean {
        return false
    }

    override fun seekToDefaultPosition() {

    }

    override fun seekToDefaultPosition(mediaItemIndex: Int) {

    }

    override fun seekTo(positionMs: Long) {

    }

    override fun seekTo(mediaItemIndex: Int, positionMs: Long) {

    }

    override fun getSeekBackIncrement(): Long {
        return 0
    }

    override fun seekBack() {

    }

    override fun getSeekForwardIncrement(): Long {
        return 0
    }

    override fun seekForward() {
    }

    override fun hasPreviousMediaItem(): Boolean {
        return false
    }

    override fun seekToPreviousMediaItem() {
        onNotification.invoke(MediaNotification(MediaCommand.PREVIOUS_TRACK))
    }

    override fun getMaxSeekToPreviousPosition(): Long {
        return 0
    }

    override fun seekToPrevious() {
        onNotification.invoke(MediaNotification(MediaCommand.PREVIOUS_TRACK))
    }

    override fun hasNextMediaItem(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seekToNextMediaItem() {
        onNotification.invoke(MediaNotification(MediaCommand.NEXT_TRACK))
    }

    override fun seekToNext() {
        onNotification.invoke(MediaNotification(MediaCommand.NEXT_TRACK))
    }

    override fun setPlaybackParameters(playbackParameters: PlaybackParameters) {
        TODO("Not yet implemented")
    }

    override fun setPlaybackSpeed(speed: Float) {
        TODO("Not yet implemented")
    }

    override fun getPlaybackParameters(): PlaybackParameters {
        TODO("Not yet implemented")
    }

    override fun stop() {
        onNotification.invoke(MediaNotification(MediaCommand.STOP))

    }

    override fun release() {

    }

    override fun getCurrentTracks(): Tracks {
        return Tracks.EMPTY
    }

    override fun getTrackSelectionParameters(): TrackSelectionParameters {
        return TrackSelectionParameters.Builder().build()
    }

    override fun setTrackSelectionParameters(parameters: TrackSelectionParameters) {

    }

    override fun getMediaMetadata(): MediaMetadata {
        return MediaMetadata.Builder().build()
    }

    override fun getPlaylistMetadata(): MediaMetadata {
        return MediaMetadata.Builder().build()
    }

    override fun setPlaylistMetadata(mediaMetadata: MediaMetadata) {

    }

    override fun getCurrentManifest(): Any {
        return 0
    }

    override fun getCurrentTimeline(): Timeline {
        return Timeline.EMPTY
    }

    override fun getCurrentPeriodIndex(): Int {
        return 0
    }

    @Deprecated("Deprecated in Java")
    override fun getCurrentWindowIndex(): Int {
        TODO("Not yet implemented")
    }

    override fun getCurrentMediaItemIndex(): Int {
        return 0
    }

    @Deprecated("Deprecated in Java")
    override fun getNextWindowIndex(): Int {
        return 0
    }

    override fun getNextMediaItemIndex(): Int {
        return 0
    }

    @Deprecated("Deprecated in Java")
    override fun getPreviousWindowIndex(): Int {
        return 0
    }

    override fun getPreviousMediaItemIndex(): Int {
        return 0
    }

    override fun getCurrentMediaItem(): MediaItem {
        return MediaItem.Builder().build()
    }

    override fun getMediaItemCount(): Int {
        return 0
    }

    override fun getMediaItemAt(index: Int): MediaItem {
        return MediaItem.Builder().build()
    }

    override fun getDuration(): Long {
        return 0
    }

    override fun getCurrentPosition(): Long {
        return 0
    }

    override fun getBufferedPosition(): Long {
        return 0
    }

    override fun getBufferedPercentage(): Int {
        return 0
    }

    override fun getTotalBufferedDuration(): Long {
        return 0
    }

    @Deprecated("Deprecated in Java")
    override fun isCurrentWindowDynamic(): Boolean {
        return false
    }

    override fun isCurrentMediaItemDynamic(): Boolean {
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun isCurrentWindowLive(): Boolean {
        return true
    }

    override fun isCurrentMediaItemLive(): Boolean {
        return true
    }

    override fun getCurrentLiveOffset(): Long {
        return 0
    }

    @Deprecated("Deprecated in Java")
    override fun isCurrentWindowSeekable(): Boolean {
        return false
    }

    override fun isCurrentMediaItemSeekable(): Boolean {
        return false
    }

    override fun isPlayingAd(): Boolean {
        return false
    }

    override fun getCurrentAdGroupIndex(): Int {
        return 0
    }

    override fun getCurrentAdIndexInAdGroup(): Int {
        return 0
    }

    override fun getContentDuration(): Long {
        return 0
    }

    override fun getContentPosition(): Long {
        return 0
    }

    override fun getContentBufferedPosition(): Long {
        return 0
    }

    override fun getAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder().build()
    }

    override fun setVolume(volume: Float) {

    }

    override fun getVolume(): Float {
        return 0.5f
    }

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

    override fun getVideoSize(): VideoSize {
        return VideoSize.UNKNOWN
    }

    @UnstableApi
    override fun getSurfaceSize(): Size {
        return Size(0, 0)
    }

    override fun getCurrentCues(): CueGroup {
        return CueGroup.EMPTY_TIME_ZERO
    }

    override fun getDeviceInfo(): DeviceInfo {
        return DeviceInfo.Builder(PLAYBACK_TYPE_LOCAL).build()
    }

    override fun getDeviceVolume(): Int {
        return 0
    }

    override fun isDeviceMuted(): Boolean {
        return false
    }

    @Deprecated("Deprecated in Java")
    override fun setDeviceVolume(volume: Int) {


    }

    override fun setDeviceVolume(volume: Int, flags: Int) {

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

    override fun setDeviceMuted(muted: Boolean, flags: Int) {
        TODO("Not yet implemented")
    }

    override fun setAudioAttributes(
        audioAttributes: AudioAttributes,
        handleAudioFocus: Boolean
    ) {

    }

}