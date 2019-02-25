package alpha.mimo;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class Main2Activity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView memoListView;
    Button btn;
    TextView tv;
    Button buttonSend;
    String fileName;
    String ID;

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

        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_single_choice, items);
        memoListView = (ListView) findViewById(R.id.MemoListView);
        memoListView.setAdapter(adapter);
        memoListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // StrictMode는 개발자가 실수하는 것을 감지하고 해결할 수 있도록 돕는 일종의 개발 툴
        // - 메인 스레드에서 디스크 접근, 네트워크 접근 등 비효율적 작업을 하려는 것을 감지하여
        //   프로그램이 부드럽게 작동하도록 돕고 빠른 응답을 갖도록 함, 즉  Android Not Responding 방지에 도움
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        myHandler = new MyHandler();
        myThread = new MyThread();
        myThread.start();
        try {
            myThread.join();	// th1의 작업이 끝날 때까지 기다린다
        } catch(InterruptedException e) {}

        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                fileName = items.get(position).toString();
                makeText(getApplicationContext(),items.get(position).toString(), LENGTH_LONG).show();
            }
        });
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
    }

    public void onButtonOpen(View view)
    {
        int pos = memoListView.getCheckedItemPosition();

        if (pos != memoListView.INVALID_POSITION) {

            // ==== 화면 전환 ====
            Intent intent = new Intent(this, Open2Activity.class);
            intent.putExtra("file", fileName);
            intent.putExtra("ID", ID);
            startActivityForResult(intent, 102);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish(); //test
        }

        else {
            makeText(getApplicationContext(), "선택되지 않았습니다.", LENGTH_SHORT).show();
        }
    }

    private String processIntent(Intent intent){
        if(intent !=null){
            Bundle bundle = intent.getExtras();
            ID = (String)bundle.getString("ID");
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
                    this.sleep(500);
                    socketOut.println("c");
                    //this.sleep(5000);
                    String data = socketIn.readLine();
                    Log.d("SAMPLEHTTP", data+" hi");
                    fileList(data);
                    socketIn.close();
                    socketOut.close();
                    clientSocket.close();
                }
                catch (Exception e) {
                    Log.d("SAMPLEHTTP","ERROR");
                    e.printStackTrace();
                }
        }
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

            int index_mimm;
            int s_index=si_filename;

            index_mimm = temp.indexOf(".mimm",s_index);
            if(index_mimm ==-1) {
                adapter.notifyDataSetChanged();
                return;// 끝내기.
            }
            index_mimm +=5;//pass .mimm
            Message msg = myHandler.obtainMessage();
            msg.obj = temp.substring(s_index,index_mimm);
            myHandler.sendMessage(msg);
//            System.out.println(temp.substring(s_index,index_mimm));

            s_index = index_mimm;

            while(true) {
                index_mimm = temp.indexOf(".mimm",s_index);
                if(index_mimm ==-1) {
                    break;// 끝내기.
                }
                index_mimm +=5;
                msg = myHandler.obtainMessage();
                msg.obj = temp.substring(s_index,index_mimm);
                myHandler.sendMessage(msg);
                ;//pass .mimm
                s_index = index_mimm;
            }
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            items.add(msg.obj.toString());
            adapter.notifyDataSetChanged();
        }
    }


}
