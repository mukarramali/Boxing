package prashushi.boxing;

/**
 * Created by Dell User on 9/3/2016.
 */
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

public class ActivityOptionSelected extends AppCompatActivity {
    ListView l;
    LinearLayoutManager mLayoutManager;

    int pos;
    int[] status;
    String[] predictions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_selected);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Prediction Trend");
        boolean old=getIntent().getBooleanExtra("old", false);
        if(old){
            //old, fight_id required
            String url=getString(R.string.localhost)+"fight/getPredictions";
            ArrayList<String> params=new ArrayList<>();
            ArrayList<String> values=new ArrayList<>();
            params.add("fight_id");
            values.add(getIntent().getExtras().getInt("fight_id", 0)+"");

            new BackgroundTaskPost(this, url, params, values, new BackgroundTaskPost.AsyncResponse() {
                @Override
                public void processFinish(String output, int code) {
                    JSONObject jsonObject= null;
                    JSONArray jsonArray=null;
                    output="{\"predictions\":[{\"id\":1,\"prediction\":\"pankaj on Points\",\"status\":0},{\"id\":2,\"prediction\":\"pankaj inside the distance\",\"status\":50},{\"id\":3,\"prediction\":\"Draw\",\"status\":50},{\"id\":4,\"prediction\":\"prakhar on Points\",\"status\":0},{\"id\":5,\"prediction\":\"prakhar inside the distance\",\"status\":0}],\"is_predicted\":true,\"prediction_id\":3}";
                    //if(code==200||code==202) {
                    try {
                            jsonObject=new JSONObject(output);
                            jsonArray=jsonObject.optJSONArray("predictions");
                            predictions=new String[jsonArray.length()];
                            status=new int[jsonArray.length()];
                            int prediction_id=jsonObject.optInt("prediction_id");
                            for(int i=0;i<jsonArray.length();i++){
                                System.out.println("*");
                                predictions[i]=jsonArray.getJSONObject(i).optString("prediction");
                                status[i]=jsonArray.getJSONObject(i).optInt("status");
                                if(prediction_id==jsonArray.getJSONObject(i).optInt("id"))
                                    pos=i;
                                initList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                //}
            }).execute();

        }else{
            pos=getIntent().getIntExtra("pos", 0);
            predictions=getIntent().getExtras().getStringArray("predictions");
            status=getIntent().getExtras().getIntArray("status");
            initList();

        }
    }
    void initList(){
        final RecyclerView list= (RecyclerView) findViewById(R.id.listView2);
        mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
        RecyclerAdapterOptionSelected recyclerAdapterOptionSelected
                =new RecyclerAdapterOptionSelected(ActivityOptionSelected.this, predictions, status, pos);
        list.setAdapter(recyclerAdapterOptionSelected);
    }

}
