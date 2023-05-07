package koke.promediando;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Menu_asignaturas extends AppCompatActivity {

    LinearLayout contenedor_asignaturas, contenedor_eliminar;
    LinearLayout asignatura1,asignatura2,asignatura3,asignatura4,asignatura5,asignatura6;
    CheckBox check1,check2,check3,check4,check5,check6;
    TextView cancelar,aceptar;
    TextView b_asignatura1,b_asignatura2,b_asignatura3,b_asignatura4,b_asignatura5,b_asignatura6;
    TextView promedio1,promedio2,promedio3,promedio4,promedio5,promedio6;
    int cont_asignaturas;

    public static String nombre_popup;
    public static String nombre_asignatura_nuevo_promedio, promedio_nuevo;
    public static ArrayList<String> nombre_asignaturas;
    SharedPreferences preferencia, datos_asignatura;
    SharedPreferences.Editor myEditor, editor_asignatura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_asignaturas);

        //agrega boton atras a la action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Asignaturas");

        preferencia = getSharedPreferences("BaseDeDatosPromediando", MODE_PRIVATE);
        myEditor = preferencia.edit();

        nombre_popup = null;
        nombre_asignatura_nuevo_promedio = null;
        promedio_nuevo = null;
        nombre_asignaturas = new ArrayList<>();

        contenedor_asignaturas = findViewById(R.id.contenedor_asignaturas);
        contenedor_eliminar = findViewById(R.id.contenedor_eliminar);
        asignatura1 = findViewById(R.id.primero);
        asignatura2 = findViewById(R.id.segundo);
        asignatura3 = findViewById(R.id.tercero);
        asignatura4 = findViewById(R.id.cuarto);
        asignatura5 = findViewById(R.id.quinto);
        asignatura6 = findViewById(R.id.sexto);
        check1 = findViewById(R.id.checkbox1);
        check2 = findViewById(R.id.checkbox2);
        check3 = findViewById(R.id.checkbox3);
        check4 = findViewById(R.id.checkbox4);
        check5 = findViewById(R.id.checkbox5);
        check6 = findViewById(R.id.checkbox6);
        b_asignatura1 = findViewById(R.id.boton1);
        b_asignatura2 = findViewById(R.id.boton2);
        b_asignatura3 = findViewById(R.id.boton3);
        b_asignatura4 = findViewById(R.id.boton4);
        b_asignatura5 = findViewById(R.id.boton5);
        b_asignatura6 = findViewById(R.id.boton6);
        promedio1 = findViewById(R.id.promedio1);
        promedio2 = findViewById(R.id.promedio2);
        promedio3 = findViewById(R.id.promedio3);
        promedio4 = findViewById(R.id.promedio4);
        promedio5 = findViewById(R.id.promedio5);
        promedio6 = findViewById(R.id.promedio6);

        cancelar = findViewById(R.id.cancelar);
        aceptar = findViewById(R.id.aceptar);

        check1.setChecked(true);
        check2.setChecked(true);
        check3.setChecked(true);
        check4.setChecked(true);
        check5.setChecked(true);
        check6.setChecked(true);
        eliminarAsignaturasInicio();
        cont_asignaturas = 0;

        cargarDatos();
    }


    @Override
    protected void onStart() {
        super.onStart();

        b_asignatura1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llama_notas = new Intent(Menu_asignaturas.this,Menu_notas.class);
                llama_notas.putExtra("nombre", b_asignatura1.getText().toString());
                startActivity(llama_notas);
            }
        });

        b_asignatura2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llama_notas = new Intent(Menu_asignaturas.this,Menu_notas.class);
                llama_notas.putExtra("nombre", b_asignatura2.getText().toString());
                startActivity(llama_notas);
            }
        });

        b_asignatura3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llama_notas = new Intent(Menu_asignaturas.this,Menu_notas.class);
                llama_notas.putExtra("nombre", b_asignatura3.getText().toString());
                startActivity(llama_notas);
            }
        });

        b_asignatura4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llama_notas = new Intent(Menu_asignaturas.this,Menu_notas.class);
                llama_notas.putExtra("nombre", b_asignatura4.getText().toString());
                startActivity(llama_notas);
            }
        });

        b_asignatura5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llama_notas = new Intent(Menu_asignaturas.this,Menu_notas.class);
                llama_notas.putExtra("nombre", b_asignatura5.getText().toString());
                startActivity(llama_notas);
            }
        });

        b_asignatura6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llama_notas = new Intent(Menu_asignaturas.this,Menu_notas.class);
                llama_notas.putExtra("nombre", b_asignatura6.getText().toString());
                startActivity(llama_notas);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_asignaturas_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //boton atras
    @Override
    public void onBackPressed() {
        guardarDatos();
        super.onBackPressed();
    }

    //boton atras de la actionbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    //boton home
    @Override
    protected void onUserLeaveHint() {
        guardarDatos();
        super.onUserLeaveHint();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(nombre_popup != null){
            agregarAsignatura(nombre_popup);
            nombre_asignaturas.add(nombre_popup);
            nombre_popup = null;
            guardarDatos();
        }
        else if (nombre_asignatura_nuevo_promedio != null){
            cambiarNuevoPromedio(nombre_asignatura_nuevo_promedio, promedio_nuevo);
            nombre_asignatura_nuevo_promedio = null;
            promedio_nuevo = null;
            guardarDatos();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_agregar:
                if(cont_asignaturas < 6){
                    Intent agregar_nombre = new Intent(getApplicationContext(),PopUp_agregar.class);
                    startActivity(agregar_nombre);
                    onPause();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Solo puedes agregar hasta un máximo de 6 asignaturas.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.accion_borrar:
                if(cont_asignaturas !=0){
                    hacerVisible();

                    aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            eliminarAsignaturas();
                            hacerInvisible();
                            guardarDatos();
                        }
                    });

                    cancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hacerInvisible();
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"No hay asignaturas para eliminar.", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Metodos para agregar asignatura

    private void agregarAsignatura(String nombre){
        if (b_asignatura1.getText().toString().equals("Asignatura 1.")){
            b_asignatura1.setText(nombre);
            contenedor_asignaturas.addView(asignatura1);
            cont_asignaturas++;
        }
        else if (b_asignatura2.getText().toString().equals("Asignatura 2.")){
            b_asignatura2.setText(nombre);
            contenedor_asignaturas.addView(asignatura2);
            cont_asignaturas++;
        }
        else if (b_asignatura3.getText().toString().equals("Asignatura 3.")){
            b_asignatura3.setText(nombre);
            contenedor_asignaturas.addView(asignatura3);
            cont_asignaturas++;
        }
        else if (b_asignatura4.getText().toString().equals("Asignatura 4.")){
            b_asignatura4.setText(nombre);
            contenedor_asignaturas.addView(asignatura4);
            cont_asignaturas++;
        }
        else if (b_asignatura5.getText().toString().equals("Asignatura 5.")){
            b_asignatura5.setText(nombre);
            contenedor_asignaturas.addView(asignatura5);
            cont_asignaturas++;
        }
        else if (b_asignatura6.getText().toString().equals("Asignatura 6.")){
            b_asignatura6.setText(nombre);
            contenedor_asignaturas.addView(asignatura6);
            cont_asignaturas++;
        }
        reservarDatos(nombre);
    }

    //Metodos para eliminar asignatura

    private void hacerInvisible(){
        check1.setVisibility(View.INVISIBLE);
        check2.setVisibility(View.INVISIBLE);
        check3.setVisibility(View.INVISIBLE);
        check4.setVisibility(View.INVISIBLE);
        check5.setVisibility(View.INVISIBLE);
        check6.setVisibility(View.INVISIBLE);

        check1.setChecked(false);
        check2.setChecked(false);
        check3.setChecked(false);
        check4.setChecked(false);
        check5.setChecked(false);
        check6.setChecked(false);

        contenedor_eliminar.setVisibility(View.INVISIBLE);
    }

    private void hacerVisible(){
        check1.setVisibility(View.VISIBLE);
        check2.setVisibility(View.VISIBLE);
        check3.setVisibility(View.VISIBLE);
        check4.setVisibility(View.VISIBLE);
        check5.setVisibility(View.VISIBLE);
        check6.setVisibility(View.VISIBLE);

        contenedor_eliminar.setVisibility(View.VISIBLE);
    }

    private void eliminarAsignaturasInicio(){

        if(check1.isChecked() || check2.isChecked() || check3.isChecked() || check4.isChecked() || check5.isChecked() || check6.isChecked()){
            if(check1.isChecked()){
                check1.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura1.getText().toString());
                }b_asignatura1.setText("Asignatura 1.");
                promedio1.setText("0.0");
                contenedor_asignaturas.removeView(asignatura1);
                cont_asignaturas--;
            }
            if(check2.isChecked()){
                check2.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura2.getText().toString());
                }
                b_asignatura2.setText("Asignatura 2.");
                promedio2.setText("0.0");
                contenedor_asignaturas.removeView(asignatura2);
                cont_asignaturas--;
            }
            if(check3.isChecked()){
                check3.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura3.getText().toString());
                }
                b_asignatura3.setText("Asignatura 3.");
                promedio3.setText("0.0");
                contenedor_asignaturas.removeView(asignatura3);
                cont_asignaturas--;
            }
            if(check4.isChecked()){
                check4.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura4.getText().toString());
                }
                b_asignatura4.setText("Asignatura 4.");
                promedio4.setText("0.0");
                contenedor_asignaturas.removeView(asignatura4);
                cont_asignaturas--;
            }
            if(check5.isChecked()){
                check5.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura5.getText().toString());
                }
                b_asignatura5.setText("Asignatura 5.");
                promedio5.setText("0.0");
                contenedor_asignaturas.removeView(asignatura5);
                cont_asignaturas--;
            }
            if(check6.isChecked()){
                check6.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura6.getText().toString());
                }
                b_asignatura6.setText("Asignatura 6.");
                promedio6.setText("0.0");
                contenedor_asignaturas.removeView(asignatura6);
                cont_asignaturas--;
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Seleccione alguna asignatura para eliminar.", Toast.LENGTH_SHORT).show();
        }
    }

    //temporal
    private void eliminarAsignaturas(){

        if(check1.isChecked() || check2.isChecked() || check3.isChecked() || check4.isChecked() || check5.isChecked() || check6.isChecked()){
            if(check1.isChecked()){
                check1.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura1.getText().toString());
                }
                borrarDatosRespectivos(b_asignatura1.getText().toString());
                b_asignatura1.setText("Asignatura 1.");
                promedio1.setText("0.0");
                contenedor_asignaturas.removeView(asignatura1);
                cont_asignaturas--;
            }
            if(check2.isChecked()){
                check2.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura2.getText().toString());
                }
                borrarDatosRespectivos(b_asignatura2.getText().toString());
                b_asignatura2.setText("Asignatura 2.");
                promedio2.setText("0.0");
                contenedor_asignaturas.removeView(asignatura2);
                cont_asignaturas--;
            }
            if(check3.isChecked()){
                check3.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura3.getText().toString());
                }
                borrarDatosRespectivos(b_asignatura3.getText().toString());
                b_asignatura3.setText("Asignatura 3.");
                promedio3.setText("0.0");
                contenedor_asignaturas.removeView(asignatura3);
                cont_asignaturas--;
            }
            if(check4.isChecked()){
                check4.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura4.getText().toString());
                }
                borrarDatosRespectivos(b_asignatura4.getText().toString());
                b_asignatura4.setText("Asignatura 4.");
                promedio4.setText("0.0");
                contenedor_asignaturas.removeView(asignatura4);
                cont_asignaturas--;
            }
            if(check5.isChecked()){
                check5.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura5.getText().toString());
                }
                borrarDatosRespectivos(b_asignatura5.getText().toString());
                b_asignatura5.setText("Asignatura 5.");
                promedio5.setText("0.0");
                contenedor_asignaturas.removeView(asignatura5);
                cont_asignaturas--;
            }
            if(check6.isChecked()){
                check6.setChecked(false);
                if(nombre_asignaturas.size() != 0){
                    nombre_asignaturas.remove(b_asignatura6.getText().toString());
                }
                borrarDatosRespectivos(b_asignatura6.getText().toString());
                b_asignatura6.setText("Asignatura 6.");
                promedio6.setText("0.0");
                contenedor_asignaturas.removeView(asignatura6);
                cont_asignaturas--;
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Seleccione alguna asignatura para eliminar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarDatos(){
        myEditor.putInt("cont_asignaturas", cont_asignaturas);
        myEditor.putString("Texto_boton1", b_asignatura1.getText().toString());
        myEditor.putString("Promedio1", promedio1.getText().toString());
        myEditor.putString("Texto_boton2", b_asignatura2.getText().toString());
        myEditor.putString("Promedio2", promedio2.getText().toString());
        myEditor.putString("Texto_boton3", b_asignatura3.getText().toString());
        myEditor.putString("Promedio3", promedio3.getText().toString());
        myEditor.putString("Texto_boton4", b_asignatura4.getText().toString());
        myEditor.putString("Promedio4", promedio4.getText().toString());
        myEditor.putString("Texto_boton5", b_asignatura5.getText().toString());
        myEditor.putString("Promedio5", promedio5.getText().toString());
        myEditor.putString("Texto_boton6", b_asignatura6.getText().toString());
        myEditor.putString("Promedio6", promedio6.getText().toString());
        myEditor.commit();
    }

    private void cargarDatos(){
        cont_asignaturas = preferencia.getInt("cont_asignaturas", 0);
        b_asignatura1.setText(preferencia.getString("Texto_boton1", "Asignatura 1."));
        if(!b_asignatura1.getText().toString().equals("Asignatura 1.")){
            contenedor_asignaturas.addView(asignatura1);
            promedio1.setText(preferencia.getString("Promedio1", "8.0"));
            nombre_asignaturas.add(b_asignatura1.getText().toString());
        }
        b_asignatura2.setText(preferencia.getString("Texto_boton2", "Asignatura 2."));
        if(!b_asignatura2.getText().toString().equals("Asignatura 2.")){
            contenedor_asignaturas.addView(asignatura2);
            promedio2.setText(preferencia.getString("Promedio2", "8.0"));
            nombre_asignaturas.add(b_asignatura2.getText().toString());
        }
        b_asignatura3.setText(preferencia.getString("Texto_boton3", "Asignatura 3."));
        if(!b_asignatura3.getText().toString().equals("Asignatura 3.")){
            contenedor_asignaturas.addView(asignatura3);
            promedio3.setText(preferencia.getString("Promedio3", "8.0"));
            nombre_asignaturas.add(b_asignatura3.getText().toString());
        }
        b_asignatura4.setText(preferencia.getString("Texto_boton4", "Asignatura 4."));
        if(!b_asignatura4.getText().toString().equals("Asignatura 4.")){
            contenedor_asignaturas.addView(asignatura4);
            promedio4.setText(preferencia.getString("Promedio4", "8.0"));
            nombre_asignaturas.add(b_asignatura4.getText().toString());
        }
        b_asignatura5.setText(preferencia.getString("Texto_boton5", "Asignatura 5."));
        if(!b_asignatura5.getText().toString().equals("Asignatura 5.")){
            contenedor_asignaturas.addView(asignatura5);
            promedio5.setText(preferencia.getString("Promedio5", "8.0"));
            nombre_asignaturas.add(b_asignatura5.getText().toString());
        }
        b_asignatura6.setText(preferencia.getString("Texto_boton6", "Asignatura 6."));
        if(!b_asignatura6.getText().toString().equals("Asignatura 6.")){
            contenedor_asignaturas.addView(asignatura6);
            promedio6.setText(preferencia.getString("Promedio6", "8.0"));
            nombre_asignaturas.add(b_asignatura6.getText().toString());
        }
    }

    private void reservarDatos(String nombre){
        if(!getSharedPreferences("Asignatura1", MODE_PRIVATE).contains("titulo")){
            datos_asignatura = getSharedPreferences("Asignatura1", MODE_PRIVATE);
        }
        else if(!getSharedPreferences("Asignatura2", MODE_PRIVATE).contains("titulo")){
            datos_asignatura = getSharedPreferences("Asignatura2", MODE_PRIVATE);
        }
        else if(!getSharedPreferences("Asignatura3", MODE_PRIVATE).contains("titulo")){
            datos_asignatura = getSharedPreferences("Asignatura3", MODE_PRIVATE);
        }
        else if(!getSharedPreferences("Asignatura4", MODE_PRIVATE).contains("titulo")){
            datos_asignatura = getSharedPreferences("Asignatura4", MODE_PRIVATE);
        }
        else if(!getSharedPreferences("Asignatura5", MODE_PRIVATE).contains("titulo")){
            datos_asignatura = getSharedPreferences("Asignatura5", MODE_PRIVATE);
        }
        else if(!getSharedPreferences("Asignatura6", MODE_PRIVATE).contains("titulo")){
            datos_asignatura = getSharedPreferences("Asignatura6", MODE_PRIVATE);
        }
        editor_asignatura = datos_asignatura.edit();
        editor_asignatura.putString("titulo", nombre);
        editor_asignatura.commit();
    }

    private void borrarDatosRespectivos(String nombre){
        if(getSharedPreferences("Asignatura1", MODE_PRIVATE).getString("titulo", "").equals(nombre)){
            getSharedPreferences("Asignatura1", MODE_PRIVATE).edit().clear().apply();
        }
        else if(getSharedPreferences("Asignatura2", MODE_PRIVATE).getString("titulo", "").equals(nombre)){
            getSharedPreferences("Asignatura2", MODE_PRIVATE).edit().clear().apply();
        }
        else if(getSharedPreferences("Asignatura3", MODE_PRIVATE).getString("titulo", "").equals(nombre)){
            getSharedPreferences("Asignatura3", MODE_PRIVATE).edit().clear().apply();
        }
        else if(getSharedPreferences("Asignatura4", MODE_PRIVATE).getString("titulo", "").equals(nombre)){
            getSharedPreferences("Asignatura4", MODE_PRIVATE).edit().clear().apply();
        }
        else if(getSharedPreferences("Asignatura5", MODE_PRIVATE).getString("titulo", "").equals(nombre)){
            getSharedPreferences("Asignatura5", MODE_PRIVATE).edit().clear().apply();
        }
        else if(getSharedPreferences("Asignatura6", MODE_PRIVATE).getString("titulo", "").equals(nombre)){
            getSharedPreferences("Asignatura6", MODE_PRIVATE).edit().clear().apply();
        }
    }

    private void cambiarNuevoPromedio(String nombre, String promedio){
        if(b_asignatura1.getText().toString().equals(nombre)){
            promedio1.setText(promedio);
        }
        else if(b_asignatura2.getText().toString().equals(nombre)){
            promedio2.setText(promedio);
        }
        else if(b_asignatura3.getText().toString().equals(nombre)){
            promedio3.setText(promedio);
        }
        else if(b_asignatura4.getText().toString().equals(nombre)){
            promedio4.setText(promedio);
        }
        else if(b_asignatura5.getText().toString().equals(nombre)){
            promedio5.setText(promedio);
        }
        else if(b_asignatura6.getText().toString().equals(nombre)){
            promedio6.setText(promedio);
        }
    }
}

