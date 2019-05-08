package com.ymg.visitem.entity;

public enum SituacaoUsuario {
	ATIVO("ATIVO"),
	INATIVO ("INATIVO");
	
    
	private String description;
	
    private SituacaoUsuario(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }
}
