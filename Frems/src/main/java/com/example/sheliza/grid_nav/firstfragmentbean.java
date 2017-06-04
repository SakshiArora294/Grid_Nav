package com.example.sheliza.grid_nav;

/**
 * Created by Sheliza on 14-05-2017.
 */

public class firstfragmentbean {

        int image;
        String text;

    public firstfragmentbean() {
    }

    public firstfragmentbean(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "firstfragmentbean{" +
                "image=" + image +
                ", text='" + text + '\'' +
                '}';
    }
}


