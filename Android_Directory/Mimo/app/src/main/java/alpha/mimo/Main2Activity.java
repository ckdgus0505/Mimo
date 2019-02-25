package alpha.mimo;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class Main2Activity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView memoListView;
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

//        try {
//            clientSocket = new Socket(ip, port);
//            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        myHandler = new MyHandler();
        myThread = new MyThread();
        myThread.start();
//
//        Intent intent  = getIntent();
//        String ID =processIntent(intent);
//        socketOut.println(ID);
        Log.d("SAMPLEHTTP", "send C");

        buttonSend = (Button) findViewById(R.id.buttonSend);
        //= (TextView) findViewById(R.id.tv);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketOut.println("c");
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
    private String processIntent(Intent intent){
        if(intent !=null){
            Bundle bundle = intent.getExtras();
            String ID = (String)bundle.getString("ID");
            return ID;
        }
        return "FALSE";
    }
    class MyThread extends Thread {
        @Override
        public void run() {

                try {
                    clientSocket = new Socket(ip, port);
                    socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
                    Intent intent  = getIntent();
                    String ID =processIntent(intent);
                    socketOut.println(ID);
                    this.sleep(5000);
                    socketOut.println("c");
                    this.sleep(5000);
                    String data = socketIn.readLine();
                    fileList(data);
                    Log.d("SAMPLEHTTP", data+" hi");
//                    data = socketIn.readLine();
//                    Log.d("SAMPLEHTTP", data+" hi");
//                    data = socketIn.readLine();
//                    Log.d("SAMPLEHTTP", data+" hi");
//                    // InputStream의 값을 읽어와서 data에 저장
                    //String data = socketIn.readLine();
                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
                    //Message msg = new Message(); ;//= myHandler.obtainMessage();
                    //msg.obj = "c";
                    //myHandler.sendMessage(msg);
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


//        public void sync(String data){
//            try{
//                items = new ArrayList<String>();
//                adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_single_choice, items);
//                memoListView = (ListView) findViewById(R.id.MemoListView);
//                memoListView.setAdapter(adapter);
//                memoListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//                //directory = getFilesDir().getParentFile();
//                //files = directory.listFiles();
//                //Log.d("Files", "Size: "+ files.length);
//                String temp = new String(data);
//                String stringnum=" ";
//                int si_filename = 0; //start index of filename;
//                char a= ' ';
//                for(int i=0;i<temp.length();i++){
//                    a= temp.charAt(i);
//                    if(a<'0' && '9'<a){
//                        stringnum = temp.substring(0,i);
//                        si_filename = i;
//                        break;
//                    }
//                }
//                 int num =    Integer.parseInt(stringnum);
//                Log.d("SAMPLEHTTP", "num/si_filename="+num+ "/"+si_filename);
//                //indexOf(String str, int fromIndex)
//                //Returns the index within this string of the first occurrence of the specified substring, starting at the specified index
//
//                int index_mimm;
//                int s_index=si_filename;
//
//                index_mimm = temp.indexOf(".mimm",s_index);
//                if(index_mimm ==-1) {
//                    return;// 끝내기.
//                }
//                while(true){
//                    index_mimm = temp.indexOf(".mimm",s_index);
//                }
//
//                for (int i = si_filename; i < temp.length(); i++) {
//                    //String str = files[i].getName();
//
//                    if (str.contains(".mimm")) {
//                        str = str.substring(5);
//                        items.add(str);
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }catch(Exception e){
//                e.printStackTrace();
//                makeText(this, e.getMessage(), LENGTH_LONG).show();
//            }
//        }

        public void fileList(String data){
            String temp = new String(data);
            String stringnum=" ";
            int si_filename = 0; //start index of filename;
            char a= ' ';
            for(int i=0;i<temp.length();i++){
                a= temp.charAt(i);
                if(a<'0' || '9'<a){
                    stringnum = temp.substring(0,i);
                    si_filename = i;
                    break;
                }
            }
            //System.out.println(stringnum);
            int num =    Integer.parseInt(stringnum);
            // System.out.println("num/si_filename="+ num+"/"+si_filename);
            //Log.d("SAMPLEHTTP", "num/si_filename="+num+ "/"+si_filename);
            //indexOf(String str, int fromIndex)
            //Returns the index within this string of the first occurrence of the specified substring, starting at the specified index


            items = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_single_choice, items);
            memoListView = (ListView) findViewById(R.id.MemoListView);
            memoListView.setAdapter(adapter);
            memoListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            int index_mimm;
            int s_index=si_filename;

            index_mimm = temp.indexOf(".mimm",s_index);
            if(index_mimm ==-1) {
                adapter.notifyDataSetChanged();
                return;// 끝내기.
            }
            items.add(temp.substring(s_index,index_mimm));
//            System.out.println(temp.substring(s_index,index_mimm));
            index_mimm +=5;//pass .mimm
            s_index = index_mimm;

            while(true) {
                index_mimm = temp.indexOf(".mimm",s_index);
                if(index_mimm ==-1) {
                    adapter.notifyDataSetChanged();
                    break;// 끝내기.
                }
                items.add(temp.substring(s_index,index_mimm));
                System.out.println(temp.substring(s_index,index_mimm));
                index_mimm +=5;//pass .mimm
                s_index = index_mimm;
            }
        }
    }

    class MyHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            tv.setText(msg.obj.toString());
//        }
    }


}
