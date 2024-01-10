package pack.controller.container;

import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import pack.model.booking.bookingDTO;
import pack.model.container.ContainDao;
import pack.model.container.ContainerDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper; // Jackson 라이브러리 추가

@Controller
@RequestMapping(value = "owner")
public class ContainerController {

	@Autowired
	private ContainDao containDao;

	@GetMapping("/ownerMain")
	public String main() {
		return "container/ownerMain";
	}

	@GetMapping("/paid")
	public String cont_pay() {
		return "container/container_paid";
	}

	@GetMapping("/register")
	public String cont_regs() {
		return "container/container_register";
	}

	@GetMapping("/list")
	public String cont_mgr(Model model, HttpSession session) {
		String business_num = (String) session.getAttribute("business_num");
		ArrayList<ContainerDto> clist = (ArrayList<ContainerDto>) containDao.getDataAll(business_num);
		model.addAttribute("datas", clist);
		return "container/container_list";
	}
	
	@GetMapping("/reserve")
	public String cont_reserve(Model model, HttpSession session) {
		String business_num = (String) session.getAttribute("business_num");
		ArrayList<ContainerDto> rlist = (ArrayList<ContainerDto>) containDao.getDataReserve(business_num);
		model.addAttribute("datas", rlist);
		return "container/container_reserve";
	}


	private double[] getCoordinatesFromAddress(String address) {
		double[] coordinates = new double[2]; // 배열 초기화

		try {
			String apiKey = "AIzaSyDzGKmDfbyNTWo-0WqNSdQlQSlxc6Wjna4";
			String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;

			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// JSON 응답에서 위도와 경도 추출
			Map<String, Object> data = new ObjectMapper().readValue(response.toString(), Map.class);
			if (data.containsKey("results")) {
				Map<String, Object> result = ((List<Map<String, Object>>) data.get("results")).get(0);
				if (result.containsKey("geometry")) {
					Map<String, Object> geometry = (Map<String, Object>) result.get("geometry");
					if (geometry.containsKey("location")) {
						Map<String, Double> location = (Map<String, Double>) geometry.get("location");
						coordinates[0] = location.get("lat");
						coordinates[1] = location.get("lng");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return coordinates;
	}

	private boolean isAllowedExtension(String filename) {
        String[] allowedExtensions = {".jpg", ".jpeg", ".png"};
        for (String extension : allowedExtensions) {
            if (filename.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
	
	@PostMapping("insert")
	   public String insertSubmit(FormBean bean, UploadFile uploadFile, BindingResult result, HttpSession session) {

	      String business_num = (String) session.getAttribute("business_num");
	      InputStream inputStream = null;
	      OutputStream outputStream = null;

	      MultipartFile file = uploadFile.getFile();
	      
	      String originalFilename = file.getOriginalFilename();
	      String randomFilename = UUID.randomUUID().toString();;
	      
	      if (!isAllowedExtension(originalFilename)) {
	          return "errorExtension";
	      }
	      
	      String fileExtension = "";
	      
	      if (originalFilename != null && originalFilename.contains(".")) {
	         fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
	      }

	      if (originalFilename != null && !originalFilename.isEmpty()) {

	    	    randomFilename = originalFilename.substring(0, originalFilename.lastIndexOf('.')) + "_" + randomFilename + fileExtension;
	    	} else {
	    	    randomFilename += fileExtension;
	    	}

	      if (result.hasErrors()) {
	         return "errorFile";
	      }

	      try {
	         inputStream = file.getInputStream();

			// 추후 경로 수정 필요!
	         String fileSavePath = "C:/Users/kwang/git/Team/src/main/resources/static/upload/" + randomFilename;

	         File newFile = new File(fileSavePath);
	         if (!newFile.exists()) {
	            newFile.createNewFile();
	         }

	         outputStream = new FileOutputStream(newFile);
	         int read = 0;
	         byte[] bytes = new byte[1024];
	         while ((read = inputStream.read(bytes)) != -1) {
	            outputStream.write(bytes, 0, read);
	         }
	         bean.setCont_image(randomFilename);
	         
	      } catch (Exception e) {
	         System.out.println("file submit err : " + e);
	         return "err";
	      } finally {
	         try {
                 assert inputStream != null;
                 inputStream.close();
                 assert outputStream != null;
                 outputStream.close();
	         } catch (Exception e2) {
	            // TODO: handle exception
	         }
	      }

	      boolean b = containDao.insertContainer(bean);


	      String address = bean.getCont_addr();
	      double[] coordinates = getCoordinatesFromAddress(address);
	      bean.setCont_we(coordinates[0]);
	      bean.setCont_kyung(coordinates[1]);
	      if (b) {
	         return "redirect:/owner/list";
	      } else {
	         return "error";
	      }
	   }

	@GetMapping("/detail")
	public String conDetail(@RequestParam("cont_no") String cont_no, Model model) {
		ContainerDto conDto = containDao.conDetail(cont_no);
		model.addAttribute("conDto", conDto);

		return "container/container_detail";
	}
	
	

	@GetMapping("/goUpdate")
	public String cont_update(@RequestParam("cont_no") String cont_no, Model model) {
		ContainerDto conDto = containDao.conDetail(cont_no);
		model.addAttribute("conDto", conDto);
		return "container/container_update";
	}

	@PostMapping("update")
	public String update(FormBean bean) {	
		boolean b = containDao.update(bean);
		if (b)
			return "redirect:/owner/list"; // 수정 후 목록보기
		else
			return "error";
	}
	

	@GetMapping("delete")
	public String delete(@RequestParam("cont_no") String cont_no) {
		boolean b = containDao.delete(cont_no);
		if (b)
			return "redirect:/owner/list"; // 수정 후 목록보기
		else
			return "error";
	}
}