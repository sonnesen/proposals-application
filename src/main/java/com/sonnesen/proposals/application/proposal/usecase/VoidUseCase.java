package com.sonnesen.proposals.application.proposal.usecase;

public abstract class VoidUseCase<T> {
    public abstract void execute(T input);
}
