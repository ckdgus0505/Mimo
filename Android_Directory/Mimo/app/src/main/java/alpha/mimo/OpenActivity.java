package alpha.mimo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OpenActivity extends AppCompatActivity {
    TextView title;
    String FileName;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        // ==== List에서 File Name 받아오기 ====
        Intent ListIntent = getIntent();
        FileName = ListIntent.getStringExtra("file");


        et = (EditText) findViewById(R.id.mimoText);
        title = (TextView)findViewById(R.id.title);
        title.setText(FileName);

        try {
            String dyStr = "";

            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + FileName));
            String str = null;
            while ((str = br.readLine()) != null)
                dyStr += str + "\n";
            br.close();
            et.setText(dyStr);
            et.setTextSize(20);
            et.setTextColor(Color.BLACK);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonSync(View view) {
        try {
            String dyStr = "";

            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + FileName, false));
            dyStr = String.valueOf(et.getText());
            bw.write(dyStr);
            bw.close();

            Toast.makeText(getApplicationContext(), "동기화 완료", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
