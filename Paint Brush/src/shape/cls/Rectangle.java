package shape.cls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.*;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Rectangle extends Shape {

	public int w, h;

	public Rectangle() {

	}

	public Rectangle(int x1, int y1, int x2, int y2, boolean f, Color c) {
		setX1(Math.min(x1, x2));
		setY1(Math.min(y1, y2));
		setF(f);
		setC(c);
		w = Math.abs(x1 - x2);
		h = Math.abs(y1 - y2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getC());
		g.drawRect(getX1(), getY1(), w, h);
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getC());
		g.fillRect(getX1(), getY1(), w, h);
	}

	@Override
	public boolean inside(int _x, int _y) {
		Rectangle2D tmp = new Rectangle2D.Double(getX1(), getY1(), w, h);
		return tmp.contains(_x, _y);
	}

	@Override
	public Shape getThis() {
		return new Rectangle(getX1(), getY1(), getX1() + w, getY1() + h, isF(),
				getC());
	}

	@Override
	public void saveXML(Document doc, Element rootElement) {
		// Shape elements
		Element shape = doc.createElement("Shape");
		rootElement.appendChild(shape);

		// set attribute to shape element
		// shorten way
		String className = this.getClass().getName();
		shape.setAttribute("type", className);

		// X1 elements
		Element xx1 = doc.createElement("x1");
		xx1.appendChild(doc.createTextNode(getX1() + ""));
		shape.appendChild(xx1);

		// Y1 elements
		Element yy1 = doc.createElement("y1");
		yy1.appendChild(doc.createTextNode(getY1() + ""));
		shape.appendChild(yy1);

		// X2 elements
		Element xx2 = doc.createElement("w");
		xx2.appendChild(doc.createTextNode(w + ""));
		shape.appendChild(xx2);
		// Y2 elements
		Element yy2 = doc.createElement("h");
		yy2.appendChild(doc.createTextNode(h + ""));
		shape.appendChild(yy2);

		// Fill elements
		Element isFilled = doc.createElement("isFilled");
		if (isF()) {
			isFilled.appendChild(doc.createTextNode("yes"));
		} else {
			isFilled.appendChild(doc.createTextNode("no"));
		}
		shape.appendChild(isFilled);

		// Color elements
		Element color = doc.createElement("color");
		color.appendChild(doc.createTextNode(getC().toString()));
		shape.appendChild(color);

	}

	@Override
	public Shape loadXML(Element shape) {
		int x1 = Integer.parseInt(shape.getElementsByTagName("x1").item(0)
				.getTextContent());
		int y1 = Integer.parseInt(shape.getElementsByTagName("y1").item(0)
				.getTextContent());
		int ww = Integer.parseInt(shape.getElementsByTagName("w").item(0)
				.getTextContent());
		int hh = Integer.parseInt(shape.getElementsByTagName("h").item(0)
				.getTextContent());
		String f = shape.getElementsByTagName("isFilled").item(0)
				.getTextContent();
		String c = shape.getElementsByTagName("color").item(0).getTextContent();
		c = c.replace("java.awt.Color[", " ");
		c = c.replace("r=", " ");
		c = c.replace("g=", " ");
		c = c.replace("b=", " ");
		c = c.replace("]", " ");
		c = c.trim();
		int[] g = new int[3];
		String[] n = c.split(",");
		for (int i = 0; i < n.length; i++) {
			g[i] = Integer.parseInt(n[i].trim());
		}
		Color t = new Color(g[0], g[1], g[2]);

		boolean v = false;
		if (f.equals("yes")) {
			v = true;
		}
		return new Rectangle(x1, y1, ww + x1, hh + y1, v, t);
	}

	@Override
	public void saveJSON(JsonWriter writer) {
		try {
			writer.beginObject();
			writer.name("type").value(this.getClass().getName());
			writer.name("x1").value(getX1());
			writer.name("y1").value(getY1());
			writer.name("w").value(w);
			writer.name("h").value(h);
			writer.name("isFilled").value(isF());
			writer.name("color").value(getC().toString());
			writer.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Shape loadJSON(JsonReader reader) {
		try {
			String c = reader.nextName();
			int xx1 = reader.nextInt();
			c = reader.nextName();
			int yy1 = reader.nextInt();
			c = reader.nextName();
			int xx2 = reader.nextInt();
			c = reader.nextName();
			int yy2 = reader.nextInt();
			c = reader.nextName(); // isFilled
			boolean v = reader.nextBoolean();
			c = reader.nextName();
			c = reader.nextString(); // color
			c = c.replace("java.awt.Color[", " ");
			c = c.replace("r=", " ");
			c = c.replace("g=", " ");
			c = c.replace("b=", " ");
			c = c.replace("]", " ");
			c = c.trim();
			int[] g = new int[3];
			String[] n = c.split(",");
			for (int i = 0; i < n.length; i++) {
				g[i] = Integer.parseInt(n[i].trim());
			}
			Color t = new Color(g[0], g[1], g[2]);
			return new Rectangle(xx1, yy1, xx1 + xx2, yy1 + yy2, v, t);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void move(Graphics g, int _x, int _y) {
		g.setColor(getC());
		if (isF())
			g.drawRect(_x, _y, w, h);
		else
			g.fillRect(_x, _y, w, h);
	}

}
