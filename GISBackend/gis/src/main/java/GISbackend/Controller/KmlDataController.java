package GISbackend.Controller;


import GISbackend.Dto.KmlDataDTO;
import GISbackend.Dto.KmlFormDTO;
import GISbackend.Entity.KmlData;
import GISbackend.Entity.Lands;
import GISbackend.Response.ApiResponse;
import GISbackend.Service.KmlDataService;
import GISbackend.Service.KmlFormService;
import GISbackend.Service.LandsService;
import GISbackend.Util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/kml-data")
public class KmlDataController {

    private static final Logger logger = LoggerFactory.getLogger(KmlDataController.class);

    private final KmlDataService kmlDataService;
    private final LandsService landsService;
    private final KmlFormService kmlFormService;

    public KmlDataController(LandsService landsService, KmlDataService kmlDataService, KmlFormService kmlFormService) {
        this.landsService = landsService;
        this.kmlDataService = kmlDataService;
        this.kmlFormService = kmlFormService;
    }

    /**
     * 根據ID獲取地塊的詳細信息
     * @param id 地塊ID
     * @param sectno 段號
     * @return 地塊詳細信息
     */
    @GetMapping("/{sectno}/{id}")
    public ApiResponse<KmlDataDTO> getKmlDataById(@PathVariable String sectno,@PathVariable Integer id) {
        KmlDataDTO kmlData = kmlDataService.getKmlDataByIdAndSecno(id,sectno);
        if (kmlData != null) {
            return ApiResponse.success(kmlData);
        } else {
            return ApiResponse.error(404, "KML data not found");
        }
    }

    /**
     * 根據地塊名稱獲取詳細信息
     *
     * @param name 地塊名稱
     * @return 地塊詳細信息
     */
    @GetMapping("/name/{name}")
    public ApiResponse<KmlData> getKmlDataByName(@PathVariable String name) {
        KmlData kmlData = kmlDataService.getKmlDataByName(name);
        if (kmlData != null) {
            return ApiResponse.success(kmlData);
        } else {
            return ApiResponse.error(404, "KML data not found");
        }
    }
     /**
     * 根據段號名稱獲取詳細信息
     *
     * @param sectno 段號
     * @return 地塊詳細信息
     */
    @GetMapping("/sectno/{sectno}")
    public ApiResponse<List<KmlDataDTO>> getKmlDataBySecno(@PathVariable String sectno) {
        List<KmlDataDTO> kmlDateDTOs= kmlDataService.getKmlDataBySecno(sectno);
        System.out.println(kmlDateDTOs);
        if (!kmlDateDTOs.isEmpty()) {
            return ApiResponse.success(kmlDateDTOs);
        }else{
            return ApiResponse.error(404, "KML data not found");
        }
    }
    /**
     * 回傳所有kml訊息
     *
     *
     * @return 地塊詳細信息
     */
    @GetMapping("/all")
    public ApiResponse<List<KmlDataDTO>> getAllKmlData() {
        List<KmlDataDTO> kmlDateDTOs= kmlDataService.getAllKmlData();
        System.out.println(kmlDateDTOs);
        if (!kmlDateDTOs.isEmpty()) {
            return ApiResponse.success(kmlDateDTOs);
        }else{
            return ApiResponse.error(404, "KML data not found");
        }
    }
    /**
     * 根據段號名稱獲取詳細信息
     *
     * @param sectno 段號
     * @param parcelno 地號
     * @return 回傳地址訊息
     */
    @GetMapping("/sectno/{sectno}/parcelno/{parcelno}")
    public ApiResponse<ArrayList<Lands>> getLandsDataBySecnoAndParcelno(
            @PathVariable String sectno,
            @PathVariable String parcelno) {

        System.out.println("paracelno:"+parcelno);
        ArrayList<Lands> lands=landsService.getLandsDataBySecnoAndParcelno(sectno,parcelno);
        if (lands != null) {
           return ApiResponse.success(lands);
        }
           return ApiResponse.error(404, "查詢結果為空");
    }
    /**
     * 根據段號名稱獲取詳細信息
     *
     * @param town 區明
     * @param sectionname 段名
     * @return 回傳地址訊息
     */
     @GetMapping("/town/{town}/selectName/{selectName}")
     public  ApiResponse<List<KmlDataDTO>> getkmlDataByTownAndSelectName(
             @PathVariable String town,
             @PathVariable String selectName) {

         System.out.println("town:"+town+"selectName:"+selectName);
         List<KmlDataDTO> kmlDateDTOs= kmlDataService.findKmlDataByTownAndSectionName(town,selectName);
         if (!kmlDateDTOs.isEmpty()) {
             return ApiResponse.success(kmlDateDTOs);
         }else{
             return ApiResponse.error(404, "KML data not found");
         }



     }

