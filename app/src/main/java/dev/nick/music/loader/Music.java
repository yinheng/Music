package dev.nick.music.loader;

/**
 * Created by Tornaco on 2017/7/23.
 * Licensed with Apache.
 */

public class Music {
    String musicName;
    String singer;
    String musicPath;

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    @Override
    public String toString() {
        return "Music{" +
                "musicName='" + musicName + '\'' +
                ", singer='" + singer + '\'' +
                ", musicPath='" + musicPath + '\'' +
                '}';
    }
}
