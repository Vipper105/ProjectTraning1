/*=========================================================
*Copyright(c) 2009 CyberLogitec
*@FileName : ErrorMessageHTMLAction.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2009.02.26
*@LastModifier : 김경범
*@LastVersion : 1.0
* 2009.02.26 김경범
* 1.0 Creation
=========================================================*/
package com.clt.syscommon.management.opus.errormessage.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.syscommon.common.table.ComErrMsgVO;


/**
 * HTTP Parser<br>
 * - NIS2010.APP-INF.src.com.clt.syscommon.nis2010.management.errormessagemanagement 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 ErrorMessageManagementSC로 실행요청<br>
 * - ErrorMessageManagementSC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 * @author KyungBum Kim
 * @see ErrorMessageManagementEvent 참조
 * @since J2EE 1.4
 */

public class ErrorMessageHTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * ErrorMessageHTMLAction 객체를 생성
	 */
	public ErrorMessageHTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 ErrorMessageManagementEvent로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		ErrorMessageManagementEvent event = new ErrorMessageManagementEvent();

		ComErrMsgVO comErrMsgVO  = new ComErrMsgVO();
		comErrMsgVO.setErrMsgCd(JSPUtil.getParameter(request, "s_err_msg_cd", ""));
		comErrMsgVO.setErrMsg(JSPUtil.getParameter(request, "s_err_msg", ""));
		comErrMsgVO.setLangTpCd(JSPUtil.getParameter(request, "lang_tp_cd", ""));
		event.setComErrMsgVO(comErrMsgVO);

		if(command.isCommand(FormCommand.MULTI)) {
			event.setComErrMsgVOS((ComErrMsgVO[])getVOs(request, ComErrMsgVO .class,""));
		}

		request.setAttribute("Event", event);

		return  event;
	}

	/**
	 * HttpRequest의 attribute에 업무시나리오 수행결과 값 저장<br>
	 * ServiceCommand에서 View(JSP)로 실행결과를 전송하는 ResultSet을 request에 셋팅<br>
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
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}

}