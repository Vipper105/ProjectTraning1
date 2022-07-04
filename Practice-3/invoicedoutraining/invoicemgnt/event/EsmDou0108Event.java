/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : EsmDou0108Event.java
*@FileTitle : Invoice Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.event;

import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SummaryVO;
import com.clt.framework.support.layer.event.EventSupport;


/**
 * ESM_DOU_0108 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  ESM_DOU_0108HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author dinhhuy
 * @see ESM_DOU_0108HTMLAction 참조
 * @since J2EE 1.6
 */

public class EsmDou0108Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	SummaryVO summaryVO = null;
	DetailsVO detailsVO = null;
	
		
	/** Table Value Object Multi Data 처리 */
	SummaryVO[] summaryVOs = null;
	DetailsVO[] detailsVOs = null;
	

	public EsmDou0108Event(){}
	
	/**
	 * 
	 * @param SummaryVO summaryVO
	 */
	
	public void setSummaryVO(SummaryVO summaryVO){
		this. summaryVO = summaryVO;
	}

	/**
	 * 
	 * @param SummaryVO[] summaryVOs
	 */
	
	public void setSummaryVOS(SummaryVO[] summaryVOs){
		this. summaryVOs = summaryVOs;
	}

	public SummaryVO getSummaryVO(){
		return summaryVO;
	}

	public SummaryVO[] getSummaryVOS(){
		return summaryVOs;
	}

	public DetailsVO getDetailsVO() {
		return detailsVO;
	}

	/**
	 * 
	 * @param DetailsVO detailsVO
	 */
	public void setDetailsVO(DetailsVO detailsVO) {
		this.detailsVO = detailsVO;
	}

	public DetailsVO[] getDetailsVOs() {
		return detailsVOs;
	}

	/**
	 * 
	 * @param DetailsVO[] detailsVOs
	 */
	public void setDetailsVOs(DetailsVO[] detailsVOs) {
		this.detailsVOs = detailsVOs;
	}
}