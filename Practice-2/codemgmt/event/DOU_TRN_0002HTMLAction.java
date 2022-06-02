package com.clt.apps.opus.dou.doutraining.codemgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeMgmtVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailByCodeIDVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class DOU_TRN_0002HTMLAction extends HTMLActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	public DOU_TRN_0002HTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 DouTrainingEvent로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	@Override
	public Event perform(HttpServletRequest request) throws HTMLActionException {

		FormCommand command = FormCommand.fromRequest(request);
		DouTrn0002Event event = new DouTrn0002Event();
		
		CodeMgmtVO cdMgmtVO = new CodeMgmtVO();
		
		DetailByCodeIDVO detailByCodeIDVO = new DetailByCodeIDVO();
		 
		if (command.isCommand(FormCommand.SEARCH)) {
			// ↓ ADDED by HuyDP - practice2 - 01-06-2022
			cdMgmtVO.setOwnrSubSysCd(JSPUtil.getParameter(request, "s_ownr_sub_sys_cd",""));
			cdMgmtVO.setIntgCdId(JSPUtil.getParameter(request, "s_intg_cd_id",""));
			// ↑ ADDED by HuyDP - practice2 - 01-06-2022
			event.setCodeVO(cdMgmtVO);
			
			// ↓ ADDED by HuyDP - practice2 - 01-06-2022
		}else if (command.isCommand(FormCommand.SEARCH02)) {
			detailByCodeIDVO.setIntgCdId(JSPUtil.getParameter(request, "codeid",""));
			event.setDetailByCodeIDVO(detailByCodeIDVO);
			// ↑ ADDED by HuyDP - practice2 - 01-06-2022
			
		}else if (command.isCommand(FormCommand.MULTI))  {
			// ↓ ADDED by HuyDP - practice2 - 01-06-2022
 			event.setCodeVOs((CodeMgmtVO[]) getVOs(request, CodeMgmtVO .class, "sheet1_"));  
 			event.setDetailByCodeIDVOs((DetailByCodeIDVO[]) getVOs(request, DetailByCodeIDVO .class,"sheet2_"));
 			// ↑ ADDED by HuyDP - practice2 - 01-06-2022
 			
		}
		return event;
	}

	
	/**
	 * HttpRequest의 attribute에 업무시나리오 수행결과 값 저장<br>
	 * ServiceCommand에서 View(JSP)로 실행결과를 전송하는 ResultSet을 request에 셋팅<br>
	 * Lưu giá trị kết quả thực thi kịch bản nghiệp vụ trong thuộc tính của HttpRequest <br>
	 * Đặt ResultSet truyền kết quả thực thi từ ServiceCommand sang View (JSP) trong yêu cầu <br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * HttpRequest의 attribute에 HttpRequest 파싱 수행결과 값 저장<br>
	 * HttpRequest 파싱 수행결과 값 request에 셋팅<br>
	 * Lưu giá trị kết quả phân tích cú pháp HttpRequest trong thuộc tính HttpRequest <br>
	 * Giá trị kết quả phân tích cú pháp HttpRequest được đặt trong yêu cầu <br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}

