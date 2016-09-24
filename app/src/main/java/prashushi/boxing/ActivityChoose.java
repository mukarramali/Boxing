package prashushi.boxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dell User on 9/2/2016.
 */
public class ActivityChoose  extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView l;
    JSONObject fight;
    String prediction[];
    int prediction_id[], status[];
    int fight_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Choose Option");
        l= (ListView) findViewById(R.id.listView);
        try {
            fight=new JSONObject(getIntent().getExtras().getString("fight", "{}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fight_id=fight.optInt("fight_id");
        String url=getString(R.string.localhost)+"fight/getPredictions";
        ArrayList<String> params=new ArrayList<>();
        ArrayList<String> values=new ArrayList<>();
        params.add("fight_id");
        values.add(fight_id+"");

        new BackgroundTaskPost(this, url, params, values, new BackgroundTaskPost.AsyncResponse() {
            @Override
            public void processFinish(String output, int code) {
                JSONObject jsonObject= null;
                JSONArray jsonArray=null;
                output="{\"predictions\":[{\"id\":1,\"prediction\":\"pankaj on Points\",\"status\":0},{\"id\":2,\"prediction\":\"pankaj inside the distance\",\"status\":50},{\"id\":3,\"prediction\":\"Draw\",\"status\":50},{\"id\":4,\"prediction\":\"prakhar on Points\",\"status\":0},{\"id\":5,\"prediction\":\"prakhar inside the distance\",\"status\":0}],\"is_predicted\":true,\"prediction_id\":3}";
                //[{"fight_id":4,"fighter1":"pankaj","fighter2":"prakhar","start_date":null,"predicted":true}]
                //if(code==200||code==202) {
                    try {
                        jsonObject=new JSONObject(output);
                        jsonArray=jsonObject.optJSONArray("predictions");
                        prediction=new String[jsonArray.length()];
                        prediction_id=new int[jsonArray.length()];
                        status=new int[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            prediction[i]=jsonArray.getJSONObject(i).optString("prediction");
                            prediction_id[i]=jsonArray.getJSONObject(i).optInt("id");
                            status[i]=jsonArray.getJSONObject(i).optInt("status");
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(ActivityChoose.this,R.layout.list_options,R.id.textView,prediction);
                        l.setAdapter(adapter);
                        l.setDividerHeight(0);
                        l.setOnItemClickListener(ActivityChoose.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                //}
            }
        }).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final int pos=position;
        String url=getString(R.string.localhost)+"fight/make_prediction";
        ArrayList<String> params=new ArrayList<>();
        ArrayList<String> values=new ArrayList<>();
        params.add("fight_id");
        values.add(fight_id+"");
        params.add("prediction_id");
        values.add(prediction_id[position]+"");

        new BackgroundTaskPost(this, url, params, values, new BackgroundTaskPost.AsyncResponse() {
            @Override
            public void processFinish(String output, int code) {
                JSONObject jsonObject= null;
                JSONArray jsonArray=null;
                //[{"fight_id":4,"fighter1":"pankaj","fighter2":"prakhar","start_date":null,"predicted":true}]
                //if(code==200||code==202) {
                    Intent intent = new Intent(ActivityChoose.this,ActivityOptionSelected.class);
                    intent.putExtra("pos",pos);
                    intent.putExtra("predictions", prediction);
                    intent.putExtra("status", status);
                    startActivity(intent);
                //}
            }}).execute();
    }
}

