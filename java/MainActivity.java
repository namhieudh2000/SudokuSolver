package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TableLayout boardLayout;
    boolean validInput;
    protected static final int BOARD_SIZE = 9;
    protected static final int SQUARE_SIZE = 50;
    protected static final int TEXT_SIZE = 20;
    private static final int MAX_LENGTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardLayout = (TableLayout) findViewById(R.id.tableLayout);
        init();
    }

    private boolean checkValidInput(int number){
        return number > 0 && number < 10;
    }

    private void init(){
        for (int i = 0; i < BOARD_SIZE; i++){
            TableRow row = new TableRow(this);
            for (int j = 0; j < BOARD_SIZE; j++){
                EditText edit = new EditText(this);
                edit.setWidth(SQUARE_SIZE);
                edit.setHeight(SQUARE_SIZE);
                edit.setBackground(getResources().getDrawable(R.drawable.shape));
                edit.setPadding(0,5,0,0);
                edit.setTextColor(Color.BLACK);
                edit.setTextSize(TEXT_SIZE);
                edit.setGravity(Gravity.CENTER_HORIZONTAL);
                edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
                edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                row.addView(edit, j);
            }
            boardLayout.addView(row, i);
        }
    }

    private char[][] convertToBoard(){
        validInput = true;
        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < boardLayout.getChildCount(); i++){
            TableRow row = (TableRow) boardLayout.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++){
                EditText view = (EditText) row.getChildAt(j);
                String input = view.getText().toString();
                if (input.length() == 0)
                    board[i][j] = SudokuSolver.EMPTY;
                else if (checkValidInput(Integer.parseInt(input))) {
                    board[i][j] = input.charAt(0);
                    view.setBackground(getResources().getDrawable(R.drawable.shape));
                }else {
                    validInput = false;
                    view.setBackground(getResources().getDrawable(R.drawable.redshape));
                }
            }
        }
        return board;
    }

    public void solve(View v){
        SudokuSolver board = new SudokuSolver(convertToBoard());
        //Log.d("board", board.printBoard());
        if (!board.isValidBoard())
            Toast.makeText(this, getString(R.string.invalidBoard), Toast.LENGTH_LONG).show();
        else if (validInput){
            boolean solvable = board.solve();
            Intent toSolution = new Intent();
            toSolution.setClass(this, Solution.class);
            toSolution.putExtra("solvable", solvable);
            toSolution.putExtra("board", board);
            startActivity(toSolution);
        }
        else
            Toast.makeText(this, (CharSequence) getString(R.string.invalidInput), Toast.LENGTH_SHORT).show();
    }
}