    @PostMapping("/update-land-numbers")
    public ResponseEntity<String> updateLandNumbers() {
        kmlDataService.updateLandNumbers();
        return ResponseEntity.ok("Land numbers updated successfully.");
    }




    /**
     * 回傳所有sectno (sectno不重複)
     *
     *
     * @return 地塊詳細信息
     */
    @GetMapping("/allsectno")
    public ApiResponse<List<String>> getAllsectno() {
        List<String> sectnos= kmlDataService.findDistinctSectno();

        if (!sectnos.isEmpty()) {
            return ApiResponse.success(sectnos);
        }else{
            return ApiResponse.error(404, "sectnos data not found");
        }
    }


    /**
     * @param town 回傳所有城鎮姓名
     *
     *
     * @return 地塊詳細信息
     */
    @GetMapping("/allTown")
    public ApiResponse<List<String>> getAllTown() {
        List<String> towns= landsService.findDistinctTown();

        if (!towns.isEmpty()) {
            return ApiResponse.success(towns);
        }else{
            return ApiResponse.error(404, "towns data not found");
        }
    }

    @GetMapping("/town/{town}")
    public ApiResponse<List<String>> getSectnoByTown(@PathVariable String town) {
        if (town == null || town.trim().isEmpty()) {
            return ApiResponse.error(400, "無效的town参数");
        }
        // 格式檢查
        if (!town.matches("^[a-zA-Z\\u4e00-\\u9fa5\\s]+$")) {
            return ApiResponse.error(400, "town格式不正確");
        }

        List<String> towns= landsService.findDistinctSectionCodeByTown(town);

        if (!towns.isEmpty()) {
            return ApiResponse.success(towns);
        }else{
            return ApiResponse.error(404, "towns data not found");
        }
    }




    @GetMapping("/test")
    public ApiResponse<KmlData> test() {

            return ApiResponse.success(null);

    }

    /**
     * 根據地圖範圍返回部分 KML Data
     *
     * @param minLng 左下角經度
     * @param minLat 左下角緯度
     * @param maxLng 右上角經度
     * @param maxLat 右上角緯度
     * @param page 分頁頁碼
     * @param size 每頁的資料量
     * @return KmlData 詳細信息
     */
    @GetMapping("/bounds")
    public ApiResponse<List<KmlDataDTO>> getKmlDataByBounds(
            @RequestParam double minLng,
            @RequestParam double minLat,
            @RequestParam double maxLng,
            @RequestParam double maxLat,
            @RequestParam int page,
            @RequestParam int size) {
        List<KmlDataDTO> kmlDateDTOs=kmlDataService.getKmlDataByBounds(minLng, minLat, maxLng, maxLat, page, size);


        if (!kmlDateDTOs.isEmpty()) {
            return ApiResponse.success(kmlDateDTOs);
        }else{
            return ApiResponse.error(404, "KML data not found");
        }


    }

    /**
     * @param kmlFormDTO 包含要儲存的 標點資訊
     * @return ApiResponse 保存結果
     */
    @PostMapping("/saveKmlForm")
    public ApiResponse<String> saveKmlData(@RequestBody KmlFormDTO kmlFormDTO) {
        System.out.println("kmlFormDTO:"+kmlFormDTO);
        return kmlFormService.saveKmlForm(kmlFormDTO)
                .map(ApiResponse::success)
                .orElseGet(() -> ApiResponse.error(500, "Failed to save KML data"));
    }
    /**
     *
     * @return ApiResponse 所有的kmlForm資訊
     */
    @GetMapping("/allKmlForm")
    public ApiResponse<List<KmlFormDTO>> getAllKmlForm() {
        List<KmlFormDTO> kmlFormDatas=  kmlFormService.getAllKmlFormData();
        if (kmlFormDatas == null || kmlFormDatas.isEmpty()) {
            return ApiResponse.error(404, "No KML forms found.");
        }

        return ApiResponse.success(kmlFormDatas);
    }

}
