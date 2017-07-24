package dev.nick.music.loader;

import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Tornaco on 2017/7/23.
 * Licensed with Apache.
 */
public class MusicLoaderTest {

    @Test
    public void loadMusic() throws Exception {
        MusicLoader musicLoader = new MusicLoader();
        List<Music> list = musicLoader.loadMusic(Environment.getExternalStorageDirectory().getPath());
        Log.i("list is", String.valueOf(list));
    }

}