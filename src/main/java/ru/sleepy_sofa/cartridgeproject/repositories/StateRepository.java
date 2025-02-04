package ru.sleepy_sofa.cartridgeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.State;
import ru.sleepy_sofa.cartridgeproject.models.Cartridge;

import java.util.List;

public interface StateRepository extends JpaRepository<Cartridge, Long> {
    List<Cartridge> findByState(State state);
}