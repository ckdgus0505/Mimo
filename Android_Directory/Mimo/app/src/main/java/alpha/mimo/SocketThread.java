package alpha.mimo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketThread extends Thread {
    private Socket clientSocket;
    private String ID;
    private char ch;
    private String fileName;
    private String content;
    private Handler handler;

    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 1154;
    Open2Activity.OpenHandler openHandler;
    private final String ip = "58.120.197.22";

    public SocketThread(String ID, char ch, String fileName, String content, Handler handler) {
        this.ID =ID;
        this.ch = ch;
        this.fileName = fileName;
        this.content =content;
        this.handler = handler;
    }

    public void run() {

        try {
            clientSocket = new Socket(ip, port);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
//            Intent intent  = getIntent();
//            String ID =processIntent(intent);

            StringBuilder sb= new StringBuilder();
            String str =" ";

            socketOut.println(ID);
            this.sleep(500);


            if(this.ch =='a'){
                socketOut.println("a");
                this.sleep(500);
                socketOut.println(fileName);
                while ((str=socketIn.readLine()) != null){
                    sb.append(str+"\n");
                    Log.d("SAMPLEHTTP","INPUT="+ str);
                }
                Message msg = handler.obtainMessage();
                msg.obj = sb.toString();
                handler.sendMessage(msg);
            }
            if(this.ch =='c'){
                socketOut.println("c");

                String data = socketIn.readLine();
                fileList(data);
            }
            else if(this.ch =='d'){
                socketOut.println("d");
                this.sleep(300);
                socketOut.println(fileName);
                String resultmessage =socketIn.readLine();
                Message msg = handler.obtainMessage();
                msg.obj = resultmessage;
                handler.sendMessage(msg);
            }
            else if(this.ch =='j'){
                socketOut.println("j");
                Log.d("SAMPLEHTTP","j");
                this.sleep(500);
                socketOut.println(fileName);
                this.sleep(500);

                // 달라...
                str = new String(this.content);
                socketOut.println(str);
                this.sleep(500);

                Message msg =handler.obtainMessage();
                msg.obj = "동기화 하였습니다.";
                handler.sendMessage(msg);

            }

            socketIn.close();
            socketOut.close();
            clientSocket.close();
        }
        catch (Exception e) {
            Log.d("SAMPLEHTTP","what's wrong?");
            e.printStackTrace();
        }
    }

    public void fileList(String data){
        String temp = new String(data);
        String stringnum=" ";
        int si_filename = 0; //start index of filename;
        char a= ' ';
        for(int i=0;i<temp.length();i++){
            a= temp.charAt(i);
            if(a<'0' || '9'<a){
                stringnum = temp.substring(0,i);
                si_filename = i;
                break;
            }
        }

        int num =    Integer.parseInt(stringnum);
        int index_mimm;
        int s_index=si_filename;

        index_mimm = temp.indexOf(".mimm",s_index);
        if(index_mimm ==-1) {
            //adapter.notifyDataSetChanged();
            return;// 끝내기.
        }
        index_mimm +=5;//pass .mimm
        Message msg = handler.obtainMessage();
        msg.obj = temp.substring(s_index,index_mimm);
        handler.sendMessage(msg);
//            System.out.println(temp.substring(s_index,index_mimm));

        s_index = index_mimm;

        while(true) {
            index_mimm = temp.indexOf(".mimm",s_index);
            if(index_mimm ==-1) {
                break;// 끝내기.
            }
            index_mimm +=5;
            msg = handler.obtainMessage();
            msg.obj = temp.substring(s_index,index_mimm);
            handler.sendMessage(msg);
            ;//pass .mimm
            s_index = index_mimm;
        }
    }
}
