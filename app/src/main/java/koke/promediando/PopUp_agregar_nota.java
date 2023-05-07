package koke.promediando;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PopUp_agregar_nota extends Activity {

    EditText escribir_nombre;
    TextView agregar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_nota);

        agregar = findViewById(R.id.t_agregar);
        cancelar = findViewById(R.id.t_cancelar);
        escribir_nombre = findViewById(R.id.inserta_nombre2);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int ancho = dm.widthPixels;
        int alto = dm.heightPixels;

        getWindow().setLayout((int)(ancho*.6),(int)(alto*.2));

        WindowManager.LayoutParams parametro = getWindow().getAttributes();
        parametro.gravity = Gravity.CENTER;
        parametro.x = 0;
        parametro.y = 0;
        getWindow().setAttributes(parametro);
    }

    @Override
    protected void onStart() {
        super.onStart();

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!escribir_nombre.getText().toString().equals("")){
                    Menu_notas.nombre_popup = escribir_nombre.getText().toString();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ingrese un nombre a la evaluación.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
