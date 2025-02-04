package ru.sleepy_sofa.cartridgeproject.responses;


import java.util.Objects;

public class ErrorResponse extends Response {
    private String message = "error";

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String status, String message) {
        super(status);
        this.message = message;
    }

    public ErrorResponse() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof final ErrorResponse other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        return Objects.equals(this$message, other$message);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ErrorResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "ErrorResponse(message=" + this.getMessage() + ")";
    }
}
