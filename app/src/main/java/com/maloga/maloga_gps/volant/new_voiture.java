package com.maloga.maloga_gps.volant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maloga.maloga_gps.R;

import java.util.ArrayList;


public class new_voiture extends AppCompatActivity {

    String nom;
    String login_email;

    EditText editText;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_voiture);

        nuit();
        spinner();

        Button retour = findViewById(R.id.button_return);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Volant.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_fast,R.anim.fade_out_fast);
            }
        });

        Button confirm = findViewById(R.id.button_next);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute();
            }
        });

    }
    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_new_voiture);
            layout.setBackgroundResource(R.color.theme2);
            ConstraintLayout layout2 = findViewById(R.id.activity_new_voiture_2);
            layout2.setBackgroundResource(R.color.theme2);
            Button geo = findViewById(R.id.geo_start);
            geo.setBackground(getResources().getDrawable(R.drawable.base_n));
            ImageView imageView = findViewById(R.id.imageView2);
            imageView.setBackground(getResources().getDrawable(R.drawable.layout_new_voiture_dark));
            TextView txtnouv = findViewById(R.id.text_nouv);
            txtnouv.setTextColor(getResources().getColor(R.color.colorText1));
            EditText editText = findViewById(R.id.editText);
            editText.setTextColor(getResources().getColor(R.color.colorText1));
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_new_voiture);
            layout.setBackgroundResource(R.color.theme1);
        }
    }
//Menu déroulant
    public void spinner(){
        final Spinner voiture = findViewById(R.id.decor);
        final SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        ArrayList<String> area = new ArrayList<>();
        Integer nombre_voiture = Integer.parseInt(sharedPref.getString("nombre_voiture","null"));
        for(int i = 1; i <= nombre_voiture; i++){
            //add values in area arrayList
            String voiture_numero = "voiture_numero_"+i;
            area.add(sharedPref.getString(voiture_numero,"null"));
        }
        voiture.post(new Runnable() {
            public void run() {
                Integer tempo = Integer.parseInt(sharedPref.getString("wvoiture","0"));
                voiture.setSelection(tempo);
            }
        });
        Boolean nuit = sharedPref.getBoolean("nuit", false);

        if (nuit == true) {
            voiture.setPopupBackgroundResource(R.color.theme2);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_itemn,area);
            voiture.setAdapter(adapter);
            ViewCompat.setBackgroundTintList(voiture, ColorStateList.valueOf(getResources().getColor(R.color.colorText1)));
            voiture.bringToFront();
            Button button_r = findViewById(R.id.button_return);
            button_r.bringToFront();
            voiture.setEnabled(false);
        } else {
            voiture.setPopupBackgroundResource(R.color.theme1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_itemj,area);
            voiture.setAdapter(adapter);
            voiture.bringToFront();
            Button button_r = findViewById(R.id.button_return);
            button_r.bringToFront();
            voiture.setEnabled(false);
        }
    }
//On rajoute une voiture a la liste
    public void execute(){
        editText  = findViewById(R.id.editText);
        nom = editText.getText().toString();

        if(nom.length() != 0 ) {
            SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            login_email = sharedPref.getString("email", "NULL").replace(".", "%");

            //Sauvegarde en ligne
            mDatabase = FirebaseDatabase.getInstance().getReference();
            String nbr_max = sharedPref.getString("nombre_voiture", "null");
            Integer nbr_max_i = Integer.parseInt(nbr_max);
            nbr_max_i++;
            mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("Voiture").child("nbr").setValue(nbr_max_i);
            mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("Voiture").child(nbr_max_i.toString()).setValue(nom);

            //Sauvegarde en local
            editor.putString("nombre_voiture", nbr_max_i.toString());
            editor.putString("voiture_numero_" + nbr_max_i.toString(), nom);
            editor.commit();

            Toast.makeText(getApplicationContext(), "Voiture ajoutée", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Volant.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        }

    }
}
