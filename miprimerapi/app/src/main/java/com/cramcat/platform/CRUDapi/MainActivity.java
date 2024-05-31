package com.cramcat.platform.CRUDapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText tvemail;
    EditText tvPass;
    private boolean isPasswordVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Context context = this;

        Funciones pop = new Funciones();

        imageView = findViewById(R.id.imageView);
        TextView olvidadoTextView = findViewById(R.id.olvidado);
        TextView tvemail = findViewById(R.id.email);
        TextView tvPass = findViewById(R.id.pass);

        Button registrarse = findViewById(R.id.registrarse);
        Button iniciar = findViewById(R.id.iniciar);

        EditText editTextPassword = findViewById(R.id.pass);

        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // El usuario ha hecho clic en el icono de la contraseña
                        togglePasswordVisibility(editTextPassword);
                        return true;
                    }
                }
                return false;
            }
        });

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tvemail.getText().toString();
                String pass = tvPass.getText().toString();

                Api api = new Api();

                try {
                    api.loginAsync(email, pass, new Api.ApiCallback() {
                        @Override
                        public void onApiResult(String result) throws JSONException {
                            handleApiResponse(result);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        olvidadoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Password.class);
                startActivity(intent);
            }
        });
    }

    private void handleApiResponse(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String userId = jsonObject.getString("id");

                // La autenticación fue exitosa, puedes realizar acciones adicionales aquí
                Intent intent = new Intent(MainActivity.this, Crud.class);
                intent.putExtra("id", userId);
                startActivity(intent);
            } catch (JSONException e) {
                showErrorDialog("Error al analizar la respuesta del servidor");
            }
        } else {
            showErrorDialog("Email o contraseña incorrectos");
        }
    }

    private void showErrorDialog(String errorMessage) {
        // Muestra un cuadro de diálogo de error con el mensaje proporcionado
        Funciones pop = new Funciones();
        pop.showNewDialog(MainActivity.this, "Error", errorMessage);
    }
    private void togglePasswordVisibility(EditText editText) {
        if (isPasswordVisible) {
            // Ocultar la contraseña
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            // Mostrar la contraseña
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        isPasswordVisible = !isPasswordVisible;
    }
}
