package alpha.mimo;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView memoListView;
    String fileName;
    File directory;
    File[] files;
    File objectFile;
    Socket sock;
    int port;
    EditText inputOrder;
    EditText inputFileName;
    EditText inputUrl;
    Button buttonSend;
    BufferedReader networkReader;
    BufferedWriter networkWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sync();
        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                fileName = items.get(position).toString();
                makeText(getApplicationContext(),items.get(position).toString(), LENGTH_LONG).show();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        buttonSend = (Button)findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFileName = (EditText) findViewById(R.id.inputFileName);
                if (inputFileName.getText().toString() != null
                        || !inputFileName.getText().toString().equals("")) {
                    PrintWriter out = new PrintWriter(networkWriter,true);

                    String return_msg = inputFileName.getText().toString();
                    Log.d("MainActivity", "통과return_msg"+return_msg.substring(0,return_msg.length()-1));
                    out.println(return_msg.substring(0,return_msg.length()));
                }
            }
        });


    }

    public void onButtonConnect(View view)
    {
        inputUrl = (EditText)findViewById(R.id.inputUrl) ;
        String addr = inputUrl.getText().toString().trim();
        ConnectThread thread = new ConnectThread(addr);
        thread.start();
//        port =1154;
//        Log.d("myerror", "result:");
//        try{
//            Log.d("myerror", "result:1");
//            sock = new Socket("58.228.40.209", port); // test...
//            Log.d("myerror", "result:2");
//            boolean tf = sock.isConnected();
//            Log.d("myerror", "result:3"+tf);
//            Toast.makeText(this, "result : " + tf, Toast.LENGTH_LONG).show();
//        }catch (Exception e){
//            Log.d("myerror", "result:-1");
//            e.printStackTrace();
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
    }

    class ConnectThread extends Thread {
        String hostname;

        public ConnectThread(String addr) {
            hostname = addr;
        }

        public void run() {

            try {
                String ID = "abc";

                inputOrder= (EditText)findViewById(R.id.inputOrder);
                inputFileName= (EditText)findViewById(R.id.inputFileName);
                String testValue;
                Log.d("MainActivity", "통과");
                int port = 1154;

                Socket sock = new Socket(hostname, port);
                DataOutputStream dout=new DataOutputStream(sock.getOutputStream());
                DataInputStream din=new DataInputStream(sock.getInputStream());
                networkReader= new BufferedReader(new InputStreamReader(sock.getInputStream()));
                networkWriter = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                //String testValue = "a";
                testValue = "abc";
                dout.writeUTF(testValue);
                dout=new DataOutputStream(sock.getOutputStream());
                testValue= inputOrder.getText().toString();
                dout.writeUTF(testValue);
                Log.d("MainActivity", "통과2");
                //dout.writeUTF(testValue);

                //dout.flush();
                Log.d("MainActivity", "통과3");
                showToast("다운로드받을 파일이름을 입력하세요.");
                //Toast.makeText(getApplicationContext(), "다운로드 받을 파일이름을 입력하세요!", LENGTH_SHORT).show();
                //Toast.makeText(this, "다운로드 받을 파일이름을 입력하세요!", Toast.LENGTH_LONG).show();
                Log.d("MainActivity", "통과4");
                String filename = networkReader.readLine();
                Log.d("MainActivity", "통과5" + filename);

                Log.d("MainActivity", "통과6");
                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                String obj = (String) instream.readObject();
                Log.d("MainActivity", "통과서버에서 받은 메시지 : " + obj);

                dout.close();
                //PrintWriter out = new PrintWriter(sock.getOutputStream());
                //out.print("a");
                //out.print(encodeValue);

                //BufferedWriter in = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "EUC_KR"));
                //in.readLine();
                //BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                //ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                //outstream.writeObject(testValue);// test
                //outstream.flush();

                //byte[] encodeValue = Base64.encode(testValue.getBytes(), Base64.DEFAULT);
                //String str = new String(DatatypeConverter.parseBase64Binary("user:123"));


                Log.d("MainActivity", "Passing encodeValue = " + testValue.toString());

                Log.d("MainActivity", "통과2");
 //               ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
//                String obj = (String) instream.readObject();
////
///               Log.d("MainActivity", "서버에서 받은 메시지 : " + obj);

                sock.close();

            } catch(Exception ex) {
                ex.printStackTrace();
            }

        }
    }
    public void showToast(final String toast) { runOnUiThread(new Runnable() { public void run() { Toast.makeText(getApplicationContext(),toast, Toast.LENGTH_SHORT).show(); } }); }



    public void onButtonNew(View view)
    {
        Intent MemoIntent = new Intent(this, NewActivity.class);
        //MemoIntent.putExtra("fileName", fileName);
        startActivityForResult(MemoIntent, 101);
    }
//    public void onButtonSend(View view)
//    {
//        inputFileName = (EditText) findViewById(R.id.inputFileName);
//        if (inputFileName.getText().toString() != null
//
//                || !inputFileName.getText().toString().equals("")) {
//            PrintWriter out = new PrintWriter(networkWriter,true);
//
//            String return_msg = inputFileName.getText().toString();
//            out.println(return_msg);
//
//        }
//    }

    public void onButtonOpen(View view)
    {
        int pos = memoListView.getCheckedItemPosition();

        if (pos != memoListView.INVALID_POSITION) {

            // ==== 화면 전환 ====
            Intent MemoIntent = new Intent(this, OpenActivity.class);
            MemoIntent.putExtra("file", fileName);
            startActivityForResult(MemoIntent, 102);
        }

        else {
            makeText(getApplicationContext(), "선택되지 않았습니다.", LENGTH_SHORT).show();
        }
}

    public void onButtonDelete(View view)
    {
        try {
            int pos = memoListView.getCheckedItemPosition();

            if (pos != memoListView.INVALID_POSITION) {
                directory = getFilesDir().getParentFile();
                files = directory.listFiles();
                String realName = "files" + fileName;

                File deleteFile = new File(getFilesDir().getParentFile(),"files" +fileName);
                deleteFile.delete();
                items.remove(pos);
                memoListView.clearChoices();
                sync();
            }
        }catch (Exception e){
            e.printStackTrace();
            makeText(this, e.getMessage(), LENGTH_LONG).show();
        }

        // 파일 삭제 하는 부분 추가해야 함
    }

    public void sync(){
        try{
            items = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_single_choice, items);
            memoListView = (ListView) findViewById(R.id.MemoListView);
            memoListView.setAdapter(adapter);
            memoListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            directory = getFilesDir().getParentFile();
            files = directory.listFiles();
            Log.d("Files", "Size: "+ files.length);
            for (int i = 0; i < files.length; i++) {
                String str = files[i].getName();
                if (str.contains(".mimm")) {
                    str = str.substring(5);
                    items.add(str);
                }
                adapter.notifyDataSetChanged();
            }
        }catch(Exception e){
            e.printStackTrace();
            makeText(this, e.getMessage(), LENGTH_LONG).show();
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);/// result code sms ok ...
        if(requestCode == 101||requestCode == 102){
            sync();
            //String name =data.getStringExtra("name"); // like hash table...10
            //Toast.makeText(getApplicationContext(), "메뉴화면으로부터 응답:"+name, Toast.LENGTH_LONG).show();
        }
    }
}
