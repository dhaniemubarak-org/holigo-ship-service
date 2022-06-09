package id.holigo.services.holigoshipservice.repositories;

import id.holigo.services.holigoshipservice.domain.ShipAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipAvailabilityRepository extends JpaRepository<ShipAvailability, UUID> {
}
