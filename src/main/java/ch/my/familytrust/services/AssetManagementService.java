package ch.my.familytrust.services;

import ch.my.familytrust.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetManagementService {
    @Autowired
    AccountRepository accountRepository;



}
