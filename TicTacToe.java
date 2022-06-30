import java.util.*;
public class TicTacToe{
	
	static void printing(Tic t){ //function for printing the TicTacToe
			System.out.println(t.getValue(0,0)+" | "+t.getValue(0,1)+" | "+t.getValue(0,2));
			System.out.println("_ _ _ _ _");
			System.out.println(" ");
			System.out.println(t.getValue(1,0)+" | "+t.getValue(1,1)+" | "+t.getValue(1,2));
			System.out.println("_ _ _ _ _");
			System.out.println(" ");
			System.out.println(t.getValue(2,0)+" | "+t.getValue(2,1)+" | "+t.getValue(2,2));
			System.out.println(" ");
			
	}
	//This function is used to check whether the player has won the game or not
	//It takes the value of the coordinates of the latest input of the player
	//This function is used in player vs player mode and computer vs player mode to check whether the player has won or not
	//The function to check whether computer won is different
	static int checkWinner(Tic t,int x, int y){ 
		if(t.getValue(x-1,0)==t.getValue(x-1,1) && t.getValue(x-1,0)==t.getValue(x-1,2))
			return 0;
		else if(t.getValue(0,y-1)==t.getValue(1,y-1) && t.getValue(0,y-1)==t.getValue(2,y-1))
			return 0;
		else if(x==y && t.getValue(0,0)==t.getValue(1,1) && t.getValue(0,0)==t.getValue(2,2))
			return 0;
		else if(x+y-2==2 && t.getValue(0,2)==t.getValue(1,1) && t.getValue(0,2)==t.getValue(2,0))
			return 0;
		else
			return 1;
	}
	//This is the main function for player vs player mode of game
	//It takes in the input of coordinates from both the players and if valid then sets the value in the object t of class Tic
	static void playerVsPlayer(Tic t){
		int x, y, i,flag=0,win;
		char c;
		Scanner scn = new Scanner(System.in);
		for(i=0;i<9;i++)
		{
			c=(i%2==0)? 'X':'O';
			flag=0;
			do{
				System.out.println(c+": Enter the x and y coordinate: ");
				x=scn.nextInt();
				y=scn.nextInt();
				System.out.println(" ");
				if(x-1<3 && x-1>-1 && y-1<3 && y-1>-1 && t.getValue(x-1,y-1)==' ')
				{
					t.setValue(x-1,y-1,c);
					flag=1;
				}
				else
					System.out.println("Invalid move");
			}while(flag==0);

			printing(t);
			win=checkWinner(t,x,y);
			if(win==0) //this means that the player has won
			{
				System.out.println("Congratulations "+c+"!");
				System.out.println("You won the game");
				return;
			}
			else if(i==8) //this means that no one one yet and all spaces in TicTacToe are filles
				System.out.println("The game has ended in a draw");		
		}
		scn.close();
	}
	//This function outputs the winning statement for the computer if and when it wins
	static void computerWins(Tic t,int x, int y,char c){
		System.out.println("The computer chose the coordinate: ");
		System.out.println((x+1)+" "+(y+1));
		System.out.println(" ");
		t.setValue(x,y,'O');
		printing(t);
		if(c=='O')
			System.out.println("You lose! Better luck next time");
	}
	//This function is regarding the move of the computer
	//The computer checks all rows, columns and diagonals to first see whether it can win
	//If not then if it can stop the player from winning
	//If it can win then it calles the computerWins function to print the winning statement, set the values and also print the TicTacToe
	//If a random move is required then the function returns 'Y' to the playerVsComputer function 
	static char computerMove(Tic t){ //returns O if computer won, X if player was stopped from winning and Y if none
		int move,i,j;
		char c;
		for(j=0;j<2;j++) //first checks whether computer can win, if not then checks if player can be stopped from winning
		{
			c=(j==0)? 'O':'X';
			for(i=0;i<3;i++)
			{
				move=t.checkRow(i,c);
				if(move!=-1)
				{
					computerWins(t,i,move,c);
					return c;
				}
			}
			for(i=0;i<3;i++)
			{
				move=t.checkColumn(i,c);
				if(move!=-1)
				{
					computerWins(t,move,i,c);
					return c;
				}
			}
			move=t.checkDiagonal1(c);
				if(move!=-1)
				{
					computerWins(t,move,move,c);
					return c;
				}
			move=t.checkDiagonal2(c);
				if(move!=-1)
				{
					computerWins(t,move,2-move,c);
					return c;
				}
		}
		return 'Y';	
	}
	//When a random move by the computer is required
	//The computer sets the first empty space as 'O'
	static void randomMove(Tic t)
	{
		int i,j;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
				if(t.getValue(i,j)==' ')
				{
					t.setValue(i,j,'O');
					System.out.println("The computer chose the coordinates: ");
					System.out.println((i+1)+" "+(j+1));
					System.out.println(" ");
					return;
				}
			}
		}
		return;
	}
	//This function contains the main code for the player vs computer mode of game
	//It inputs the coordinates from the player, calls various functions to perform all the other tasks
	static void playerVsComputer(Tic t){
		int i,x,y,win=0,flag=0;
		char c;
		char move;
		Scanner scn = new Scanner(System.in);
		System.out.println("The computer is O and you are X");
		for(i=0;i<9;i++)
		{
			flag=0;
			c=(i%2==0)? 'X':'O';
			if(i%2==0) //first move is the players
			{
				do{
					System.out.println(c+": Enter the x and y coordinate: ");
					x=scn.nextInt();
					y=scn.nextInt();
					System.out.println(" ");
					if(x-1<3 && x-1>-1 && y-1<3 && y-1>-1 && t.getValue(x-1,y-1)==' ')
					{
						t.setValue(x-1,y-1,c);
						flag=1;
					}
					else
						System.out.println("Invalid move");
				}while(flag==0);
				printing(t);
				win=checkWinner(t,x,y);
			}
			else
			{
				move=computerMove(t);
				if(move=='O') //the computer has won and the winning statement has been printed by the computerWin function
					return;
				else if(move=='Y') //a random move by the computer is required
				{
					randomMove(t);
					printing(t);
				}
			}
			if(win==0) //player won
			{
				System.out.println("Congratulations! You've won!");
				return;
			}
			else if(i==8) //no one won, its a draw
			{
				System.out.println("The game has ended in a draw");
				return;
			}
			
		}
		scn.close();
	}
	//The main function
	//Takes the mode as input
	//calls the fuction based on the mode mentioned
	//Has an object of class Tic
	public static void main(String[] args) {
		int i;
		Scanner sc = new Scanner(System.in);
		Tic t= new Tic();
		System.out.println("Welcome to TicTacToe");
		do{
			System.out.println("Press 0 for a 2-player game");
			System.out.println("Press 1 for a game vs Computer");
			i=sc.nextInt();
			if(i==0)
			{
				playerVsPlayer(t);
				return;
			}
			else if(i==1)
			{
				playerVsComputer(t);
				return;
			}
			else
				System.out.println("Invalid");
		}while(i!=0 || i!=1);
		sc.close();				
	}
}


