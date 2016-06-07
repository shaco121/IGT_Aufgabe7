import java.util.List;

import javax.wsdl.Definition;

public class main {

	public static void main(String[] args) {
		wsdlReader wsdl = new wsdlReader();
		BPMN2WSDL bpmn = new BPMN2WSDL();
		Search search = new Search();

		wsdl.getWsdlUri("C:/Users/User/Desktop/IGT/WSDLURI.txt");
		wsdl.getWsdlDef();
		List<String> keyWordList = bpmn.getActionFromBPMN();
		List<Definition> wsdlDefs = wsdl.wsdls;
		
		int[][] allCounts = new int[wsdlDefs.size()][keyWordList.size()];

		for (int i = 0; i < wsdlDefs.size(); i++) {
			for (int j = 0; j < keyWordList.size(); j++) {

				allCounts[i][j] = search.count(wsdlDefs.get(i).getDocumentationElement().getTextContent().toLowerCase(),
						keyWordList.get(j).toLowerCase());
			}

		}
		for (int i = 0; i < wsdlDefs.size(); i++) {
			for (int j = 0; j < keyWordList.size(); j++) {

				System.out.print(allCounts[i][j] + " ");

			}
			System.out.println();

		}

	}

}
