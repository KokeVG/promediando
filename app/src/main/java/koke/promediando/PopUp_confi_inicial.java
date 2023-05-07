package koke.promediando;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PopUp_confi_inicial extends Activity {

    TextView confirmar;
    EditText nota_max;
    EditText nota_min;
    EditText nota_aprobatoria;
    Spinner spinner;
    ArrayList<String> nombres_spinner;
    int opcion_spinner;

    //base de datos
    SharedPreferences preferencia;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_confi_inicial);

        confirmar = findViewById(R.id.t_agregar3);
        nota_min = findViewById(R.id.nota_min);
        nota_max = findViewById(R.id.nota_max);
        nota_aprobatoria = findViewById(R.id.nota_aprobatoria);
        spinner = findViewById(R.id.spinner);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int ancho = dm.widthPixels;
        int alto = dm.heightPixels;

        getWindow().setLayout((int)(ancho*.85),(int)(alto*.4));

        WindowManager.LayoutParams parametro = getWindow().getAttributes();
        parametro.gravity = Gravity.CENTER;
        parametro.x = 0;
        parametro.y = 0;
        getWindow().setAttributes(parametro);

        preferencia = getSharedPreferences("BDNotas", MODE_PRIVATE);
        myEditor = preferencia.edit();

        nombres_spinner = new ArrayList<>();

        nombres_spinner.add("Seleccione un tipo para redondear o truncar.");
        nombres_spinner.add("Redondear a 1 decimal.");
        nombres_spinner.add("Redondear a 2 decimales.");
        nombres_spinner.add("Truncar a 1 decimal.");
        nombres_spinner.add("Truncar a 2 decimales.");
        ArrayAdapter adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres_spinner);
        spinner.setAdapter(adaptador);
        opcion_spinner=0;
    }

    @Override
    protected void onStart() {
        super.onStart();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(pos == 0){
                    opcion_spinner=0;
                }
                else if (pos == 1){
                    opcion_spinner=1;
                }
                else if (pos == 2){
                    opcion_spinner=2;
                }
                else if (pos == 3){
                    opcion_spinner=3;
                }
                else if (pos == 4){
                    opcion_spinner=4;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobar_llenado()){
                    if(comprobar_contenido()){
                        if(opcion_spinner != 0){
                            if(comprobar_logica()) {
                                myEditor.putFloat("NOTA_MIN", Float.parseFloat(nota_min.getText().toString()));
                                myEditor.putFloat("NOTA_MAX", Float.parseFloat(nota_max.getText().toString()));
                                myEditor.putFloat("NOTA_APROBATORIA", Float.parseFloat(nota_aprobatoria.getText().toString()));
                                guardar_seleccion_spinner();
                                myEditor.putBoolean("PrimerInicio?", false);
                                myEditor.commit();
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Recuerde que la nota mínima debe ser menor que la nota aprobatoria y la nota aprobatoria debe ser menor que la nota máxima.",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Seleccione un tipo para redondear o truncar.",Toast.LENGTH_LONG).show();
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

    private void guardar_seleccion_spinner(){
        if(opcion_spinner == 1){
            myEditor.putInt("Redondear", 1);
            myEditor.remove("Truncar");
        }
        else if (opcion_spinner == 2){
            myEditor.putInt("Redondear", 2);
            myEditor.remove("Truncar");
        }
        else if (opcion_spinner == 3){
            myEditor.putInt("Truncar", 1);
            myEditor.remove("Redondear");
        }
        else if (opcion_spinner == 4){
            myEditor.putInt("Truncar", 2);
            myEditor.remove("Redondear");
        }
    }
}
