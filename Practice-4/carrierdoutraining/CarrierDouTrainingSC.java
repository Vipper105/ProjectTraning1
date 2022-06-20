/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierDouTrainingSC.java
*@FileTitle : Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.14
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.14 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.carrierdoutraining;

import java.util.List;

import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.basic.CarrierMgmtBC;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.basic.CarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.event.DouTrn0004Event;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.integration.CarrierMgmtDBDAO;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CustomerVO;
import com.clt.apps.opus.esm.clv.carrierdoutraining.customermgnt.event.CustomerEvent;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;


/**
 * ALPS-CarrierDouTraining Business Logic ServiceCommand - ALPS-CarrierDouTraining 대한 비지니스 트랜잭션을 처리한다.
 * 
 * @author dinhhuy
 * @see CarrierMgmtDBDAO
 * @since J2EE 1.6
 */

public class CarrierDouTrainingSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * CarrierDouTraining system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		log.debug("CarrierDouTrainingSC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * CarrierDouTraining system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("CarrierDouTrainingSC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-CarrierDouTraining system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Even4
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if (e.getEventName().equalsIgnoreCase("DouTrn0004Event")) {
			
			if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initData(e);
			}
			
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCarrierVO(e);
			}
			
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageCarrierVO(e);
			}
			
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)) {
				eventResponse = searchCustomerVO(e);
			}
			
			else if (e.getFormCommand().isCommand(FormCommand.COMMAND01)){
				eventResponse = chkDupData(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.COMMAND02) ||
					e.getFormCommand().isCommand(FormCommand.COMMAND04) || 
					e.getFormCommand().isCommand(FormCommand.COMMAND05)){
				eventResponse = chkExistData(e);
			}
			
			
		}
		
		if (e.getEventName().equalsIgnoreCase("CustomerEvent")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCustomerVO(e);
			}
			
		}
		return eventResponse;
	}
	
	/**
	 * DOU_TRN_0004 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchCarrierVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();

		try{
			List<CarrierVO> list = command.searchCarrierVO(event.getCarrierVO());
			eventResponse.setRsVoList(list);
			
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * DOU_TRN_0004 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse manageCarrierVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();

		try{
			begin();
			command.manageCarrierVO(event.getCarrierVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXXXXXX").getUserMessage());
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
	
	/**
	 * DOU_TRN_0004 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse initData(Event e) throws EventException {
		
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
 

		try{
			// 1. Search partners
			List<CarrierVO> list = command.searchCarrierList(event.getCarrierVO());
			eventResponse.setRsVoList(list);
			
			StringBuilder carriersBuilder = new StringBuilder();
			
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					String carriers = list.get(i).getJoCrrCd();
					carriersBuilder.append(carriers);
					if (i < list.size() - 1) {
						carriersBuilder.append("|");
					}
				}	
			}
			
			eventResponse.setETCData("carriers", carriersBuilder.toString());

			List<CarrierVO> listLane = command.searchLane(event.getCarrierVO());
			eventResponse.setRsVoList(listLane);
			
			StringBuilder revLanesBuilder = new StringBuilder();
			
			if(listLane != null){
				for (int i = 0; i < listLane.size(); i++) {
					String lanes = listLane.get(i).getRlaneCd();
					revLanesBuilder.append(lanes);
					if (i < listLane.size() - 1) {
						revLanesBuilder.append("|");
					}
				}	
			}
			
			eventResponse.setETCData("lanes", revLanesBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
			
		}catch (Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
		return eventResponse;
	}
	
	
	/**
	 * DOU_TRN_0004 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchCustomerVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		CustomerEvent event = (CustomerEvent)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();

		try{
			List<CustomerVO> list = command.searchCustomerVO(event.getCustomerVO());
			eventResponse.setRsVoList(list);
			
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}

	/**
	 * 
	 * @param e
	 * @return
	 * @throws EventException
	 */
	private EventResponse chkExistData(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		int num = 0;
		try{
			if (e.getFormCommand().isCommand(FormCommand.COMMAND02)){
				num = command.checkVndrCdInput(event.getCarrierVO());
			}
			else if (e.getFormCommand().isCommand(FormCommand.COMMAND04)){
				num = command.checkTrdCdInput(event.getCarrierVO());
			}
			else if (e.getFormCommand().isCommand(FormCommand.COMMAND05)){
				num = command.checkExistLane(event.getCarrierVO());
			}
			eventResponse.setETCData("ISEXIST", num > 0 ? "Y" : "N");
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * Check duplicate data input.
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @throws EventException
	 */
	private EventResponse chkDupData(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		
		try{
			int num = command.checkDuplicateInput(event.getCarrierVO());
			eventResponse.setETCData("ISEXIST", num > 0 ? "Y" : "N");
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	
}