package br.com.melixmen.api.service.impl;

import static br.com.melixmen.api.utils.MeliXmenStringConfig.GET_ALL_DNAS_CACHE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.GET_STATS_CACHE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.MUTANT_DNA_SEQUENCE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.VALID_LETTERS_PATTERN;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import br.com.melixmen.api.exception.DNAInvalidException;
import br.com.melixmen.api.model.DNA;
import br.com.melixmen.api.model.Stats;
import br.com.melixmen.api.model.enumeration.SpecieType;
import br.com.melixmen.api.repository.DNARepository;
import br.com.melixmen.api.service.DNAService;

@Service
public class DNAServiceImpl implements DNAService {

	private static final Logger LOG = LoggerFactory.getLogger(DNAServiceImpl.class);

	private char[][] dnaValidSample;
	private 	Set<String> mutantSequenceMatchPosition; 

	@Autowired
	DNARepository dnaRepository;
	
	@Autowired
	CacheManager cacheManager;
	
	@Override
	public Boolean isMutant(String[] dnaSample) {
		DNA sample = getDNA(dnaSample);
		
		if(sample == null) {
			mutantSequenceMatchPosition  = new HashSet<>();
			sample = new DNA(dnaSample, identifySpecie(dnaSample));
	
			saveAndRefreshCache(sample);
		}

		return sample.getSpecieType() == SpecieType.MUTANT;
	}
	
	@Override
	public DNA getDNA(String[] dnaSample) {
		return dnaRepository.findByDna(dnaSample);
	}

	@Override
	public DNA saveDNA(DNA dna) {
		return dnaRepository.save(dna);
	}

	@Override
	public List<DNA> getAllDNAs() {
		return dnaRepository.findAll();
	}

	@Override
	public Stats getStats() {
		return new Stats(dnaRepository.countBySpecieType(SpecieType.MUTANT),
				dnaRepository.count());
	}

	private void saveAndRefreshCache(DNA dnaSample) {
		LOG.info("Refreshing getting all mutants and Stats cache");
		CompletableFuture.runAsync(() -> {
			if(getDNA(dnaSample.getDna()) == null ) {
				cacheManager.getCache(GET_STATS_CACHE).clear();
				cacheManager.getCache(GET_ALL_DNAS_CACHE).clear();
				
				saveDNA(dnaSample);
				getAllDNAs();
				getStats();
			}
		});
	}

	private SpecieType identifySpecie(String[] dnaSample) {
		int dnaSampleColumnsQuantity = dnaSample.length;
		int dnaSampleLineQuantity = dnaSample[0].length();
		int matches = 0;

		readValidateDNASample(dnaSample);
		
		for (int dnaSampleRow = 0; dnaSampleRow < dnaSampleLineQuantity; dnaSampleRow++)
			for (int dnaSampleColumn = 0; dnaSampleColumn < dnaSampleColumnsQuantity; dnaSampleColumn++)
				for (int dnaSampleRowDelta = -1; dnaSampleRowDelta <= 1; dnaSampleRowDelta++)
					for (int dnaSampleColumnDelta = -1; dnaSampleColumnDelta <= 1; dnaSampleColumnDelta++)
						if (dnaSampleRowDelta != 0 || dnaSampleColumnDelta != 0) {
							matches += findMuntantSequence(dnaSampleLineQuantity, dnaSampleColumnsQuantity, dnaSampleRow, dnaSampleColumn, dnaSampleRowDelta, dnaSampleColumnDelta);
							if(matches >=2) {
								return SpecieType.MUTANT;
							}
						}

		return SpecieType.HUMAN;
	}

	private void readValidateDNASample(String[] dnaSample) {
		int dnaSampleColumnsQuantity = dnaSample.length;
		int dnaSampleLineQuantity = dnaSample[0].length();
		int line = 0;

		dnaValidSample = new char[dnaSampleColumnsQuantity][dnaSampleLineQuantity];

		for (String dnaLine : dnaSample) {
			if (dnaSampleLineQuantity != dnaLine.length()) {
				throw new DNAInvalidException("Invalid Structure in line: " + line);
			}

			if (!dnaLine.matches(VALID_LETTERS_PATTERN + "{" + dnaSampleLineQuantity + "}")) {
				throw new DNAInvalidException(
						"Invalid DNA Composition found in line: " + line + " Composition: " + dnaLine);
			}

			dnaValidSample[line++] = dnaLine.toCharArray();
		}
	}

	private int findMuntantSequence(int dnaSampleLineQuantity, int dnaSampleColumnsQuantity, int dnaSampleRow, int dnaSampleColumn, int dnaSampleRowDelta, int dnaSampleColumnDelta) {
		String dnaSequence = "" + dnaValidSample[dnaSampleRow][dnaSampleColumn];;
		int searchResult, dnaMutantMatches = 0;
		
		for (int i = dnaSampleRow + dnaSampleRowDelta, j = dnaSampleColumn + dnaSampleColumnDelta; i >= 0 && j >= 0 && i < dnaSampleLineQuantity
				&& j < dnaSampleColumnsQuantity; i += dnaSampleRowDelta, j += dnaSampleColumnDelta) {
			
			dnaSequence += dnaValidSample[i][j];
			int index = Arrays.binarySearch(MUTANT_DNA_SEQUENCE, dnaSequence);
			searchResult = index < 0 ? -index - 1 : index;
			
			if(searchResult == MUTANT_DNA_SEQUENCE.length )
                break;			

			if (!((String) MUTANT_DNA_SEQUENCE[searchResult]).startsWith(dnaSequence))
				break;

			if (MUTANT_DNA_SEQUENCE[searchResult].equals(dnaSequence) && !containsMatch(dnaSampleRow,dnaSampleColumn,i,j)) {
				LOG.info("Mutant Dna found " + dnaSequence + " at " + dnaSampleRow + " " + dnaSampleColumn + " to " + i + " " + j);
				dnaMutantMatches++;
				mutantSequenceMatchPosition.add("" + dnaSampleRow + dnaSampleColumn + i + j);
				
			}
		}

		return dnaMutantMatches;
	}
	
	private Boolean containsMatch(int dnaSampleRow,int dnaSampleColumn,int i, int j) {
		return mutantSequenceMatchPosition.contains("" + dnaSampleRow + dnaSampleColumn + i + j) ||
				mutantSequenceMatchPosition.contains("" + i + j + dnaSampleRow +  dnaSampleColumn);
		
	}
}
