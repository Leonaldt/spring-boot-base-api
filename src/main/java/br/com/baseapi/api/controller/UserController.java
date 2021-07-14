package br.com.baseapi.api.controller;

import br.com.baseapi.api.model.UserModel;
import br.com.baseapi.api.model.input.UserInput;
import br.com.baseapi.api.model.input.SendWelcomeEmailInput;
import br.com.baseapi.domain.exceptions.UserNotFoundException;
import br.com.baseapi.domain.model.User;
import br.com.baseapi.domain.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation("Create a new user")
    public UserModel create(@RequestBody @Valid UserInput userInput){
        return this.userService.create(userInput);
    }

    @GetMapping
    @ApiOperation("List all users")
    public List<UserModel> listAll(){
        return this.userService.findAll();
    }

    @PostMapping("/sendWelcomeEmail")
    public ResponseEntity<?> sendWelcomeEmail(@RequestBody @Valid SendWelcomeEmailInput dto){
        User user = this.userService.findByEmail(dto.getEmail()).orElseThrow(() -> new UserNotFoundException());
        this.userService.sendWelcomeEmail(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
