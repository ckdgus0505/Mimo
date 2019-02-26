package alpha.mimo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Open2Activity extends AppCompatActivity {
    private int port = 1154;
    private final String ip = "58.120.197.22";
    SocketThread socketThread;
    private OpenHandler openHandler;
//    private OpenThread openThread;


    TextView title;
    String fileName;
    String ID;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open2);

        // ==== List에서 File Name 받아오기 ===
        Intent ListIntent = getIntent();
        fileName = ListIntent.getStringExtra("file");

        et = (EditText) findViewById(R.id.mimoText);
        title = (TextView)findViewById(R.id.title);
        title.setText(fileName);

        // 분기점.

        openHandler = new OpenHandler();
        Intent intent  = getIntent();
        processIntent(intent);
        socketThread = new SocketThread(ID,'a',fileName,null,openHandler);
        socketThread.start();
        try {
            socketThread.join();	// th1의 작업이 끝날 때까지 기다린다
        } catch(InterruptedException e) {

        }

    }
    private void processIntent(Intent intent){
        if(intent !=null){
            ID = intent.getStringExtra("ID");
        }
    }

    class OpenHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            et.setText(msg.obj.toString());
            et.setTextSize(20);
            et.setTextColor(Color.BLACK);
        }
    }


    public void onButtonSync(View view) {
        socketThread = new SocketThread(ID,'j',fileName,String.valueOf(et.getText()),null);
        socketThread.start();
    }
    public void onButtonBack(View view) {
        finish();
    }
}
