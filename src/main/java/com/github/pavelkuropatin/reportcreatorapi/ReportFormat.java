package com.github.pavelkuropatin.reportcreatorapi;

public enum ReportFormat {

    DOCX(".docx"),
    PDF(".pdf"),
    HTML(".html"),
    ;

    private final String extension;

    ReportFormat(final String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return this.extension;
    }
}