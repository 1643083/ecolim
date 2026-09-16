package com.example.ecolim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText txtUsuario, txtContrasena;
    Button btnIngresar;

    private final String USUARIO_VALIDO = "admin";
    private final String CONTRASENA_VALIDA = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(v -> {
            String usuario = txtUsuario.getText().toString().trim();
            String contrasena = txtContrasena.getText().toString().trim();

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            if (usuario.equals(USUARIO_VALIDO) && contrasena.equals(CONTRASENA_VALIDA)) {
                startActivity(new Intent(Login.this, Home.class));
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}