package com.doganesad;

import androidx.annotation.NonNull;

public class Music {
    String songName,artistName,musicUrl,coverUrl;
    int musicId;

    public Music(String songName, String artistName, String musicUrl, String coverUrl, int musicId) {
        this.songName = songName;
        this.artistName = artistName;
        this.musicUrl = musicUrl;
        this.coverUrl = coverUrl;
        this.musicId = musicId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getcoverUrl() {
        return coverUrl;
    }

    public void setcoverUrl(String imageUrl) {
        this.coverUrl = imageUrl;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    @NonNull
    @Override
    public String toString() {
        return songName+" "+artistName;
    }
}
