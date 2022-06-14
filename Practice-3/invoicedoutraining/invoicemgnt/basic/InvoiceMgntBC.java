/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : InvoiceMgntBC.java
*@FileTitle : Invoice Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SearchPartnerVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SummaryVO;
import com.clt.framework.core.layer.event.EventException;

/**
 * ALPS-Invoicedoutraining Business Logic Command Interface<br>
 * - ALPS-Invoicedoutraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author dinhhuy
 * @since J2EE 1.6
 */

public interface InvoiceMgntBC {

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchSummaryVO(SummaryVO summaryVO) throws EventException;
	
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param DetailsVO detailsVO
	 * @return List<DetailsVO>
	 * @exception EventException
	 */
	public List<DetailsVO> searchDetailsVO(DetailsVO detailsVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SearchPartnerVO searchPartnerVO
	 * @return List<SearchPartnerVO>
	 * @exception EventException
	 */
	public List<SearchPartnerVO> getAllPartner(SearchPartnerVO searchPartnerVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchLane(SummaryVO summaryVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchTrade(SummaryVO summaryVO) throws EventException;

}