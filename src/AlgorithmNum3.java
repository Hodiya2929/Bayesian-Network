import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlgorithmNum3  {

	public static List<Var> OrderByBestHueristic(List<Var> listOfVarToEliminate, List<Var> graph) {

		class VarNeighbors{

			String name;
			int numOfNeighborss;

			public VarNeighbors(String name,int num) {
				this.name=name;
				numOfNeighborss=num;
			}	
		}

		List<VarNeighbors> vars=new ArrayList<>();

		for (Var var : listOfVarToEliminate) 
			vars.add(new VarNeighbors(var.name,0));

		for (Var var : graph) {
			for (Var parent : var.parents) {
				for (VarNeighbors neighbor : vars) {
					if(parent.name.equals(neighbor.name))
						neighbor.numOfNeighborss++;
				}
			}
		}
	
		vars.sort(new Comparator<VarNeighbors>() {

			@Override
			public int compare(VarNeighbors o1, VarNeighbors o2) {
				if(o1.numOfNeighborss>o2.numOfNeighborss)
					return 1;
				else if(o1.numOfNeighborss<o2.numOfNeighborss)
					return -1;

				return 0;
			}
		});

		List<Var> newOrder=new ArrayList<>();

		for (VarNeighbors varNeighbors : vars) {
			for (Var var : listOfVarToEliminate) {
				if(varNeighbors.name.equals(var.name))
					newOrder.add(var);
			}
		}
	
		
		return newOrder;
	}	
}



