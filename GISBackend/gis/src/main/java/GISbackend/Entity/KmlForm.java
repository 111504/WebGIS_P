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


    @Column(name = "description", length = 100)
    private String description;


    public KmlForm(String description, String latlng, LocalDateTime time) {
        this.description = description;
        this.latlng = latlng;
        this.time = time;
    }

}

