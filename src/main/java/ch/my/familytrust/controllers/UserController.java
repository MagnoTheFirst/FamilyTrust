package ch.my.familytrust.controllers;


import ch.my.familytrust.dtos.AccountCashFlowRequest;
import ch.my.familytrust.dtos.CreateAccountRequest;
import ch.my.familytrust.dtos.DeleteAccountRequest;
import ch.my.familytrust.entities.Account;
import ch.my.familytrust.dtos.AccountResponseDto;
import ch.my.familytrust.enums.CashflowType;
import ch.my.familytrust.services.AccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1")
public class UserController {

}
