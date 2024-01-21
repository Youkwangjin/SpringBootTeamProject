package pack.service.container;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pack.dto.container.ContainerDTO;
import pack.dto.upload.UploadFileDTO;

import java.util.List;
import java.util.Map;

@Service
public interface ContainerService {
    Map<String, Object> containerProcess();
    List<ContainerDTO> getContainerAllData(String business_num);
    List<ContainerDTO> getDataReserve(String business_num);
    double[] getCoordinatesFromAddress(String address);
    boolean isAllowedExtension(String filename);
    String insertContainerWithFile(ContainerDTO containerDTO, UploadFileDTO uploadFileDTO, BindingResult result, String uploadDirectory);
    boolean updateContainer(ContainerDTO containerDTO);
    boolean deleteContainer(String cont_no);
    ContainerDTO containerDetail(String cont_no);
    ResponseEntity<?> processReservation(String cont_no);

}
