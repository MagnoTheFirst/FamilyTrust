package ch.my.familytrust.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("api/v1/admin")
public class AdminController {


    /**
     * @param json-request
     * */
    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        return null;
    }


    /**
     * @param json-request
     *
     * */
    @PostMapping("/create/user")
    public ResponseEntity<Object> createUser(){
        return null;
    }

    @DeleteMapping("/delete/user")
    public ResponseEntity<Object> deleteUser(){
        return null;
    }

    @PatchMapping("/change/user")
    public ResponseEntity<Object> changeUser(){
        return null;
    }
}
