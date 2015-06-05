package scrabble;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class Scrabble {
	
	public enum State {
		MENU,
		GAME,
		CLOSING
	}
	static State state;
	boolean playerTurn;
	Board board;
	
	//GUI Elements
	
	
	public Scrabble()
	{
		state = State.MENU;
		board = new Board(5,5);
		board.placeWord("teft", 0, 0, 0, 3);
		board.print();
		board.placeWord("end", 0, 2, 1, 1);
		board.print();
		System.out.println("Total Score: " + board.getScore());
	}
	
	public void start()
	{
		Display.setDisplayMode(new DisplayMode(100,100));
		
		while(state != State.CLOSING){
			render();
		}
	}
	
	private void render()
	{
		switch(state) {
		case MENU:
			break;
		case GAME:
			break;
		}
	}

	public static void main(String[] args)
	{
		Scrabble game = new Scrabble();
		game.start();
	}

}
