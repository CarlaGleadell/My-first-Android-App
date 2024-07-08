package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class HistorialIngresos extends AppCompatActivity {

    private ListView listViewRegistros;
    private MyBD myBD;
    private SimpleCursorAdapter adapter;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial_ingresos);

        myBD = new MyBD(this);
        listViewRegistros = findViewById(R.id.listViewRegistros);
        Cursor cursor = myBD.mostrarRegistros();

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor,
                new String[]{"Nombre", "Fecha_de_ingreso"},
                new int[]{android.R.id.text1, android.R.id.text2},0);

        listViewRegistros.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null && adapter.getCursor() != null) {
            adapter.getCursor().close();
        }
    }
}