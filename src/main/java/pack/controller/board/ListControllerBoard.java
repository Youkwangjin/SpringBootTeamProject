package pack.controller.board;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dao.board.BoardDAO;
import pack.dto.board.BoardDTO;


@Controller
@RequestMapping("/board")

public class ListControllerBoard {
	@Autowired
	private BoardDAO daoImpl;
	
	private int tot;
	private int pList = 15;
	private int pageSu;

	// 페이지 당 목록 데이터 추출
	public ArrayList<BoardDTO> getListData(ArrayList<BoardDTO> list, int page){
		ArrayList<BoardDTO> result = new ArrayList<BoardDTO>();
		
		int start = (page - 1) * pList;   // 목록의 인덱스 계산
		System.out.println("start:" + start);
		
		int size = pList <= list.size() - start?pList : list.size() - start;
		
		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
			System.out.println("i:" + i + ", start + i : " + (start + i));
		}
		return result;
	}
	
	// 총 페이지 수 계산
	public int getPageSu() {
		tot = daoImpl.totalCnt();
		pageSu = tot / pList;
		if(tot % pList > 0) pageSu += 1;
		return pageSu;
	}
	
	// 게시판 목록 처리
	@GetMapping("/list")
	public String listProcess(@RequestParam("page")int page, Model model) {  

		int sPage = 0;
		try {
			sPage = page;
		} catch (Exception e) {
			sPage = 1;
		}
		if(page <= 0) sPage = 1;
		

		ArrayList<BoardDTO> list = (ArrayList<BoardDTO>)daoImpl.listAll();
		ArrayList<BoardDTO> result = getListData(list, sPage);
		
		model.addAttribute("data", result);
		model.addAttribute("pageSu", getPageSu());
		model.addAttribute("page", sPage);
		
		return "board/board-list";
	}
	

	@GetMapping("/listAdmin")
	public String listAdminProcess(@RequestParam("page")int page, Model model) {
		int sPage = 0;
		try {
			sPage = page;
		} catch (Exception e) {
			sPage = 1;
		}
		if(page <= 0) sPage = 1;
		
		ArrayList<BoardDTO> list = (ArrayList<BoardDTO>)daoImpl.listAll();
		ArrayList<BoardDTO> result = getListData(list, sPage);
		
		model.addAttribute("data", result);
		model.addAttribute("pageSu", getPageSu());
		model.addAttribute("page", sPage);
		
		return "board/board-list-admin";
	}
	
	@GetMapping("/listUser")
	public String listUserProcess(@RequestParam("page")int page, Model model) {
		int sPage = 0;
		try {
			sPage = page;
		} catch (Exception e) {
			sPage = 1;
		}
		if(page <= 0) sPage = 1;
		
		ArrayList<BoardDTO> list = (ArrayList<BoardDTO>)daoImpl.listAll();
		ArrayList<BoardDTO> result = getListData(list, sPage);
		
		model.addAttribute("data", result);
		model.addAttribute("pageSu", getPageSu());
		model.addAttribute("page", sPage);
		
		return "board/board-list-user";
	}
	
	@GetMapping("/listOwner")
	public String listOwnerProcess(@RequestParam("page")int page, Model model) {
		int sPage = 0;
		try {
			sPage = page;
		} catch (Exception e) {
			sPage = 1;
		}
		if(page <= 0) sPage = 1;
		
		ArrayList<BoardDTO> list = (ArrayList<BoardDTO>)daoImpl.listAll();
		ArrayList<BoardDTO> result = getListData(list, sPage);
		
		model.addAttribute("data", result);
		model.addAttribute("pageSu", getPageSu());
		model.addAttribute("page", sPage);
		
		return "board/board-list-owner";
	}

	@PostMapping("board/search")
	public String searchProcess(BoardDTO boardDTO, Model model) {
		System.out.println(boardDTO.getSearchName() + " " + boardDTO.getSearchValue());
		ArrayList<BoardDTO> list = (ArrayList<BoardDTO>)daoImpl.search(boardDTO);
		
		model.addAttribute("data", list);
		model.addAttribute("pageSu", getPageSu());
		model.addAttribute("page", "1");
		return "board/board-list";
	}
}
