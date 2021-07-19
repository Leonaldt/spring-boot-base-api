package br.com.baseapi.api.controller;

import br.com.baseapi.api.assembler.UserModelAssembler;
import br.com.baseapi.api.model.UserModel;
import br.com.baseapi.api.model.input.UserInput;
import br.com.baseapi.api.model.input.SendWelcomeEmailInput;
import br.com.baseapi.domain.exceptions.UserNotFoundException;
import br.com.baseapi.domain.model.User;
import br.com.baseapi.domain.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private UserModelAssembler userModelAssembler;

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

    @GetMapping("/{id}")
    @ApiOperation("List a user by id")
    public UserModel findById(@PathVariable Long id) {
        return this.userModelAssembler.toModel(this.userService.findById(id));
    }

    @GetMapping("/page")
    @ApiOperation("List all users pageable")
    public Page<UserModel> page(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "name", required = false) String parameter
    ){
        return this.userService.page(page, linesPerPage, orderBy, direction, parameter);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a user by id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/sendWelcomeEmail")
    public ResponseEntity<?> sendWelcomeEmail(@RequestBody @Valid SendWelcomeEmailInput dto){
        User user = this.userService.findByEmail(dto.getEmail()).orElseThrow(() -> new UserNotFoundException());
        this.userService.sendWelcomeEmail(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
