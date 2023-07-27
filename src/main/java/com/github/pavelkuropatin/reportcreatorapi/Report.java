package com.github.pavelkuropatin.reportcreatorapi;

public class Report {

    private final String fileName;
    private final byte[] fileData;

    public Report(final String fileName, final byte[] fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public String getFileName() {
        return this.fileName;
    }

    public byte[] getFileData() {
        return this.fileData;
    }
}