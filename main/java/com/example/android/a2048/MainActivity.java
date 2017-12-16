package com.example.android.a2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import android.view.GestureDetector.OnGestureListener;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnGestureListener{

    private static int grid[][]=new int[4][4];
    private int score, best, started,win;
    TextView b[][] =new TextView[4][4];
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int idd[][] = {{R.id.block00, R.id.block01, R.id.block02, R.id.block03},
                {R.id.block10, R.id.block11, R.id.block12, R.id.block13},
                {R.id.block20, R.id.block21, R.id.block22, R.id.block23},
                {R.id.block30, R.id.block31, R.id.block32, R.id.block33}};
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<4;j++) {
                b[i][j] = (TextView) findViewById(idd[i][j]);
            }
        started=win=0;
        start();
        draw();
        gestureDetector = new GestureDetector(MainActivity.this, MainActivity.this);
        ImageButton ib= (ImageButton)findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
    }
    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {


        if(motionEvent1.getY() - motionEvent2.getY() > 200){

            onUpSwipe();
            return true;
        }

        else if(motionEvent2.getY() - motionEvent1.getY() > 200){

            onDownSwipe();

            return true;
        }
        else if(motionEvent1.getX() - motionEvent2.getX() > 200){

            onLeftSwipe();

            return true;
        }

        else if(motionEvent2.getX() - motionEvent1.getX() > 200) {

            onRightSwipe();

            return true;
        }
        else {

            return true ;
        }
    }
    public static void copy(int a[][],int b[][])
    {
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<4;j++)
                a[i][j]=b[i][j];
    }
    public static boolean isEqual(int a[][],int b[][])
    {
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<4;j++)
                if(a[i][j]!=b[i][j])
                    return false;
        return true;
    }
    public void onRightSwipe()
            {
                int i, j, k, temp;
                int check[][]=new int[4][4];
                copy(check,grid);
                for (i = 0; i < 4; i++) {
                    k = j = 3;
                    while (j > 0) {
                        if (grid[i][j] != 0) {
                            temp = j - 1;
                            while (grid[i][temp] == 0 && temp > 0)
                                temp--;
                            if (grid[i][j] == grid[i][temp]) {
                                grid[i][k] = grid[i][j]*2;
                                j = temp - 1;
                                score += grid[i][k] * (log2(grid[i][k]) - 1);
                            } else {
                                grid[i][k] = grid[i][j];
                                j--;
                            }
                            k--;
                        } else
                            j--;
                    }
                    if(j==0)
                        grid[i][k--]=grid[i][j];
                    while (k >= 0)
                        grid[i][k--] = 0;
                }
                if (score > best)
                    best = score;
                if(!isEqual(grid,check))
                addRandomTile();
                draw();
                if(isGameOver())
                    endGame();
            }


            public void onLeftSwipe() {
                int i, j, k, temp;
                int check[][]=new int[4][4];
                copy(check,grid);
                for (i = 0; i < 4; i++) {
                    k = j = 0;
                    while (j < 3) {
                        if (grid[i][j] != 0) {
                            temp = j + 1;
                            while (grid[i][temp] == 0 && temp < 3)
                                temp++;
                            if (grid[i][j] == grid[i][temp]) {
                                grid[i][k] = grid[i][j]*2;
                                j = temp + 1;
                                score += grid[i][k] * (log2(grid[i][k]) - 1);
                            } else {
                                grid[i][k] = grid[i][j];
                                j++;
                            }
                            k++;
                        } else
                            j++;
                    }
                    if(j==3)
                        grid[i][k++]=grid[i][j];
                    while (k < 4)
                        grid[i][k++] = 0;
                }
                if (score > best)
                    best = score;
                if(!isEqual(grid,check))
                    addRandomTile();
                draw();
                if(isGameOver())
                    endGame();
            }

            public void onDownSwipe()
            {
                int i, j, k, temp;
                int check[][]=new int[4][4];
                copy(check,grid);
                for (j = 0; j < 4; j++) {
                    k = i = 3;
                    while (i > 0) {
                        if (grid[i][j] != 0) {
                            temp = i - 1;
                            while (grid[temp][j] == 0 && temp > 0)
                                temp--;
                            if (grid[i][j] == grid[temp][j]) {
                                grid[k][j] = grid[i][j]*2;
                                i = temp-1;
                                score += grid[k][j] * (log2(grid[k][j]) - 1);
                            } else {
                                grid[k][j] = grid[i][j];
                                i--;
                            }
                            k--;
                        } else
                            i--;
                    }
                    if(i==0)
                        grid[k--][j]=grid[i][j];
                    while (k >= 0)
                        grid[k--][j] = 0;
                }
                if (score > best)
                    best = score;
                if(!isEqual(grid,check))
                addRandomTile();
                draw();
                if(isGameOver())
                    endGame();
            }

            public void onUpSwipe() {
                int i, j, k, temp;
                int check[][]=new int[4][4];
                copy(check,grid);
                for (j = 0; j < 4; j++) {
                    k = i = 0;
                    while (i < 3) {
                        if (grid[i][j] != 0) {
                            temp = i + 1;
                            while (grid[temp][j] == 0 && temp < 3)
                                temp++;
                            if (grid[i][j] == grid[temp][j]) {
                                grid[k][j] = grid[i][j]*2;
                                i = temp+1;
                                score += grid[k][j] * (log2(grid[k][j]) - 1);
                            } else {
                                grid[k][j] = grid[i][j];
                                i++;
                            }
                            k++;
                        } else
                            i++;
                    }
                    if(i==3)
                        grid[k++][j]=grid[i][j];
                    while (k < 4)
                        grid[k++][j] = 0;
                }
                if (score > best)
                    best = score;
                if(!isEqual(grid,check))
                addRandomTile();
                draw();
                if(isGameOver())
                    endGame();
            }

    public void addRandomTile()
    {
        Random rand = new Random();
        int x,y;
        do {

            x = rand.nextInt(4);
            y = rand.nextInt(4);
        }while(grid[x][y]!=0);
        int var=rand.nextFloat()<0.9?2:4;
        grid[x][y]=var;
    }
    public void save()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<4;j++)
                editor.putInt(Integer.toString(i*10+j),grid[i][j]);
        editor.putInt("score",score);
        editor.putInt("best",best);
        editor.putInt("flag",1);
        editor.apply();

    }
    public void start()
    {
          int i,j;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        for(i=0;i<4;i++)
            for(j=0;j<4;j++)
                grid[i][j]=pref.getInt(Integer.toString(i*10+j),0);
        int flag=pref.getInt("flag",0);
        if(started==flag) {
            addRandomTile();
            addRandomTile();
        }
        score=pref.getInt("score",0);
        best=pref.getInt("best",0);
        started=1;
    }
    public void reset()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<4;j++)
                editor.putInt(Integer.toString(i*10+j),0);
        editor.putInt("score",0);
        editor.putInt("flag",0);
        editor.putInt("winFlag",0);
        started=0;
        win=0;
        editor.apply();
    }
    public void restart()
    {
        reset();
        start();
        draw();
    }
    public static int log2(int x) {
        int y,v;
        if(x==0)
            return 0;
        if (x <= 0) {
            throw new IllegalArgumentException(""+x+" <= 0");
        }
        v = x;
        y = -1;
        while (v>0) {
            v >>=1;
            y++;
        }
        return y;
    }
    public void draw()
    {
            int i,j;
            int img[] = {R.drawable.t0, R.drawable.t2, R.drawable.t4, R.drawable.t8, R.drawable.t16, R.drawable.t32, R.drawable.t64, R.drawable.t128, R.drawable.t256, R.drawable.t512, R.drawable.t1024, R.drawable.t2048, R.drawable.t4096, R.drawable.t8192, R.drawable.t16384, R.drawable.t32768, R.drawable.t65536, R.drawable.t131072};

            for(i=0;i<4;i++)
                for(j=0;j<4;j++)
                {
                    b[i][j].setBackgroundResource(img[log2(grid[i][j])]);
                    if(grid[i][j]!=0)
                        b[i][j].setText(Integer.toString(grid[i][j]));
                    else
                        b[i][j].setText("");
                    if(grid[i][j]==2048)
                        win=1;
                }
        TextView tv=(TextView)findViewById(R.id.score);
        TextView tv1=(TextView)findViewById(R.id.best);
        tv.setText(Integer.toString(score));
        tv1.setText(Integer.toString(best));
        save();
        if(win==1)
            declareWin();
    }
    public void declareWin()
    {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int flag=pref.getInt("winFlag",0);
        if(flag==0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //Setting message manually and performing action on button click
            builder.setMessage("You Win!").setCancelable(false)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("winFlag",1);
                            editor.apply();
                        }
                    });

            //Creating dialog box
            final AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Congratulations!");
            alert.show();
        }
    }
    public boolean isNoMove()
    {
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<3;j++)
                if(grid[i][j]==grid[i][j+1])
                    return false;

        for(j=0;j<4;j++)
            for(i=0;i<3;i++)
                if(grid[i][j]==grid[i+1][j])
                    return false;
        return true;
    }
    public boolean isGameOver()
    {
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<4;j++)
                if(grid[i][j]==0)
                    return false;
        return isNoMove();
    }
    public void endGame()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Setting message manually and performing action on button click
        builder.setMessage("Try Again").setCancelable(false)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        restart();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        //Creating dialog box
        final AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("GAME OVER!");
        alert.show();
    }
}
