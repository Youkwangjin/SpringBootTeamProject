package pack.service.admin;

import org.springframework.stereotype.Service;
import pack.dto.container.ContainerDTO;
import pack.dto.form.FormDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public interface ContainerInfoListService {
    List<ContainerDTO> getContainerList();
    int totalRegistered();
    List<ContainerDTO> getRegSearch(FormDTO formDTO);
    ContainerDTO containerDetail(String cont_no);
    ArrayList<ContainerDTO> getRegisteredListData(ArrayList<ContainerDTO> list, int page);
    int getRegisteredPageSu();
    String deleteContainer(String cont_no);
}
