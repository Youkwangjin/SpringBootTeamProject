package pack.dao.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pack.dto.form.FormDTO;
import pack.dto.owner.OwnerDTO;
import pack.mapper.admin.OwnerInfoListMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class OwnerInfoListDAO {

   private final OwnerInfoListMapper ownerInfoListMapper;
   public List<OwnerDTO> getOwnerAll(){
       return ownerInfoListMapper.selectOwner();
   }
   
   public List<OwnerDTO> getOwnerSearch(FormDTO formDTO){
       return ownerInfoListMapper.selectOwnerSearch(formDTO);
   }
   
   public int totalOwner() {
	      return ownerInfoListMapper.ownerRecords();
   }
   
   public boolean ownerDelete(String business_num) {
		boolean b = false;
		int re = ownerInfoListMapper.ownerDeleteData(business_num);
		if (re > 0)
			b = true;
		return b;
	}
   
   public int getOwnerRecords() {
       return ownerInfoListMapper.ownerRecords();
   }
}