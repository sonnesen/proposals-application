package com.sonnesen.proposals.application.proposal.usecase;

/**
 * Abstract class representing a use case with input and output types.
 */
public abstract class UseCase<T, R> {

    /**
     * Executes the use case with the given input and returns the output.
     *
     * @param input the input data for the use case
     * @return the output data from the use case
     */
    public abstract R execute(T input);
}
