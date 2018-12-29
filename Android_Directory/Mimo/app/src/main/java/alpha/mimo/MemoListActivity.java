package alpha.mimo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MemoListActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView memoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        items = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(MemoListActivity.this, android.R.layout.simple_list_item_single_choice, items);

        memoListView = (ListView) findViewById(R.id.MemoListView);
        memoListView.setAdapter(adapter);
        memoListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public void onButtonNew(View view)
    {
        EditText ed = (EditText) findViewById(R.id.fileText);
        String text = ed.getText().toString();

        if (!text.isEmpty()) {
            items.add(text);
            ed.setText("");
            adapter.notifyDataSetChanged();
        }
    }

    public void onButtonOpen(View view)
    {

    }

    public void onButtonDelete(View view)
    {
        int pos = memoListView.getCheckedItemPosition();

        if (pos != memoListView.INVALID_POSITION) {
            items.remove(pos);
            memoListView.clearChoices();
            adapter.notifyDataSetChanged();
        }
    }
}
