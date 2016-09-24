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
public class RecyclerAdapterFights extends Adapter<RecyclerAdapterFights.ViewHolder> {

    JSONArray items;
    private Context mContext;
    boolean old;


    public RecyclerAdapterFights(Context context, JSONArray jsonArray, boolean old) {
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
                R.layout.list_fights, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(RecyclerAdapterFights.ViewHolder holder, int position) {
        TextView nameTag1= (TextView) holder.itemView.findViewById(R.id.tv_name1);
        TextView nameTag2= (TextView) holder.itemView.findViewById(R.id.tv_name2);
        TextView date= (TextView) holder.itemView.findViewById(R.id.tv_date);

        ImageView imCheck= (ImageView) holder.itemView.findViewById(R.id.im_checked);
        if(old){
            imCheck.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_checkbox_marked_circle));
        }else{
            imCheck.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_checkbox_red));
        }
            JSONObject item=items.optJSONObject(position);
            nameTag1.setText(item.optString("fighter1"));
            nameTag2.setText(item.optString("fighter2"));
            date.setText(item.optString("start_date"));


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
                    int fight_id=items.optJSONObject(i).optInt("fight_id");
                    boolean predicted=items.optJSONObject(i).optBoolean("predicted");
                    if(!old&&!predicted)
                        intent = new Intent(mContext, ActivityChoose.class);
                    else{
                        intent=new Intent(mContext, ActivityOptionSelected.class);
                        intent.putExtra("old", true);

                    }
                    intent.putExtra("fight", items.optJSONObject(i).toString());
                    intent.putExtra("fight_id", fight_id);
                    mContext.startActivity(intent);
                }
            });
        }
    }


}