package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Asset;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT a FROM Asset a WHERE a.name = :assetName")
    Optional<Asset> findByAssetName(@Param("assetName") String assetName);


    @Query("SELECT a FROM Asset a WHERE a.name = :assetName AND a.assetId = :accountId")
    Optional<Asset> findByAssetNameAndAccount(@Param("assetName") String assetName, @Param("accountId") UUID accountId);


    @Query("SELECT a FROM Asset a WHERE a.name = :assetName AND a.account.id = :accountId")
    Optional<Asset> findByAssetNameAndAccountId(@Param("assetName") String assetName, @Param("accountId") UUID accountId);


    @Query("SELECT a FROM Asset a WHERE a.account.id = ?1 ")
    List<Asset> findByAccountId(UUID accountId);

    @Query("SELECT a FROM Asset a WHERE a.assetType = :assetType")
    List<Asset> findAssetByAssetType(@Param("assetType") AssetType assetType);

    @Query("SELECT a FROM Asset a LEFT JOIN FETCH a.assetTransactions WHERE a.assetId = :id")
    Asset findAssetWithTransactions(@Param("id") Long id);

    @Query("SELECT a FROM Asset a WHERE a.name = :assetName AND a.account.id = :accountId AND (a.active = true OR a.active IS NULL)")
    Optional<Asset> findActiveByAssetNameAndAccountId(@Param("assetName") String assetName, @Param("accountId") UUID accountId);

    @Query("SELECT a FROM Asset a WHERE a.account.id = :accountId AND (a.active = true OR a.active IS NULL)")
    List<Asset> findActiveByAccountId(@Param("accountId") UUID accountId);
}
