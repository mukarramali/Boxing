package prashushi.boxing;

/**
 * Created by Dell User on 9/14/2016.
 */

        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.support.design.widget.Snackbar;
        import android.view.View;
public class PrintSnackbar {
    View parent;
    Context context;
    public PrintSnackbar(Context context, View parent, AsyncResponse delegate){
        this.context=context;
        this.parent=parent;
        this.delegate=delegate;
    }
    AsyncResponse delegate;

    public interface AsyncResponse {
        void retry();

    }


    void printSnackbar(){
        System.out.println("in snackbar");
        if(parent!=null)
            Snackbar.make(parent, "Couldn't Connect!", Snackbar.LENGTH_LONG)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delegate.retry();

                        }
                    })  // action text on the right side
//                    .setActionTextColor(context.getResources().getColor(R.color.green))
                    .setDuration(Snackbar.LENGTH_INDEFINITE).show();

    }


    public  boolean checkConnection(){
        final ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if((connectivityManager.getActiveNetworkInfo()!=null&&connectivityManager.getActiveNetworkInfo().isConnected()))
            {
                System.out.println("connected");
                return true;
            }
        else
        {
            System.out.println("not connected");
            printSnackbar();
            return false;
        }
    }

}
