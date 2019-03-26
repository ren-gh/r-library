
package com.rengh.library.common.player;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class PlayerParams {
    private String videoTile = null;
    private Uri videoUri = null;
    private Drawable coverDrawable = null;
    private PlayerListener playerListener;
    private Boolean isAdVideo = false;
    private Boolean doubleClick = false;
    private Boolean autoFinish = false;
    private Integer autoFinishDelay = 0;
    private Boolean showLoading = false;
    private Boolean showCenterClickToast = true;

    public String getVideoTile() {
        return videoTile;
    }

    public void setVideoTile(String sVideoTile) {
        this.videoTile = sVideoTile;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri sVideoUri) {
        this.videoUri = sVideoUri;
    }

    public Drawable getCoverDrawable() {
        return coverDrawable;
    }

    public void setCoverDrawable(Drawable sCoverDrawable) {
        this.coverDrawable = sCoverDrawable;
    }

    public PlayerListener getPlayerListener() {
        return playerListener;
    }

    public void setPlayerListener(PlayerListener sPlayerListener) {
        this.playerListener = sPlayerListener;
    }

    public Boolean isAdVideo() {
        return isAdVideo;
    }

    public void setAdVideo(Boolean sIsAdVideo) {
        this.isAdVideo = sIsAdVideo;
    }

    public Boolean getDoubleClick() {
        return doubleClick;
    }

    public void setDoubleClick(Boolean sDoubleClick) {
        this.doubleClick = sDoubleClick;
    }

    public Boolean getAutoFinish() {
        return autoFinish;
    }

    public void setAutoFinish(Boolean sAutoFinish) {
        this.autoFinish = sAutoFinish;
    }

    public Integer getAutoFinishDelay() {
        return autoFinishDelay;
    }

    public void setAutoFinishDelay(Integer sAutoFinishDelay) {
        this.autoFinishDelay = sAutoFinishDelay;
    }

    public Boolean getShowLoading() {
        return showLoading;
    }

    public void setShowLoading(Boolean sShowLoading) {
        this.showLoading = sShowLoading;
    }

    public Boolean getShowCenterClickToast() {
        return showCenterClickToast;
    }

    public void setShowCenterClickToast(Boolean sShowCenterClickToast) {
        this.showCenterClickToast = sShowCenterClickToast;
    }
}
