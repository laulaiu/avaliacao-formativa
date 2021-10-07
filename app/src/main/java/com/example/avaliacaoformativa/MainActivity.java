package com.example.avaliacaoformativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button consulta;
    private EditText chave;


    private FirebaseFirestore conexao = FirebaseFirestore.getInstance(); ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consulta = findViewById(R.id.consulta);
        chave   = findViewById(R.id.chaveEdt);

        CollectionReference banco = conexao.collection("1");

        banco.document("1").update("status","1");

       // Task<Void> banco = conexao.collection("1").document("1").update("status","1");
/*
        Map<String, Object> data1 = new HashMap<>();
        data1.put("status", "0");
        banco.document("1").set(data1);*/


        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent consulta = new Intent(MainActivity.this, Consulta.class);
                startActivity(consulta);

            }
        });

    }




}