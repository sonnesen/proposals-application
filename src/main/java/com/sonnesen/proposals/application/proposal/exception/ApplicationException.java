package com.sonnesen.proposals.application.proposal.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message, null, true, false);
    }

}
