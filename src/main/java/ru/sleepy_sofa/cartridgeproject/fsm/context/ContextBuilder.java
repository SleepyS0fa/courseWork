package ru.sleepy_sofa.cartridgeproject.fsm.context;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContextBuilder<S extends Enum<S>, E extends Enum<E>> {
    private final Context<S, E> context = new Context<>();

    private final StateConfigurator<S, E> stateConfigurator = new StateConfigurator<>(this);
    private final TransitionConfigurator<S, E> transitionConfigurator = new TransitionConfigurator<>(this);

    public StateConfigurator<S, E> states() {
        return stateConfigurator;
    }

    public TransitionConfigurator<S, E> transitions() {
        return transitionConfigurator;
    }

    public Context<S, E> build() {
        // получаем общий вид состояний и переходов
        List<State<S, E>> stateConfig = stateConfigurator.getStateList();
        List<Transition<S, E>> transitionConfig = transitionConfigurator.getTransitionList();

        // добавляем все возможные состояния в контекст
        for (State<S, E> state : stateConfig) {
            context.stateList.put(state.getId(), state);
        }
        // добавляем в состояние переход, если состояние является исходной точкой перехода
        for (Transition<S, E> transition : transitionConfig) {
            context.stateList.get(transition.getSource().getId()).addTransition(transition);
        }
        // обновляем заполненные состояния в переходах
        for (Transition<S, E> transition : transitionConfig) {
            State<S, E> state = context.stateList.get(transition.getSource().getId());
            transition.setSource(state);
            state = context.stateList.get(transition.getTarget().getId());
            transition.setTarget(state);
        }
        // заполняем граф
        for (State<S, E> state : context.stateList.values()) {
            context.graph.put(state, state.getTransitionList());
        }
        // устанавливаем начальную точку
        context.initialState = stateConfigurator.getInitialState();

        return context;
    }
}
