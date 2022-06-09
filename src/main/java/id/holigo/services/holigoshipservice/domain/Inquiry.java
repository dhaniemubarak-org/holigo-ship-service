package id.holigo.services.holigoshipservice.domain;


import id.holigo.services.common.model.TripType;
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
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "inquiries")
public class Inquiry {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(columnDefinition = "varchar(10)")
    private String originHarborId;

    @Column(columnDefinition = "varchar(10)")
    private String destinationHarborId;

    private Date departureDate;

    @Enumerated(EnumType.STRING)
    private TripType tripType;

    @Column(columnDefinition = "tinyint(1)")
    private Integer adultMaleAmount;

    @Column(columnDefinition = "tinyint(1)")
    private Integer adultFemaleAmount;

    @Column(columnDefinition = "tinyint(1)")
    private Integer childAmount;

    @Column(columnDefinition = "tinyint(1)")
    private Integer infantAmount;

    private Boolean isFamily;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
