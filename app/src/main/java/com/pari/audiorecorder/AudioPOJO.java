package com.pari.audiorecorder;

public class AudioPOJO {

    private String path;
    private String name;

    public AudioPOJO(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }


}
