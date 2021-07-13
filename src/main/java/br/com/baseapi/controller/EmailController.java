package br.com.baseapi.controller;

import br.com.baseapi.domain.User;
import br.com.baseapi.domain.dto.SendWelcomeEmailDTO;
import br.com.baseapi.exceptions.UserNotFoundException;
import br.com.baseapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendWelcomeEmail")
    public ResponseEntity<?> sendWelcomeEmail(@RequestBody @Valid SendWelcomeEmailDTO dto){
        User user = this.userService.findByEmail(dto.getEmail()).orElseThrow(() -> new UserNotFoundException());
        this.userService.sendWelcomeEmail(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
