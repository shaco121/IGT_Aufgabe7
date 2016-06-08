import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	
	public int[] getTopWSDL(int[][] counts, int activitySpalte){
		int[] topWSDL= new int[3];
		List<Integer> treffer = new ArrayList<Integer>();
		if(counts[activitySpalte]!=null){
			for (int index = 0; index < counts[activitySpalte].length; index++)
			{
			    treffer.add(counts[activitySpalte][index]);
			}
			
			Arrays.sort(counts[activitySpalte]);
			if(counts[activitySpalte][counts[activitySpalte].length-1]==0){
				return null;
			}
			for(int i=0;i<3;i++){
			topWSDL[i]=treffer.indexOf(counts[activitySpalte][counts[activitySpalte].length-(i+1)]);
			treffer.set(treffer.indexOf(counts[activitySpalte][counts[activitySpalte].length-(i+1)]), -1);
			}
		}
		return topWSDL;
		}
	
	
	
	public void calculate(double allPositivAnswers, double selectedPositives, double allSelected) {

		double precision = (selectedPositives / allSelected) * 100;
		double recall = (selectedPositives / allPositivAnswers) * 100;

		double fMeasure = (2 * (precision * recall)) / precision + recall;

		System.out.println("Bei der WSDL-Suche sind die Werte: \n Recall: " + recall + "\n Precision: " + precision
				+ "\n F-Measure: " + fMeasure);

	}
	
	}
	



