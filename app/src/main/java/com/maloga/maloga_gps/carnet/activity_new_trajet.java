package com.maloga.maloga_gps.carnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.carnet.Carnet;
import com.maloga.maloga_gps.param.Parametre;

import java.util.ArrayList;

public class activity_new_trajet extends AppCompatActivity {

    /***
     * On déclare toutes les variables
     */

    Button boutton_return;
    Button boutton_valide;
    Button neige,orage,pluie,nuage,soleil;

    EditText track_A;
    EditText track_B;
    EditText track_distance;
    EditText track_temps;

    String A;
    String B;
    String distance;
    String temps;
    String nom_voiture;
    String valeurmeteo;
    String notes_traj = "";

    Integer nombre_vue;
    Integer name_pos;

    Boolean bool_A = false;
    Boolean bool_B = false;
    Boolean bool_distance = false;
    Boolean bool_temps = false;
    Boolean bool_neige = false;
    Boolean bool_orage = false;
    Boolean bool_pluie = false;
    Boolean bool_nuage = false;
    Boolean bool_soleil = false;

    DatabaseReference mDatabase;
    DatabaseReference DataRef_nombre_fenetre;
    FirebaseDatabase mDatabaseref;

    ScrollView scrollView;

    float x1,x2,y1,y2;

    String login_email;

