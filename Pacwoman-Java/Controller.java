//Name: Brian Quach
//Date: 03/27/2024
//Description: Controller class of Assignment 5

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements ActionListener, MouseListener, KeyListener
{
	//class member variable to reference View
	private Model model;
	private View view;
	private int startX, startY;

	private boolean editMode = false;
	private boolean toggleWall = true;
	private boolean toggleGhost = false;
	private boolean toggleFruit = false;
	private boolean togglePellet = false;
	private boolean toggleRemove = false;

	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;

	public Controller(Model m)
	{
		this.model = m;
	}

	//Method that lets caller set object that view references
	void setView(View v)
	{
		this.view = v;
	}

	public void actionPerformed(ActionEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		startX = e.getX();
		startY = e.getY() + view.getScrollPosY();
	}

	public void mouseReleased(MouseEvent e) 
	{
		// Height and width of wall
		int width = Math.abs(e.getX() - startX);
		int height = Math.abs(e.getY() + view.getScrollPosY() - startY);

		// Upper corners of wall
		int upperLeftX = Math.min(startX, e.getX());
		int upperLeftY = Math.min(startY, e.getY() + view.getScrollPosY());

		// Adding/removing
		if ((toggleWall == true) && (editMode))
		{
			model.addWall(upperLeftX, upperLeftY, width, height);
		}
		else if (toggleRemove == true && (editMode))
		{
			model.removeSprite(upperLeftX, upperLeftY);
		}
		else if ((toggleGhost == true) && (editMode))
		{
			model.addGhost(e.getX(), e.getY() + view.scrollPosY, 50, 50);
		}
		else if ((toggleFruit == true) && (editMode))
		{
			model.addFruit(e.getX(), e.getY() + view.scrollPosY, 50, 50);
		}
		else if ((togglePellet == true) && (editMode))
		{
			model.addPellet(e.getX(), e.getY() + view.scrollPosY, 10, 10);
		}
		view.repaint();
	}
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	public void keyPressed(KeyEvent e)
	{
		if (editMode)
		{
			int scrollSpeed = 20;
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP: view.updateScrollPosY(-scrollSpeed); break;
				case KeyEvent.VK_DOWN: view.updateScrollPosY(scrollSpeed); break;
			}
		}

		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: 
				keyUp = true;
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = true;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = true;
				break;
			case KeyEvent.VK_RIGHT: 
				keyRight = true;
				break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		char c = Character.toLowerCase(e.getKeyChar());
		if (c == 'q')
		{
			System.exit(0);
		}
		else if (c == 'c')
		{
			model.clearWalls();
		}
		else if (c == 'e')
		{
			editMode = !editMode;
			view.editMode = !view.editMode;
		}

		if (editMode)
		{
			// Adding walls
			if ((c == 'a') && (toggleWall == false))
			{
				// System.out.print("Currently in add mode");
				toggleWall = true;
				toggleGhost = false;
				toggleFruit = false;
				togglePellet = false;
				toggleRemove = false;
				view.toggleWall = true;
				view.toggleGhost = false;
				view.toggleFruit = false;
				view.togglePellet = false;
				view.toggleRemove = false;
			}
			// Removing walls
			else if ((c == 'r') && (toggleRemove == false))
			{
				// System.out.print("Currently in remove mode");
				toggleWall = false;
				toggleGhost = false;
				toggleFruit = false;
				togglePellet = false;
				toggleRemove = true;
				view.toggleWall = false;
				view.toggleGhost = false;
				view.toggleFruit = false;
				view.togglePellet = false;
				view.toggleRemove = true;
			}
			// Adding ghosts
			else if ((c == 'g') && (toggleGhost == false))
			{
				toggleWall = false;
				toggleGhost = true;
				toggleFruit = false;
				togglePellet = false;
				toggleRemove = false;
				view.toggleWall = false;
				view.toggleGhost = true;
				view.toggleFruit = false;
				view.togglePellet = false;
				view.toggleRemove = false;
			}
			// Adding fruits
			else if ((c == 'f') && (toggleFruit == false))
			{
				toggleWall = false;
				toggleGhost = false;
				toggleFruit = true;
				togglePellet = false;
				toggleRemove = false;
				view.toggleWall = false;
				view.toggleGhost = false;
				view.toggleFruit = true;
				view.togglePellet = false;
				view.toggleRemove = false;
			}
			// Adding pellets
			else if ((c == 'p') && (togglePellet == false))
			{
				toggleWall = false;
				toggleGhost = false;
				toggleFruit = false;
				togglePellet = true;
				toggleRemove = false;
				view.toggleWall = false;
				view.toggleGhost = false;
				view.toggleFruit = false;
				view.togglePellet = true;
				view.toggleRemove = false;
			} 
		}
		else if ((c == 'e') && (editMode == false))
		{
			editMode = false;
			// System.out.print("Out of edit mode");
		}

		if (c == 's')
		{
			Json modelJson = model.marshal();
			modelJson.save("map.json");
		}
		else if (c == 'l')
		{
			Json modelJson = Json.load("map.json");
			model.unmarshal(modelJson);
			view.repaint();
		}

		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: 
				keyUp = false;
				model.getPacman().currentImage = 0;
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = false;
				model.getPacman().currentImage = 0;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = false;
				model.getPacman().currentImage = 0;
				break;
			case KeyEvent.VK_RIGHT: 
				keyRight = false;
				model.getPacman().currentImage = 0;
				break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void update()
	{
		model.pacman.prevX = model.pacman.x;
		model.pacman.prevY = model.pacman.y;

		if(keyUp)
		{
			model.pacman.y -= 10;
			model.pacman.direction = 1;
			model.pacman.currentImage++;
		}
		if(keyDown)
		{
			model.pacman.y += 10;
			model.pacman.direction = 3;
			model.pacman.currentImage++;
		}
		if(keyLeft)
		{
			model.pacman.x -= 10;
			model.pacman.direction = 0;
			model.pacman.currentImage++;
		}
		if(keyRight)
		{
			model.pacman.x += 10;
			model.pacman.direction = 2;
			model.pacman.currentImage++;
		}
	}
}