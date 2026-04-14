package com.sonnesen.proposals.application.proposal.exception;

/**
 * Exception thrown when a domain entity is not found.
 */
public class NotFoundException extends ApplicationException {

    /**
     * Constructor for NotFoundException.
     *
     * @param message The exception message.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
