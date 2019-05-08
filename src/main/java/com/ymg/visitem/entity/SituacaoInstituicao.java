package com.ymg.visitem.entity;

public enum SituacaoInstituicao {
	ATIVO("ATIVO"),
	INATIVO ("INATIVO"),
	PENDENTE ("PENDENTE"),
	AGUARDANDO ("AGUARDANDO CONFIRMAÇÃO");
	
    
	private String description;
	
    private SituacaoInstituicao(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }
}
