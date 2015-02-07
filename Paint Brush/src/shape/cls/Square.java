package shape.cls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Square extends Rectangle {
	public Square(){
		
	}
	
	public Square(int x1, int y1, int x2, int y2, boolean f, Color c) {
		super(x1, y1, x2, y2, f, c);
	}

	@Override
	public 	void draw(Graphics g) {
		g.setColor(getC());
		g.drawRect(getX1(), getY1(), w, w);
	}

	@Override
	public 	void fill(Graphics g) {
		g.setColor(getC());
		g.fillRect(getX1(), getY1(), w, w);
	}

	@Override
	public	boolean inside(int _x, int _y) {
		Rectangle2D tmp = new Rectangle2D.Double(getX1(), getY1(), w, w);
		return tmp.contains(_x, _y);
	}

	@Override
	public Shape getThis() {
		return new Square(getX1(), getY1(), getX1() + w, getY1() + w, isF(),
				getC());
	}

	@Override
	public void move(Graphics g, int _x, int _y) {
		if (isF()) {
			g.setColor(getC());
			g.drawRect(_x, _y, w, w);
		} else {
			g.setColor(getC());
			g.fillRect(_x, _y, w, w);
		}
	}

}
