package com.cramcat.platform.CRUDapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        Context context = this;
        Funciones pop = new Funciones();
        TextView tvNick = findViewById(R.id.nick);
        TextView tvPass = findViewById(R.id.pass);
        TextView tvemail = findViewById(R.id.email);
        TextView tvRePass = findViewById(R.id.rePass);

        Button home = findViewById(R.id.home);
        Button registro = findViewById(R.id.registro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(tvemail.getText());
                String nick = String.valueOf(tvNick.getText());
                String pass = String.valueOf(tvPass.getText());
                String rePass = String.valueOf(tvRePass.getText());
                Integer isadmin = 0;
                Integer isblock = 0;
                Integer id= 0;

                if (nick.matches("^[a-zA-Z0-9]{3,16}$")) {
                    if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                        if (pass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,32}$")) {
                            if (pass.equals(rePass)) {
                                Api api = new Api();
                                try {
                                    api.registerUserAsync(id, nick, email, pass,isadmin ,isblock, new Api.ApiCallback() {
                                        @Override
                                        public void onApiResult(String result) throws JSONException {
                                            if (result != null) {
                                                // Registro exitoso
                                                Intent intent = new Intent(Register.this, Crud.class);
                                                startActivity(intent);
                                            } else {
                                                pop.showNewDialog(context, "Error", "Fallo en el registro.");
                                            }
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    pop.showNewDialog(context, "Error", "Fallo en el registro.");
                                }
                            } else {
                                pop.showNewDialog(context, "Error", "Las contraseñas no coinciden");
                            }
                        } else {
                            pop.showNewDialog(context, "Error", "La contraseña debe tener entre 8 y 32 caracteres, contener al menos 1 letra minúscula, 1 letra mayúscula y 1 número.");
                        }
                    } else {
                        pop.showNewDialog(context, "Error", "Este no es un mail valido.");
                    }
                } else {
                    pop.showNewDialog(context, "Error", "El nick debe contener entre 3 y 16 caracteres y no puede contener caracteres especiales.");
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(context, "Home", Toast.LENGTH_LONG).show();
            }
        });
    }
}
