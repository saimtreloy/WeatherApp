package com.moodybugs.saim.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;

public class Developer extends AppCompatActivity {

    ImageButton btnFacebook, btnPlayStor, btnNavigationHomeDev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_developer);
        SetupWindowAnimations();
        Initialization();
        socialAction();
    }

    private void SetupWindowAnimations() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }
    }

    public void Initialization(){
        btnPlayStor = (ImageButton) findViewById(R.id.btnPlayStor);
        btnFacebook = (ImageButton) findViewById(R.id.btnFacebook);
        btnNavigationHomeDev = (ImageButton) findViewById(R.id.btnNavigationHomeDev);
    }

    public void socialAction(){
        btnPlayStor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=MoodyBugs");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/streloy");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnNavigationHomeDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Developer.this, MainActivity.class));
            }
        });
    }
}
