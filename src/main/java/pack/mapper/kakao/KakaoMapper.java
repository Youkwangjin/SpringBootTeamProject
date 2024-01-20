package pack.mapper.kakao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pack.dto.container.ContainMapDTO;

//재민
@Mapper
public interface KakaoMapper {
	@Select("select * from container")
	List<ContainMapDTO> MapAll();
}
