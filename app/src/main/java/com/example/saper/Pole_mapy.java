package com.example.saper;


public class Pole_mapy implements Pole_mapy_okna{
    private boolean is_bomb;
    private boolean is_opened;
    private boolean is_flagged;

    public Pole_mapy(boolean if_is_bomb){
        is_bomb = if_is_bomb;
        is_opened = false;
        is_flagged = false;
    }

    public boolean is_it_bomb(){
        return is_bomb;
    }

    public boolean is_it_opened(){
        return is_opened;
    }

    public boolean is_it_flagged(){
        return is_flagged;
    }

    public void flag_field(){
        is_flagged = !is_flagged;
    }

    public void open_field(){
        is_opened = true;
    }
}
