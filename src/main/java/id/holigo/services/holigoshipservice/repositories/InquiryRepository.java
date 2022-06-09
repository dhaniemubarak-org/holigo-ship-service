package id.holigo.services.holigoshipservice.repositories;

import id.holigo.services.holigoshipservice.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface InquiryRepository extends JpaRepository<Inquiry, UUID> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM inquiries " +
                    "WHERE origin_harbor_id = :originHarborId " +
                    "AND destination_harbor_id= :destinationHarborId " +
                    "AND departure_date=:departureDate " +
                    "AND trip_type=:tripType " +
                    "AND adult_male_amount=:adultMaleAmount " +
                    "AND adult_female_amount=:adultFemaleAmount " +
                    "AND child_amount=:childAmount " +
                    "AND infant_amount=:infantAmount " +
                    "AND is_family=:isFamily LIMIT 1")
    Optional<Inquiry> getInquiry(
            @Param("originHarborId") String originHarborId,
            @Param("destinationHarborId") String destinationHarborId,
            @Param("departureDate") String departureDate,
            @Param("tripType") String tripType,
            @Param("adultMaleAmount") Integer adultMaleAmount,
            @Param("adultFemaleAmount") Integer adultFemaleAmount,
            @Param("childAmount") Integer childAmount,
            @Param("infantAmount") Integer infantAmount,
            @Param("isFamily") Boolean isFamily);

}
