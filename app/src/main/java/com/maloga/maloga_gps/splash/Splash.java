package com.maloga.maloga_gps.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.volant.Volant;
import com.maloga.maloga_gps.connexion.Login;

public class Splash extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference DataRef_nombre_fenetre;
    private FirebaseDatabase mDatabase;
    private String monemail;
    private String monpassword;
    private TextView text_ch;
    private String value; //Nombres de fenetres
    private Integer numero_fenetre; //Numero de la fenetre
    private String distance;
    private String temps;
    private String trajetA;
    private String trajetB;
    private String nbr_voiture;
    private String nom_voiture;
    private String meteo;
    private Integer ACCESS_LOCATION_CODE = 1;
    private Integer ACCESS_READ_EXTERNAL_STORAGE_CODE = 2;
    private Integer ACCESS_WRITE_EXTERNAL_STORAGE_CODE = 3;
    private Integer ACCESS_FOLDER_CODE_CODE = 4;
    private String notes = "";
    private boolean locbool = false;
    private boolean storbool = false;

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.activity_splash);

        //initialisation
        text_ch = findViewById(R.id.text_chargement);
        text_ch.setText("Obtention des données...");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        DataRef_nombre_fenetre = mDatabase.getReference();

        //splash
        nuit();
        get_localdata();
    }

    //splash

    public void get_localdata(){
        //Le handler sert à mettre un délai pour une éxecution de programme
        //Soit : - Un retard
        //Soit : - Un temps maximum pour une ligne de code
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                text_ch.setText("vérification des autorisations...");
                verifLocation();

            }
        }, 500);
        Log.d("DATAID","Splash get_local...");
        get_local();
    }

    public void firebase_connexion(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d("DATAID","Splash firebase_co...");
                text_ch.setText("connexion au profil utilisateur...");
                firebase_co();

            }
        }, 1500);
    }

    public void get_database(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //vers activité principale
                Log.d("DATAID","menu principal");
                Intent splash = new Intent(getApplicationContext(), Volant.class);
                startActivity(splash);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();

            }
        }, 3000);
        text_ch.setText("mise à jour des données...");
        Log.d("DATAID","Splash get_data...");
        get_data();
    }

    //fonctions
    public void firebase_co(){
        //test firebase
        mAuth.signInWithEmailAndPassword(monemail, monpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Splash suivant
                            get_database();
                        } else {
                            //Renvoi au login
                            Log.d("DATAID","Renvois au login...");
                            Intent splash = new Intent(getApplicationContext(), Login.class);
                            startActivity(splash);
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            finish();
                        }
                    }
                });
    }

    public void get_data(){
        //On récupère toutes les données sauvegardées en ligne qu'on écrase sur les données locale
        DataRef_nombre_fenetre = mDatabase.getReference("Users").child(monemail.replace(".","%")).child("Trajets").child("Id");
        DataRef_nombre_fenetre.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot.child("nbr").getValue().toString();
                nbr_voiture = dataSnapshot.child("Voiture").child("nbr").getValue().toString();
                nom_voiture = dataSnapshot.child("Voiture").child("1").getValue().toString();
                Log.d("DATAID", "Donée reçus value : " + value + " Nombre Voiture : " + nbr_voiture + " Nom voiture 1 : " + nom_voiture);

                //sauvegarde locale
                SharedPreferences sharedPref = getSharedPreferences("DATA",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("nombre_fenetre_a_charger",value);
                editor.putString("nombre_voiture",nbr_voiture);
                for(int w = 1;w <= Integer.parseInt(nbr_voiture);w++) {
                    Integer w_s = w;
                    nom_voiture = dataSnapshot.child("Voiture").child(w_s.toString()).getValue().toString();
                    editor.putString("voiture_numero_" + w, nom_voiture);
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("DATAID", "Failed to read value.", error.toException());
            }
        });

        //On cherche les ressources intérieures aux fenetres

        DataRef_nombre_fenetre = mDatabase.getReference("Users").child(monemail.replace(".","%")).child("Trajets").child("Id");
        DataRef_nombre_fenetre.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
