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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell User on 7/9/2016.
 */
public class DialogOutcomes extends DialogFragment implements View.OnClickListener {


    int position, whichPlayer;
    int[] bts=new int[]{R.id.bt7,R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4,R.id.bt5,R.id.bt6};
    private boolean isModal = false;
    public static DialogOutcomes newInstance() {
        DialogOutcomes dialog = new DialogOutcomes();
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
            View view = inflater.inflate(R.layout.dialog_outcome, container, false);
            return view;
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_outcome, null);

        position=getArguments().getInt("position");
        whichPlayer=getArguments().getInt("whichPlayer");
        v.findViewById(R.id.im_close).setOnClickListener(this);
        v.findViewById(R.id.block_score_dialog).setOnClickListener(this);
        for(int i=0;i<bts.length;i++)
            v.findViewById(bts[i]).setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_close:
                this.dismiss();
                break;
            case R.id.block_score_dialog:
                mListener.flip(position,true, false,whichPlayer);

                break;
            default:
                for(int i=0;i<bts.length;i++){
                    if(v.getId()==bts[i])
                    {
                        System.out.println("Position:"+position);
                        mListener.outcome(position, i, whichPlayer);
                        dismiss();

                    }
                }

        }
    }

    public interface OnCompleteListenerOutcomes {
        void onComplete(int pos, String item, String tag);
        void flip(int position,boolean score, boolean outcomes, int whichPlayer);
        void outcome(int position, int i, int player);
    }

    OnCompleteListenerOutcomes mListener;



    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListenerOutcomes)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

}