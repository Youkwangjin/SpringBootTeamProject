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

   @Autowired
   private DataMapper dataMapper;
   public DataDAO() {
      
   }
   // User 정보 출력하기
   public List<UserDTO> getUserAll(){
       return dataMapper.selectUser();
   }
   public List<UserDTO> getUserSearch(FormDTO formDTO){
       return (List<UserDTO>)dataMapper.selectUserSearch(formDTO);
   }
   public int totalUser() {
	      return dataMapper.totalUser();
   }
   
   public boolean userDelete(String user_id) {
		boolean b = false;
		int re = dataMapper.userDeleteData(user_id);
		if (re > 0)
			b = true;
		return b;
	}
   
   public int userCount() {
       
       return dataMapper.userCount();
   }

   // Owner 정보 출력 및 검색하기
   public List<OwnerDTO> getOwnerAll(){
       return dataMapper.selectOwner();
   }
   
   public List<OwnerDTO> getOwnerSearch(FormDTO formDTO){
       return (List<OwnerDTO>)dataMapper.selectOwnerSearch(formDTO);
   }
   
   public int totalOwner() {
	      return dataMapper.ownerRecords();
   }
   
   public boolean ownerDelete(String business_num) {
		boolean b = false;
		int re = dataMapper.ownerDeleteData(business_num);
		if (re > 0)
			b = true;
		return b;
	}
   
   public int getOwnerRecords() {
       return dataMapper.ownerRecords();
   }

   public List<ContainerDTO> getConAll(){
       return dataMapper.selectContainer();
   }
   
   public int totalRegistered() {
	      return dataMapper.totalContainer();
   }
   
   public List<ContainerDTO> getRegSearch(FormDTO formDTO){
       return (List<ContainerDTO>)dataMapper.selectContainerSearch(formDTO);
	   }

   public ContainerDTO containerDetail(String cont_no) {
       return dataMapper.selectOne(cont_no);
   }
   @Transactional
      public boolean containerDelete(String cont_no) {
         boolean b = false;
         int re = dataMapper.delete(cont_no);
         if (re > 0)
            b = true;
         
         return b;
      }
      public List<ContainerDTO> getUserContainer(){
          return dataMapper.selectMyContainer();
      }
}