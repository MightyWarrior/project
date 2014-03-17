package com.project.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Sozos on 3/10/14.
 */
public class GridView extends View {

    private static final int cellSize = 32;
    private static int width;
    private static int height;
    private static int cellSizeX;
    private static int cellSizeY;

    public GridView(Context context, int x, int y) {
        super(context);
        this.width=x;
        this.height=y;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.background));
        Paint route = new Paint();
        route.setColor(getResources().getColor(R.color.route));
        Paint cell = new Paint();
        cell.setColor(getResources().getColor(R.color.cell));
        Paint wall = new Paint();
        wall.setColor(getResources().getColor(R.color.wall));
        Paint position = new Paint();
        position.setColor(getResources().getColor(R.color.pos));

        // draw background
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        // draw cells
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (MainActivity.getGridItems()[h][w].getType() == 1) {
                    canvas.drawRect(
                            w * cellSize,
                            h * cellSize,
                            (w * cellSize) + cellSize,
                            (h * cellSize) + cellSize,
                            cell);
                }
                else if (MainActivity.getGridItems()[h][w].getType() == 2) {
                    canvas.drawRect(
                            w * cellSize,
                            h * cellSize,
                            (w * cellSize) + cellSize,
                            (h * cellSize) + cellSize,
                            position);
                }
                else if (MainActivity.getGridItems()[h][w].getType() == 3) {
                    canvas.drawRect(
                            w * cellSize,
                            h * cellSize,
                            (w * cellSize) + cellSize,
                            (h * cellSize) + cellSize,
                            wall);
                }
                else if (MainActivity.getGridItems()[h][w].getType() == 4) {
                    canvas.drawRect(
                            w * cellSize,
                            h * cellSize,
                            (w * cellSize) + cellSize,
                            (h * cellSize) + cellSize,
                            route);
                }
                else if (MainActivity.getGridItems()[h][w].getType() == 5) {
                    canvas.drawRect(
                            w * cellSize,
                            h * cellSize,
                            (w * cellSize) + cellSize,
                            (h * cellSize) + cellSize,
                            position);
                }

            }
        }
    }
}