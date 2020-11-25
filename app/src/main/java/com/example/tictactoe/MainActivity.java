 package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button[][] buttons=new Button[3][3];
    private boolean p1turn=true;
    private int roundno;
    private int p1points;
    private int p2points;
    private int draws;
    private TextView tvp1;
    private TextView tvp2;
    private TextView draw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvp1=findViewById(R.id.text_view_p1);
        tvp2=findViewById(R.id.text_view_p2);
        draw=findViewById(R.id.text_view_draw);
        int i,j;
        for(i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                String buttonID="button_"+i+j;
                int resID=getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }//End of for
        Button buttonReset=findViewById(R.id.reset_button);
        buttonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
                {
                    resetGame();
                }
            });
        }


    @Override
    public void onClick(View v)
    {
        if(!((Button) v).getText().toString().equals(""))
        {
            return;
        }
        if(p1turn)
        {
            ((Button) v).setText("X");
        }
        else
        {
            ((Button) v).setText("O");
        }
        roundno++;
        if(check())
        {
            if(p1turn)
                p1wins();
            else
                p2wins();
        }
        else if(roundno==9)
            draw();
        else
            p1turn=!p1turn;

    }
    private boolean check()
    {
        String[][] space=new String[3][3];
        int i,j;
        for(i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                space[i][j]=buttons[i][j].getText().toString();
            }
        }
        for(i=0;i<3;i++)
        {
            if(space[i][0].equals(space[i][1]) && space[i][1].equals(space[i][2]) &&
                    !(space[i][0].equals("")))
                return true;
        }//End of for
        for(i=0;i<3;i++)
        {
            if(space[0][i].equals(space[1][i]) && space[1][i].equals(space[2][i]) &&
                    !(space[0][i].equals("")))
                return true;
        }//End of for
        if(space[0][0].equals(space[1][1]) && space[1][1].equals(space[2][2]) &&
                !(space[1][1].equals("")))
            return true;
        if(space[0][2].equals(space[1][1]) && space[1][1].equals(space[2][0]) &&
                !(space[0][2].equals("")))
            return true;
        return false;
    }
    private void p1wins()
    {
        p1points++;
        Toast.makeText(this, "PLAYER1 WINS", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    private void p2wins()
    {
        p2points++;
        Toast.makeText(this, "PLAYER2 WINS!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    private void draw()
    {
        draws++;
        Toast.makeText(this, "DRAW", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    public void updatePoints()
    {
        tvp1.setText("Player 1 : "+p1points);
        tvp2.setText("Player 2 : "+p2points);
        draw.setText("Draw : "+draws);

    }
    public void resetBoard()
    {
        int i,j;
        for(i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
                buttons[i][j].setText("");
        }
        roundno=0;
        p1turn=true;
    }
    private void resetGame()
    {
        p1points=0;
        p2points=0;
        draws=0;
        updatePoints();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundno", roundno);
        outState.putInt("p1points", p1points);
        outState.putInt("p2points", p2points);
        outState.putBoolean("p1turn", p1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundno=savedInstanceState.getInt("roundno");
        p1points=savedInstanceState.getInt("p1points");
        p2points=savedInstanceState.getInt("p2points");
        p1turn=savedInstanceState.getBoolean("p1turn");

    }
}