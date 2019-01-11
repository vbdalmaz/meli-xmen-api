package br.com.melixmen.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.melixmen.api.model.DNA;
import br.com.melixmen.api.model.enumeration.SpecieType;

public interface DNARepository extends MongoRepository<DNA, String> {
	
	DNA findByDna(String[] dna);
	Long countBySpecieType(SpecieType specieType);
	List<DNA> findAll();
	
}