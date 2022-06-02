/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_ERR_0001HTMLAction.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.errormanagementtraining.errormsgmanagerment.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.errormanagementtraining.errormsgmanagerment.vo.ErrMsgVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

/**
 * HTTP Parser<br>
 * - com.clt.apps.opus.esm.clv.errormanagementtraining 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 ErrorManagementTrainingSC로 실행요청<br>
 * - ErrorManagementTrainingSC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 * @author dinhhuy
 * @see ErrorManagementTrainingEvent 참조
 * @since J2EE 1.6
 */

public class DOU_ERR_0001HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * DOU_ERR_0001HTMLAction 객체를 생성
	 */
	public DOU_ERR_0001HTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 ErrorManagementTrainingEvent로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		DouErr0001Event event = new DouErr0001Event();
		
		if(command.isCommand(FormCommand.MULTI)) {
			event.setErrMsgVOS((ErrMsgVO[])getVOs(request, ErrMsgVO .class,""));
		}
		else if(command.isCommand(FormCommand.SEARCH)) {
//			event.setErrMsgVO((ErrMsgVO)getVO(request, ErrMsgVO .class));
			ErrMsgVO errMstg = new ErrMsgVO();
			errMstg.setErrMsgCd(JSPUtil.getParameter(request, "s_err_msg_cd", ""));
//			System.err.println("err code nhập vào"+ errMstg.getErrMsgCd());
			errMstg.setErrMsg(JSPUtil.getParameter(request, "s_err_msg", ""));
			event.setErrMsgVO(errMstg);
		}

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