<<<<<<< HEAD
                try {
                    for (int i = 1;i <= Integer.parseInt(value);i++) {
                        numero_fenetre = i;
                        distance = dataSnapshot.child(numero_fenetre.toString()).child("Distance").getValue().toString();
                        temps = dataSnapshot.child(numero_fenetre.toString()).child("Temps").getValue().toString();
                        trajetA = dataSnapshot.child(numero_fenetre.toString()).child("A").getValue().toString();
                        trajetB = dataSnapshot.child(numero_fenetre.toString()).child("B").getValue().toString();
                        nom_voiture = dataSnapshot.child(numero_fenetre.toString()).child("Voiture").getValue().toString();

                        //sauvegarde locale
                        SharedPreferences sharedPref = getSharedPreferences("DATA",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("fenetre"+i+"_distance",distance);
                        editor.putString("fenetre"+i+"_temps",temps);
                        editor.putString("fenetre"+i+"_A",trajetA);
                        editor.putString("fenetre"+i+"_B",trajetB);
                        editor.putString("fenetre"+i+"_voiture",nom_voiture);
                        editor.putString("vvvv","0");
                        editor.commit();

                        Log.d("DATAID", "Donée reçus pour i =  "+ i + " Distance : " + distance + " Temps : " + temps + "Trajets : " + trajetA + " ->" + trajetB
                                + "  Nbr Voiture : " + nbr_voiture + " Nom Voiture : " + nom_voiture);
                    }
                }catch(Exception e){
=======
                for (int i = 1;i <= Integer.parseInt(value);i++) {
                    numero_fenetre = i;
                    try {
                    distance = dataSnapshot.child(numero_fenetre.toString()).child("Distance").getValue().toString();
                    temps = dataSnapshot.child(numero_fenetre.toString()).child("Temps").getValue().toString();
                    trajetA = dataSnapshot.child(numero_fenetre.toString()).child("A").getValue().toString();
                    trajetB = dataSnapshot.child(numero_fenetre.toString()).child("B").getValue().toString();
                    nom_voiture = dataSnapshot.child(numero_fenetre.toString()).child("Voiture").getValue().toString();
                    notes = dataSnapshot.child(numero_fenetre.toString()).child("Notes").getValue().toString();
                    meteo = dataSnapshot.child(numero_fenetre.toString()).child("Meteo").getValue().toString();
                    }catch (Exception e){
                    }

                    //sauvegarde locale
                    SharedPreferences sharedPref = getSharedPreferences("DATA",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("fenetre"+i+"_distance",distance);
                    editor.putString("fenetre"+i+"_temps",temps);
                    editor.putString("fenetre"+i+"_A",trajetA);
                    editor.putString("fenetre"+i+"_B",trajetB);
                    editor.putString("fenetre"+i+"_voiture",nom_voiture);
                    editor.putString("fenetre"+i+"_notes",notes);
                    editor.putString("fenetre"+i+"_meteo",meteo);
                    editor.putString("vvvv","0");
                    editor.commit();
>>>>>>>

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("DATAID", "Failed to read value.", error.toException());
            }
        });

    }

    public void get_local(){
        //get local
        SharedPreferences pref = getSharedPreferences("DATA",Context.MODE_PRIVATE);
        monemail = pref.getString("email","NULL");
        monpassword = pref.getString("password","NULL");
        Log.d("DATAID","adresse mail :"+ monemail);
        Log.d("DATAID","mot de passe :"+monpassword);
    }


    /**
     * Cette fonction verifie que l'accès à la localisation est donné
     */
    public void verifLocation(){
        requestLocPermission();
        requestFolderPermission();
        firebase_connexion();
    }

    /**
     * Demande les permission d'accès au stockage de l'appareil.
     */
    private void requestFolderPermission(){
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, ACCESS_READ_EXTERNAL_STORAGE_CODE);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACCESS_WRITE_EXTERNAL_STORAGE_CODE);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_PHONE_STATE}, ACCESS_FOLDER_CODE_CODE);
    }

    /**
     * Cette fonction vérifie les permissions concernant la localisation
     */
    private void requestLocPermission(){
        // On affiche poruquoi la permission est requise
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_CODE);
    }

    /**
     * On verifie que la permission à bien était obtenue lorqu'une permission est validé
     * @param requestCode --> Code de la requete
     * @param permissions --> Permissions
     * @param grantResults --> Résultats d'autorisations
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_LOCATION_CODE){
            Log.d("PERMISSION", "Accès à la localisation autorisé !");
            SharedPreferences pref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("autor","True");
            editor.commit();
        }else{
            SharedPreferences pref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("autor","False");
            editor.commit();
        }
        if (requestCode == ACCESS_READ_EXTERNAL_STORAGE_CODE){
            Log.d("PERMISSION", "Accès au stockage autorisé !");
        }
        if (requestCode == ACCESS_WRITE_EXTERNAL_STORAGE_CODE){
            Log.d("PERMISSION", "Accès au stockage autorisé !");
        }
        if (requestCode == ACCESS_FOLDER_CODE_CODE){
            Log.d("PERMISSION", "Accès au stockage autorisé !");
            storbool = true;
        }
    }

    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_splash);
            layout.setBackgroundResource(R.color.theme2);
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_splash);
            layout.setBackgroundResource(R.color.theme1);
        }
    }
}
