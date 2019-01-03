package alpha.mimo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView memoListView;
    String fileName;
    File directory;
    File[] files;
    File objectFile;
    Socket sock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sync();
        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                fileName = items.get(position).toString();
                Toast.makeText(getApplicationContext(),items.get(position).toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onButtonConnect(View view)
    {
        Log.d("myerror", "result:");
        try{
            Log.d("myerror", "result:1");
            sock = new Socket("58.228.40.209", 1154);
            Log.d("myerror", "result:2");
            boolean tf = sock.isConnected();
            Log.d("myerror", "result:3"+tf);
            Toast.makeText(this, "result : " + tf, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.d("myerror", "result:-1");
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
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
            Toast.makeText(getApplicationContext(), "선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
