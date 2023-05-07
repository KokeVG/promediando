package koke.promediando;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PopUp_confi_rango extends Activity {

    TextView confirmar, cancelar;
    EditText nota_max;
    EditText nota_min;
    EditText nota_aprobatoria;

    //base de datos
    SharedPreferences preferencia;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_confi_rango);

        confirmar = findViewById(R.id.t_agregar4);
        cancelar = findViewById(R.id.t_cancelar4);
        nota_min = findViewById(R.id.nota_min2);
        nota_max = findViewById(R.id.nota_max2);
        nota_aprobatoria = findViewById(R.id.nota_aprobatoria2);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int ancho = dm.widthPixels;
        int alto = dm.heightPixels;

        getWindow().setLayout((int)(ancho*.85),(int)(alto*.35));

        WindowManager.LayoutParams parametro = getWindow().getAttributes();
        parametro.gravity = Gravity.CENTER;
        parametro.x = 0;
        parametro.y = 0;
        getWindow().setAttributes(parametro);

        preferencia = getSharedPreferences("BDNotas", MODE_PRIVATE);
        myEditor = preferencia.edit();

        nota_min.setHint(String.valueOf(preferencia.getFloat("NOTA_MIN", 1.0f)));
        nota_max.setHint(String.valueOf(preferencia.getFloat("NOTA_MAX", 7.0f)));
        nota_aprobatoria.setHint(String.valueOf(preferencia.getFloat("NOTA_APROBATORIA", 4.0f)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobar_llenado()){
                    if(comprobar_contenido()){
                        if(comprobar_logica()) {
                            myEditor.putFloat("NOTA_MIN", Float.parseFloat(nota_min.getText().toString()));
                            myEditor.putFloat("NOTA_MAX", Float.parseFloat(nota_max.getText().toString()));
                            myEditor.putFloat("NOTA_APROBATORIA", Float.parseFloat(nota_aprobatoria.getText().toString()));
                            myEditor.commit();
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Recuerde que la nota mínima debe ser menor que la nota aprobatoria y la nota aprobatoria debe ser menor que la nota máxima.",Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Ingrese una nota como por ejemplo: 7 ó 7.0",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Llene todos los espacios.",Toast.LENGTH_SHORT).show();
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

    private boolean comprobar_llenado(){
        if(nota_min.getText().toString().equals("")){
            return false;
        }
        if(nota_max.getText().toString().equals("")){
            return false;
        }
        if(nota_aprobatoria.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private boolean comprobar_contenido(){
        if(!contiene_numero(nota_min.getText().toString())){
            return false;
        }
        if(!contiene_numero(nota_max.getText().toString())){
            return false;
        }
        if(!contiene_numero(nota_aprobatoria.getText().toString())){
            return false;
        }
        return true;
    }

    private boolean contiene_numero(String texto){
        if(texto.contains("1")){
            return true;
        }
        else if(texto.contains("2")){
            return true;
        }
        else if(texto.contains("3")){
            return true;
        }
        else if(texto.contains("4")){
            return true;
        }
        else if(texto.contains("5")){
            return true;
        }
        else if(texto.contains("6")){
            return true;
        }
        else if(texto.contains("7")){
            return true;
        }
        else if(texto.contains("8")){
            return true;
        }
        else if(texto.contains("9")){
            return true;
        }
        else if(texto.contains("0")){
            return true;
        }
        return false;
    }

    private boolean comprobar_logica(){
        if(Float.parseFloat(nota_min.getText().toString()) < Float.parseFloat(nota_aprobatoria.getText().toString()) && Float.parseFloat(nota_aprobatoria.getText().toString()) < Float.parseFloat(nota_max.getText().toString())){
            return true;
        }
        return false;
    }
}
