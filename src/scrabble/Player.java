package scrabble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Player {
	
	protected List<Cell> tray = new ArrayList<Cell>();//Letters in the players tray
	protected boolean turn;//Whether or not its this players turn
	protected String name;//name of this player
	protected Board board;//Board that this player is playing on
	protected int score = 0;//Score of this player
	
	final int TRAY_SIZE = 7;//How many letters must be in this players tray
	
	public Player(String name, Board board)
	{
		turn = false;
		this.name = name;
		this.board = board;
		
		for(int x = 0; x < TRAY_SIZE; x++) {
			tray.add(new Cell());
		}
		tray = newLetters(tray);
		System.out.print(name + "'s starting letters are ");
		for(Cell c : tray) {
			System.out.print(c.getLetter() + " ");
		}
		System.out.println();
	}
	
	//Discard all tiles in tray and replace with new ones
	public boolean exchangeTiles() {
		if(tray.size() == TRAY_SIZE) {
			tray = newLetters(tray);
			return true;
		}
		return false;
	}
	
	//Replace all tiles in given list with new ones with different letters
	//This method makes sure not to give out any letters that have run out
	protected List<Cell> newLetters(List<Cell> oldLetters)
	{
		System.out.println("Letter Lim Size " + board.letterLimits.size());
		int emptyLetterCount = 0;
		for(Map.Entry<Character, Integer> entry : board.letterLimits.entrySet()) {
			System.out.println(entry.getValue());
			if(entry.getValue() == 0) {
				emptyLetterCount++;
				System.out.println("Out of letter " + entry.getKey());
			}
		}
		
		if(emptyLetterCount >= 20) {
			System.out.println("Game Over!");
		}
		
		Random r = new Random();
		for(int x = 0; x < oldLetters.size(); x++) {
			char l = (char)(r.nextInt(26) + 'a');
			if(board.letterLimits.get(l) > 0) {
				if(oldLetters.get(x).getLetter() != Cell.DEFAULT_CHAR){
					board.letterLimits.replace(oldLetters.get(x).getLetter(), board.letterLimits.get(oldLetters.get(x).getLetter()) + 1);
				}
				oldLetters.get(x).setLetter(l);;
				System.out.println("Replacing " + l + " with " + (board.letterLimits.get(l) - 1));
				board.letterLimits.replace(l, board.letterLimits.get(l) - 1);
			}
			else {
				System.out.println("No letters left: " + l);
				x -= 1;
			}
		}	
		return oldLetters;
		
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
