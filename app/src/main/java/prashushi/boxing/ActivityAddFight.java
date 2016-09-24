package prashushi.boxing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityAddFight extends AppCompatActivity implements View.OnClickListener {
Button button;
    int[] ids;
    int[] rounds;
    int n=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fight);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ids=new int[]{R.id.Button3,R.id.Button4,R.id.Button5,R.id.Button6,R.id.Button8,R.id.Button10,R.id.Button12};
        rounds=new int[]{3,4,5,6,8,10,12};
        button= (Button) findViewById(R.id.Button3);
        button.setTextColor(getResources().getColor(R.color.white));
        button.setBackgroundColor(getResources().getColor(R.color.blue));
        findViewById(R.id.bt_add_fight).setOnClickListener(this);
    }

    public void Button(View view) {

    for(int i=0;i<ids.length;i++){
        if(view.getId()==ids[i]){
            //clicked button
            n=rounds[i];
            Button button= (Button) findViewById(ids[i]);
            button.setTextColor(getResources().getColor(R.color.white));
            button.setBackgroundColor(getResources().getColor(R.color.blue));

        }else{
            //rest of the buttons
            Button button= (Button) findViewById(ids[i]);
            button.setTextColor(getResources().getColor(R.color.blue));
            button.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_add_fight){
            EditText etPlayer1= (EditText) findViewById(R.id.editText);
            EditText etPlayer2= (EditText) findViewById(R.id.editText2);
            final String player1=etPlayer1.getText().toString();
            final String player2=etPlayer2.getText().toString();

            if(player1.length()==0)
            {
                etPlayer1.setError("Can't be empty!");
                return;
            }
            if(player2.length()==0)
            {
                etPlayer2.setError("Can't be empty!");
                return;
            }
            SimpleDateFormat pattern=new SimpleDateFormat("yy/mm/dd hh::mm:ss");
            String url=getString(R.string.localhost)+"fight/create";
            ArrayList<String> params=new ArrayList<>();
            ArrayList<String> values=new ArrayList<>();


            params.add("fighter1");
            params.add("fighter2");
            params.add("rounds");
            params.add("start_date");

            values.add(player1);
            values.add(player2);
            values.add(n+"");
            values.add(pattern.format(new Date()));
            new BackgroundTask(this, url, params, values, new BackgroundTask.AsyncResponse() {
                @Override
                public void processFinish(String output, int code) {
                    if(code>=200&&code<=299){
                        if(!output.contains("falsexxx")){

                            Intent intent=new Intent(ActivityAddFight.this, ActivityFightRounds.class);
                            intent.putExtra("no_rounds", n);
                            intent.putExtra("fighter1", player1);
                            intent.putExtra("fighter2", player2);
                            startActivity(intent);

                        }
                    }else{
                        //handle error
                    }
                }
            }).execute();

        }
    }
}
