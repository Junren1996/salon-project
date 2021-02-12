package edu.uh.tech.cis3368.semesterproject;

import java.time.LocalDate;
import java.util.Date;

public class ReportService {

    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public boolean validate() {
        return startDate.isBefore(endDate);
    }

    public ProfitReport getProfitReport() {
        return null;
    }
}
