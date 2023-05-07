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

public class PopUp_agregar extends Activity {

    TextView agregar, cancelar;
    EditText escribir_nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar);

        agregar = findViewById(R.id.t_agregar2);
        cancelar = findViewById(R.id.t_cancelar2);
        escribir_nombre = findViewById(R.id.inserta_nombre);

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
                    if(!Menu_asignaturas.nombre_asignaturas.contains(escribir_nombre.getText().toString())){
                        Menu_asignaturas.nombre_popup = escribir_nombre.getText().toString();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "El nombre de asignatura ya existe", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ingrese un nombre a la asignatura.", Toast.LENGTH_SHORT).show();
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
