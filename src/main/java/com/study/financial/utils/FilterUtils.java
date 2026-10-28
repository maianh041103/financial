package com.study.financial.utils;

import com.study.financial.enums.GroupType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FilterUtils {
    public static GroupType determineGroupType(LocalDate startDate, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);

        if (days <= 15) {
            return GroupType.DAY;
        }

        if (days <= 90) {
            return GroupType.WEEK;
        }

        if (days <= 365) {
            return GroupType.MONTH;
        }

        return GroupType.YEAR;
    }
}
