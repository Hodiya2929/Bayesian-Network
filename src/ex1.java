

public class ex1 {

	public static void main(String[] args) {

		ReadingTheSource fileReading=new ReadingTheSource();

		ExploringTheFile exploring=new ExploringTheFile(fileReading.StartTheFileReading());	

		exploring.startExplore();

		BayesianGraph theGraph=new BayesianGraph(exploring.fileInArrayList, exploring.varNames,exploring.varIndexes, exploring.queriesStart) ;

		
		theGraph.initVarsName();

		Queries queries=new Queries(theGraph.queries, theGraph.graph);

		queries.extablishQueries();

		CartesianProduct cartesian=new CartesianProduct(queries.questions,theGraph.graph);

		cartesian.allValuesFirstVar();

		PrintAnswers printer=new PrintAnswers(cartesian.answers);

		printer.print();

	}




}
