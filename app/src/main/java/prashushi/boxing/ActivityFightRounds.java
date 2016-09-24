package prashushi.boxing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dell User on 9/3/2016.
 */
public class ActivityFightRounds extends AppCompatActivity implements DialogScore.OnCompleteListenerScore, DialogOutcomes.OnCompleteListenerOutcomes,AdapterView.OnItemClickListener {

    int no_round=3;
    String player1="", player2="";
    DialogScore dialogScore;
    DialogOutcomes dialogOutcomes;
    JSONArray jsonArray;
    RoundInstance[] roundInstances;
    ListView list1;
    ListViewAdapter adapter1, adapter2;
    int player1_id=0, player2_id=0;
    int cases=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_rounds);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Fight Rounds");
       //no_round=getIntent().getIntExtra("no_rounds", 3);
        cases=getIntent().getIntExtra("case", 1);
        if(cases==2){
            player1_id=getIntent().getExtras().getInt("fighter1_id");
            player2_id=getIntent().getExtras().getInt("fighter2_id");
        }
        System.out.println("case:"+cases);
        player1=getIntent().getExtras().getString("fighter1");
        player2=getIntent().getExtras().getString("fighter2");
        System.out.println(player1+player2);
        dialogScore=DialogScore.newInstance();
        dialogOutcomes=DialogOutcomes.newInstance();
        updateToolbar();
        initList(cases);

    }

    void updateToolbar(){
        TextView tv_player1= (TextView) findViewById(R.id.tv_player1);
        TextView tv_player2= (TextView) findViewById(R.id.tv_player2);
        tv_player1.setText(player1+"");
        tv_player2.setText(player2+"");
    }
/*
    void updateScore(int i, int i1) {
        TextView score= (TextView) findViewById(R.id.tv_score);
        score.setText(i+"     =      "+i1);
    }
    */
