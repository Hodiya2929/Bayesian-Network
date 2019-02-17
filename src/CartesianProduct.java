import java.util.ArrayList;
import java.util.List;

public class CartesianProduct {

	List<Question> allQuestions;
	List<Var> graph;
	List<String> answers;



	public CartesianProduct(List<Question> questions, ArrayList<Var> graph) {

		allQuestions=questions;
		this.graph=graph;
		answers=new ArrayList<>();
	}


	public void allValuesFirstVar () {

		for (Question q : allQuestions) {

			int mark;

			Var first= q.questionVars.get(0);
			String answer=first.reqValue;
			List<String> values=new ArrayList<>(first.values);

			values.remove(first.reqValue);

			List<List<Var>> fullQuestion=new ArrayList<>();

			cartesianProduct(fullQuestion, q.questionVars, graph);

			mark=fullQuestion.size();

			for (String value : values) {

				first.reqValue=value;
				cartesianProduct(fullQuestion, q.questionVars, graph);
			}

			if(q.algorithmMethod==1)
				answers.add(new AlgorithmNum1().answer(fullQuestion,mark));

			else if(q.algorithmMethod==2)
				answers.add(AlgorithmNum2.variableElimination(q, graph,2,answer));
			else answers.add(AlgorithmNum2.variableElimination(q, graph,3,answer));
	
		}
	}



	private void cartesianProduct(List<List<Var>> fullQuestion, List<Var> questionVars, List<Var> graph) {

		List<Var> missing=new ArrayList<>();

		for (Var var : graph) {

			boolean isThere=false;

			for (Var var2 : questionVars) {

				if(var.name.equals(var2.name)) 
					isThere=true;
			}


			if(!isThere) 
				missing.add(var);		
		}


		List<Var> temp=new ArrayList<>();

		for (Var var : questionVars ) {
			temp.add(var);
		}
		for (Var var : missing) {
			temp.add(var);
		}

		combine(missing,fullQuestion,temp,questionVars.size(),0);

	}

	private void combine(List<Var> missing, List<List<Var>> allPossibilities, List<Var> temp,int k,int j) {


		if(k==temp.size())
		{
			List<Var> copy=new ArrayList<>();

			for (Var var : temp) {
				copy.add(new Var(var, var.reqValue));

			}

			allPossibilities.add(copy);

		}
		else {

			for(int i=0;i<missing.get(j).values.size();i++)
			{
				temp.get(k).reqValue=missing.get(j).values.get(i);
				combine(missing, allPossibilities, temp, k+1, j+1);
			}

		}
	}

}

