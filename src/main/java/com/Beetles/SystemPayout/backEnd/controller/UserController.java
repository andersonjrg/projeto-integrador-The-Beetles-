package com.Beetles.SystemPayout.backEnd.controller;

import com.Beetles.SystemPayout.backEnd.entity.User;
import com.Beetles.SystemPayout.backEnd.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUsers(@RequestBody User user){
            User savedUser = userService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> showAllUsers(){
            List<User> user = userService.showUsers();
            return ResponseEntity.ok(user);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<User> showIdUser(@PathVariable Long id){
            User user = userService.showUserById(id);
            return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
            User userUp = userService.updateUser(id, user);
            return new ResponseEntity<>(userUp, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
