package prashushi.boxing;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Dell User on 9/2/2016.
 */
public class ActivityListFights extends AppCompatActivity {
    LinearLayoutManager mLayoutManager;
    boolean old;
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fights);
        old=getIntent().getBooleanExtra("old", false);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        if(old)
            actionBar.setTitle("Predicted Fights");
        else
            actionBar.setTitle("Predict Fights");
        initList();
    }
    void initList(){
        list= (RecyclerView) findViewById(R.id.list_fights);
        mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
            callServer();
    }

    private void callServer() {
        //String url="";
        String url=getString(R.string.localhost)+"fight/";
        if(old){
            //update url
            url+="getPredictions";
        }
        else
        {
            url+="all";

        }
        ArrayList<String> params=new ArrayList<>();
        ArrayList<String> values=new ArrayList<>();

        new BackgroundTaskPost(this, url, params, values, new BackgroundTaskPost.AsyncResponse() {
            @Override
            public void processFinish(String output, int code) {
                JSONArray jsonArray= new JSONArray();
                output="[{\"fight_id\":4,\"fighter1\":\"Ashish Saklani\",\"fighter2\":\"Satyendra Mishra\",\"start_date\":\"21st September\",\"predicted\":false}]";
                //if(code==200||code==202){
                    try {
                        jsonArray = new JSONArray(output);
                        RecyclerAdapterFights recyclerAdapterFights=new RecyclerAdapterFights(ActivityListFights.this, jsonArray, old);
                        list.setAdapter(recyclerAdapterFights);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
           /*     }else{
                    new PrintSnackbar(ActivityListFights.this, getWindow().getDecorView(), new PrintSnackbar.AsyncResponse() {
                        @Override
                        public void retry() {
                            callServer();
                        }
                    }).printSnackbar();
                }*/
            }
        }).execute();

    }
}
