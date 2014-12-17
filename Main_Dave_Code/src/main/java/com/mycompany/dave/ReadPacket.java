package com.mycompany.dave;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadPacket {

	
	public void readFile(String fileName) throws Exception
	{
		File xmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(fileName);
		doc.getDocumentElement().normalize();
		
		String root = doc.getDocumentElement().getNodeName();
		System.out.println("root element: "+ root);
		
		NodeList nl = doc.getElementsByTagName(root);
		
		for(int i=0; i<nl.getLength(); i++)
		{
			
		}
		
	}
	
	
	
	
	public static void readTest() {

		try {

			File fXmlFile = new File("one.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("staff");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("Staff id : "
							+ eElement.getAttribute("id"));
					System.out.println("First Name : "
							+ eElement.getElementsByTagName("firstname")
									.item(0).getTextContent());
					System.out.println("Last Name : "
							+ eElement.getElementsByTagName("lastname").item(0)
									.getTextContent());
					System.out.println("Nick Name : "
							+ eElement.getElementsByTagName("nickname").item(0)
									.getTextContent());
					System.out.println("Salary : "
							+ eElement.getElementsByTagName("salary").item(0)
									.getTextContent());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}