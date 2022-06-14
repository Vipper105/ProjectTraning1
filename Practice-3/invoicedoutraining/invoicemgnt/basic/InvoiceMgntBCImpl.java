/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : InvoiceMgntBCImpl.java
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

import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.integration.InvoiceMgntDBDAO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SearchPartnerVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;

/**
 * ALPS-InvoiceDouTraining Business Logic Command Interface<br>
 * - ALPS-InvoiceDouTraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author dinhhuy
 * @since J2EE 1.6
 */
public class InvoiceMgntBCImpl extends BasicCommandSupport implements InvoiceMgntBC {

	// Database Access Object
	private transient InvoiceMgntDBDAO dbDao = null;

	/**
	 * InvoiceMgntBCImpl 객체 생성<br>
	 * InvoiceMgntDBDAO를 생성한다.<br>
	 */
	public InvoiceMgntBCImpl() {
		dbDao = new InvoiceMgntDBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchSummaryVO(SummaryVO summaryVO) throws EventException {
		try {
			return dbDao.searchSummaryVO(summaryVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<DetailsVO> searchDetailsVO(DetailsVO detailsVO) throws EventException {
		try {
			return dbDao.searchDetailsVO(detailsVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SearchPartnerVO searchPartnerVO
	 * @return List<SearchPartnerVO>
	 * @exception EventException
	 */
	@Override
	public List<SearchPartnerVO> getAllPartner(SearchPartnerVO searchPartnerVO) throws EventException {
		try {
			return dbDao.searchAllPartner(searchPartnerVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	@Override
	public List<SummaryVO> searchLane(SummaryVO summaryVO) throws EventException{
		try {
			return dbDao.searchLane(summaryVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	@Override
	public List<SummaryVO> searchTrade(SummaryVO summaryVO)
			throws EventException {
		try {
			return dbDao.getTrade(summaryVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
}