package pack.mapper.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


import pack.dto.admin.AdminDTO;

@Mapper
public interface ApproveMapper {
	
	@Update("update container set cont_status='1' where cont_no=#{cont_no}")
	int approve(AdminDTO adminDTO);
	
	@Update("update container set cont_status='4' where cont_no=#{cont_no}")
	int deny(AdminDTO adminDTO);
}
