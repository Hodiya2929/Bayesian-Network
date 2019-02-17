import java.util.ArrayList;
import java.util.Enumeration;

public class BayesianGraph {

	ArrayList<String> fileInArrayList;

	ArrayList<Integer> variableIndexes;

	ArrayList<String> varNames;

	int queriesStart;

	ArrayList<Var> graph;

	ArrayList<String> queries;

	public BayesianGraph(ArrayList<String> fileInArrayList, ArrayList<String> varNames,ArrayList<Integer> variableIndexes
			,int queriesStart) {

		this.fileInArrayList = fileInArrayList;
		this.varNames = varNames;
		this.variableIndexes=variableIndexes;
		this.queriesStart = queriesStart;
		graph=new ArrayList<>(varNames.size());
		queries=new ArrayList<>();

	}

	public void initVarsName() {

		for (int i = 0; i < varNames.size(); i++) {

			Var var=new Var(varNames.get(i));
			graph.add(var);

		}
		initEachVairable();


	}

	private void initEachVairable() {

		for (int i=0;i<variableIndexes.size()-1;i++) {

			String thisVar=fileInArrayList.get(variableIndexes.get(i));

			for(int j=variableIndexes.get(i);j<variableIndexes.get(i+1);j++)
			{

				for (int r=0;r<graph.size();r++) {

					if(thisVar.contains(graph.get(r).name)) {

						if(fileInArrayList.get(j).contains("Values:"))
						{
							String values=fileInArrayList.get(j).substring(7);
							String allValues[]=values.split(",");

							for (int k = 0; k < allValues.length; k++) 
								graph.get(r).values.add(allValues[k].trim());

						}
						if(fileInArrayList.get(j).contains("Parents:")) {

							String parents=fileInArrayList.get(j).substring(8);
							String parentNames[]=parents.split("[,]");


							for (int k = 0; k < parentNames.length; k++) {
								parentNames[k]=parentNames[k].trim();

							}

							if(!parentNames[0].equals("none"))
								for(int g=0;g<parentNames.length;g++) 
									for (Var node : graph) 
										if(node.name.equals(parentNames[g]))
											graph.get(r).parents.add(node);
						}

						if(fileInArrayList.get(j).toUpperCase().contains("CPT:"))
						{

							for(int d=j+1;d<variableIndexes.get(i+1);d++)
							{

								if(!fileInArrayList.get(d).equals(""))
								{
									String temp =fileInArrayList.get(d);
									String temp2[]=temp.split(",");

									for (int k = 0; k < temp2.length; k++) {
										temp2[k]=temp2[k].trim();
										if(temp2[k].contains("="))
											temp2[k]=temp2[k].substring(1);
									}

									double sum=0;
									int numOfParents=graph.get(r).parents.size();

									StringBuilder builder=new StringBuilder();

									for (int a=0; a<numOfParents;a++) {
										builder.append(graph.get(r).parents.get(a).name);
										builder.append(",");
										builder.append(temp2[a]);
										builder.append(",");
									}

									builder.append(graph.get(r).name);
									builder.append(",");
									
									for(int b=numOfParents;b<temp2.length;b+=2) {
										sum+=Double.parseDouble(temp2[b+1]);
										graph.get(r).cpt.put(builder.toString()+temp2[b], Double.parseDouble(temp2[b+1]));
									}

									graph.get(r).cpt.put(builder.toString()+graph.get(r).values.get(graph.get(r).values.size()-1),1-sum);



								}

							}


						}

					}
				}
			}

		}


		this.proccessingTheQueries();
	}

	public String toString() {

		StringBuilder builder=new StringBuilder();

		for (Var node :graph) {

			builder.append(node.name+"\n"
					+ "Values: ");
			for (String value : node.values) {
				builder.append(value+" ");	
			}
			builder.append("\nParents:");
			for (Var var : node.parents) {
				builder.append(var.name+" ");
			}

			builder.append("\nCPT:\n");
			Enumeration<String> keys = node.cpt.keys();

			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				builder.append(key+"  ");
				builder.append(node.cpt.get(key));
				builder.append("\r\n");
			}


		}


		return builder.toString();
	}


	public void proccessingTheQueries(){

		for (int i=queriesStart+1; i<fileInArrayList.size(); i++) {

			queries.add(fileInArrayList.get(i));
		}

	}

}

