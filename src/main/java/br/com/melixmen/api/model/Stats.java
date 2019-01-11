package br.com.melixmen.api.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats implements Serializable {

	private static final long serialVersionUID = 2019010901L;
	
	public Stats() {
		super();
	}
	
	public Stats(Long quantityMutantDna, Long quantityHumanDna) {
		super();
		
		this.quantityMutantDna = quantityMutantDna;
		this.quantityHumanDna = quantityHumanDna;
	}
	
	@JsonProperty("count_mutant_dna")
	private Long quantityMutantDna;
	
	@JsonProperty("count_human_dna")
	private Long quantityHumanDna;
	
	private Double ratio;

	public Long getQuantityMutantDna() {
		return quantityMutantDna;
	}

	public void setQuantityMutantDna(Long quantityMutantDna) {
		this.quantityMutantDna = quantityMutantDna;
	}

	public Long getQuantityHumanDna() {
		return quantityHumanDna;
	}

	public void setQuantityHumanDna(Long quantityHumanDna) {
		this.quantityHumanDna = quantityHumanDna;
	}

	@JsonProperty("ratio")
	public Double getRatio() {
		if (this.ratio == null) {
			if(quantityMutantDna == 0 && quantityHumanDna == 0)
				ratio = 0.0;
			else if(quantityMutantDna == 0 && quantityHumanDna >= 0) {
				ratio = 0.0;
			}else {
				ratio = (double) quantityMutantDna / (quantityHumanDna);
			}
		}
		return this.ratio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quantityHumanDna == null) ? 0 : quantityHumanDna.hashCode());
		result = prime * result + ((quantityMutantDna == null) ? 0 : quantityMutantDna.hashCode());
		result = prime * result + ((ratio == null) ? 0 : ratio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stats other = (Stats) obj;
		if (quantityHumanDna == null) {
			if (other.quantityHumanDna != null)
				return false;
		} else if (!quantityHumanDna.equals(other.quantityHumanDna))
			return false;
		if (quantityMutantDna == null) {
			if (other.quantityMutantDna != null)
				return false;
		} else if (!quantityMutantDna.equals(other.quantityMutantDna))
			return false;
		if (ratio == null) {
			if (other.ratio != null)
				return false;
		} else if (!ratio.equals(other.ratio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stats [quantityMutantDna=" + quantityMutantDna + ", quantityHumanDna=" + quantityHumanDna + ", ratio="
				+ ratio + "]";
	}
}
