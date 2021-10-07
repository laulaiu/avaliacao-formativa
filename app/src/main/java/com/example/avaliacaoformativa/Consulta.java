package com.example.avaliacaoformativa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

public class Consulta extends AppCompatActivity {

    private FirebaseFirestore conexao ;
    private ListView listaV;
    private int indice = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        listaV = findViewById(R.id.lista);

/*        DocumentReference docRef = conexao .collection("1").document(String.valueOf(indice));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/


        listarL();

    }


    public void listarL(){


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Dados> lista = new ArrayList<Dados>();

                            for(QueryDocumentSnapshot doc : task.getResult()){
                                // if( ! doc.get("status").equals("0")){
                                Dados  p = new Dados(
                                        doc.get("chave").toString(),
                                        doc.get("autenticacao").toString(),
                                        doc.get("status").toString()
                                );
                                lista.add(p);
                                //  }
                            }
                            ArrayAdapter<Dados> adapter = new ArrayAdapter<>(
                                    Consulta.this,
                                    android.R.layout.simple_list_item_1, lista);
                            listaV.setAdapter(adapter);

                        }else{
                            Toast.makeText(Consulta.this, "Erro ao resgatar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}