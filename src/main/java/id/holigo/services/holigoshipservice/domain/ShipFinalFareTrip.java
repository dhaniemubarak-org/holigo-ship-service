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
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ship_final_fare_trips")
public class ShipFinalFareTrip {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    private ShipFinalFare finalFare;

    @Column(length = 50, columnDefinition = "varchar(50)")
    private String shipName;

    @Column(length = 20, columnDefinition = "varchar(20)")
    private String shipNumber;

    @Column(length = 4, columnDefinition = "varchar(4)", nullable = false)
    private String originHarborId;

    @Transient
    private Harbor origin;

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

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal fareAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal adminAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal ntaAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal nraAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal cpAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal mpAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal ipAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal hpAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal hvAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal prAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal ipcAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal hpcAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal prcAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal lossAmount;

    private String supplierId;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
