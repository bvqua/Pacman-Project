//Name: Brian Quach
//Date: 03/27/2024
//Description: View class of Assignment 5

import javax.swing.JPanel;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Font;

public class View extends JPanel 
{
    private Model model;
    public int scrollPosY = 0;
    boolean editMode = false;
    boolean toggleWall = true;
    boolean toggleGhost = false;
    boolean toggleFruit = false;
    boolean togglePellet = false;
    boolean toggleRemove = false;

    public View(Controller c, Model m) 
	{
        // Calls setter method from controller
        c.setView(this);
        this.model = m;

        // Loads wall image into memory
        try 
		{
            ImageIO.read(new File("wallimages/wall.png"));
        } 
		catch(Exception e) 
		{
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public void paintComponent(Graphics g) 
	{
        int viewCenterY = getHeight() / 2;
        int pacmanY = model.getPacman().getY();
        int cameraY = pacmanY - viewCenterY;

        if (cameraY < 0) 
		{
            cameraY = 0;
        } 
		else if (cameraY > Game.worldH - Game.viewH) 
		{
            cameraY = Game.worldH - Game.viewH;
        }
        scrollPosY = cameraY;

        g.setColor(new Color(25, 25, 50));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int i = 0; i < model.sprites.size(); i++)
        {
            model.sprites.get(i).draw(g, scrollPosY);
        }

        g.setColor(new Color(255, 0, 255));
        g.setFont(new Font("Default", Font.BOLD, 20));

        if (editMode == true) 
		{
            if (toggleWall)
            {
                g.drawString("Edit mode: add walls", 0, 20);
            }
            else if (toggleRemove)
            {
                g.drawString("Edit mode: remove", 0, 20);
            }
            else if (toggleGhost)
            {
                g.drawString("Edit mode: add ghost", 0, 20);
            }
            else if (toggleFruit)
            {
                g.drawString("Edit mode: add fruit", 0, 20);
            }
            else if (togglePellet)
            {
                g.drawString("Edit mode: add pellet", 0, 20);
            }
            else
            {
                g.drawString("Edit mode: add walls", 0, 20);
            }
        }
		else 
		{
            g.drawString("Edit mode: off", 0, 20);
        }
        repaint();
    }

    public void updateScrollPosY(int posY) 
	{
        scrollPosY += posY;
        repaint();
    }

    public int getScrollPosY() 
	{
        return scrollPosY;
    }
}