void updateScore(String i, String i1) {
    TextView score= (TextView) findViewById(R.id.tv_score);
    score.setText(i+"     =      "+i1);
}

    private void initList(int cases) {
        list1= (ListView) findViewById(R.id.list1);

        Integer[] rounds=new Integer[no_round];
        String[] player1=new String[no_round];
        String[] player2=new String[no_round];
        roundInstances=new RoundInstance[no_round];

        if(cases!=3) {

            for (int i = 1; i <= no_round; i++) {
                roundInstances[i - 1] = new RoundInstance();
                player1[i - 1] = "-";
                player2[i - 1] = "-";
                rounds[i - 1] = i;
            }
        }else{
            try {
                //{"score":[{"fighter1":1,"fighter2":2,"round":1},{"fighter1":1,"fighter2":3,"round":2},{"fighter1":"W\/RTD","fighter2":"L\/RTD","round":3}],"final_score":{"fighter1":"W2\/RTD3","fighter2":"L5\/RTD3"},"fight_id":"4","fighters":{"fighter1":{"name":"pankaj","id":1},"fighter2":{"name":"prakhar","id":2}}}
                String output="{\"score\":[{\"fighter1\":1,\"fighter2\":2,\"round\":1},{\"fighter1\":1,\"fighter2\":3,\"round\":2},{\"fighter1\":\"W\\/RTD\",\"fighter2\":\"L\\/RTD\",\"round\":3}],\"final_score\":{\"fighter1\":\"W2\\/RTD3\",\"fighter2\":\"L5\\/RTD3\"},\"fight_id\":\"4\",\"fighters\":{\"fighter1\":{\"name\":\"pankaj\",\"id\":1},\"fighter2\":{\"name\":\"prakhar\",\"id\":2}}}";
                JSONArray array=new JSONObject(output).optJSONArray("score");

                //JSONArray array=new JSONArray(getIntent().getExtras().getString("rounds"));
                roundInstances=RoundInstance.jsonToObject(array);
                JSONObject finalScoreObject=new JSONObject(output).optJSONObject("final_score");
                updateScore(finalScoreObject.getString("fighter1"), finalScoreObject.getString("fighter2"));
                for(int i=1;i<=roundInstances.length;i++)
                {
                    player1[i-1]=roundInstances[i-1].tag1();
                    player2[i-1]=roundInstances[i-1].tag2();
               /*     if(roundInstances[i-1].player1()>roundInstances[i-1].player2()) {
                        player1[i - 1] += "W";
                        player2[i - 1] += "L";
                    }
                    else if(roundInstances[i-1].player1()<roundInstances[i-1].player2()){
                        player2[i - 1] += "W";
                        player1[i - 1] += "L";
                    }
                    player1[i-1]+=roundInstances[i-1].player1()+"/"+roundInstances[i-1].outcome();
                    player2[i-1]+=roundInstances[i-1].player2()+"/"+roundInstances[i-1].outcome();
*/
                    rounds[i-1]=i;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter1=new ListViewAdapter(this,player1, player2);

        list1.setAdapter(adapter1);

        list1.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(cases!=3)
       flip(position,true,false, 1);
    }


    @Override
    public void onComplete(int pos, String item, String tag) {

    }


    @Override
    public void flip(int position,boolean score, boolean outcomes, int whichPlayer) {
        Bundle args=new Bundle();
        args.putInt("position",position);
        args.putInt("whichPlayer",whichPlayer);
        args.putString("fighter1", player1);
        args.putString("fighter2", player2);

        if (score)
    {

        dialogScore.setArguments(args);
        dialogScore.show(getSupportFragmentManager(),"");
        if(dialogOutcomes.isAdded())
            dialogOutcomes.dismiss();
    }
        else {
        dialogOutcomes.setArguments(args);
        dialogOutcomes.show(getSupportFragmentManager(),"");
        if(dialogScore.isAdded())
            dialogScore.dismiss();

        }
    }

    @Override
    public void outcome(int position, int i, int whichPlayer) {
     roundInstances[position].outcome(i);

        String tag1="", tag2="";
        if(whichPlayer==1)
        {
            tag1+="W";
            tag2+="L";
        }else{
            tag1+="L";
            tag2+="W";
        }
        if(roundInstances[position].player1()>0)
            tag1+=roundInstances[position].player1();
        if(roundInstances[position].player2()>0)
            tag2+=roundInstances[position].player2();

        tag1+="/"+roundInstances[position].outcome();
        tag2+="/"+roundInstances[position].outcome();
        TextView tv1= (TextView) list1.getChildAt(position).findViewById(R.id.tv_column1);
        TextView tv2= (TextView) list1.getChildAt(position).findViewById(R.id.tv_column3);
        tv1.setText(tag1);
        tv2.setText(tag2);

//     score(position, 1,roundInstances[position].player1);
    }

    @Override
    public void score(int position, int player, int points) {


        roundInstances[position].setScore(player,points);
        int point1=roundInstances[position].player1();
        int point2=roundInstances[position].player2();

        String tag1="", tag2="";
        if(point1>point2)
        {tag1="W"+point1;
        tag2="L"+point2;
        }
        else if(point1<point2)
        {tag2="W"+point2;
         tag1="L"+point1;
        }
        else
        {tag2=point2+"";
            tag1=point1+"";
        }
        tag1+="/"+roundInstances[position].outcome();
        tag2+="/"+roundInstances[position].outcome();


        TextView tv1= (TextView) list1.getChildAt(position).findViewById(R.id.tv_column1);
        TextView tv2= (TextView) list1.getChildAt(position).findViewById(R.id.tv_column3);
        tv1.setText(tag1);
        tv2.setText(tag2);


        findTotalScore();
    }

    private void updateServer(int round, int one, int two, int fight_id, int outcome_id, int winner_id) {
        String url=getString(R.string.localhost)+"store_round_score";
        ArrayList<String> params=new ArrayList<>();
        ArrayList<String> values=new ArrayList<>();


        params.add("score_fighter1");
        params.add("score_fighter2");
        params.add("round_no");
        params.add("fight_id");
        params.add("outcome_id");
        params.add("winner_id");


        values.add(one+"");
        values.add(two+"");
        values.add(round+"");
        values.add(fight_id+"");
        values.add(outcome_id+"");
        values.add(winner_id+"");

//        values.add(n+"");
        new BackgroundTask(this, url, params, values, new BackgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output, int code) {
                if(code>=200&&code<=299) {
                    if (!output.contains("falsexxx")) {

                    }
                }
            }
        }).execute();


    }

    private void findTotalScore() {
        int score1=0, score2=0;
        for(int i=0;i<roundInstances.length;i++)
        {
            score1+=roundInstances[i].player1();
            score2+=roundInstances[i].player2();

        }
        updateScore(score1+"", score2+"");
    }
}