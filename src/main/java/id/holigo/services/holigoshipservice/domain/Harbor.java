package id.holigo.services.holigoshipservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "harbors")
public class Harbor {

    @Id
    @Column(length = 4, columnDefinition = "varchar(4)", nullable = false)
    private String id;

    @Column(length = 50, columnDefinition = "varchar(50)", nullable = false)
    private String name;

    private Boolean isActive;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
