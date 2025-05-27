//Name: Brian Quach
//Date: 03/27/2024
//Description: Sprite class of Assignment 5

import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public abstract class Sprite
{
    protected int x, y, w, h;
    protected BufferedImage image;

    public Sprite(int x, int y, int w, int h, String imagePath)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        try
        {
            this.image = ImageIO.read(new File(imagePath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public abstract void update();

    public abstract void draw(Graphics g, int scrollPosY);

    public abstract Json marshal();

    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }

    public int getW() 
    {
        return w;
    }

    public int getH() 
    {
        return h;
    }

    public void setX(int x) 
    {
        this.x = x;
    }

    public void setY(int y) 
    {
        this.y = y;
    }

    public boolean contains(int x, int y) 
    {
        // Check if the point (x, y) falls within the boundaries of the wall
        return x >= this.x && x <= (this.x + this.w) && y >= this.y && y <= (this.y + this.h);
    }

    abstract boolean isPacman();
    abstract boolean isGhost();
    abstract boolean isWall();
    abstract boolean isFruit();
    abstract boolean isPellet();

    @Override 
    public String toString()
    {
        return "Sprite (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}