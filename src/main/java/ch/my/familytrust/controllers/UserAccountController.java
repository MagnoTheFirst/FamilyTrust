package ch.my.familytrust.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("api/v1/user/{user-id}/account/{account-id}")
public class UserAccountController {



    @GetMapping("/list/accounts")
    public ResponseEntity<Object> getAccounts(){
        return null;
    }

    /**
     * @param json-request
     *
     * */
    @PostMapping("/buy/stock/")
    public ResponseEntity<Object> createUser(){
        return null;
    }


    /**
     * @param json-request
     *
     * */
    @PatchMapping("/sell/stock")
    public ResponseEntity<Object> changeUser(){
        return null;
    }


}
