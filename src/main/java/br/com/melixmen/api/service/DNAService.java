package br.com.melixmen.api.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import br.com.melixmen.api.model.DNA;
import br.com.melixmen.api.model.Stats;

import static br.com.melixmen.api.utils.MeliXmenStringConfig.GET_ALL_DNAS_CACHE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.GET_DNA_CACHE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.GET_STATS_CACHE;

public interface DNAService {
	
	@Cacheable(cacheNames = GET_DNA_CACHE)
	public Boolean isMutant(String[] dna);
	
	@Cacheable(cacheNames = GET_DNA_CACHE) 
	public DNA saveDNA(DNA dna);
	
	@Cacheable(cacheNames = GET_DNA_CACHE) 
	public DNA getDNA(String[] dnaSample);
	
	@Cacheable(cacheNames = GET_ALL_DNAS_CACHE)
	public List<DNA> getAllDNAs();
	
	@Cacheable(cacheNames = GET_STATS_CACHE)
	public Stats getStats();
}
