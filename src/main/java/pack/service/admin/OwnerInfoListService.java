package pack.service.admin;

import org.springframework.stereotype.Service;
import pack.dto.form.FormDTO;
import pack.dto.owner.OwnerDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public interface OwnerInfoListService {
    List<OwnerDTO> getOwnerList();
    List<OwnerDTO> getOwnerSearch(FormDTO formDTO);
    int totalOwner();
    boolean ownerDelete(String business_num);
    int getOwnerRecords();
    ArrayList<OwnerDTO> getOwnerListData(ArrayList<OwnerDTO> list, int page);
    int getOwnerPageSu();
}
