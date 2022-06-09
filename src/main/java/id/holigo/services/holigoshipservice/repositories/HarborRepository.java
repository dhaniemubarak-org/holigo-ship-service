package id.holigo.services.holigoshipservice.repositories;

import id.holigo.services.holigoshipservice.domain.Harbor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarborRepository extends JpaRepository<Harbor, String> {
}
