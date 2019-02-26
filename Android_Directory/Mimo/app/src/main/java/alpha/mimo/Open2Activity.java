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
    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 1154;
    private final String ip = "58.120.197.22";
    private OpenHandler openHandler;
    private OpenThread openThread;
    Button buttonSync;

    TextView title;
    String FileName;
    String ID;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open2);
        Log.d("SAMPLEHTTP","OPEN2");

        // ==== List에서 File Name 받아오기 ===
        Intent ListIntent = getIntent();
        FileName = ListIntent.getStringExtra("file");

        et = (EditText) findViewById(R.id.mimoText);
        title = (TextView)findViewById(R.id.title);
        title.setText(FileName);


        // 분기점.

        openHandler = new OpenHandler();
        openThread = new OpenThread('a');
        openThread.start();
        try {
            openThread.join();	// th1의 작업이 끝날 때까지 기다린다
        } catch(InterruptedException e) {}

        buttonSync = (Button)findViewById(R.id.buttonSync);
        buttonSync.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openHandler = new OpenHandler();
                openThread = new OpenThread('j');
                openThread.start();
            }
        });



    }
    private String processIntent(Intent intent){
        if(intent !=null){
            ID = intent.getStringExtra("ID");
            return ID;
        }
        return "FALSE";
    }


    class OpenThread extends Thread {
        char ch;
        public OpenThread(char ch) {
            this.ch = ch;
        }

        @Override
        public void run() {

            try {
                clientSocket = new Socket(ip, port);
                socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
                Intent intent  = getIntent();
                String ID =processIntent(intent);

                StringBuilder sb= new StringBuilder();
                String str =null;

                socketOut.println(ID);
                this.sleep(500);
                if(this.ch =='a'){
                    socketOut.println("a");
                    Log.d("SAMPLEHTTP","a");
                    this.sleep(500);
                    socketOut.println(FileName);
                    while ((str=socketIn.readLine()) != null){
                        sb.append(str+"\n");
                        Log.d("SAMPLEHTTP","INPUT="+ str);
                    }
                    Message msg = openHandler.obtainMessage();
                    msg.obj = sb.toString();
                    openHandler.sendMessage(msg);
                }
                else if(this.ch =='j'){
                    socketOut.println("j");
                    Log.d("SAMPLEHTTP","j");
                    this.sleep(500);
                    socketOut.println(FileName);
                    this.sleep(500);

                    // 달라...
                    str = String.valueOf(et.getText());
                    socketOut.println(str);
                    this.sleep(500);
                }

                Log.d("SAMPLEHTTP","SUCCESS");

                socketIn.close();
                socketOut.close();
                clientSocket.close();
            }
            catch (Exception e) {
                Log.d("SAMPLEHTTP","what's wrong?");
                e.printStackTrace();
            }
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
        try {

//            String dyStr = "";
//
//            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + FileName, false));
//            dyStr = String.valueOf(et.getText());
//            bw.write(dyStr);
//            bw.close();

            Toast.makeText(getApplicationContext(), "동기화 완료", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
