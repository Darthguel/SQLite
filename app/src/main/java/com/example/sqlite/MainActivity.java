package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public EditText nom1;
    public EditText ape1;
    public EditText eda1;
    public EditText sex1;
    public Button btnSave, btnMod, btnBorra;

    SQLiteDB obj;
    public ListView mostrarDatos;
    public ArrayList<String> listaNombres;
    public ArrayList<Usuario> listaCompleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obj = new SQLiteDB(getApplicationContext());

        nom1 = (EditText) findViewById(R.id.etNombre);
        ape1 = (EditText) findViewById(R.id.etApellido);
        eda1 = (EditText) findViewById(R.id.etEdad);
        sex1 = (EditText) findViewById(R.id.etSexo);
        mostrarDatos = (ListView) findViewById(R.id.lvDatosBD);
        Spinner spin1 = (Spinner) findViewById(R.id.spinMenu);
        Button btnSave = (Button) findViewById(R.id.btnGrabar);
        Button btnMod = (Button) findViewById(R.id.btnModificar);
        Button btnBorra = (Button) findViewById(R.id.btnBorrar);
        ListView lvUsuarios = (ListView) findViewById(R.id.lvDatosBD);

        btnSave.setVisibility(View.GONE);
        btnMod.setVisibility(View.GONE);
        btnBorra.setVisibility(View.GONE);

        ArrayList<String> list1 = new ArrayList<String>();

        list1.add("MENU");
        list1.add("Agregar Usuario");
        list1.add("Modifica Usuario");
        list1.add("Eliminar Usuario");
        list1.add("Cargar Info Usuarios");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list1);
        spin1.setAdapter(adapter);


        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 1){
                    limpia();
                    btnSave.setVisibility(View.VISIBLE);
                    btnMod.setVisibility(View.GONE);
                    btnBorra.setVisibility(View.GONE);
                    nom1.requestFocus();
                }
                if (position == 2){
                    btnMod.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.GONE);
                    btnBorra.setVisibility(View.GONE);
                }
                if (position == 3){
                    btnBorra.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.GONE);
                    btnMod.setVisibility(View.GONE);
                }
                if (position == 4){
                    desplegar();
                }
                spin1.setAdapter(null);
                spin1.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

            lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    nom1.setText(listaCompleta.get(position).getNombre());
                    ape1.setText(listaCompleta.get(position).getApellido());
                    eda1.setText(String.valueOf(listaCompleta.get(position).getEdad()).toString());
                    sex1.setText(listaCompleta.get(position).getSexo());
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatos(this);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnSave.getWindowToken(), 0);
                if(btnSave.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
                    btnSave.setVisibility(View.GONE);
                }else{ // si no es Visible, lo pones
                    btnSave.setVisibility(View.VISIBLE);
                }
            }
        });

        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModificarDatos(this);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnMod.getWindowToken(), 0);
                if(btnMod.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
                    btnMod.setVisibility(View.GONE);
                }else{ // si no es Visible, lo pones
                    btnMod.setVisibility(View.VISIBLE);
                }
            }
        });

        btnBorra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarUsuario(this);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnBorra.getWindowToken(), 0);
                if(btnBorra.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
                    btnBorra.setVisibility(View.GONE);
                }else{ // si no es Visible, lo pones
                    btnBorra.setVisibility(View.VISIBLE);
                }
            }
        });


    }// Cierre onCreate

        public void cargarDatos (View.OnClickListener view){
            if (nom1.length()<1) {
                Mensaje("Campo nombre NO puede estar vacio!!");
                } else if (ape1.length()<1) {
                    Mensaje("Campo apellido NO puede estar vacio!!");
                        } else if (Integer.parseInt(String.valueOf(eda1.getText())) < 1) {
                           Mensaje("Campo edad NO puede ser menor a cero!!");
                                } else if (sex1.length() < 1) {
                                    Mensaje("Campo sexo NO puede estar vacio!!");
                                         } else {
                                            obj.insertarDatos(nom1.getText().toString(), ape1.getText().toString(), Integer.parseInt(eda1.getText().toString()), sex1.getText().toString());
                                            limpia();
                                            desplegar();
            }
        } // Cierre CargaDatos

    private void Mensaje(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }

    public void desplegar(){
            listaNombres = obj.llenaLista();
            ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaNombres);
            mostrarDatos.setAdapter(adp);
            listaCompleta = obj.cargaArray();
       } // Cierre Desplegar

    public void ModificarDatos(View.OnClickListener view){
        Usuario usuario = new Usuario();
        usuario.setNombre(nom1.getText().toString());
        usuario.setApellido(ape1.getText().toString());
        usuario.setEdad(Integer.parseInt(eda1.getText().toString()));
        usuario.setSexo(sex1.getText().toString());
        obj.modificarDatos(usuario);
        limpia();
        desplegar();
    }

    public void EliminarUsuario(View.OnClickListener view){
        Usuario usuario = new Usuario();
        usuario.setNombre(nom1.getText().toString());
        usuario.setApellido(ape1.getText().toString());
        usuario.setEdad(Integer.parseInt(eda1.getText().toString()));
        usuario.setSexo(sex1.getText().toString());
        obj.eliminarUsuario(usuario);
        limpia();
        desplegar();
    }

    public void limpia(){
        nom1.setText(null);
        ape1.setText(null);
        eda1.setText(null);
        sex1.setText(null);
    }
}//Cierre Main