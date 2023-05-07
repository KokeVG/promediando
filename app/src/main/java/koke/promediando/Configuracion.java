package koke.promediando;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Configuracion extends AppCompatActivity {

    TextView rango_notas;
    Spinner spinner;
    ArrayList<String> nombres_spinner;

    //base de datos
    SharedPreferences preferencia;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        //agrega boton atras a la action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configuración");

        rango_notas = findViewById(R.id.rango_notas);
        spinner = findViewById(R.id.spinner2);

        preferencia = getSharedPreferences("BDNotas", MODE_PRIVATE);
        myEditor = preferencia.edit();

        nombres_spinner = new ArrayList<>();
        nombres_spinner.add("Redondear a 1 decimal.");
        nombres_spinner.add("Redondear a 2 decimales.");
        nombres_spinner.add("Truncar a 1 decimal.");
        nombres_spinner.add("Truncar a 2 decimales.");
        ArrayAdapter adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres_spinner);
        spinner.setAdapter(adaptador);
    }

    @Override
    protected void onStart() {
        super.onStart();
        spinner.setSelection(obtener_pos_spinner());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(pos == 0){
                    myEditor.putInt("Redondear", 1);
                    myEditor.remove("Truncar");
                }
                else if (pos == 1){
                    myEditor.putInt("Redondear", 2);
                    myEditor.remove("Truncar");
                }
                else if (pos == 2){
                    myEditor.putInt("Truncar", 1);
                    myEditor.remove("Redondear");
                }
                else if (pos == 3){
                    myEditor.putInt("Truncar", 2);
                    myEditor.remove("Redondear");
                }
                myEditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rango_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rango = new Intent(Configuracion.this, PopUp_confi_rango.class);
                startActivity(rango);
            }
        });
    }

    private int obtener_pos_spinner(){
        if(preferencia.getInt("Redondear", 0) == 1){
            return 0;
        }
        else if(preferencia.getInt("Redondear", 0) == 2){
            return 1;
        }
        else if(preferencia.getInt("Truncar", 0) == 1){
            return 2;
        }
        else if(preferencia.getInt("Truncar", 0) == 2){
            return 3;
        }
        return 0;
    }
}
