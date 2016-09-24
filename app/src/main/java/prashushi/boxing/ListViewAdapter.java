package prashushi.boxing;

/**
 * Created by Dell User on 9/5/2016.
 */
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.text.DecimalFormat;
        import java.util.ArrayList;

//for drawerList

public class ListViewAdapter extends ArrayAdapter<String> {
    private final Context context;
    String[] values1, values2;
    TextView tv1, tv2, tv3;
    public ListViewAdapter(Context context, String[] values1, String[] values2 ) {
        super(context, R.layout.list_rounds_column, values1);
        this.context = context;
        this.values1 = values1;
        this.values2 = values2;
    }

    @Override
    public void add(String object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.values1.length;
    }

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_rounds_column, parent, false);
        tv1=(TextView)rowView.findViewById(R.id.tv_column1);
        tv1.setText(values1[position]);
        tv2=(TextView)rowView.findViewById(R.id.tv_column2);
        tv2.setText(position+1+"");
        tv3=(TextView)rowView.findViewById(R.id.tv_column3);
        tv3.setText(values2[position]);
        return rowView;
    }
}

