package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Asset;
import ch.my.familytrust.entities.AssetTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long> {

    @Query("SELECT a FROM AssetTransaction a WHERE a.asset.assetId = :assetId AND a.asset.account.id = :accountId")
    Optional<AssetTransaction> findByAssetTransactionIdAndAccountId(@Param("assetId") Long assetId, @Param("accountId") UUID accountId);


    @Query("SELECT a FROM AssetTransaction a WHERE a.asset.assetId = :assetId AND a.asset.account.id = :accountId")
    Optional<AssetTransaction> findByAssetTransactionNameAndAccountId(@Param("assetName") Long assetId, @Param("accountId") UUID accountId);



}
