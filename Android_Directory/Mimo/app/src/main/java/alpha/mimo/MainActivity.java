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

    }

    public void showToast(final String toast) { runOnUiThread(new Runnable() { public void run() { Toast.makeText(getApplicationContext(),toast, Toast.LENGTH_SHORT).show(); } }); }

    public void onButtonNew(View view)
    {
        Intent MemoIntent = new Intent(this, NewActivity.class);
        //MemoIntent.putExtra("fileName", fileName);
        startActivityForResult(MemoIntent, 101);
    }

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
