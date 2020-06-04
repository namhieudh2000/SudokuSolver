package com.example.sudokusolver;// Name: Hieu Do

import java.io.Serializable;
import java.util.*;

public class SudokuSolver implements Serializable{
	public static final char EMPTY = '.';

	private boolean solvable = false;
	private boolean validBoard = true;
	private char[][] board;
	public int size = 0;
	private HashSet<String> checkValid = new HashSet<String>();

	public SudokuSolver(char[][] board) {
		this.board = board;
		size = board.length;
		populateCheckValid();
	}

	/**
	 * Solve the sudoku
	 * @return boolean value whether the problem is solvable
	 */
	public boolean solve(){
		if (validBoard) solver(0,0);
		return solvable;
	}

	public String printBoard(){
		String printmsg = "";
		for (int i = 0; i < size; i++){
			printmsg += Arrays.toString(board[i]);
			printmsg += "\n";
		}
		return printmsg;
	}

	public char[][] getBoard(){
		return board;
	}

	public int size(){
		return size;
	}

	public boolean isValidBoard(){
		return validBoard;
	}

	private void populateBoardNull(){

	}

	/**
	 * add the original number into the set
	 */
	private void populateCheckValid(){
		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++){
				char current = board[row][col];
				if (current != EMPTY){
					//the string contains information about the numbers that already exist in each
					//row, column, in square
					if (!checkValid.add(current + ": row " + row) ||
					!checkValid.add(current + ": col " + col) ||
					!checkValid.add(current + ": square " + row/3 + " " + col/3))
						validBoard = false;
				}
			}
		}
	}

	/**
	 * check whether this number satisfies the sudoku rule
	 * @param row
	 * @param col
	 * @param current the current number
	 * @return boolean value whether it satisfies the sudoku rule
	 */
	private boolean checkValidNum(int row, int col, char current){
		return      !checkValid.contains(current + ": row " + row) &&
				!checkValid.contains(current + ": col " + col) &&
				!checkValid.contains(current + ": square " + row/3 + " " + col/3);
	}

	private void add(int row, int col, char current){
		board[row][col] = current;
		checkValid.add(current + ": row " + row);
		checkValid.add(current + ": col " + col);
		checkValid.add(current + ": square " + row/3 + " " + col/3);
	}

	private void remove(int row, int col){
		char current = board[row][col];
		board[row][col] = EMPTY;
		checkValid.remove(current + ": row " + row);
		checkValid.remove(current + ": col " + col);
		checkValid.remove(current + ": square " + row/3 + " " + col/3);
	}

	/**
	 * Solver acts as the main backtracking recursive function
	 * if there is an unfilled cell, it will try to find if there exists any number from 1 to 9 that
	 * satisfies the sudoku rule. Otherwise, it backtracks to the previous empty cell and try a
	 * different number.
	 * @param row: current row position of the cell
	 * @param col: current column position of the cell
	 */
	private void solver(int row, int col){
		if (!solvable && row < size){
			if (board[row][col] != EMPTY){
				if (col == size - 1) solver(row + 1, 0);
				else solver(row, col + 1);
			}
			else{
				for (int i = 1; i < 10; i++){
					char current = (char) (i + '0');
					if (checkValidNum(row, col, current)){
						//System.out.println("Add row & col: " + row + " " + col + ": " + current);
						add(row, col, current);
						if (col == size - 1) solver(row + 1, 0);
						else solver(row, col + 1);
						//System.out.println("Remove row & col: " + row + " " + col + ": " + board[row][col]);
						if (!solvable) remove(row, col);
					}
				}
			}
		}
		if (row == size) solvable = true;
	}
}