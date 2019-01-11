package br.com.melixmen.api.controller;

import static br.com.melixmen.api.utils.MeliXmenStringConfig.ALL;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.DNA_BASE_URL;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.DNA_SERVICE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.STATS;
import static br.com.melixmen.api.utils.ObjectCreator.getDNAList;
import static br.com.melixmen.api.utils.ObjectCreator.getEmptyStats;
import static br.com.melixmen.api.utils.ObjectCreator.getNoMutantDNASample;
import static br.com.melixmen.api.utils.ObjectCreator.getMutantDNASample;
import static br.com.melixmen.api.utils.ObjectCreator.getStats5050;
import static br.com.melixmen.api.utils.ObjectCreator.getStatsNoMutants;
import static br.com.melixmen.api.utils.ObjectCreator.getStatsAllMutants;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.melixmen.api.exception.DNAInvalidException;
import br.com.melixmen.api.repository.DNARepository;
import br.com.melixmen.api.service.DNAService;

@RunWith(SpringRunner.class)
@WebMvcTest(DNAController.class)
public class DNAControllerTest {

	@MockBean
	@Qualifier("dnaService")
	private DNAService service;
	
	@MockBean
	@Qualifier("dnaRepository")
	private DNARepository dnaRepository;
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void shouldReturnAJsonListWithStatusOk() throws Exception {
		when(service.getAllDNAs()).thenReturn(getDNAList());

		mvc.perform(get(DNA_BASE_URL + ALL).contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void shouldReturnStatus204DueToAEmptyList() throws Exception {
		when(service.getAllDNAs()).thenReturn(null);

		mvc.perform(get(DNA_BASE_URL + ALL).contentType(APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNoContent());
	}

	@Test
	public void shouldReceiveAEmptyStats() throws Exception {
		when(service.getStats()).thenReturn(getEmptyStats());

		mvc.perform(get(DNA_BASE_URL + STATS).contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.count_mutant_dna", is(0))).andExpect(jsonPath("$.count_human_dna", is(0)))
				.andExpect(jsonPath("$.ratio", is(0.0)));
	}

	@Test
	public void shouldReceiveAnAllMutantsStats() throws Exception {
		when(service.getStats()).thenReturn(getStatsAllMutants());

		mvc.perform(get(DNA_BASE_URL + STATS).contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.count_mutant_dna", is(100))).andExpect(jsonPath("$.count_human_dna", is(100)))
				.andExpect(jsonPath("$.ratio", is(1.0)));
	}

	@Test
	public void shouldReceiveAnAllNoMutantsStats() throws Exception {
		when(service.getStats()).thenReturn(getStatsNoMutants());

		mvc.perform(get(DNA_BASE_URL + STATS).contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.count_mutant_dna", is(0))).andExpect(jsonPath("$.count_human_dna", is(100)))
				.andExpect(jsonPath("$.ratio", is(0.0)));
	}

	@Test
	public void shouldReceiveA5050Stats() throws Exception {
		when(service.getStats()).thenReturn(getStats5050());

		mvc.perform(get(DNA_BASE_URL + STATS).contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.count_mutant_dna", is(50))).andExpect(jsonPath("$.count_human_dna", is(100)))
				.andExpect(jsonPath("$.ratio", is(0.5)));
	}

	@Test
	public void shouldReceiveA200BecauseIsAMutantDNA() throws Exception {
		String[] dnaSampleOfAMutant = getMutantDNASample().getDna();

		when(service.isMutant(dnaSampleOfAMutant)).thenReturn(true);

		mvc.perform(post(DNA_BASE_URL + DNA_SERVICE).content("{ \"Dna\" : [\"AAAAAA\",\"CCCCGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}")
				.contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}
	
	@Test
	public void shouldReceiveA403BecauseIsNotAMutant() throws Exception {
		String[] dnaSampleOfAHuman = getNoMutantDNASample().getDna();

		when(service.isMutant(dnaSampleOfAHuman)).thenReturn(false);

		mvc.perform(post(DNA_BASE_URL + DNA_SERVICE).content("{ \"Dna\" : [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}")
				.contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isForbidden());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldReceiveANErrorDueAInvalidStructure()  throws Exception {
		String invalidDNA[] = new String[] {"AGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACZ"};

		when(service.isMutant(invalidDNA)).thenThrow(DNAInvalidException.class);

		mvc.perform(post(DNA_BASE_URL + DNA_SERVICE).content("{ \"Dna\" : [\"AGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACZ\"]}")
				.contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isBadRequest());
	}
}
