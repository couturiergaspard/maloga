package com.maloga.maloga_gps.carnet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.param.Parametre;
import com.maloga.maloga_gps.volant.Volant;

public class Carnet extends AppCompatActivity {

    //On définis les variables
    Button volant;
    Button parametre;
    Button button_new;
    Button click;
    Integer nombre_vue = 0;
    TextView name;
    TextView distance;
    TextView temps;
    LinearLayout parentConstraintLayout;
    private LinearLayout parentLinearLayout;
    TextView Km;
    Integer km_i = 0;
    Integer temps_t = 0;

    MotionEvent touchEvent;
    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carnet);
        //Parametre de départ
        nuit();
        View();
        charger_activite();
        //Bouttons
        Onclick();
    }

    //On définis certains objets
    private void View(){
        //Boutons
        volant = findViewById(R.id.volant);
        parametre = findViewById(R.id.parametre);
        button_new = findViewById(R.id.button_new);
        parentConstraintLayout = findViewById(R.id.constraint_layout_carnet);
        Km = findViewById(R.id.km);


    }
    //On définis le bouton volant qui nous fais changer d'activité
    private void Onclick(){
        volant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_volant = new Intent(getApplicationContext(), Volant.class);
                startActivity(intent_volant);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        //On appliques la fonction onTouch, qui permet de détecter un slide sur la droite ou la gauche.
        findViewById(R.id.volant).setOnTouchListener(new View.OnTouchListener() {
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
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });
        parametre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_parametre = new Intent(getApplicationContext(), Parametre.class);
                startActivity(intent_parametre);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        findViewById(R.id.parametre).setOnTouchListener(new View.OnTouchListener() {
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
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_new_trajet.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button_new).setOnTouchListener(new View.OnTouchListener() {
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
                        } else if (x1 - x2 > 350) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });
    }
    //On charge et affiche tous les trajets précedement chargé dans le splash
    private void charger_activite() {
        //On récupère le nombres de trajets
        final SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        nombre_vue = Integer.parseInt(sharedPref.getString("nombre_fenetre_a_charger", "0"));
        Log.d("DATAID","fenetre à charger = " + nombre_vue.toString());
            Integer i;
            //On les affiches 1 par 1
        for ( i = nombre_vue; i != 0; i--){
            parentLinearLayout = findViewById(R.id.constraint_layout_carnet);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.activity_row, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            //On leurs applique le thème de couleur adapté
            Boolean nuit = sharedPref.getBoolean("nuit",false);
            if(nuit == true){
                ConstraintLayout layout = rowView.findViewById(R.id.activity_row);
                layout.setBackgroundResource(R.color.theme2);
                TextView name = layout.findViewById(R.id.text_name);
                name.setTextColor(getResources().getColor(R.color.colorText1));
                TextView distance = layout.findViewById(R.id.text_distance);
                distance.setTextColor(getResources().getColor(R.color.colorText1));
                TextView temps = layout.findViewById(R.id.text_temps);
                temps.setTextColor(getResources().getColor(R.color.colorText1));
            }else{
                ConstraintLayout layout = rowView.findViewById(R.id.activity_row);
                layout.setBackgroundResource(R.color.theme1);
                TextView name = layout.findViewById(R.id.text_name);
                name.setTextColor(getResources().getColor(R.color.secondary));
                TextView distance = layout.findViewById(R.id.text_distance);
                distance.setTextColor(getResources().getColor(R.color.secondary));
                TextView temps = layout.findViewById(R.id.text_temps);
                temps.setTextColor(getResources().getColor(R.color.secondary));
            }

            //Si le nom du trajet est trop long il se finiras par "..."
            String val_trajetA = sharedPref.getString("fenetre"+i+"_A","Null");
            String val_trajetB = sharedPref.getString("fenetre"+i+"_B","Null");
            name = rowView.findViewById(R.id.text_name);
            if(val_trajetA.length()+val_trajetB.length() >= 21){
                String txt = val_trajetA + " -> " + val_trajetB;
                txt = txt.substring(0,21);
                txt += "...";
                name.setText(txt);
            }else {
                name.setText(val_trajetA + " -> " + val_trajetB);
            }

            //On remplis les trajets de leurs infos
            String val_distance = sharedPref.getString("fenetre"+i+"_distance","0");
            distance = rowView.findViewById(R.id.text_distance);
            distance.setText(" Distance : "+ val_distance+" km");
            km_i += Integer.parseInt(val_distance);

            String val_temps = sharedPref.getString("fenetre"+i+"_temps","0");
            temps = rowView.findViewById(R.id.text_temps);
            temps.setText(" Temps : " + val_temps+" min");
            temps_t += Integer.parseInt(val_temps);

            click = rowView.findViewById(R.id.click);
            click.setId(i);
            final int id_ = click.getId();

            Boolean nuit2 = sharedPref.getBoolean("nuit",false);
            if(nuit2 == true){
                View view = rowView.findViewById(R.id.line);
                view.setBackgroundColor(getResources().getColor(R.color.colorText1));
            }


            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());


            //On paramètre un clic qui permet de "rentrer" dans le trajet concerné
            click = rowView.findViewById(id_);
            click.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(),"Button clicked index = " + id_, Toast.LENGTH_SHORT).show();

                    //sauvegarde local du numéro de la fenetre
                    SharedPreferences.Editor editor = sharedPref.edit();
                    Integer id = id_;
                    editor.putString("actu_num",id.toString());
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), Interieur_trajet.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });
        }
        Log.d("DATAID",km_i.toString());
        Km.setText(km_i.toString());




        /**
        Sauvegarde de toutes les infos utiles pour les parametres :
         **/
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("info_trajets_distance_totale",km_i.toString());
        editor.putString("info_trajets_temps_total",temps_t.toString());
        editor.commit();


        /**
         * Detection de slide
         */
        findViewById(R.id.scrollView2).setOnTouchListener(new View.OnTouchListener() {
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
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
            ConstraintLayout layout = findViewById(R.id.activity_carnet);
            layout.setBackgroundResource(R.color.theme2);
            TextView km = findViewById(R.id.km);
            km.setTextColor(getResources().getColor(R.color.colorText1));
            TextView km_s = findViewById(R.id.textView2);
            km_s.setTextColor(getResources().getColor(R.color.colorText1));
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_carnet);
            layout.setBackgroundResource(R.color.theme1);
        }
    }

//Detection de slide
    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {
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
                } else if (x1 - x2 > 200) {
                    //Right
                    Intent i = new Intent(getApplicationContext(), Volant.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return false;
    }
}