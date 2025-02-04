package ru.sleepy_sofa.cartridgeproject.fsm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sleepy_sofa.cartridgeproject.fsm.context.Context;
import ru.sleepy_sofa.cartridgeproject.fsm.context.ContextBuilder;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.Event;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.State;

import java.util.EnumSet;

@Configuration
public class StateMachineConfig {

    @Bean
    public Context<State, Event> configure(ContextBuilder<State, Event> config) {
        return config
                .states()
                .initial(State.READY)
                .setStates(EnumSet.allOf(State.class))
                .save()
                .transitions()
                .source(State.READY).target(State.INSTALLED).event(Event.INSTALL)
                .and()
                .source(State.INSTALLED).target(State.EMPTY).event(Event.UNINSTALL)
                .and()
                .source(State.EMPTY).target(State.REFUELING).event(Event.REFUEL)
                .and()
                .source(State.REFUELING).target(State.READY).event(Event.RETURN)
                .save()
                .build();
    }
}