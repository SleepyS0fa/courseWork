package ru.sleepy_sofa.cartridgeproject.fsm.context;

import java.util.ArrayList;
import java.util.List;

public class TransitionConfigurator<S extends Enum<S>, E extends Enum<E>> {
    private final ContextBuilder<S, E> contextBuilder;
    private final List<Transition<S, E>> transitionList = new ArrayList<>();
    private Transition<S, E> currentTransition = new Transition<>();

    public TransitionConfigurator(ContextBuilder<S, E> contextBuilder) {
        this.contextBuilder = contextBuilder;
    }

    public TransitionConfigurator<S, E> source(S state) {
        currentTransition.setSource(new State<>(state));
        return this;
    }

    public TransitionConfigurator<S, E> target(S state) {
        currentTransition.setTarget(new State<>(state));
        return this;
    }

    public TransitionConfigurator<S, E> event(E event) {
        currentTransition.setEvent(event);
        return this;
    }

    public TransitionConfigurator<S, E> and() {
        transitionList.add(currentTransition);
        currentTransition = new Transition<>();
        return this;
    }

    public ContextBuilder<S, E> save() {
        transitionList.add(currentTransition);
        return contextBuilder;
    }

    protected List<Transition<S, E>> getTransitionList() {
        return transitionList;
    }
}
