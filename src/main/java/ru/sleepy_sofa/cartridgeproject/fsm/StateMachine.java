package ru.sleepy_sofa.cartridgeproject.fsm;

import ru.sleepy_sofa.cartridgeproject.fsm.context.Context;
import ru.sleepy_sofa.cartridgeproject.fsm.context.State;

public class StateMachine<S extends Enum<S>, E extends Enum<E>> {
    private final Context<S, E> context;
    private final State<S, E> initialState;
    private State<S, E> currentState;

    public StateMachine(Context<S, E> context, State<S, E> initialState) {
        this.context = context;
        this.initialState = context.getInitialState();
        this.currentState = context.getState(initialState.getId());
    }

    public StateMachine(Context<S, E> context) {
        this.context = context;
        this.initialState = context.getInitialState();
        this.currentState = context.getInitialState();
    }

    public StateMachineEventResult<S, E> sendEvent(E event) {
        StateMachineEventResult<S, E> eventResult = context.getState(currentState.getId()).transition(event);
        if (eventResult.getResultType().equals(StateMachineEventResult.ResultType.ACCEPTED)) {
            currentState = context.getState(currentState.getNextState().getId());
        }
        return eventResult;
    }

    public State<S, E> getState() {
        return currentState;
    }

    public void setState(S state) {

    }

    public Context<S, E> getContext() {
        return context;
    }
}
