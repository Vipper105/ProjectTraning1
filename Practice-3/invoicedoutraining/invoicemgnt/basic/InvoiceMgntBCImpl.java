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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.integration.InvoiceMgntDBDAO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
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
	public List<SummaryVO> getPartner(SummaryVO summaryVO) throws EventException {
		try {
			return dbDao.searchPartner(summaryVO);
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
	public List<SummaryVO> searchTrade(SummaryVO summaryVO) throws EventException {
		try {
			return dbDao.getTrade(summaryVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	/**
	 * 
	 * @param DetailsVO detailsVO
	 * @return List<Object>
	 * @exception EventException
	 */
	@Override
	public List<Object> excelDownloadFromServer(DetailsVO detailsVO) throws EventException {
		try{
			DBRowSet rs = dbDao.excelDownloadFromServer(detailsVO);
			List<Object> li=new ArrayList<>();
			Map<Object, Object> mp=null;
			String prefix = "sheet2_";
			while (rs.next()){
				mp=new HashMap<>(); 
				mp.put(prefix+   "csr_no",rs.getString("CSR_NO"));
			    mp.put(prefix+ "inv_rev_act_amt",rs.getString("INV_REV_ACT_AMT")); 
			    mp.put(prefix+ "locl_curr_cd",rs.getString("LOCL_CURR_CD"));
			    mp.put(prefix+ "cust_vndr_seq",rs.getString("CUST_VNDR_SEQ")); 
			    mp.put( prefix+"jo_crr_cd",rs.getString("JO_CRR_CD"));
			    mp.put(prefix+ "rlane_cd",rs.getString("RLANE_CD")); 
			    mp.put(prefix+ "rev_exp",rs.getString("REV_EXP"));
			    mp.put(prefix+ "cust_vndr_cnt_cd",rs.getString("CUST_VNDR_CNT_CD")); 
			    mp.put(prefix+ "inv_no",rs.getString("INV_NO"));
			    mp.put(prefix+ "cust_vndr_eng_nm",rs.getString("CUST_VNDR_ENG_NM"));
			    mp.put(prefix+ "inv_exp_act_amt",rs.getString("INV_EXP_ACT_AMT")); 
			    mp.put(prefix+ "item",rs.getString("ITEM"));
			    mp.put(prefix+ "prnr_ref_no",rs.getString("PRNR_REF_NO")); 
			    mp.put(prefix+ "apro_flg",rs.getString("APRO_FLG")); 
			    li.add(mp); 
			}

			return li;
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		

	}
	
}