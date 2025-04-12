package com.example.attendance_api.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    // All-args constructor
    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Two-args constructor (with data = null)
    public ApiResponse(boolean success, String message) {
        this(success, message, null);
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // toString() method
    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    // equals() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse that = (ApiResponse) o;
        return success == that.success &&
                message.equals(that.message) &&
                (data != null ? data.equals(that.data) : that.data == null);
    }

    // hashCode() method
    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + message.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}