    /***
     * Boucle principale du code; le "@Override" signifie qu'il s'execute au démarrage.
     * Le "setContentView" va rattacher le code au rendu xml fait précedemment, c'est une sorte de focus.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trajet);

        /***
         * findViewById() = Nous avons définis l'objet "bouton_return" comme un bouton il faut donc maintenant le rattacher à un objet du visuel (du xml).
         * Apres avoir définis l'objet nous lui ajoutons un "OnTouchListener" qui va détecter des que l'utilisateur va poser son doigts dessus
         * Ce "OnTouchListener" va permettre de récupérer la position X et Y, avant et apres avoir pressé le bouton, grâce à ses données
         * nous pouvons créer une sorte de fonction slide.
         */
        boutton_return = findViewById(R.id.button_return);
        boutton_return.setOnTouchListener(new View.OnTouchListener() {
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
                        /***
                         * L'application détecte quand le doigts de l'utilisateur parcours plus de 200 pixels de gauche à droite
                         * L'orsque ce déplacement est détecté on lance une nouvelle avtivité, et on y rajoute une transition
                         */
                        if(x2 - x1 > 200){
                            //Left
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        }else if(x1 > x2){
                            //Right
                        }
                        break;
                }
                return false;
            }
        });
        boutton_valide = findViewById(R.id.button_new);
        boutton_valide.setOnTouchListener(new View.OnTouchListener() {
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
                        if(x2 - x1 > 200){
                            //Left
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        }else if(x1 > x2){
                            //Right
                        }
                        break;
                }
                return false;
            }
        });
        track_A = findViewById(R.id.A);
        track_A.setOnTouchListener(new View.OnTouchListener() {
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
                        if(x2 - x1 > 200){
                            //Left
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        }else if(x1 > x2){
                            //Right
                        }
                        break;
                }
                return false;
            }
        });
        track_B = findViewById(R.id.B);
        track_B.setOnTouchListener(new View.OnTouchListener() {
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
                        if(x2 - x1 > 200){
                            //Left
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        }else if(x1 > x2){
                            //Right
                        }
                        break;
                }
                return false;
            }
        });
        track_distance = findViewById(R.id.distanceText);
        track_distance.setOnTouchListener(new View.OnTouchListener() {
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
                        if(x2 - x1 > 200){
                            //Left
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        }else if(x1 > x2){
                            //Right
                        }
                        break;
                }
                return false;
            }
        });
        track_temps = findViewById(R.id.temps);
        track_temps.setOnTouchListener(new View.OnTouchListener() {
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
                        if(x2 - x1 > 200){
                            //Left
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        }else if(x1 > x2){
                            //Right
                        }
                        break;
                }
                return false;
            }
        });

        neige = findViewById(R.id.neige_but);
        neige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = true;
                neige.setBackgroundResource(R.drawable.snowy);
                bool_nuage = false;
                nuage.setBackgroundResource(R.drawable.weather_50);
                bool_orage = false;
                orage.setBackgroundResource(R.drawable.storm_50);
                bool_pluie = false;
                pluie.setBackgroundResource(R.drawable.rain_50);
                bool_soleil = false;
                soleil.setBackgroundResource(R.drawable.sun_50);
            }
        });
        orage = findViewById(R.id.orage_but);
        orage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = false;
                neige.setBackgroundResource(R.drawable.snowy_50);
                bool_nuage = false;
                nuage.setBackgroundResource(R.drawable.weather_50);
                bool_orage = true;
                orage.setBackgroundResource(R.drawable.storm);
                bool_pluie = false;
                pluie.setBackgroundResource(R.drawable.rain_50);
                bool_soleil = false;
                soleil.setBackgroundResource(R.drawable.sun_50);
            }
        });
        pluie = findViewById(R.id.pluie_but);
        pluie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = false;
                neige.setBackgroundResource(R.drawable.snowy_50);
                bool_nuage = false;
                nuage.setBackgroundResource(R.drawable.weather_50);
                bool_orage = false;
                orage.setBackgroundResource(R.drawable.storm_50);
                bool_pluie = true;
                pluie.setBackgroundResource(R.drawable.rain);
                bool_soleil = false;
                soleil.setBackgroundResource(R.drawable.sun_50);
            }
        });
        nuage = findViewById(R.id.nuage);
        nuage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = false;
                neige.setBackgroundResource(R.drawable.snowy_50);
                bool_nuage = true;
                nuage.setBackgroundResource(R.drawable.weather);
                bool_orage = false;
                orage.setBackgroundResource(R.drawable.storm_50);
                bool_pluie = false;
                pluie.setBackgroundResource(R.drawable.rain_50);
                bool_soleil = false;
                soleil.setBackgroundResource(R.drawable.sun_50);
            }
        });
        soleil = findViewById(R.id.soleil);
        soleil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = false;
                neige.setBackgroundResource(R.drawable.snowy_50);
                bool_nuage = false;
                nuage.setBackgroundResource(R.drawable.weather_50);
                bool_orage = false;
                orage.setBackgroundResource(R.drawable.storm_50);
                bool_pluie = false;
                pluie.setBackgroundResource(R.drawable.rain_50);
                bool_soleil = true;
                soleil.setBackgroundResource(R.drawable.sun);
            }
        });


        /***
         * On créer le lien entre Firebase et l'application
         */
        mDatabaseref = FirebaseDatabase.getInstance();
        DataRef_nombre_fenetre = mDatabaseref.getReference();

        //nuit() = permet de détecter quand l'application est en mode sombre
        nuit();
        //onClick() = permet de détecter quand le bouton retour est préssé, ou encore le bouton validé dans ce cas la il sauvegarderas le nouveau trajet
        onclick();
        //textchanged() = permet de détecter qu'aucune case n'est vide
        textchanged();
        //spinner() = objet qui permet d'afficher plusieurs informations deja définis, c'est une table déroulante
        spinner();
    }

    private void onclick() {
        /***
         * quand le bouton retour est préssé on lance une nouvelle activité
         */
        boutton_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Carnet.class);
                startActivity(intent);
            }
        });
        /***
         * quand le bouton "valider" est préssé on lance un processus de sauvegarde d'une nouvelle activité
         */
        boutton_valide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DATAID", "A : " + A + " B : " + B + " distance : " + distance + " temps : " + temps);
                /***
                 * Le if vérifie qu'aucune case n'est vide grâce au variable de type boolean (vrai ou faux)
                 * qui ont été actualisé dans "textchanged"
                 */
                    if(bool_neige){
                        valeurmeteo = "neige";
                    }else if(bool_orage){
                        valeurmeteo = "orage";
                    }else if(bool_pluie){
                        valeurmeteo = "pluie";
                    }else if(bool_nuage){
                        valeurmeteo = "nuage";
                    }else if(bool_soleil){
                        valeurmeteo = "soleil";
                    }

                    try {
                        EditText notes = findViewById(R.id.txtnotes);
                        notes_traj = notes.getText().toString();
                    }catch (Exception e){

                    }


                if(bool_A && bool_B && bool_distance && bool_temps){
                    //On initialise la fonction de sauvregarde de données en local
                    SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                    //On récupère le nombre de fenêtre déjà existante
                    nombre_vue = Integer.parseInt(sharedPref.getString("nombre_fenetre_a_charger", "0"));
                    //On rajoute 1 au nombre de fenêtre
                    nombre_vue++;
                    Log.d("DATAID","Nombre de fentre : " + nombre_vue);
                    //On récupère l'email pour la sauvegarde en ligne
                    login_email = sharedPref.getString("email","NULL").replace(".","%");
                    Log.d("DATAID","Email : " + login_email);


                    //Sauvegarde en ligne
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("nbr").setValue(nombre_vue.toString());
                    mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("A").setValue(A);
                    mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("B").setValue(B);
                    mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Distance").setValue(distance);
                    mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Temps").setValue(temps);
                    mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Voiture").setValue(nom_voiture);
                    try {
                        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Notes").setValue(notes_traj);
                        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Meteo").setValue(valeurmeteo);
                    }catch (Exception e){
                        Log.d("METEO","Aucune météo");
                    }


                    //Sauvegarde locale
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("nombre_fenetre_a_charger",nombre_vue.toString());
                    editor.putString("fenetre"+nombre_vue+"_distance",distance);
                    editor.putString("fenetre"+nombre_vue+"_temps",temps);
                    editor.putString("fenetre"+nombre_vue+"_A",A);
                    editor.putString("fenetre"+nombre_vue+"_B",B);
                    editor.putString("fenetre"+nombre_vue+"_voiture",nom_voiture);
                    editor.putString("fenetre"+nombre_vue+"_voiture_num",name_pos.toString());
                    try {
                        editor.putString("fenetre" + nombre_vue + "_meteo", valeurmeteo);
                        editor.putString("fenetre"+nombre_vue+"_notes",notes_traj);
                    }catch (Exception e){

                    }
                    editor.commit();

                    //On annonce à l'utilisateur que le trajet à bien été ajouté
                    Toast.makeText(getApplicationContext(),"Trajet ajouté",Toast.LENGTH_SHORT).show();

                    //On retourne à l'activité qui affiche tous les trajets
                    Intent intent = new Intent(getApplicationContext(),Carnet.class);
                    startActivity(intent);

                    /***
                     * On affiche un message d'alerte à l'utilisateur si il à voulu créer un trajet
                     * sans avoir remplis une ou plusieurs cases
                     */
                }else{
                    A = track_A.getText().toString();
                    if(A.length() == 0) {
                        track_A.setError("Saisir un point de dépard");
                        bool_A = false;
                    }else{
                        bool_A = true;
                    }
                    B = track_B.getText().toString();
                    if(B.length() == 0) {
                        track_B.setError("Saisir un point d'arrivée");
                        bool_B = false;
                    }else{
                        bool_B = true;
                    }
                    distance = track_distance.getText().toString();
                    if(distance.length() == 0) {
                        track_distance.setError("Saisir une distance");
                        bool_distance = false;
                    }else{
                        bool_distance = true;
                    }
                    temps = track_temps.getText().toString();
                    if(temps.length() == 0) {
                        track_temps.setError("Saisir un temps");
                        bool_temps = false;
                    }else{
                        bool_temps = true;
                    }
                }
            }
        });
    }

    private void textchanged() {

        /***
         * On récupère tous les objets du xml qu'on assigne à des variable java
         */
        A = track_A.getText().toString();
        B = track_B.getText().toString();
        distance = track_distance.getText().toString();
        temps = track_temps.getText().toString();

        /***
         * Cette fonction s'active à chaque fois que le texte change
         */
        track_A.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                A = track_A.getText().toString();
                /***
                 * On vérifie que le texte comporte bien des caractères, qu'il n'est pas vide
                 */
                if(A.length() == 0) {
                    //Si il est vide on affiche une erreur
                    track_A.setError("Saisir un point de dépard");
                    bool_A = false;
                }else{
                    bool_A = true;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        track_B.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                B = track_B.getText().toString();
                if(B.length() == 0) {
                    track_B.setError("Saisir un point d'arrivée");
                    bool_B = false;
                }else{
                    bool_B = true;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        track_distance.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                distance = track_distance.getText().toString();
                if(distance.length() == 0) {
                    track_distance.setError("Saisir une distance");
                    bool_distance = false;
                }else{
                    bool_distance = true;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        track_temps.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                temps = track_temps.getText().toString();
                if(temps.length() == 0) {
                    track_temps.setError("Saisir un temps");
                    bool_temps = false;
                }else{
                    bool_temps = true;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_new_trajet);
            layout.setBackgroundResource(R.color.theme2);
            TextView name = findViewById(R.id.name);
            name.setTextColor(getResources().getColor(R.color.colorText1));
            TextView A = findViewById(R.id.mot_de_passe);
            A.setTextColor(getResources().getColor(R.color.colorText1));
            TextView B = findViewById(R.id.textView7);
            B.setTextColor(getResources().getColor(R.color.colorText1));
            TextView distance = findViewById(R.id.textView8);
            distance.setTextColor(getResources().getColor(R.color.colorText1));
            TextView temps = findViewById(R.id.textView9);
            temps.setTextColor(getResources().getColor(R.color.colorText1));
            TextView voiture = findViewById(R.id.textView_voiture);
            voiture.setTextColor(getResources().getColor(R.color.colorText1));
            TextView notestxtv = findViewById(R.id.textView_notes);
            notestxtv.setTextColor(getResources().getColor(R.color.colorText1));
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_new_trajet);
            layout.setBackgroundResource(R.color.theme1);
            TextView name = findViewById(R.id.name);
            name.setTextColor(getResources().getColor(R.color.colorText1));
            TextView A = findViewById(R.id.mot_de_passe);
            A.setTextColor(getResources().getColor(R.color.secondary));
            TextView B = findViewById(R.id.textView7);
            B.setTextColor(getResources().getColor(R.color.secondary));
            TextView distance = findViewById(R.id.textView8);
            distance.setTextColor(getResources().getColor(R.color.secondary));
            TextView temps = findViewById(R.id.textView9);
            temps.setTextColor(getResources().getColor(R.color.secondary));
        }
    }

    /***
     * On paramètre une détection de mouvement du doigts pour faire une sorte de slide
     */
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x2 - x1 > 200){
                    //Left
                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }else if(x1 > x2){
                    //Right
                }
                break;
        }
        return false;
    }

    public void spinner(){

        //On assigne le spinner xml au spinner java
        Spinner voiture = findViewById(R.id.spinner_voiture);
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        //On créer une liste qui seras remplies par le nom des différentes voitures crées par l'utilisateur
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
        } else {
            voiture.setPopupBackgroundResource(R.color.theme1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_itemn,area);
            voiture.setAdapter(adapter);
        }

        //Des qu'une voiture est sélectionné on récupère sa position dans la liste
        voiture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                name_pos = position;
                Log.d("SPINNER","pos : " + position);
                Log.d("SPINNER","name_pos : " + name_pos.toString());


                /***
                 * Grâce à sa position on remonte donc au nom de la voiture
                 */
                SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                login_email = sharedPref.getString("email","NULL").replace(".","%");

                DataRef_nombre_fenetre = mDatabaseref.getReference("Users").child(login_email).child("Trajets").child("Id");
                DataRef_nombre_fenetre.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        name_pos++;
                        nom_voiture = dataSnapshot.child("Voiture").child(name_pos.toString()).getValue().toString();
                        Log.d("SPINNER", "nom voiture : "+ nom_voiture);
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

        // Bouton de fin de course
        final Button bottom = findViewById(R.id.bottombytt);
        scrollView = findViewById(R.id.scrollviewIT);
        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            //scroll view is at bottom
                            //bottom.setBackgroundColor(getResources().getColor(R.color.transparent));
                            //bottom.setText("");
                            bottom.setVisibility(View.GONE);
                        } else {
                            //scroll view is not at bottom
                            bottom.setVisibility(View.VISIBLE);
                            bottom.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                            bottom.setText("BOTTOM");
                        }
                    }
                });
    }
}