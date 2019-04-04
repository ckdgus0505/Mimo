package alpha.mimo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class URLConnector extends Thread {
    private String result;
    private String URLL;

    public URLConnector(String url){
        URLL = url;
    }

    @Override
    public void run(){
        final String output = request(URLL);
        result =output;
    }
    public String getResult(){
        return result;
    }
    private String request(String urlStr){
        StringBuilder output = new StringBuilder();
        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while(true){
                        line = reader.readLine();
                        if(line == null){
                            break;
                        }
                        Log.d("sampleHTTP",output.toString());
                        output.append(line + "\n");
                    }

                    reader.close();
                    conn.disconnect();
                }
            }

        }catch(Exception ex){
            Log.e("SampleHTTP", "Exception in processing response", ex);
            ex.printStackTrace();
        }
        return output.toString();
    }
}


