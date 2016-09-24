package prashushi.boxing;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;



class BackgroundTask extends AsyncTask<Void, Void, Boolean> {
    public AsyncResponse delegate = null;
    String url="";
    ArrayList<String> params, values;
    String result="";
    Boolean loader;
    Context context;
    long time;
    AlertDialog alertDialog;
    String msg;
    ProgressDialog progressDialog;
    int response_code=404;  //no_internet:1, not_found:404,400 , success:200
    BackgroundTask(String url, ArrayList<String> params, ArrayList<String> values, AsyncResponse delegate){
        this.url=url;
        this.params=params;
        this.values=values;
        this.delegate=delegate;
        this.loader = false;
    }

    BackgroundTask(Context context, String url, ArrayList<String> params, ArrayList<String> values, AsyncResponse delegate) {
        //with loader and message
        this.url = url;
        this.params = params;
        this.values = values;
        this.delegate = delegate;
        this.loader = true;
        this.context = context;
    }

    BackgroundTask(Context context, String msg, String url, ArrayList<String> params, ArrayList<String> values, AsyncResponse delegate) {
        //with loader and message
        this.url = url;
        this.params = params;
        this.values = values;
        this.delegate = delegate;
        this.loader = true;
        this.context = context;
        time = (new Date().getTime()) % 100000;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (loader) {
            progressDialog = new ProgressDialog(context);
            String title = "Loading...";
            if (msg != null)
                title = msg;
            progressDialog.setMessage(title);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }
    }


    @Override
    protected Boolean doInBackground(Void... param) {
//        time=(new Date().getTime())%100000;
        //      System.out.println("doInBackground1:"+time);
        StringBuilder sb = new StringBuilder();
        try {
            if(params.size()>0)
                url+="?"+params.get(0)+"="+values.get(0);
            for(int i=1;i<values.size();i++) {
                url+="&"+params.get(i)+"="+values.get(i);
            }
            System.out.println(url);
            URL Url = new URL(url);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) Url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response_code=conn.getResponseCode();
            String line ;// Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
            time = (new Date().getTime()) % 100000;
            System.out.println("doInBackground4:" + time);
        } catch(UnknownHostException e){
            response_code=1;
        }
        catch (IOException e) {
            e.printStackTrace();
            response_code=404;
            result="falsexxx";
        }
        return false;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        response_code=1;
        Toast.makeText(context, context.getString(R.string.no_connection), Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        System.out.println(result);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        delegate.processFinish(result, response_code);
        //  delegate.processFinish(result);
    }

    public interface AsyncResponse {
        //    void processFinish(String output);
        void processFinish(String output, int code);

    }
}
