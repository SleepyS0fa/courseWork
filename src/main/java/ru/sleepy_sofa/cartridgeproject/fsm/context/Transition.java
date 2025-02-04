package ru.sleepy_sofa.cartridgeproject.fsm.context;

public class Transition<S, E> {
    private State<S, E> source;
    private State<S, E> target;
    private E event;

    public Transition(State<S, E> source, State<S, E> target, E event) {
        this.source = source;
        this.target = target;
        this.event = event;
    }

    public Transition() {
    }

    public boolean checkCondition(E event) {
        return this.event.equals(event);
    }

    public State<S, E> getTarget() {
        return target;
    }

    protected void setTarget(State<S, E> target) {
        this.target = target;
    }

    public State<S, E> getSource() {
        return source;
    }

    protected void setSource(State<S, E> source) {
        this.source = source;
    }

    public E getEvent() {
        return event;
    }

    protected void setEvent(E event) {
        this.event = event;
    }
}
