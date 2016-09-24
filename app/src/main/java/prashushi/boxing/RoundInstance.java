package prashushi.boxing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell User on 9/5/2016.
 */
public class RoundInstance {
    String[] outcomes=new String[]{"","KO", "RTD", "NC", "DQ", "TD", "TKO"};
    //[{"id":15,"abbr":"RTD"},{"id":16,"abbr":"KO"},{"id":20,"abbr":"NC"},
    // {"id":17,"abbr":"TKO"},{"id":18,"abbr":"TD"},{"id":19,"abbr":"DQ"}]
    int[] outcome_id=new int[]{0,16,15,20,19,18, 17};
    int player1=0;
    int player2=0;
    int outcome=0;
    String tag1="", tag2="";
    RoundInstance(){

    }
    void setScore(int player, int points){
        if(player==1)
            player1=points;
        else
            player2=points;
    }
    void setTag(String one, String two){
        tag1=one;
        tag2=two;
    }
    String tag1(){
        return tag1;
    }
    String tag2(){
        return tag2;
    }

    void player1(int i){
        player1=i;
    }
    void player2(int i){
        player2=i;
    }
    void outcome(int i){
        outcome=i;
    }
    int player1(){
        return player1;
    }
    int player2(){
        return player2;
    }
    String outcome(){
        return outcomes[outcome];
    }
    int outcome_id(){
        return outcome_id[outcome];
    }
    static RoundInstance[] jsonToObject(JSONArray array){
        RoundInstance[] roundInstances=new RoundInstance[array.length()];
        for(int i=0;i<array.length();i++){
            try {
                roundInstances[i]=jsonToObject(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return roundInstances;
    }
    static RoundInstance jsonToObject(JSONObject object){
        RoundInstance roundInstances=new RoundInstance();
        int player1=object.optInt("fighter1");
        int player2=object.optInt("fighter2");
        String tag1=object.optString("fighter1");
        String tag2=object.optString("fighter2");
        int outcome=object.optInt("outcome");
        roundInstances.outcome(outcome);
        roundInstances.player1(player1);
        roundInstances.player2(player2);
        roundInstances.setTag(tag1, tag2);
        return roundInstances;
    }

}
