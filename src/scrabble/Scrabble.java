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
			appgc.setDisplayMode(600, 600, false);
			appgc.setShowFPS(true);
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
		board.placeWord("Words", 7, 7, Board.WordDirection.RIGHT);
		board.placeWord("without", 8, 3, Board.WordDirection.DOWN);
		board.placeWord("Friends", 11, 1, Board.WordDirection.DOWN);
		System.out.println("Score: " + board.getScore());
	}
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		switch(state) {
		case MENU:
			break;
		case GAME:
			images.get("board").draw(0,0);
			board.render(arg0, arg1);
			break;
		}
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		
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
}
