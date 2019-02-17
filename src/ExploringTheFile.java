import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ExploringTheFile {

	ArrayList<String> fileInArrayList;

	ArrayList<String> varNames;

	ArrayList<Integer> varIndexes;

	int queriesStart;
	int positionInFile;


	public ExploringTheFile(ArrayList<String> fileInArrayList) {

		this.fileInArrayList=fileInArrayList;
		varIndexes=new ArrayList<>();
		positionInFile=0;
	}

	public void startExplore() {

		if(isExistNetworkIdentifier())
			howManyVars();
		else 
			System.out.println("Network identifier is not exist.");

	}


	private boolean isExistNetworkIdentifier() {

		while(positionInFile<fileInArrayList.size()){

			if(fileInArrayList.get(positionInFile).equalsIgnoreCase("NETWORK")) {

				return true;
			}

			positionInFile++;
		}

		return false;
	}

	private void howManyVars() {

		while(positionInFile<fileInArrayList.size()){

			positionInFile++;//positionInFile still indicates where 'NETWORK' is.


			if(fileInArrayList.get(positionInFile).toUpperCase().contains("VARIABLES:")) {

				String vars=fileInArrayList.get(positionInFile).substring(11);

				String	varsArray[]=vars.split(",");

				for (int i = 0; i < varsArray.length; i++) {

					varsArray[i]=varsArray[i].trim();

				}

				varNames= new ArrayList<String>(Arrays.asList(varsArray));
				positionInFile++;
				break;
			}
		}

	queriesStart=variablesInexes();
	
	}


	private int variablesInexes() {
		
		int i;
		
		for(i=positionInFile ;!fileInArrayList.get(i).equalsIgnoreCase("QUERIES");i++) {

			if(fileInArrayList.get(i).length()>3&&fileInArrayList.get(i).substring(0,3).contains("Var"))
			{

				String temp=fileInArrayList.get(i).substring(3).trim();

				Iterator<String> nameIter=varNames.iterator();

				while(nameIter.hasNext())
					if(nameIter.next().equals(temp))
						varIndexes.add(i);

			}
		}
		varIndexes.add(i-1);
		return i;
	}

}







