package br.com.melixmen.api.models;

import static br.com.melixmen.api.utils.ObjectCreator.getStats5050;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.melixmen.api.model.Stats;

@RunWith(SpringRunner.class)
public class StatsTest {
	
	@Test
	public void testEqualsAndHashCodeSymmetric() {
		Stats stats1 = getStats5050();
		Stats stats2 = getStats5050();
		
		assertThat(stats1.equals(stats2)).isTrue();
		assertThat(stats1.hashCode()).isEqualTo(stats2.hashCode());
		assertThat(stats1.toString()).isEqualTo(stats2.toString());
	}

}
