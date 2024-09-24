package GISbackend.Repository;

import GISbackend.Entity.KmlForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KmlFormRepository extends JpaRepository<KmlForm, Integer> {

}
