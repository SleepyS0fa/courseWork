package ru.sleepy_sofa.cartridgeproject.fsm.context;

import ru.sleepy_sofa.cartridgeproject.fsm.StateMachineEventResult;

import java.util.ArrayList;
import java.util.List;

public class State<S, E> {
    private final S id;
    private final List<Transition<S, E>> transitionList = new ArrayList<>();
    private State<S, E> nextState;


    public State(S id) {
        this.id = id;
    }

    public StateMachineEventResult<S, E> transition(E event) {
        String message = event.toString();
        for (Transition<S, E> transition : transitionList) {
            if (transition != null && transition.checkCondition(event)) {
                nextState = transition.getTarget();
                return StateMachineEventResult.from(nextState, message, StateMachineEventResult.ResultType.ACCEPTED);
            }
        }
        return StateMachineEventResult.from(nextState, message, StateMachineEventResult.ResultType.DENIED);
    }

    public S getId() {
        return this.id;
    }

    public State<S, E> getNextState() {
        return nextState;
    }

    public void setState(Transition<S, E> transition) {
        transitionList.add(transition);
    }

    protected List<Transition<S, E>> getTransitionList() {
        return transitionList;
    }

    public void addTransition(Transition<S, E> transition) {
        transitionList.add(transition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State<?, ?> state = (State<?, ?>) o;

        return id.equals(state.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                '}';
    }
}
