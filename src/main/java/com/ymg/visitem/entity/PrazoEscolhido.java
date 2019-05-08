package com.ymg.visitem.entity;

public enum PrazoEscolhido {
	UM("1"),
    DOIS("2"),
	TRES("3"),
	QUATRO("4"),
	CINCO("5"),
	SEIS("6"),
	SETE ("7");
	
    
	private String description;
	
    private PrazoEscolhido(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }
}
