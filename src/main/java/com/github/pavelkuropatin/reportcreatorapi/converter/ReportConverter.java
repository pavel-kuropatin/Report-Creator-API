package com.github.pavelkuropatin.reportcreatorapi.converter;

import com.github.pavelkuropatin.reportcreatorapi.Report;

public interface ReportConverter {

    Report convertReport(Report docxReport);
}