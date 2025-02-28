package com.sc.main.page;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sc.main.service.ReservService;
import com.sc.main.service.ReviewService;
import com.sc.main.service.RoomService;
import com.sc.main.vo.ModalReviewVO;
import com.sc.main.vo.PagingVO;
import com.sc.main.vo.ReviewImgVO;
import com.sc.main.vo.ReviewVO;

@Controller
@RequestMapping("/page/review")
public class ReviewController {
	ModelAndView mav;
	
	@Inject
	ReviewService service;
	
	@Inject
	ReservService reserv_service;
	
	//紐⑤떖�뙘�뾽(鍮꾨룞湲�)
	@PostMapping("/modalReview")
	@ResponseBody
	//public ReviewVO modalReview(@RequestBody String reviewno, String roomno) {
	public ModalReviewVO modalReview(@RequestBody Map<String, Object> requestData) {
		//System.out.println("reviewno->"+requestData.get("reviewno"));
		//System.out.println("roomno->"+requestData.get("roomno"));
		
		int reviewno = Integer.parseInt(requestData.get("reviewno").toString());
		int roomno = Integer.parseInt(requestData.get("roomno").toString());
		
		//議고쉶�닔 利앷�
		service.reviewHitCount(reviewno);
		ModalReviewVO mrvo= new ModalReviewVO();
		mrvo.setVo(service.reviewDetail(reviewno));
		mrvo.setRivo(service.reviewImageSelect(reviewno));
		return mrvo;
	}
	
	//由щ럭 �옉�꽦�뤌
	@GetMapping("/write")
	public ModelAndView reviewWrite(HttpServletRequest request, HttpSession session) {
		String userid=(String) session.getAttribute("loginId");
		mav=new ModelAndView();
		mav.setViewName("page/review/review_write");
		System.out.println(service.reviewHistory(userid));
		mav.addObject("rVO", service.reviewHistory(userid));
		return mav;
	}

	//由щ럭 �옉�꽦(DB), �뙆�씪 �뾽濡쒕뱶
	@PostMapping("/writeOk")
	public ModelAndView reviewWriteOk(ReviewVO vo, HttpServletRequest request, ReviewImgVO imgVO, MultipartFile[] mf, RedirectAttributes redirect) {
		HttpSession session = request.getSession();
		mav = new ModelAndView();
		String userid = (String)session.getAttribute("loginId");
		if(vo.getReservno()==-1) {
			redirect.addAttribute("msg","rir");
			mav.setViewName("redirect:/notice");
			return mav;
		}
		vo.setUserid(userid);
		vo.setRoomno(reserv_service.selectRoomnoByReservNo(vo.getReservno()));
		int result = service.reviewInsert(vo);
		int reviewno = service.reviewImage(userid);
		
		for(MultipartFile f: mf) {
			String path = session.getServletContext().getRealPath("/uploadfile/"+Integer.toString(reviewno));
			String orgFilename = f.getOriginalFilename();
			System.out.println(orgFilename+"!!!");
			File file = new File(path, orgFilename);
			orgFilename = fileRename(file, path, orgFilename);
			System.out.println(orgFilename+"???");
			try {
				file = new File(path, orgFilename);
				f.transferTo(file);
			}catch(Exception e) {e.printStackTrace();}
			
			imgVO.setFilename(orgFilename);
			
			int imgResult = 0;
			
			try {
				imgVO.setReviewno(reviewno);
				imgResult = service.imgInsert(imgVO);	
			}catch(Exception e) {
				e.printStackTrace();
				File fi = new File(path, orgFilename);
				fi.delete();
			}
			if(result==1) {
				mav.setViewName("redirect:/page/review");
			}else {
				mav.setViewName("page/review/review_write");
			}
		}

		return mav;
	}
	
	public String fileRename(File file, String path, String orgFilename) {
		if(file.exists()) {
			for(int i=1; ;i++) {
				int point = orgFilename.lastIndexOf(".");
				String f = orgFilename.substring(0, point);
				String ext = orgFilename.substring(point+1);
				
				String newFilename = f + " ("+i+")."+ext;//01(1).jpeg
				file = new File(path, newFilename);
				if(!file.exists()) {
					orgFilename = newFilename;
					break;
				}
			}
		}
		return orgFilename;
	}

	@PostMapping("/edit")
	public ModelAndView reviewEdit(String reviewno) {
		mav = new ModelAndView();
		mav.addObject("vo", service.reviewDetail(Integer.parseInt(reviewno)));	
		mav.addObject("imgVO", service.reviewImageSelect(Integer.parseInt(reviewno)));
		mav.setViewName("page/review/review_edit");
		
		return mav;
	}
	
