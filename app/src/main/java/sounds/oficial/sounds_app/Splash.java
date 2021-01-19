package sounds.oficial.sounds_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;//revisar por el loading largo
import android.os.Bundle;
import android.util.Log;


public class Splash extends AppCompatActivity {
    Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstRun", true);
        editor.commit();


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //MANTENER POSICION ONLY VERTICAL
        setContentView(R.layout.activity_splash);        //REFERENCIA TO THE VIEW activity_splash
        final Thread thread;                                    //aqui
        thread = new Thread() {
            @Override
            public void run() {             //se ejecuta una arreglito para correr el hilo con imteset de 3000
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (internetBaglantisiVarMi()){
                        Intent i = new Intent(context, MainActivity.class); //clase principal
                        startActivity(i);// inica la actividad
                        finish();



                    }else{
                        Intent i = new Intent(context, Sinconexion.class);//clase opcional si falta internet
                        startActivity(i);// inica la actividad
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
    boolean internetBaglantisiVarMi(){
        ConnectivityManager conMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); ////conecta con el servicio de contenido de la pagina
        if (conMngr.getActiveNetworkInfo() != null && conMngr.getActiveNetworkInfo().isAvailable() && conMngr.getActiveNetworkInfo().isConnected()){
            return true;
        }else{
            return false;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        if (!firstRun) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Log.d("TAG1", "firstRun(false): " + Boolean.valueOf(firstRun).toString());
        } else {
            Log.d("TAG1", "firstRun(true): " + Boolean.valueOf(firstRun).toString());
        }
    }
}
