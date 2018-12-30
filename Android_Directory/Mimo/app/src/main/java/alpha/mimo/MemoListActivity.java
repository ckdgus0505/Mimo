package alpha.mimo;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class MemoListActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView memoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        try{
            items = new ArrayList<String>();

            adapter = new ArrayAdapter<String>(MemoListActivity.this, android.R.layout.simple_list_item_single_choice, items);

            memoListView = (ListView) findViewById(R.id.MemoListView);
            memoListView.setAdapter(adapter);
            memoListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            File directory2 = getFilesDir().getParentFile();

            File[] files2 = directory2.listFiles();
            Log.d("Files2", "Size: "+ files2.length);
            for (int i = 0; i < files2.length; i++)
            {
                String str = files2[i].getName();
                if(str.contains(".mimm"))
                    items.add(files2[i].getName());
                adapter.notifyDataSetChanged();
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),items.get(position).toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onButtonNew(View view)
    {
        // ==== ListView 수정 부분 ====
        EditText ed = (EditText) findViewById(R.id.fileText);
        String text = ed.getText().toString();

        if (!text.isEmpty()) {
            items.add(text);
            ed.setText("");
            adapter.notifyDataSetChanged();
        }

        // ==== mimm 확장자 수정 부분 ====
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + text + ".mimm", false));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonOpen(View view)
    {
        int pos = memoListView.getCheckedItemPosition();

        if (pos != memoListView.INVALID_POSITION) {

            // ==== 화면 전환 ====
            Intent MemoIntent = new Intent(this, MemoActivity.class);
            MemoIntent.putExtra("file", items.get(pos).toString());
            startActivity(MemoIntent);
        }

        else {
            Toast.makeText(getApplicationContext(), "선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonDelete(View view)
    {
        int pos = memoListView.getCheckedItemPosition();

        if (pos != memoListView.INVALID_POSITION) {
            items.remove(pos);
            memoListView.clearChoices();
            adapter.notifyDataSetChanged();
        }

        // 파일 삭제 하는 부분 추가해야 함
    }
}
