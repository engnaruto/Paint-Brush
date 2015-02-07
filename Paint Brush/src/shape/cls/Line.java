package shape.cls;

import java.awt.*;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Line extends Shape {

	private int x2, y2;
	

	public Line() {

	}

	public Line(int x1, int y1, int x2, int y2, Color c) {
		setX1(x1);
		setY1(y1);
		setF(true);
		setC(c);
		this.x2 = x2;
		this.y2 = y2;
	}
	// Compute the dot product AB ⋅ BC
	private int dot(int[] A, int[] B, int[] C) {
		int[] AB = new int[2];
		int[] BC = new int[2];
		AB[0] = B[0] - A[0];
		AB[1] = B[1] - A[1];
		BC[0] = C[0] - B[0];
		BC[1] = C[1] - B[1];
		int dot = AB[0] * BC[0] + AB[1] * BC[1];
		return dot;
	}

	// Compute the cross product AB x AC
	private int cross(int[] A, int[] B, int[] C) {
		int[] AB = new int[2];
		int[] AC = new int[2];
		AB[0] = B[0] - A[0];
		AB[1] = B[1] - A[1];
		AC[0] = C[0] - A[0];
		AC[1] = C[1] - A[1];
		int cross = AB[0] * AC[1] - AB[1] * AC[0];
		return cross;
	}

	// Compute the distance from A to B
	private double distance(int[] A, int[] B) {
		int d1 = A[0] - B[0];
		int d2 = A[1] - B[1];
		return Math.sqrt(d1 * d1 + d2 * d2);
	}

	// Compute the distance from AB to C
	private double linePointDist(int[] A, int[] B, int[] C) {
		double dist = cross(A, B, C) / distance(A, B);
		int dot1 = dot(A, B, C);
		if (dot1 > 0)
			return distance(B, C);
		int dot2 = dot(B, A, C);
		if (dot2 > 0)
			return distance(A, C);
		return Math.abs(dist);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getC());
		g.drawLine(getX1(), getY1(), x2, y2);
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getC());
		g.drawLine(getX1(), getY1(), x2, y2);
	}

	@Override
	public boolean inside(int _x, int _y) {
		double dist = linePointDist(new int[] { getX1(), getY1() }, new int[] {
				x2, y2 }, new int[] { _x, _y });
		return dist <= 5;
	}

	@Override
	public Shape getThis() {
		return new Line(getX1(), getY1(), x2, y2, getC());
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
		return new Line(x1, y1, x2, y2, t);

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
			reader.nextBoolean();
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
			return new Line(xx1, yy1, xx2, yy2, t);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void move(Graphics g, int _x, int _y) {
		// TODO Auto-generated method stub
		
	}

}