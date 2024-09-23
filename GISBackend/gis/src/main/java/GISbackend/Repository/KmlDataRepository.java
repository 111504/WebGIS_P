package GISbackend.Repository;

import GISbackend.Entity.KmlData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KmlDataRepository extends JpaRepository<KmlData, Integer> {
    // 你可以在這裡添加自定義查詢方法
    List<KmlData> findBySectno(String sectno);

    List<KmlData> findByParcelno(String parcelno);

    Optional<KmlData> findByName(String name);

//    @Query("SELECT ST_AsText(geom) as geom, id, name, sectno, parcelno FROM KmlData WHERE id = :id")
//    KmlData findKmlDataById(@Param("id") Integer id);



    @Query("SELECT k FROM KmlData k WHERE k.id = :id AND k.sectno = :sectno")
    KmlData findKmlDataByIdAndSectno(@Param("id") Integer id, @Param("sectno") String sectno);

    @Query("SELECT DISTINCT k.sectno FROM KmlData k")
    List<String> findDistinctSectno();

    @Query("SELECT k FROM KmlData k WHERE ST_Intersects(k.geom, ST_MakeEnvelope(:minLng, :minLat, :maxLng, :maxLat, 4326))")
    List<KmlData> findByBounds(double minLng, double minLat, double maxLng, double maxLat, Pageable pageable);

    @Query("SELECT K FROM KmlData K WHERE K.sectno = :sectno")
    List<KmlData> findKmlDataBySectno(@Param("sectno") String secno);




    @Query("SELECT k FROM KmlData k JOIN Lands l ON k.landNumber = l.landNumber WHERE l.town = :town AND l.sectionName = :sectionName")
    List<KmlData> findKmlDataByTownAndSectionName(@Param("town") String town, @Param("sectionName") String sectionName);


}
