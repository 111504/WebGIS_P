package GISbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "kml_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KmlData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "sectno", length = 255)
    private String sectno;

    @Column(name = "parcelno", length = 255)
    private String parcelno;

    @Column(name = "geom", columnDefinition = "geometry(Polygon,4326)")
    private Geometry geom;

    public KmlData(String name, String sectno, String parcelno, Geometry geom) {
        this.name = name;
        this.sectno = sectno;
        this.parcelno = parcelno;
        this.geom = geom;
    }
}
