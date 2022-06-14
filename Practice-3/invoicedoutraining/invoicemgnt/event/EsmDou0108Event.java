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
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SearchLaneVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SearchPartnerVO;
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
	SearchPartnerVO searchPartnerVO = null;
	SearchLaneVO searchLaneVO = null;
		
	/** Table Value Object Multi Data 처리 */
	SummaryVO[] summaryVOs = null;
	DetailsVO[] detailsVOs = null;
	SearchPartnerVO[] searchPartnerVOs = null;	
	SearchLaneVO[] searchLaneVOs = null;

	public EsmDou0108Event(){}
	
	/**
	 * 
	 * @param jooCarrierVO
	 */
	
	public void setSummaryVO(SummaryVO jooCarrierVO){
		this. summaryVO = jooCarrierVO;
	}

	/**
	 * 
	 * @param jooCarrierVOs
	 */
	public void setJooCarrierVOS(SummaryVO[] jooCarrierVOs){
		this. summaryVOs = jooCarrierVOs;
	}

	/**
	 * 
	 * @return
	 */
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
	 * @param detailsVO
	 */
	public void setDetailsVO(DetailsVO detailsVO) {
		this.detailsVO = detailsVO;
	}

	public DetailsVO[] getDetailsVOs() {
		return detailsVOs;
	}

	/**
	 * 
	 * @param detailsVOs
	 */
	public void setDetailsVOs(DetailsVO[] detailsVOs) {
		this.detailsVOs = detailsVOs;
	}

	public SearchPartnerVO getSearchPartnerVO() {
		return searchPartnerVO;
	}

	/**
	 * 
	 * @param searchPartnerVO
	 */
	public void setSearchPartnerVO(SearchPartnerVO searchPartnerVO) {
		this.searchPartnerVO = searchPartnerVO;
	}

	public SearchPartnerVO[] getSearchPartnerVOs() {
		return searchPartnerVOs;
	}

	/**
	 * 
	 * @param searchPartnerVOs
	 */
	public void setSearchPartnerVOs(SearchPartnerVO[] searchPartnerVOs) {
		this.searchPartnerVOs = searchPartnerVOs;
	}

	public SearchLaneVO getSearchLaneVO() {
		return searchLaneVO;
	}

	/** 
	 * 
	 * @param searchLaneVO
	 */
	public void setSearchLaneVO(SearchLaneVO searchLaneVO) {
		this.searchLaneVO = searchLaneVO;
	}

	public SearchLaneVO[] getSearchLaneVOs() {
		return searchLaneVOs;
	}

	/**
	 * 
	 * @param searchLaneVOs
	 */
	public void setSearchLaneVOs(SearchLaneVO[] searchLaneVOs) {
		this.searchLaneVOs = searchLaneVOs;
	}

}