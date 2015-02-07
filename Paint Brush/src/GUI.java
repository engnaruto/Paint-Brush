import java.awt.*;


import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import shape.cls.Circle;
import shape.cls.Ellipse;
import shape.cls.Line;
import shape.cls.Rectangle;
import shape.cls.Shape;
import shape.cls.Square;
import shape.cls.Triangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	GUI() {
		initComponents();
	}

	private void initComponents() {
		a = new Color[] { Color.black, Color.blue, Color.cyan, Color.darkGray,
				Color.gray, Color.green, Color.lightGray, Color.magenta,
				Color.orange, Color.pink, Color.red, Color.yellow };

		al = new ArrayList<Shape>();
		undo = new Stack<ArrayList<Shape>>();
		redo = new Stack<ArrayList<Shape>>();

		jPanel1 = new JPanel() {
			public void paint(Graphics g) {
				// TODO add your handling code here:
				super.paint(g);
				for (Shape s : al) {
					if (s.isF())
						s.draw(g);
					else
						s.fill(g);
				}
			}
		};
		jPanel1.setBorder(new LineBorder(new Color(0, 0, 0)));
		jPanel1.setVisible(true);
		jPanel1.setBackground(Color.WHITE);
		jButton7 = new JButton();
		jButton8 = new JButton();
		jButton9 = new JButton();
		jButton10 = new JButton();

		jScrollPane1 = new JScrollPane();

		jCheckBox1 = new JCheckBox();
		jCheckBox2 = new JCheckBox();

		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jLabel4 = new JLabel();
		jLabel5 = new JLabel();
		jLabel6 = new JLabel();
		jLabel7 = new JLabel();

		X1 = new JTextField();
		Y1 = new JTextField();
		X2 = new JTextField();
		Y2 = new JTextField();
		X3 = new JTextField();
		Y3 = new JTextField();

		jCheckBox2.setSelected(true);

		X1.setText("0");
		Y1.setText("0");
		X2.setText("100");
		Y2.setText("100");
		X3.setText("100");
		Y3.setText("100");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		jButton7.setText("Undo");

		jButton7.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton7MouseClicked(evt);
			}
		});

		jButton8.setText("Redo");

		jButton8.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton8MouseClicked(evt);
			}
		});

		jButton9.setText("Clear");

		jButton9.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton9MouseClicked(evt);
			}
		});

		jButton10.setText("Change");

		jButton10.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton10MouseClicked(evt);
			}
		});

		jLabel1.setText("Colors");
		jLabel2.setText("X1");
		jLabel3.setText("Y1");
		jLabel4.setText("X2");
		jLabel5.setText("Y2");
		jLabel6.setText("X3");
		jLabel7.setText("Y3");

		jCheckBox1.setText("Fill");

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				Alignment.TRAILING).addGap(0, 530, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				Alignment.LEADING).addGap(0, 522, Short.MAX_VALUE));
		jPanel1.setLayout(jPanel1Layout);

		jCheckBox2.setText("Draw with mouse");

		jButton10.setText("Change");

		scrollPane = new JScrollPane();

		button11 = new JButton();
		button11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// TODO add your handling code here:
				removeListeners();
				MouseAdapter l = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						for (int i = al.size() - 1; i > -1; i--) {
							Shape s = al.get(i);
							if (s.inside(evt.getX(), evt.getY())) {
								al.remove(i);
								break;
							}
						}
						undo.add(new ArrayList<>(al));
						redo.clear();
						repaint();
					}
				};
				jPanel1.addMouseListener(l);
				jPanel1.addMouseMotionListener(l);
			}

		});

		button11.setText("Delete");

		button_1 = new JButton();
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO add your handling code here:
				removeListeners();
				MouseAdapter l = new MouseAdapter() {
					Shape t = null;
					Graphics2D g = (Graphics2D) jPanel1.getGraphics();

					@Override
					public void mousePressed(MouseEvent evt) {
						for (int i = al.size() - 1; i > -1; i--) {
							Shape s = al.get(i);
							if (s.inside(evt.getX(), evt.getY())) {
								t = al.get(i).getThis();
								al.remove(i);
								break;
							}
						}
					}

					@Override
					public void mouseDragged(MouseEvent evt) {
						if (t != null) {
							repaint();
							t.move(g, evt.getX(), evt.getY());
						}
					}

					@Override
					public void mouseReleased(MouseEvent evt) {
						if (t != null) {
							Shape tmp = t.getThis();
							tmp.setX1(evt.getX());
							tmp.setY1(evt.getY());
							addShape(tmp);
							repaint();
							t = null;
						}
					}

				};
				jPanel1.addMouseListener(l);
				jPanel1.addMouseMotionListener(l);
			}
		});
		button_1.setText("Move");

		button_2 = new JButton();
		button_2.setText("Resize");
		// btnLoadShape.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// // addShpeButton();
		// try {
		// // ClassLoader myClassLoader =
		// // ClassLoader.getSystemClassLoader();
		//
		// // Step 2: Define a class to be loaded.
		// // File file = new File("c:\\myclasses\\");
		// // Convert File to a URL
		// File file = new File("E:\\cl\\DemoClass.class");
		// URL url = null;
		// url = file.toURL();
		// URL[] urls = new URL[] { url };
		// ClassLoader cl = new URLClassLoader(urls);
		// String g = file.getName().replace(".class", "");
		// Class<?> myClass = cl.loadClass(g);
		//
		// // Step 3: Load the class
		//
		// // Class<?> myClass =
		// // myClassLoader.loadClass(classNameToBeLoaded);
		//
		// // Step 4: create a new instance of that class
		//
		// // Object whatInstance = myClass.newInstance();
		//
		// // String methodParameter = "a quick brown fox";
		//
		// // Step 5: get the method, with proper parameter signature.
		// // The second parameter is the parameter type.
		// // There can be multiple parameters for the method we are
		// // trying to
		// // call,
		// // hence the use of array.
		//
		// // Method myMethod = myClass.getMethod("demoMethod",
		// // new Class[] { String.class });
		//
		// // Step 6:
		// // Calling the real method. Passing methodParameter as
		// // parameter. You can pass multiple parameters based on
		// // the signature of the method you are calling. Hence
		// // there is an array.
		// //
		// // String returnValue = (String)
		// // myMethod.invoke(whatInstance,
		// // new Object[] { methodParameter });
		//
		// System.out
		// .println("The value returned from the method is:");
		// } catch (SecurityException e1) {
		// e1.printStackTrace();
		// } catch (IllegalArgumentException e1) {
		// e1.printStackTrace();
		// } catch (ClassNotFoundException e1) {
		// e1.printStackTrace();
		// } catch (MalformedURLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// }
		//
		// int counter = 6;
		//
		// @SuppressWarnings({ "deprecation", "resource", "unused" })
		// public void addShpeButton() throws MalformedURLException,
		// ClassNotFoundException {
		// // TODO
		// JFileChooser chooser = new JFileChooser();
		// chooser.setCurrentDirectory(new java.io.File("."));
		// chooser.setDialogTitle("Load Shape file");
		// chooser.setCurrentDirectory(new File("E:\\"));
		// chooser.setMultiSelectionEnabled(false);
		// chooser.setAcceptAllFileFilterUsed(false);
		// chooser.addChoosableFileFilter(new FileNameExtensionFilter(
		// "Shape file (.class)", "class"));
		// // set default type
		// chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
		// if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		// // File shapeFile = chooser.getSelectedFile();
		//
		// File shapeFile = new File("E:\\Circle.class");
		//
		// URL url = shapeFile.toURL();
		// URL[] urls = new URL[] { url };
		// ClassLoader cl = new URLClassLoader(urls);
		// String g = shapeFile.getName().replace(".class", "");
		// Class<?> shapeClass = cl.loadClass(g);
		// System.out.println("Done");
		//
		// JButton f = new JButton();
		// f.setText("No: " + counter++);
		//
		// panel.add(f);
		// scrollPane.revalidate();
		// panel.repaint();
		// }
		// }
		// });

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabel2)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										X1,
																										GroupLayout.PREFERRED_SIZE,
																										50,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jLabel3)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										Y1,
																										GroupLayout.PREFERRED_SIZE,
																										50,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jLabel4)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										X2,
																										GroupLayout.PREFERRED_SIZE,
																										50,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jLabel5)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										Y2,
																										GroupLayout.PREFERRED_SIZE,
																										50,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jLabel6)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										X3,
																										GroupLayout.PREFERRED_SIZE,
																										50,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jLabel7)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										Y3,
																										GroupLayout.PREFERRED_SIZE,
																										50,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jCheckBox2)
																								.addGap(0,
																										5,
																										Short.MAX_VALUE))
																				.addComponent(
																						jPanel1,
																						GroupLayout.DEFAULT_SIZE,
																						530,
																						Short.MAX_VALUE))
																.addGap(5)
																.addGroup(
																		layout.createParallelGroup(
																				Alignment.TRAILING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												Alignment.LEADING)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addGap(20)
																																.addComponent(
																																		jLabel1))
																												.addGroup(
																														layout.createParallelGroup(
																																Alignment.TRAILING)
																																.addComponent(
																																		jScrollPane1,
																																		GroupLayout.PREFERRED_SIZE,
																																		99,
																																		GroupLayout.PREFERRED_SIZE)
																																.addComponent(
																																		scrollPane,
																																		GroupLayout.PREFERRED_SIZE,
																																		120,
																																		GroupLayout.PREFERRED_SIZE)
																																.addComponent(
																																		jCheckBox1,
																																		GroupLayout.PREFERRED_SIZE,
																																		99,
																																		GroupLayout.PREFERRED_SIZE)))
																								.addGap(1))
																				.addComponent(
																						jButton10,
																						GroupLayout.PREFERRED_SIZE,
																						99,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						button11,
																						GroupLayout.PREFERRED_SIZE,
																						99,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						button_1,
																						GroupLayout.PREFERRED_SIZE,
																						99,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						button_2,
																						GroupLayout.PREFERRED_SIZE,
																						99,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jButton7,
																						GroupLayout.PREFERRED_SIZE,
																						99,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jButton8,
																						GroupLayout.PREFERRED_SIZE,
																						99,
																						GroupLayout.PREFERRED_SIZE)))
												.addComponent(
														jButton9,
														Alignment.TRAILING,
														GroupLayout.PREFERRED_SIZE,
														99,
														GroupLayout.PREFERRED_SIZE))));
		layout.setVerticalGroup(layout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														scrollPane,
														GroupLayout.PREFERRED_SIZE,
														210,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						jLabel2)
																				.addComponent(
																						X1,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jLabel3)
																				.addComponent(
																						Y1,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jLabel4)
																				.addComponent(
																						X2,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jLabel5)
																				.addComponent(
																						Y2,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jLabel6)
																				.addComponent(
																						X3,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jLabel7)
																				.addComponent(
																						Y3,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jCheckBox2))
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				Alignment.LEADING)
																				.addGroup(
																						Alignment.TRAILING,
																						layout.createSequentialGroup()
																								.addGap(179)
																								.addComponent(
																										jLabel1)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jScrollPane1,
																										GroupLayout.PREFERRED_SIZE,
																										151,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jCheckBox1)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jButton10)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										button11)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										button_1)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										button_2)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jButton7)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jButton8))
																				.addComponent(
																						jPanel1,
																						GroupLayout.DEFAULT_SIZE,
																						545,
																						Short.MAX_VALUE))))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(jButton9).addGap(1)));
		jList1 = new JList<String>();
		jScrollPane1.setViewportView(jList1);

		jList1.setModel(new AbstractListModel<String>() {
			String[] strings = { "Black", "Blue", "Cyan", "Dark Gray", "Gray",
					"Green", "Light Gray", "Magenta", "Orange", "Pink", "Red",
					"Yellow" };

			public int getSize() {
				return strings.length;
			}

			public String getElementAt(int i) {
				return strings[i];
			}
		});

		jList1.setSelectedIndex(0);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		jButton6 = new JButton();

		jButton6.setText("Square");

		jButton6.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton6MouseClicked(evt);
			}
		});
		jButton2 = new JButton();

		jButton2.setText("Circle");

		jButton2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton2MouseClicked(evt);
			}
		});

		jButton1 = new JButton();

		jButton1.setText("Line");

		jButton1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton1MouseClicked(evt);
			}
		});
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		panel.add(jButton1);
		panel.add(jButton2);
		jButton3 = new JButton();

		jButton3.setText("Ellipse");

		jButton3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton3MouseClicked(evt);
			}
		});
		panel.add(jButton3);
		jButton5 = new JButton();

		jButton5.setText("Rect");

		jButton5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton5MouseClicked(evt);
			}
		});
		jButton4 = new JButton();

		jButton4.setText("Triangle");

		jButton4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jButton4MouseClicked(evt);
			}
		});
		panel.add(jButton4);
		panel.add(jButton5);
		panel.add(jButton6);
		getContentPane().setLayout(layout);

		pack();

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("XML");
		menuBar.add(mnFile);

		mntmSaveXML = new JMenuItem("Save as XML");
		mntmSaveXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
				try {

					DocumentBuilderFactory docFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder docBuilder = docFactory
							.newDocumentBuilder();

					// root elements
					Document doc = docBuilder.newDocument();
					Element rootElement = doc.createElement("Shapes");
					doc.appendChild(rootElement);

					for (Shape s : al) {
						s.saveXML(doc, rootElement);
					}

					// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory
							.newInstance();
					Transformer transformer = transformerFactory
							.newTransformer();
					DOMSource source = new DOMSource(doc);

					JFileChooser chooser = new JFileChooser();

					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Save as XML");
					// chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					//
					// disable the "All files" option.
					//
					chooser.setMultiSelectionEnabled(false);
					chooser.setAcceptAllFileFilterUsed(false);
					chooser.addChoosableFileFilter(new FileNameExtensionFilter(
							"XML file (.xml)", "xml"));

					// set default type
					chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
					// // set default file
					chooser.setSelectedFile(new File("E:\\New Drawing.xml"));

					String dir = "E:\\file.xml";
					if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						dir = chooser.getSelectedFile().getAbsolutePath();
						if (!dir.endsWith(".xml")) {
							dir += ".xml";
						}
						StreamResult result = new StreamResult(new File(dir));
						transformer.transform(source, result);
//						System.out.println("File saved!");
						JOptionPane.showMessageDialog(null,
								"File saved sucessfully!");
					}

					// StreamResult result = new StreamResult(new File(
					// "E:\\file.xml"));

					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);

				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				} catch (TransformerException tfe) {
					tfe.printStackTrace();
				}
			}
		});

		mnFile.add(mntmSaveXML);

		mntmLoadXML = new JMenuItem("Load XML File");
		mntmLoadXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				try {
					// undo.push(new ArrayList<>(al));
					al.clear();
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Load XML file");
					// disable the "All files" option.
					chooser.setCurrentDirectory(new File("E:\\"));
					chooser.setMultiSelectionEnabled(false);
					chooser.setAcceptAllFileFilterUsed(false);
					chooser.addChoosableFileFilter(new FileNameExtensionFilter(
							"XML file (.xml)", "xml"));

					// set default type
					chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File xmlFile = chooser.getSelectedFile();
						// load from file
						// File xmlFile = new File("E:\\file.xml");
						DocumentBuilderFactory documentFactory = DocumentBuilderFactory
								.newInstance();
						DocumentBuilder documentBuilder = documentFactory
								.newDocumentBuilder();
						Document doc = documentBuilder.parse(xmlFile);

						doc.getDocumentElement().normalize();
						NodeList nodeList = doc.getElementsByTagName("Shape");

						// System.out.println("Root element :"
						// + doc.getDocumentElement().getNodeName());

						for (int temp = 0; temp < nodeList.getLength(); temp++) {
							Node node = nodeList.item(temp);

							// System.out.println("\nElement type :"
							// + node.getNodeName());

							if (node.getNodeType() == Node.ELEMENT_NODE) {

								Element shape = (Element) node;

								String shapeType = shape.getAttribute("type");
								// System.out.println(shapeType);
								if (!shapeType.startsWith("shape.cls.")) {
									shapeType = "shape.cls." + shapeType;
								}
								Shape s;
								try {
									s = castClass(shapeType);
								} catch (Exception e2) {
									continue;
								}

								s = s.loadXML(shape);
								al.add(s);
							}
						}
						undo.push(new ArrayList<>(al));
						repaint();
						JOptionPane.showMessageDialog(null, "Load");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		mnFile.add(mntmLoadXML);

		mnJson = new JMenu("JSON");
		menuBar.add(mnJson);

		mntmSaveAsJson = new JMenuItem("Save As JSON");
		mntmSaveAsJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
				try {

					JFileChooser chooser = new JFileChooser();

					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Save as JSON");
					// chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					//
					// disable the "All files" option.
					//
					chooser.setMultiSelectionEnabled(false);
					chooser.setAcceptAllFileFilterUsed(false);
					chooser.addChoosableFileFilter(new FileNameExtensionFilter(
							"JSON file (.json)", "json"));

					// set default type
					chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
					// // set default file
					chooser.setSelectedFile(new File("E:\\New Drawing.json"));

					String dir = "E:\\file.xml";
					if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						dir = chooser.getSelectedFile().getAbsolutePath();
						if (!dir.endsWith(".json")) {
							dir += ".json";
						}
						JsonWriter writer = new JsonWriter(new FileWriter(dir));
						writer.beginArray();
						for (Shape s : al) {
							s.saveJSON(writer);
						}
						writer.endArray();

						writer.close();

						// System.out.println("File saved!");
						JOptionPane.showMessageDialog(null,
								"File saved sucessfully!");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		mnJson.add(mntmSaveAsJson);

		mntmLoadJsonFile = new JMenuItem("Load JSON File");
		mntmLoadJsonFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
				try {
					al.clear();
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Load JSON file");
					// disable the "All files" option.
					chooser.setCurrentDirectory(new File("E:\\"));
					chooser.setMultiSelectionEnabled(false);
					chooser.setAcceptAllFileFilterUsed(false);
					chooser.addChoosableFileFilter(new FileNameExtensionFilter(
							"JSON file (.json)", "json"));

					// set default type
					chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File jsonFile = chooser.getSelectedFile();
						JsonReader reader = new JsonReader(new FileReader(
								jsonFile));
						// load from file
						reader.beginArray();
						while (reader.hasNext()) {
							reader.beginObject();
							while (reader.hasNext()) {

								String shapeType = reader.nextName();
								shapeType = reader.nextString();
								// System.out.println(shapeType);
								if (!shapeType.startsWith("shape.cls.")) {
									shapeType = "shape.cls." + shapeType;
								}
								Shape s;
								try {
									s = castClass(shapeType);
								} catch (Exception e2) {
									continue;
								}

								s = s.loadJSON(reader);
								al.add(s);

							}
							reader.endObject();
						}
						reader.endArray();
						reader.close();

						undo.push(new ArrayList<>(al));
						repaint();
						JOptionPane.showMessageDialog(null, "Load");
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		mnJson.add(mntmLoadJsonFile);
	}

	public static Shape castClass(String whichClass) {
		// System.out.println(whichClass);
		try {
			// Class<?> clazz = Class.forName(whichClass);
			// return (Shape) clazz.newInstance();
			return (Shape) Class.forName(whichClass).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void removeListeners() {
		MouseListener[] a = jPanel1.getMouseListeners();
		MouseMotionListener[] a2 = jPanel1.getMouseMotionListeners();
		for (MouseListener ml : a)
			jPanel1.removeMouseListener(ml);
		for (MouseMotionListener ml : a2)
			jPanel1.removeMouseMotionListener(ml);
	}

	private boolean getDrawType() {
		return !jCheckBox1.isSelected();
	}

	private Color getSelectedColor() {
		return a[jList1.getSelectedIndex()];
	}

	private void addShape(Shape s) {
		al.add(s);
		undo.push(new ArrayList<>(al));
		redo.clear();
	}

	private void enableAll() {
		X1.setVisible(true);
		Y1.setVisible(true);
		X2.setVisible(true);
		Y2.setVisible(true);
		X3.setVisible(false);
		Y3.setVisible(false);
		jLabel2.setVisible(true);
		jLabel3.setVisible(true);
		jLabel4.setVisible(true);
		jLabel5.setVisible(true);
		jLabel6.setVisible(false);
		jLabel7.setVisible(false);
	}

	private boolean isNumber(String s) {
		for (int i = 0; i < s.length(); i++)
			if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9'))
				return false;
		return true;
	}

	private void jButton1MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		enableAll();
		jLabel2.setText("X1");
		jLabel3.setText("Y1");
		jLabel4.setText("X2");
		jLabel5.setText("Y2");
		if (jCheckBox2.isSelected()) {
			MouseAdapter l = new MouseAdapter() {
				int x1, y1, x2, y2;
				Graphics2D g = (Graphics2D) jPanel1.getGraphics();

				@Override
				public void mousePressed(MouseEvent evt) {
					x1 = x2 = evt.getX();
					y1 = y2 = evt.getY();
					g.setColor(getSelectedColor());
				}

				@Override
				public void mouseReleased(MouseEvent evt) {
					x2 = evt.getX();
					y2 = evt.getY();
					 g.drawLine(x1, y1, x2, y2);
					addShape(new Line(x1, y1, x2, y2, getSelectedColor()));
				}

				@Override
				public void mouseDragged(MouseEvent evt) {
					x2 = evt.getX();
					y2 = evt.getY();
					repaint();
					g.drawLine(x1, y1, x2, y2);
				}
			};
			jPanel1.addMouseListener(l);
			jPanel1.addMouseMotionListener(l);
		} else {
			int x1, y1, x2, y2;
			Graphics2D g = (Graphics2D) jPanel1.getGraphics();
			g.setColor(getSelectedColor());
			if (isNumber(X1.getText() + X2.getText() + Y1.getText()
					+ Y2.getText())) {
				x1 = Integer.parseInt(X1.getText());
				y1 = Integer.parseInt(Y1.getText());
				x2 = Integer.parseInt(X2.getText());
				y2 = Integer.parseInt(Y2.getText());
				g.drawLine(x1, y1, x2, y2);
				addShape(new Line(x1, y1, x2, y2, getSelectedColor()));
				repaint();
			}
		}
	}

	private void jButton2MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		enableAll();
		jLabel2.setText("X");
		jLabel3.setText("Y");
		jLabel4.setText("R");
		jLabel5.setVisible(false);
		Y2.setVisible(false);
		if (jCheckBox2.isSelected()) {
			MouseAdapter l = new MouseAdapter() {
				int x, y, r;
				Graphics2D g = (Graphics2D) jPanel1.getGraphics();

				@Override
				public void mousePressed(MouseEvent evt) {
					x = evt.getX();
					y = evt.getY();
					r = 0;
					g.setColor(getSelectedColor());
				}

				@Override
				public void mouseReleased(MouseEvent evt) {
					r = Math.abs(x - evt.getX());
					if (getDrawType())
						g.drawOval(x - r, y - r, 2 * r, 2 * r);
					else
						g.fillOval(x - r, y - r, 2 * r, 2 * r);
					addShape(new Circle(x, y, r, getDrawType(),
							getSelectedColor()));
				}

				@Override
				public void mouseDragged(MouseEvent evt) {
					r = Math.abs(x - evt.getX());
					repaint();
					if (getDrawType())
						g.drawOval(x - r, y - r, 2 * r, 2 * r);
					else
						g.fillOval(x - r, y - r, 2 * r, 2 * r);
				}
			};
			jPanel1.addMouseListener(l);
			jPanel1.addMouseMotionListener(l);
		} else {
			int x1, y1, r;
			Graphics2D g = (Graphics2D) jPanel1.getGraphics();
			g.setColor(getSelectedColor());
			if (isNumber(X1.getText() + Y1.getText() + X2.getText())) {
				x1 = Integer.parseInt(X1.getText());
				y1 = Integer.parseInt(Y1.getText());
				r = Integer.parseInt(X2.getText());
				if (getDrawType()) {
					g.drawOval(x1 - r, y1 - r, 2 * r, 2 * r);
				} else {
					g.fillOval(x1 - r, y1 - r, 2 * r, 2 * r);
				}
				addShape(new Circle(x1, y1, r, getDrawType(),
						getSelectedColor()));
			}
		}
	}

	private void jButton3MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		enableAll();
		jLabel2.setText("X");
		jLabel3.setText("Y");
		jLabel4.setText("H");
		jLabel5.setText("W");
		if (jCheckBox2.isSelected()) {
			MouseAdapter l = new MouseAdapter() {
				int x, y, w, h;
				Graphics2D g = (Graphics2D) jPanel1.getGraphics();

				@Override
				public void mousePressed(MouseEvent evt) {
					x = evt.getX();
					y = evt.getY();
					w = h = 0;
					g.setColor(getSelectedColor());
				}

				@Override
				public void mouseReleased(MouseEvent evt) {
					w = Math.abs(x - evt.getX());
					h = Math.abs(y - evt.getY());
					if (getDrawType())
						g.drawOval(x - w, y - h, 2 * w, 2 * h);
					else
						g.fillOval(x - w, y - h, 2 * w, 2 * h);
					addShape(new Ellipse(x, y, w, h, getDrawType(),
							getSelectedColor()));
				}

				@Override
				public void mouseDragged(MouseEvent evt) {
					w = Math.abs(x - evt.getX());
					h = Math.abs(y - evt.getY());
					repaint();
					if (getDrawType()) {
						g.drawOval(x - w, y - h, 2 * w, 2 * h);
					} else {
						g.fillOval(x - w, y - h, 2 * w, 2 * h);
					}
				}
			};
			jPanel1.addMouseListener(l);
			jPanel1.addMouseMotionListener(l);
		} else {
			int x1, y1, h, w;
			Graphics2D g = (Graphics2D) jPanel1.getGraphics();
			g.setColor(getSelectedColor());
			if (isNumber(X1.getText() + Y1.getText() + X2.getText()
					+ Y2.getText())) {
				x1 = Integer.parseInt(X1.getText());
				y1 = Integer.parseInt(Y1.getText());
				h = Integer.parseInt(X2.getText());
				w = Integer.parseInt(Y2.getText());
				if (getDrawType()) {
					g.drawOval(x1 - w, y1 - h, 2 * w, 2 * h);
				} else {
					g.fillOval(x1 - w, y1 - h, 2 * w, 2 * h);
				}
				addShape(new Ellipse(x1, y1, w, h, getDrawType(),
						getSelectedColor()));
			}
		}
	}

	private void jButton4MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		enableAll();
		jLabel6.setVisible(true);
		jLabel7.setVisible(true);
		X3.setVisible(true);
		Y3.setVisible(true);
		jLabel2.setText("X1");
		jLabel3.setText("Y1");
		jLabel4.setText("X2");
		jLabel5.setText("Y2");
		jLabel6.setText("X3");
		jLabel7.setText("Y3");
		if (jCheckBox2.isSelected()) {
			MouseAdapter l = new MouseAdapter() {
				int x[] = new int[3];
				int y[] = new int[3];
				int counter = 0;
				Graphics2D g = (Graphics2D) jPanel1.getGraphics();

				@Override
				public void mousePressed(MouseEvent evt) {
					if (counter == 0)
						g.setColor(getSelectedColor());
					x[counter] = evt.getX();
					y[counter++] = evt.getY();
					if (counter > 2)
						counter = 2;
				}

				@Override
				public void mouseDragged(MouseEvent evt) {
					int x2 = evt.getX();
					int y2 = evt.getY();
					if (counter == 1) {
						g.drawLine(x[0], y[0], x2, y2);
					} else if (counter == 2) {
						if (getDrawType())
							g.drawPolygon(new int[] { x[0], x[1], x2 },
									new int[] { y[0], y[1], y2 }, 3);
						else
							g.fillPolygon(new int[] { x[0], x[1], x2 },
									new int[] { y[0], y[1], y2 }, 3);
					}
					repaint();
				}

				@Override
				public void mouseReleased(MouseEvent evt) {
					if (counter == 1) {
						x[counter] = evt.getX();
						y[counter++] = evt.getY();
						g.drawLine(x[0], y[0], x[1], y[1]);
					} else if (counter == 2) {
						x[2] = evt.getX();
						y[2] = evt.getY();
						if (getDrawType())
							g.drawPolygon(new int[] { x[0], x[1], x[2] },
									new int[] { y[0], y[1], y[2] }, 3);
						else
							g.fillPolygon(new int[] { x[0], x[1], x[2] },
									new int[] { y[0], y[1], y[2] }, 3);
						addShape(new Triangle(x[0], y[0], x[1], y[1], x[2],
								y[2], getDrawType(), getSelectedColor()));
						counter = 0;
					}
					repaint();
				}
			};
			jPanel1.addMouseListener(l);
			jPanel1.addMouseMotionListener(l);
		} else {
			int x1, y1, x2, y2, x3, y3;
			Graphics2D g = (Graphics2D) jPanel1.getGraphics();
			g.setColor(getSelectedColor());
			if (isNumber(X1.getText() + Y1.getText() + X2.getText()
					+ Y2.getText() + X3.getText() + Y3.getText())) {
				x1 = Integer.parseInt(X1.getText());
				y1 = Integer.parseInt(Y1.getText());
				x2 = Integer.parseInt(X2.getText());
				y2 = Integer.parseInt(Y2.getText());
				x3 = Integer.parseInt(X3.getText());
				y3 = Integer.parseInt(Y3.getText());
				if (getDrawType()) {
					g.drawPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2,
							y3 }, 3);
				} else {
					g.fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2,
							y3 }, 3);
				}
				addShape(new Triangle(x1, y1, x2, y2, x3, y3, getDrawType(),
						getSelectedColor()));
			}
		}
	}

	private void jButton5MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		enableAll();
		jLabel2.setText("X1");
		jLabel3.setText("Y1");
		jLabel4.setText("X2");
		jLabel5.setText("Y2");
		if (jCheckBox2.isSelected()) {
			MouseAdapter l = new MouseAdapter() {
				int x1, y1, x2, y2;
				Graphics2D g = (Graphics2D) jPanel1.getGraphics();

				@Override
				public void mousePressed(MouseEvent evt) {
					x1 = x2 = evt.getX();
					y1 = y2 = evt.getY();
					g.setColor(getSelectedColor());
				}

				@Override
				public void mouseReleased(MouseEvent evt) {
					x2 = evt.getX();
					y2 = evt.getY();
					if (getDrawType())
						g.drawRect(Math.min(x1, x2), Math.min(y1, y2),
								Math.abs(x1 - x2), Math.abs(y1 - y2));
					else
						g.fillRect(Math.min(x1, x2), Math.min(y1, y2),
								Math.abs(x1 - x2), Math.abs(y1 - y2));
					addShape(new Rectangle(x1, y1, x2, y2, getDrawType(),
							getSelectedColor()));
				}

				@Override
				public void mouseDragged(MouseEvent evt) {
					x2 = evt.getX();
					y2 = evt.getY();
					repaint();
					if (getDrawType())
						g.drawRect(Math.min(x1, x2), Math.min(y1, y2),
								Math.abs(x1 - x2), Math.abs(y1 - y2));
					else
						g.fillRect(Math.min(x1, x2), Math.min(y1, y2),
								Math.abs(x1 - x2), Math.abs(y1 - y2));
				}
			};
			jPanel1.addMouseListener(l);
			jPanel1.addMouseMotionListener(l);
		} else {
			int x1, y1, x2, y2;
			Graphics2D g = (Graphics2D) jPanel1.getGraphics();
			g.setColor(getSelectedColor());
			if (isNumber(X1.getText() + Y1.getText() + X2.getText()
					+ Y2.getText())) {
				x1 = Integer.parseInt(X1.getText());
				y1 = Integer.parseInt(Y1.getText());
				x2 = Integer.parseInt(X2.getText());
				y2 = Integer.parseInt(Y2.getText());
				if (getDrawType()) {
					g.drawRect(Math.min(x1, x2), Math.min(y1, y2),
							Math.abs(x1 - x2), Math.abs(y1 - y2));
				} else {
					g.fillRect(Math.min(x1, x2), Math.min(y1, y2),
							Math.abs(x1 - x2), Math.abs(y1 - y2));
				}
				addShape(new Rectangle(x1, y1, x2, y2, getDrawType(),
						getSelectedColor()));
			}
		}
	}

	private void jButton6MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		enableAll();
		jLabel2.setText("X");
		jLabel3.setText("Y");
		jLabel4.setText("L");
		jLabel5.setVisible(false);
		Y2.setVisible(false);
		if (jCheckBox2.isSelected()) {
			MouseAdapter l = new MouseAdapter() {
				int x1, y1, x2, y2;
				Graphics2D g = (Graphics2D) jPanel1.getGraphics();

				@Override
				public void mousePressed(MouseEvent evt) {
					x1 = x2 = evt.getX();
					y1 = y2 = evt.getY();
					g.setColor(getSelectedColor());
				}

				@Override
				public void mouseReleased(MouseEvent evt) {
					x2 = evt.getX();
					y2 = evt.getY();
					if (y2 < y1)
						y2 = y1 - Math.abs(x1 - x2);
					else
						y2 = y1 + Math.abs(x1 - x2);
					if (getDrawType())
						g.drawRect(Math.min(x1, x2), Math.min(y1, y2),
								Math.abs(x1 - x2), Math.abs(y1 - y2));
					else
						g.fillRect(Math.min(x1, x2), Math.min(y1, y2),
								Math.abs(x1 - x2), Math.abs(y1 - y2));
					addShape(new Square(x1, y1, x2, y2, getDrawType(),
							getSelectedColor()));
				}

				@Override
				public void mouseDragged(MouseEvent evt) {
					x2 = evt.getX();
					y2 = evt.getY();
					if (y2 < y1)
						y2 = y1 - Math.abs(x1 - x2);
					else
						y2 = y1 + Math.abs(x1 - x2);
					repaint();
					if (getDrawType())
						g.drawRect(Math.min(x1, x2), Math.min(y1, y2),
								Math.abs(x1 - x2), Math.abs(y1 - y2));
					else
						g.fillRect(Math.min(x1, x2), Math.min(y1, y2),
								Math.abs(x1 - x2), Math.abs(y1 - y2));
				}
			};
			jPanel1.addMouseListener(l);
			jPanel1.addMouseMotionListener(l);
		} else {
			int x1, y1, x2;
			Graphics2D g = (Graphics2D) jPanel1.getGraphics();
			g.setColor(getSelectedColor());
			if (isNumber(X1.getText() + Y1.getText() + X2.getText())) {
				x1 = Integer.parseInt(X1.getText());
				y1 = Integer.parseInt(Y1.getText());
				x2 = Integer.parseInt(X2.getText());
				if (getDrawType()) {
					g.drawRect(x1, y1, x1 + x2, y1 + x2);
				} else {
					g.fillRect(x1, y1, x1 + x2, y1 + x2);
				}
				addShape(new Square(x1, y1, x1 + x2, y1 + x2, getDrawType(),
						getSelectedColor()));
			}
		}
	}

	private void jButton7MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		if (!undo.isEmpty()) {
			redo.push(new ArrayList<>(undo.pop()));
			if (undo.isEmpty()) {
				al.clear();
			} else {
				al = new ArrayList<>(undo.peek());
			}
		}
		repaint();
	}

	private void jButton8MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		if (!redo.isEmpty()) {
			undo.push(new ArrayList<>(redo.peek()));
			al = new ArrayList<>(redo.pop());
		}
		repaint();
	}

	private void jButton9MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		if (!al.isEmpty()) {
			al.clear();
			undo.push(new ArrayList<Shape>());
			redo.clear();
		}
		repaint();
	}

	private void jButton10MouseClicked(MouseEvent evt) {
		// TODO add your handling code here:
		removeListeners();
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				boolean changed = false;
				for (int i = al.size() - 1; i > -1; i--) {
					Shape s = al.get(i);
					if (s.inside(evt.getX(), evt.getY())) {
						al.set(i, s.getThis());
						al.get(i).setC(getSelectedColor());
						al.get(i).setF(getDrawType());
						changed = true;
						break;
					}
				}
				if (changed) {
					undo.add(new ArrayList<>(al));
					redo.clear();
					repaint();
				}
			}
		};
		jPanel1.addMouseListener(l);
		jPanel1.addMouseMotionListener(l);
	}

	public static void main(String args[]) throws MalformedURLException {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;

				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI tmp = new GUI();
				tmp.setSize(1300, 735);
				tmp.setVisible(true);
			}
		});
	}

	private JPanel jPanel1;
	private JPanel panel;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	private JButton jButton6;
	private JButton jButton7;
	private JButton jButton8;
	private JButton jButton9;
	private JButton jButton10;

	private JCheckBox jCheckBox1;
	private JCheckBox jCheckBox2;

	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;

	private JList<String> jList1;
	private JScrollPane jScrollPane1;

	private JTextField X1;
	private JTextField Y1;
	private JTextField X2;
	private JTextField Y2;
	private JTextField X3;
	private JTextField Y3;

	private Color[] a;
	private ArrayList<Shape> al;
	private Stack<ArrayList<Shape>> undo;
	private Stack<ArrayList<Shape>> redo;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSaveXML;
	private JMenuItem mntmLoadXML;
	private JMenu mnJson;
	private JMenuItem mntmSaveAsJson;
	private JMenuItem mntmLoadJsonFile;
	private JScrollPane scrollPane;
	private JButton button11;
	private JButton button_1;
	private JButton button_2;
}
