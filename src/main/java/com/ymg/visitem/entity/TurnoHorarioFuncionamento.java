package com.ymg.visitem.entity;

public enum TurnoHorarioFuncionamento {
	 M ("Manhã"), T ("Tarde"), N ("Noite")	;
	
    
	private String description;
	
    private TurnoHorarioFuncionamento(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }
}
