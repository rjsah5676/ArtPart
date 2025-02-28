package com.sc.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeController {
	
	@RequestMapping(value="/notice")
	public ModelAndView doNotice(String msg){
		ModelAndView mav = new ModelAndView();
		if(msg.equals("rir")) mav.addObject("msg","���� �ۼ��ϱ� ���� ���� ������ �����ϴ�.");
		if(msg.equals("rdo")) mav.addObject("msg","������ ���������� ��� �Ǿ����ϴ�.");
		if(msg.equals("rio")) mav.addObject("msg","������ ���������� ó�� �Ǿ����ϴ�.");
		if(msg.equals("se")) mav.addObject("msg","������ ���������� �����Ǿ����ϴ�.");
		mav.setViewName("notice/notice");
		return mav;
	}
}
