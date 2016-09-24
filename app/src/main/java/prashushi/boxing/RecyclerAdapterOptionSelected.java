package prashushi.boxing;

/**
 * Created by Dell User on 9/2/2016.
 */

import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//1 425 3 425 3
public class RecyclerAdapterOptionSelected extends Adapter<RecyclerAdapterOptionSelected.ViewHolder> {

    JSONArray items;
    private Context mContext;
    int pos;
    String[] predictions;
    int[] status;


    public RecyclerAdapterOptionSelected(Context context, String[] predictions, int[] status, int pos) {
        mContext = context;
        // sPrefs=context.getSharedPreferences(context.getString(R.string.S_PREFS), context.MODE_PRIVATE);
        //editor=sPrefs.edit();
        this.predictions=predictions;
        this.status=status;
        this.pos=pos;
    }


    @Override
    public int getItemCount() {
        return predictions.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_option_selected, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(RecyclerAdapterOptionSelected.ViewHolder holder, int position) {
        TextView option= (TextView) holder.itemView.findViewById(R.id.tv_option_selected);
        TextView per= (TextView) holder.itemView.findViewById(R.id.tv_per);
        ProgressBar progressBar= (ProgressBar) holder.itemView.findViewById(R.id.progressBar);
        if(position==pos){
            holder.itemView.findViewById(R.id.fade_block).setBackground(mContext.getResources().getDrawable(R.drawable.box_rounded_border));
        }else{
            holder.itemView.findViewById(R.id.fade_block).setBackground(mContext.getResources().getDrawable(R.drawable.box_rounded_border_dark));
        }

            option.setText(predictions[position]);
            progressBar.setProgress(status[position]);
            per.setText(status[position]+"%");

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
  //                  Intent intent=new Intent(mContext, ActivityChoose.class);
//                    mContext.startActivity(intent);
                }
            });
        }
    }


}