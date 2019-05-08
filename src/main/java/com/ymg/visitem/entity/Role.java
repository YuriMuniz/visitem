package com.ymg.visitem.entity;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    INST("INST");
    
	private String description;
	
    private Role(String description){
    	this.description = description;
    }
    
    @Override
    public String toString() {
    	
    	return description;
    }
}

