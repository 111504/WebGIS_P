package GISbackend.Repository;

import GISbackend.Entity.Lands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LandsRepository extends JpaRepository<Lands, Long> {


    @Query("SELECT L FROM Lands L WHERE L.sectionCode = :sectionCode AND L.landNumber = :landNumber")
    ArrayList<Lands> findBySectionCodeAndLandNumber(@Param("sectionCode")String sectionCode, @Param("landNumber")String landNumber);
    // 你可以在這裡添加自定義查詢方法

    @Query("SELECT DISTINCT L.town FROM Lands L")
    List<String> findDistinctTowm();

    //根據區的名稱回傳段號的列表
    @Query("SELECT DISTINCT L.sectionName FROM Lands L WHERE L.town = :town")
    List<String> findDistinctSectionCodeByTown(@Param("town")String town);

    @Query("SELECT L.landNumber FROM Lands L WHERE L.town = :town AND L.sectionName = :sectionName")
    ArrayList<String> findLandNumberByTownAndSelectName(String town, String sectionName);
}
