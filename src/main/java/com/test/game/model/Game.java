package com.test.game.model;

import java.time.LocalDate;
import java.util.Objects;

public class Game {
	
	private String name;
    private LocalDate dateOfCreation;
    private boolean active;
	
    
	public Game(String name, LocalDate dateOfCreation, boolean active) {
		super();
		this.name = name;
		this.dateOfCreation = dateOfCreation;
		this.active = active;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(LocalDate dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "Game [name=" + name + ", dateOfCreation=" + dateOfCreation + ", active=" + active + "]";
	}

	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Game other = (Game) obj;
        return Objects.equals(name, other.name)
                && Objects.equals(dateOfCreation, other.dateOfCreation)
                && active == other.active;
    }
    
}
