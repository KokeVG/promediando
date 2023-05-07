package koke.promediando;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Menu_principal extends AppCompatActivity {

    Button b_asignaturas;
    Button b_salir;
    Button b_donar;
    AlertDialog.Builder dialogo;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        MobileAds.initialize(this, "ca-app-pub-3401273703654950~6936205954");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3401273703654950/8814393241");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdClosed() {
                finish();
            }
        });

        b_asignaturas = findViewById(R.id.b_asignatura);
        b_donar = findViewById(R.id.b_donar);
        b_salir = findViewById(R.id.b_salir);
        dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Promediando")
                .setMessage("¿Seguro que desea salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        else {
                            finish();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no hacer nada
                    }
                })
                .setCancelable(false);
        dialogo.create();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_principal_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_configuracion:
                Intent confi = new Intent(Menu_principal.this, Configuracion.class);
                startActivity(confi);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(getSharedPreferences("BDNotas", MODE_PRIVATE).getBoolean("PrimerInicio?",true)){
            Intent confi_inicial = new Intent(Menu_principal.this, PopUp_confi_inicial.class);
            startActivity(confi_inicial);
        }

        b_asignaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asignaturas = new Intent(Menu_principal.this, Menu_asignaturas.class);
                startActivity(asignaturas);
            }
        });

        b_donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_donar = new Intent(Menu_principal.this, Donacion.class);
                startActivity(intent_donar);
            }
        });

        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        dialogo.show();
    }
}
