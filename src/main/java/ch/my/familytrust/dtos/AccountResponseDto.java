package ch.my.familytrust.dtos;

import ch.my.familytrust.entities.AccountCashFlow;
import ch.my.familytrust.entities.Asset;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) für die Übertragung von Account-Informationen
 * an den Client. Enthält nur die relevanten Daten, die der Client sehen darf
 * oder benötigt, ohne die volle Entität mit ihren Beziehungen preiszugeben.
 */
public record AccountResponseDto(
        UUID id,
        String accountName,
        String currencyCode,
        UUID ownerUserId,
        BigDecimal balance,
        LocalDateTime lasAccessDate,
        LocalDateTime createdDate,
        BigDecimal availableMoney,
        List<AccountCashFlowDto> cashFlowList,
        List<Asset> assets
) {}