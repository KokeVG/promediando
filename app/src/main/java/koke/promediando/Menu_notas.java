package koke.promediando;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.math.BigDecimal;

public class Menu_notas extends AppCompatActivity {

    String titulo;
    int cont_bloques, cont_notas1, cont_notas2, iden_eliminar;
    boolean visible_bloque1;
    boolean visible_bloque2;

    //bloques
    LinearLayout contenedor_bloques, primer_bloque, segundo_bloque;
    TextView espacio1, espacio2, espaciofinal;
    CheckBox check_bloque1, check_bloque2;
    //bloque 1
    Button agregar1_notas, eliminar1_notas;
    CheckBox check_ponderado_bloque1;
    EditText edit_ponderado_bloque1;
    LinearLayout primera_nota1, primera_nota2, primera_nota3, primera_nota4, primera_nota5, primera_nota6;
    TextView nombre1, nombre2, nombre3, nombre4, nombre5, nombre6;
    CheckBox check_nota1, check_nota2, check_nota3, check_nota4, check_nota5, check_nota6;
    EditText porcentaje1, porcentaje2, porcentaje3, porcentaje4, porcentaje5, porcentaje6;
    EditText nota1, nota2, nota3, nota4, nota5, nota6;
    //bloque 2
    Button agregar2_notas, eliminar2_notas;
    CheckBox check_ponderado_bloque2;
    EditText edit_ponderado_bloque2;
    LinearLayout primera_nota7, primera_nota8, primera_nota9, primera_nota10, primera_nota11, primera_nota12;
    TextView nombre7, nombre8, nombre9, nombre10, nombre11, nombre12;
    CheckBox check_nota7, check_nota8, check_nota9, check_nota10, check_nota11, check_nota12;
    EditText porcentaje7, porcentaje8, porcentaje9, porcentaje10, porcentaje11, porcentaje12;
    EditText nota7, nota8, nota9, nota10, nota11, nota12;
    //eliminar bloques
    LinearLayout contenedor_eliminar_bloques;
    TextView boton_confirmar, boton_cancelar;

    //agregar nota
    public static String nombre_popup;
    int bloque;

    //base de datos
    SharedPreferences preferencia;
    SharedPreferences.Editor myEditor;

    //variables para calcular
    float nota_bloque1;
    float sumador_bloque1;
    float nota_final_bloque1;
    float nota_bloque2;
    float sumador_bloque2;
    float nota_final_bloque2;
    float nota_final;
    float suma_porcentaje_bloques;
    float suma_porcentaje_bloque1;
    float suma_porcentaje_bloque2;
    Intent intent_promedio;

    //variables notas del usuario
    float NOTA_MAX;
    float NOTA_MIN;

    //variables para redondear o truncar
    boolean redondear, truncar;
    int cantidad_decimales;


    //publicidad
    InterstitialAd mInterstitialAd;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_notas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titulo = getIntent().getStringExtra("nombre");
        getSupportActionBar().setTitle(titulo);

        intent_promedio = new Intent(Menu_notas.this, PopUp_promedio.class);

        nombre_popup = null;
        bloque = 0;
        visible_bloque1=false;
        visible_bloque2=false;

