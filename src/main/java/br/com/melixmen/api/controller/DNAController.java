package br.com.melixmen.api.controller;

import static br.com.melixmen.api.utils.MeliXmenStringConfig.ALL;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.DNA_BASE_URL;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.DNA_SERVICE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.STATS;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.melixmen.api.model.DNA;
import br.com.melixmen.api.model.Stats;
import br.com.melixmen.api.service.DNAService;

@RestController
public class DNAController extends BaseController{

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private DNAService dnaService;
	
	@RequestMapping(path = DNA_BASE_URL + ALL, method = { GET }, produces = {APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<List<DNA>> getAllDNA() {
		LOG.info("Getting all dnas");

		return createResponse(dnaService.getAllDNAs());
	}
	
	@RequestMapping(path = DNA_BASE_URL + STATS, method = { GET }, produces = {APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<Stats> getStats() {
		LOG.info("Getting stats");

		return createResponse(dnaService.getStats());
	}

	@RequestMapping(path = DNA_BASE_URL + DNA_SERVICE, method = { POST }, produces = { APPLICATION_JSON_UTF8_VALUE }, consumes = {
			APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<?> postDNA(@RequestBody @NotBlank DNA dna) {
		LOG.info("Checking and saving dna : {}", dna);
		
		if (dnaService.isMutant(dna.getDna())){
			return createResponse200();
		}
		
		return createResponse403();
	}
}
