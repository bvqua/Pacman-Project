//Name: Brian Quach
//Date: 03/27/2024
//Description: Model class of Assignment 5

import java.util.ArrayList;
import java.util.Iterator;

public class Model
{
	public ArrayList<Sprite> sprites;
	public Pacman pacman;

	public Model()
	{
		sprites = new ArrayList<Sprite>();
		pacman = new Pacman(100, 100, 50, 50);
        sprites.add(pacman);
	}

	public ArrayList<Sprite> getSprites() 
	{
		return sprites;
	}

	public Pacman getPacman() 
	{
        return pacman;
    }

	public void addWall(int x, int y, int w, int h) 
	{
        sprites.add(new Wall(x, y, w, h));
    }

	public void addGhost(int x, int y, int w, int h)
	{
		sprites.add(new Ghost(x, y, w, h));
	}

	public void addFruit(int x, int y, int w, int h)
	{
		sprites.add(new Fruit(x, y, w, h));
	}
	public void addPellet(int x, int y, int w, int h)
	{
		sprites.add(new Pellet(x, y, w, h));
	}

	public void removeSprite(int x, int y) 
	{
		//System.out.print("Remove success!");
        for (int i = 0; i < sprites.size(); i++) 
		{
            Sprite sprite = sprites.get(i);
            if (sprite.contains(x, y)) 
			{
                sprites.remove(i);
                return;
            }
        }
    }

    public void clearWalls() 
	{
        sprites.clear();
    }

	public void update()
	{
        if (pacman.x <= 0) 
		{
            pacman.x = Game.worldW;
        } 
		else if (pacman.x >= Game.worldW) 
		{
            pacman.x = 0;
        }

        if (pacman.y <= 0) 
		{
            pacman.y = Game.worldH - 1;
        } 
		else if (pacman.y >= Game.worldH) 
		{
            pacman.y = 1;
        }

		//Iterator
		Iterator<Sprite> spriteIterator = sprites.iterator();
    	while (spriteIterator.hasNext()) 
		{
			Sprite sprite = spriteIterator.next();
			if (sprite instanceof Wall || sprite instanceof Ghost || sprite instanceof Fruit || sprite instanceof Pellet) 
			{
				// Collision with pacman
				if (collision(pacman, sprite)) 
				{
					if (sprite instanceof Wall) 
					{
						pacman.x = pacman.prevX;
						pacman.y = pacman.prevY;
					} 
					else if (sprite instanceof Ghost)
					{
						Ghost ghost = (Ghost) sprite;
						ghost.isBlue = true;
						if (ghost.clear)
						{
							spriteIterator.remove();
						}
					}
					else if (sprite instanceof Fruit)
					{
						spriteIterator.remove();
					}

					else if (sprite instanceof Pellet)
					{
						spriteIterator.remove();
					}
				}
				if (sprite instanceof Fruit) 
				{
					Fruit fruit = (Fruit) sprite;
					for (Sprite sprite2 : sprites) 
					{
						if (sprite2 instanceof Wall && collision(fruit, sprite2)) 
						{
							fruit.speed = -fruit.speed;
							break;
						}
					}
				}
			}
		}
		for (Sprite sprite : sprites) 
		{
			sprite.update();
		}
	}

	Json marshal()
	{
		Json ob = Json.newObject();

		Json tmpListW = Json.newList();
		ob.add("walls", tmpListW);

		Json tmpListG = Json.newList();
		ob.add("ghosts", tmpListG);

		Json tmpListF = Json.newList();
		ob.add("fruits", tmpListF);

		Json tmpListP = Json.newList();
		ob.add("pellets", tmpListP);

		for(int i = 0; i < sprites.size(); i++)
		{
			if (sprites.get(i).isWall())
			{
				tmpListW.add(sprites.get(i).marshal());
			}
			if (sprites.get(i).isGhost())
			{
				tmpListG.add(sprites.get(i).marshal());
			}
			if (sprites.get(i).isFruit())
			{
				tmpListF.add(sprites.get(i).marshal());
			}
			if (sprites.get(i).isPellet())
			{
				tmpListP.add(sprites.get(i).marshal());
			}
		}
		return ob;
	}

	public void unmarshal(Json ob)
    {
        sprites.clear();
		
		sprites = new ArrayList<Sprite>();
		sprites.add(pacman);

    	Json tmpListW = ob.get("walls");
    	for (int i = 0; i < tmpListW.size(); i++) 
		{
        	sprites.add(new Wall(tmpListW.get(i)));
    	}

		Json tmpListG = ob.get("ghosts");
		for (int i = 0; i< tmpListG.size(); i++)
		{
			sprites.add(new Ghost(tmpListG.get(i)));
		}

		Json tmpListF = ob.get("fruits");
    	for (int i = 0; i < tmpListF.size(); i++) 
		{
        	sprites.add(new Fruit(tmpListF.get(i)));
    	}

		Json tmpListP = ob.get("pellets");
		for (int i = 0; i< tmpListP.size(); i++)
		{
			sprites.add(new Pellet(tmpListP.get(i)));
		}
    }

	public boolean collision(Sprite s, Sprite s2)
	{
		// Sprite 1
		int sRight = s.x + s.w;
		int sLeft = s.x;
		int sBottom = s.y + s.h;
		int sTop = s.y;

		// Sprite 2
		int s2Left = s2.x;
		int s2Right = s2.x + s2.w;
		int s2Bottom = s2.y + s2.h;
		int s2Top = s2.y;

		if (sRight < s2Left)
		{
			return false;
		}
		if (sLeft > s2Right)
		{
			return false;
		}
		if (sBottom < s2Top)
		{
			return false;
		}
		if (sTop > s2Bottom)
		{
			return false;
		}
		return true;
    }
}