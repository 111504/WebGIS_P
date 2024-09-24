package GISbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "kml_data_form")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KmlForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動增長 ID
    private Integer id;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "latlng", length = 100)
    private String latlng;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "description", length = 100)
    private String description;


    public KmlForm(LocalDateTime time, String latlng, String location, String description) {
        this.time = time;
        this.latlng = latlng;
        this.location = location;
        this.description = description;
    }
}

