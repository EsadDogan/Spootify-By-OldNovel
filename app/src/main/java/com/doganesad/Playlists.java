package com.doganesad;

public class Playlists {
    private String text;
    private String imageUrl;

    public Playlists(String text, String image) {
        this.text = text;
        this.imageUrl = image;
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
