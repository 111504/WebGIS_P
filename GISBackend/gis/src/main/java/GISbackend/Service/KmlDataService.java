package GISbackend.Service;


import GISbackend.Dto.KmlDataDTO;
import GISbackend.Dto.KmlFormDTO;
import GISbackend.Entity.KmlData;
import GISbackend.Entity.KmlForm;
import GISbackend.Repository.KmlDataRepository;
import GISbackend.Repository.KmlFormRepository;
import GISbackend.Util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static GISbackend.Dto.KmlDataDTO.convertGeometryToWKT;

@Service
public class KmlDataService {

    private final KmlDataRepository kmlDataRepository;
    private final KmlFormRepository kmlFormRepository;

    public KmlDataService(KmlDataRepository kmlDataRepository, KmlFormRepository kmlFormRepository) {
        this.kmlDataRepository = kmlDataRepository;
        this.kmlFormRepository = kmlFormRepository;
    }



    /**
     * 根據地塊名稱查詢q
     * @param name 地塊名稱
     * @return 地塊的詳細信息
     */
    public KmlData getKmlDataByName(String name) {
        return kmlDataRepository.findByName(name).orElse(null);
    }

    /**
     * 根據 ID 獲取 KML Data
     * @param id 地塊 ID
     * @return KmlData 詳細信息
     */
    public KmlDataDTO getKmlDataByIdAndSecno(Integer id, String sectno) {
        KmlData kmlData = kmlDataRepository.findKmlDataByIdAndSectno(id,sectno);
        if (kmlData != null) {
            KmlDataDTO dto = new KmlDataDTO();
            dto.setId(kmlData.getId());
            dto.setName(kmlData.getName());
            dto.setSectno(kmlData.getSectno());
            dto.setParcelno(kmlData.getParcelno());
            dto.setGeom(convertGeometryToWKT(kmlData.getGeom())); // 將 Geometry 轉換為 WKT
            return dto;
        }
        return null;
    }
    /**
     * 根據 SEC_NO 獲取 KML Data
     * @param sectno 地塊 sectno
     * @return KmlData 詳細信息
     */
    public List<KmlDataDTO> getKmlDataBySecno(String sectno) {
        List<KmlData> kmlDatas = kmlDataRepository.findBySectno(sectno);

        return getKmlDataDTOS(kmlDatas);
    }
     /**
     * 回傳所有 KML Data
     *
     * @return KmlData 詳細信息
     */
    public List<KmlDataDTO> getAllKmlData() {
        List<KmlData> kmlDatas = kmlDataRepository.findAll();

        return getKmlDataDTOS(kmlDatas);
    }


    /**
     * 回傳所有 sectno (不重複)
     *
     * @return KmlData 詳細信息
     */
    public List<String> findDistinctSectno() {

        return kmlDataRepository.findDistinctSectno();
    }




    /**
     * 根據地圖範圍和分頁參數返回 KML Data
     * @param minLng 左下角經度
     * @param minLat 左下角緯度
     * @param maxLng 右上角經度
     * @param maxLat 右上角緯度
     * @param page 分頁頁碼
     * @param size 每頁的資料量
     * @return KmlData 詳細信息
     */
    public List<KmlDataDTO> getKmlDataByBounds(double minLng, double minLat, double maxLng, double maxLat, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<KmlData> kmlDatas = kmlDataRepository.findByBounds(minLng, minLat, maxLng, maxLat, pageable);
        System.out.println("根據經緯度渲染="+kmlDatas.size());
        return getKmlDataDTOS(kmlDatas);
    }
    /**
     * @param  town 城鎮姓名
    * @param  sectionName
     * */
    public List<KmlDataDTO> findKmlDataByTownAndSectionName(String town, String sectionName) {

        List<KmlData> kmlDatas = kmlDataRepository.findKmlDataByTownAndSectionName(town, sectionName);
        return getKmlDataDTOS(kmlDatas);
    }

    public void updateLandNumbers() {
        // 取出所有的 KmlData
        List<KmlData> kmlDataList = kmlDataRepository.findAll();

        // 遍历每一条 KmlData 记录
        for (KmlData kmlData : kmlDataList) {
            String parcelno = kmlData.getParcelno();

            // 使用 convertParcelnoToLandNumber 轉換
            String landNumber = Tool.convertParcelnoToLandNumber(parcelno);

            // 更新 land_number
            kmlData.setLandNumber(landNumber);
        }

        // 批量保存更新后的记录
        kmlDataRepository.saveAll(kmlDataList);
    }




    private List<KmlDataDTO> getKmlDataDTOS(List<KmlData> kmlDatas) {
        List<KmlDataDTO> dtos = new ArrayList<>();
        if (kmlDatas != null) {

            kmlDatas.forEach(kmlData -> {
                KmlDataDTO dto = new KmlDataDTO();
                dto.setId(kmlData.getId());
                dto.setName(kmlData.getName());
                dto.setSectno(kmlData.getSectno());
                dto.setParcelno(kmlData.getParcelno());
                dto.setGeom(convertGeometryToWKT(kmlData.getGeom())); // 將 Geometry 轉換為 WKT

                dtos.add(dto);
            });


            return dtos;
        }
        return null;
    }








}
