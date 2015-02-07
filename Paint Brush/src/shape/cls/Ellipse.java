package shape.cls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Ellipse extends Shape {

	private int w, h;

	public Ellipse() {

	}

	public Ellipse(int x, int y, int w, int h, boolean f, Color c) {
		setX1(x);
		setY1(y);
		setF(f);
		setC(c);
		this.w = w;
		this.h = h;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getC());
		g.drawOval(getX1() - w, getY1() - h, 2 * w, 2 * h);
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getC());
		g.fillOval(getX1() - w, getY1() - h, 2 * w, 2 * h);
	}

	@Override
	public boolean inside(int _x, int _y) {
		Ellipse2D tmp = new Ellipse2D.Double(getX1() - w, getY1() - h, 2 * w,
				2 * h);
		return tmp.contains(_x, _y);
	}

	@Override
	public Shape getThis() {
		return new Ellipse(getX1(), getY1(), w, h, isF(), getC());
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

		// W elements
		Element ww = doc.createElement("weight");
		ww.appendChild(doc.createTextNode(w + ""));
		shape.appendChild(ww);
		// H elements
		Element hh = doc.createElement("height");
		hh.appendChild(doc.createTextNode(h + ""));
		shape.appendChild(hh);

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
		int weight = Integer.parseInt(shape.getElementsByTagName("weight")
				.item(0).getTextContent());
		int height = Integer.parseInt(shape.getElementsByTagName("height")
				.item(0).getTextContent());
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
		return new Ellipse(x1, y1, weight, height, v, t);
	}

	@Override
	public void saveJSON(JsonWriter writer) {
		try {
			writer.beginObject();
			writer.name("type").value(this.getClass().getName());
			writer.name("x1").value(getX1());
			writer.name("y1").value(getY1());
			writer.name("weight").value(w);
			writer.name("height").value(h);
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
			int ww = reader.nextInt();
			c = reader.nextName();
			int hh = reader.nextInt();
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
			return new Ellipse(xx1, yy1, ww, hh, v, t);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void move(Graphics g, int _x, int _y) {
		if (isF()) {
			g.setColor(getC());
			g.drawOval(_x - w, _y - h, 2 * w, 2 * h);
		} else {
			g.setColor(getC());
			g.fillOval(_x - w, _y - h, 2 * w, 2 * h);
		}

	}

}
