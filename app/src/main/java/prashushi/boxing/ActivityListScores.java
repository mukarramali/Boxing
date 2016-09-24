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
public class ActivityListScores extends AppCompatActivity {
    LinearLayoutManager mLayoutManager;
    boolean old;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fights);
        old=getIntent().getBooleanExtra("old", false);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        if(old)
            actionBar.setTitle("Predicted Score");
        else
            actionBar.setTitle("Predict Score");
        initList();
    }
    void initList(){
        final RecyclerView list= (RecyclerView) findViewById(R.id.list_fights);
        mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);

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
                output="[{\"fight_id\":4,\"fighter1\":{\"name\":\"Ashish Saklani\",\"id\":1},\"fighter2\":{\"name\":\"Satyendra Mishra\",\"id\":2}," +
                        "\"start_date\":\"09/21/16\",\"scores\":{\"fighter1\":\"W2/RTD3\",\"fighter2\":\"L5/RTD3\"},\"no_rounds\":5}]";
                //W2\/RTD3, L5\/RTD3
                //if(code==200||code==202){
                try {
                    jsonArray = new JSONArray(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //}
                RecyclerAdapterScores recyclerAdapterScores=new RecyclerAdapterScores(ActivityListScores.this, jsonArray, old);
                list.setAdapter(recyclerAdapterScores);
            }
        }).execute();
    }
}
