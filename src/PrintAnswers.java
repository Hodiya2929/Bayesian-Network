
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;


public class PrintAnswers {

	List<String> answers;


	public PrintAnswers(List<String> answers) {
		this.answers=answers;
	}


	public void print() {

		try {

			FileWriter writer = new FileWriter("output.txt"); 
			for(String str: answers)
			{
				writer.write(str);
				writer.write("\r\n");

			}
			writer.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

}

