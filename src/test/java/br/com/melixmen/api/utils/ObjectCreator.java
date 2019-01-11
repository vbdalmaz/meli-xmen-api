package br.com.melixmen.api.utils;


import java.util.ArrayList;
import java.util.List;

import br.com.melixmen.api.model.DNA;
import br.com.melixmen.api.model.Stats;
import br.com.melixmen.api.model.enumeration.SpecieType;;

public class ObjectCreator {
	
	public static DNA createDNASample(String dna[], SpecieType specieType){
		return new DNA(dna, specieType);
	}
	
	public static DNA getMutantDNASample(){
		String mutantDNASample[] = new String[] {"AAAAAA","CCCCGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
		return createDNASample(mutantDNASample, SpecieType.MUTANT);
	}
	
	public static DNA getNoMutantDNASample(){
		String humanDNASample[] = new String[] {"TTGCGA","CAGTGC","TTATGT","AGAATG","CCCCTA","TCACTG"};
		return createDNASample(humanDNASample, SpecieType.HUMAN);
	}
	
	public static DNA getMutantDNASample2DNASVertically(){
		String mutantDNASample[] = new String[] {"ACCCGC","ACCCGC","ACATGT","ACAATG","TCCCTA","TCACTG"};
		return createDNASample(mutantDNASample, SpecieType.MUTANT);
	}
	
	public static DNA getMutantDNASample2DNASHorizontally(){
		String mutantDNASample[] = new String[] {"AAAACT","CCCCAT","ACATGT","ACAAGG","TCCCTA","TCACTG"};
		return createDNASample(mutantDNASample, SpecieType.MUTANT);
	}
	
	public static DNA getMutantDNASample2DNASDiagonally(){
		String mutantDNASample[] = new String[] {"AGTA","GAAG","AAAG","AGGA"};
		return createDNASample(mutantDNASample, SpecieType.MUTANT);
	}
	
	public static List<DNA> getDNAList(){
		List<DNA> dnaList = new ArrayList<>();
		dnaList.add(getNoMutantDNASample());
		dnaList.add(getMutantDNASample());
		
		return dnaList;
	}
	
	public static Stats createStats(long quantityMutantDna, long quantityHumanDna) {
		return new Stats(quantityMutantDna, quantityHumanDna);
	}
	
	public static Stats getStatsAllMutants() {
		return createStats(100, 100);
	}
	
	public static Stats getStatsNoMutants() {
		return createStats(0, 100);
	}
	
	public static Stats getStats5050() {
		return createStats(50, 100);
	}
	
	public static Stats getEmptyStats() {
		return createStats(0, 0);
	}
	
}
