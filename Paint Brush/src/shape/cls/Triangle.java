package shape.cls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Triangle extends Shape {

	private int x2, y2, x3, y3;

	public Triangle() {

	}

	public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, boolean f,
			Color c) {
		setX1(x1);
		setY1(y1);
		setF(f);
		setC(c);
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getC());
		g.drawPolygon(new int[] { getX1(), x2, x3 }, new int[] { getY1(), y2,
				y3 }, 3);
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getC());
		g.fillPolygon(new int[] { getX1(), x2, x3 }, new int[] { getY1(), y2,
				y3 }, 3);
	}

	@Override
	public boolean inside(int _x, int _y) {
		Polygon tmp = new Polygon(new int[] { getX1(), x2, x3 }, new int[] {
				getY1(), y2, y3 }, 3);
		return tmp.contains(_x, _y);
	}

	@Override
	public Shape getThis() {
		return new Triangle(getX1(), getY1(), x2, y2, x3, y3, isF(), getC());
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
		Element xx2 = doc.createElement("x2");
		xx2.appendChild(doc.createTextNode(x2 + ""));
		shape.appendChild(xx2);

		// Y2 elements
		Element yy2 = doc.createElement("y2");
		yy2.appendChild(doc.createTextNode(y2 + ""));
		shape.appendChild(yy2);

		// X3 elements
		Element xx3 = doc.createElement("x3");
		xx3.appendChild(doc.createTextNode(x3 + ""));
		shape.appendChild(xx3);

		// Y3 elements
		Element yy3 = doc.createElement("y3");
		yy3.appendChild(doc.createTextNode(y3 + ""));
		shape.appendChild(yy3);

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
		int x2 = Integer.parseInt(shape.getElementsByTagName("x2").item(0)
				.getTextContent());
		int y2 = Integer.parseInt(shape.getElementsByTagName("y2").item(0)
				.getTextContent());
		int x3 = Integer.parseInt(shape.getElementsByTagName("x3").item(0)
				.getTextContent());
		int y3 = Integer.parseInt(shape.getElementsByTagName("y3").item(0)
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
		return new Triangle(x1, y1, x2, y2, x3, y3, v, t);
	}

	@Override
	public void saveJSON(JsonWriter writer) {
		try {
			writer.beginObject();
			writer.name("type").value(this.getClass().getName());
			writer.name("x1").value(getX1());
			writer.name("y1").value(getY1());
			writer.name("x2").value(x2);
			writer.name("y2").value(y2);
			writer.name("x3").value(x3);
			writer.name("y3").value(y3);
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
			c = reader.nextName();
			int xx3 = reader.nextInt();
			c = reader.nextName();
			int yy3 = reader.nextInt();
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
			return new Triangle(xx1, yy1, xx2, yy2, xx3, yy3, v, t);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void move(Graphics g, int _x, int _y) {
	

	}

}
