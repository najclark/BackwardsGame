package io.github.najclark.backwardsgame;

/**
 * Created by nicholas on 6/25/16.
 */
public class Word {

    private int x, y, speed, size;
    private String text;

    public Word(String text, int x, int y, int speed, int size){
        this.text = text;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
    }

    public void moveDown(){
        this.y = y + speed;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getText(){
        return text;
    }

    public int getSize(){
        return size;
    }
}
