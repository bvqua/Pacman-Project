#!/bin/bash
set -u -e
javac Game.java View.java Controller.java Model.java Wall.java Ghost.java Fruit.java Pellet.java Sprite.java Json.java
java Game