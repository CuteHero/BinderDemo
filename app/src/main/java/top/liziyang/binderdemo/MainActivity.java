package top.liziyang.binderdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 进程内Binder使用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MusicService musicService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.text_play)).setOnClickListener(this);
        (findViewById(R.id.text_pause)).setOnClickListener(this);

        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService = ((MusicService.MusicBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicService = null;
            }
        }, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_play:
                musicService.play();
                // 播放之后finish掉当前的Activity 后台应该继续播放
                finish();
                break;
            case R.id.text_pause:
                musicService.pause();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
