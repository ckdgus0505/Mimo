package alpha.mimo;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class New2Activity extends AppCompatActivity {
    private NewHandler newHandler;
    private SocketThread socketThread;
    Button saveButton;
    String ID;
    String title;
    String content;
    String fileName;
    EditText editTitle;
    EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new2);
        title = "None";
        content = "None";
        saveButton= (Button)findViewById(R.id.saveButton);
        editTitle= (EditText)findViewById(R.id.editTitle);
        editContent = (EditText)findViewById(R.id.editContent);
        Intent intent  = getIntent();
        processIntent(intent);

    }
    public void onButtonSave(View view){
        try {
            title = editTitle.getText().toString();
            content = editContent.getText().toString();
            if('0'<=title.charAt(0)&&title.charAt(0)<='9'){
                Toast.makeText(getApplicationContext(), "제목의 첫글자는 숫자가될수 없습니다. 다시입력하세요" , Toast.LENGTH_SHORT).show();
                return;
            }

            newHandler = new NewHandler();// ...
            if(title.contains(".mimm") ==false)
                socketThread = new SocketThread(ID,'j',title+".mimm",content,newHandler);
            else
                socketThread = new SocketThread(ID,'j',title, content,newHandler);
            socketThread.start();
            try{
                socketThread.join();
            }catch (Exception e){}
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK,intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonCancle(View view){
        Toast.makeText(getApplicationContext(),"파일 생성을 취소하였습니다." , Toast.LENGTH_LONG).show();
        finish();
    }
    public void processIntent(Intent intent){
        if(intent !=null){
            ID = intent.getStringExtra("ID");
            fileName = intent.getStringExtra("fileName");
        }
    }

    class NewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
