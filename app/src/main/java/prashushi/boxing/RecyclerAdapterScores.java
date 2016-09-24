package prashushi.boxing;

/**
 * Created by Dell User on 9/2/2016.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//1 425 3 425 3
public class RecyclerAdapterScores extends Adapter<RecyclerAdapterScores.ViewHolder> {

    JSONArray items;
    private Context mContext;
    boolean old;


    public RecyclerAdapterScores(Context context, JSONArray jsonArray, boolean old) {
        mContext = context;
        // sPrefs=context.getSharedPreferences(context.getString(R.string.S_PREFS), context.MODE_PRIVATE);
        //editor=sPrefs.edit();
        this.old=old;
        items=jsonArray;
    }


    @Override
    public int getItemCount() {
        return items.length();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_scores, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(RecyclerAdapterScores.ViewHolder holder, int position) {
        TextView nameTag1= (TextView) holder.itemView.findViewById(R.id.tv_name1);
        TextView nameTag2= (TextView) holder.itemView.findViewById(R.id.tv_name2);
        TextView date= (TextView) holder.itemView.findViewById(R.id.tv_date);
        TextView score1= (TextView) holder.itemView.findViewById(R.id.tv_score1);
        TextView score2= (TextView) holder.itemView.findViewById(R.id.tv_score2);
//      output="[{\"fight_id\":4,\"fighter1\":{\"name\":\"pankaj\",\"id\":1},\"fighter2\":{\"name\":\"prakhar\",\"id\":2}," +
//        "\"start_date\":21st September,\"scores\":{\"fighter1\":\"2\",\"fighter2\":\"5\"}}]";

        JSONObject item=items.optJSONObject(position);
        nameTag1.setText(item.optJSONObject("fighter1").optString("name"));
        nameTag2.setText(item.optJSONObject("fighter2").optString("name"));
        date.setText(item.optString("start_date"));
            if(old){
                score1.setText(item.optJSONObject("scores").optString("fighter1")+"");
                score2.setText(item.optJSONObject("scores").optString("fighter2")+"");
            }

    }



    // Not use static
    public class ViewHolder extends RecyclerView.ViewHolder {
        int i=0;
        @TargetApi(Build.VERSION_CODES.KITKAT)
        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=getAdapterPosition();
                    Intent intent;
                    JSONObject item= null;
                    try {
                        item = items.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String name1=item.optJSONObject("fighter1").optString("name");
                    String name2=item.optJSONObject("fighter2").optString("name");
                    System.out.println(name1+name2);
                    int rounds=item.optInt("no_rounds");
                    if(!old)//new
                        {
                            intent=new Intent(mContext, ActivityFightRounds.class);
                            intent.putExtra("case",2);
                            intent.putExtra("fighter1", name1);
                            intent.putExtra("fighter2", name2);
                            intent.putExtra("no_rounds", rounds);
                        }
                    else
                    {
                        String roundArray=item.optString("rounds");
                        intent=new Intent(mContext, ActivityFightRounds.class);
                        intent.putExtra("case",3);
                        intent.putExtra("fighter1", name1);
                        intent.putExtra("fighter2", name2);
                        intent.putExtra("no_rounds", rounds);
                        intent.putExtra("rounds", roundArray);
                    }
                    mContext.startActivity(intent);
                }
            });
        }
    }


}