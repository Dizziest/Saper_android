package com.example.saper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Mapa {
    private int width;
    private int height;
    private Pole_mapy array[][];
    private int fields_to_open;

    public Mapa(int n_width, int n_height, int percent_of_mines){
        width = n_width;
        height = n_height;

        array = new Pole_mapy[height][width];
        int how_many_bombs = width * height * percent_of_mines / 100;
        this.fields_to_open = width * height - how_many_bombs;

        List<Punkt> points = new ArrayList<>();
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                points.add(new Punkt(j,i));
            }
        }

        Collections.shuffle(points);

        for (int i = 0; i < how_many_bombs; i++){
            Punkt point = points.remove(0);
            array[point.y][point.x] = new Pole_mapy(true);
        }

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if (array[i][j] == null){
                    array[i][j] = new Pole_mapy(false);
                }
            }
        }
    }

    public int return_width(){
        return width;
    }

    public int return_height(){
        return height;
    }

    public Pole_mapy return_field(int nx, int ny){
        return array[ny][nx];
    }

    public int return_number_of_bombs_around(int nx, int ny){
        int number_of_bombs = 0;

        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                int x = nx + j;
                int y = ny + i;

                if(i == 0 && j == 0){
                    continue;
                }
                if(x < 0 || y < 0 || x >= width || y >= height){
                    continue;
                }
                if(array[y][x].is_it_bomb() == true){
                    number_of_bombs++;
                }

            }
        }
        return number_of_bombs;
    }

    public int count_score(){
        int score = 0;
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                Pole_mapy field = this.array[i][j];
                if(field.is_it_opened() && field.is_it_bomb() == false){
                    score += 10;
                }
            }
        }
        return score;
    }

    public boolean check_for_win(){
        if(this.fields_to_open == 0){
            return true;
        } else {
            return false;
        }
    }

    public void open(int nx, int ny){
        Pole_mapy field = this.array[ny][nx];
        if (field.is_it_opened() == false && field.is_it_bomb() == false){
            this.fields_to_open--;
        }
        field.open_field();
    }

    public List<Punkt> return_zero_fields_around(int nx, int ny){
        List<Punkt> result = new LinkedList<>();
        List<Pole_mapy> analized_fields = new LinkedList<>();

        List<Punkt> list = new LinkedList<>();
        list.add(new Punkt(nx,ny));
        do{
            Punkt point = list.remove(0);

            for(int i = -1; i < 2; i++){
                for (int j = -1; j < 2; j++){
                    int x = point.x + j;
                    int y = point.y + i;

                    if (i == 0 && j == 0){
                        continue;
                    }
                    if (x < 0 || y < 0 || x >= this.width || y >= this.height){
                        continue;
                    }
                    if(analized_fields.contains(this.array[y][x]) == true){
                        continue;
                    }

                    int bombs_around = this.return_number_of_bombs_around(x,y);
                    if(bombs_around == 0){
                        result.add(new Punkt(x,y));
                        list.add(new Punkt(x,y));
                    }
                    analized_fields.add(this.array[y][x]);
                }
            }
        } while (list.size() > 0);
        return result;
    }

    public void set_flag(int nx, int ny){
        Pole_mapy field = this.array[ny][nx];
        field.flag_field();
    }
}
