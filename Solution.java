package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Solution extends AppCompatActivity {

    TableLayout tableLayout;
    SudokuSolver board;
    boolean solvable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        Intent caller = getIntent();
        solvable = caller.getBooleanExtra("solvable", false);
        board = (SudokuSolver) caller.getSerializableExtra("board");

        TextView prompt = (TextView) findViewById(R.id.textView2);
        String display = getString(R.string.prompt);
        if (solvable == true) display += " " + getString(R.string.solved);
        else display += " " + getString(R.string.unsolved);
        prompt.setText((CharSequence) display);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout2);
        initSolution();
    }

    private void initSolution(){
        char[][] board = this.board.getBoard();
        for (int i = 0; i < MainActivity.BOARD_SIZE; i++) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < MainActivity.BOARD_SIZE; j++) {
                TextView text = new TextView(this);
                text.setWidth(MainActivity.SQUARE_SIZE);
                text.setHeight(MainActivity.SQUARE_SIZE);
                text.setBackground(getResources().getDrawable(R.drawable.shape));
                text.setPadding(0,5,0,0);
                if (board[i][j] != SudokuSolver.EMPTY)
                    text.setText((CharSequence) Character.toString(board[i][j]));
                text.setTextColor(Color.BLACK);
                text.setTextSize(MainActivity.TEXT_SIZE);
                text.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(text, j);
            }
            tableLayout.addView(row, i);
        }
    }

    public void backToMain(View v){
        Intent backToMain = new Intent();
        backToMain.setClass(this, MainActivity.class);
        startActivity(backToMain);
    }
}
