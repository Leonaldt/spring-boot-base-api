package br.com.baseapi.api.assembler;

import br.com.baseapi.api.model.UserModel;
import br.com.baseapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UserModel toModel(User user) {
        return this.modelMapper.map(user, UserModel.class);
    }

    public List<UserModel> toCollectionModel(List<User> users) {
        return users.stream().map(user -> toModel(user)).collect(Collectors.toList());
    }
}
