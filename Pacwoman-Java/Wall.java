//Name: Brian Quach
//Date: 03/27/2024
//Description: Wall class of Assignment 5

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wall extends Sprite
{
    private static BufferedImage image;

    public Wall(int x, int y, int w, int h) 
    {
        super(x, y, w, h, "wallImages/wall.png");
        loadImage();
    }

    private static void loadImage() 
    {
        try 
        {
            image = ImageIO.read(new File("wallImages/wall.png"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int scrollPosY) 
    {
        g.drawImage(image, x, y - scrollPosY, w, h, null);
    }

    public Wall(Json ob) 
    {
        super(
        (int) ob.getLong("x"),
        (int) ob.getLong("y"),
        (int) ob.getLong("w"), 
        (int) ob.getLong("h"),
        "wallImages/wall.png");
    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    @Override 
    public String toString()
    {
        return "Wall (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    public void update(){}

    boolean isPacman()
    {
        return false;
    }

    boolean isWall()
    {
        return true;
    }
    
    boolean isGhost()
    {
        return false;
    }

    boolean isFruit()
    {
        return false;
    }

    boolean isPellet()
    {
        return false;
    }
}