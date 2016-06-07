import java.util.List;
import javax.wsdl.Definition;

public class Search {

	public Search() {

	}

	public int count(String def, String keywords) {

		int countKeyword = 0;
		String[] keyword = keywords.split(",");
		
		for (int i = 0; i < keyword.length; i++) {
			try {
				String[] splittedWsdlDoc = def.split(keyword[i].toLowerCase());
				
				countKeyword = countKeyword + splittedWsdlDoc.length-1;
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

		return countKeyword;
	}

}
