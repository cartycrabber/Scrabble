package scrabble;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Button {
	
	private static List<Button> register = new ArrayList<Button>();
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Image image;
	private boolean clicked = false;
	private boolean prevClicked = false;

	public Button(int xPosition, int yPosition, Image image)
	{
		x = xPosition;
		y = yPosition;
		width = image.getWidth();
		height = image.getHeight();
		this.image = image;
		register.add(this);
	}
	public Button(int xPosition, int yPosition, int width, int height)
	{
		x = xPosition;
		y = yPosition;
		this.width = width;
		this.height = height;
		register.add(this);
	}
	public Button(int xPosition, int yPosition, int width, int height, Image image)
	{
		x = xPosition;
		y = yPosition;
		this.width = width;
		this.height = height;
		this.image = image;
		register.add(this);
	}
	private void render(GameContainer arg0, Graphics arg1)
	{
		if(image != null) {
			image.draw(x, y, width, height);
		}
	}
	private void update(GameContainer arg0, int arg1)
	{
		Input input = arg0.getInput();
		int mx = input.getMouseX();
		int my = input.getMouseY();
		if(((mx > x) && (mx < (x + width))) && ((my > y) && (my < (my + height)))) {
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				clicked = true;
			}
			else {
				clicked = false;
			}
		}
		else {
			clicked = false;
		}
	}
	
	public boolean isPressedEvent()
	{
		return clicked;
	}
	public boolean isDownEvent()
	{
		if(clicked) {
			if(!prevClicked) {
				prevClicked = true;
				return true;
			}
		}
		else {
			if(prevClicked) {
				prevClicked = false;
			}
		}
		return false;
	}
	
	public static void renderAll(GameContainer arg0, Graphics arg1)
	{
		for(Button b : register) {
			b.render(arg0, arg1);
		}
	}
	public static void updateAll(GameContainer arg0, int arg1)
	{
		for(Button b : register) {
			b.update(arg0, arg1);
		}
	}
}
