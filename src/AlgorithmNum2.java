import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


public class AlgorithmNum2{

	public static int multiplyCounter;
	public static int sumCounter;

	public static String variableElimination(Question q, List<Var> graph,int num,String requested) {
		
		sumCounter=0;
		multiplyCounter=0;
		
		//A list of all node which are necessary to the question
		List<Var> releventNodes=new ArrayList<Var>(Unnecessary.removeUnnecessaryNodes(q, graph));

		//A list which contains the factors of the question
		List<Hashtable<String, Double>> listOfFactors=new ArrayList<>();

		//Add the relevant CPT=factors to the array and removing unrelated evidence data 
		addReleventFactors(releventNodes,listOfFactors,q.questionVars);


		//Contains list of vars to eliminate
		List<Var> listOfVarToEliminate=FindingVarsToEliminate(releventNodes,q.questionVars);

		listOfVarToEliminate=determingOrderOfElimination(listOfVarToEliminate,releventNodes,num);
	
		Elimination(listOfFactors,listOfVarToEliminate);

		multiplyQueryAndEvidenceFactors(listOfFactors);

		double answer=normalization(listOfFactors.get(0),q.questionVars,requested);

		StringBuilder finalAnswer=new StringBuilder();

		DecimalFormat ds = new DecimalFormat("0.00000");
		finalAnswer.append(	ds.format(answer));
		finalAnswer.append(',');
		finalAnswer.append(sumCounter);
		finalAnswer.append(',');
		finalAnswer.append(multiplyCounter);
		return finalAnswer.toString();

	}

	private static double normalization(Hashtable<String, Double> hashtable,List<Var> q,String requested) {



		double answer=0;
		double sum=0;

		String temp;
		Enumeration<String> keys=hashtable.keys();

		while(keys.hasMoreElements()) {

			temp=keys.nextElement();
			String[] tempArray=temp.split(",");
			for (int i = 0; i < tempArray.length; i+=2) {
				if(tempArray[i].equals(q.get(0).name)&&tempArray[i+1].equals(requested))
					answer=hashtable.get(temp);

			}
			sum+=hashtable.get(temp);

		}
		sumCounter+=hashtable.size()-1;

		return (answer/sum);
	}

	private static void multiplyQueryAndEvidenceFactors(List<Hashtable<String, Double>> listOfFactors) {

		while(listOfFactors.size()>1) {

			sortBySizeOfHash(listOfFactors);
			listOfFactors.add(multiplyTwoFactors(listOfFactors.get(0), listOfFactors.get(1)));
			listOfFactors.remove(0);
			listOfFactors.remove(0);
			sortBySizeOfHash(listOfFactors);

		}
	}

	private static void Elimination(List<Hashtable<String, Double>> listOfFactors, List<Var> listOfVarToEliminate) {

		for (int i = 0; i <listOfVarToEliminate.size(); i++) {
			partOneOfElimination(listOfFactors,listOfVarToEliminate.get(i));
		}
	}

	private static void partOneOfElimination(List<Hashtable<String, Double>> listOfFactors, Var var) {

		List<Hashtable<String,Double>> factorsWhichContainsVar=new ArrayList<>();

		String varName=var.name;

		for (int i = 0; i < listOfFactors.size(); i++) {

			Enumeration<String> keys=listOfFactors.get(i).keys();
			String key=keys.nextElement();
			String temp[]=key.split(",");
			for (int j = 0; j < temp.length; j+=2) {
				if(temp[j].equals(varName)) {

					factorsWhichContainsVar.add(listOfFactors.get(i));		
				}
			}
		}

		listOfFactors.removeAll(factorsWhichContainsVar);

		listOfFactors.add(multiplyFactors(listOfFactors,factorsWhichContainsVar,var));

	}

	private static Hashtable<String,Double> multiplyFactors(List<Hashtable<String, Double>> listOfFactors,List<Hashtable<String, Double>> factorsWhichContainsVar,Var var) {

		sortBySizeOfHash(factorsWhichContainsVar);

		while(factorsWhichContainsVar.size()>1) {

			factorsWhichContainsVar.add(multiplyTwoFactors(factorsWhichContainsVar.get(0),factorsWhichContainsVar.get(1)));

			factorsWhichContainsVar.remove(0);
			factorsWhichContainsVar.remove(0);
			sortBySizeOfHash(factorsWhichContainsVar);
		};

		return sumOut(factorsWhichContainsVar.get(0),var.name);
	}

	private static Hashtable<String,Double> sumOut(Hashtable<String, Double> hashtable,String varName) {

		Hashtable<String,Double> hashAfterSummantion=new Hashtable<>();

		List<String> subtract=new ArrayList<>();

		while(!hashtable.isEmpty()) {

			Enumeration<String> keys=hashtable.keys();

			String firstKey=keys.nextElement();

			subtract.add(firstKey);

			Enumeration<String> comparingKeys=hashtable.keys();

			while(comparingKeys.hasMoreElements()) {

				String randomkey=comparingKeys.nextElement();

				if(!firstKey.equals(randomkey)) 
					if	(summantion(firstKey,randomkey,varName)) {
						subtract.add(randomkey);
						sumCounter++;

					}
			}

			double sum=0;

			for (String string : subtract) 
				sum+=hashtable.get(string);




			String answer=subtract.get(0);
			String[] arr=answer.split(",");

			StringBuilder build=new StringBuilder();

			for (int i = 0; i < arr.length; i+=2) 
				if(!arr[i].equals(varName))
					build.append(arr[i]+","+arr[i+1]+",");

			build.deleteCharAt(build.length()-1);

			hashAfterSummantion.put(build.toString(), sum);

			for (String string : subtract) {
				hashtable.remove(string);
			}

			subtract.removeAll(subtract);
		}

		return hashAfterSummantion;
	}

