import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Var {

	String name;

	ArrayList<String> values;

	ArrayList<Var> parents;

	Hashtable<String,Double> cpt;

	String reqValue;	

	public Var(String name) {
		this.name = name;
		values=new ArrayList<>();
		parents=new ArrayList<>();
		cpt=new Hashtable<>();
		reqValue=null;
	}


	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();


		builder.append("\nCPT:\n");
		Enumeration<String> keys = this.cpt.keys();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			builder.append(key+"  ");
			builder.append(this.cpt.get(key));
			builder.append("\r\n");
		}

		return builder.toString();

	}

public Var(Var other, String value) {
	this.name=other.name;
	this.values=other.values;
	this.parents=other.parents;
	this.reqValue=value;
	this.cpt=other.cpt;
}




}
