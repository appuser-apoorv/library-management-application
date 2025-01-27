package com.example.library_management.Library.Management.Application.dto;

    public class ReturnBookResponse {
        private String message;
        private double fineAmount;

        public ReturnBookResponse(String message, double fineAmount) {
            this.message = message;
            this.fineAmount = fineAmount;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public double getFineAmount() {
            return fineAmount;
        }

        public void setFineAmount(double fineAmount) {
            this.fineAmount = fineAmount;
        }
    }


