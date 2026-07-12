package com.ayush.smart_splitwise.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        private Builder(){}

        public Builder timestamp(LocalDateTime timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(int status){
            this.status = status;
            return this;
        }

        public Builder error(String error){
            this.error = error;
            return this;
        }

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Builder path(String path){
            this.path = path;
            return this;
        }

        public ErrorResponse build(){
            return new ErrorResponse(timestamp, status, error, message, path);
        }
    }
}
