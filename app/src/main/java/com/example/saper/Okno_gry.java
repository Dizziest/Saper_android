package com.example.saper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static android.graphics.Color.*;

public class Okno_gry extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okno_gry);

        Bundle bundle = this.getIntent().getExtras();

        int szer_mapy = bundle.getInt(Menu_glowne.s_szerokosc_mapy);
        int wys_mapy = bundle.getInt(Menu_glowne.s_wysokosc_mapy);
        int min_procentowo = bundle.getInt(Menu_glowne.s_min_procentowo);
        final List<Button> buttons = new ArrayList<>();
        final Timer timer = new Timer();
        final Button buttons2D[][]= new Button[wys_mapy][szer_mapy];
        final Mapa map;
        map = new Mapa(szer_mapy,wys_mapy,min_procentowo);

        AlertDialog.Builder builder = new AlertDialog.Builder(Okno_gry.this);
        builder.setMessage("Przegrałeś!");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Powrót",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Okno_gry.this, Menu_glowne.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                }
        );
        final AlertDialog alert_lose = builder.create();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Okno_gry.this);
        builder1.setMessage("Wygrałeś!");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Powrót",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Okno_gry.this, Menu_glowne.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                }
        );

        final AlertDialog alert_win = builder1.create();

        Log.d("komunikat","odebralem! szerokosc = "+szer_mapy);


        final LinearLayout panel_mapy = this.findViewById(R.id.panel_mapy);

        for(int i = 0; i < wys_mapy; i++){
            LinearLayout panel_wiersza = new LinearLayout(this);
            panel_wiersza.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,1
            ));
            panel_wiersza.setOrientation(LinearLayout.HORIZONTAL);


            for(int j = 0; j < szer_mapy; j++){
                final Button przycisk = new Button(this);
                przycisk.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,1
                ));
                buttons.add(przycisk);
                buttons2D[i][j]=przycisk;

                final int x = j;
                final int y = i;

                przycisk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(przycisk.isEnabled()==false)
                            return;

                        Pole_mapy field = map.return_field(x,y);

                        if (field.is_it_opened() == false){
                            if (field.is_it_flagged() == false){
                                map.open(x,y);
                                if(field.is_it_bomb() == true){
                                    przycisk.setBackgroundColor(RED);
                                    alert_lose.show();
                                    for (Button przycisk : buttons){
                                        przycisk.setEnabled(false);
                                    }
                                } else {
                                    int bombs_around = map.return_number_of_bombs_around(x,y);
                                    przycisk.setText(""+map.return_number_of_bombs_around(x,y));
                                    if(bombs_around == 0){
                                        List<Punkt> zero_fields = map.return_zero_fields_around(x,y);
                                        for (Punkt punkt : zero_fields){
                                            buttons2D[punkt.y][punkt.x].setText("0");
                                            buttons2D[punkt.y][punkt.x].setEnabled(false);
                                            map.open(punkt.x,punkt.y);
                                            for (int i = -1; i < 2; i++){
                                                for (int j = -1; j < 2; j++){
                                                    int neighbour_x = punkt.x + j;
                                                    int neighbour_y = punkt.y + i;

                                                    if(neighbour_x < 0 || neighbour_x >= map.return_width() || neighbour_y < 0 || neighbour_y >= map.return_height() || (i == 0 && j == 0)){
                                                        continue;
                                                    }
                                                    buttons2D[neighbour_y][neighbour_x].setText(""+map.return_number_of_bombs_around(neighbour_x, neighbour_y));
                                                    map.open(neighbour_x, neighbour_y);
                                                }
                                            }
                                        }
                                        przycisk.setEnabled(false);
                                    }
                                    if (map.check_for_win() == true){
                                        alert_win.show();
                                        for (Button przycisk : buttons){
                                            przycisk.setEnabled(false);
                                        }
                                    }
                                }
                            }
                            panel_mapy.requestFocus();
                        }
                    }
                });

                przycisk.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        map.set_flag(x,y);
                        przycisk.setTextColor(BLUE);
                        przycisk.setText("#");
                        return true;
                    }
                });

                panel_wiersza.addView(przycisk);

            }

            panel_mapy.addView(panel_wiersza);
        }

    }

}
