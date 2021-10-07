package com.example.avaliacaoformativa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

/*        CollectionReference banco = conexao.collection("1");
        banco.document("1").update("status","1");*/

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

    public void banco(){



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Dados> lista = new ArrayList<Dados>();



                            for(QueryDocumentSnapshot doc : task.getResult()){

                                if(  doc.get("chave").equals(chave.getText().toString())){

                                    Dados  p = new Dados(
                                            doc.get("chave").toString(),
                                            doc.get("autenticacao").toString(),
                                            doc.get("status").toString()
                                    );

                                    if(     doc.get("status").toString().equals("1")){
                                        update(String.valueOf(doc),"0");
                                    }else{
                                        update(String.valueOf(doc),"1");
                                    }

                                    lista.add(p);


                                }
                            }

/*                                ArrayAdapter<Dados> adapter = new ArrayAdapter<>(
                                    Consulta.this,
                                    android.R.layout.simple_list_item_1, lista);
                            listaV.setAdapter(adapter);*/

                        }else{
                            Toast.makeText(MainActivity.this, "Erro ao resgatar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void update(String documento, String valor){

        CollectionReference banco = conexao.collection("1");
        banco.document(documento).update("status",valor);
    }




}