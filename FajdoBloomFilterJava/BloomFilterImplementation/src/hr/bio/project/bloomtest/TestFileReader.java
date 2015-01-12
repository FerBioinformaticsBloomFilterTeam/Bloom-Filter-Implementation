package hr.bio.project.bloomtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestFileReader {
	private String declaration;
	private String fastaFile1;
	private String fastaFile2;
	
	public TestFileReader(String fastaFile1, String fastaFile2) {
		this.fastaFile1 = fastaFile1;
		this.fastaFile2 = fastaFile2;
	}
	
	public String getFastaString() {
		BufferedReader input = null;
		String fastaLine;
		List<String> lista = new ArrayList<String>();
		StringBuilder fastaAllBuilder = new StringBuilder();
		
		try {
			input= new BufferedReader(new FileReader(this.fastaFile1));
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
	
	public List<String> getTestList() {
		BufferedReader input = null;
		String fastaLine;
		List<String> lista = new ArrayList<String>();
		
		try {
			input= new BufferedReader(new FileReader(this.fastaFile2));
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
		
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
}
