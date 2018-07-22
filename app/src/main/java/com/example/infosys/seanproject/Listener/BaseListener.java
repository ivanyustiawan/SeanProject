package com.example.infosys.seanproject.Listener;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.infosys.seanproject.Widget.CustomDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class BaseListener {

    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String STATUS_OK = "OK";

    public interface OnRequestListener {
        void onPreExecute();
        void onPostExecute(JSONObject response) throws Exception;
    }

    public void request(String method, String url, Map<String, Object> map, OnRequestListener listener) {
        new HandlerRequest(method, map, listener).execute(url);
    }

    public void request(String method, String url, OnRequestListener listener){
        new HandlerRequest(method, listener).execute(url);
    }

    private class HandlerRequest extends AsyncTask<String, Void, JSONObject> {

        String method;
        JSONObject object;
        OnRequestListener listener;

        public HandlerRequest(String method, Map<String, Object> map, OnRequestListener listener) {
            this.method = method;
            this.listener = listener;
            this.object = new JSONObject(map);
        }

        public HandlerRequest(String method, OnRequestListener listener) {
            this.method = method;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            listener.onPreExecute();
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String textUrl = strings[0];
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            JSONObject jsonObject;

            try{
                URL url = new URL(textUrl);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

                Log.d("baba", method);
                if(method.equals(METHOD_POST)){
                    httpConn.setDoInput(true);
                    httpConn.setDoOutput(true);
                    httpConn.setRequestProperty("Content-Type", "application/json");
                    httpConn.setRequestMethod(method);

                    if (object != null) {
                        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
                        writer.write(object.toString());
                        writer.flush();
                    }
                }
                else if(method.equals(METHOD_GET)){
                    httpConn.setAllowUserInteraction(false);
                    httpConn.setInstanceFollowRedirects(true);
                    httpConn.setRequestMethod(method);
                    httpConn.connect();
                }

                int resCode = httpConn.getResponseCode();

                if (resCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpConn.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder sb = new StringBuilder();
                    String s = null;
                    while ((s = bufferedReader.readLine()) != null) {
                        sb.append(s);
                        sb.append("\n");
                    }

                    jsonObject = new JSONObject(sb.toString());
                    return jsonObject;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                listener.onPostExecute(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    CustomDialog progressDialog;
    synchronized public void showProgressDialog(Context context) {
        progressDialog = CustomDialog.show(context, "", "", true, false);
    }

    synchronized public void hideProgressDialog() {
        if (progressDialog!=null) {
            progressDialog.dismiss();
        }

    }
}
