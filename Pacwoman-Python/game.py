# Name: Brian Quach
# Description: Porting A5 to Python
# Date: 05/02/2024

import pygame
import time
import json

from pygame.locals import *
from time import sleep


class Sprite():
    def __init__(self, x, y, w, h, image):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.image = pygame.image.load(image)

    def update(self):
        pass

    def isPacman(self):
        return False

    def isWall(self):
        return False

    def isGhost(self):
        return False

    def isFruit(self):
        return False

    def isPellet(self):
        return False


class Pacman(Sprite):
    def __init__(self, x, y):
        super().__init__(x, y, 50, 50, "pacmanImages/pacman1.png")
        self.direction = 0
        # Lists of images for each direction
        self.images_left = [f"pacmanImages/pacman{i}.png" for i in range(1, 4)]
        self.images_up = [f"pacmanImages/pacman{i}.png" for i in range(4, 7)]
        self.images_right = [f"pacmanImages/pacman{i}.png" for i in range(7, 10)]
        self.images_down = [f"pacmanImages/pacman{i}.png" for i in range(10, 13)]
        self.animation_index = 0

    def animate(self):
        # Update image based on direction
        if self.direction == 0:  # Left
            self.image = pygame.image.load(self.images_left[self.animation_index])
        elif self.direction == 1:  # Up
            self.image = pygame.image.load(self.images_up[self.animation_index])
        elif self.direction == 2:  # Right
            self.image = pygame.image.load(self.images_right[self.animation_index])
        elif self.direction == 3:  # Down
            self.image = pygame.image.load(self.images_down[self.animation_index])

        # Increment animation index to cycle through images
        self.animation_index = (self.animation_index + 1) % 3  # 3 is the number of images for each direction

    def move(self, x, y):
        self.x += x
        self.y += y

        if self.x < 0:
            self.x = 500
        elif self.x > 500:
            self.x = 0

    def isPacman(self):
        return True

    def isWall(self):
        return False

    def isGhost(self):
        return False

    def isFruit(self):
        return False

    def isPellet(self):
        return False


class Wall(Sprite):
    def __init__(self, x, y, w, h):
        super().__init__(x, y, w, h, "wall.png")

    def isPacman(self):
        return False

    def isWall(self):
        return True

    def isGhost(self):
        return False

    def isFruit(self):
        return False

    def isPellet(self):
        return False


class Ghost(Sprite):
    def __init__(self, x, y):
        super().__init__(x, y, 50, 50, "pacmanImages/blinky1.png")
        self.isBlue = False
        self.isEyes = False
        self.isClear = False
        self.counter = 0

    def update(self):
        if self.isBlue:
            self.image = pygame.image.load("pacmanImages/ghost1.png")
            self.counter += 1
            if self.counter >= 20:
                self.isBlue = False
                self.isEyes = True
                self.isClear = False
        elif self.isEyes:
            self.image = pygame.image.load("pacmanImages/ghost5.png")
            self.counter += 1
            if self.counter >= 40:
                self.isBlue = False
                self.isEyes = False
                self.isClear = True

    def isPacman(self):
        return False

    def isWall(self):
        return False

    def isGhost(self):
        return True

    def isFruit(self):
        return False

    def isPellet(self):
        return False


class Fruit(Sprite):
    def __init__(self, x, y):
        super().__init__(x, y, 50, 50, "pacmanImages/fruit2.png")
        self.speed = 5

    def update(self):
        self.y += self.speed

    def isPacman(self):
        return False

    def isWall(self):
        return False

    def isGhost(self):
        return False

    def isFruit(self):
        return True

    def isPellet(self):
        return False


class Pellet(Sprite):
    def __init__(self, x, y):
        super().__init__(x, y, 10, 10, "pacmanImages/pellet.png")

    def isPacman(self):
        return False

    def isWall(self):
        return False

    def isGhost(self):
        return False

    def isFruit(self):
        return False

    def isPellet(self):
        return True


class Model():
    def __init__(self):
        self.sprites = []
        self.pacman = Pacman(100, 100)
        self.sprites.append(self.pacman)

        # Open the json map and pull out the individual lists of sprite objects
        with open("map.json") as file:
            data = json.load(file)
            # Get the list labeled as "lettuces" from the map.json file
            walls = data["walls"]
            ghosts = data["ghosts"]
            fruits = data["fruits"]
            pellets = data["pellets"]
        file.close()

        # For each entry inside the lettuces list, pull the key:value pair out and create
        # a new Lettuce object with (x,y,w,h)
        for entry in walls:
            self.sprites.append(Wall(entry["x"], entry["y"], entry["w"], entry["h"]))
        for entry in ghosts:
            self.sprites.append(Ghost(entry["x"], entry["y"]))
        for entry in fruits:
            self.sprites.append(Fruit(entry["x"], entry["y"]))
        for entry in pellets:
            self.sprites.append(Pellet(entry["x"], entry["y"]))

    def update(self):
        for i in self.sprites:
            i.update()

            # Pacman collision
            if i.isPacman():
                for j in self.sprites:
                    # Pacman x Wall
                    if j.isWall():
                        if self.collision(i, j) == True:
                            self.getOutOfSprite(i, j)
                            # print("Collision")
                    # Pacman x Ghost
                    if j.isGhost():
                        if self.collision(i, j) == True:
                            j.isBlue = True
                    # Pacman x Fruit
                    if j.isFruit():
                        if self.collision(i, j) == True:
                            self.sprites.remove(j)
                    # Pacman x Pellet
                    if j.isPellet():
                        if self.collision(i, j) == True:
                            self.sprites.remove(j)
            # Fruit collision
            if i.isFruit():
                for j in self.sprites:
                    # Fruit x wall
                    if j.isWall():
                        if self.collision(i, j):
                            i.speed = -i.speed
        i = 0
        while i < len(self.sprites):
            if isinstance(self.sprites[i], Ghost) and self.sprites[i].isClear:
                del self.sprites[i]
                i -= 1
            i += 1

        for sprite in self.sprites:
            sprite.update()

    def collision(self, s, s2):
        if s.x + 50 < s2.x:
            return False
        if s.x > s2.x + s2.w:
            return False
        if s.y + 50 < s2.y:
            return False
        if s.y > s2.y + s2.h:
            return False
        return True

    def getOutOfSprite(self, s, s2):
        dx = (s.x + s.w / 2) - (s2.x + s2.w / 2)
        dy = (s.y + s.h / 2) - (s2.y + s2.h / 2)
        hw = (s.w + s2.w) / 2
        hh = (s.h + s2.h) / 2

        if abs(dx) < hw and abs(dy) < hh:
            overlap_x = hw - abs(dx)
            overlap_y = hh - abs(dy)

            if overlap_x >= overlap_y:
                if dy > 0:
                    s.y += overlap_y
                else:
                    s.y -= overlap_y
            else:
                if dx > 0:
                    s.x += overlap_x
                else:
                    s.x -= overlap_x

    def movePacman(self, x, y):
        self.pacman.move(x, y)


