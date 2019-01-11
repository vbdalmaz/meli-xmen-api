package br.com.melixmen.api.service;

import static br.com.melixmen.api.utils.MeliXmenStringConfig.GET_ALL_DNAS_CACHE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.GET_DNA_CACHE;
import static br.com.melixmen.api.utils.MeliXmenStringConfig.GET_STATS_CACHE;
import static br.com.melixmen.api.utils.ObjectCreator.getDNAList;
import static br.com.melixmen.api.utils.ObjectCreator.getMutantDNASample2DNASDiagonally;
import static br.com.melixmen.api.utils.ObjectCreator.getMutantDNASample2DNASHorizontally;
import static br.com.melixmen.api.utils.ObjectCreator.getMutantDNASample2DNASVertically;
import static br.com.melixmen.api.utils.ObjectCreator.getNoMutantDNASample;
import static br.com.melixmen.api.utils.ObjectCreator.getStats5050;
import static br.com.melixmen.api.utils.ObjectCreator.getStatsAllMutants;
import static br.com.melixmen.api.utils.ObjectCreator.getStatsNoMutants;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.melixmen.api.exception.DNAInvalidException;
import br.com.melixmen.api.repository.DNARepository;
import br.com.melixmen.api.service.impl.DNAServiceImpl;

@RunWith(SpringRunner.class)
public class DNAServiceImplTest {
	
    @Configuration
    @EnableCaching
    static class Config {
      @Bean
      CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(GET_ALL_DNAS_CACHE,GET_DNA_CACHE,GET_STATS_CACHE);
      }
      
    }
    
    @TestConfiguration
    static class DNAServiceImplTestContextConfiguration {
  
    		@Bean
    		@Qualifier("dnaServiceImpl")
        public DNAService dnaService() {
            return new DNAServiceImpl();
        }
    }
	
    @MockBean
    private DNARepository dnaRepository;
    
    @Autowired 
    CacheManager cacheManager;
    
    @Autowired
    private DNAService dnaService;
    
	@Test
	public void shouldReturnAList() throws Exception {
		when(dnaRepository.findAll()).thenReturn(getDNAList());
		
		assertThat(dnaService.getAllDNAs()).isEqualTo(getDNAList());
	}
	
	@Test
	public void shouldCallFindAllOnceTestingCache() throws Exception {
		when(dnaRepository.findAll()).thenReturn(getDNAList());
		cacheManager.getCache(GET_ALL_DNAS_CACHE).clear();
		
		assertThat(dnaService.getAllDNAs()).isEqualTo(getDNAList());
		assertThat(dnaService.getAllDNAs()).isEqualTo(getDNAList());
		assertThat(dnaService.getAllDNAs()).isEqualTo(getDNAList());
		assertThat(dnaService.getAllDNAs()).isEqualTo(getDNAList());
		assertThat(dnaService.getAllDNAs()).isEqualTo(getDNAList());
		
		verify(dnaRepository, times(1)).findAll();
	}
	
	@Test
	public void shouldReturnAnAllMutantsStats() throws Exception {
		when(dnaRepository.countBySpecieType(any())).thenReturn(100l);
		when(dnaRepository.count()).thenReturn(100l);
		
		assertThat(dnaService.getStats()).isEqualTo(getStatsAllMutants());
	}
	
	@Test
	public void shouldReturnAnNoMutantsStats() throws Exception {
		when(dnaRepository.countBySpecieType(any())).thenReturn(0l);
		when(dnaRepository.count()).thenReturn(100l);
		
		assertThat(dnaService.getStats()).isEqualTo(getStatsNoMutants());
	}
	
	@Test
	public void shouldReturnAn5050Stats() throws Exception {
		when(dnaRepository.countBySpecieType(any())).thenReturn(50l);
		when(dnaRepository.count()).thenReturn(100l);
		
		assertThat(dnaService.getStats()).isEqualTo(getStats5050());
	}
	
	@Test
	public void shouldCallCountAndCountBySpecieTypeOnceTestingCache() throws Exception {
		when(dnaRepository.countBySpecieType(any())).thenReturn(100l);
		when(dnaRepository.count()).thenReturn(100l);
		
		cacheManager.getCache(GET_STATS_CACHE).clear();
		
		assertThat(dnaService.getStats()).isEqualTo(getStatsAllMutants());
		assertThat(dnaService.getStats()).isEqualTo(getStatsAllMutants());
		assertThat(dnaService.getStats()).isEqualTo(getStatsAllMutants());
		assertThat(dnaService.getStats()).isEqualTo(getStatsAllMutants());
		assertThat(dnaService.getStats()).isEqualTo(getStatsAllMutants());
		
		
		verify(dnaRepository, times(1)).count();
		verify(dnaRepository, times(1)).countBySpecieType(any());
	}
	
	
	@Test
	public void shouldReturnTrueBecauseIsAMutantSample2Vertically() throws Exception {
		assertThat(dnaService.isMutant(getMutantDNASample2DNASVertically().getDna())).isTrue();
		
	}
	@Test
	public void shouldReturnTrueBecauseIsAMutantSample2Diagonally() throws Exception {
		assertThat(dnaService.isMutant(getMutantDNASample2DNASDiagonally().getDna())).isTrue();
	}
	
	@Test
	public void shouldReturnTrueBecauseIsAMutantSample2Horizontally() throws Exception {
		assertThat(dnaService.isMutant(getMutantDNASample2DNASHorizontally().getDna())).isTrue();
	}
	
	@Test
	public void shouldReturnFalseBecauseIsANoMutantSample() throws Exception {
		assertThat(dnaService.isMutant(getNoMutantDNASample().getDna())).isFalse();
	}
	
	@Test(expected = DNAInvalidException.class)
	public void shouldThrowAnExceptionDueAnInvalidLetter() throws Exception {
		String invalidDNA[] = new String[] {"AGCGAA","CAGTGC","TTATGT","AGAAGG","CCCCTA","ATCACZ"};
		
		dnaService.isMutant(invalidDNA);
	}
	
	@Test(expected = DNAInvalidException.class)
	public void shouldThrowAnExceptionDueAnInvalidComposition() throws Exception {
		String invalidDNA[] = new String[] {"AGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACT"};
		
		dnaService.isMutant(invalidDNA);
	}
	    
}
