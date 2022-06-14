/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : InvoiceMgntDBDAO.java
*@FileTitle : Invoice Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.basic.InvoiceMgntBCImpl;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SearchPartnerVO;
import com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;


/**
 * ALPS InvoiceMgntDBDAO <br>
 * - ALPS-InvoiceDouTraining system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author dinhhuy
 * @see InvoiceMgntBCImpl 참조
 * @since J2EE 1.6
 */
public class InvoiceMgntDBDAO extends DBDAOSupport {

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param JooCarrierVO jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<SummaryVO> searchSummaryVO(SummaryVO summaryVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(summaryVO != null){
				Map<String, String> mapVO = summaryVO .getColumnValues();
			
				List<String> obj_list_no = new ArrayList<>();
				
				if(summaryVO.getJoCrrCd() != null){
					
//					if(summaryVO.getJoCrrCd().equals("ALL")){
//						
//					}
					
					
					String[] crr_cd = summaryVO.getJoCrrCd().split(",");
					if(crr_cd.length > 0){
						for(int i=0;i<crr_cd.length;i++){
							obj_list_no.add(crr_cd[i]);
						}
					}
				}
				
				
				param.putAll(mapVO);
				param.put("obj_list_no",obj_list_no);
				velParam.putAll(mapVO);
				velParam.put("obj_list_no",obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new InvoiceMgntDBDAOSummaryVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	 
	 
	 
		/**
		 * [처리대상] 정보를 [행위] 합니다.<br>
		 * 
		 * @param JooCarrierVO jooCarrierVO
		 * @return List<JooCarrierVO>
		 * @exception DAOException
		 */
		 @SuppressWarnings("unchecked")
		public List<DetailsVO> searchDetailsVO(DetailsVO detailsVO) throws DAOException {
			DBRowSet dbRowset = null;
			List<DetailsVO> list = null;
			//query parameter
			Map<String, Object> param = new HashMap<String, Object>();
			//velocity parameter
			Map<String, Object> velParam = new HashMap<String, Object>();

			try{
				if(detailsVO != null){
					Map<String, String> mapVO = detailsVO .getColumnValues();
				
					param.putAll(mapVO);
					velParam.putAll(mapVO);
				}
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new InvoiceMgntDBDAODetailsVORSQL(), param, velParam);
				list = (List)RowSetUtil.rowSetToVOs(dbRowset, DetailsVO .class);
			} catch(SQLException se) {
				log.error(se.getMessage(),se);
				throw new DAOException(new ErrorHandler(se).getMessage());
			} catch(Exception ex) {
				log.error(ex.getMessage(),ex);
				throw new DAOException(new ErrorHandler(ex).getMessage());
			}
			return list;
		 }

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param JooCarrierVO jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<SearchPartnerVO> searchAllPartner(SearchPartnerVO searchPartnerVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SearchPartnerVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(searchPartnerVO != null){
				Map<String, String> mapVO = searchPartnerVO .getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new InvoiceMgntDBDAOSearchPartnerRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SearchPartnerVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	 
	@SuppressWarnings("unchecked")
	public List<SummaryVO> searchLane(SummaryVO summaryVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (summaryVO != null) {
				Map<String, String> mapVO = summaryVO.getColumnValues();

				List<String> obj_list_no = new ArrayList<>();
				if (null != summaryVO.getJoCrrCd()) {
					String[] joCrrCd = summaryVO.getJoCrrCd().split(",");
					if (joCrrCd.length > 0) {
						for (int i = 0; i < joCrrCd.length; i++) {
							obj_list_no.add(joCrrCd[i]);
						}
					}
				}

				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new InvoiceMgntDBDAOSearchLaneRSQL(), param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, SummaryVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	 
	@SuppressWarnings("unchecked")
	public List<SummaryVO> getTrade(SummaryVO jooCarrierVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (jooCarrierVO != null) {
				Map<String, String> mapVO = jooCarrierVO.getColumnValues();

				List<String> obj_list_no = new ArrayList<>();
				if (null != jooCarrierVO.getJoCrrCd()) {
					String[] joCrrCd = jooCarrierVO.getJoCrrCd().split(",");
					if (joCrrCd.length > 0) {
						for (int i = 0; i < joCrrCd.length; i++) {
							obj_list_no.add(joCrrCd[i]);
						}
					}
				}

				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new InvoiceMgntDBDAOSearchTradeRSQL(), param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, SummaryVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	} 
}