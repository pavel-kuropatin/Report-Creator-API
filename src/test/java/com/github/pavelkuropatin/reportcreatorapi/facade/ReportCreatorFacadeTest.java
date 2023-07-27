package com.github.pavelkuropatin.reportcreatorapi.facade;

import com.github.pavelkuropatin.reportcreatorapi.Image;
import com.github.pavelkuropatin.reportcreatorapi.Report;
import com.github.pavelkuropatin.reportcreatorapi.ReportFormat;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportCreatorFacadeTest {

    private static byte[] docxTemplate;
    private static byte[] imageBytes;
    private static Map<String, Object> params;
    private static List<Image> images;

    @BeforeAll
    static void beforeAll() throws IOException {
        final File docxTemplateFile = new File("src/test/resources/report-templates/test_template.docx");
        final File imageFile = new File("src/test/resources/report-templates/img/img.jpg");
        docxTemplate = FileUtils.readFileToByteArray(docxTemplateFile);
        imageBytes = FileUtils.readFileToByteArray(imageFile);
        params = new HashMap<>();
        params.put("Name", "XDocReport user");
        images = new ArrayList<>();
        images.add(new Image("image", imageBytes));
    }

    @Test
    void createDocxReportTest() {
        final Report report = new ReportCreatorFacade()
                .createReport(docxTemplate, "docxReport", params, images, ReportFormat.DOCX);
        assertNotNull(report);
        assertNotNull(report.getFileName());
        assertTrue(report.getFileName().endsWith(ReportFormat.DOCX.getExtension()));
        assertNotNull(report.getFileData());
        assertTrue(report.getFileData().length > 0);
    }

    @Test
    void createPdfReportTest() {
        final Report report = new ReportCreatorFacade()
                .createReport(docxTemplate, "pdfReport", params, images, ReportFormat.PDF);

        assertNotNull(report);
        assertNotNull(report.getFileName());
        assertTrue(report.getFileName().endsWith(ReportFormat.PDF.getExtension()));
        assertNotNull(report.getFileData());
        assertTrue(report.getFileData().length > 0);
    }

    @Test
    void createHtmlReportTest() {
        final Report report = new ReportCreatorFacade()
                .createReport(docxTemplate, "htmlReport", params, images, ReportFormat.HTML);

        assertNotNull(report);
        assertNotNull(report.getFileName());
        assertTrue(report.getFileName().endsWith(ReportFormat.HTML.getExtension()));
        assertNotNull(report.getFileData());
        assertTrue(report.getFileData().length > 0);
    }
}