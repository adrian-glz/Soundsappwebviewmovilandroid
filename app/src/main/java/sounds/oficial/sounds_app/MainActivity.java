package sounds.oficial.sounds_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pushbots.push.Pushbots;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "NOTICIAS";


    WebView webView,etc;
    String url = "https://www.sounds.mx/";
    ProgressDialog mProgressDialog;
    Context context = this;
    CustomWebViewClient webViewClient;
    NetworkChangeReceiver receiver;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ////


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Mantener pocision de xml en vertical desde metodo de la clase
        setContentView(R.layout.activity_main);


        //Register for Push Notifications
        Pushbots.sharedInstance().registerForRemoteNotifications();
        //Receiveri register ediyoruz
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);
        //SwipeRefreshLayuout
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!internetBaglantisiVarMi()){
                    Intent i = new Intent(context, Sinconexion.class);      //Funcion para refrescar con modo swipe
                    startActivity(i);
                    finish();
                }
                webView.reload();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //mProgressDialog
        mProgressDialog = new ProgressDialog(context,R.style.MyAlertDialogStyle);
        mProgressDialog.setMessage("Cargando...");
        webViewClient = new CustomWebViewClient();


        webView = findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(webViewClient);

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }
    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (!mProgressDialog.isShowing()){
                mProgressDialog.show();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //error == 8{
            // Timeout.

            // }
        }
    }

    @Override   //EVENTEO PARA HABILITAR FUNCION VOLVER POR TELEFONO
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            MainActivity.super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    boolean internetBaglantisiVarMi(){
        ConnectivityManager conMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMngr.getActiveNetworkInfo() != null && conMngr.getActiveNetworkInfo().isAvailable() && conMngr.getActiveNetworkInfo().isConnected()){
            return true;
        }else{
            return false;
        }
    }
}
