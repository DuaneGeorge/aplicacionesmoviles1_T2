package com.example.aplicationt2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.aplicationt2.entity.User;
import com.example.aplicationt2.service.UserService;
import com.example.aplicationt2.util.connection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner spnUsuarios;
    ArrayAdapter<String> adapter;
    ArrayList<String> usuarios = new ArrayList<String>();

    Button tbnSearch;
    TextView txtInfo;

    UserService userService;

    List<User>  lstUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Se conectara el arraylist de datos al adaptador
        spnUsuarios = findViewById(R.id.spnUsuarios);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, usuarios);
        spnUsuarios.setAdapter(adapter);


        tbnSearch = findViewById(R.id.tbnSearch);
        txtInfo = findViewById(R.id.txtInfo);



        userService = connection.getConnecion().create(UserService.class);

        cargaData();
        tbnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = spnUsuarios.getSelectedItemPosition();
                //mensajeAlert("ID seleccionado => " + id);

                User objUsuario = lstUsuarios.get(id);
                String salida = "Información de Mensaje \n\n";

                salida += "posId : " + objUsuario.getPostId() +"\n";
                salida += "id : " + objUsuario.getId() +"\n";
                salida += "name : " + objUsuario.getName() +"\n";
                salida += "email : " + objUsuario.getEmail() +"\n";
                salida += "body : " + objUsuario.getBody() +"\n";
                txtInfo.setText(salida);

            }
        });

    }

    public void cargaData(){
        Call<List<User>> call = userService.listaUsuarios();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response <List<User>>response) {
                if (response.isSuccessful()){
                    mensajeToast("Acceso exitoso al servicio REST");
                    lstUsuarios = response.body();
                    for (User user:lstUsuarios){
                    usuarios.add(user.getName());////////////////
                    }
                    adapter.notifyDataSetChanged();


                }else {
                    mensajeToast("Error de acceso al servicio REST");
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mensajeToast("Error de acceso al servicio REST");
            }


        });

    }


    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    void mensajeToast(String mensaje){
        Toast toast1 = Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();}


}