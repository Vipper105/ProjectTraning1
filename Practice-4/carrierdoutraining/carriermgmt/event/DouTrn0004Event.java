/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTrn0001Event.java
*@FileTitle : Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.14
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.14 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CarrierVO;


/**
 * DOU_TRN_0001 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  DOU_TRN_0001HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author dinhhuy
 * @see DOU_TRN_0004HTMLAction 참조
 * @since J2EE 1.6
 */

public class DouTrn0004Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	CarrierVO carrierVO = null;
	
	/** Table Value Object Multi Data 처리 */
	CarrierVO[] carrierVOs = null;

	public DouTrn0004Event(){}
	
	public void setCarrierVO(CarrierVO carrierVO){
		this. carrierVO = carrierVO;
	}

	public void setCarrierVOS(CarrierVO[] carrierVOs){
		this. carrierVOs = carrierVOs;
	}

	public CarrierVO getCarrierVO(){
		return carrierVO;
	}

	public CarrierVO[] getCarrierVOS(){
		return carrierVOs;
	}

}