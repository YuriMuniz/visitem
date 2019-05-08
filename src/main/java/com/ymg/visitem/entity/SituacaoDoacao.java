package com.ymg.visitem.entity;

public enum SituacaoDoacao {
	ABERTO("EM ABERTO"),
	ACEITO("ACEITO"),
	ENTREGUE ("ENTREGUE");
	
    
	private String description;
	
    private SituacaoDoacao(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }
}