class Tic{
	private char[][] arr=new char[3][3];
	private int i;
	private int j;
	//sets the default value of the array as " "
	public Tic(){
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
				this.arr[i][j]=' ';
		}
	}
	//to get the character stored at the coordinate a,b
	public char getValue(int a, int b){
		return this.arr[a][b];
	}
	//to set the coordinate a,b as c
	public void setValue(int a,int b, char c){
		this.arr[a][b]=c;
	}
	//to check whether any of the rows have eithe cc_ or _cc or c_c 
	public int checkRow(int i, char c){
		if(this.arr[i][0]==this.arr[i][1] && this.arr[i][0]==c && this.arr[i][2]==' ')
			return 2;
		if(this.arr[i][0]==this.arr[i][2] && this.arr[i][0]==c && this.arr[i][1]==' ')
			return 1;
		if(this.arr[i][1]==this.arr[i][2] && this.arr[i][1]==c && this.arr[i][0]==' ')
			return 0;
		else
			return -1;
	}
	//to ckech whether any column has cc_ or _cc or c_c 
	public int checkColumn(int i, char c){
		if(this.arr[0][i]==this.arr[1][i] && this.arr[0][i]==c && this.arr[2][i]==' ')
			return 2;
		if(this.arr[0][i]==this.arr[2][i] && this.arr[0][i]==c && this.arr[1][i]==' ')
			return 1;
		if(this.arr[1][i]==this.arr[2][i] && this.arr[1][i]==c && this.arr[0][i]==' ')
			return 0;
		else
			return -1;
	}
	//to check whether the diagonal from the coordinate (1,1) has c_c or cc_ or _cc
	public int checkDiagonal1(char c){
		if(this.arr[0][0]==this.arr[1][1] && this.arr[0][0]==c && this.arr[2][2]==' ')
			return 2;
		if(this.arr[0][0]==this.arr[2][2] && this.arr[0][0]==c && this.arr[1][1]==' ')
			return 1;
		if(this.arr[1][1]==this.arr[2][2] && this.arr[1][1]==c && this.arr[0][0]==' ')
			return 0;
		else
			return -1;
	}
	//to check whether the diagonal from the coordinate (1,3) has c_c or cc_ or _cc
	public int checkDiagonal2(char c){
		if(this.arr[0][2]==this.arr[1][1] && this.arr[0][2]==c && this.arr[2][0]==' ')
			return 2;
		if(this.arr[0][2]==this.arr[2][0] && this.arr[0][2]==c && this.arr[1][1]==' ')
			return 1;
		if(this.arr[1][1]==this.arr[2][0] && this.arr[1][1]==c && this.arr[0][2]==' ')
			return 0;
		else
			return -1;
	}
	
}

