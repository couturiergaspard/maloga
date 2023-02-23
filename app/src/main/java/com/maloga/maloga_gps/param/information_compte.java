package com.maloga.maloga_gps.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maloga.maloga_gps.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class information_compte extends AppCompatActivity {

    Button button_return;
    Button changemail;
    Button changeMDP;

    TextView mailInput;
    TextView motdepasse;
    TextView textMail;

    Spinner voiture;

    EditText mail_input;

    Integer name_pos;
    Integer nombre_voiture;
    Integer nombre_trajets;
    Integer i;

    String email;
    String nom_voiture;

    DatabaseReference DataRef_nombre_fenetre;
    FirebaseDatabase mDatabaseref;
    FirebaseAuth mAuth;
    FirebaseUser user;

    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_compte);
        button_return = findViewById(R.id.button_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Parametre.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
        mDatabaseref = FirebaseDatabase.getInstance();
        DataRef_nombre_fenetre = mDatabaseref.getReference();
        mAuth = FirebaseAuth.getInstance();

        //On affiche l'email de l'utilisateur
        mailInput = findViewById(R.id.mail_input);
        mailInput.setText(mAuth.getCurrentUser().getEmail());
        user = FirebaseAuth.getInstance().getCurrentUser();
        mail_input = findViewById(R.id.mail_input);
        changemail = findViewById(R.id.changeMailButton);
        //Bouton pour changer d'adresse mail
        changemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updateEmail(mail_input.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("change_user", "User email address updated.");
                                    Toast.makeText(getApplicationContext(),"Email updated",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        //Bouton pour changer de mot de passe
        changeMDP = findViewById(R.id.changeMdpButton);
        changeMDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(mAuth.getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("PASSWORD-RESET", "Email sent.");
                            Toast.makeText(getApplicationContext(), "Email envoyé !", Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        });

        nuit();
        spinner();
        boutons();
    }
    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_information_compte);
            layout.setBackgroundResource(R.color.theme2);
            textMail = findViewById(R.id.textMail);
            textMail.setTextColor(R.color.colorText1);
            mailInput = findViewById(R.id.mail_input);
            mailInput.setTextColor(R.color.colorText1);
            motdepasse = findViewById(R.id.textMdp);
            motdepasse.setTextColor(R.color.colorText1);
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_information_compte);
            layout.setBackgroundResource(R.color.theme1);
        }
    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x2 - x1 > 200) {
                    //Left
                    Intent i = new Intent(getApplicationContext(), Parametre.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (x1 - x2 > 200) {
                    //Right
                }
                break;
        }
        return false;
    }

    public void spinner(){

        voiture = findViewById(R.id.spinner_voiture);
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        ArrayList<String> area = new ArrayList<>();
        Integer nombre_voiture = Integer.parseInt(sharedPref.getString("nombre_voiture","null"));
        for(int i = 1; i <= nombre_voiture; i++){
            //add values in area arrayList
            String voiture_numero = "voiture_numero_"+i;
            area.add(sharedPref.getString(voiture_numero,"null"));
        }


        Boolean nuit = sharedPref.getBoolean("nuit", false);
        if (nuit == true) {
            voiture.setPopupBackgroundResource(R.color.theme2);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_itemn,area);
            voiture.setAdapter(adapter);
            ViewCompat.setBackgroundTintList(voiture, ColorStateList.valueOf(getResources().getColor(R.color.colorText1)));

            TextView textvoiture = findViewById(R.id.textvoiture);
            textvoiture.setTextColor(getResources().getColor(R.color.colorText1));
        } else {
            voiture.setPopupBackgroundResource(R.color.theme1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_itemn,area);
            voiture.setAdapter(adapter);
        }

        voiture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                name_pos = position+1;
                Log.d("SPINNER","pos : " + position);
                Log.d("SPINNER","name_pos : " + name_pos.toString());

