package shape.cls;

import java.awt.Color;
import java.awt.Graphics;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class Shape {

	private int x1, y1;
	private boolean f;
	private Color c;

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public boolean isF() {
		return f;
	}

	public void setF(boolean f) {
		this.f = f;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public abstract void draw(Graphics g);

	public abstract void fill(Graphics g);

	public abstract boolean inside(int x, int y);

	public abstract Shape getThis();

	public abstract void move(Graphics g, int _x, int _y);
	
	public abstract void saveXML(Document doc, Element rootElement);

	public abstract Shape loadXML(Element shape);

	public abstract void saveJSON(JsonWriter writer);

	public abstract Shape loadJSON(JsonReader reader);
}
