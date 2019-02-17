import java.util.ArrayList;
import java.util.List;

public class Queries {

	List<String> queries;
	
	List<Question> questions ;
	List<Var> graph;

	public Queries(List<String> queries,List<Var> graph) {
		
		this.queries = queries;
		questions=new ArrayList<>();
		this.graph=graph;
		
	}
	
	public void extablishQueries() {
		
		for (String query : queries) {
			
			String questionLine[]=query.split("[()]+");	
			String theQuestion=questionLine[1];
			String devidingTheQuestion[]=theQuestion.split("[,|= ]+");
		
			Question q=new Question();
			
			for(int i=0;i<devidingTheQuestion.length;i+=2) {
				
				for (Var var : this.graph) {
					if(var.name.equals(devidingTheQuestion[i]))
						q.questionVars.add(new Var(var,devidingTheQuestion[i+1]));
				}
				
			}
			
			String num=questionLine[questionLine.length-1];
			
			if( num.contains("1"))
				q.algorithmMethod=1;
			else if(num.contains("2"))
				q.algorithmMethod=2;
			else
			q.algorithmMethod=3;
				
			questions.add(q);
		
		}
	}
		
}
