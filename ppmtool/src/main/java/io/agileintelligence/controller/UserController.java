package io.agileintelligence.controller;

import io.agileintelligence.model.User;
import io.agileintelligence.service.IUserService;
import io.agileintelligence.util.Util;
import io.agileintelligence.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private IUserService iUserService;
    private UserValidator userValidator;

    @Autowired
    public UserController(IUserService iUserService, UserValidator userValidator) {
        this.iUserService = iUserService;
        this.userValidator = userValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        // Validate passwords match
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = Util.mapValidationMethod(result);
        if(errorMap != null) {
            return errorMap;
        }

        User newUser = iUserService.saveUser(user);

        return  new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
