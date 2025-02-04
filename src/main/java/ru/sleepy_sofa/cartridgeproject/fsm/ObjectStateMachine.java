package ru.sleepy_sofa.cartridgeproject.fsm;

import ru.sleepy_sofa.cartridgeproject.fsm.context.Context;

public class ObjectStateMachine<S extends Enum<S>, E extends Enum<E>> extends StateMachine<S, E> {
    public ObjectStateMachine(Context<S, E> context, S initialState) {
        super(context, new ru.sleepy_sofa.cartridgeproject.fsm.context.State<>(initialState));
    }

    public ObjectStateMachine(Context<S, E> context) {
        super(context);
    }
}
