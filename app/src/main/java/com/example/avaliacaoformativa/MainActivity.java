package com.example.avaliacaoformativa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    private Button autenticar;
    private Button btn_notificacao;
    private EditText chave;
    private NotificationManager servico;
    private final int CHANNEL_ID = 123;


    private FirebaseFirestore conexao = FirebaseFirestore.getInstance();
    private final String ID_CANAL = "canalNotificacaoApp";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consulta = findViewById(R.id.consulta);
        autenticar = findViewById(R.id.btn_autenticar);
        chave   = findViewById(R.id.chaveEdt);
        criarCanalNotificacao();

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consulta = new Intent(MainActivity.this, Consulta.class);
                startActivity(consulta);
            }
        });

    }



    public void notificacao(String content, String title){
        Intent bsc = new Intent(this, Consulta.class);
        bsc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent bsc_PI = PendingIntent.getActivity(this, 0, bsc, 0);

        NotificationCompat.Builder contrutor = new NotificationCompat.Builder(this, ID_CANAL)
                .setSmallIcon(R.drawable.icone_notificacao)
                .setContentTitle(content)
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(bsc_PI);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(CHANNEL_ID , contrutor.build());

    }

    private void criarCanalNotificacao() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID_CANAL, getString(R.string.canal_nome), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(getString(R.string.channel_description));
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void banco1(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("banco")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){

                                if(  doc.getId().equals(chave.getText().toString())){

                                    if( doc.get("status").toString().equals("1")){
                                        update(doc.getId(),"0");

                                    }else{
                                        update(doc.getId(),"1");
                                    }
                                    notificacao("FIREBASE" ,"Auth: "+doc.get("autenticacao").toString());
                                    Log.d("leo_app","Ola, mundo");

                                }else{

                                    notificacao("FIREBASE" ,"Chave não encontrada");
                                }
                            }
                        }
                    }
                });
    }

    //atualiza o valor do banco
    public void update(String documento, String valor){
        //colocar a collectionPath que sera atualizada.
        CollectionReference banco = conexao.collection("banco");
        banco.document(documento).update("status",valor);
    }

}