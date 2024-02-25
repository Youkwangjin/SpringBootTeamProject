package pack.service.admin.adminImpl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pack.dao.admin.ContainerInfoListDAO;
import pack.dto.container.ContainerDTO;
import pack.dto.form.FormDTO;
import pack.service.admin.ContainerInfoListService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContainerInfoListServiceImpl implements ContainerInfoListService {

    private final ContainerInfoListDAO containerInfoListDAO;
    private final int pList = 10;

    @Override
    public List<ContainerDTO> getContainerList() {
        return containerInfoListDAO.getContainerList();
    }

    @Override
    public int totalRegistered() {
        return containerInfoListDAO.totalRegistered();
    }

    @Override
    public List<ContainerDTO> getRegSearch(FormDTO formDTO) {
        return containerInfoListDAO.getRegSearch(formDTO);
    }

    @Override
    public ContainerDTO containerDetail(String cont_no) {
        return containerInfoListDAO.containerDetail(cont_no);
    }

    @Override
    public ArrayList<ContainerDTO> getRegisteredListData(ArrayList<ContainerDTO> list, int page) {
        ArrayList<ContainerDTO> regResult = new ArrayList<>();
        int start = (page - 1) * pList;
        int end = Math.min(start + pList, list.size());
        for (int i = start; i < end; i++) {
            regResult.add(list.get(i));
        }
        return regResult;
    }
    @Override
    public int getRegisteredPageSu() {
        int tot = totalRegistered();
        int pageSu = tot / pList;
        if (tot % pList > 0) pageSu += 1;
        return pageSu;
    }

    @Override
    public String deleteContainer(String cont_no) {
        boolean deletionSuccess = containerInfoListDAO.containerDelete(cont_no);
        Logger logger = LoggerFactory.getLogger(ContainerInfoListServiceImpl.class);
        if (deletionSuccess) {
            logger.info("컨테이너 삭제 성공! " + cont_no);
            return "redirect:/admin/container/registered";
        } else {
            logger.error("컨테이너 삭제 실패! ID: " + cont_no);
            return "/container/container-error";
        }
    }
}
