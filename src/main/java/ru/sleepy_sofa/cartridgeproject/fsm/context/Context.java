package ru.sleepy_sofa.cartridgeproject.fsm.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context<S extends Enum<S>, E extends Enum<E>> {
    protected final Map<S, State<S, E>> stateList = new HashMap<>();
    protected final Map<State<S, E>, List<Transition<S, E>>> graph = new HashMap<>();
    protected State<S, E> initialState;

    public State<S, E> getState(S state) {
        return stateList.get(state);
    }

    public List<Transition<S, E>> getTransitions(State<S, E> state) {
        return graph.get(state);
    }

    public State<S, E> getInitialState() {
        return initialState;
    }
}