	@PostMapping("/editOk")
	public ModelAndView reviewEdit(ReviewVO vo, ReviewImgVO imgVO, MultipartFile[] mf, HttpSession session) {
		mav = new ModelAndView();
		int reviewno = service.reviewImage((String)session.getAttribute("loginId"));
		String path = session.getServletContext().getRealPath("/uploadfile/"+Integer.toString(reviewno));

		ArrayList<ReviewImgVO> orgVO = service.reviewImageSelect(vo.getReviewno());
		String orgFilename="";
		int flag=0;
		int over;
		if(orgVO.size() > mf.length) {
			flag=1;
			over = orgVO.size()-mf.length;
		}
		if(orgVO.size() < mf.length) {
			flag=2;
		}
		String p="";
		boolean isOk=true;
		try {
			p = mf[0].getOriginalFilename();
		} catch(Exception e) {
			isOk=false;
		}
		System.out.println(isOk);
		if(isOk && (p!=null && !p.equals(""))) {
				for(int i=0;i<orgVO.size();i++) {
					try {
						if(orgVO.get(i).getFilename()!=null) {
							File fi = new File(path, orgVO.get(i).getFilename());
							int x = orgVO.get(i).getImgno();
							orgVO.get(i).setImgno(x);
							fi.delete();
							File folder = new File(path,"");
							folder.delete();
						}
					} catch(Exception ee) {
						ee.printStackTrace();
					}
				}
			int idx=-1;
			for(MultipartFile f : mf) {
				if(++idx == orgVO.size()) break;
				orgFilename="";
				if(f!=null) {
					try {
						orgFilename = f.getOriginalFilename();
					} catch(Exception e) {
						orgFilename="";
					}
					if(orgFilename!="") {
						File file = new File(path, orgFilename);
						orgFilename = fileRename(file, path, orgFilename);
						//�뾽濡쒕뱶
						try {
							file = new File(path, orgFilename);
							f.transferTo(file);
						}catch(Exception e) {
							System.out.println("�뙆�씪 �뾽濡쒕뱶�떆 �뿉�윭 ->"+e);
						}
						orgVO.get(idx).setFilename(orgFilename);
					}
					try {
						System.out.println(orgVO.get(idx).toString());
						service.reviewImageUpdate(orgVO.get(idx));
	
						mav.setViewName("redirect:/page/review");
					}catch(Exception e) {
						if(imgVO.getFilename()!=null) {
							File fi = new File(path, imgVO.getFilename());
							fi.delete();
						}
						mav.setViewName("page/review/review_result");
					}
				}
			}
			if(flag == 1) {
				System.out.println("flag"+flag);
				for(int i=mf.length;i<orgVO.size();i++) {
					service.reviewImageDelete(orgVO.get(i).getImgno());
				}
			}
			if(flag == 2) {
				System.out.println("flag"+flag);
				for(int i=idx;i<mf.length;i++) {
					orgFilename = mf[i].getOriginalFilename();
					
					File file = new File(path, orgFilename);
					orgFilename = fileRename(file, path, orgFilename);
					
					try {
						file = new File(path, orgFilename);
						mf[i].transferTo(file);
					}catch(Exception e) {e.printStackTrace();}
					
					imgVO.setFilename(orgFilename);
					
					int imgResult = 0;
					
					try {
						imgVO.setReviewno(reviewno);
						imgResult = service.imgInsert(imgVO);	
					}catch(Exception e) { //insert�븯�떎媛� �떎�뙣�븯硫� �뙆�씪�쓣 �궘�젣�빐�빞�븿
						e.printStackTrace();
						//�젅肄붾뱶媛� �깮�꽦�릺吏� �븡�븘 �씠誘� �뾽濡쒕뱶 �릺�뼱�엳�뒗 �뙆�씪�쓣 �궘�젣�빐�빞�븳�떎.
						File fi = new File(path, orgFilename);
						fi.delete();
					}
				}
			}
		} else {
			mav.setViewName("redirect:/page/review");
		}
		int result = service.reviewUpdate(vo);	//review 湲� �뾽�뜲�씠�듃
	
		return mav;
	}

	//由щ럭 �궘�젣
	@PostMapping("/delete")
	public ModelAndView reviewDelete(String reviewno, HttpServletRequest request) {
		
		
		mav = new ModelAndView();
		
		
		/////////////////////////////////////////////////////
		
		//�빐�떦�젅肄붾뱶 癒쇱� �꽑�깮
		ArrayList<ReviewImgVO> imgVO = service.reviewImageSelect(Integer.parseInt(reviewno));
		System.out.println("reviewno="+reviewno);
		System.out.println(imgVO.toString());
		for(int t=0;t<imgVO.size();t++) {
			try {
				String path = request.getSession().getServletContext().getRealPath("/uploadfile/"+reviewno);
				File file = new File(path, imgVO.get(t).getFilename());
				file.delete();
				File folder = new File(path,"");
				folder.delete();
				mav.setViewName("redirect:/page/review");
			}catch(Exception e) {
				mav.setViewName("page/review/review_result");
			}
		}
		int result = service.reviewDelete(Integer.parseInt(reviewno));
		if(result>0) {
			mav.setViewName("redirect:/page/review");
		}else {
			mav.setViewName("page/review/review_result");
		}
		
		return mav;
	}

	@GetMapping("/mypageReview")
    @ResponseBody
    public List<ReviewVO> mypageReview(HttpSession session) {
        String userid = (String) session.getAttribute("loginId");
        if (userid != null) {
            PagingVO pVO = new PagingVO();
            pVO.setUserid(userid);
            return service.reviewSelectByUserid(pVO);
        }
        return null;
    }
}
