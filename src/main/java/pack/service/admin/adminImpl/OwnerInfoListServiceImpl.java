package pack.service.admin.adminImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.admin.OwnerInfoListDAO;
import pack.dto.form.FormDTO;
import pack.dto.owner.OwnerDTO;
import pack.service.admin.OwnerInfoListService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OwnerInfoListServiceImpl implements OwnerInfoListService {
    private final OwnerInfoListDAO ownerInfoListDAO;
    private final int pList = 10;

    @Override
    public List<OwnerDTO> getOwnerList() {
        return ownerInfoListDAO.getOwnerAll();
    }

    @Override
    public List<OwnerDTO> getOwnerSearch(FormDTO formDTO) {
        return ownerInfoListDAO.getOwnerSearch(formDTO);
    }

    @Override
    public int totalOwner() {
        return ownerInfoListDAO.totalOwner();
    }

    @Override
    public boolean ownerDelete(String business_num) {
        return ownerInfoListDAO.ownerDelete(business_num);
    }

    @Override
    public int getOwnerRecords() {
        return ownerInfoListDAO.getOwnerRecords();
    }

    @Override
    public ArrayList<OwnerDTO> getOwnerListData(ArrayList<OwnerDTO> list, int page) {
        ArrayList<OwnerDTO> ownerResult = new ArrayList<>();
        int start = (page - 1) * pList;
        int end = Math.min(start + pList, list.size());
        for (int i = start; i < end; i++) {
            ownerResult.add(list.get(i));
        }
        return ownerResult;
    }

    @Override
    public int getOwnerPageSu() {
        int tot = totalOwner();
        int pageSu = tot / pList;
        if (tot % pList > 0) pageSu += 1;
        return pageSu;
    }
}
