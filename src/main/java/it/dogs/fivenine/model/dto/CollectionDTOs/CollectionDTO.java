package it.dogs.fivenine.model.dto.CollectionDTOs;

import it.dogs.fivenine.model.domain.sets.CollectionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CollectionDTO {
    
    private String name;
    private CollectionType type;
}
