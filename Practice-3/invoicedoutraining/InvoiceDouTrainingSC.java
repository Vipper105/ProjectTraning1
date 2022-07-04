/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : InvoiceDouTrainingSC.java
*@FileTitle : Invoice Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.invoicedoutraining;

import java.util.List;

import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.basic.InvoiceMgntBC;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.basic.InvoiceMgntBCImpl;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.event.EsmDou0108Event;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.integration.InvoiceMgntDBDAO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;


/**
 * ALPS-InvoiceDouTraining Business Logic ServiceCommand - ALPS-InvoiceDouTraining 대한 비지니스 트랜잭션을 처리한다.
 * 
 * @author dinhhuy
 * @see InvoiceMgntDBDAO
 * @since J2EE 1.6
 */

public class InvoiceDouTrainingSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * InvoiceDouTraining system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		log.debug("InvoiceDouTrainingSC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * InvoiceDouTraining system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("InvoiceDouTrainingSC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-InvoiceDouTraining system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if ("EsmDou0108Event".equalsIgnoreCase(e.getEventName())) {
			if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initData(e);
			}
			
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchSummaryVO(e);
			}
			
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)) {
				eventResponse = searchDetailsVO(e);
			}
						
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH03)) {
				eventResponse = searchLane(e);
			}
			
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH04)) {
				eventResponse = searchTrade(e);
			}
			
			else if (e.getFormCommand().isCommand(FormCommand.COMMAND01)) {
				eventResponse = excelDownloadFromServer(e); 
			}
		}
		return eventResponse;
	}
	
	/**
	 * ESM_DOU_0108 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchSummaryVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		InvoiceMgntBC command = new InvoiceMgntBCImpl();

		try{
			List<SummaryVO> list = command.searchSummaryVO(event.getSummaryVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
		
	/**
	 * ESM_DOU_0108 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchDetailsVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		InvoiceMgntBC command = new InvoiceMgntBCImpl();

		try{
			List<DetailsVO> list = command.searchDetailsVO(event.getDetailsVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * ESM_DOU_0108 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse initData(Event e) throws EventException {
		
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		InvoiceMgntBC command = new InvoiceMgntBCImpl();

		try{
			// 1. Search partners
			List<SummaryVO> listPartners = command.getPartner(event.getSummaryVO());
			StringBuilder partnerBuilder = new StringBuilder();
			
			if(listPartners != null){
				for (int i = 0; i < listPartners.size(); i++) {
					String partner = listPartners.get(i).getJoCrrCd();
					partnerBuilder.append(partner);
					if (i < listPartners.size() - 1) {
						partnerBuilder.append("|");
					}
				}	
			}
			eventResponse.setETCData("partners", partnerBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
			
		}catch (Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
		return eventResponse;
	}
	
	/**
	 * ESM_DOU_0108 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchLane(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		InvoiceMgntBC command = new InvoiceMgntBCImpl();
		try{		
			List<SummaryVO> list = command.searchLane(event.getSummaryVO());
			StringBuilder laneBuilder = new StringBuilder();
			if(list!= null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					laneBuilder.append(list.get(i).getRlaneCd());
					if (i < list.size() - 1){
						laneBuilder.append("|");
					}
				}
			}
			eventResponse.setETCData("lanes",laneBuilder.toString());
			System.out.println(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * ESM_DOU_0108 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchTrade(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		InvoiceMgntBC command = new InvoiceMgntBCImpl();
		try{		
			List<SummaryVO> list = command.searchTrade(event.getSummaryVO());
			StringBuilder tradeBuilder = new StringBuilder();
			if(list!= null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					tradeBuilder.append(list.get(i).getTrdCd());
					if (i < list.size() - 1){
						tradeBuilder.append("|");
					}	
				}
			}
			eventResponse.setETCData("trades",tradeBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * ESM_DOU_0108 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse excelDownloadFromServer(Event e) throws EventException {
		// Solution 1
//		InvoiceMgntBC command = new InvoiceMgntBCImpl();
//		DownExcelFromServer108Event event = (DownExcelFromServer108Event)e;
//		GeneralEventResponse eventResponse = new GeneralEventResponse();
//		SummaryVO joo = event.getSummaryVO();
//		
//		eventResponse.setCustomData("vos", command.searchSummaryVO(event.getSummaryVO()));
//		eventResponse.setCustomData("title", joo.getColumn());
//		eventResponse.setCustomData("columns",joo.getColumn());
//		eventResponse.setCustomData("fileName", "downLoadExcel2.xls");
//		eventResponse.setCustomData("isZip", false);
		
		// Solution 2
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		InvoiceMgntBC command = new InvoiceMgntBCImpl();

		try{
			List<Object> list = command.excelDownloadFromServer(event.getDetailsVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
		return eventResponse;
	}
}