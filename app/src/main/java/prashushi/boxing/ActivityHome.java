package prashushi.boxing;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

/**
 * Created by Dell User on 9/1/2016.
 */
public class ActivityHome extends AppCompatActivity implements View.OnClickListener {

    ScrollView hiddenPanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.block1).setOnClickListener(this);
        findViewById(R.id.block3).setOnClickListener(this);
        findViewById(R.id.block2).setOnClickListener(this);
        findViewById(R.id.block4).setOnClickListener(this);
        findViewById(R.id.add_custom).setOnClickListener(this);
        findViewById(R.id.how).setOnClickListener(this);
        findViewById(R.id.im_close).setOnClickListener(this);
        hiddenPanel= (ScrollView) findViewById(R.id.hidden_panel);
    }

    boolean isPanelShown(){
        return hiddenPanel. getVisibility()==View.VISIBLE;
    }

    void slideUpDown(){
        if(!isPanelShown()) {
            Animation bottom_up = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
            hiddenPanel.startAnimation(bottom_up);
            hiddenPanel.setVisibility(View.VISIBLE);
            findViewById(R.id.im_close).setVisibility(View.VISIBLE);
        }else{
            Animation bottom_down = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
            hiddenPanel.startAnimation(bottom_down);
            hiddenPanel.setVisibility(View.INVISIBLE);
            findViewById(R.id.im_close).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.block1:
                startActivity(new Intent(this, ActivityListFights.class));
                break;
            case R.id.block3:
                intent=new Intent(this, ActivityListFights.class);
                intent.putExtra("old", true);
                startActivity(intent);
                break;
            case R.id.block2:
                startActivity(new Intent(this, ActivityListScores.class));
                break;
            case R.id.block4:
                intent=new Intent(this, ActivityListScores.class);
                intent.putExtra("old", true);
                startActivity(intent);
                break;
            case R.id.add_custom:
                startActivity(new Intent(this, ActivityAddFight.class));
                break;
            case R.id.how:
                slideUpDown();
                break;
            case R.id.im_close:
                slideUpDown();
                break;

        }
    }

}
