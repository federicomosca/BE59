package it.dogs.fivenine.builder;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.SignUpDTO;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserBuilder {

    private final ModelMapper modelMapper;

    public User build(SignUpDTO dto) {
        User model = modelMapper.map(dto, User.class);
        return model;
    }

}