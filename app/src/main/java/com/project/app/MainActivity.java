package com.project.app;


import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends ActionBarActivity{
    private static Item[][] gridItems;
    private static final int cellSize =32;
    private static int width;
    private static int height;
    private boolean first = true;
    private int whereX;
    private int whereY;
    boolean spawned = true;
    private static int locX = 4;
    private static int locY = 4;
    public static int[] routeX = new int[1000];
    public static int[] routeY = new int[1000];
    public static int tempI = 0;
    Guide gd = new Guide();
    private GridView gridView;
    private int editKey = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        width = size.x/cellSize;
        height = size.y/cellSize;


        gridItems =new Item[height][width];
        for (int i=0; i < height; i++){
            for (int j=0; j < width; j++){
                gridItems[i][j] = new Item();
            }
        }

        gridView = new GridView(this, width, height);
        initializeGrid();
        setContentView(gridView);
        gridView.requestFocus();
        gridView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int posX = x/ cellSize;
                    int posY = y/ cellSize;
                    if(editKey==0) {
                        markStart(x, y);
                    }
                    else if (editKey==45){
                        gridItems[posY][posX].setType(0);
                        gridView.invalidate();
                    }
                    else if (editKey==33){
                        gridItems[posY][posX].setType(1);
                        gridView.invalidate();
                    }
                    else if (editKey==51){
                        gridItems[posY][posX].setType(3);
                        gridView.invalidate();
                    }
                    else if (editKey==46){
                        gridItems[posY][posX].setType(5);
                        locX=posX;
                        locY=posY;
                        gridView.invalidate();
                    }
                    else if (editKey==31){
                        cleanRoute();
                        gridView.invalidate();
                    }
                    else {
                        editKey=0;
                    }
                    //Log.i("posX", Integer.toString(posX));
                    //Log.i("posY", Integer.toString(posY));

                }
                return true;
            }
        });
    }
    public static Item[][] getGridItems() {return gridItems;}
    public void markStart(int x,int y){

        int posX = x/ cellSize;
        int posY = y/ cellSize;
        if (gridItems[posY][posX].getType() ==0) {
            gridItems[posY][posX].setType(2);

            if (first) {
                first = false;
            } else {
                cleanRoute();
            }
            whereY = posY;
            whereX = posX;
            if(spawned){
                guide();
            }
            gridView.invalidate();
        }
    }

    public void initializeGrid(){
        for (int i =2; i<19; i++){
            gridItems[i][3].setType(3);
            gridItems[i][36].setType(3);
        }
        for (int i=3; i<37;i++){
            gridItems[2][i].setType(3);
            gridItems[18][i].setType(3);
        }
        for (int i=17; i>8; i--){
            gridItems[i][15].setType(3);
            gridItems[i][24].setType(3);
        }
        for (int i=4; i<15; i++){
            for (int j=17; j>2; j--){
                if(((i!=4 && i!=9 && i!=14) || j==17)&&j%2!=0){
                    gridItems[j][i].setType(1);
                }
            }
        }
        for (int i=25; i<36; i++){
            for (int j=17; j>2; j--){
                if(((i!=25 && i!=30 && i!=35) || j==17)&&j%2!=0){
                    gridItems[j][i].setType(1);
                }
            }
        }



    }
    public void guide(){

        int i=0;
        boolean something=false;
        while (!something && i<=7){
        i++;
            cleanRoute();
            gridItems[whereY][whereX].setType(2);

        something =gd.guide(locY, locX, whereY, whereX, height, width, gridItems, i,1);
        }
        /*Log.i("Try: ", Integer.toString(i));*/




    }
    //45=q 51=w 33=e 46=r
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //Log.i("key pressed", String.valueOf(event.getKeyCode()));
        editKey=event.getKeyCode();
        return super.dispatchKeyEvent(event);
    }
    public void cleanRoute(){
        tempI=gd.getTempI();
        routeY=gd.getRouteY();
        routeX=gd.getRouteX();
        gridItems[whereY][whereX].setType(0);
        for (int s =0; s < tempI-1; s++){
            gridItems[routeY[s]][routeX[s]].setType(0);
            gridItems[routeY[s]][routeX[s]].zeroVisited();
        }
        gridItems[routeY[tempI]][routeX[tempI]].zeroVisited();
        gridItems[locY][locY].zeroVisited();

    }
}
