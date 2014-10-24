package hr.bio.project.bloomfilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FastaFileReader {
	private String declaration;
	private String fastaFile;
	
	public FastaFileReader(String fastaFile) {
		this.fastaFile = fastaFile;
	}
	
	public String getFastaString() {
		BufferedReader input = null;
		String fastaLine;
		List<String> lista = new ArrayList<String>();
		StringBuilder fastaAllBuilder = new StringBuilder();
		
		try {
			input= new BufferedReader(new FileReader(this.fastaFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			while ((fastaLine = input.readLine()) != null) {
				lista.add(fastaLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		declaration = lista.get(0);
		lista.remove(0);
		
		for (String s : lista) {
			fastaAllBuilder.append(s);
		}
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fastaAllBuilder.toString();
	}
}