class View:
    def __init__(self, model):
        screen_size = (500, 500)
        self.screen = pygame.display.set_mode(screen_size, 32)
        self.model = model
        self.scroll_pos_y = 0

    def update(self):
        self.screen.fill([25, 25, 50])
        for sprite in self.model.sprites:
            location = (sprite.x, sprite.y - self.scroll_pos_y)
            size = (sprite.w, sprite.h)
            self.screen.blit(pygame.transform.scale(sprite.image, size), location)
        pygame.display.flip()

    def move_cam(self):
        pacman_y = self.model.pacman.y
        self.scroll_pos_y = max(0, pacman_y - 225)


class Controller():
    def __init__(self, model, view):
        self.model = model
        self.view = view
        self.keep_going = True
        self.edit_mode = False
        self.add_ghosts = False
        self.add_pellets = False
        self.add_fruit = False

    def update(self):
        for event in pygame.event.get():
            if event.type == QUIT:
                self.keep_going = False
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE or event.key == K_q:
                    self.keep_going = False
                elif event.key == K_e:
                    self.edit_mode = not self.edit_mode
                    self.add_ghosts = False
                    self.add_pellets = False
                    self.add_fruit = False
                elif event.key == K_l:
                    self.load_map("map.json")
            elif event.type == pygame.MOUSEBUTTONUP:
                if self.edit_mode:
                    if self.add_ghosts:
                        adjusted_x = event.pos[0]
                        adjusted_y = event.pos[1] + self.view.scroll_pos_y  # Adjust for vertical scroll
                        self.model.sprites.append(Ghost(adjusted_x, adjusted_y))
                    elif self.add_pellets:
                        adjusted_x = event.pos[0]
                        adjusted_y = event.pos[1] + self.view.scroll_pos_y  # Adjust for vertical scroll
                        self.model.sprites.append(Pellet(adjusted_x, adjusted_y))
                    elif self.add_fruit:
                        adjusted_x = event.pos[0]
                        adjusted_y = event.pos[1] + self.view.scroll_pos_y  # Adjust for vertical scroll
                        self.model.sprites.append(Fruit(adjusted_x, adjusted_y))
            elif event.type == pygame.KEYUP:
                pass

        keys = pygame.key.get_pressed()
        if keys[K_LEFT]:
            self.model.movePacman(-10, 0)
            self.model.pacman.direction = 0
            self.model.pacman.animate()
        if keys[K_RIGHT]:
            self.model.movePacman(10, 0)
            self.model.pacman.direction = 2
            self.model.pacman.animate()
        if keys[K_UP]:
            self.model.movePacman(0, -10)
            self.model.pacman.direction = 1
            self.model.pacman.animate()
        if keys[K_DOWN]:
            self.model.movePacman(0, 10)
            self.model.pacman.direction = 3
            self.model.pacman.animate()

        if self.edit_mode:
            if keys[K_g]:
                self.add_ghosts = True
                self.add_pellets = False
                self.add_fruit = False
            elif keys[K_p]:
                self.add_pellets = True
                self.add_ghosts = False
                self.add_fruit = False
            elif keys[K_f]:
                self.add_fruit = True
                self.add_ghosts = False
                self.add_pellets = False

    def load_map(self, filename):
        self.model.sprites = [sprite for sprite in self.model.sprites if isinstance(sprite, Pacman)]  # Keep only Pac-Man
        with open(filename) as file:
            data = json.load(file)
            walls = data["walls"]
            ghosts = data["ghosts"]
            fruits = data["fruits"]
            pellets = data["pellets"]
        file.close()

        for entry in walls:
            self.model.sprites.append(Wall(entry["x"], entry["y"], entry["w"], entry["h"]))
        for entry in ghosts:
            self.model.sprites.append(Ghost(entry["x"], entry["y"]))
        for entry in fruits:
            self.model.sprites.append(Fruit(entry["x"], entry["y"]))
        for entry in pellets:
            self.model.sprites.append(Pellet(entry["x"], entry["y"]))


print("Use the arrow keys to move. Press 'e' to toggle edit mode. Press 'q' or 'esc' to quit.")

pygame.init()
m = Model()
v = View(m)
c = Controller(m, v)
while c.keep_going:
    c.update()
    m.update()
    v.update()
    v.move_cam()
    sleep(0.04)
print("Goodbye")
