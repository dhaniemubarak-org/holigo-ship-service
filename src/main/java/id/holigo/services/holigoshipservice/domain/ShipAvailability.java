package id.holigo.services.holigoshipservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ship_availabilities")
public class ShipAvailability {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(length = 50, columnDefinition = "varchar(50)")
    private String shipName;

    @Column(length = 20, columnDefinition = "varchar(20)")
    private String shipNumber;

    @Column(length = 4, columnDefinition = "varchar(4)", nullable = false)
    private String originHarborId;

    @Transient
    private Harbor origin;

    @Column(length = 4, columnDefinition = "varchar(4)", nullable = false)
    private String destinationHarborId;

    @Transient
    private Harbor destination;

    private Date departureDate;

    private Time departureTime;

    private Date arrivalDate;

    private Time arrivalTime;

    private String imageUrl;

    @Column(length = 20, columnDefinition = "varchar(20)")
    private String shipClass;

    @Column(length = 20, columnDefinition = "varchar(20)")
    private String shipSubClass;

    @Lob
    private String fare;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