        MobileAds.initialize(this, "ca-app-pub-3401273703654950~6936205954");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3401273703654950/7818047328");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdClosed() {
                Menu_notas.super.onBackPressed();
            }
        });

        //espacios
        espacio1 = findViewById(R.id.espacio1);
        espacio2 = findViewById(R.id.espacio2);
        espaciofinal = findViewById(R.id.espaciofinal);

        //bloques
        contenedor_bloques = findViewById(R.id.contenedor_bloques);
        primer_bloque = findViewById(R.id.primer_bloque);
        segundo_bloque = findViewById(R.id.segundo_bloque);

        //bloque 1
        check_bloque1 = findViewById(R.id.check_bloque1);
        agregar1_notas = findViewById(R.id.agregar1); eliminar1_notas = findViewById(R.id.eliminar1);
        check_ponderado_bloque1 = findViewById(R.id.check_ponderado1);
        edit_ponderado_bloque1 = findViewById(R.id.ponderado_bloque1);
        primera_nota1 = findViewById(R.id.primera_nota1); primera_nota2 = findViewById(R.id.primera_nota2); primera_nota3 = findViewById(R.id.primera_nota3);
        primera_nota4 = findViewById(R.id.primera_nota4); primera_nota5 = findViewById(R.id.primera_nota5); primera_nota6 = findViewById(R.id.primera_nota6);
        nombre1 = findViewById(R.id.titulo1); nombre2 = findViewById(R.id.titulo2); nombre3 = findViewById(R.id.titulo3);
        nombre4 = findViewById(R.id.titulo4); nombre5 = findViewById(R.id.titulo5); nombre6 = findViewById(R.id.titulo6);
        check_nota1 = findViewById(R.id.check_nota1); check_nota2 = findViewById(R.id.check_nota2); check_nota3 = findViewById(R.id.check_nota3);
        check_nota4 = findViewById(R.id.check_nota4); check_nota5 = findViewById(R.id.check_nota5); check_nota6 = findViewById(R.id.check_nota6);
        porcentaje1 = findViewById(R.id.porcentaje1); porcentaje2 = findViewById(R.id.porcentaje2); porcentaje3 = findViewById(R.id.porcentaje3);
        porcentaje4 = findViewById(R.id.porcentaje4); porcentaje5 = findViewById(R.id.porcentaje5); porcentaje6 = findViewById(R.id.porcentaje6);
        nota1 = findViewById(R.id.nota1); nota2 = findViewById(R.id.nota2); nota3 = findViewById(R.id.nota3);
        nota4 = findViewById(R.id.nota4); nota5 = findViewById(R.id.nota5); nota6 = findViewById(R.id.nota6);

        //bloque 2
        check_bloque2 = findViewById(R.id.check_bloque2);
        agregar2_notas = findViewById(R.id.agregar2); eliminar2_notas = findViewById(R.id.eliminar2);
        check_ponderado_bloque2 = findViewById(R.id.check_ponderado2);
        edit_ponderado_bloque2 = findViewById(R.id.ponderado_bloque2);
        primera_nota7 = findViewById(R.id.primera_nota7); primera_nota8 = findViewById(R.id.primera_nota8); primera_nota9 = findViewById(R.id.primera_nota9);
        primera_nota10 = findViewById(R.id.primera_nota10); primera_nota11 = findViewById(R.id.primera_nota11); primera_nota12 = findViewById(R.id.primera_nota12);
        nombre7 = findViewById(R.id.titulo7); nombre8 = findViewById(R.id.titulo8); nombre9 = findViewById(R.id.titulo9);
        nombre10 = findViewById(R.id.titulo10); nombre11 = findViewById(R.id.titulo11); nombre12 = findViewById(R.id.titulo12);
        check_nota7 = findViewById(R.id.check_nota7); check_nota8 = findViewById(R.id.check_nota8); check_nota9 = findViewById(R.id.check_nota9);
        check_nota10 = findViewById(R.id.check_nota10); check_nota11 = findViewById(R.id.check_nota11); check_nota12 = findViewById(R.id.check_nota12);
        porcentaje7 = findViewById(R.id.porcentaje7); porcentaje8 = findViewById(R.id.porcentaje8); porcentaje9 = findViewById(R.id.porcentaje9);
        porcentaje10 = findViewById(R.id.porcentaje10); porcentaje11 = findViewById(R.id.porcentaje11); porcentaje12 = findViewById(R.id.porcentaje12);
        nota7 = findViewById(R.id.nota7); nota8 = findViewById(R.id.nota8); nota9 = findViewById(R.id.nota9);
        nota10 = findViewById(R.id.nota10); nota11 = findViewById(R.id.nota11); nota12 = findViewById(R.id.nota12);

        //eliminar bloques
        contenedor_eliminar_bloques = findViewById(R.id.contenedor_eliminar);
        boton_confirmar = findViewById(R.id.aceptar_bloques);
        boton_cancelar = findViewById(R.id.cancelar_bloques);

        //sacar layout de los bloques
        check_bloque1.setChecked(true);
        check_bloque2.setChecked(true);
        eliminarBloques();

        cont_bloques=0; cont_notas1=0; cont_notas2=0; iden_eliminar=0;

        //base de datos
        if(titulo.equals(getSharedPreferences("Asignatura1", MODE_PRIVATE).getString("titulo", ""))){
            preferencia = getSharedPreferences("Asignatura1", MODE_PRIVATE);
            myEditor = preferencia.edit();
            cargarDatos();
        }
        else if(titulo.equals(getSharedPreferences("Asignatura2", MODE_PRIVATE).getString("titulo", ""))){
            preferencia = getSharedPreferences("Asignatura2", MODE_PRIVATE);
            myEditor = preferencia.edit();
            cargarDatos();
        }
        else if(titulo.equals(getSharedPreferences("Asignatura3", MODE_PRIVATE).getString("titulo", ""))){
            preferencia = getSharedPreferences("Asignatura3", MODE_PRIVATE);
            myEditor = preferencia.edit();
            cargarDatos();
        }
        else if(titulo.equals(getSharedPreferences("Asignatura4", MODE_PRIVATE).getString("titulo", ""))){
            preferencia = getSharedPreferences("Asignatura4", MODE_PRIVATE);
            myEditor = preferencia.edit();
            cargarDatos();
        }
        else if(titulo.equals(getSharedPreferences("Asignatura5", MODE_PRIVATE).getString("titulo", ""))){
            preferencia = getSharedPreferences("Asignatura5", MODE_PRIVATE);
            myEditor = preferencia.edit();
            cargarDatos();
        }
        else if(titulo.equals(getSharedPreferences("Asignatura6", MODE_PRIVATE).getString("titulo", ""))){
            preferencia = getSharedPreferences("Asignatura6", MODE_PRIVATE);
            myEditor = preferencia.edit();
            cargarDatos();
        }

        inicializarValoresCalcular();

        NOTA_MAX = getSharedPreferences("BDNotas", MODE_PRIVATE).getFloat("NOTA_MAX", 7.0f);
        NOTA_MIN = getSharedPreferences("BDNotas", MODE_PRIVATE).getFloat("NOTA_MIN", 1.0f);

        //inicializar hints de notas
        nota1.setHint(String.valueOf(NOTA_MAX));
        nota2.setHint(String.valueOf(NOTA_MAX));
        nota3.setHint(String.valueOf(NOTA_MAX));
        nota4.setHint(String.valueOf(NOTA_MAX));
        nota5.setHint(String.valueOf(NOTA_MAX));
        nota6.setHint(String.valueOf(NOTA_MAX));
        nota7.setHint(String.valueOf(NOTA_MAX));
        nota8.setHint(String.valueOf(NOTA_MAX));
        nota9.setHint(String.valueOf(NOTA_MAX));
        nota10.setHint(String.valueOf(NOTA_MAX));
        nota11.setHint(String.valueOf(NOTA_MAX));
        nota12.setHint(String.valueOf(NOTA_MAX));

        redondear=false;
        truncar=false;
        cantidad_decimales=0;

        if(getSharedPreferences("BDNotas", MODE_PRIVATE).getInt("Redondear", 0) == 1){
            redondear=true;
            truncar=false;
            cantidad_decimales=1;
        }
        else if(getSharedPreferences("BDNotas", MODE_PRIVATE).getInt("Redondear", 0) == 2){
            redondear=true;
            truncar=false;
            cantidad_decimales=2;
        }
        else if(getSharedPreferences("BDNotas", MODE_PRIVATE).getInt("Truncar", 0) == 1){
            truncar=true;
            redondear=false;
            cantidad_decimales=1;
        }
        else if(getSharedPreferences("BDNotas", MODE_PRIVATE).getInt("Truncar", 0) == 2){
            truncar=true;
            redondear=false;
            cantidad_decimales=2;
        }
        else {
            Toast.makeText(getApplicationContext(),"no pesco", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_notas_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_calcular:
                if(iden_eliminar == 0){
                    calcular_nota();
                    inicializarValoresCalcular();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Termine la eliminación.", Toast.LENGTH_SHORT).show();
                }
                guardarDatos();
                return true;
            case R.id.accion_agregar:
                if(iden_eliminar == 0){
                    if(cont_bloques<2){
                        agregarBloque();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Solo puedes agregar hasta un máximo de 2 bloques ponderados.", Toast.LENGTH_SHORT).show();
                    }
                    guardarDatos();
                }
                return true;
            case R.id.accion_borrar:
                if(iden_eliminar == 0){
                    if(cont_bloques>0){
                        iden_eliminar = 1;
                        hacerVisibleCheckBloques();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No hay bloques para eliminar.", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //boton atras
    @Override
    public void onBackPressed() {
        guardarDatos();
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
        else {
            super.onBackPressed();
        }
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
            if(bloque == 1){
                agregarNotas1(nombre_popup);
            }
            else if(bloque == 2){
                agregarNotas2(nombre_popup);
            }
            guardarDatos();
            nombre_popup = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //bloque 1
        agregar1_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iden_eliminar == 0){
                    if(cont_notas1 < 6){
                        bloque = 1;
                        Intent agregar_nombre1 = new Intent(getApplicationContext(),PopUp_agregar_nota.class);
                        startActivity(agregar_nombre1);
                        onPause();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Solo puedes agregar hasta un máximo de 6 notas por bloque ponderado.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        eliminar1_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iden_eliminar == 0){
                    if(cont_notas1 > 0){
                        iden_eliminar = 2;
                        hacerVisibleCheckNotas1();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No hay evaluaciones, en este bloque, para eliminar.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //bloque 2
        agregar2_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iden_eliminar == 0){
                    if(cont_notas2 < 6){
                        bloque = 2;
                        Intent agregar_nombre2 = new Intent(getApplicationContext(),PopUp_agregar_nota.class);
                        startActivity(agregar_nombre2);
                        onPause();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Solo puedes agregar hasta un máximo de 6 notas por bloque ponderado.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        eliminar2_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iden_eliminar == 0){
                    if(cont_notas2 > 0){
                        iden_eliminar = 3;
                        hacerVisibleCheckNotas2();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No hay evaluaciones, en este bloque, para eliminar.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //botones eliminar
        boton_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmar bloques
                if(iden_eliminar == 1){
                    eliminarBloques();
                    hacerInvisibleCheckBloques();
                }
                //confirmar notas bloque 1
                else if(iden_eliminar == 2){
                    eliminarNotas1();
                    hacerInvisibleCheckNotas1();
                }
                //confirmar notas bloque 2
                else if(iden_eliminar == 3){
                    eliminarNotas2();
                    hacerInvisibleCheckNotas2();
                }
                guardarDatos();
                iden_eliminar = 0;
            }
        });
        boton_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancelar bloques
                if(iden_eliminar == 1){
                    hacerInvisibleCheckBloques();
                }
                //cancelar notas bloque 1
                else if(iden_eliminar == 2){
                    hacerInvisibleCheckNotas1();
                }
                //cancelar notas bloque 2
                else if(iden_eliminar == 3){
                    hacerInvisibleCheckNotas2();
                }
                iden_eliminar = 0;
            }
        });

        check_ponderado_bloque1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    porcentaje1.setEnabled(true);
                    porcentaje2.setEnabled(true);
                    porcentaje3.setEnabled(true);
                    porcentaje4.setEnabled(true);
                    porcentaje5.setEnabled(true);
                    porcentaje6.setEnabled(true);
                }
                else{
                    porcentaje1.setEnabled(false);
                    porcentaje2.setEnabled(false);
                    porcentaje3.setEnabled(false);
                    porcentaje4.setEnabled(false);
                    porcentaje5.setEnabled(false);
                    porcentaje6.setEnabled(false);
                }
                guardarDatos();
            }
        });

        check_ponderado_bloque2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    porcentaje7.setEnabled(true);
                    porcentaje8.setEnabled(true);
                    porcentaje9.setEnabled(true);
                    porcentaje10.setEnabled(true);
                    porcentaje11.setEnabled(true);
                    porcentaje12.setEnabled(true);
                }
                else{
                    porcentaje7.setEnabled(false);
                    porcentaje8.setEnabled(false);
                    porcentaje9.setEnabled(false);
                    porcentaje10.setEnabled(false);
                    porcentaje11.setEnabled(false);
                    porcentaje12.setEnabled(false);
                }
                guardarDatos();
            }
        });
    }

    //metodos para eliminar notas
    private void eliminarNotas1(){
        if(check_nota1.isChecked() || check_nota2.isChecked() || check_nota3.isChecked() || check_nota4.isChecked() || check_nota5.isChecked() || check_nota6.isChecked()){
            if(check_nota1.isChecked()){
                primer_bloque.removeView(primera_nota1);
                check_nota1.setChecked(false);
                nombre1.setText("Nota 1.");
                porcentaje1.setText("");
                nota1.setText("");
                cont_notas1--;
            }
            if(check_nota2.isChecked()){
                primer_bloque.removeView(primera_nota2);
                check_nota2.setChecked(false);
                nombre2.setText("Nota 2.");
                porcentaje2.setText("");
                nota2.setText("");
                cont_notas1--;
            }
            if(check_nota3.isChecked()){
                primer_bloque.removeView(primera_nota3);
                check_nota3.setChecked(false);
                nombre3.setText("Nota 3.");
                porcentaje3.setText("");
                nota3.setText("");
                cont_notas1--;
            }
            if(check_nota4.isChecked()){
                primer_bloque.removeView(primera_nota4);
                check_nota4.setChecked(false);
                nombre4.setText("Nota 4.");
                porcentaje4.setText("");
                nota4.setText("");
                cont_notas1--;
            }
            if(check_nota5.isChecked()){
                primer_bloque.removeView(primera_nota5);
                check_nota5.setChecked(false);
                nombre5.setText("Nota 5.");
                porcentaje5.setText("");
                nota5.setText("");
                cont_notas1--;
            }
            if(check_nota6.isChecked()){
                primer_bloque.removeView(primera_nota6);
                check_nota6.setChecked(false);
                nombre6.setText("Nota 6.");
                porcentaje6.setText("");
                nota6.setText("");
                cont_notas1--;
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Seleccione alguna evaluación para eliminar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarNotas2(){
        if(check_nota7.isChecked() || check_nota8.isChecked() || check_nota9.isChecked() || check_nota10.isChecked() || check_nota11.isChecked() || check_nota12.isChecked()){
            if(check_nota7.isChecked()){
                segundo_bloque.removeView(primera_nota7);
                check_nota7.setChecked(false);
                nombre7.setText("Nota 1.");
                porcentaje7.setText("");
                nota7.setText("");
                cont_notas2--;
            }
            if(check_nota8.isChecked()){
                segundo_bloque.removeView(primera_nota8);
                check_nota8.setChecked(false);
                nombre8.setText("Nota 2.");
                porcentaje8.setText("");
                nota8.setText("");
                cont_notas2--;
            }
            if(check_nota9.isChecked()){
                segundo_bloque.removeView(primera_nota9);
                check_nota9.setChecked(false);
                nombre9.setText("Nota 3.");
                porcentaje9.setText("");
                nota9.setText("");
                cont_notas2--;
            }
            if(check_nota10.isChecked()){
                segundo_bloque.removeView(primera_nota10);
                check_nota10.setChecked(false);
                nombre10.setText("Nota 4.");
                porcentaje10.setText("");
                nota10.setText("");
                cont_notas2--;
            }
            if(check_nota11.isChecked()){
                segundo_bloque.removeView(primera_nota11);
                check_nota11.setChecked(false);
                nombre11.setText("Nota 5.");
                porcentaje11.setText("");
                nota11.setText("");
                cont_notas2--;
            }
            if(check_nota12.isChecked()){
                segundo_bloque.removeView(primera_nota12);
                check_nota12.setChecked(false);
                nombre12.setText("Nota 6.");
                porcentaje12.setText("");
                nota12.setText("");
                cont_notas2--;
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Seleccione alguna evaluación para eliminar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void hacerVisibleCheckNotas1(){
        check_nota1.setVisibility(View.VISIBLE);
        check_nota2.setVisibility(View.VISIBLE);
        check_nota3.setVisibility(View.VISIBLE);
        check_nota4.setVisibility(View.VISIBLE);
        check_nota5.setVisibility(View.VISIBLE);
        check_nota6.setVisibility(View.VISIBLE);
        hacerVisibleEliminar();
    }

    private void hacerInvisibleCheckNotas1(){
        check_nota1.setVisibility(View.INVISIBLE);
        check_nota2.setVisibility(View.INVISIBLE);
        check_nota3.setVisibility(View.INVISIBLE);
        check_nota4.setVisibility(View.INVISIBLE);
        check_nota5.setVisibility(View.INVISIBLE);
        check_nota6.setVisibility(View.INVISIBLE);
        hacerInvisibleEliminar();
    }

    private void hacerVisibleCheckNotas2(){
        check_nota7.setVisibility(View.VISIBLE);
        check_nota8.setVisibility(View.VISIBLE);
        check_nota9.setVisibility(View.VISIBLE);
        check_nota10.setVisibility(View.VISIBLE);
        check_nota11.setVisibility(View.VISIBLE);
        check_nota12.setVisibility(View.VISIBLE);
        hacerVisibleEliminar();
    }

    private void hacerInvisibleCheckNotas2(){
        check_nota7.setVisibility(View.INVISIBLE);
        check_nota8.setVisibility(View.INVISIBLE);
        check_nota9.setVisibility(View.INVISIBLE);
        check_nota10.setVisibility(View.INVISIBLE);
        check_nota11.setVisibility(View.INVISIBLE);
        check_nota12.setVisibility(View.INVISIBLE);
        hacerInvisibleEliminar();
    }


    //metodos para agregar notas
    private void agregarNotas1(String nombre){
        if(nombre1.getText().toString().equals("Nota 1.")){
            nombre1.setText(nombre);
            primer_bloque.addView(primera_nota1);
            cont_notas1++;
        }
        else if(nombre2.getText().toString().equals("Nota 2.")){
            nombre2.setText(nombre);
            primera_nota2.setVisibility(View.VISIBLE);
            primer_bloque.addView(primera_nota2);
            cont_notas1++;
        }
        else if(nombre3.getText().toString().equals("Nota 3.")){
            nombre3.setText(nombre);
            primera_nota3.setVisibility(View.VISIBLE);
            primer_bloque.addView(primera_nota3);
            cont_notas1++;
        }
        else if(nombre4.getText().toString().equals("Nota 4.")){
            nombre4.setText(nombre);
            primera_nota4.setVisibility(View.VISIBLE);
            primer_bloque.addView(primera_nota4);
            cont_notas1++;
        }
        else if(nombre5.getText().toString().equals("Nota 5.")){
            nombre5.setText(nombre);
            primera_nota5.setVisibility(View.VISIBLE);
            primer_bloque.addView(primera_nota5);
            cont_notas1++;
        }
        else if(nombre6.getText().toString().equals("Nota 6.")){
            nombre6.setText(nombre);
            primera_nota6.setVisibility(View.VISIBLE);
            primer_bloque.addView(primera_nota6);
            cont_notas1++;
        }
    }

    private void agregarNotas2(String nombre){
        if(nombre7.getText().toString().equals("Nota 1.")){
            nombre7.setText(nombre);
            primera_nota7.setVisibility(View.VISIBLE);
            segundo_bloque.addView(primera_nota7);
            cont_notas2++;
        }
        else if(nombre8.getText().toString().equals("Nota 2.")){
            nombre8.setText(nombre);
            primera_nota8.setVisibility(View.VISIBLE);
            segundo_bloque.addView(primera_nota8);
            cont_notas2++;
        }
        else if(nombre9.getText().toString().equals("Nota 3.")){
            nombre9.setText(nombre);
            primera_nota9.setVisibility(View.VISIBLE);
            segundo_bloque.addView(primera_nota9);
            cont_notas2++;
        }
        else if(nombre10.getText().toString().equals("Nota 4.")){
            nombre10.setText(nombre);
            primera_nota10.setVisibility(View.VISIBLE);
            segundo_bloque.addView(primera_nota10);
            cont_notas2++;
        }
        else if(nombre11.getText().toString().equals("Nota 5.")){
            nombre11.setText(nombre);
            primera_nota11.setVisibility(View.VISIBLE);
            segundo_bloque.addView(primera_nota11);
            cont_notas2++;
        }
        else if(nombre12.getText().toString().equals("Nota 6.")){
            nombre12.setText(nombre);
            primera_nota12.setVisibility(View.VISIBLE);
            segundo_bloque.addView(primera_nota12);
            cont_notas2++;
        }
    }

    //metodo de agregar bloque
    private void agregarBloque(){
        if(primer_bloque.getVisibility() == View.GONE){
            espacio1.setVisibility(View.INVISIBLE);
            primer_bloque.setVisibility(View.VISIBLE);
            cont_notas1=0;
            visible_bloque1=true;
            cont_bloques++;
        }
        else {
            espacio2.setVisibility(View.INVISIBLE);
            segundo_bloque.setVisibility(View.VISIBLE);
            cont_notas2=0;
            visible_bloque2=true;
            cont_bloques++;
        }
    }

    //terminar eliminar_bloques
    private void eliminarBloques(){
        if(check_bloque1.isChecked() || check_bloque2.isChecked()){
            if(check_bloque1.isChecked()){
                primer_bloque.setVisibility(View.GONE);
                check_bloque1.setChecked(false);
                check_ponderado_bloque1.setChecked(false);
                edit_ponderado_bloque1.setText("");
                eliminarNotasBloque1();
                contenedor_bloques.removeView(espaciofinal);
                contenedor_bloques.removeView(espacio1);
                contenedor_bloques.removeView(primer_bloque);
                contenedor_bloques.addView(espacio1);
                contenedor_bloques.addView(primer_bloque);
                contenedor_bloques.addView(espaciofinal);
                visible_bloque1=false;
                cont_bloques--;
            }
            if(check_bloque2.isChecked()){
                segundo_bloque.setVisibility(View.GONE);
                check_bloque2.setChecked(false);
                check_ponderado_bloque2.setChecked(false);
                edit_ponderado_bloque2.setText("");
                eliminarNotasBloque2();
                contenedor_bloques.removeView(espaciofinal);
                contenedor_bloques.removeView(espacio2);
                contenedor_bloques.removeView(segundo_bloque);
                contenedor_bloques.addView(espacio2);
                contenedor_bloques.addView(segundo_bloque);
                contenedor_bloques.addView(espaciofinal);
                visible_bloque2=false;
                cont_bloques--;
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Seleccione algún bloque para eliminar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarNotasBloque1(){
        check_nota1.setChecked(true);
        check_nota2.setChecked(true);
        check_nota3.setChecked(true);
        check_nota4.setChecked(true);
        check_nota5.setChecked(true);
        check_nota6.setChecked(true);
        eliminarNotas1();
    }

    private void eliminarNotasBloque2(){
        check_nota7.setChecked(true);
        check_nota8.setChecked(true);
        check_nota9.setChecked(true);
        check_nota10.setChecked(true);
        check_nota11.setChecked(true);
        check_nota12.setChecked(true);
        eliminarNotas2();
    }

    private void hacerInvisibleCheckBloques(){
        check_bloque1.setVisibility(View.INVISIBLE);
        check_bloque2.setVisibility(View.INVISIBLE);
        check_bloque1.setChecked(false);
        check_bloque2.setChecked(false);
        hacerInvisibleEliminar();
    }

    private void hacerVisibleCheckBloques(){
        check_bloque1.setVisibility(View.VISIBLE);
        check_bloque2.setVisibility(View.VISIBLE);
        hacerVisibleEliminar();
    }

    private void hacerVisibleEliminar(){
        espaciofinal.setVisibility(View.INVISIBLE);
        contenedor_eliminar_bloques.setVisibility(View.VISIBLE);
    }

    private void hacerInvisibleEliminar(){
        espaciofinal.setVisibility(View.GONE);
        contenedor_eliminar_bloques.setVisibility(View.GONE);
    }

    //metodos de la base de datos
    private void guardarDatos(){
        myEditor.putInt("cont_bloques", cont_bloques);
        myEditor.putInt("cont_notas1", cont_notas1);
        myEditor.putInt("cont_notas2", cont_notas2);
        myEditor.putBoolean("visible_bloque1", visible_bloque1);
        myEditor.putBoolean("visible_bloque2", visible_bloque2);
        myEditor.putBoolean("check_ponderado_bloque1", check_ponderado_bloque1.isChecked());
        myEditor.putBoolean("check_ponderado_bloque2", check_ponderado_bloque2.isChecked());
        myEditor.putString("edit_ponderado_bloque1", edit_ponderado_bloque1.getText().toString());
        myEditor.putString("edit_ponderado_bloque2", edit_ponderado_bloque2.getText().toString());
        myEditor.putString("nombre1", nombre1.getText().toString());
        myEditor.putString("porcentaje1", porcentaje1.getText().toString());
        myEditor.putString("nota1", nota1.getText().toString());
        myEditor.putString("nombre2", nombre2.getText().toString());
        myEditor.putString("porcentaje2", porcentaje2.getText().toString());
        myEditor.putString("nota2", nota2.getText().toString());
        myEditor.putString("nombre3", nombre3.getText().toString());
        myEditor.putString("porcentaje3", porcentaje3.getText().toString());
        myEditor.putString("nota3", nota3.getText().toString());
        myEditor.putString("nombre4", nombre4.getText().toString());
        myEditor.putString("porcentaje4", porcentaje4.getText().toString());
        myEditor.putString("nota4", nota4.getText().toString());
        myEditor.putString("nombre5", nombre5.getText().toString());
        myEditor.putString("porcentaje5", porcentaje5.getText().toString());
        myEditor.putString("nota5", nota5.getText().toString());
        myEditor.putString("nombre6", nombre6.getText().toString());
        myEditor.putString("porcentaje6", porcentaje6.getText().toString());
        myEditor.putString("nota6", nota6.getText().toString());
        myEditor.putString("nombre7", nombre7.getText().toString());
        myEditor.putString("porcentaje7", porcentaje7.getText().toString());
        myEditor.putString("nota7", nota7.getText().toString());
        myEditor.putString("nombre8", nombre8.getText().toString());
        myEditor.putString("porcentaje8", porcentaje8.getText().toString());
        myEditor.putString("nota8", nota8.getText().toString());
        myEditor.putString("nombre9", nombre9.getText().toString());
        myEditor.putString("porcentaje9", porcentaje9.getText().toString());
        myEditor.putString("nota9", nota9.getText().toString());
        myEditor.putString("nombre10", nombre10.getText().toString());
        myEditor.putString("porcentaje10", porcentaje10.getText().toString());
        myEditor.putString("nota10", nota10.getText().toString());
        myEditor.putString("nombre11", nombre11.getText().toString());
        myEditor.putString("porcentaje11", porcentaje11.getText().toString());
        myEditor.putString("nota11", nota11.getText().toString());
        myEditor.putString("nombre12", nombre12.getText().toString());
        myEditor.putString("porcentaje12", porcentaje12.getText().toString());
        myEditor.putString("nota12", nota12.getText().toString());
        myEditor.commit();
    }
    private void cargarDatos(){
        cont_bloques = preferencia.getInt("cont_bloques", 0);
        cont_notas1 = preferencia.getInt("cont_notas1", 0);
        cont_notas2 = preferencia.getInt("cont_notas2", 0);
        visible_bloque1 = preferencia.getBoolean("visible_bloque1", false);
        visible_bloque2 = preferencia.getBoolean("visible_bloque2", false);
        nombre1.setText(preferencia.getString("nombre1", "Nota 1."));
        nombre2.setText(preferencia.getString("nombre2", "Nota 2."));
        nombre3.setText(preferencia.getString("nombre3", "Nota 3."));
        nombre4.setText(preferencia.getString("nombre4", "Nota 4."));
        nombre5.setText(preferencia.getString("nombre5", "Nota 5."));
        nombre6.setText(preferencia.getString("nombre6", "Nota 6."));
        nombre7.setText(preferencia.getString("nombre7", "Nota 1."));
        nombre8.setText(preferencia.getString("nombre8", "Nota 2."));
        nombre9.setText(preferencia.getString("nombre9", "Nota 3."));
        nombre10.setText(preferencia.getString("nombre10", "Nota 4."));
        nombre11.setText(preferencia.getString("nombre11", "Nota 5."));
        nombre12.setText(preferencia.getString("nombre12", "Nota 6."));

        if(!nombre1.getText().toString().equals("Nota 1.")){
            porcentaje1.setText(preferencia.getString("porcentaje1", ""));
            nota1.setText(preferencia.getString("nota1", ""));
            primer_bloque.addView(primera_nota1);
        }
        if(!nombre2.getText().toString().equals("Nota 2.")){
            porcentaje2.setText(preferencia.getString("porcentaje2", ""));
            nota2.setText(preferencia.getString("nota2", ""));
            primer_bloque.addView(primera_nota2);
        }
        if(!nombre3.getText().toString().equals("Nota 3.")){
            porcentaje3.setText(preferencia.getString("porcentaje3", ""));
            nota3.setText(preferencia.getString("nota3", ""));
            primer_bloque.addView(primera_nota3);
        }
        if(!nombre4.getText().toString().equals("Nota 4.")){
            porcentaje4.setText(preferencia.getString("porcentaje4", ""));
            nota4.setText(preferencia.getString("nota4", ""));
            primer_bloque.addView(primera_nota4);
        }
        if(!nombre5.getText().toString().equals("Nota 5.")){
            porcentaje5.setText(preferencia.getString("porcentaje5", ""));
            nota5.setText(preferencia.getString("nota5", ""));
            primer_bloque.addView(primera_nota5);
        }
        if(!nombre6.getText().toString().equals("Nota 6.")){
            porcentaje6.setText(preferencia.getString("porcentaje6", ""));
            nota6.setText(preferencia.getString("nota6", ""));
            primer_bloque.addView(primera_nota6);
        }
        if(!nombre7.getText().toString().equals("Nota 1.")){
            porcentaje7.setText(preferencia.getString("porcentaje7", ""));
            nota7.setText(preferencia.getString("nota7", ""));
            segundo_bloque.addView(primera_nota7);
        }
        if(!nombre8.getText().toString().equals("Nota 2.")){
            porcentaje8.setText(preferencia.getString("porcentaje8", ""));
            nota8.setText(preferencia.getString("nota8", ""));
            segundo_bloque.addView(primera_nota8);
        }
        if(!nombre9.getText().toString().equals("Nota 3.")){
            porcentaje9.setText(preferencia.getString("porcentaje9", ""));
            nota9.setText(preferencia.getString("nota9", ""));
            segundo_bloque.addView(primera_nota9);
        }
        if(!nombre10.getText().toString().equals("Nota 4.")){
            porcentaje10.setText(preferencia.getString("porcentaje10", ""));
            nota10.setText(preferencia.getString("nota10", ""));
            segundo_bloque.addView(primera_nota10);
        }
        if(!nombre11.getText().toString().equals("Nota 5.")){
            porcentaje11.setText(preferencia.getString("porcentaje11", ""));
            nota11.setText(preferencia.getString("nota11", ""));
            segundo_bloque.addView(primera_nota11);
        }
        if(!nombre12.getText().toString().equals("Nota 6.")){
            porcentaje12.setText(preferencia.getString("porcentaje12", ""));
            nota12.setText(preferencia.getString("nota12", ""));
            segundo_bloque.addView(primera_nota12);
        }
        if(visible_bloque1){
            check_ponderado_bloque1.setChecked(preferencia.getBoolean("check_ponderado_bloque1", false));
            if(check_ponderado_bloque1.isChecked()){
                porcentaje1.setEnabled(true);
                porcentaje2.setEnabled(true);
                porcentaje3.setEnabled(true);
                porcentaje4.setEnabled(true);
                porcentaje5.setEnabled(true);
                porcentaje6.setEnabled(true);
            }
            else{
                porcentaje1.setEnabled(false);
                porcentaje2.setEnabled(false);
                porcentaje3.setEnabled(false);
                porcentaje4.setEnabled(false);
                porcentaje5.setEnabled(false);
                porcentaje6.setEnabled(false);
            }
            edit_ponderado_bloque1.setText(preferencia.getString("edit_ponderado_bloque1", ""));
            espacio1.setVisibility(View.INVISIBLE);
            primer_bloque.setVisibility(View.VISIBLE);
        }
        if(visible_bloque2){
            check_ponderado_bloque2.setChecked(preferencia.getBoolean("check_ponderado_bloque2", false));
            if(check_ponderado_bloque2.isChecked()){
                porcentaje7.setEnabled(true);
                porcentaje8.setEnabled(true);
                porcentaje9.setEnabled(true);
                porcentaje10.setEnabled(true);
                porcentaje11.setEnabled(true);
                porcentaje12.setEnabled(true);
            }
            else{
                porcentaje7.setEnabled(false);
                porcentaje8.setEnabled(false);
                porcentaje9.setEnabled(false);
                porcentaje10.setEnabled(false);
                porcentaje11.setEnabled(false);
                porcentaje12.setEnabled(false);
            }
            edit_ponderado_bloque2.setText(preferencia.getString("edit_ponderado_bloque2", ""));
            espacio2.setVisibility(View.INVISIBLE);
            segundo_bloque.setVisibility(View.VISIBLE);
        }
    }

    private void calcular_nota(){
        if(cont_bloques != 0){
            if(visible_bloque1 && visible_bloque2){
                if(cont_notas1 != 0){
                    if(cont_notas2 !=0){
                        if(comprobar_llenado1() && comprobar_llenado2()){
                            if(Float.parseFloat(edit_ponderado_bloque1.getText().toString()) < 100 && Float.parseFloat(edit_ponderado_bloque1.getText().toString()) > 0){
                                if(Float.parseFloat(edit_ponderado_bloque2.getText().toString()) < 100 && Float.parseFloat(edit_ponderado_bloque2.getText().toString()) > 0){
                                    suma_porcentaje_bloques=Float.parseFloat(edit_ponderado_bloque1.getText().toString()) + Float.parseFloat(edit_ponderado_bloque2.getText().toString());
                                    if(suma_porcentaje_bloques == 100){
                                        if(check_ponderado_bloque1.isChecked()){
                                            if(comprobar_rango_porcentaje_bloque1()){
                                                if(comprobar_suma_porcentaje_bloque1() == 100){
                                                    if(comprobar_logica_nota_bloque1()){
                                                        if(check_ponderado_bloque2.isChecked()){
                                                            if(comprobar_rango_porcentaje_bloque2()){
                                                                if(comprobar_suma_porcentaje_bloque2() == 100){
                                                                    if(comprobar_logica_nota_bloque2()){
                                                                        calcular_bloque1();
                                                                        calcular_bloque2();
                                                                        nota_final=redondear(nota_final_bloque1)+redondear(nota_final_bloque2);
                                                                        intent_promedio.putExtra("promedio", nota_final);
                                                                        intent_promedio.putExtra("nombre", titulo);
                                                                        startActivity(intent_promedio);
                                                                    }
                                                                    else {
                                                                        Toast.makeText(getApplicationContext(), "Las notas debe estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                                                    }
                                                                }
                                                                else {
                                                                    Toast.makeText(getApplicationContext(), "La suma de los porcentajes ponderados del segundo bloque debe ser igual a 100 (usted esta sumando " + String.valueOf(comprobar_suma_porcentaje_bloque2()) + ").",Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                            else {
                                                                Toast.makeText(getApplicationContext(), "Los porcentajes ponderados del segundo bloque deben estar entre 0 y 100 (sin incluir el 0).",Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                        else {
                                                            if(comprobar_logica_nota_bloque2()){
                                                                calcular_bloque1();
                                                                calcular_bloque2();
                                                                nota_final=redondear(nota_final_bloque1)+redondear(nota_final_bloque2);
                                                                intent_promedio.putExtra("promedio", nota_final);
                                                                intent_promedio.putExtra("nombre", titulo);
                                                                startActivity(intent_promedio);
                                                            }
                                                            else {
                                                                Toast.makeText(getApplicationContext(), "Las notas debe estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    }
                                                    else {
                                                        Toast.makeText(getApplicationContext(), "Las notas debe estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), "La suma de los porcentajes ponderados del primer bloque debe ser igual a 100 (usted esta sumando " + String.valueOf(comprobar_suma_porcentaje_bloque1()) + ").",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "Los porcentajes ponderados del primer bloque deben estar entre 0 y 100 (sin incluir el 0).",Toast.LENGTH_SHORT).show(); }
                                        }
                                        else {
                                            if(comprobar_logica_nota_bloque1()){
                                                if(check_ponderado_bloque2.isChecked()){
                                                    if(comprobar_rango_porcentaje_bloque2()) {
                                                        if(comprobar_suma_porcentaje_bloque2() == 100){
                                                            if(comprobar_logica_nota_bloque2()){
                                                                calcular_bloque1();
                                                                calcular_bloque2();
                                                                nota_final=redondear(nota_final_bloque1)+redondear(nota_final_bloque2);
                                                                intent_promedio.putExtra("promedio", nota_final);
                                                                intent_promedio.putExtra("nombre", titulo);
                                                                startActivity(intent_promedio);
                                                            }
                                                            else {
                                                                Toast.makeText(getApplicationContext(), "Las notas debe estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                        else {
                                                            Toast.makeText(getApplicationContext(), "La suma de los porcentajes ponderados del segundo bloque debe ser igual a 100 (usted esta sumando " + String.valueOf(comprobar_suma_porcentaje_bloque2()) + ").",Toast.LENGTH_LONG).show();

                                                        }
                                                    }
                                                    else {
                                                        Toast.makeText(getApplicationContext(), "Los porcentajes ponderados del segundo bloque deben estar entre 0 y 100 (sin incluir el 0).",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                else {
                                                    if(comprobar_logica_nota_bloque2()){
                                                        calcular_bloque1();
                                                        calcular_bloque2();
                                                        nota_final=redondear(nota_final_bloque1)+redondear(nota_final_bloque2);
                                                        intent_promedio.putExtra("promedio", nota_final);
                                                        intent_promedio.putExtra("nombre", titulo);
                                                        startActivity(intent_promedio);
                                                    }
                                                    else {
                                                        Toast.makeText(getApplicationContext(), "Las notas debe estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "Las notas debe estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "La suma de los porcentajes ponderados de los bloques debe ser igual a 100.",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "El porcentaje ponderado del segundo bloque debe estar entre 0 y 100 (no puede ser 0 ni 100).",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "El porcentaje ponderado del primer bloque debe estar entre 0 y 100 (no puede ser 0 ni 100).",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Llene los espacios en blanco.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Agregue notas al segundo bloque.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Agregue notas al primer bloque.", Toast.LENGTH_SHORT).show();
                }
            }
            else if(visible_bloque1){
                if(cont_notas1 != 0){
                    if(comprobar_llenado1()){
                        if(Float.parseFloat(edit_ponderado_bloque1.getText().toString()) == 100){
                            if(check_ponderado_bloque1.isChecked()) {
                                if(comprobar_rango_porcentaje_bloque1()){
                                    if(comprobar_suma_porcentaje_bloque1() == 100){
                                        if(comprobar_logica_nota_bloque1()){
                                            calcular_bloque1();
                                            intent_promedio.putExtra("promedio", redondear(nota_final_bloque1));
                                            intent_promedio.putExtra("nombre", titulo);
                                            startActivity(intent_promedio);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Las notas deben estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "La suma de los porcentajes ponderados del bloque debe ser igual a 100 (usted esta sumando " + String.valueOf(comprobar_suma_porcentaje_bloque1()) + ").",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Los porcentajes ponderados del bloque deben estar entre 0 y 100 (sin incluir el 0).",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                if(comprobar_logica_nota_bloque1()){
                                    calcular_bloque1();
                                    intent_promedio.putExtra("promedio", redondear(nota_final_bloque1));
                                    intent_promedio.putExtra("nombre", titulo);
                                    startActivity(intent_promedio);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Las notas debe estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "El bloque debe ponderar el 100.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Llene los espacios en blanco.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Agregue notas al bloque.", Toast.LENGTH_SHORT).show();
                }
            }
            else if(visible_bloque2){
                if(cont_notas2 != 0){
                    if(comprobar_llenado2()){
                        if(Float.parseFloat(edit_ponderado_bloque2.getText().toString()) == 100){
                            if(check_ponderado_bloque2.isChecked()){
                                if(comprobar_rango_porcentaje_bloque2()){
                                    if(comprobar_suma_porcentaje_bloque2() == 100){
                                        if(comprobar_logica_nota_bloque2()){
                                            calcular_bloque2();
                                            intent_promedio.putExtra("promedio", redondear(nota_final_bloque2));
                                            intent_promedio.putExtra("nombre", titulo);
                                            startActivity(intent_promedio);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Las notas deben estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "La suma de los porcentajes ponderados del bloque debe ser igual a 100 (usted esta sumando " + String.valueOf(comprobar_suma_porcentaje_bloque2()) + ").",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Los porcentajes ponderados del bloque deben estar entre 0 y 100 (sin incluir el 0).",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                if(comprobar_logica_nota_bloque2()){
                                    calcular_bloque2();
                                    intent_promedio.putExtra("promedio", redondear(nota_final_bloque2));
                                    intent_promedio.putExtra("nombre", titulo);
                                    startActivity(intent_promedio);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Las notas deben estar entre " + String.valueOf(NOTA_MIN) + " y " + String.valueOf(NOTA_MAX),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "El bloque debe ponderar el 100.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Llene los espacios en blanco.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Agregue notas al bloque.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Agregue bloques para poder calcular.", Toast.LENGTH_SHORT).show();
        }
    }

    private float comprobar_suma_porcentaje_bloque1(){
        suma_porcentaje_bloque1=0;
        if(!nombre1.getText().toString().equals("Nota 1.")){
            suma_porcentaje_bloque1+=Float.parseFloat(porcentaje1.getText().toString());
        }
        if(!nombre2.getText().toString().equals("Nota 2.")){
            suma_porcentaje_bloque1+=Float.parseFloat(porcentaje2.getText().toString());
        }
        if(!nombre3.getText().toString().equals("Nota 3.")){
            suma_porcentaje_bloque1+=Float.parseFloat(porcentaje3.getText().toString());
        }
        if(!nombre4.getText().toString().equals("Nota 4.")){
            suma_porcentaje_bloque1+=Float.parseFloat(porcentaje4.getText().toString());
        }
        if(!nombre5.getText().toString().equals("Nota 5.")){
            suma_porcentaje_bloque1+=Float.parseFloat(porcentaje5.getText().toString());
        }
        if(!nombre6.getText().toString().equals("Nota 6.")){
            suma_porcentaje_bloque1+=Float.parseFloat(porcentaje6.getText().toString());
        }
        return suma_porcentaje_bloque1;
    }

    private float comprobar_suma_porcentaje_bloque2(){
        suma_porcentaje_bloque2=0;
        if(!nombre7.getText().toString().equals("Nota 1.")){
            suma_porcentaje_bloque2+=Float.parseFloat(porcentaje7.getText().toString());
        }
        if(!nombre8.getText().toString().equals("Nota 2.")){
            suma_porcentaje_bloque2+=Float.parseFloat(porcentaje8.getText().toString());
        }
        if(!nombre9.getText().toString().equals("Nota 3.")){
            suma_porcentaje_bloque2+=Float.parseFloat(porcentaje9.getText().toString());
        }
        if(!nombre10.getText().toString().equals("Nota 4.")){
            suma_porcentaje_bloque2+=Float.parseFloat(porcentaje10.getText().toString());
        }
        if(!nombre11.getText().toString().equals("Nota 5.")){
            suma_porcentaje_bloque2+=Float.parseFloat(porcentaje11.getText().toString());
        }
        if(!nombre12.getText().toString().equals("Nota 6.")){
            suma_porcentaje_bloque2+=Float.parseFloat(porcentaje12.getText().toString());
        }
        return suma_porcentaje_bloque2;
    }

    private boolean comprobar_logica_nota_bloque1(){
        if(!nombre1.getText().toString().equals("Nota 1.")){
            if(Float.parseFloat(nota1.getText().toString()) < NOTA_MIN || Float.parseFloat(nota1.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre2.getText().toString().equals("Nota 2.")){
            if(Float.parseFloat(nota2.getText().toString()) < NOTA_MIN || Float.parseFloat(nota2.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre3.getText().toString().equals("Nota 3.")){
            if(Float.parseFloat(nota3.getText().toString()) < NOTA_MIN || Float.parseFloat(nota3.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre4.getText().toString().equals("Nota 4.")){
            if(Float.parseFloat(nota4.getText().toString()) < NOTA_MIN || Float.parseFloat(nota4.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre5.getText().toString().equals("Nota 5.")){
            if(Float.parseFloat(nota5.getText().toString()) < NOTA_MIN || Float.parseFloat(nota5.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre6.getText().toString().equals("Nota 6.")){
            if(Float.parseFloat(nota6.getText().toString()) < NOTA_MIN || Float.parseFloat(nota6.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        return true;
    }

    private boolean comprobar_logica_nota_bloque2(){
        if(!nombre7.getText().toString().equals("Nota 1.")){
            if(Float.parseFloat(nota7.getText().toString()) < NOTA_MIN || Float.parseFloat(nota7.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre8.getText().toString().equals("Nota 2.")){
            if(Float.parseFloat(nota8.getText().toString()) < NOTA_MIN || Float.parseFloat(nota8.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre9.getText().toString().equals("Nota 3.")){
            if(Float.parseFloat(nota9.getText().toString()) < NOTA_MIN || Float.parseFloat(nota9.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre10.getText().toString().equals("Nota 4.")){
            if(Float.parseFloat(nota10.getText().toString()) < NOTA_MIN || Float.parseFloat(nota10.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre11.getText().toString().equals("Nota 5.")){
            if(Float.parseFloat(nota11.getText().toString()) < NOTA_MIN || Float.parseFloat(nota11.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        if(!nombre12.getText().toString().equals("Nota 6.")){
            if(Float.parseFloat(nota12.getText().toString()) < NOTA_MIN || Float.parseFloat(nota12.getText().toString()) > NOTA_MAX){
                return false;
            }
        }
        return true;
    }

    private boolean comprobar_rango_porcentaje_bloque1(){
        if(!nombre1.getText().toString().equals("Nota 1.")){
            if(Float.parseFloat(porcentaje1.getText().toString()) <= 0 || Float.parseFloat(porcentaje1.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre2.getText().toString().equals("Nota 2.")){
            if(Float.parseFloat(porcentaje2.getText().toString()) <= 0 || Float.parseFloat(porcentaje2.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre3.getText().toString().equals("Nota 3.")){
            if(Float.parseFloat(porcentaje3.getText().toString()) <= 0 || Float.parseFloat(porcentaje3.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre4.getText().toString().equals("Nota 4.")){
            if(Float.parseFloat(porcentaje4.getText().toString()) <= 0 || Float.parseFloat(porcentaje4.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre5.getText().toString().equals("Nota 5.")){
            if(Float.parseFloat(porcentaje5.getText().toString()) <= 0 || Float.parseFloat(porcentaje5.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre6.getText().toString().equals("Nota 6.")){
            if(Float.parseFloat(porcentaje6.getText().toString()) <= 0 || Float.parseFloat(porcentaje6.getText().toString()) > 100){
                return false;
            }
        }
        return true;
    }

    private boolean comprobar_rango_porcentaje_bloque2(){
        if(!nombre7.getText().toString().equals("Nota 1.")){
            if(Float.parseFloat(porcentaje7.getText().toString()) <= 0 || Float.parseFloat(porcentaje7.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre8.getText().toString().equals("Nota 2.")){
            if(Float.parseFloat(porcentaje8.getText().toString()) <= 0 || Float.parseFloat(porcentaje8.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre9.getText().toString().equals("Nota 3.")){
            if(Float.parseFloat(porcentaje9.getText().toString()) <= 0 || Float.parseFloat(porcentaje9.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre10.getText().toString().equals("Nota 4.")){
            if(Float.parseFloat(porcentaje10.getText().toString()) <= 0 || Float.parseFloat(porcentaje10.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre11.getText().toString().equals("Nota 5.")){
            if(Float.parseFloat(porcentaje11.getText().toString()) <= 0 || Float.parseFloat(porcentaje11.getText().toString()) > 100){
                return false;
            }
        }
        if(!nombre12.getText().toString().equals("Nota 6.")){
            if(Float.parseFloat(porcentaje12.getText().toString()) <= 0 || Float.parseFloat(porcentaje12.getText().toString()) > 100){
                return false;
            }
        }
        return true;
    }

    private boolean comprobar_llenado1() {
        if(edit_ponderado_bloque1.getText().toString().isEmpty()){
            return false;
        }
        if(nota1.getText().toString().isEmpty() && !nombre1.getText().toString().equals("Nota 1.")){
            return false;
        }
        if(nota2.getText().toString().isEmpty() && !nombre2.getText().toString().equals("Nota 2.")){
            return false;
        }
        if(nota3.getText().toString().isEmpty() && !nombre3.getText().toString().equals("Nota 3.")){
            return false;
        }
        if(nota4.getText().toString().isEmpty() && !nombre4.getText().toString().equals("Nota 4.")){
            return false;
        }
        if(nota5.getText().toString().isEmpty() && !nombre5.getText().toString().equals("Nota 5.")){
            return false;
        }
        if(nota6.getText().toString().isEmpty() && !nombre6.getText().toString().equals("Nota 6.")){
            return false;
        }
        if(check_ponderado_bloque1.isChecked()){
            if(porcentaje1.getText().toString().isEmpty() && !nombre1.getText().toString().equals("Nota 1.")){
                return false;
            }
            if(porcentaje2.getText().toString().isEmpty() && !nombre2.getText().toString().equals("Nota 2.")){
                return false;
            }
            if(porcentaje3.getText().toString().isEmpty() && !nombre3.getText().toString().equals("Nota 3.")){
                return false;
            }
            if(porcentaje4.getText().toString().isEmpty() && !nombre4.getText().toString().equals("Nota 4.")){
                return false;
            }
            if(porcentaje5.getText().toString().isEmpty() && !nombre5.getText().toString().equals("Nota 5.")){
                return false;
            }
            if(porcentaje6.getText().toString().isEmpty() && !nombre6.getText().toString().equals("Nota 6.")){
                return false;
            }
        }
        return true;
    }

    private void calcular_bloque1(){
        if(check_ponderado_bloque1.isChecked()){
            if(!nombre1.getText().toString().equals("Nota 1.")){
                sumador_bloque1+=Float.parseFloat(nota1.getText().toString())*Float.parseFloat(porcentaje1.getText().toString())/100;
                suma_porcentaje_bloque1+=Float.parseFloat(porcentaje1.getText().toString());
            }
            if(!nombre2.getText().toString().equals("Nota 2.")){
                sumador_bloque1+=Float.parseFloat(nota2.getText().toString())*Float.parseFloat(porcentaje2.getText().toString())/100;
                suma_porcentaje_bloque1+=Float.parseFloat(porcentaje2.getText().toString());
            }
            if(!nombre3.getText().toString().equals("Nota 3.")){
                sumador_bloque1+=Float.parseFloat(nota3.getText().toString())*Float.parseFloat(porcentaje3.getText().toString())/100;
                suma_porcentaje_bloque1+=Float.parseFloat(porcentaje3.getText().toString());
            }
            if(!nombre4.getText().toString().equals("Nota 4.")){
                sumador_bloque1+=Float.parseFloat(nota4.getText().toString())*Float.parseFloat(porcentaje4.getText().toString())/100;
                suma_porcentaje_bloque1+=Float.parseFloat(porcentaje4.getText().toString());
            }
            if(!nombre5.getText().toString().equals("Nota 5.")){
                sumador_bloque1+=Float.parseFloat(nota5.getText().toString())*Float.parseFloat(porcentaje5.getText().toString())/100;
                suma_porcentaje_bloque1+=Float.parseFloat(porcentaje5.getText().toString());
            }
            if(!nombre6.getText().toString().equals("Nota 6.")){
                sumador_bloque1+=Float.parseFloat(nota6.getText().toString())*Float.parseFloat(porcentaje6.getText().toString())/100;
                suma_porcentaje_bloque1+=Float.parseFloat(porcentaje6.getText().toString());
            }
            nota_final_bloque1=sumador_bloque1*(Float.parseFloat(edit_ponderado_bloque1.getText().toString())/100);
        }
        else{
            if(!nombre1.getText().toString().equals("Nota 1.")){
                sumador_bloque1+=Float.parseFloat(nota1.getText().toString());
            }
            if(!nombre2.getText().toString().equals("Nota 2.")){
                sumador_bloque1+=Float.parseFloat(nota2.getText().toString());
            }
            if(!nombre3.getText().toString().equals("Nota 3.")){
                sumador_bloque1+=Float.parseFloat(nota3.getText().toString());
            }
            if(!nombre4.getText().toString().equals("Nota 4.")){
                sumador_bloque1+=Float.parseFloat(nota4.getText().toString());
            }
            if(!nombre5.getText().toString().equals("Nota 5.")){
                sumador_bloque1+=Float.parseFloat(nota5.getText().toString());
            }
            if(!nombre6.getText().toString().equals("Nota 6.")){
                sumador_bloque1+=Float.parseFloat(nota6.getText().toString());
            }
            nota_bloque1=sumador_bloque1/cont_notas1;
            nota_final_bloque1=nota_bloque1*(Float.parseFloat(edit_ponderado_bloque1.getText().toString())/100);
        }
    }

    //comprueba campos llenados y suma porcentajes igual a 100
    private boolean comprobar_llenado2() {
        if(edit_ponderado_bloque2.getText().toString().isEmpty()){
            return false;
        }
        if(nota7.getText().toString().isEmpty() && !nombre7.getText().toString().equals("Nota 1.")){
            return false;
        }
        if(nota8.getText().toString().isEmpty() && !nombre8.getText().toString().equals("Nota 2.")){
            return false;
        }
        if(nota9.getText().toString().isEmpty() && !nombre9.getText().toString().equals("Nota 3.")){
            return false;
        }
        if(nota10.getText().toString().isEmpty() && !nombre10.getText().toString().equals("Nota 4.")){
            return false;
        }
        if(nota11.getText().toString().isEmpty() && !nombre11.getText().toString().equals("Nota 5.")){
            return false;
        }
        if(nota12.getText().toString().isEmpty() && !nombre12.getText().toString().equals("Nota 6.")){
            return false;
        }
        if(check_ponderado_bloque2.isChecked()){
            if(porcentaje7.getText().toString().isEmpty() && !nombre7.getText().toString().equals("Nota 1.")){
                return false;
            }
            if(porcentaje8.getText().toString().isEmpty() && !nombre8.getText().toString().equals("Nota 2.")){
                return false;
            }
            if(porcentaje9.getText().toString().isEmpty() && !nombre9.getText().toString().equals("Nota 3.")){
                return false;
            }
            if(porcentaje10.getText().toString().isEmpty() && !nombre10.getText().toString().equals("Nota 4.")){
                return false;
            }
            if(porcentaje11.getText().toString().isEmpty() && !nombre11.getText().toString().equals("Nota 5.")){
                return false;
            }
            if(porcentaje12.getText().toString().isEmpty() && !nombre12.getText().toString().equals("Nota 6.")){
                return false;
            }
        }
        return true;
    }

    private void calcular_bloque2(){
        if(check_ponderado_bloque2.isChecked()){
            if(!nombre7.getText().toString().equals("Nota 1.")){
                sumador_bloque2+=Float.parseFloat(nota7.getText().toString())*Float.parseFloat(porcentaje7.getText().toString())/100;
                suma_porcentaje_bloque2+=Float.parseFloat(porcentaje7.getText().toString());
            }
            if(!nombre8.getText().toString().equals("Nota 2.")){
                sumador_bloque2+=Float.parseFloat(nota8.getText().toString())*Float.parseFloat(porcentaje8.getText().toString())/100;
                suma_porcentaje_bloque2+=Float.parseFloat(porcentaje8.getText().toString());
            }
            if(!nombre9.getText().toString().equals("Nota 3.")){
                sumador_bloque2+=Float.parseFloat(nota9.getText().toString())*Float.parseFloat(porcentaje9.getText().toString())/100;
                suma_porcentaje_bloque2+=Float.parseFloat(porcentaje9.getText().toString());
            }
            if(!nombre10.getText().toString().equals("Nota 4.")){
                sumador_bloque2+=Float.parseFloat(nota10.getText().toString())*Float.parseFloat(porcentaje10.getText().toString())/100;
                suma_porcentaje_bloque2+=Float.parseFloat(porcentaje10.getText().toString());
            }
            if(!nombre11.getText().toString().equals("Nota 5.")){
                sumador_bloque2+=Float.parseFloat(nota11.getText().toString())*Float.parseFloat(porcentaje11.getText().toString())/100;
                suma_porcentaje_bloque2+=Float.parseFloat(porcentaje11.getText().toString());
            }
            if(!nombre12.getText().toString().equals("Nota 6.")){
                sumador_bloque2+=Float.parseFloat(nota12.getText().toString())*Float.parseFloat(porcentaje12.getText().toString())/100;
                suma_porcentaje_bloque2+=Float.parseFloat(porcentaje12.getText().toString());
            }
            nota_final_bloque2=sumador_bloque2*(Float.parseFloat(edit_ponderado_bloque2.getText().toString())/100);
        }
        else{
            if(!nombre7.getText().toString().equals("Nota 1.")){
                sumador_bloque2+=Float.parseFloat(nota7.getText().toString());
            }
            if(!nombre8.getText().toString().equals("Nota 2.")){
                sumador_bloque2+=Float.parseFloat(nota8.getText().toString());
            }
            if(!nombre9.getText().toString().equals("Nota 3.")){
                sumador_bloque2+=Float.parseFloat(nota9.getText().toString());
            }
            if(!nombre10.getText().toString().equals("Nota 4.")){
                sumador_bloque2+=Float.parseFloat(nota10.getText().toString());
            }
            if(!nombre11.getText().toString().equals("Nota 5.")){
                sumador_bloque2+=Float.parseFloat(nota11.getText().toString());
            }
            if(!nombre12.getText().toString().equals("Nota 6.")){
                sumador_bloque2+=Float.parseFloat(nota12.getText().toString());
            }
            nota_bloque2=sumador_bloque2/cont_notas2;
            nota_final_bloque2=nota_bloque2*(Float.parseFloat(edit_ponderado_bloque2.getText().toString())/100);
        }
    }

    //inicializar variables para calcular
    private void inicializarValoresCalcular(){
        nota_bloque1=0;
        sumador_bloque1=0;
        nota_final_bloque1=0;
        nota_bloque2=0;
        sumador_bloque2=0;
        nota_final_bloque2=0;
        nota_final=0;
        suma_porcentaje_bloques=0;
        suma_porcentaje_bloque1=0;
        suma_porcentaje_bloque2=0;
    }

    private float redondear(float numero){
        BigDecimal bd = new BigDecimal(Float.toString(numero));
        if(redondear){
            bd = bd.setScale(cantidad_decimales, BigDecimal.ROUND_HALF_UP);
        }
        else if (truncar){
            bd = bd.setScale(cantidad_decimales, BigDecimal.ROUND_DOWN);
        }
        return bd.floatValue();
    }
}
