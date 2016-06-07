import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import com.ibm.wsdl.Constants;



public class wsdlReader {

	List<Definition> wsdls = new ArrayList<Definition>();
	List<String> wsdlUri;
	
	public wsdlReader() {
		
	}
	/**
	 * get WSDL-Definitions
	 */
	public void getWsdlDef(){
		
		for(int i=0;i<wsdlUri.size();i++){
			try {
				
				WSDLReader wsdlReader = WSDLFactory.newInstance().newWSDLReader();
				wsdlReader.setFeature(Constants.FEATURE_VERBOSE, false);
		        wsdlReader.setFeature("javax.wsdl.importDocuments", true);
		        Definition wsdlDef = wsdlReader.readWSDL(wsdlUri.get(i));
				//System.out.println(wsdlDef.getDocumentationElement().getTextContent());
				wsdls.add(wsdlDef);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}
	/**
	 * Liest aus einer Textdatei die URIs für die WSDLs
	 * @param path
	 * @return
	 */
	public void getWsdlUri(String path){
		List<String> wsdlList = new ArrayList<String>();
		String everything= "";
		try {
		BufferedReader br = new BufferedReader(new FileReader(path));

		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        line = br.readLine();
		        wsdlList.add(sb.toString());
		        sb.delete(0, sb.length());
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		this.wsdlUri=wsdlList;
		
		}
	}



