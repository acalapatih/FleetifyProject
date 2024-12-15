package com.acalapatih.fleetifyproject.core.data.network;

public abstract class ApiResponse<R> {
    public static final class Success<T> extends ApiResponse<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Success{data=" + data + '}';
        }
    }

    public static final class Error<T> extends ApiResponse<Void> {
        private final String errorMessage;

        public Error(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public String toString() {
            return "Error{errorMessage='" + errorMessage + "'}";
        }
    }

    public static final class Empty extends ApiResponse<Void> {
        @Override
        public String toString() {
            return "Empty{}";
        }
    }
}

