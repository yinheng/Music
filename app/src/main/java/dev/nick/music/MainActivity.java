package dev.nick.music;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import dev.nick.music.loader.Music;
import dev.nick.music.loader.MusicLoader;

public class MainActivity extends AppCompatActivity {
    List<Music> musicList;

    MediaPlayer mediaPlayer = new MediaPlayer();

    ListView listView;

    ImageButton prevBtn, playBtn, nextBtn;

    BaseAdapter adapter;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAndDisplay();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "item:" + (i + 1), Toast.LENGTH_SHORT).show();
                play(i);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playNext();
            }
        });


        playBtn = (ImageButton) findViewById(R.id.imageButton2);
        nextBtn = (ImageButton) findViewById(R.id.imageButton3);
        prevBtn = (ImageButton) findViewById(R.id.imageButton);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                } else {
                    mediaPlayer.start();
                    playBtn.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index == 0) {
                    index = musicList.size() - 1;
                } else {
                    index = index - 1;
                }
                play(index);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNext();
            }
        });


        loadAndDisplay();
    }

    private void playNext() {
        if (index == musicList.size() - 1) {
            index = 0;
        } else {
            index += 1;
        }
        play(index);
    }

    private void play(int i) {
        index = i;
        String path = musicList.get(i).getMusicPath();
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.reset();

            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();

            playBtn.setImageResource(R.drawable.ic_pause_black_24dp);
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void loadAndDisplay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MusicLoader musicLoader = new MusicLoader();
                musicList = musicLoader.loadMusic(Environment.getExternalStorageDirectory().getPath());
                Log.i("yinheng", "item:" + musicList.size());
                Iterator<Music> iterator = musicList.iterator();
                while (iterator.hasNext()) {
                    Music m = iterator.next();
                    Log.i("yinheng", m.toString());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onLoadComplete();
                    }
                });
            }
        }).start();
    }

    private void onLoadComplete() {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setRefreshing(false);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        adapter = new MusicAdapter();
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

    }

    class MusicAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return musicList.size();
        }

        @Override
        public Object getItem(int i) {
            return musicList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View itemView = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.item, viewGroup, false);

            TextView line1 = itemView.findViewById(R.id.textView);
            TextView line2 = itemView.findViewById(R.id.textView2);

            Music music = musicList.get(position);

            line1.setText(music.getMusicName());
            line2.setText(music.getMusicPath());

            if (index == position) {
                itemView.setBackgroundColor(Color.GREEN);
            }

            return itemView;
        }
    }
}
