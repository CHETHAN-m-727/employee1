package com.firstock.employee.controller;


import com.firstock.employee.entity.User;
import com.firstock.employee.repository.UserRepository;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    private static final Gson gson = new Gson();

    @GetMapping(value = "/users")
    public ResponseEntity<?> getUsers(Model model){
        List<User> users = new ArrayList<>();
        Iterable<User> list =  userRepo.findAll();
        list.forEach(t -> users.add(t));
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/adduser", consumes = {"application/json"})
    public ResponseEntity<String> saveUser(@RequestBody @Valid User user, BindingResult result){
        if(result.hasErrors() || userRepo.existsById(user.getUsername()) ) {
            return new ResponseEntity<String>(gson.toJson("User already Exist try Different username"), HttpStatus.BAD_REQUEST);
        }else{
            userRepo.save(user);
            return new ResponseEntity<String>(gson.toJson("User added Succesfully!!"), HttpStatus.OK);
        }
    }

}