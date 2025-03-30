package ch.my.familytrust.controller;

import jakarta.persistence.PostUpdate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/stock")
public class TransactionController {

    @GetMapping()
    public void test1(){

    }

    @PostMapping()
    public void test2(){

    }

    @PutMapping
    public void test3(){

    }

    @DeleteMapping
    public void test4(){

    }

}
