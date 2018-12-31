package alpha.mimo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewActivity extends AppCompatActivity {
    Button saveButton;
    String title;
    String content;
    EditText editTitle;
    EditText editContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        title = "None";
        content = "None";
        saveButton= (Button)findViewById(R.id.saveButton);
        editTitle= (EditText)findViewById(R.id.editTitle);
        editContent = (EditText)findViewById(R.id.editContent);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    title = editTitle.getText().toString();
                    content = editContent.getText().toString();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + title + ".mimm", false));
                    bw.write(content);
                    bw.close();
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK,intent);
                    finish();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

            }
        });
    }
}
