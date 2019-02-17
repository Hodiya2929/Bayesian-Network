import java.text.DecimalFormat;
import java.util.List;

public class AlgorithmNum1 implements SolvingAQuery {

	StringBuilder finalAnswer;
	int plusCounter;
	int multiplyCounter;
	double firstAnswer=1;
	double firstAnswer2=0;
	double normal;
	double normal2;


	@Override
	public String answer(List<List<Var>> fullQuestion, int mark) {

		plusCounter=fullQuestion.size()-1;
		multiplyCounter=(fullQuestion.get(0).size()-1)*(plusCounter+1);

		for (int i = 0; i < mark; i++) {
			firstAnswer=1;

			for (int j = 0; j < fullQuestion.get(i).size(); j++) 
				firstAnswer*=	help(fullQuestion.get(i),fullQuestion.get(i).get(j));


			firstAnswer2+=firstAnswer;
		}


		for (int i = mark; i < fullQuestion.size(); i++) {
			normal=1;

			for (int j = 0; j < fullQuestion.get(i).size(); j++) 
				normal*=	help(fullQuestion.get(i),fullQuestion.get(i).get(j));

			normal2+=normal;

		}

		DecimalFormat df = new DecimalFormat("0.00000");

		finalAnswer=new StringBuilder();
		finalAnswer.append(	df.format(firstAnswer2/(firstAnswer2+normal2)));
		finalAnswer.append(',');
		finalAnswer.append(plusCounter);
		finalAnswer.append(',');
		finalAnswer.append(multiplyCounter);
		return finalAnswer.toString();
	}


	public static double help (List<Var> list,Var current) {

		StringBuilder build=new StringBuilder();

		if(!current.parents.isEmpty()) {
			for (Var parent : current.parents) {
				for (Var varList : list) {
					if (parent.name.equals(varList.name))
						build.append(varList.name+","+varList.reqValue+",");

				}
			}
		}
		build.append(current.name+","+current.reqValue);

		return	current.cpt.get(build.toString());	
	}

}


