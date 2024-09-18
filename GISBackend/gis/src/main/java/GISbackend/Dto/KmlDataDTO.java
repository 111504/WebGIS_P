package GISbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KmlDataDTO {
    private String geom; // WKT 格式
    private Integer id;
    private String name;
    private String sectno;
    private String parcelno;

    // getter 和 setter


    // 將 Geometry 轉換為 WKT
    public static String convertGeometryToWKT(Geometry geom) {
        WKTWriter writer = new WKTWriter();
        return writer.write(geom);
    }
}
