package ru.sleepy_sofa.cartridgeproject.fsm;

import ru.sleepy_sofa.cartridgeproject.fsm.context.State;

public interface StateMachineEventResult<S, E> {

    static <S, E> StateMachineEventResult<S, E> from(State<S, E> state, String message, ResultType resultType) {
        return new DefaultStateMachineEventResult<S, E>(state, message, resultType);
    }

    ResultType getResultType();

    String getMessage();

    State<S, E> getState();

    enum ResultType {
        ACCEPTED,
        DENIED;

        ResultType() {
        }
    }

    class DefaultStateMachineEventResult<S, E> implements StateMachineEventResult<S, E> {
        private final State<S, E> state;
        private final String message;
        private final ResultType resultType;

        DefaultStateMachineEventResult(State<S, E> state, String message, ResultType resultType) {
            this.state = state;
            this.message = message;
            this.resultType = resultType;
        }

        public State<S, E> getState() {
            return this.state;
        }

        public String getMessage() {
            return this.message;
        }

        public ResultType getResultType() {
            return this.resultType;
        }

        public String toString() {
            return "DefaultStateMachineEventResult [ state=" + this.state + ", message=" + this.message + ", resultType=" + this.resultType + "]";
        }
    }
}
