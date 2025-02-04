package ru.sleepy_sofa.cartridgeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.State;
import ru.sleepy_sofa.cartridgeproject.models.Cartridge;

import java.util.List;

@Repository
public interface CartridgeRepository extends JpaRepository<Cartridge, Long> {
    List<Cartridge> findByState(State state);
}
