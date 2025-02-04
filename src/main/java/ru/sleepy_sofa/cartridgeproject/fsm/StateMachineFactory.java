package ru.sleepy_sofa.cartridgeproject.fsm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sleepy_sofa.cartridgeproject.fsm.context.Context;

@Component
public class StateMachineFactory<S extends Enum<S>, E extends Enum<E>> {
    @Autowired
    Context<S, E> context;

    public StateMachine<S, E> create() {
        return new ObjectStateMachine<>(context);
    }

    public StateMachine<S, E> createWithState(S state) {
        return new ObjectStateMachine<>(context, state);
    }
}
