package scrabble;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Player {
//	char[] letters = new char[7];
	char[] letters = {'c','a','t','p','p','l','e'};
	boolean turn;
	String name;
	Board board;
	int score = 0;
	
	public Player(String name, Board board)
	{
		turn = false;
		this.name = name;
		this.board = board;
		
		Random r = new Random();
		for(int x = 0; x < letters.length; x++) {
	//		letters[x] = (char)(r.nextInt(26) + 'a');
		}
		System.out.println(name + "'s starting letters are: " + new String(letters));
	}
	
	public void setTurn(boolean turn)
	{
		this.turn = turn;
	}
	
	public abstract void render(GameContainer arg0, Graphics arg1);
	
	public abstract void takeTurn();
}
