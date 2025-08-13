package it.dogs.fivenine.model.dto.CollectionDTOs;

import it.dogs.fivenine.model.domain.sets.CollectionType;
import it.dogs.fivenine.model.domain.enums.CollectionVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CollectionDTO {
    
    @NotBlank(message = "Collection name is required")
    @Size(min = 1, max = 100, message = "Collection name must be between 1 and 100 characters")
    private String name;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Collection visibility is required")
    private CollectionVisibility visibility = CollectionVisibility.PRIVATE;
    
    @NotNull(message = "Collection type is required")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CollectionVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CollectionVisibility visibility) {
        this.visibility = visibility;
    }
}
