package it.dogs.fivenine.builder;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;

@Component
public class UserBuilder {

    private final ModelMapper modelMapper;

    public UserBuilder(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public User build(SignUpDTO dto) {
        User model = modelMapper.map(dto, User.class);
        return model;
    }

    public SignUpDTO buildDTO(User user) {
        SignUpDTO dto = modelMapper.map(user, SignUpDTO.class);
        return dto;
    }
}