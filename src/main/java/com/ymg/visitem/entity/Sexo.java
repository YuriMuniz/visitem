package com.ymg.visitem.entity;

public enum Sexo {
	M("MASCULINO"),
    F("FEMININO");

	
    
	private String description;
	
    private Sexo(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }

}
