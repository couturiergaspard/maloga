package com.maloga.maloga_gps.connexion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.connexion.Login;
import com.maloga.maloga_gps.connexion.Verification;


public class NotVerified extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_verified);

        Button connetion = findViewById(R.id.connection);
        Button mailButton = findViewById(R.id.mailButton2);
        TextView mail = findViewById(R.id.mail);

        mail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("MAIL-VERIF", "Email sent.");
                                    Intent intent_splash = new Intent(getApplicationContext(), Verification.class);
                                    startActivity(intent_splash);
                                    finish();
                                }
                            }
                        });
            }
        });

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
