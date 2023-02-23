package com.maloga.maloga_gps.connexion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.splash.Splash;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    //Verif non vide
    private Boolean nomb = false;
    private Boolean prenomb = false;
    private Boolean postalb = false;
    private Boolean emailb = false;
    private Boolean motdepasseb = false;
    private Boolean motdepasse2b = false;
    private Boolean voitureb = false;

    private String login_nom;
    private String login_prenom;
    private String login_postal;
    private String login_email;
    private String login_motdepasse;
    private String login_motdepasse2;
    private String voiture_s;

    //Database
    private DatabaseReference mDatabase;


    //Boutons
    private Button bouton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        variable();
        nuit();


        /**
         * Message d'erreur
         */
        setup_nom_error();
        setup_prenom_error();
        setup_postal_error();
        setup_email_error();
        setup_motdepasse_error();
        setup_motdepasseverif_error();
        setup_car_error();


        bouton = findViewById(R.id.login_id);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


    }

//On récupère toutes les informations pour l'inscription
    public void signup(){
        if (nomb && prenomb && postalb && emailb && motdepasseb && motdepasse2b) {


            final EditText login_nom_edittext;
            login_nom_edittext = findViewById(R.id.login_nom);
            login_nom = login_nom_edittext.getText().toString();

            final EditText login_prenom_edittext;
            login_prenom_edittext = findViewById(R.id.login_prenom);
            login_prenom = login_prenom_edittext.getText().toString();

            final EditText login_postal_edittext;
            login_postal_edittext = findViewById(R.id.login_postal);
            login_postal = login_postal_edittext.getText().toString();

            final EditText login_email_edittext;
            login_email_edittext = findViewById(R.id.login_email);
            login_email = login_email_edittext.getText().toString();

            final EditText login_password_edittext;
            login_password_edittext = findViewById(R.id.login_motdepasse);
            login_motdepasse = login_password_edittext.getText().toString();

            final EditText login_password2_edittext;
            login_password2_edittext = findViewById(R.id.login_motdepasse2);
            login_motdepasse2 = login_password2_edittext.getText().toString();

            // Initialize Firebase Auth
            //On créer l'utilisateur avec les identifiants donnés
            mAuth.createUserWithEmailAndPassword(login_email, login_motdepasse)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                //SAUVEGARDE DES DONNEES
                                SharedPreferences sharedPref = getSharedPreferences("DATA",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("email",login_email);
                                editor.putString("password",login_motdepasse);
                                editor.commit();


                                //Stockage en ligne


                                final EditText login_nom_edittext;
                                login_nom_edittext = findViewById(R.id.login_nom);
                                login_nom = login_nom_edittext.getText().toString();
                                login_nom = login_nom.replace(".","%");

                                final EditText login_prenom_edittext;
                                login_prenom_edittext = findViewById(R.id.login_prenom);
                                login_prenom = login_prenom_edittext.getText().toString();
                                login_prenom = login_prenom.replace(".","%");

                                final EditText login_postal_edittext;
                                login_postal_edittext = findViewById(R.id.login_postal);
                                login_postal = login_postal_edittext.getText().toString();
                                login_postal = login_postal.replace(".","%");

                                final EditText login_email_edittext;
                                login_email_edittext = findViewById(R.id.login_email);
                                login_email = login_email_edittext.getText().toString();
                                login_email = login_email.replace(".","%");

                                final EditText voiture;
                                voiture = findViewById(R.id.voiture);
                                voiture_s = voiture.getText().toString();




                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("Users").child(login_email).child("Nom").setValue(login_nom);
                                mDatabase.child("Users").child(login_email).child("Prenom").setValue(login_prenom);
                                mDatabase.child("Users").child(login_email).child("Code postal").setValue(login_postal);
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("nbr").setValue("0");
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("1").child("Distance").setValue("0");
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("1").child("Temps").setValue("0");
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("1").child("A").setValue("NULL");
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("1").child("B").setValue("NULL");
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("1").child("Voiture").setValue("NULL");
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("Voiture").child("1").setValue("Aucune voiture sélectionée");
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("Voiture").child("2").setValue(voiture_s);
                                mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("Voiture").child("nbr").setValue("2");


                                Toast.makeText(getApplicationContext(),"Inscription réussie",Toast.LENGTH_SHORT).show();
                                Intent intent_splash = new Intent(getApplicationContext(), Splash.class);
                                startActivity(intent_splash);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Inscription échoué...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }


    }



//On affiche toutes les erreurs potentielles
    private void setup_nom_error() {
        final TextInputLayout nom = findViewById(R.id.nom_input);
        nom.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() <= 1) {
                    nom.setError("Saisir un nom");

                    nom.setErrorEnabled(true);
                    nomb = false;
                } else {
                    nom.setErrorEnabled(false);
                    nomb = true;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setup_prenom_error() {
        final TextInputLayout prenom = findViewById(R.id.prenom_input);
        prenom.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() <= 1) {
                    prenom.setError("Saisir un prénom");
                    prenom.setErrorEnabled(true);
                    prenomb = false;
                } else {
                    prenom.setErrorEnabled(false);
                    prenomb = true;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setup_postal_error() {
        final TextInputLayout postal = findViewById(R.id.postal_input);
        postal.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() != 5) {
                    postal.setError("Saisir un code postal");
                    postal.setErrorEnabled(true);
                    postalb = false;
                } else {
                    postal.setErrorEnabled(false);
                    postalb = true;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setup_email_error() {
        final TextInputLayout email = findViewById(R.id.email_input);
        email.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                final EditText login_email_edittext;
                login_email_edittext = findViewById(R.id.login_email);
                login_email = login_email_edittext.getText().toString();
                if (!(login_email.contains("@"))) {
                    email.setError("Saisir un email");
                    email.setErrorEnabled(true);
                    emailb = false;
                } else {
                    email.setErrorEnabled(false);
                    emailb = true;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setup_motdepasse_error() {
        final TextInputLayout motdepasse = findViewById(R.id.motdepasse_input);
        motdepasse.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() < 5) {
                    motdepasse.setError("Saisir un mot de passe");
                    motdepasse.setErrorEnabled(true);
                    motdepasseb = false;
                } else {
                    motdepasse.setErrorEnabled(false);
                    motdepasseb = true;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setup_motdepasseverif_error() {


        final TextInputLayout motdepasse2 = findViewById(R.id.motdepasse2_input);
        motdepasse2.getEditText().addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() >= 0) {
                    final EditText login_password_edittext;
                    login_password_edittext = findViewById(R.id.login_motdepasse);
                    login_motdepasse = login_password_edittext.getText().toString();

                    final EditText login_password2_edittext;
                    login_password2_edittext = findViewById(R.id.login_motdepasse2);
                    login_motdepasse2 = login_password2_edittext.getText().toString();


                    if (!(login_motdepasse.equals(login_motdepasse2))) {
                        motdepasse2.setError("Ressaisissez le mot de passe");
                        motdepasse2.setErrorEnabled(true);
                        motdepasse2b = false;
                    } else {
                        motdepasse2.setErrorEnabled(false);
                        motdepasse2b = true;
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void setup_car_error() {


        final TextInputLayout voiture = findViewById(R.id.voiture_input);
        voiture.getEditText().addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() >= 1) {
                        voiture.setErrorEnabled(false);
                        voitureb = true;
                    } else {
                        voiture.setError("Saisissez une voiture");
                        voiture.setErrorEnabled(true);
                        voitureb = false;
                    }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void variable(){
        //Récupérer les identifiants :
        EditText login_nom_edittext;
        login_nom_edittext = findViewById(R.id.login_nom);
        login_nom = login_nom_edittext.getText().toString();

        EditText login_prenom_edittext;
        login_prenom_edittext = findViewById(R.id.login_prenom);
        login_prenom = login_prenom_edittext.getText().toString();

        EditText login_postal_edittext;
        login_postal_edittext = findViewById(R.id.login_postal);
        login_postal = login_postal_edittext.getText().toString();

        EditText login_email_edittext;
        login_email_edittext = findViewById(R.id.login_email);
        login_email = login_email_edittext.getText().toString();

        EditText login_password_edittext;
        login_password_edittext = findViewById(R.id.login_motdepasse);
        login_motdepasse = login_password_edittext.getText().toString();

        EditText login_password2_edittext;
        login_password2_edittext = findViewById(R.id.login_motdepasse2);
        login_motdepasse2 = login_password2_edittext.getText().toString();

        EditText car;
        car = findViewById(R.id.voiture);
        voiture_s = car.getText().toString();
    }
    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_register);
            layout.setBackgroundResource(R.color.theme2);
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_register);
            layout.setBackgroundResource(R.color.theme1);
        }
    }

}
