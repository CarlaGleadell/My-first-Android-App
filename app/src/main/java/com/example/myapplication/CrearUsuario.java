package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrearUsuario extends AppCompatActivity {
    private EditText name;
    private EditText password;
    private TextView mensaje;
    private MyBD myBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_usuario);
        name = findViewById(R.id.name2);
        password = findViewById(R.id.password2);
        mensaje = findViewById(R.id.mensaje);
        myBD = new MyBD(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void crearUsuario(View view) {
        String nombre = name.getText().toString().trim();
        String clave = password.getText().toString().trim();
        if (!nombre.isEmpty() || !clave.isEmpty()) {
            myBD.insertarUsuario(nombre, clave);
            mensaje.setText("¡Usuario creado exitosamente!");
        } else {
            mensaje.setText("Por favor ingrese nombre y clave.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBD.close();
    }
}