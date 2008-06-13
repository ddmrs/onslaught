package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class StraightRoad {
	private Point position;
	private final int WIDTH = 25;
	private final Color BACKGROUNDCOLOR = new Color(178, 136, 91);
	private int height;
	private Direction direction; // degrees

	public StraightRoad(Point position, int height, Direction direction) {
		this.position = position;
		this.height = height;
		this.direction = direction;

	}

//	private public void draw(Graphics graphics) {
//		graphics.setColor(BACKGROUNDCOLOR);
//		switch (direction) {
//		case NORTH:
//
//			break;
//		case SOUTH:
//			break;
//		case EAST:
//			break;
//		case WEST:
//			break;
//		}
//		if (direction == Direction.EAST || direction == Direction.WEST) {
//			graphics.fillRect(position.x, position.y, WIDTH, height);
//		} else {
//			graphics.fillRect(position.x, position.y, height, WIDTH);
//		}
//	}

}
