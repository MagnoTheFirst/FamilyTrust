package ch.my.familytrust.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("api/v1/user/{user-id}")
public class UserController {


    @GetMapping("/get/accounts/")
    public ResponseEntity<Object> getUsers(){
        return null;
    }

    /**
     * @param json-request
     *
     * */
    @PostMapping("/create/account/")
    public ResponseEntity<Object> createUser(){
        return null;
    }

    @DeleteMapping("/delete/account/{account-id}")
    public ResponseEntity<Object> deleteUser(){
        return null;
    }

    /**
     * @param json-request
     *
     * */
    @PatchMapping("/change/account")
    public ResponseEntity<Object> changeUser(){
        return null;
    }




}
