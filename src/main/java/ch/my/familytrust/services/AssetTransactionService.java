package ch.my.familytrust.services;

import ch.my.familytrust.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetTransactionService {

    @Autowired
    AssetRepository assetRepository;


}
