package br.com.baseapi.api.assembler;

import br.com.baseapi.api.model.input.UserInput;
import br.com.baseapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public User toDomainObject(UserInput userInput) {
        return this.modelMapper.map(userInput, User.class);
    }

    public void copyToDomainObject(UserInput userInput, User user) {
        this.modelMapper.map(userInput, user);
    }
}
