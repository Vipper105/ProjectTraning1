package com.clt.apps.opus.esm.clv.carrierdoutraining.customermgnt.event;


import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CustomerVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class CUSTOMERHTMLAction extends HTMLActionSupport {
	private static final long serialVersionUID = 1L;
	/**
	 * CUSTOMERHTMLAction function constructor
	 */
	public CUSTOMERHTMLAction(){
		
	}
	
	/**
	 * Parsing the value of HTML DOM object as a Java variable<br>
	 * Parsing the information of HttpRequest as Practice1Event and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event An object that implements the Event interface.
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
		FormCommand command = FormCommand.fromRequest(request);
		CustomerEvent event = new CustomerEvent();
		
		if(command.isCommand(FormCommand.SEARCH02)) {
			CustomerVO customerVO = new CustomerVO();
			customerVO.setCustCntCd(JSPUtil.getParameter(request, "s_cust_cnt_cd", ""));
			customerVO.setCustSeq(JSPUtil.getParameter(request, "s_cust_seq",	""));
			event.setCustomerVO(customerVO);
		}
		
		return event;
	}
	
	/**
	 * Storing the business scenario execution result value in the attribute of HttpRequest<br>
	 * Setting the ResultSet that transmits the execution result from ServiceCommand to View (JSP) in the request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse object that implements the interface
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}
	
	/**
	 * Saving HttpRequest parsing result value in HttpRequest attribute<br>
	 * HttpRequest parsing result value set in request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event object that implements the interface
	 */  
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}
