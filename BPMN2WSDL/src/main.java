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
		
		
		int[][] allCounts = new int[keyWordList.size()][wsdlDefs.size()];

		for (int i = 0; i < keyWordList.size(); i++) {
			for (int j = 0; j < wsdlDefs.size(); j++) {

				allCounts[i][j] = search.count(wsdlDefs.get(j).getDocumentationElement().getTextContent().toLowerCase(),
						keyWordList.get(i).toLowerCase());
			}

		}
		

		
		for (int i = 0; i < keyWordList.size(); i++) {
			for (int j = 0; j < wsdlDefs.size(); j++) {

				System.out.print(allCounts[i][j] + " ");

			}
			System.out.println();
		}

		
		
		
		for(int i=0;i<bpmn.activityNamen.size();i++){
			int[] posTopWsdl = search.getTopWSDL(allCounts, i);
			System.out.println(bpmn.activityNamen.get(i)+": ");
		if(posTopWsdl!=null){	
		for(int a : posTopWsdl){
			System.out.println(wsdlDefs.get(a).getDocumentBaseURI());
		}
		
			
		}else{
			System.out.println("kein passender Webdienstgefunden");
		}
		System.out.println();
		}
		
		
		
	}

}
