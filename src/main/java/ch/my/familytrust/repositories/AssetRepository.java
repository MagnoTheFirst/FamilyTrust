package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT a FROM Asset a where a.name = :assetName")
    Asset findByAssetName(@Param("userId") String assetName);



}
