package com.github.pavelkuropatin.reportcreatorapi.creator;

import com.github.pavelkuropatin.reportcreatorapi.Image;
import com.github.pavelkuropatin.reportcreatorapi.Report;

import java.util.List;
import java.util.Map;

public interface ReportCreator {

    Report createReport(byte[] template, String fileName);

    Report createReport(byte[] template, String fileName, Map<String, Object> paramMap);

    Report createReport(byte[] template, String fileName, Map<String, Object> paramMap, List<Image> images);
}