package com.study.financial.service;

import com.study.financial.dto.ReportBudget;
import com.study.financial.dto.ReportTransaction;
import com.study.financial.dto.ReportTransactionDetail;
import com.study.financial.enums.GroupType;
import com.study.financial.exception.BusinessException;
import com.study.financial.messages.AuthMessage;
import com.study.financial.messages.CommonMessage;
import com.study.financial.repository.BudgetRepository;
import com.study.financial.repository.TransactionRepository;
import com.study.financial.utils.FilterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final TransactionRepository transactionRepository;

    private final BudgetRepository budgetRepository;

    public BigDecimal getCurrentBalance(){
        Long userId = JwtService.getUserId();
        if(userId == null) throw new BusinessException(AuthMessage.USER_NULL, HttpStatus.UNAUTHORIZED);
        return transactionRepository.sum(userId);
    }

    public ReportTransaction getReportTransaction(
            LocalDate startDate,
            LocalDate endDate
    ){
        if(startDate.isAfter(endDate)){
            throw new BusinessException(CommonMessage.DATE_FILTER_INVALID, HttpStatus.BAD_REQUEST);
        }
        Long userId = JwtService.getUserId();
        if(userId == null) throw new BusinessException(AuthMessage.USER_NULL, HttpStatus.UNAUTHORIZED);
        ReportTransaction reportTransaction = transactionRepository.getTransactionSummary(startDate, endDate, userId);

        GroupType groupType = FilterUtils.determineGroupType(startDate, endDate);

        List<Object[]> results = transactionRepository.getTransactionDetail(
                startDate,
                endDate,
                userId,
                groupType.name()
        );

        List<ReportTransactionDetail> details = results.stream()
                .map(row -> new ReportTransactionDetail(
                        row[1] != null
                                ? new BigDecimal(row[1].toString())
                                : BigDecimal.ZERO,
                        row[0] != null
                                ? formatGroupDate(row[0].toString(), groupType)
                                : null
                ))
                .toList();
        reportTransaction.setDetails(details);
        return reportTransaction;
    }

    public List<ReportBudget> getReportBudget(LocalDate startDate, LocalDate endDate){
        Long userId = JwtService.getUserId();
        if(userId == null) throw new BusinessException(AuthMessage.USER_NULL, HttpStatus.UNAUTHORIZED);
        return budgetRepository.getReportBudget(startDate, endDate, userId);
    }

    private String formatGroupDate(String groupDate, GroupType groupType) {
        if (groupDate == null) return null;

        return switch (groupType) {
            case DAY -> LocalDate.parse(groupDate)
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            case WEEK -> {
                String[] parts = groupDate.split("-");
                int year = Integer.parseInt(parts[0]);
                int week = Integer.parseInt(parts[1]);

                LocalDate date = LocalDate.of(year, 1, 4)
                        .with(WeekFields.ISO.weekOfWeekBasedYear(), week)
                        .with(WeekFields.ISO.dayOfWeek(), 1);

                yield date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }

            case MONTH -> YearMonth.parse(groupDate)
                    .format(DateTimeFormatter.ofPattern("MM/yyyy"));

            case YEAR -> groupDate;
        };
    }
}
