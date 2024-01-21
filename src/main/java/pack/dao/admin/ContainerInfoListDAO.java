package pack.dao.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.container.ContainerDTO;
import pack.dto.form.FormDTO;
import pack.mapper.admin.ContainerInfoListMapper;


import java.util.List;

@Repository
@AllArgsConstructor
public class ContainerInfoListDAO {


   private final ContainerInfoListMapper containerInfoListMapper;

   public List<ContainerDTO> getContainerList(){ // 창고 목록 보여주기
       return containerInfoListMapper.selectContainer();
   }
   
   public int totalRegistered() { // 창고 총 갯수
	      return containerInfoListMapper.totalContainer();
   }
   public List<ContainerDTO> getRegSearch(FormDTO formDTO){ // 창고 검색
       return containerInfoListMapper.selectContainerSearch(formDTO);
	   }
       
   public ContainerDTO containerDetail(String cont_no) { // 창고 상세정보
       return containerInfoListMapper.selectOne(cont_no);
   }
   @Transactional
      public boolean containerDelete(String cont_no) { // 창고 지우기
         boolean b = false;
         int re = containerInfoListMapper.containerDelete(cont_no);
         if (re > 0)
            b = true;
         return b;
      }
}