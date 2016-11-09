package org.zerock.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.domain.SearchCriteria;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/sboard/*")	//보드로 시작하는 요청에 대해 시작함.
public class BoardController {
	@Inject
	private BoardService service;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void registerGET(BoardVO vo)throws Exception{
		System.out.println("OK");
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPOST(BoardVO board)throws Exception{
		service.regist(board);
		//return "redirect:/board/listAll";
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listCri(@ModelAttribute("cri") SearchCriteria cri, Model model)throws Exception{	//리턴형이 없을땐 요청된 url로 감.
		
		model.addAttribute("list", service.listCri(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCri(cri));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public String read(@RequestParam("bno") int bno,
					@ModelAttribute("cri") SearchCriteria cri, Model model)throws Exception{
		BoardVO board = service.read(bno);
		model.addAttribute("boardVO", board);
		return "/sboard/readPage";
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public String modify(@ModelAttribute("cri") SearchCriteria cri, @RequestParam("bno") int bno, Model model)throws Exception{
		BoardVO board = service.read(bno);
		model.addAttribute("boardVO", board);
		return "/sboard/modifyPage";
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String board_modify(SearchCriteria cri, BoardVO board, RedirectAttributes rttr)throws Exception{
		
		service.modify(board);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/sboard/listPage";
	}
	
	@RequestMapping("/remove")
	public String remove(@RequestParam("bno") int bno)throws Exception{
		service.remove(bno);
		return "redirect:/board/listPage";
	}
	
}
