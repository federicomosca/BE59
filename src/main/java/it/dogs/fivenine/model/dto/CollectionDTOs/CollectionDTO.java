package it.dogs.fivenine.model.dto.CollectionDTOs;

import it.dogs.fivenine.model.domain.sets.CollectionType;

public class CollectionDTO {
    
    private String name;
    private CollectionType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CollectionType getType() {
        return type;
    }

    public void setType(CollectionType type) {
        this.type = type;
    }
}
