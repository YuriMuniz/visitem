package com.ymg.visitem.entity;

public enum Categoria {
	ASILO("ASILO"),
	ORFANATO ("ORFANATO"),
	CRECHE("CRECHE");
	
    
	private String description;
	
    private Categoria(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }
}