/**
 * Récupère le nom de la voiture
 *
 */
                SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                email = sharedPref.getString("email","NULL").replace(".","%");

                DataRef_nombre_fenetre = mDatabaseref.getReference("Users").child(email).child("Trajets").child("Id");
                DataRef_nombre_fenetre.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        nom_voiture = dataSnapshot.child("Voiture").child(name_pos.toString()).getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //On detecte un slide et on parametre les objets de type boutons
    public void boutons(){
        ScrollView linear = findViewById(R.id.scrollv);
        linear.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Parametre.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                        }
                        break;
                }
                return false;
            }
        });


        /**
         * Supprime la voiture
         */
        Button button = findViewById(R.id.button_del_voiture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On vérifie que la voiture sélectioné en est bien une
                if(name_pos != 1) {
                    DataRef_nombre_fenetre = mDatabaseref.getReference("Users").child(email).child("Trajets").child("Id");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (nombre_voiture == null){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (nombre_voiture != null){
                                            delete_voiture();
                                        }else if(nom_voiture == null){
                                            Toast.makeText(getApplicationContext(),"Mauvaise connexion...",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },3000);
                                DataRef_nombre_fenetre.addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String temp = dataSnapshot.child("Voiture").child("nbr").getValue().toString();
                                        nombre_voiture = Integer.parseInt(temp);
                                        Toast.makeText(getApplicationContext(),"Suppression...",Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }else {
                                delete_voiture();
                            }
                        }
                    }, 1000);
                    DataRef_nombre_fenetre.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String temp = dataSnapshot.child("Voiture").child("nbr").getValue().toString();
                            nombre_voiture = Integer.parseInt(temp);
                            Toast.makeText(getApplicationContext(),"Suppression...",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Impossible de supprimer la voiture",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void delete_voiture(){

        //Nombre de voiture - 1
        nombre_voiture--;
        DataRef_nombre_fenetre.child("Voiture").child("nbr").setValue(nombre_voiture);

        //On supprime la voiture du repertoire
        //en ligne
        DataRef_nombre_fenetre.child("Voiture").child(name_pos.toString()).removeValue();
        //en local
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nombre_voiture", nombre_voiture.toString());
        editor.commit();

        //décalage
        name_pos++;
        Log.d("VOITURE","name-pos : " + name_pos);
        nombre_voiture++;
        Log.d("VOITURE","nbr : " + nombre_voiture);
        for (int k = name_pos; k <= nombre_voiture; k++) {
            //en ligne
            Log.d("VOITURE","Numéro voiture à décaler : " + k);
            Integer temp3 = k - 1;
            //voiture avant
            String voiture_avant;
            voiture_avant = sharedPref.getString("voiture_numero_"+k,"null");
            Log.d("VOITURE","Nom : " + voiture_avant);

            //voiture apres
            Integer voiture_apres = k - 1;
            DataRef_nombre_fenetre.child("Voiture").child(voiture_apres.toString()).setValue(voiture_avant);

            //supprime l'ancienne
            Integer voiture_apres2 = k;
            DataRef_nombre_fenetre.child("Voiture").child(voiture_apres2.toString()).removeValue();
        }

        //On enlève la voiture de tous les trajets concernés
        nombre_trajets = Integer.parseInt(sharedPref.getString("nombre_fenetre_a_charger", "0"));
        Log.d("VOITURE","Analyse...");
        Log.d("VOITURE","Analyses à faire : "+nombre_trajets);
        for ( i = nombre_trajets; i > 0; i--) {
            final Integer temp4 = i;
            DataRef_nombre_fenetre = mDatabaseref.getReference("Users").child(email).child("Trajets").child("Id");
            DataRef_nombre_fenetre.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String temp = dataSnapshot.child(temp4.toString()).child("Voiture").getValue().toString();
                    Log.d("VOITURE","Analyse "+temp4+" : "+ temp + " dois éviter : " + nom_voiture);
                    if(temp.equals(nom_voiture)){
                        //Implementation
                        //En ligne
                        DataRef_nombre_fenetre.child(temp4.toString()).child("Voiture").setValue("Non renseigné");
                        //Locale
                        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("fenetre"+temp4+"_voiture","Non renseigné");
                        editor.putString("fenetre"+temp4+"_voiture_num","1");
                        editor.commit();

                        Log.d("VOITURE","Analyse "+temp4+" : "+ temp + " --> " + "Non renseigné");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //On recharge le spinner
        spinner();
    }
}
