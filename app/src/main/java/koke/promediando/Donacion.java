package koke.promediando;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class Donacion extends AppCompatActivity implements RewardedVideoAdListener {

    Button b_video;
    LinearLayout bloque_reproducir, bloque_nointernet;
    TextView mensaje1;
    ImageView imagen_gracias;

    RewardedVideoAd mRewardedVideoAd;

    //variables premio
    String contenido;
    int cantidad;

    boolean video_completado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion);
        getSupportActionBar().setTitle("Ayudar");

        bloque_reproducir = findViewById(R.id.bloque_reproducir);
        bloque_nointernet = findViewById(R.id.bloque_conexion);
        b_video = findViewById(R.id.b_video);
        mensaje1 = findViewById(R.id.mensaje_ayudar);
        imagen_gracias = findViewById(R.id.imagen_gracias);

        mensaje1.setText("En este apartado de la aplicación usted podrá brindar ayuda para que la aplicación pueda seguir activo y gratuito, además ayuda a agregar y mejorar sus funcionalidades.");

        video_completado=false;

        //publicidad video
        MobileAds.initialize(this, "ca-app-pub-3401273703654950~6936205954");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        //comprobar conexion a internet
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            bloque_nointernet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        b_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mRewardedVideoAd.show();
            }
        });
    }

    //boton atras
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //boton atras de la actionbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3401273703654950/7538845727",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewarded(RewardItem reward) {
        cantidad = reward.getAmount();
        contenido = reward.getType();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        //no hacer nada
    }

    @Override
    public void onRewardedVideoAdClosed() {
        if(!video_completado){
            loadRewardedVideoAd();
            bloque_reproducir.setVisibility(View.GONE);
            mensaje1.setText("No se completo el video, podria intentarlo de nuevo?");
        }
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        bloque_nointernet.setVisibility(View.GONE);
        bloque_reproducir.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //no hacer nada
    }

    @Override
    public void onRewardedVideoStarted() {
        //no hacer nada
    }

    @Override
    public void onRewardedVideoCompleted() {
        bloque_reproducir.setVisibility(View.GONE);
        mensaje1.setText("Muchas gracias por ayudar, se agradece mucho.");
        imagen_gracias.setVisibility(View.VISIBLE);
        video_completado=true;
    }

    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
}
