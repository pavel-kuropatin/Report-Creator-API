package com.github.pavelkuropatin.reportcreatorapi.facade;

import com.github.pavelkuropatin.reportcreatorapi.Image;
import com.github.pavelkuropatin.reportcreatorapi.Report;
import com.github.pavelkuropatin.reportcreatorapi.ReportFormat;
import com.github.pavelkuropatin.reportcreatorapi.converter.PdfReportConverter;
import com.github.pavelkuropatin.reportcreatorapi.converter.XHTMLReportConverter;
import com.github.pavelkuropatin.reportcreatorapi.creator.DocxReportCreator;
import com.github.pavelkuropatin.reportcreatorapi.exception.ReportCreatorException;

import java.util.List;
import java.util.Map;

public final class ReportCreatorFacade {

    public Report createReport(final byte[] template, final String fileName, final ReportFormat format) {
        final Report report = new DocxReportCreator().createReport(template, fileName);
        return convertIfNeeded(format, report);
    }

    public Report createReport(final byte[] template, String fileName, final Map<String, Object> paramMap,
                               final ReportFormat format) {
        final Report report = new DocxReportCreator().createReport(template, fileName, paramMap);
        return convertIfNeeded(format, report);
    }

    public Report createReport(final byte[] template, String fileName, final Map<String, Object> paramMap,
                               final List<Image> images, final ReportFormat format) {
        final Report report = new DocxReportCreator().createReport(template, fileName, paramMap, images);
        return convertIfNeeded(format, report);
    }

    private Report convertIfNeeded(final ReportFormat format, final Report report) {
        switch (format) {
            case DOCX:
                return report;
            case PDF:
                return new PdfReportConverter().convertReport(report);
            case HTML:
                return new XHTMLReportConverter().convertReport(report);
            default:
                throw new ReportCreatorException("Unsupported format: " + format);
        }
    }
}
