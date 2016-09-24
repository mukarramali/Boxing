package prashushi.boxing;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell User on 7/9/2016.
 */
public class DialogScore extends DialogFragment implements View.OnClickListener {


    int position=0;
    String player1, player2;
    private boolean isModal = false;
    int player=1;
    TextView tvPlayer;
    int[] bts=new int[]{R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4,R.id.bt5,R.id.bt6,R.id.bt7,R.id.bt8,R.id.bt9,R.id.bt10};
    public static DialogScore newInstance() {
        DialogScore dialog = new DialogScore();
        dialog.isModal=true;
        return dialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(isModal) // AVOID REQUEST FEATURE CRASH
        {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        else
        {
            View view = inflater.inflate(R.layout.dialog_score, container, false);
            return view;
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_score, null);

        position=getArguments().getInt("position");
        System.out.println("Position:"+position);
        player1=getArguments().getString("fighter1");
        player2=getArguments().getString("fighter2");
        tvPlayer= (TextView) v.findViewById(R.id.tv_player);
        tvPlayer.setText(player==1?player1:player2);
        v.findViewById(R.id.im_close).setOnClickListener(this);
        v.findViewById(R.id.block_outcomes_dialog).setOnClickListener(this);
        for(int i=0;i<10;i++)
            v.findViewById(bts[i]).setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        return builder.create();
    }

    void switchPlayer(){
        if (player==1) {
            player = 2;
            tvPlayer.setText(player2);
        }
        else {
            player = 1;
            tvPlayer.setText(player1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_close:
                this.dismiss();
                break;
            case R.id.block_outcomes_dialog:
                mListener.flip(position,false, true, player);
                break;
            default:
                for(int i=0;i<10;i++){
                    if(v.getId()==bts[i])
                    {
                        System.out.println("Position:"+position);
                        mListener.score(position, player, i+1);
                        if(player==2)
                         //   mListener.flip(position, false, true);
                        this.dismiss();
                        switchPlayer();

                    }
                }
        }
    }

    public interface OnCompleteListenerScore {
        void onComplete(int pos, String item, String tag);
        void flip(int position,boolean score, boolean outcomes, int whichPlayer);
        //player1:false
        void score(int position,int player, int points);
    }

    OnCompleteListenerScore mListener;



    // make sure the Activity implemented it
   @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListenerScore)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

}