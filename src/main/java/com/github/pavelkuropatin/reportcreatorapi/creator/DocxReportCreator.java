package com.github.pavelkuropatin.reportcreatorapi.creator;

import com.github.pavelkuropatin.reportcreatorapi.Image;
import com.github.pavelkuropatin.reportcreatorapi.Report;
import com.github.pavelkuropatin.reportcreatorapi.ReportFormat;
import com.github.pavelkuropatin.reportcreatorapi.exception.ReportCreatorException;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ByteArrayImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.NullImageBehaviour;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class DocxReportCreator implements ReportCreator {

    /**
     * Generates a simple report in .docx format. Template must be in <b>.docx</b> format.
     *
     * @param template template bytes
     * @param fileName the name under which the report will be created
     *
     * @return generated report
     *
     * @throws NullPointerException   if {@code template} or {@code fileName} is null
     * @throws ReportCreatorException if the report is generated with errors
     */
    public Report createReport(final byte[] template, final String fileName) {
        return mainCreateReport(template, fileName, null, null);
    }

    /**
     * Generates a report in .docx format with specified parameters. Template must be in <b>.docx</b> format.
     *
     * @param template template bytes
     * @param fileName the name under which the report will be created
     * @param paramMap parameters that must be mapped into template, might be null
     *
     * @return generated report
     *
     * @throws NullPointerException   if {@code template} or {@code fileName} is null
     * @throws ReportCreatorException if the report is generated with errors
     */
    public Report createReport(
            final byte[] template,
            final String fileName,
            final Map<String, Object> paramMap
    ) {
        return mainCreateReport(template, fileName, paramMap, null);
    }

    /**
     * Generates a report in .docx format with specified parameters and images. Template must be in <b>.docx</b>
     * format.
     *
     * @param template template bytes
     * @param fileName the name under which the report will be created
     * @param paramMap parameters that must be mapped into template, might be null
     * @param images   images that must be mapped into template, might be null
     *
     * @return generated report
     *
     * @throws NullPointerException   if {@code template} or {@code fileName} is null
     * @throws ReportCreatorException if the report is generated with errors
     */
    public Report createReport(
            final byte[] template,
            final String fileName,
            final Map<String, Object> paramMap,
            final List<Image> images
    ) {
        return mainCreateReport(template, fileName, paramMap, images);
    }

    /**
     * Main method for report generation. Generates a report in .docx format with specified parameters and images.
     * Template must be in <b>.docx</b> format.
     *
     * @param template template bytes
     * @param fileName the name under which the report will be created
     * @param paramMap parameters that must be mapped into template, might be null
     * @param images   images that must be mapped into template, might be null
     *
     * @return generated report
     *
     * @throws NullPointerException   if {@code template} or {@code fileName} is null
     * @throws ReportCreatorException if the report is generated with errors
     */
    private Report mainCreateReport(
            final byte[] template,
            final String fileName,
            final Map<String, Object> paramMap,
            final List<Image> images
    ) {
        Objects.requireNonNull(template);
        Objects.requireNonNull(fileName);
        final String name = fileName + ReportFormat.DOCX.getExtension();
        final byte[] fileData = getFileData(template, paramMap, images);
        return new Report(name, fileData);
    }

    private byte[] getFileData(
            final byte[] template,
            final Map<String, Object> paramMap,
            final List<Image> images
    ) {
        try {
            final InputStream inputStream = new ByteArrayInputStream(template);
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final IXDocReport report = XDocReportRegistry.getRegistry()
                    .loadReport(inputStream, TemplateEngineKind.Velocity);

            final IContext context = report.createContext();

            if (paramMap != null) {
                context.putMap(paramMap);
            }

            if (images != null && !images.isEmpty()) {
                final FieldsMetadata metadata = report.createFieldsMetadata();
                context.putMap(createimages(images, metadata));
            }

            report.process(context, outputStream);

            return outputStream.toByteArray();
        } catch (final XDocReportException | IOException e) {
            throw new ReportCreatorException(e);
        }
    }

    private Map<String, Object> createimages(final List<Image> images, final FieldsMetadata metadata) {
        final Map<String, Object> map = new HashMap<>();
        images.forEach(image -> {
            metadata.addFieldAsImage(image.getImageName(), NullImageBehaviour.RemoveImageTemplate,
                    image.isUseOriginalSize());
            map.put(image.getImageName(), new ByteArrayImageProvider(image.getImageData(), image.isUseOriginalSize()));
        });
        return map;
    }
}