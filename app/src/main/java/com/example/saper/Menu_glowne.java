package com.example.saper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Menu_glowne extends AppCompatActivity {

    public static final String s_szerokosc_mapy = "szer_mapy";
    public static final String s_wysokosc_mapy = "wys_mapy";
    public static final String s_min_procentowo = "min_procentowo";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_glowne);

        Button przycisk_easy = this.findViewById(R.id.przycisk_easy);
        przycisk_easy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intencja = new Intent(Menu_glowne.this,Okno_gry.class);
                intencja.putExtra(s_szerokosc_mapy,5);
                intencja.putExtra(s_wysokosc_mapy,5);
                intencja.putExtra(s_min_procentowo,20);

                startActivity(intencja);
            }
        });

        Button przycisk_normal = this.findViewById(R.id.przycisk_normal);
        przycisk_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_glowne.this,Okno_gry.class);
                intent.putExtra(s_szerokosc_mapy,8);
                intent.putExtra(s_wysokosc_mapy,8);
                intent.putExtra(s_min_procentowo, 20);
                startActivity(intent);
            }
        });

        Button przycisk_hard = this.findViewById(R.id.przycisk_hard);
        przycisk_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_glowne.this,Okno_gry.class);
                intent.putExtra(s_szerokosc_mapy,12);
                intent.putExtra(s_wysokosc_mapy,12);
                intent.putExtra(s_min_procentowo, 20);
                startActivity(intent);
            }
        });


        Log.d("komunikat","create");
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        Log.d("komunikat","start");
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        Log.d("komunikat","stop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        Log.d("komunikat","destroy");
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        Log.d("komunikat","pause");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Log.d("komunikat","resume");

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        Log.d("komunikat","restart");
    }
}


