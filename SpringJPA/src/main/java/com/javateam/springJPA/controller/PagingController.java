package com.javateam.springJPA.controller;

// 컨트롤러 클래스에서는 서비스를 호출하여 웹 요청에 대한 응답을 생성
/*
  이 클래스는 사용자로부터 들어오는 요청을 처리하고, 서비스를 호출하여 결과를 반환합니다. 
  컨트롤러는 비즈니스 로직을 구현하지 않지만, 
  서비스를 호출하여 데이터를 가져오고 사용자에게 응답을 제공합니다.
 */
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javateam.springJPA.domain.DemoVO;
import com.javateam.springJPA.service.PagingJpaService;

import lombok.extern.slf4j.Slf4j;
 
@Controller
@Slf4j
public class PagingController {
   
    @Autowired
    private PagingJpaService svc;
    
    @RequestMapping("/")
    public String home() {
    	
    	// 루트 URL에 접근 시 /paging?page=1으로 리디렉션합니다
    	   
    	return "redirect:/paging?page=1";
      }
   
    @RequestMapping("/sort")
    public String sort(Model model) {
       
    	// "오름차순"으로 정렬된 리스트를 반환합니다
        log.info("sort");
       
        model.addAttribute("list", svc.findAll("오름차순"));
       
        return "sorted";
    } //
   
   
    @GetMapping("/paging")
    public String paging(@RequestParam("page") int page, Model model) {
       
    	//  주어진 페이지 번호에 해당하는 페이징된 리스트를 반환합니다.
        log.info("paging");
        
        Page<DemoVO> pageList = svc.findAllByPaging(page, 5);
        List<DemoVO> list = pageList.getContent();
       
        model.addAttribute("total_page", pageList.getTotalPages());
        model.addAttribute("curr_page", page);
        model.addAttribute("list", list);
       
        return "paging";
    } //
   
    @RequestMapping("/member/{id}")
    @ResponseBody
    public DemoVO getOne(@PathVariable int id) {
       
    	// 주어진 id에 해당하는 DemoVO 객체를 JSON 형태로 반환합니다.
        log.info("getOne");
       
        return svc.findById(id);
    } //
 
}