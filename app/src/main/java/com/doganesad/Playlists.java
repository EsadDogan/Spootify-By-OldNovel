package com.doganesad;

public class Playlists {
    private String text;
    private String imageUrl;
    private String playlistID;
    private int recViewID;

    public Playlists(String text, String image, String playlistID, int recViewID)
     {
        this.text = text;
        this.imageUrl = image;
        this.playlistID=playlistID;
        this.recViewID = recViewID;
    }

    public String getPlaylistID() {
        return playlistID;
    }

    public int getRecViewID() {
        return recViewID;
    }

    public void setRecViewID(int recViewID) {
        this.recViewID = recViewID;
    }

    public void setPlaylistID(String playlistID) {
        this.playlistID = playlistID;
    }

    public Playlists() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }
}
