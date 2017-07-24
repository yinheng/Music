package dev.nick.music.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tornaco on 2017/7/23.
 * Licensed with Apache.
 */

public class MusicLoader {

    List<Music> musicList = new ArrayList();

    public List<Music> loadMusic(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return musicList;
        }
        if (file.isFile()) {
            if (file.getName().endsWith(".mp3")) {
                Music music = new Music();
                music.setMusicPath(file.getPath());
                music.setMusicName(file.getName());
                musicList.add(music);
            } else {
                System.out.println("fiie is not music file");
            }
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    loadMusic(subFile.getPath());
                }

            }
        }
        return musicList;
    }
}
