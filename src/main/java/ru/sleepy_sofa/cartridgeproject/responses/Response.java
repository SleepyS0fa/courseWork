package ru.sleepy_sofa.cartridgeproject.responses;


import java.util.Objects;

public class Response {
    private String status = "success";

    public Response(String status) {
        this.status = status;
    }

    public Response() {
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof final Response other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        return Objects.equals(this$status, other$status);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Response;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "Response(status=" + this.getStatus() + ")";
    }
}
