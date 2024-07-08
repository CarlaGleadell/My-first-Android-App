package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Ingresar extends AppCompatActivity {
    private EditText name;
    private EditText password;
    private TextView mensaje1;
    private Button crearUsuario;
    private TextView historialIngresos;
    private MyBD myBD;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingresar);

        myBD = new MyBD(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        mensaje1 = findViewById(R.id.mensaje1);
        crearUsuario = findViewById(R.id.crearUsuario);
        historialIngresos = findViewById(R.id.historialIngresos);
        historialIngresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistorial = new Intent(Ingresar.this, HistorialIngresos.class);
                startActivity(intentHistorial);
            }
        });
        crearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCrearUsuario = new Intent(Ingresar.this, CrearUsuario.class);
                startActivity(intentCrearUsuario);
            }
        });

    }

    public void ingresar(View view) {
        String nombre = name.getText().toString().trim();
        String clave = password.getText().toString().trim();
        if (myBD.verificarUsuario(nombre, clave)) {
            registrarIngreso(nombre);
            mensaje1.setText("¡Ingresó correctamente!");
        } else {
            mensaje1.setText("Usuario o clave incorrectos");
        }
    }

    private void registrarIngreso(String nombreUsuario) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String fechaHora = sdf.format(new Date());
        myBD.ingresarRegistro(nombreUsuario, fechaHora);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBD.close();
    }
}
