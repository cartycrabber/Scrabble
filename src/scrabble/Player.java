package scrabble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Player {
//	char[] letters = new char[7];
	List<Cell> tray = new ArrayList<Cell>();
	boolean turn;
	String name;
	Board board;
	int score = 0;
	
	final int TRAY_SIZE = 7;
	
	public Player(String name, Board board)
	{
		turn = false;
		this.name = name;
		this.board = board;
		
		Random r = new Random();
		for(int x = 0; x < TRAY_SIZE; x++) {
			Cell c = new Cell(-1, -1, (char)(r.nextInt(26) + 'a'), false);
			tray.add(x, c);
		}
		System.out.print(name + "'s starting letters are ");
		for(Cell c : tray) {
			System.out.print(c.getLetter() + " ");
		}
		System.out.println();
	}
	
	public void exchangeTiles() {
		if(tray.size() == TRAY_SIZE) {
			Random r = new Random();
			for(Cell c : tray) {
				c.setLetter((char)(r.nextInt(26) + 'a'));
			}
		}
	}
	
	public boolean isTurn() {
		return turn;
	}
	public void setTurn(boolean set) {
		turn = set;
	}
	
	public int getScore() {
		return score;
	}
	
	public abstract void render(GameContainer arg0, Graphics arg1);
	
	public abstract void takeTurn();
}
