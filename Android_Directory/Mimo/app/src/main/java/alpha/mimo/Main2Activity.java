package alpha.mimo;

import android.os.StrictMode;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main2Activity extends AppCompatActivity {
    Button btn;
    TextView tv;
    Button buttonSend;

    //  TCP연결 관련
    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 1154;
    private final String ip = "58.120.197.22";
    private MyHandler myHandler;
    private MyThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // StrictMode는 개발자가 실수하는 것을 감지하고 해결할 수 있도록 돕는 일종의 개발 툴
        // - 메인 스레드에서 디스크 접근, 네트워크 접근 등 비효율적 작업을 하려는 것을 감지하여
        //   프로그램이 부드럽게 작동하도록 돕고 빠른 응답을 갖도록 함, 즉  Android Not Responding 방지에 도움
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            clientSocket = new Socket(ip, port);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        myHandler = new MyHandler();
        myThread = new MyThread();
        myThread.start();
        socketOut.print("c");

        buttonSend = (Button) findViewById(R.id.buttonSend);
        //= (TextView) findViewById(R.id.tv);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketOut.print("c");
                Log.d("SAMPLEHTTP", "CLICK");
            }
        });


        //btn = (Button) findViewById(R.id.btn);
        //tv = (TextView) findViewById(R.id.tv);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                socketOut.println(123);
//            }
//        });
    }
    class MyThread extends Thread {
        @Override
        public void run() {

                try {
                    Log.d("SAMPLEHTTP", "SEND? C??????");
                    // InputStream의 값을 읽어와서 data에 저장
                    //String data = socketIn.readLine();
                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
                    Message msg = new Message(); ;//= myHandler.obtainMessage();
                    msg.obj = "c";
                    myHandler.sendMessage(msg);
                    Log.d("SAMPLEHTTP", "SEND? C?");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }


//            while (true) {
//                try {
//                    // InputStream의 값을 읽어와서 data에 저장
//                    String data = socketIn.readLine();
//                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
//                    Message msg = myHandler.obtainMessage();
//                    msg.obj = data;
//                    myHandler.sendMessage(msg);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    class MyHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            tv.setText(msg.obj.toString());
//        }
    }


}
