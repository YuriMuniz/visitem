package com.ymg.visitem.entity;

public enum SituacaoHospede {
	ATIVO("ATIVO"),
	INATIVO ("INATIVO");
	
    
	private String description;
	
    private SituacaoHospede(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }
}
