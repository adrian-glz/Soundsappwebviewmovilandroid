package sounds.oficial.sounds_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        internetBaglantisiVarMi(context);
    }
    boolean internetBaglantisiVarMi(Context context){
        ConnectivityManager conMngr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); //creamos un conectivity manager para saber el estado de la network
        if (conMngr.getActiveNetworkInfo() != null && conMngr.getActiveNetworkInfo().isAvailable() && conMngr.getActiveNetworkInfo().isConnected()){

            return true;
        }else{
            Toast.makeText(context, "Se agoto el tiempo de espera, vuelva a intentar", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
