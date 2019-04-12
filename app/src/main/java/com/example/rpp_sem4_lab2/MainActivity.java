package com.example.rpp_sem4_lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private DataLoadTask loadTask;
    private String url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadTask = new DataLoadTask(this);
        loadTask.execute(this.url);
    }

    public void onEnd() {
        Intent i = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadTask.cancel(true);
    }

    private static class DataLoadTask extends AsyncTask<String, Void, String> {
        final MainActivity listener;
        OkHttpClient client = new OkHttpClient();

        DataLoadTask(MainActivity listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            Request.Builder builder = new Request.Builder();
            builder.url(String.valueOf(params[0]));
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            DataHold dataHold = DataHold.getInstance();

            JSONArray json = null;

            try {
                json = new JSONArray(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            dataHold.setData(json);

            if (listener != null)
                listener.onEnd();
        }
    }
}
