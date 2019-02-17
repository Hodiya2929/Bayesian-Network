import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Unnecessary {

	public static List<Var> removeUnnecessaryNodes(Question q,List<Var> graph) {

		//exist will contain all nodes that are relevant, in the end of this algorithm each node that is not in it is unrelevant
		Set<String> exist=new TreeSet<>();

		Queue<Var> queue=new LinkedList<>();

		//add all question and evidence variables to a queue
		for (Var var : q.questionVars) 
			queue.add(var);

		//while there are still vars in queue add their parents and remove them
		while (!queue.isEmpty()) {

			Var temp=queue.remove();

			queue.addAll(temp.parents);

			exist.add(temp.name);

		}
		
		List<Var> fromStringToVar=new ArrayList<>();
		
		for (String varName : exist) {
			for (Var var : graph) {
				if(varName.equals(var.name))
					fromStringToVar.add(var);
			}
		}
		
		return fromStringToVar;

	}	

}




