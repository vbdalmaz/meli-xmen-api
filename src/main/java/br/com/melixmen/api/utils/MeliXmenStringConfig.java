package br.com.melixmen.api.utils;

public abstract class MeliXmenStringConfig {
    /* DNA */
    public static final String DNA_BASE_URL = "/dna";
    
    /* URL */
    public static final String ALL = "/all";
    public static final String STATS = "/stats";
    public static final String DNA_SERVICE = "/mutant";

    /* MESSAGES */
    public static final String EXCEPTION_MESSAGE = "We had problem try again in a few minutes";
    public static final String DNA_ERROR_EXCEPTION_MESSAGE = "Your DNA is not valid check your input";
    
    /* CACHE NAMES */
    public static final String GET_ALL_DNAS_CACHE = "AllDNAsCache";
    public static final String GET_DNA_CACHE = "DNACache";
    public static final String GET_STATS_CACHE = "StatsCache";
    
    
    /* PATTERNS */
    public static final String VALID_LETTERS_PATTERN = "[A,C,G,T]";
    
    public static final String FOUR_A_PATTERN = "AAAA";
    public static final String FOUR_C_PATTERN = "CCCC";
    public static final String FOUR_G_PATTERN = "GGGG";
    public static final String FOUR_T_PATTERN = "TTTT";
    
    public static final String[] MUTANT_DNA_SEQUENCE = new String[]{FOUR_A_PATTERN,FOUR_C_PATTERN,FOUR_G_PATTERN,FOUR_T_PATTERN}; 
    
}
