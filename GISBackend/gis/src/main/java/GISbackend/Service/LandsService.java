package GISbackend.Service;


import GISbackend.Entity.Lands;
import GISbackend.Repository.LandsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static GISbackend.Util.Tool.convertParcelnoToLandNumber;

@Service
public class LandsService {
    private static final Logger log = LoggerFactory.getLogger(LandsService.class);
    @Autowired
    private LandsRepository landsRepository;

    public ArrayList<Lands> getLandsDataBySecnoAndParcelno(String  sectno, String parcelno) {
        String landNumber=convertParcelnoToLandNumber(parcelno);

        return  landsRepository.findBySectionCodeAndLandNumber(sectno,landNumber);

    }


    /**
     * 回傳所有 towns (不重複)
     *
     * @return String 列表 詳細信息 城鎮名稱
     */
    public List<String> findDistinctTown() {

        return landsRepository.findDistinctTowm();
    }

    /**
     *  town名稱
     *
     * @return  sectionCode 列表
     */
    public List<String> findDistinctSectionCodeByTown(String town){
        return landsRepository.findDistinctSectionCodeByTown(town);
    }
    /**
    * @return  land numbers的列表
    **/
    public ArrayList<String> getLandNumberByTownAndSelectName(String town, String selectName) {
        return landsRepository.findLandNumberByTownAndSelectName(town,selectName);
    }



}
