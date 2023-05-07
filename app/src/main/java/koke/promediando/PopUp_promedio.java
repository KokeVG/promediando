package koke.promediando;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class PopUp_promedio extends Activity {

    TextView asignatura, promedio_obtenido, promedio_asignado;
    TextView mensaje;
    TextView confirmar;

    float promedio, nota_aprobatoria;
    String nombre_asignatura;
    SharedPreferences preferencia, preferencia2;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_promedio);

        promedio = getIntent().getFloatExtra("promedio", 0);
        preferencia = getSharedPreferences("BDNotas", MODE_PRIVATE);
        nota_aprobatoria = preferencia.getFloat("NOTA_APROBATORIA", 4.0f);
        nombre_asignatura = getIntent().getStringExtra("nombre");

        asignatura = findViewById(R.id.asignatura);
        promedio_obtenido = findViewById(R.id.promedio_obtenido);
        promedio_asignado = findViewById(R.id.promedio_asignado);
        mensaje = findViewById(R.id.mensaje_promedio);
        confirmar = findViewById(R.id.t_agregar5);

        asignatura.setText(nombre_asignatura);
        promedio_obtenido.setText(String.valueOf(promedio));
        promedio_asignado.setText(String.valueOf(nota_aprobatoria));
        asignar_mensaje();

        preferencia2 = getSharedPreferences("BaseDeDatosPromediando", MODE_PRIVATE);
        myEditor = preferencia2.edit();
        Menu_asignaturas.nombre_asignatura_nuevo_promedio = nombre_asignatura;
        Menu_asignaturas.promedio_nuevo = String.valueOf(promedio);
        almacenar_promedio();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int ancho = dm.widthPixels;
        int alto = dm.heightPixels;

        getWindow().setLayout((int)(ancho*.8),(int)(alto*.35));

        WindowManager.LayoutParams parametro = getWindow().getAttributes();
        parametro.gravity = Gravity.CENTER;
        parametro.x = 0;
        parametro.y = 0;
        getWindow().setAttributes(parametro);
    }

    @Override
    protected void onStart() {
        super.onStart();

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void asignar_mensaje(){
        if(promedio > nota_aprobatoria){
            mensaje.setText("Apruebas la asignatura con un promedio de " + String.valueOf(promedio) + ", felicidades.");
        }
        else if (promedio == nota_aprobatoria){
            mensaje.setText("Apruebas la asignatura con un promedio de " + String.valueOf(promedio) + ", que suerte tienes.");
        }
        else if (promedio < nota_aprobatoria){
            mensaje.setText("Repruebas la asignatura con un promedio de " + String.valueOf(promedio) + ", será para la próxima.");
        }
    }

    private void almacenar_promedio(){
        if(preferencia2.getString("Texto_boton1", "").equals(nombre_asignatura)){
            myEditor.putString("Promedio1", String.valueOf(promedio));
        }
        else if(preferencia2.getString("Texto_boton2", "").equals(nombre_asignatura)){
            myEditor.putString("Promedio2", String.valueOf(promedio));
        }
        else if(preferencia2.getString("Texto_boton3", "").equals(nombre_asignatura)){
            myEditor.putString("Promedio3", String.valueOf(promedio));
        }
        else if(preferencia2.getString("Texto_boton4", "").equals(nombre_asignatura)){
            myEditor.putString("Promedio4", String.valueOf(promedio));
        }
        else if(preferencia2.getString("Texto_boton5", "").equals(nombre_asignatura)){
            myEditor.putString("Promedio5", String.valueOf(promedio));
        }
        else if(preferencia2.getString("Texto_boton6", "").equals(nombre_asignatura)){
            myEditor.putString("Promedio6", String.valueOf(promedio));
        }
        myEditor.commit();
    }
}
