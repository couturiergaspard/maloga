package com.maloga.maloga_gps.connexion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.maloga.maloga_gps.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Verification extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        Button connetion = findViewById(R.id.mailButton);
        TextView mail = findViewById(R.id.mail);

        mail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        connetion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
