package scrabble;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;;

public class HumanPlayer extends Player{

	private List<Cell> tray;
	private List<Cell> placed;
	private Cell selected;
	private int boardWidth;
	
	public HumanPlayer(String name, Board board, int boardWidth)
	{
		super(name, board);
		tray = new ArrayList<Cell>();
		placed = new ArrayList<Cell>();
		this.boardWidth = boardWidth;
		for(int x = 0; x < letters.length; x++) {
			Cell c = new Cell(-1, -1, letters[x], false);
			c.setWindow(boardWidth + (50 * x) + (x * 10) + 50, 540);
			tray.add(x, c);;
		}
	}
	@Override
	public void render(GameContainer arg0, Graphics arg1) {
		for(int x = 0; x < tray.size(); x++) {
			Scrabble.getImage(String.valueOf(tray.get(x).getLetter()).toUpperCase()).draw(tray.get(x).getWindowX(), tray.get(x).getWindowY());
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		for(Cell c : tray) {
			if(c.checkIfWithin(x, y)) {
				System.out.println(String.valueOf(c.getLetter()) + " clicked");
				selected = c;
				break;
			}
		}
		
		if((selected != null) && (x < boardWidth)) {
			if(board.placeLetter(x, y, selected.getLetter(), true)) {;
				placed.add(selected);
				tray.remove(selected);
				selected = null;
			}
		}
	}
	
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_RETURN) {
			if(placed.size() > 2) {
				if(board.submitWord()) {
					placed.clear();
				}
			}
		}
		if(key == Input.KEY_DELETE) {
			tray.addAll(placed);
			board.removeAllTempLetters();
			placed.clear();
			System.out.println("Returning");
		}
	}
}
