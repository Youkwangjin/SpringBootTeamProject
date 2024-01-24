package pack.dao.container;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.container.ContainerDTO;
import pack.mapper.container.ContainerMapper;

@Repository
@AllArgsConstructor
public class ContainerDAO {

    private final ContainerMapper containerMapper;

    public List<ContainerDTO> getContainerAll() {
        return containerMapper.kakaoMapContainer();
    }

    public List<ContainerDTO> getContainerAllData(String business_num) {
        return containerMapper.selectAllContainer(business_num);
    }

    public List<ContainerDTO> getDataReserve(String business_num) {
        return containerMapper.selectReserve(business_num);
    }

    public ContainerDTO containerDetail(String cont_no) {
        return containerMapper.selectContainer(cont_no);
    }

    @Transactional
    public boolean insertContainer(ContainerDTO containerDTO) {
        boolean b = false;
        int re = containerMapper.insertContainer(containerDTO);
        if (re > 0)
            b = true;
        return b;
    }

    @Transactional
    public boolean updateContainer(ContainerDTO containerDTO) {
        boolean b = false;
        int re = containerMapper.updateContainer(containerDTO);
        if (re > 0)
            b = true;
        return b;
    }

    @Transactional
    public boolean deleteContainer(String cont_no) {
        boolean b = false;
        int re = containerMapper.deleteContainer(cont_no);
        if (re > 0)
            b = true;
        return b;
    }
}