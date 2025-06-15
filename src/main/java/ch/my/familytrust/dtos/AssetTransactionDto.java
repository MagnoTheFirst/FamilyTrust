package ch.my.familytrust.dtos;

import ch.my.familytrust.entities.Asset;
import ch.my.familytrust.enums.AssetTransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AssetTransactionDto(
        Long assetTransactionId,
        LocalDateTime transactionDate,
        AssetTransactionType assetTransactionType,
        Double quantity,
        BigDecimal price,
        BigDecimal assetTransactionBalance,
        String comment, String assetName
) {
}
