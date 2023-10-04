/* This program is meant to solve and display the solution for a 
 * series of different sudoku boards! The program takes in a 9x9 board
 * and solves for the missing value then displays the row, column, then 
 * the missing value!
 * 
 * Date: 3/9/23
 * Class: EECS 1015 Object Oriented Programming
 * Professor: Dr. Joseph Hobbs
 * Name: Paulo Nadal
 * 
 */
import java.util.Scanner;
public class Sudoku 
{
	static int[][] board = new int[9][9]; // 9x9 board
	static int zeroes = 0; // initializing zeroes to 0
	static int row = 0, col = 0; // initializing row and col to 0
	static Scanner input = new Scanner(System.in); // scanner

	public static void main(String[] args)
	{	
		do
		{
			readBoard(); // calls the readBoard method
			if (zeroes <= 3) // if zeroes are less than or equal to 3, call solveBoard
				solveBoard();
		}
		while (zeroes < 81); // if there are 81 zeroes, print end
		{
			System.out.print("END");
		}	
	}
	
	static void readBoard() // where we read in the boards and count the zeroes
	{
		zeroes = 0;
		for (row = 0; row < 9; row++) // iterate thru each row
			for (col = 0; col < 9; col++) // iterate thru each col
			{
				board[row][col] = input.nextInt(); // takes the keyboard input and stores it in the 9x9
				// count up the number of zeroes
				if (board[row][col] == 0) // checks if board[row][col] is equal to zero
					zeroes++;			  // if it is equal to zero, zeroes increments
			}
	}
	
	public static void solveBoard()
	{			
			while (zeroes > 0)
			{
			// MAIN LOOP //	
				if (zeroes == 1) // its da single zero
				{
					for (row = 0; row < 9; row++) // iterate thru rows
						for (col = 0; col < 9; col++) // iterate thru columns
							if (board[row][col] == 0) // checks if its zero
							{
								board[row][col] = findMissing(board[row]); // sends that particular row to findMissing
								// System.out.print("Type 1 Code");
								System.out.println("("+ row + "," + col + "," + board[row][col] + ") "); // prints out row, col, and value
							}
					return;
				}	
			
				if (zeroes == 2) // side-by-side or stacked
					{
						for (row = 0; row < 9; row++) // iterate thru rows
							for (col = 0; col < 9; col++) // iterate thru cols
								if (board[row][col] == 0) // found da first zero
								{	
									if (col != 8) // cannot exceed past eighth index
									{
										if (board[row][col + 1] == 0) // checks if there is an adjacent zero
										{
										int[] column = new int[]{board[0][col], board[1][col], 
												board[2][col], board[3][col], board[4][col], 
												board[5][col], board[6][col], board[7][col], 
												board[8][col]};	
										
										board[row][col] = findMissing(column); // send the above array to findMissing to get value
										System.out.print("(" + row + "," + col + "," + board[row][col]+ ") ");
										
										int[] newCol = new int[]{board[0][col + 1], board[1][col + 1],  // next column over 
												board[2][col + 1], board[3][col + 1], board[4][col + 1], 
												board[5][col + 1], board[6][col + 1], board[7][col + 1], 
												board[8][col + 1]};
										
										board[row][col] = findMissing(newCol); // send the above array to findMissing to get value
										System.out.println("(" + row + "," + (col + 1) + "," + board[row][col]+ ") ");
										}
									}
									if (row != 8) // cannot exceed past eighth index
									{
										if (board[row + 1][col] == 0) // checks if there is an adjacent zero
										{
										int[] roW = new int[] {board[row][0], board[row][1], board[row][2],
																board[row][3], board[row][4], board[row][5],
																board[row][6], board[row][7], board[row][8]};
												
										board[row][col] = findMissing(roW); // send the above array to findMissing to get value
										System.out.print("(" + row  + "," + col + "," + board[row][col]+ ") ");
										
										int[] newRow = new int[]{board[row + 1][0], board[row + 1][1], board[row + 1][2], // column below
												board[row + 1][3], board[row + 1][4], board[row + 1][5],
												board[row + 1][6], board[row + 1][7], board[row + 1][8]};
										
										board[row][col] = findMissing(newRow); // send the above array to findMissing to get value
										System.out.println("(" + (row + 1) + "," + col + "," + board[row][col]+ ")");
										}	
									}
								}
						return;
					}
				
				if (zeroes == 3) // one of three zeroes is a "loner" zero. 
				{
					int[][] threeByCounts = new int[9][3]; // makes a 9x3 array
					int whichBox = 0;	// variable to indicate which box we're in
					for (int r = 0; r < 9; r++) // iterate thru rows
						for (int c = 0; c < 9; c++) // iterate thru cols
							if (board[r][c] == 0) // checks if board[r][c] is zero
							{
								whichBox = (r/3) * 3 + (c/3); 	// allows us to find which box
								threeByCounts[whichBox][0]++;	// increases if there is a zero
								threeByCounts[whichBox][1] = r; // gives back the row
								threeByCounts[whichBox][2] = c; // gives back the col
							}
					
					for (int i = 0; i < 9; i++) // goes through each row of threeByCounts
					{
						if (threeByCounts[i][0] == 1) // iterates through the rows and checks if the first column = 1
						{
							row = threeByCounts[i][1]; // assigns the # to row
							col = threeByCounts[i][2]; // assigns the # to col
						}
					}
					
					int startRow = (row/3)*3; // the [0][0] position of each cell, hence startCol
					int startCol = (col/3)*3; // the [0][0] position of each cell, hence startCol 
					
					int[] val = new int[] { board[startRow + 0][startCol], board[startRow + 0][startCol + 1], board[startRow + 0][startCol + 2],
											board[startRow + 1][startCol], board[startRow + 1][startCol + 1], board[startRow + 1][startCol + 2],
											board[startRow + 2][startCol], board[startRow + 2][startCol + 1], board[startRow + 2][startCol + 2]			
										  };
					// the above array is the entire cell, starting from the startRow and startCol postion
											
					int missing = findMissing(val); // finds the missing value of the 3x3 cell
					board[row][col] = missing; // replaces the zero with the missing value
					System.out.print("(" + row + "," + col + "," + missing + ") "); // prints out row, col, and missing value
					zeroes = 2; // sets zeroes = 2, which calls the if (zeroes == 2) then solves the other two zeroes
				}	
			}
		}
	
	static int findMissing(int[] A) // method to find the missing value
	{	
		boolean[] found = {false, false, false, false, false, false, false, false, false, false}; 
		{
		for (int i = 0; i < 9; i++) // goes thru the found array
		{
			found[A[i]] = true;	// if a number exists at that position, found[A[i]] = true;
		}
		
		for (int i = 1; i < 10; i++) // checks numbers 1-10
		{
			if (!found[i]) // if a # 1-9 is not found, the missing number is returned
				return i;
		}
		}
		return row;
	}
}