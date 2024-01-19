package pack.controller.container;

import java.io.*;

import java.util.ArrayList;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import pack.dao.container.ContainDAO;
import pack.dto.container.ContainerDTO;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper; // Jackson 라이브러리 추가
import pack.dto.upload.UploadFileDTO;

@Controller
@RequestMapping(value = "owner")
@AllArgsConstructor
public class ContainerController {


	private final ContainDAO containDao;


	@GetMapping("/paid")
	public String cont_pay() {
		return "container/container-paid";
	}

	@GetMapping("/register")
	public String cont_regs() {
		return "container/container-register";
	}


	@GetMapping("/list")
	public String cont_mgr(Model model, HttpSession session) {
		String business_num = (String) session.getAttribute("business_num");
		ArrayList<ContainerDTO> cList = (ArrayList<ContainerDTO>) containDao.getDataAll(business_num);
		model.addAttribute("datas", cList);
		return "container/container-list";
	}
	
	@GetMapping("/reserve")
	public String cont_reserve(Model model, HttpSession session) {
		String business_num = (String) session.getAttribute("business_num");
		ArrayList<ContainerDTO> rlist = (ArrayList<ContainerDTO>) containDao.getDataReserve(business_num);
		model.addAttribute("datas", rlist);
		return "container/container-reserve";
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

	@PostMapping("/insert")
	public String insertSubmit(@ModelAttribute ContainerDTO containerDTO, UploadFileDTO uploadFileDTO, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "container/container-error";
		}

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			MultipartFile file = uploadFileDTO.getFile();
			String originalFilename = file.getOriginalFilename();
			String randomFilename = UUID.randomUUID().toString();

			String fileExtension = "";
			if (originalFilename != null && originalFilename.contains(".")) {
				fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
			}

			if (originalFilename != null && !originalFilename.isEmpty()) {
				randomFilename = originalFilename.substring(0, originalFilename.lastIndexOf('.')) + "_" + randomFilename + fileExtension;
			} else {
				randomFilename += fileExtension;
			}

			String fileSavePath = "C:/work/AcornProject/src/main/resources/static/upload/" + randomFilename;

			File directory = new File(fileSavePath).getParentFile();
			if (!directory.exists()) {
				directory.mkdirs(); // 상위 디렉터리 생성
			}

			File newFile = new File(fileSavePath);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			inputStream = file.getInputStream();
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			containerDTO.setCont_image(randomFilename);

			boolean isInserted = containDao.insertContainer(containerDTO);
			if (!isInserted) {
				throw new Exception("Database insert failed");
			}
		} catch (Exception e) {
			return "container/container-error";
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return "redirect:/owner/list";
	}

	@GetMapping("/detail")
	public String conDetail(@RequestParam("cont_no") String cont_no, Model model) {
		ContainerDTO conDto = containDao.conDetail(cont_no);
		model.addAttribute("conDto", conDto);

		return "container/container-detail";
	}
	
	

	@GetMapping("/goUpdate")
	public String cont_update(@RequestParam("cont_no") String cont_no, Model model) {
		ContainerDTO conDto = containDao.conDetail(cont_no);
		model.addAttribute("conDto", conDto);
		return "container/container-update";
	}

	@PostMapping("update")
	public String update(ContainerDTO containerDTO) {
		boolean b = containDao.updateContainer(containerDTO);
		if (b)
			return "redirect:/owner/list"; // 수정 후 목록보기
		else
			return "container/container-error";
	}
	

	@GetMapping("delete")
	public String delete(@RequestParam("cont_no") String cont_no) {
		boolean b = containDao.deleteContainer(cont_no);
		if (b)
			return "redirect:/owner/list"; // 수정 후 목록보기
		else
			return "container/container-error";
	}

}