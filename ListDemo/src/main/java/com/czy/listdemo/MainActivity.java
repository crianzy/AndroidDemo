package com.czy.listdemo;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    ListView mListView;
    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new MyAdapter(this));

        mMediaPlayer = new MediaPlayer();

        try {
            mMediaPlayer.addTimedTextSource("", MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaPlayer.TrackInfo[] trackInfos = mMediaPlayer.getTrackInfo();

        if (trackInfos != null && trackInfos.length > 0) {
            for (int i = 0; i < trackInfos.length; i++) {
                final MediaPlayer.TrackInfo info = trackInfos[i];

                LogUtil.w(TAG, "TrackInfo: " + info.getTrackType() + " "
                        + info.getLanguage());

                if (info.getTrackType() == MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_AUDIO) {
                    // mMediaPlayer.selectTrack(i);
                } else if (info.getTrackType() == MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT) {
                    mMediaPlayer.selectTrack(i);
                }
            }
        }
        mMediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(MediaPlayer mp, TimedText text) {
                if (text != null) {
                    LogUtil.d(TAG, "text = " + text.getText());
                }
            }
        });

    }


    class MyAdapter extends BaseAdapter {
        private List<String> mStringList = new ArrayList<>();
        Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
            for (int i = 0; i < 1000; i++) {
                mStringList.add("String - " + i);
            }

        }


        @Override
        public int getCount() {
            return mStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return mStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
                holder = new Holder();
                holder.mTextView = (TextView) convertView.findViewById(R.id.txt);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.mTextView.setText(mStringList.get(position));
            return convertView;
        }

        class Holder {
            TextView mTextView;
        }

    }
}
