package alpha.mimo;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private MyHandler myHandler;
    private DeleteHandler deleteHandler;
    private  SocketThread socketThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent  = getIntent();
        processIntent(intent);

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
        deleteHandler = new DeleteHandler();

        socketThread = new SocketThread(ID,'c',null,null,myHandler);
        socketThread.start();
                try {
            socketThread.join();	// th1의 작업이 끝날 때까지 기다린다
        } catch(InterruptedException e) {}

        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                fileName = items.get(position).toString();
                makeText(getApplicationContext(),items.get(position).toString(), LENGTH_LONG).show();
            }
        });
    }

    public void onButtonNew(View view)
    {
        Intent MemoIntent = new Intent(this, New2Activity.class);
        MemoIntent.putExtra("fileName", fileName);
        MemoIntent.putExtra("ID", ID);
        startActivityForResult(MemoIntent, 101);
    }
    public void onButtonDelete(View view)
    {
        socketThread = new SocketThread(ID,'d',fileName,null,deleteHandler);
        socketThread.start();
        try {
            socketThread.join();	// th1의 작업이 끝날 때까지 기다린다
        } catch(InterruptedException e) {}
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

        }

        else {
            makeText(getApplicationContext(), "선택되지 않았습니다.", LENGTH_SHORT).show();
        }
    }

    private void processIntent(Intent intent){
        if(intent !=null){
            ID = intent.getStringExtra("ID");
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            items.add(msg.obj.toString());
            adapter.notifyDataSetChanged();
        }
    }
    class DeleteHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "서버로부터 응답:"+msg.obj.toString(), Toast.LENGTH_LONG).show();
            int pos = memoListView.getCheckedItemPosition();
            items.remove(pos);
            adapter.notifyDataSetChanged();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);/// result code sms ok ...
        if(requestCode == 101||requestCode == 102){
            socketThread = new SocketThread(ID,'c',null,null,myHandler);
            socketThread.start();
            try {
                socketThread.join();	// th1의 작업이 끝날 때까지 기다린다
            } catch(InterruptedException e) {}
        }
    }
}