package pack.dao.container;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.booking.bookingDTO;
import pack.dto.container.ContainerDTO;
import pack.dto.container.ContainMapDTO;
import pack.mapper.container.ContainerMapper;
import pack.mapper.kakao.KakaoMapper;

//재민
@Repository
public class ContainDAO {
   @Autowired
   private KakaoMapper kakaoMapper;

   // 전체자료 읽기
   public List<ContainMapDTO> getContainAll() {
      List<ContainMapDTO> sList = kakaoMapper.MapAll();
      System.out.println(sList);
      return sList;
   }

   @Value("${C:/work/AcornProject/src/main/resources/static/upload/}")
   private String uploadDirectory;

   @Autowired
   private ContainerMapper containerMapper;


   public List<ContainerDTO> getDataAll(String business_num) {
      List<ContainerDTO> list = containerMapper.selectAll(business_num);
      System.out.println(list);
      return list;
   }
   
   public List<ContainerDTO> getDataReserve(String business_num) { // 예약된 창고 정보 보기
      List<ContainerDTO> list = containerMapper.selectReserve(business_num);
      System.out.println(list);
      return list;
   }

   public ContainerDTO conDetail(String cont_no) { // 상세보기 용
      System.out.println("cont_no : " + cont_no);
      ContainerDTO conDto = containerMapper.selectOne(cont_no);
      System.out.println(conDto);
      return conDto;
   }
   
   public bookingDTO selectBookCont(String cont_no) {
	   bookingDTO bookDto = containerMapper.selectBookCont(cont_no);
	   System.out.println(bookDto);
	   return bookDto;
   }
   

   @Transactional
   public boolean insertContainer(ContainerDTO containerDTO) {
      boolean b = false;
      int re = containerMapper.insertContainer(containerDTO);
      if (re > 0)
         b = true;
      return b;
   }

   @Transactional // 성공하면 커밋 실패하면 롤백
   public boolean updateContainer(ContainerDTO containerDTO) {
      boolean b = false;
      int re = containerMapper.updateContainer(containerDTO);
      if (re > 0)
         b = true;
      return b;
   }

   @Transactional // 성공하면 커밋 실패하면 롤백
   public boolean deleteContainer(String cont_no) {
      boolean b = false;
      int re = containerMapper.deleteContainer(cont_no);
      if (re > 0)
         b = true;
      return b;
   }
}