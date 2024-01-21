package pack.mapper.admin;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pack.dto.container.ContainerDTO;
import pack.dto.form.FormDTO;

import java.util.List;

@Mapper
public interface ContainerInfoListMapper {

   @Select("select * from container")
   List<ContainerDTO> selectContainer();
   
   @Select("select count(*) from container")
   int totalContainer();
   
   @Select("SELECT * FROM container WHERE cont_name LIKE CONCAT('%', #{searchValue}, '%')")
   List<ContainerDTO> selectContainerSearch(FormDTO formDTO);

   @Select("select * from container where cont_no=#{cont_no}")
   ContainerDTO selectOne(String cont_no);

   @Delete("delete from container where cont_no=#{cont_no}")
   int containerDelete(String cont_no);

}