	private static boolean summantion(String firstKey, String randomkey,String varName) {

		String[] arr1=firstKey.split(",");
		String[] arr2=randomkey.split(",");

		for (int i = 0; i < arr1.length; i+=2) {
			if(arr1[i].equals(varName))
				i+=2;
			for (int j = 0; j < arr2.length; j+=2) {

				if(arr1[i].equals(arr2[j])&&(!arr1[i+1].equals(arr2[j+1])))
					return false;
			}
		}
		return true;
	}

	private static Hashtable<String, Double> multiplyTwoFactors(Hashtable<String, Double> hash1,Hashtable<String, Double> hash2) {


		Hashtable<String,Double> newFactor=new Hashtable<>();

		Enumeration<String> keysOfHash1=hash1.keys();

		String keyHash1;
		String keyHash2;

		String test;

		while(keysOfHash1.hasMoreElements()) {

			keyHash1=keysOfHash1.nextElement();
			String[] arrayOfHash1=keyHash1.split(",");

			Enumeration<String> keysOfHash2=hash2.keys();

			while(keysOfHash2.hasMoreElements()) {

				keyHash2=keysOfHash2.nextElement();
				String[] arrayOfHash2=keyHash2.split(",");

				test=findingPerfectMatch(arrayOfHash1,arrayOfHash2);

				if(test!=null) {


					newFactor.put(test, hash1.get(keyHash1)*hash2.get(keyHash2));
					multiplyCounter++;
				}

			}
		}

		return newFactor;
	}

	private static String findingPerfectMatch(String[] arrayOfHash1, String[] arrayOfHash2) {

		List<String> concat=new ArrayList<>();

		for (int i = 0; i < arrayOfHash1.length; i+=2) {
			for (int j = 0; j < arrayOfHash2.length; j+=2) {

				if(arrayOfHash1[i].equals(arrayOfHash2[j]))
					if(!arrayOfHash1[i+1].equals(arrayOfHash2[j+1]))
						return null;
					else {
						concat.add(arrayOfHash1[i]);
						concat.add(arrayOfHash1[i+1]);

					}
			}
		}

		for (int i = 0; i < arrayOfHash1.length; i+=2) 
			if(!concat.contains(arrayOfHash1[i])) {
				concat.add(arrayOfHash1[i]);
				concat.add(arrayOfHash1[i+1]);
			}

		for (int i = 0; i < arrayOfHash2.length; i+=2) 
			if(!concat.contains(arrayOfHash2[i])) {
				concat.add(arrayOfHash2[i]);
				concat.add(arrayOfHash2[i+1]);
			}

		StringBuilder build=new StringBuilder();

		for (String string : concat) 
			build.append(string+",");

		build.deleteCharAt(build.length()-1);

		return build.toString();

	}

	private static void sortBySizeOfHash(List<Hashtable<String, Double>> factorsWhichContainsVar) {

		factorsWhichContainsVar.sort(new Comparator<Hashtable<String,Double>>() {

			@Override
			public int compare(Hashtable<String, Double> o1, Hashtable<String, Double> o2) {
				if(o1.size()<o2.size())
					return -1;
				else if(o1.size()>o2.size())
					return 1;
				else return 0;

			}});

	}

	private static List<Var> determingOrderOfElimination(List<Var> listOfVarToEliminate,List<Var> graph, int num) {

		if(num==2) {
			
			listOfVarToEliminate.sort(new Comparator<Var>() {

				@Override
				public int compare(Var o1, Var o2) {

					char[]ofO1=o1.name.toCharArray();
					char[]ofO2=o2.name.toCharArray();

					int min=Math.min(ofO1.length, ofO2.length);

					for (int h=0;h<min;h++) {

						if(ofO1[h]<ofO2[h] )
							return -1;
						else if(ofO1[h]>ofO2[h])
							return 1;
					}
					if(ofO1.length==ofO2.length)
						return 0;
					else if(ofO1.length>ofO2.length)
						return  1;
					else return -1;
				}
			});
		}

		else if (num==3) 
			listOfVarToEliminate=AlgorithmNum3.OrderByBestHueristic(listOfVarToEliminate,graph);
			
	return	listOfVarToEliminate;
		 
	}

	private static void addReleventFactors(List<Var> releventNodes, List<Hashtable<String, Double>> listOfFactors,List<Var> q) {

		String line;

		for (Var var : releventNodes) {
			listOfFactors.add(new Hashtable<String,Double>(var.cpt));
		}

		for (int i = 0; i < listOfFactors.size(); i++) {

			Hashtable<String,Double> hash=listOfFactors.get(i);

			Enumeration<String> keys=hash.keys();
			while(keys.hasMoreElements()) {

				line=keys.nextElement();

				String[] arr=line.split(",");

				for (int j = 0; j < arr.length; j+=2) {

					for (int r = 1; r <q.size() ; r++) {

						if(arr[j].equals(q.get(r).name)&&!arr[j+1].equals(q.get(r).reqValue))
							hash.remove(line);
					}

				}
			}
		}


	}
	private static List<Var> FindingVarsToEliminate(List<Var> releventNodes, List<Var> questionVars) {

		List<Var> varsToEliminate=new ArrayList<>();

		boolean isThere;

		for (Var var : releventNodes) {
			isThere=false;
			for (int i = 0; i < questionVars.size(); i++) 
				if(var.name.equals(questionVars.get(i).name)) 	
					isThere=true;
			if(!isThere)
				varsToEliminate.add(var);
		}
		return varsToEliminate;
	}
}
