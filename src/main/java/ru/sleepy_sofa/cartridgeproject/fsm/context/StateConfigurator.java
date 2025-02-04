package ru.sleepy_sofa.cartridgeproject.fsm.context;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class StateConfigurator<S extends Enum<S>, E extends Enum<E>> {

    private final List<State<S, E>> stateList = new ArrayList<>();
    private final ContextBuilder<S, E> contextBuilder;
    private State<S, E> initialState;

    public StateConfigurator(ContextBuilder<S, E> contextBuilder) {
        this.contextBuilder = contextBuilder;
    }


    public StateConfigurator<S, E> initial(S state) {
        initialState = new State<>(state);
        return this;
    }

    public StateConfigurator<S, E> setStates(EnumSet<S> stateEnumSet) {
        for (S state : stateEnumSet) {
            stateList.add(new State<>(state));
        }
        return this;
    }

    public ContextBuilder<S, E> save() {
        return contextBuilder;
    }

    protected List<State<S, E>> getStateList() {
        return stateList;
    }

    protected State<S, E> getInitialState() {
        return initialState;
    }
}
