package com.github.pavelkuropatin.reportcreatorapi.converter;

import com.github.pavelkuropatin.reportcreatorapi.Report;
import com.github.pavelkuropatin.reportcreatorapi.ReportFormat;
import com.github.pavelkuropatin.reportcreatorapi.exception.ReportCreatorException;
import fr.opensagres.poi.xwpf.converter.xhtml.Base64EmbedImgManager;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class XHTMLReportConverter implements ReportConverter {

    public Report convertReport(final Report docxReport) {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(docxReport.getFileData());
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final XWPFDocument document = new XWPFDocument(inputStream);
            final XHTMLOptions options = XHTMLOptions.create().setImageManager(new Base64EmbedImgManager());

            XHTMLConverter.getInstance().convert(document, outputStream, options);

            final String fileName = docxReport.getFileName().replace(ReportFormat.DOCX.getExtension(), StringUtils.EMPTY) + ReportFormat.HTML.getExtension();
            final byte[] fileData = outputStream.toByteArray();

            return new Report(fileName, fileData);
        } catch (IOException e) {
            throw new ReportCreatorException(e);
        }
    }
}