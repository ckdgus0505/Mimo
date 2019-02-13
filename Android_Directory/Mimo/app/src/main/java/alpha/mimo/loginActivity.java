package alpha.mimo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;

public class loginActivity extends AppCompatActivity {
    Button test;
    String testphp;
    URLConnector task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        testphp = "http://localhost/MediumServer/SelectAllPost.php";
        task = new URLConnector(testphp);
        task.start();
        try{
            task.join();
            Toast.makeText(this, "waiting... for result", Toast.LENGTH_SHORT).show();
        }catch (InterruptedException e){

        }

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
