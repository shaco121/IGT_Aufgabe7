import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BPMN2WSDL {
	
	List<String> activityNamen = new ArrayList<String>();

	public BPMN2WSDL() {

	}

	public List<String> getActionFromBPMN() {
		

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("ActivityAction")) {
						boolean isInList = false;
						for (String a : activityNamen) {
							if (a.equals(attributes.getValue("Name"))) {
								isInList = true;
							}
						}
						if (!isInList) {
							activityNamen.add(attributes.getValue("Name"));
						}
					}

				}

				public void endElement(String uri, String localName, String qName) throws SAXException {

				}

				public void characters(char ch[], int start, int length) throws SAXException {

				}

			};

			saxParser.parse("C:/Users/User/Desktop/IGT/project.xml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String everything = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("C:/Users/User/Desktop/IGT/Fillers.txt"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			everything = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] fillers = everything.split(";");
		List<String> fillerList = Arrays.asList(fillers);
		List<String> keyList = new ArrayList<String>();

		for (String activityName : activityNamen) {
			String[] parts = activityName.split(" ");
			String cleanName = "";
			for (String part : parts) {
				if (!fillerList.contains(part)) {
					
					cleanName = cleanName + part+ "," ;//+ getSynonyms(part);
				
				}
			}
			keyList.add(cleanName);
		}
		return keyList;

	}



	private static Document loadTestDocument(String url) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder dbuilder = factory.newDocumentBuilder();
		Document doc = dbuilder.parse(new URL(url).openStream());
		// System.out.println(doc.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(1).getAttributes().getNamedItem("term"));
		return doc;

	}

	public String getSynonyms(String word) {
		String result = "";
		try {
			String url = ("https://www.openthesaurus.de/synonyme/search?q=" + URLEncoder.encode(word, "UTF-8")
					+ "&format=text/xml");
			Document document = loadTestDocument(url);
			NodeList synList = document.getElementsByTagName("synset");
			for (int temp = 0; temp < synList.getLength(); temp++) {
				Node syn = synList.item(temp);
				NodeList termList = syn.getChildNodes();
				if(termList.getLength()>=3){
				for (int i = 0; i < 3 ; i++) {
					if (termList.item(i).getNodeType() == Node.ELEMENT_NODE
							&& termList.item(i).getNodeName().equals("term")
							&& termList.item(i).getAttributes().getNamedItem("level") == null) {
						Element term = (Element) termList.item(i);
						result = result + "," + term.getAttribute("term");

					}
				}
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
