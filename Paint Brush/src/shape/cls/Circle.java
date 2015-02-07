package shape.cls;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Circle extends Shape {

	private int r;

	public Circle() {

	}

	public Circle(int x, int y, int r, boolean f, Color c) {
		setX1(x);
		setY1(y);
		this.r = r;
		setF(f);
		setC(c);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getC());
		g.drawOval(getX1() - r, getY1() - r, 2 * r, 2 * r);
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getC());
		g.fillOval(getX1() - r, getY1() - r, 2 * r, 2 * r);
	}

	@Override
	public boolean inside(int _x, int _y) {
		return Math.sqrt((getX1() - _x) * (getX1() - _x) + (getY1() - _y)
				* (getY1() - _y)) <= r;
	}

	@Override
	public Shape getThis() {
		return new Circle(getX1(), getY1(), r, isF(), getC());
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

		// R elements
		Element rr = doc.createElement("raduis");
		rr.appendChild(doc.createTextNode(r + ""));
		shape.appendChild(rr);

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
		int r = Integer.parseInt(shape.getElementsByTagName("raduis").item(0)
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
		return new Circle(x1, y1, r, v, t);

	}

	@Override
	public void saveJSON(JsonWriter writer) {
		try {
			writer.beginObject();
			writer.name("type").value(this.getClass().getName());
			writer.name("x1").value(getX1());
			writer.name("y1").value(getY1());
			writer.name("raduis").value(r);
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
			int r = reader.nextInt();
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
			return new Circle(xx1, yy1, r, v, t);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void move(Graphics g, int _x, int _y) {
		if (isF()) {
			g.setColor(getC());
			g.drawOval(_x - r, _y - r, 2 * r, 2 * r);
		} else {
			g.setColor(getC());
			g.fillOval(_x - r, _y - r, 2 * r, 2 * r);
		}

	}

}
