package ua.webapp.votingsystem.exception;



public class ErrorInfo {
    public CharSequence url;
    public String cause;
    public String[] details;

    public ErrorInfo(CharSequence url, Throwable ex) {
        this(url, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }

    public ErrorInfo(CharSequence url, String cause, String... details) {
        this.url = url;
        this.cause = cause;
        this.details = details;
    }
}
