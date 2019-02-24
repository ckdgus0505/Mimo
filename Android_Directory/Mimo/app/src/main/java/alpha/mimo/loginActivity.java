package alpha.mimo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;

public class loginActivity extends AppCompatActivity {
    TextView inputID;
    TextView inputPW;

    Button test;

    Button login;
    String testphp;
    URLConnector task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testphp = "http://58.120.197.22/helloworld.php";
                task = new URLConnector(testphp);
                Log.d("HELP", "WAITING0");
                task.start();
                try{
                    task.join();
                    Log.d("HELP", "WAITING1");
                    Toast.makeText(getApplicationContext(), "waiting... for result", Toast.LENGTH_SHORT).show();

                }catch (InterruptedException e){

                }
                String result = task.getResult();
                String ID;
                String PASSWORD;
                Log.d("SAMPLEHTTP",result);
                try{
                    JSONObject root = new JSONObject(result);
                    JSONArray ja = root.getJSONArray("result");
                    inputID = (TextView) findViewById(R.id.inputID);
                    inputPW = (TextView) findViewById(R.id.inputPW);
                    for(int i=0;i<ja.length();i++){
                        Log.d("SAMPLEHTTP",i+"");
                        JSONObject jo = ja.getJSONObject(i);
                        ID = jo.getString("ID");
                        PASSWORD = jo.getString("PASSWORD");
                        Log.d("SAMPLEHTTP",ID +" "+PASSWORD);
                        if(inputID.getText().toString().equals(ID) &&inputPW.getText().toString().equals(PASSWORD)){
                            Toast.makeText(getApplicationContext(), "ID and PW are correct!", Toast.LENGTH_SHORT).show();
                            Intent MemoIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivityForResult(MemoIntent, 111);
                        }
                    }
                    Toast.makeText(getApplicationContext(), "ID and PW are incorrect!", Toast.LENGTH_SHORT).show();


                }catch(JSONException e){
                    e.printStackTrace();
                }

          //      Intent MemoIntent = new Intent(getApplicationContext(), MainActivity.class);
        //        startActivityForResult(MemoIntent, 111);
            }
        });

        test = (Button)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemoIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(MemoIntent, 111);
            }
        });
    }
}
