package com.github.pavelkuropatin.reportcreatorapi.exception;

public class ReportCreatorException extends RuntimeException {

    private static final long serialVersionUID = -5648640385446067156L;

    public ReportCreatorException(final Throwable cause) {
        super(cause);
    }

    public ReportCreatorException(final String message) {
        super(message);
    }
}
