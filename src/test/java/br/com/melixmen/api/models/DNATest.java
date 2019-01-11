package br.com.melixmen.api.models;

import static br.com.melixmen.api.utils.ObjectCreator.getMutantDNASample;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.melixmen.api.model.DNA;

@RunWith(SpringRunner.class)
public class DNATest {
	
	@Test
	public void testEqualsAndHashCodeSymmetric() {
		DNA dnaSample1 = getMutantDNASample();
		DNA dnaSample2 = getMutantDNASample();
		
		assertThat(dnaSample1.equals(dnaSample2)).isTrue();
		assertThat(dnaSample1.hashCode()).isEqualTo(dnaSample2.hashCode());
		assertThat(dnaSample1.toString()).isEqualTo(dnaSample2.toString());
	}
}
