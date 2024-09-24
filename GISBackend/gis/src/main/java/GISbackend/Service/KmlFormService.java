package GISbackend.Service;


import GISbackend.Dto.KmlFormDTO;
import GISbackend.Entity.KmlForm;
import GISbackend.Repository.KmlFormRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KmlFormService {

    private final KmlFormRepository kmlFormRepository;
    public KmlFormService(KmlFormRepository kmlFormRepository) {
        this.kmlFormRepository = kmlFormRepository;
    }

    public Optional<String> saveKmlForm(KmlFormDTO kmlFormDTO) {
        try {

            KmlForm kmlForm = new KmlForm();
            kmlForm.setTime(kmlFormDTO.getTime());
            kmlForm.setLatlng(kmlFormDTO.getLatlng());
            kmlForm.setLocation(kmlFormDTO.getLocation());
            kmlForm.setDescription(kmlFormDTO.getDescription());
            KmlForm savedKmlForm =  kmlFormRepository.save(kmlForm);
            return Optional.ofNullable(savedKmlForm.getId())
                    .map(id -> "KML data saved successfully with ID: " + id);
        }
        catch (Exception e) {
            // 捕獲異常並返回空的 Optional
            return Optional.empty();
        }

    }


    public List<KmlFormDTO> getAllKmlFormData() {
        List<KmlForm> kmlForms = kmlFormRepository.findAll();
        List<KmlFormDTO> dtoList = new ArrayList<>();

        for (KmlForm kmlForm : kmlForms) {
            KmlFormDTO dto = new KmlFormDTO();
            dto.setTime(kmlForm.getTime());
            dto.setLocation(kmlForm.getLocation()); // 確保 KmlForm 實體中有 location 欄位
            dto.setLatlng(kmlForm.getLatlng());
            dto.setDescription(kmlForm.getDescription());
            dtoList.add(dto);
        }

        return dtoList;
    }
}
