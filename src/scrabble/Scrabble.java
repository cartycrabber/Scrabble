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
	
	private enum Turn {
		PLAYER1,
		PLAYER2
	}
	
	State state;
	Turn turn;
	boolean playerTurn;
	private Board board;
	
	HumanPlayer player1;
	HumanPlayer player2;
	
	//URL Prefixes
	static final String imageLoc = "res/images/";
	static final String dictLoc = "res/dictionaries/";
	
	
	//GUI Elements
	private static Map<String, Image> images = new HashMap<String, Image>();
	Button exchangeButton;
	Button startGameButton;
	
	public Scrabble(String gamename)
	{
		super(gamename);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Scrabble("Scrabble"));
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
		images.put("board", new Image(imageLoc + "board.png"));//http://interactive.usc.edu/wp-content/uploads/2011/08/board.png
		images.put("exchange", new Image(imageLoc + "recycle.png"));//https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Recycle001.svg/250px-Recycle001.svg.png
		images.put("start", new Image(imageLoc + "startgame.png"));//http://img3.wikia.nocookie.net/__cb20081130214319/banjokazooie/images/5/5b/Start_button_(GBA).png
		images.put("menu", new Image(imageLoc + "menu.png"));//http://www.androidheadlines.com/wp-content/uploads/2013/11/scrabble_header.jpg
		images.put("a", new Image(imageLoc + "A.png"));//ALL OF THESE LETTERS:
		images.put("b", new Image(imageLoc + "B.png"));//http://3.bp.blogspot.com/-bi0-jBO-3_Y/TfVyhHCfW8I/AAAAAAAAAes/rMDQ2I2AECY/s1600/word%255E2_tiles_sprites_active_new.png
		images.put("c", new Image(imageLoc + "C.png"));
		images.put("d", new Image(imageLoc + "D.png"));
		images.put("e", new Image(imageLoc + "E.png"));
		images.put("f", new Image(imageLoc + "F.png"));
		images.put("g", new Image(imageLoc + "G.png"));
		images.put("h", new Image(imageLoc + "H.png"));
		images.put("i", new Image(imageLoc + "I.png"));
		images.put("j", new Image(imageLoc + "J.png"));
		images.put("k", new Image(imageLoc + "K.png"));
		images.put("l", new Image(imageLoc + "L.png"));
		images.put("m", new Image(imageLoc + "M.png"));
		images.put("n", new Image(imageLoc + "N.png"));
		images.put("o", new Image(imageLoc + "O.png"));
		images.put("p", new Image(imageLoc + "P.png"));
		images.put("q", new Image(imageLoc + "Q.png"));
		images.put("r", new Image(imageLoc + "R.png"));
		images.put("s", new Image(imageLoc + "S.png"));
		images.put("t", new Image(imageLoc + "T.png"));
		images.put("u", new Image(imageLoc + "U.png"));
		images.put("v", new Image(imageLoc + "V.png"));
		images.put("w", new Image(imageLoc + "W.png"));
		images.put("x", new Image(imageLoc + "X.png"));
		images.put("y", new Image(imageLoc + "Y.png"));
		images.put("z", new Image(imageLoc + "Z.png"));
		
		state = State.MENU;
		turn = Turn.PLAYER1;
		
		board = new Board(15,15);
		exchangeButton = new Button(1050, 300, 50, 50, images.get("exchange"));
		startGameButton = new Button(650, 480, images.get("start"));
		
		player1 = new HumanPlayer("Will", board, 600);
		player2 = new HumanPlayer("Someone else", board, 600);
//		board.placeWord("Words", 7, 7, Board.WordDirection.RIGHT);
//		board.placeWord("without", 8, 3, Board.WordDirection.DOWN);
//		board.placeWord("Friends", 11, 1, Board.WordDirection.DOWN);
//		System.out.println("Score: " + board.getScore());
	}
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		switch(state) {
		case MENU:
			images.get("menu").draw(0, 0, 1100, 600);
			startGameButton.setRender(true);
			exchangeButton.setRender(false);
			break;
		case GAME:
			images.get("board").draw(0,0);
			exchangeButton.setRender(true);
			startGameButton.setRender(false);
			board.render(arg0, arg1);
			player1.render(arg0, arg1);
			player2.render(arg0, arg1);
			arg1.drawString(player1.name + ": " + player1.getScore(), 700, 100);
			arg1.drawString(player2.name + ": " + player2.getScore(), 700, 120);
			
			switch(turn) {
			case PLAYER1:
				arg1.drawString(player1.name + "'s Turn", 800, 50);
				break;
			case PLAYER2:
				arg1.drawString(player2.name + "'s Turn", 800, 50);
				break;
			}
			break;
		}
		Button.renderAll(arg0, arg1);
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		Button.updateAll(arg0, arg1);
		
		switch(state) {
		case MENU:
			player1.setTurn(false);
			player2.setTurn(false);
			if(startGameButton.isDownEvent()) {
				state = State.GAME;
			}
			break;
		case GAME:
			switch(turn) {
			case PLAYER1:
				if(!player1.isTurn()) {
					turn = Turn.PLAYER2;
					player2.takeTurn();
				}
				else {
					if(exchangeButton.isDownEvent()) {
						System.out.println("Player 1 Exchanging");
						if(player1.exchangeTiles()){
							player1.setTurn(false);
						}
					}
				}
				break;
			case PLAYER2:
				if(!player2.isTurn()) {
					turn = Turn.PLAYER1;
					player1.takeTurn();
				}
				else {
					if(exchangeButton.isDownEvent()) {
						System.out.println("Player 2 Exchanging");
						if(player2.exchangeTiles()){
							player2.setTurn(false);
						}
					}
				}
				break;
			}
			break;
		}
	}

	
	public static Image getImage(String name)
	{
		return images.get(name);
	}
	
	public void mousePressed(int button, int x, int y) {
		player1.mousePressed(button, x, y);
		player2.mousePressed(button, x, y);
	}
	public void keyPressed(int key, char c) {
		player1.keyPressed(key, c);
		player2.keyPressed(key, c);
	}
}
