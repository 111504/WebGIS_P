package GISbackend.Service;


import GISbackend.Entity.Lands;
import GISbackend.Repository.LandsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandsService {
    private static final Logger log = LoggerFactory.getLogger(LandsService.class);
    @Autowired
    private LandsRepository landsRepository;

    public Lands getLandsDataBySecnoAndParcelno(String  sectno,String parcelno) {
        String landNumber=convertParcelnoToLandNumber(parcelno);
        System.out.println(landNumber);
        log.info("landNumber="+landNumber);
        return landsRepository.findBySectionCodeAndLandNumber(sectno,landNumber);

    }

    public String convertParcelnoToLandNumber(String parcelNumber) {
        // 檢查是否包含 "-"
        if (parcelNumber.contains("-")) {
            String[] parts = parcelNumber.split("-");
            String leftPart = String.format("%04d", Integer.parseInt(parts[0]));  // 處理左邊部分
            String rightPart = String.format("%04d", Integer.parseInt(parts[1])); // 處理右邊部分
            return leftPart + rightPart;
        } else {
            // 如果沒有 "-"，右邊補零到 8 位數
            return String.format("%04d", Integer.parseInt(parcelNumber)) + "0000";
        }
    }

}
