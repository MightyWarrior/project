package com.project.app;

/**
 * Created by Sozos on 3/12/14.
 */
public class Item {
    private int type = 0;
    private int visited = 0;
    public boolean canWalk(){
        return type == 0 || type == 5 || type == 4;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void canSet(int type){
        if (this.type == 0){
            this.type=type;
        }
    }
    public void incVisited(){
        visited++;
    }
    public void zeroVisited(){
        visited=0;
    }
    public int getVisits(){ return visited;}
}
