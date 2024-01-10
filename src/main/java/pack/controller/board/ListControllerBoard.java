package pack.controller.board;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.board.BoardDaoImpl;
import pack.model.board.BoardDto;


@Controller
@RequestMapping("/board")

public class ListControllerBoard {
	@Autowired
	private BoardDaoImpl daoImpl;
	
	private int tot;
	private int plist = 15;
	private int pagesu;

	// 페이지 당 목록 데이터 추출
	public ArrayList<BoardDto> getListdata(ArrayList<BoardDto> list, int page){
		ArrayList<BoardDto> result = new ArrayList<BoardDto>();
		
		int start = (page - 1) * plist;   // 목록의 인덱스 계산
		System.out.println("start:" + start);
		
		int size = plist <= list.size() - start?plist : list.size() - start;
		
		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
			System.out.println("i:" + i + ", start + i : " + (start + i));
		}
		return result;
	}
	
	// 총 페이지 수 계산
	public int getPageSu() {
		tot = daoImpl.totalCnt();
		pagesu = tot / plist;
		if(tot % plist > 0) pagesu += 1;
		return pagesu;
	}
	
	// 게시판 목록 처리
	@GetMapping("/list")
	public String listProcess(@RequestParam("page")int page, Model model) {  


		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1;
		

		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.listAll();
		ArrayList<BoardDto> result = getListdata(list, spage);
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "board/list";
	}
	

	@GetMapping("/listAdmin")
	public String listAdminProcess(@RequestParam("page")int page, Model model) {
		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1;
		
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.listAll();
		ArrayList<BoardDto> result = getListdata(list, spage);
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "board/listAdmin";
	}
	
	@GetMapping("/listUser")
	public String listUserProcess(@RequestParam("page")int page, Model model) {
		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1;
		
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.listAll();
		ArrayList<BoardDto> result = getListdata(list, spage);
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "board/listUser";
	}
	
	@GetMapping("/listOwner")
	public String listOwnerProcess(@RequestParam("page")int page, Model model) {
		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1;
		
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.listAll();
		ArrayList<BoardDto> result = getListdata(list, spage);
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "board/listOwner";
	}

	@PostMapping("board/search")
	public String searchProcess(BoardBean bean, Model model) {
		System.out.println(bean.getSearchName() + " " + bean.getSearchValue());
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.search(bean);
		
		model.addAttribute("data", list);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", "1");
		return "board/list";
	}
}
