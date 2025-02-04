package ru.sleepy_sofa.cartridgeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sleepy_sofa.cartridgeproject.models.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
}
