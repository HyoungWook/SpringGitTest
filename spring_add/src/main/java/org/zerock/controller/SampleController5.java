package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.ProductVO;

@Controller
public class SampleController5 {
	@RequestMapping("doJSON")
	public @ResponseBody ProductVO doJSON(){
		ProductVO vo = new ProductVO("샘플프로닥트", 30000);
		
		return vo; //vo => JSON코드로
	}
}
