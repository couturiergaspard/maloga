package com.maloga.maloga_gps.carnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;


public class Interieur_trajet extends AppCompatActivity {

    //On définis les variables
    Button back;
    Button save;
    Button delete;
    Button bottom;
    Button neige_b;
    Button orage_b;
    Button pluie_b;
    Button nuage_b;
    Button soleil_b;

    EditText A;
    EditText B;
    EditText distance;
    EditText temps;
    EditText notes_in;

    TextView name;

    String num;
    String email;
    String nom_voiture;
    String notes;
    String meteo;

    DatabaseReference mDatabase;
    DatabaseReference DataRef_nombre_fenetre;
    FirebaseDatabase mDatabaseref;

    Integer name_pos = 0;

    Spinner voiture;

    Boolean row2 = false;
    Boolean bool_neige = false;
    Boolean bool_orage = false;
    Boolean bool_pluie = false;
    Boolean bool_nuage = false;
    Boolean bool_soleil = false;

    ScrollView scrollView;

    float x1,x2,y1,y2;

    LinearLayout parentConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interieur_trajet);

        //Boutons
        back = findViewById(R.id.button_return);
            back.setOnTouchListener(new View.OnTouchListener() {
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
                                if(row2 == false) {
                                    //Left
                                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            } else if (x1 - x2 > 200) {
                                //Right
                            }
                            break;
                    }
                    return false;
                }
            });
        save = findViewById(R.id.button_save);
            save.setOnTouchListener(new View.OnTouchListener() {
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
                                if(row2 == false) {
                                    //Left
                                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            } else if (x1 - x2 > 200) {
                                //Right
                            }
                            break;
                    }
                    return false;
                }
            });
        delete = findViewById(R.id.button_delete);
            delete.setOnTouchListener(new View.OnTouchListener() {
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
                                if(row2 == false) {
                                    //Left
                                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            } else if (x1 - x2 > 200) {
                                //Right
                            }
                            break;
                    }
                    return false;
                }
            });
        name = findViewById(R.id.name);
            name.setOnTouchListener(new View.OnTouchListener() {
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
                                if(row2 == false) {
                                    //Left
                                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            } else if (x1 - x2 > 200) {
                                //Right
                            }
                            break;
                    }
                    return false;
                }
            });
        A = findViewById(R.id.A);
            A.setOnTouchListener(new View.OnTouchListener() {
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
                                if(row2 == false) {
                                    //Left
                                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            } else if (x1 - x2 > 200) {
                                //Right
                            }
                            break;
                    }
                    return false;
                }
            });
        B = findViewById(R.id.B);
            B.setOnTouchListener(new View.OnTouchListener() {
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
                                if(row2 == false) {
                                    //Left
                                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            } else if (x1 - x2 > 200) {
                                //Right
                            }
                            break;
                    }
                    return false;
                }
            });
        distance = findViewById(R.id.distanceText);
            distance.setOnTouchListener(new View.OnTouchListener() {
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
                                if(row2 == false) {
                                    //Left
                                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            } else if (x1 - x2 > 200) {
                                //Right
                            }
                            break;
                    }
                    return false;
                }
            });
        temps = findViewById(R.id.temps);
            temps.setOnTouchListener(new View.OnTouchListener() {
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
                                if(row2 == false) {
                                    //Left
                                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            } else if (x1 - x2 > 200) {
                                //Right
                            }
                            break;
                    }
                    return false;
                }
            });

            bottom = findViewById(R.id.bottombytt);
            bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView = findViewById(R.id.scrollviewIT);
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    },300);
                }
            });


        neige_b = findViewById(R.id.neige_but);
        neige_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = true;
                neige_b.setBackgroundResource(R.drawable.snowy);
                bool_nuage = false;
                nuage_b.setBackgroundResource(R.drawable.weather_50);
                bool_orage = false;
                orage_b.setBackgroundResource(R.drawable.storm_50);
                bool_pluie = false;
                pluie_b.setBackgroundResource(R.drawable.rain_50);
                bool_soleil = false;
                soleil_b.setBackgroundResource(R.drawable.sun_50);
            }
        });
        orage_b = findViewById(R.id.orage_but);
        orage_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = false;
                neige_b.setBackgroundResource(R.drawable.snowy_50);
                bool_nuage = false;
                nuage_b.setBackgroundResource(R.drawable.weather_50);
                bool_orage = true;
                orage_b.setBackgroundResource(R.drawable.storm);
                bool_pluie = false;
                pluie_b.setBackgroundResource(R.drawable.rain_50);
                bool_soleil = false;
                soleil_b.setBackgroundResource(R.drawable.sun_50);
            }
        });
        pluie_b = findViewById(R.id.pluie_but);
        pluie_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = false;
                neige_b.setBackgroundResource(R.drawable.snowy_50);
                bool_nuage = false;
                nuage_b.setBackgroundResource(R.drawable.weather_50);
                bool_orage = false;
                orage_b.setBackgroundResource(R.drawable.storm_50);
                bool_pluie = true;
                pluie_b.setBackgroundResource(R.drawable.rain);
                bool_soleil = false;
                soleil_b.setBackgroundResource(R.drawable.sun_50);
            }
        });
        nuage_b = findViewById(R.id.nuage);
        nuage_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = false;
                neige_b.setBackgroundResource(R.drawable.snowy_50);
                bool_nuage = true;
                nuage_b.setBackgroundResource(R.drawable.weather);
                bool_orage = false;
                orage_b.setBackgroundResource(R.drawable.storm_50);
                bool_pluie = false;
                pluie_b.setBackgroundResource(R.drawable.rain_50);
                bool_soleil = false;
                soleil_b.setBackgroundResource(R.drawable.sun_50);
            }
        });
        soleil_b = findViewById(R.id.soleil);
        soleil_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_neige = false;
                neige_b.setBackgroundResource(R.drawable.snowy_50);
                bool_nuage = false;
                nuage_b.setBackgroundResource(R.drawable.weather_50);
                bool_orage = false;
                orage_b.setBackgroundResource(R.drawable.storm_50);
                bool_pluie = false;
                pluie_b.setBackgroundResource(R.drawable.rain_50);
                bool_soleil = true;
                soleil_b.setBackgroundResource(R.drawable.sun);
            }
        });



        parentConstraintLayout = findViewById(R.id.constraint_layout_carnet);

        mDatabaseref = FirebaseDatabase.getInstance();
        DataRef_nombre_fenetre = mDatabaseref.getReference();

        //Détection du mode nuit
        nuit();
        //On rajoute un bouton pour descendre en bas du xml automatiquement
        info();
        //On récupère les données et on les affiches
        config();
        //On paramètre un bouton pour valider les informations qui ont étés modifiées
        onclick();
        //On paramètre le menu déroulant pour les voitures
        spinner();
    }

    private void config(){
        //On récupère le numéro du trajet
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        num = sharedPref.getString("actu_num", "NULL");
        //Log.d("DATAID",num);

        //charger la sauvegarde
        String val_trajetA = sharedPref.getString("fenetre"+num+"_A","Null");
        String val_trajetB = sharedPref.getString("fenetre"+num+"_B","Null");
        A.setText(val_trajetA);
        B.setText(val_trajetB);

        //On mets "..." si le nom est trop long
        if(val_trajetA.length()+val_trajetB.length() >= 21){
             String txt = val_trajetA + " -> " + val_trajetB;
             txt = txt.substring(0,21);
             txt += "...";
             name.setText(txt);
        }else {
            name.setText(val_trajetA + " -> " + val_trajetB);
        }

        String val_distance = sharedPref.getString("fenetre"+num+"_distance","0");
        distance.setText(val_distance);

        String val_temps = sharedPref.getString("fenetre"+num+"_temps","0");
        temps.setText(val_temps);

        final String numero_voiture = sharedPref.getString("fenetre"+num+"_voiture_num","0");

        //On enlève 1 au numéro de voiture car le 0 est compté comme une voiture
        voiture = findViewById(R.id.spinner_voiture);
        voiture.post(new Runnable() {
            public void run() {
                Integer numv = Integer.parseInt(numero_voiture) - 1;
                voiture.setSelection(numv);
            }
        });

        notes_in = findViewById(R.id.txtnotes);
        notes = sharedPref.getString("fenetre"+num+"_notes","");
        notes_in.setText(notes);

        neige_b = findViewById(R.id.neige_but);
        orage_b = findViewById(R.id.orage_but);
        pluie_b = findViewById(R.id.pluie_but);
        nuage_b = findViewById(R.id.nuage);
        soleil_b = findViewById(R.id.soleil);
        meteo = sharedPref.getString("fenetre"+num+"_meteo","");
        if(meteo.equals("neige") || meteo.equals("Neige")){
            neige_b.setBackgroundResource(R.drawable.snowy);
            bool_neige = true;
            orage_b.setBackgroundResource(R.drawable.storm_50);
            bool_orage = false;
            pluie_b.setBackgroundResource(R.drawable.rain_50);
            bool_pluie = false;
            nuage_b.setBackgroundResource(R.drawable.weather_50);
            bool_nuage = false;
            soleil_b.setBackgroundResource(R.drawable.sun_50);
            bool_soleil = false;
        }else if(meteo.equals("orage") || meteo.equals("Orage")){
            neige_b.setBackgroundResource(R.drawable.snowy_50);
            bool_neige = false;
            orage_b.setBackgroundResource(R.drawable.storm);
            bool_orage = true;
            pluie_b.setBackgroundResource(R.drawable.rain_50);
            bool_pluie = false;
            nuage_b.setBackgroundResource(R.drawable.weather_50);
            bool_nuage = false;
            soleil_b.setBackgroundResource(R.drawable.sun_50);
            bool_soleil = false;
        }else if(meteo.equals("pluie") || meteo.equals("Pluie")){
            neige_b.setBackgroundResource(R.drawable.snowy_50);
            bool_neige = false;
            orage_b.setBackgroundResource(R.drawable.storm_50);
            bool_orage = false;
            pluie_b.setBackgroundResource(R.drawable.rain);
            bool_pluie = true;
            nuage_b.setBackgroundResource(R.drawable.weather_50);
            bool_nuage = false;
            soleil_b.setBackgroundResource(R.drawable.sun_50);
            bool_soleil = false;
        }else if(meteo.equals("nuage") || meteo.equals("Nuage")){
            neige_b.setBackgroundResource(R.drawable.snowy_50);
            bool_neige = false;
            orage_b.setBackgroundResource(R.drawable.storm_50);
            bool_orage = false;
            pluie_b.setBackgroundResource(R.drawable.rain_50);
            bool_pluie = false;
            nuage_b.setBackgroundResource(R.drawable.weather);
            bool_nuage = true;
            soleil_b.setBackgroundResource(R.drawable.sun_50);
            bool_soleil = false;
        }else if(meteo.equals("soleil") || meteo.equals("Soleil")){
            neige_b.setBackgroundResource(R.drawable.snowy_50);
            bool_neige = false;
            orage_b.setBackgroundResource(R.drawable.storm_50);
            bool_orage = false;
            pluie_b.setBackgroundResource(R.drawable.rain_50);
            bool_pluie = false;
            nuage_b.setBackgroundResource(R.drawable.weather_50);
            bool_nuage = false;
            soleil_b.setBackgroundResource(R.drawable.sun);
            bool_soleil = true;
        }
    }

    private void onclick(){
        //On parametre le bouton retour
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Carnet.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                ConstraintLayout activity_row2 = findViewById(R.id.activity_interieur_trajet);
            }
        });

        //On parametre le bouton pour sauvegarder les modifications
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bool_neige){
                    meteo = "neige";
                }else if(bool_orage){
                    meteo = "orage";
                }else if(bool_pluie){
                    meteo = "pluie";
                }else if(bool_nuage){
                    meteo = "nuage";
                }else if(bool_soleil){
                    meteo = "soleil";
                }else{
                    meteo = "null";
                }

                //email
                SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                email = sharedPref.getString("email","NULL").replace(".","%");

                //Sauvegarde en ligne
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num).child("A").setValue(A.getText().toString());
                mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num).child("B").setValue(B.getText().toString());
                mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num).child("Distance").setValue(distance.getText().toString());
                mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num).child("Temps").setValue(temps.getText().toString());
                mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num).child("Voiture").setValue(nom_voiture);
                mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num).child("Notes").setValue(notes_in.getText().toString());
                mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num).child("Meteo").setValue(meteo);

                //Sauvegarde locale
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("fenetre"+num+"_distance",distance.getText().toString());
                editor.putString("fenetre"+num+"_temps",temps.getText().toString());
                editor.putString("fenetre"+num+"_A",A.getText().toString());
                editor.putString("fenetre"+num+"_B",B.getText().toString());
                editor.putString("fenetre"+num+"_voiture",nom_voiture);
                editor.putString("fenetre"+num+"_voiture_num",name_pos.toString());
                editor.putString("fenetre"+num+"_notes",notes_in.getText().toString());
                editor.putString("fenetre"+num+"_meteo",meteo);
                editor.commit();

                //On lance l'activité suivante
                Intent intent = new Intent(getApplicationContext(),Carnet.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
        //Bouton pour supprimmer le trajet avec demande de validation
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On crée donc une superposition de layout avec deux nouveau boutons, un pour valider l'autre refuser
                ConstraintLayout activity_basic = findViewById(R.id.cstr1);
                AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.5f);
                animation1.setDuration(500);
                animation1.setStartOffset(0);
                animation1.setFillAfter(true);
                activity_basic.startAnimation(animation1);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.activity_row2, null);
                ConstraintLayout activity_row2 = findViewById(R.id.cstr2);
                activity_row2.addView(rowView);
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in_fast);
                animation.setStartOffset(0);
                rowView.startAnimation(animation);

                /**
                 * On rends les anciens boutons non cliquable
                 */
                back.setClickable(false);
                back.setLongClickable(false);
                A.setClickable(false);
                A.setLongClickable(false);
                B.setClickable(false);
                B.setLongClickable(false);
                distance.setClickable(false);
                distance.setLongClickable(false);
                temps.setClickable(false);
                temps.setLongClickable(false);
                save.setClickable(false);
                save.setLongClickable(false);
                delete.setClickable(false);
                delete.setLongClickable(false);
                voiture.setClickable(false);
                voiture.setLongClickable(false);
                voiture.setEnabled(false);
                row2 = true;

                /**
                 * On rends les nouveaux boutons cliquable
                 */
                final Button yes,no;
                yes = findViewById(R.id.yes);
                no = findViewById(R.id.no);
                //Si le trajet dois vraiment etre supprimé on le retire de la base de donnée
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //email
                        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                        email = sharedPref.getString("email","NULL").replace(".","%");

                        //Sauvegarde en ligne
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        Integer nbr = Integer.parseInt(sharedPref.getString("nombre_fenetre_a_charger", "0"));
                        //On enlève 1 au nombre maximums de fenêtres
                        mDatabase.child("Users").child(email).child("Trajets").child("Id").child("nbr").setValue(nbr-1);
                        mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num).removeValue();


                        if(nbr - Integer.parseInt(num) >= 0) {
                            Integer i = nbr - Integer.parseInt(num);
                            Log.d("DATAID", "nbr fenetre a refaire : " + i);

                            /***
                             * Partie complexe :
                             * Apres avoir supprimé le trajet concerné on se retrouve avec un trou dans la liste de trajet, on va donc décaler un par un tout les trajets
                             * et étudier tout les cas possible
                             */
                            if (i != 0) {
                                for (int k = Integer.parseInt(num); k < nbr; k++) {
                                    Integer num_decal = k + 1;

                                    //Sauvegarde en ligne
                                    //Receuille les donées locale
                                    String val_trajetA = sharedPref.getString("fenetre"+num_decal+"_A","Null");
                                    String val_trajetB = sharedPref.getString("fenetre"+num_decal+"_B","Null");
                                    String val_distance = sharedPref.getString("fenetre"+num_decal+"_distance","0");
                                    String val_temps = sharedPref.getString("fenetre"+num_decal+"_temps","0");
                                    notes = sharedPref.getString("fenetre"+num_decal+"_notes","");

                                    Integer decal_to = num_decal- 1;
                                    //recréer
                                    Log.d("DATAID","num_decal : "+ num_decal);
                                    Log.d("DATAID","decal to : "+ decal_to);
                                    mDatabase.child("Users").child(email).child("Trajets").child("Id").child(decal_to.toString()).child("A").setValue(val_trajetA);
                                    mDatabase.child("Users").child(email).child("Trajets").child("Id").child(decal_to.toString()).child("B").setValue(val_trajetB);
                                    mDatabase.child("Users").child(email).child("Trajets").child("Id").child(decal_to.toString()).child("Distance").setValue(val_distance);
                                    mDatabase.child("Users").child(email).child("Trajets").child("Id").child(decal_to.toString()).child("Temps").setValue(val_temps);
                                    mDatabase.child("Users").child(email).child("Trajets").child("Id").child(decal_to.toString()).child("Notes").setValue(notes);

                                    //supprime
                                    mDatabase.child("Users").child(email).child("Trajets").child("Id").child(num_decal.toString()).removeValue();

                                    //Sauvegarde locale
                                    Integer nbr_total = nbr - 1;
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("nombre_fenetre_a_charger",nbr_total.toString());
                                    editor.putString("fenetre"+decal_to.toString()+"_A",val_trajetA);
                                    editor.putString("fenetre"+decal_to.toString()+"_B",val_trajetB);
                                    editor.putString("fenetre"+decal_to.toString()+"_distance",val_distance);
                                    editor.putString("fenetre"+decal_to.toString()+"_temps",val_temps);
                                    editor.putString("fenetre"+decal_to.toString()+"_notes",notes);
                                    editor.commit();


                                }
                            }else if(nbr>1) {

                                Log.d("DATAID", "Parts 2");
                                //Sauvegarde locale
                                Integer nbr_total = nbr - 1;
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("nombre_fenetre_a_charger", nbr_total.toString());


                                editor.commit();

                            }else if(nbr <= 1){
                                Log.d("DATAID","Parts 3");
                                //Sauvegarde locale
                                Integer nbr_total = nbr - 1;
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("nombre_fenetre_a_charger", nbr_total.toString());


                                mDatabase.child("Users").child(email).child("Trajets").child("Id").child("nbr").setValue("0");
                                mDatabase.child("Users").child(email).child("Trajets").child("Id").child("1").child("Distance").setValue("0");
                                mDatabase.child("Users").child(email).child("Trajets").child("Id").child("1").child("Temps").setValue("0");
                                mDatabase.child("Users").child(email).child("Trajets").child("Id").child("1").child("A").setValue("NULL");
                                mDatabase.child("Users").child(email).child("Trajets").child("Id").child("1").child("B").setValue("NULL");
                                mDatabase.child("Users").child(email).child("Trajets").child("Id").child("1").child("Notes").setValue("NULL");
                                editor.commit();
                            }
                        }


                        Intent intent = new Intent(getApplicationContext(), Carnet.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in_fast,R.anim.fade_out_fast);
                    }
                });
                //On parametre différent endroits pour refuser
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Interieur_trajet.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in_fast,R.anim.fade_out_fast);
                        row2 = false;
                    }
                });
                activity_basic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Interieur_trajet.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in_fast,R.anim.fade_out_fast);
                        row2 = false;
                    }
                });
                LinearLayout activity_basic2 = findViewById(R.id.linear_cstr1);
                activity_basic2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Interieur_trajet.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in_fast,R.anim.fade_out_fast);
                        row2 = false;
                    }
                });
                ConstraintLayout buttons = findViewById(R.id.button_save_del);
                buttons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Interieur_trajet.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in_fast,R.anim.fade_out_fast);
                        row2 = false;
                    }
                });
            }
        });
        //On parametre un slide
        LinearLayout cs1 = findViewById(R.id.linear_cstr1);
        cs1.setOnTouchListener(new View.OnTouchListener() {
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
                            if(row2 == false) {
                                //Left
                                Intent i = new Intent(getApplicationContext(), Carnet.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        } else if (x1 - x2 > 200) {
                            //Right
                        }
                        break;
                }
                return false;
            }
        });
    }
    //Mode nuit
    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_interieur_trajet);
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
            ConstraintLayout layout = findViewById(R.id.activity_interieur_trajet);
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
    //Detection de slide
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
                    if(row2 == false) {
                        Intent i = new Intent(getApplicationContext(), Carnet.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                } else if (x1 - x2 > 200) {
                    //Right
                }
                break;
        }
        return false;
    }

    //Menu déroulant pour les voitures
    public void spinner(){

        voiture = findViewById(R.id.spinner_voiture);
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        ArrayList<String> area = new ArrayList<>();
        final Integer nombre_voiture = Integer.parseInt(sharedPref.getString("nombre_voiture","null"));
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

        voiture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                name_pos = position;
                Log.d("SPINNER","pos : " + position);
                Log.d("SPINNER","name_pos : " + name_pos.toString());

/**
 * Récupère le nom de la voiture
 *
 */
                final SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                email = sharedPref.getString("email","NULL").replace(".","%");
                name_pos++;
                nom_voiture = sharedPref.getString("voiture_numero_"+name_pos.toString(),"null");
                Log.d("SPINNER", "nom voiture : "+ nom_voiture);

                DataRef_nombre_fenetre = mDatabaseref.getReference("Users").child(email).child("Trajets").child("Id");
                DataRef_nombre_fenetre.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
    public void info(){
        LinearLayout linearlay = findViewById(R.id.linear_cstr1);
        linearlay.setOnTouchListener(new View.OnTouchListener() {
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
                            if(row2 == false) {
                                //Left
                                Intent i = new Intent(getApplicationContext(), Carnet.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        } else if (x1 - x2 > 200) {
                            //Right
                        }
                        break;
                }
                return false;
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
