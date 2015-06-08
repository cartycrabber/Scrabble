package scrabble;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Scrabble extends BasicGame{
	
	public enum State {
		MENU,
		GAME,
		CLOSING
	}
	static State state;
	boolean playerTurn;
	private Board board;
	
	HumanPlayer player1;
	ComputerPlayer player2;
	
	//URL Prefixes
	static final String imageLoc = "res/images/";
	static final String dictLoc = "res/dictionaries/";
	
	
	//GUI Elements
	private Map<String, Image> images = new HashMap<String, Image>();
	
	public Scrabble(String gamename)
	{
		super(gamename);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Scrabble("Words Without Friends"));
			appgc.setDisplayMode(1100, 600, false);
			appgc.setShowFPS(false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Scrabble.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {		
		//Create all images
		images.put("board", getImage("board"));
		
		state = State.GAME;
		board = new Board(15,15);
		
		player1 = new HumanPlayer("Player 1", board, 600);
		player2 = new ComputerPlayer("Player 2", board);
		player1.setTurn(true);
//		board.placeWord("Words", 7, 7, Board.WordDirection.RIGHT);
//		board.placeWord("without", 8, 3, Board.WordDirection.DOWN);
//		board.placeWord("Friends", 11, 1, Board.WordDirection.DOWN);
//		System.out.println("Score: " + board.getScore());
	}
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		switch(state) {
		case MENU:
			break;
		case GAME:
			images.get("board").draw(0,0);
			board.render(arg0, arg1);
			player1.render(arg0, arg1);
			player2.render(arg0, arg1);
			break;
		}
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
	}

	
	public static Image getImage(String name)
	{
		try {
			return new Image(imageLoc + name + ".png");
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		player1.mousePressed(button, x, y);
	}
	public void keyPressed(int key, char c) {
		player1.keyPressed(key, c);
	}
}
