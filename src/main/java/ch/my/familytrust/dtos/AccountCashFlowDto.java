package ch.my.familytrust.dtos;

import ch.my.familytrust.enums.CashflowType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AccountCashFlowDto(
        UUID id,
        String accountName,
        String currencyCode,
        BigDecimal amount,
        UUID ownerUserId,
        CashflowType cashflowType,
        String comment,
        LocalDateTime cashflowDate
) {
}
