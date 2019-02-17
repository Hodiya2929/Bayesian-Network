import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class ReadingTheSource {

	String fileName;
	ArrayList<String> fileInArrayList;
	File textFile; 
	BufferedReader reader;
	Scanner scanner;


	public ArrayList<String> StartTheFileReading() {


		scanner=new Scanner(System.in);
		
		System.out.println("Please insert the name of the bayesian network you want to run: ");
		
		fileName=scanner.nextLine();
		
		fileName+=".txt";


		try {

			textFile = new File(fileName);

			reader = new BufferedReader(new FileReader("resources/"+textFile));

			fileInArrayList=new ArrayList<>();

			String temp;

			while((temp=reader.readLine())!=null) 
				fileInArrayList.add(temp);

			reader.close();


		} catch (FileNotFoundException e) {

			System.out.println("Can't find the file name specified");
			e.printStackTrace();


		} catch (IOException e) {

			System.out.println("Can't read the file");
			e.printStackTrace();
		}
		
		scanner.close();

		return fileInArrayList;
	}





}
