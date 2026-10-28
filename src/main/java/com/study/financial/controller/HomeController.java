package com.study.financial.controller;

import com.study.financial.messages.HomeMessage;
import com.study.financial.response.ApiResponse;
import com.study.financial.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/home/")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("current-balance")
    public ResponseEntity<?> getCurrentBalance(){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                homeService.getCurrentBalance(),
                HomeMessage.GET_CURRENT_BALANCE_SUCCESS
        ));
    }

    @GetMapping("report-transaction")
    public ResponseEntity<?> getReportTransaction(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                homeService.getReportTransaction(startDate, endDate),
                HomeMessage.GET_REPORT_TRANSACTION_SUCCESS
        ));
    }

    @GetMapping("report-budget")
    public ResponseEntity<?> getReportBudget(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                homeService.getReportBudget(startDate, endDate),
                HomeMessage.GET_REPORT_TRANSACTION_SUCCESS
        ));
    }

}
