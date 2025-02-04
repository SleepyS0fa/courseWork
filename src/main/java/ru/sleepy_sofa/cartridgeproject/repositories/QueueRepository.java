package ru.sleepy_sofa.cartridgeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sleepy_sofa.cartridgeproject.models.Cartridge;
import ru.sleepy_sofa.cartridgeproject.models.Office;
import ru.sleepy_sofa.cartridgeproject.models.Queue;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
    @Query(value = """
                SELECT queue.id, cartridge_id, cartridges.name AS cartridge_name,
                       office_id, offices.name AS office_name\s
                       FROM queue JOIN cartridges ON queue.cartridge_id=cartridges.id
                       JOIN offices ON queue.office_id=offices.id
                       ORDER BY queue.id
                   """,
           nativeQuery = true)
    List<Queue> findAll();

    Optional<Queue> findByCartridge(Cartridge cartridge);

    Optional<Queue> findByCartridgeAndOffice(Cartridge cartridge, Office office);
}
