package ru.sleepy_sofa.cartridgeproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.sleepy_sofa.cartridgeproject.fsm.StateMachine;
import ru.sleepy_sofa.cartridgeproject.fsm.StateMachineEventResult;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.Event;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.State;

@Entity
@Table(name = "cartridges")
public class Cartridge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private State state;

    @ManyToOne
    @JoinColumn(name = "office_id")
    @JsonIgnore
    private Office office;


    @JsonIgnore
    @Transient
    private StateMachine<State, Event> stateMachine;

    public Cartridge(Long id, String name, State state, Office office, StateMachine<State, Event> stateMachine) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.office = office;
        this.stateMachine = stateMachine;
    }

    public Cartridge() {
    }

    public boolean sendEvent(Event event) {
        StateMachineEventResult<State, Event> result = stateMachine.sendEvent(event);
        System.out.println(result);
        if (result.getResultType().equals(StateMachineEventResult.ResultType.ACCEPTED)) {
            this.state = result.getState().getId();
            System.out.println(getState());
            return true;
        }
        return false;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Office getOffice() {
        return this.office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public StateMachine<State, Event> getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(StateMachine<State, Event> stateMachine) {
        this.stateMachine = stateMachine;
    }

}
