package com.project.app;

import android.util.Log;
import android.view.View;

/**
 * Created by Sozos on 3/12/14.
 */
public class Guide {
    private int[] routeX = new int[1000];
    private int[] routeY = new int[1000];
    private int tempI;
    public boolean guide(int locY, int locX, int whereY, int whereX, int height, int width, Item[][] gridArray, int time, int second){
        int tempY = whereY;
        int tempX = whereX;
        int distX;
        int distY;
        int tempTime=0;
        tempI=0;
        boolean startY;
        boolean startX;
        boolean reverse = false;
        boolean priorityY;
        boolean priorityX;
        boolean secondReverse = false;
        boolean visited=false;
        boolean moved = false;
        int steps=0;
        boolean stuck;
    while((tempX!=locX||tempY!=locY)&&!visited) {
        stuck=false;
        if (tempY < locY) {
            distY=locY-tempY;
        } else if (tempY > locY) {
            distY=tempY-locY;
        }
        else {
            distY=0;
        }
        if (tempX < locX) {
            distX = locX-tempX;
        } else if (tempX > locX) {
            distX = tempX-locX;
        }
        else {
            distX=0;
        }
        if ((distX>=distY && distX!=0 &&second==1)/*||(distX<distY && distX!=0 &&second==2) || (distX<=distY && distX!=0 &&second==3) || (distX>distY && distX!=0 &&second==4)*/){
            if ((time==2 || time==4)&&tempY <= locY) {
                priorityY = false;
                startY = false;
            }
            else if (time==5||time==7) {
                priorityY = true;
                startY = true;
            }
            else if (tempY < locY){
                priorityY = false;
                startY = false;
            }
            else {
                priorityY = true;
                startY = true;
            }
            if (tempX < locX) {
                priorityX = false;
                startX = false;
            } else {
                priorityX = true;
                startX = true;
            }

            while (tempX!=locX && !visited && !stuck) {

                if (tempX + 1 == width-1 || (((tempY + 1 == height && gridArray[tempY - 1][tempX].canWalk()) || (tempY - 1 == -1 && gridArray[tempY + 1][tempX].canWalk())) && startY != priorityY && !priorityX)) {
                    if (gridArray[tempY][tempX + 1].canWalk() || tempX + 1 == width-1) {
                        priorityX = true;
                    }

                } else if (tempX - 1 == -1 || (((tempY + 1 == height && gridArray[tempY - 1][tempX].canWalk()) || (tempY - 1 == -1 && gridArray[tempY + 1][tempX].canWalk())) && startY != priorityY && priorityX)) {
                    if (tempX - 1 == -1) {
                        priorityX = false;
                    }
                    else if (gridArray[tempY][tempX - 1].canWalk()){
                        priorityX = false;
                    }
                }
                if (tempY + 1 == height-1) {
                    priorityY = true;
                } else if (tempY - 1 == -1) {
                    priorityY = false;
                }
                if (!priorityX && !visited) {
                    if (gridArray[tempY][tempX + 1].canWalk()) {

                        tempX++;
                        gridArray[tempY][tempX].canSet(4);
                        visited =calcVisits(tempY, tempX, gridArray);
                        if(gridArray[tempY][tempX].getVisits()%2==0){
                            gridArray[tempY][tempX-1].setType(0);
                        }
                        moved = true;
                        saveRoute(tempX, tempY);


                    }
                    if (secondReverse && !visited) {

                        if (!priorityY) {
                            if (gridArray[tempY + 1][tempX].canWalk()) {
                                tempY++;
                                gridArray[tempY][tempX].canSet(4);
                                visited =calcVisits(tempY, tempX, gridArray);
                                if(gridArray[tempY][tempX].getVisits()%2==0){
                                    gridArray[tempY-1][tempX].setType(0);
                                }
                                secondReverse = false;
                                saveRoute(tempX, tempY);
                                if (startX) {
                                    priorityX = true;
                                }
                            }

                        } else if (priorityY) {
                            if (gridArray[tempY - 1][tempX].canWalk()) {
                                tempY--;
                                gridArray[tempY][tempX].canSet(4);
                                visited =calcVisits(tempY, tempX, gridArray);
                                if(gridArray[tempY][tempX].getVisits()%2==0){
                                    gridArray[tempY+1][tempX].setType(0);
                                }
                                secondReverse = false;
                                saveRoute(tempX, tempY);
                                if (startX) {
                                    priorityX = true;
                                }
                            }

                        }

                    }
                    if (startX){
                        steps++;
                        if (gridArray[tempY][tempX-(steps+1)].canWalk()){
                            priorityX=true;
                            steps=0;
                        }
                    }
                    else {steps=0;}
                } else if (priorityX&&!visited) {
                    if (gridArray[tempY][tempX - 1].canWalk()) {
                        tempX--;
                        gridArray[tempY][tempX].canSet(4);
                        visited =calcVisits(tempY, tempX, gridArray);
                        if(gridArray[tempY][tempX].getVisits()%2==0){
                            gridArray[tempY][tempX+1].setType(0);
                        }
                        moved = true;
                        saveRoute(tempX, tempY);

                    }
                    if (secondReverse&&!visited) {
                        if (!priorityY) {
                            if (gridArray[tempY + 1][tempX].canWalk()) {
                                tempY++;
                                gridArray[tempY][tempX].canSet(4);
                                visited =calcVisits(tempY, tempX, gridArray);
                                if(gridArray[tempY][tempX].getVisits()%2==0){
                                    gridArray[tempY-1][tempX].setType(0);
                                }
                                secondReverse = false;
                                saveRoute(tempX, tempY);
                                if (!startX) {
                                    priorityX = false;
                                }
                            }

                        } else if (priorityY) {
                            if (gridArray[tempY - 1][tempX].canWalk()) {
                                tempY--;
                                gridArray[tempY][tempX].canSet(4);
                                visited =calcVisits(tempY, tempX, gridArray);
                                if(gridArray[tempY][tempX].getVisits()%2==0){
                                    gridArray[tempY+1][tempX].setType(0);
                                }
                                secondReverse = false;
                                saveRoute(tempX, tempY);
                                if (!startX) {
                                    priorityX = false;
                                }
                            }

                        }
                    }
                    if (!startX){
                        steps++;
                        if (gridArray[tempY][tempX+(steps+1)].canWalk()){
                            priorityX=false;
                            steps=0;
                        }
                    }
                    else {steps=0;}
                }
                if (!priorityY && !moved&&!visited) {
                    if (gridArray[tempY + 1][tempX].canWalk()) {
                        tempY++;
                        gridArray[tempY][tempX].canSet(4);
                        visited =calcVisits(tempY, tempX, gridArray);
                        if(gridArray[tempY][tempX].getVisits()%2==0){
                            gridArray[tempY-1][tempX].setType(0);
                        }
                        saveRoute(tempX, tempY);

                    } else if (startY && !reverse) {
                        priorityX = !priorityX;
                        reverse = true;
                        secondReverse=true;
                    } else if (startY && reverse) {
                        priorityY = true;
                        secondReverse=false;
                    }
                    else if (!startY && secondReverse){
                        secondReverse=false;
                        priorityY=!priorityY;
                    }
                    else if (!startY && reverse) {
                        priorityX = !priorityX;
                        reverse = false;
                        secondReverse = true;
                    } else {
                        priorityY = true;
                    }
                } else if (priorityY && !moved&&!visited) {
                    if (gridArray[tempY - 1][tempX].canWalk()) {
                        tempY--;
                        gridArray[tempY][tempX].canSet(4);
                        visited =calcVisits(tempY, tempX, gridArray);
                        if(gridArray[tempY][tempX].getVisits()%2==0){
                            gridArray[tempY+1][tempX].setType(0);
                        }
                        saveRoute(tempX, tempY);

                    } else if (!startY && !reverse) {
                        priorityX = !priorityX;
                        reverse = true;
                        secondReverse=true;
                    } else if (!startY && reverse) {
                        priorityY = true;
                        secondReverse=false;
                    }
                    else if (startY && secondReverse){
                        secondReverse=false;
                        priorityY=!priorityY;
                    }
                    else if (startY && reverse) {
                        priorityX = !priorityX;
                        reverse = false;
                        secondReverse = true;
                    } else {
                        priorityY = false;
                    }
                }
                else {

                   /*Log.i("Changed","Changed");*/
                   if(time==1){
                       time=2;
                       tempTime=1;
                   }
                   else if (time==2 && tempTime==1){
                        time=4;
                   }
                   else if (time==4){
                       time=2;
                       tempTime=4;
                   }
                   else if (time==2 && tempTime==4){
                       time=1;
                   }
                   stuck=true;
                }
                moved = false;

            }


        }

        else if ((distY>distX && distY!=0)&&second==1 /*||(distY<=distX && distY!=0)&&second==1|| (distY<distX && distY!=0)&&second==2||(distY>=distX && distY!=0)&&second==4*/){
            moved = false;
            secondReverse = false;
            reverse = false;
            if (tempY < locY) {
                priorityY = false;
                startY = false;
            } else {
                priorityY = true;
                startY = true;
            }

            if ((time==2 || time==3) && tempX <= locX) {
                priorityX = false;
                startX = false;
            }
            else if(time==5||time==6) {
                priorityX = true;
                startX = true;
            }
            else if (tempX < locX) {
                priorityX = false;
                startX = false;
            }
            else {
                priorityX = true;
                startX = true;
            }

            while (tempY!=locY && !visited && !stuck) {

                if (tempY + 1 == height-1 || (((tempX + 1 == width && gridArray[tempY][tempX - 1].canWalk()) || (tempX - 1 == -1 && gridArray[tempY][tempX + 1].canWalk())) && startX != priorityX && !priorityY)) {
                    if (gridArray[tempY + 1][tempX].canWalk() || tempY + 1 == height-1) {
                        priorityY = true;
                    }

                } else if (tempY - 1 == -1 || (((tempX + 1 == width && gridArray[tempY][tempX - 1].canWalk()) || (tempX - 1 == -1 && gridArray[tempY][tempX + 1].canWalk())) && startX != priorityX && priorityY)) {
                    if (tempY - 1 == -1) {
                        priorityY = false;
                    }
                    else if (gridArray[tempY - 1][tempX].canWalk()){
                        priorityY = false;
                    }
                }
                if (tempX + 1 == width-1) {
                    priorityX = true;
                } else if (tempX - 1 == -1) {
                    priorityX = false;
                }
                if (!priorityY&&!visited) {
                    if (gridArray[tempY + 1][tempX].canWalk()) {
                        tempY++;
                        gridArray[tempY][tempX].canSet(4);
                        visited =calcVisits(tempY, tempX, gridArray);
                        if(gridArray[tempY][tempX].getVisits()%2==0){
                            gridArray[tempY-1][tempX].setType(0);
                        }
                        moved = true;
                        saveRoute(tempX, tempY);

                    }
                    if (secondReverse&&!visited) {
                        if (!priorityX) {
                            if (gridArray[tempY][tempX + 1].canWalk()) {
                                tempX++;
                                gridArray[tempY][tempX].canSet(4);
                                visited =calcVisits(tempY, tempX, gridArray);
                                if(gridArray[tempY][tempX].getVisits()%2==0){
                                    gridArray[tempY][tempX-1].setType(0);
                                }
                                secondReverse = false;
                                saveRoute(tempX, tempY);
                                if (startY) {
                                    priorityY = true;
                                }
                            }

                        } else if (priorityX) {
                            if (gridArray[tempY][tempX - 1].canWalk()) {
                                tempX--;
                                gridArray[tempY][tempX].canSet(4);
                                visited =calcVisits(tempY, tempX, gridArray);
                                if(gridArray[tempY][tempX].getVisits()%2==0){
                                    gridArray[tempY][tempX+1].setType(0);
                                }
                                secondReverse = false;
                                saveRoute(tempX, tempY);

                                if (startY) {
                                    priorityY = true;
                                }
                            }

                        }
                    }
                    if (startY){
                        steps++;
                        if (gridArray[tempY-(steps+1)][tempX].canWalk()){
                            priorityY=true;
                            steps=0;
                        }
                    }
                    else {steps=0;}
                } else if (priorityY&&!visited) {
                    if (gridArray[tempY - 1][tempX].canWalk()) {
                        tempY--;
                        gridArray[tempY][tempX].canSet(4);
                        visited =calcVisits(tempY, tempX, gridArray);
                        if(gridArray[tempY][tempX].getVisits()%2==0){
                            gridArray[tempY+1][tempX].setType(0);
                        }
                        moved = true;
                        saveRoute(tempX, tempY);

                    }
                    if (secondReverse&&!visited) {
                        if (!priorityX) {
                            if (gridArray[tempY][tempX + 1].canWalk()) {
                                tempX++;
                                gridArray[tempY][tempX].canSet(4);
                                visited =calcVisits(tempY, tempX, gridArray);
                                if(gridArray[tempY][tempX].getVisits()%2==0){
                                    gridArray[tempY][tempX-1].setType(0);
                                }
                                secondReverse = false;
                                saveRoute(tempX, tempY);
                                if (!startY) {
                                    priorityY = false;
                                }
                            }

                        } else if (priorityX) {
                            if (gridArray[tempY][tempX - 1].canWalk()) {
                                tempX--;
                                gridArray[tempY][tempX].canSet(4);
                                visited =calcVisits(tempY, tempX, gridArray);
                                if(gridArray[tempY][tempX].getVisits()%2==0){
                                    gridArray[tempY][tempX+1].setType(0);
                                }
                                secondReverse = false;
                                saveRoute(tempX, tempY);
                                if (!startY) {
                                    priorityY = false;
                                }

                            }

                        }
                    }
                    if (!startY){
                        steps++;
                        if (gridArray[tempY+(steps+1)][tempX].canWalk()){
                            priorityY=false;
                            steps=0;
                        }
                    }
                    else {steps=0;}
                }
                if (!priorityX && !moved&&!visited) {
                    if (gridArray[tempY][tempX + 1].canWalk()) {
                        tempX++;
                        gridArray[tempY][tempX].canSet(4);
                        visited =calcVisits(tempY, tempX, gridArray);
                        if(gridArray[tempY][tempX].getVisits()%2==0){
                            gridArray[tempY][tempX-1].setType(0);
                        }
                        saveRoute(tempX, tempY);

                    } else if (startX && !reverse) {
                        priorityY = !priorityY;
                        reverse = true;
                        secondReverse=true;
                    } else if (startX && reverse) {
                        priorityX = true;
                        secondReverse=false;
                    }
                    else if (!startX && secondReverse){
                        secondReverse=false;
                        priorityX=!priorityX;
                    }
                    else if (!startX && reverse) {
                        priorityY = !priorityY;
                        reverse = false;
                        secondReverse = true;
                    } else {
                        priorityX = true;
                    }
                } else if (priorityX && !moved&&!visited) {
                    if (gridArray[tempY][tempX - 1].canWalk()) {
                        tempX--;
                        gridArray[tempY][tempX].canSet(4);
                        visited =calcVisits(tempY, tempX, gridArray);
                        if(gridArray[tempY][tempX].getVisits()%2==0){
                            gridArray[tempY][tempX+1].setType(0);
                        }
                        saveRoute(tempX, tempY);

                    } else if (!startX && !reverse) {
                        priorityY = !priorityY;
                        reverse = true;
                        secondReverse=true;
                    } else if (!startX && reverse) {

                        priorityX = true;
                        secondReverse=false;
                    }
                    else if (startX && secondReverse){
                        secondReverse=false;
                        priorityX=!priorityX;
                    }
                    else if (startX && reverse) {
                        priorityY = !priorityY;
                        reverse = false;
                        secondReverse = true;
                    } else {
                        priorityX = false;
                    }
                }
                else {
                   /*Log.i("Changed","Changed");*/
                    if(time==1){
                        time=3;
                        tempTime=1;
                    }
                    else if (time==3 && tempTime==1){
                        time=4;
                    }
                    else if (time==4){
                        time=3;
                        tempTime=4;
                    }
                    else if (time==3 && tempTime==4){
                        time=1;
                    }
                    stuck=true;
                }

                moved = false;

            }
        }
    }

        return !(visited && time <= 7 && tempY!=locY && tempX!=locX);


    }

    public void saveRoute(int tempX, int tempY){
        routeX[tempI]=tempX;
        routeY[tempI]=tempY;

        tempI=tempI+1;

    }
    public int[] getRouteX() {
        return routeX;
    }

    public int[] getRouteY() {
        return routeY;
    }

    public int getTempI() {
        return tempI;
    }
    public boolean calcVisits(int locY,int locX,Item[][] gridArray){
        gridArray[locY][locX].incVisited();
        return gridArray[locY][locX].getVisits() >= 10;
    }


}

