package br.com.melixmen.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.melixmen.api.controller.DNAController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeliXmenAPIJavaApplicationTest {
	
    @Autowired
    private DNAController controller;
    
    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}