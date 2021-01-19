package sounds.oficial.sounds_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;



public class Sinconexion extends AppCompatActivity {
    Button btnYenile;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sinconexion);



//*        ImageView img = (ImageView)findViewById(R.id.loadingView);
        //     img.setBackgroundResource(R.drawable.loading);

        //      AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        //     frameAnimation.start();*//
        // frameAnimation.stop();

        btnYenile = findViewById(R.id.btn1);
        btnYenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // accion para boton de activity_splash
                Intent intent = new Intent(context,Splash.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
