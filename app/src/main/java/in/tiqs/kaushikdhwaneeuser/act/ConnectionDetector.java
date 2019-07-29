package in.tiqs.kaushikdhwaneeuser.act;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    
   private Context _context;
    boolean connected;
    
   public ConnectionDetector(Context context) {
       this._context = context;
   }

   public boolean isConnectingToInternet(){
       ConnectivityManager connectivityManager = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
       if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
               connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
           //we are connected to a network
           connected = true;
       }
       else
           connected = false;
       return connected;
   }
}
