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
import com.google.firebase.auth.FirebaseUser;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.splash.Splash;



public class Login extends AppCompatActivity {

    private Button login_id;
    private Button login_signup;

    private Boolean emailb = false;
    private Boolean motdepasseb = false;

    //firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        variable();
        nuit();
        setup_email_error();
        setup_motdepasse_error();

//Connexion
        login_id = findViewById(R.id.login_id);
        login_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
//Inscription
        login_signup = findViewById(R.id.login_signup);
        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_dir();
            }
        });


    }

//On redirige vers une autre activigé pour l'inscription
    public void signup_dir(){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }
//On test de se connecter avec les identifiants fournis
    public void login(){

        //Récupérer les identifiants :
        EditText login_email_edittext;
        login_email_edittext = findViewById(R.id.login_email);
        final String login_email = login_email_edittext.getText().toString();
        EditText login_password_edittext;
        login_password_edittext = findViewById(R.id.login_password);
        final String login_password = login_password_edittext.getText().toString();
//On tests qu'aucune case n'est vide
        if(emailb && motdepasseb) {
            //Log.d("DATAID", "NON NULL");
            //On test de se connecter a la database
            mAuth.signInWithEmailAndPassword(login_email, login_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Si la connection est réussie on redirige l'utilisateur vers le splash
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent_volant = new Intent(getApplicationContext(), Splash.class);
                                startActivity(intent_volant);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Impossible de se connecter", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            //On sauvegarde les identifiants de connexion en local pour éviter de se reconnecter manuallement à chaque lancement de l'application
            //SAUVEGARDE DES DONNEES
            SharedPreferences sharedPref = getSharedPreferences("DATA",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("email",login_email);
            editor.putString("password",login_password);
            editor.commit();
        }
    }

    public void variable(){
//On récupère les identifiants écris
        //Récupérer les identifiants :
        EditText login_email_edittext;
        login_email_edittext = findViewById(R.id.login_email);
        final String login_email = login_email_edittext.getText().toString();
        EditText login_password_edittext;
        login_password_edittext = findViewById(R.id.login_password);
        final String login_password = login_password_edittext.getText().toString();
    }

    //On affiche un message d'alerte ou d'erreur dans les cases vide
    private void setup_email_error() {
        final TextInputLayout email = findViewById(R.id.email_input);
        email.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                EditText login_email_edittext;
                login_email_edittext = findViewById(R.id.login_email);
                final String login_email = login_email_edittext.getText().toString();

                if (!(login_email.contains("@"))) {
                    email.setError("Saisir l'email");

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
        final TextInputLayout motdepasse = findViewById(R.id.password_input);
        motdepasse.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() < 5) {
                    motdepasse.setError("Saisir le mot de passe");

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
    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_login);
            layout.setBackgroundResource(R.color.theme2);
            Button text = findViewById(R.id.login_signup);
            text.setTextColor(getResources().getColor(R.color.colorText1));
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_login);
            layout.setBackgroundResource(R.color.theme1);
        }
    }
}
