package com.github.pavelkuropatin.reportcreatorapi.converter;

import com.github.pavelkuropatin.reportcreatorapi.Image;
import com.github.pavelkuropatin.reportcreatorapi.Report;
import com.github.pavelkuropatin.reportcreatorapi.creator.DocxReportCreator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XHTMLReportConverterTest {

    @Test
    void createReportTest() throws IOException {
        final File docxTemplateFile = new File("src/test/resources/report-templates/test_template.docx");
        final File imageFile = new File("src/test/resources/report-templates/img/img.jpg");
        final byte[] docxTemplate = FileUtils.readFileToByteArray(docxTemplateFile);
        final byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);

        final Map<String, Object> params = new HashMap<>();
        params.put("Name", "XDocReport user");

        final List<Image> images = new ArrayList<>();
        images.add(new Image("image", imageBytes));

        final Report report = new DocxReportCreator()
                .createReport(docxTemplate, "htmlReport", params, images);

        final Report convertedReport = new XHTMLReportConverter().convertReport(report);

        assertNotNull(convertedReport);
        assertNotNull(convertedReport.getFileName());
        assertNotNull(convertedReport.getFileData());
        assertTrue(convertedReport.getFileData().length > 0);

        final File reportFile = new File("src/test/resources/report-examples/" + convertedReport.getFileName());
        FileUtils.writeByteArrayToFile(reportFile, convertedReport.getFileData());
    }
}