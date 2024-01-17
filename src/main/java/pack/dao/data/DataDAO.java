package pack.dao.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.dto.form.FormDTO;
import pack.repository.data.DataMapper;
import pack.dto.container.ContainerDTO;
import pack.dto.owner.OwnerDTO;
import pack.dto.user.UserDTO;

@Repository
public class DataDAO {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   private DataMapper dataMapper;
   public DataDAO() {
      
   }
   // User 정보 출력하기
   public List<UserDTO> getUserAll(){
      List<UserDTO> list = dataMapper.selectAll();
      logger.info("Userdatas1 : " + list.size() + "개");
      return list;
   }
   public List<UserDTO> getUserSearch(FormDTO formDTO){
      List<UserDTO> list = (List<UserDTO>)dataMapper.selectSearch(formDTO);
      return list;
   }
   
   public int totalUser() {
	      return dataMapper.totalUser();
   }
   
   public boolean userdelete(String user_id) {
		boolean b = false;
		int re = dataMapper.userdeleteData(user_id);
		if (re > 0)
			b = true;
		return b;
	}
   
   public int usercount() {
       
       return dataMapper.usercount();
   }

   // Owner 정보 출력 및 검색하기
   public List<OwnerDTO> getOwnerAll(){
      List<OwnerDTO> list2 = dataMapper.selectAll2();  // sql문이 실행
      logger.info("Ownerdatas2 : " + list2.size() + "개");
      return list2;
   }
   
   public List<OwnerDTO> getOwnerSearch(FormDTO formDTO){   // 검색용
      List<OwnerDTO> list = (List<OwnerDTO>)dataMapper.selectSearch2(formDTO); // sql문이 실행
      logger.info("Ownersearch datas : " + list.size() + "개");
      return list;
   }
   
   public int totalOwner() {
	      return dataMapper.ownerrecords();
   }
   
   public boolean ownerdelete(String business_num) {
		boolean b = false;
		int re = dataMapper.ownerdeleteData(business_num);
		if (re > 0)
			b = true;
		return b;
	}
   
   public int getownerrecords() {
       
       return dataMapper.ownerrecords();
   }
  
   // Container 정보 출력 및 검색하기
   public List<ContainerDTO> getConAll(){
      List<ContainerDTO> list3 = dataMapper.selectAll3();  // sql문이 실행
      logger.info("datas3 : " + list3.size() + "개");
      return list3;
   }
   
   public int totalRegistered() {
	      return dataMapper.totalContainer();
   }
   
   public List<ContainerDTO> getRegSearch(FormDTO formDTO){   // 검색용
	      List<ContainerDTO> list = (List<ContainerDTO>)dataMapper.selectSearch3(formDTO); // sql문이 실행
	      logger.info("Regsearch datas : " + list.size() + "개");
	      return list;
	   }
   
   // Cotainer 세부정보 보기 및 수정 삭제하기
   public ContainerDTO condetail(String cont_no) {  // 상세보기용
      
       ContainerDTO containerDto = dataMapper.selectOne(cont_no);
       return containerDto;
   }

   
   @Transactional  // detail.html에서 삭제버튼 누르면 삭제하도록 하기
      public boolean condelete(String cont_no) {
         boolean b = false;
         int re = dataMapper.delete(cont_no);
         if (re > 0)
            b = true;
         
         return b;
      }
   
   // 사용한 창고 정보 출력 및 검색하기
      public List<ContainerDTO> getUserCon(){
         List<ContainerDTO> list4 = dataMapper.selectAll4();  // sql문이 실행
         logger.info("datas4 : " + list4.size() + "개");
         return list4;
      }

}