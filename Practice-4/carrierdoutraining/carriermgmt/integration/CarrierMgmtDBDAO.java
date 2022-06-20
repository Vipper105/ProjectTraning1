/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierMgmtDBDAO.java
*@FileTitle : Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.14
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.14 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.basic.CarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CustomerVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;


/**
 * ALPS CarrierMgmtDBDAO <br>
 * - ALPS-CarrierDouTraining system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author dinhhuy
 * @see CarrierMgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class CarrierMgmtDBDAO extends DBDAOSupport {

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierListVO
	 * @return List<CarrierListVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<CarrierVO> searchCarrierVO(CarrierVO carrierVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<CarrierVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(carrierVO != null){
				Map<String, String> mapVO = carrierVO .getColumnValues();
			
				List<String> obj_list_no = new ArrayList<>();
				
				if(carrierVO.getJoCrrCd() != null){
								
					String[] crr_cd = carrierVO.getJoCrrCd().split(",");
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
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCarrierVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
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
		 * @param CarrierVO carrierListVO
		 * @return List<CarrierListVO>
		 * @exception DAOException
		 */
		 @SuppressWarnings("unchecked")
		public List<CarrierVO> searchCarrierList(CarrierVO carrierVO) throws DAOException {
			DBRowSet dbRowset = null;
			List<CarrierVO> list = null;
			//query parameter
			Map<String, Object> param = new HashMap<String, Object>();
			//velocity parameter
			Map<String, Object> velParam = new HashMap<String, Object>();

			try{
				if(carrierVO != null){
					Map<String, String> mapVO = carrierVO .getColumnValues();
				
					param.putAll(mapVO);
					velParam.putAll(mapVO);
				}
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCarrierListVORSQL(), param, velParam);
				list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
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
	 * @param CarrierVO carrierListVO
	 * @exception DAOException
	 * @exception Exception
	 */
	public void addCarrierVO(CarrierVO carrierVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			Map<String, String> mapVO = carrierVO .getColumnValues();
			
			param.putAll(mapVO);
			velParam.putAll(mapVO);
			
			SQLExecuter sqlExe = new SQLExecuter("");
			int result = sqlExe.executeUpdate((ISQLTemplate)new CarrierMgmtDBDAOCarrierVOCSQL(), param, velParam);
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	}
	
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierListVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	public int modifyCarrierVO(CarrierVO carrierVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		int result = 0;
		try {
			Map<String, String> mapVO = carrierVO .getColumnValues();
			
			param.putAll(mapVO);
			velParam.putAll(mapVO);
			
			SQLExecuter sqlExe = new SQLExecuter("");
			result = sqlExe.executeUpdate((ISQLTemplate)new CarrierMgmtDBDAOCarrierVOUSQL(), param, velParam);
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return result;
	}
	
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierListVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	public int removeCarrierVO(CarrierVO carrierVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		int result = 0;
		try {
			Map<String, String> mapVO = carrierVO .getColumnValues();
			
			param.putAll(mapVO);
			velParam.putAll(mapVO);
			
			SQLExecuter sqlExe = new SQLExecuter("");
			result = sqlExe.executeUpdate((ISQLTemplate)new CarrierMgmtDBDAOCarrierVODSQL(), param, velParam);
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return result;
	}

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List<CarrierListVO> carrierListVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] addCarrierVOS(List<CarrierVO> carrierVOs) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(carrierVOs .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDBDAOCarrierVOCSQL(), carrierVOs,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List<CarrierListVO> carrierListVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifyCarrierVOS(List<CarrierVO> carrierVOs) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(carrierVOs .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDBDAOCarrierVOUSQL(), carrierVOs,null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}
	
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List<CarrierListVO> carrierListVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removeCarrierVOS(List<CarrierVO> carrierVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(carrierVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDBDAOCarrierVODSQL(), carrierVO,null);
				for(int i = 0; i < delCnt.length; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
	/**
	 * 
	 * @param customerVO
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerVO> searchCustomerVO(CustomerVO customerVO) throws DAOException {
 		DBRowSet dbRowset = null;
		List<CustomerVO> list = new ArrayList();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try{
			if(customerVO != null){
				Map<String, String> mapVO = customerVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCustomerVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CustomerVO .class);
		}
		catch (SQLException se){
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}
		catch (Exception ex){
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
 	}
	
	/**
	 * @param carrierVO
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchLane(CarrierVO carrierVO) throws DAOException {
 		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try{
			if(carrierVO != null){
				Map<String, String> mapVO = carrierVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOSearchLaneVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
		}
		catch (SQLException se){
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}
		catch (Exception ex){
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
 	}
	
	
	 /**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierListVO
	 * @return List<CarrierListVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public int checkDuplicated(CarrierVO carrierVO) throws DAOException {
		DBRowSet dbRowset = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		
		int count =0;
		try{
			if(carrierVO != null){
				Map<String, String> mapVO = carrierVO .getColumnValues();	
				param.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCheckDuplicateRSQL(), param, null);
			while(dbRowset.next()){
				count = dbRowset.getInt(1);
			}
			
			
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return count;
	}
	 
	 /**
		 * Check vendor code exist. </br>
		 * 
		 * @param CarrierVO carrierVO
		 * @return int
		 * @throws DAOException
		 * @throws Exception
		 */
		public int checkVndrCdInput(CarrierVO carrierVO) throws DAOException, Exception{
			DBRowSet dbRowset = null;
			Map<String, Object> param = new HashMap<String, Object>();
			int count = 0;
			try{
				Map<String, String> mapVO = carrierVO.getColumnValues();
				param.putAll(mapVO);
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCarrierSearchVndrRSQL(), param, null);
				while (dbRowset.next()){
					count = dbRowset.getInt(1);
				}
			}
			catch(SQLException se) {
				//show error in console with error message
				log.error(se.getMessage(),se);
				//throw EventException
				throw new DAOException(new ErrorHandler(se).getMessage());
			} catch(Exception ex) {
				//show error in console with error message
				log.error(ex.getMessage(),ex);
				//throw EventException
				throw new DAOException(new ErrorHandler(ex).getMessage());
			}
			return count;
		}
		
		/**
		 * Check trade code exist.</br>
		 * 
		 * @param CarrierVO carrierVO
		 * @return int
		 * @throws DAOException
		 * @throws Exception
		 */
		public int checkTrdCdInput(CarrierVO carrierVO) throws DAOException, Exception {
			DBRowSet dbRowset = null;
			Map<String, Object> param = new HashMap<String, Object>();
			int count = 0;
			try{
				Map<String, String> mapVO = carrierVO.getColumnValues();
				param.putAll(mapVO);
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgntDBDAOCarrierSearchTrdRSQL(), param, null);
				while (dbRowset.next()){
					count = dbRowset.getInt(1);
				}
			}
			catch(SQLException se) {
				//show error in console with error message
				log.error(se.getMessage(),se);
				//throw EventException
				throw new DAOException(new ErrorHandler(se).getMessage());
			} catch(Exception ex) {
				//show error in console with error message
				log.error(ex.getMessage(),ex);
				//throw EventException
				throw new DAOException(new ErrorHandler(ex).getMessage());
			}
			return count;
		}
		
		/**
		 * Check trade code exist.</br>
		 * 
		 * @param CarrierVO carrierVO
		 * @return int
		 * @throws DAOException
		 * @throws Exception
		 */
		public int checkExistLane(CarrierVO carrierVO) throws DAOException, Exception {
			DBRowSet dbRowset = null;
			Map<String, Object> param = new HashMap<String, Object>();
			int count = 0;
			try{
				Map<String, String> mapVO = carrierVO.getColumnValues();
				param.putAll(mapVO);
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgntDBDAOCheckExistLaneRSQL(), param, null);
				while (dbRowset.next()){
					count = dbRowset.getInt(1);
				}
			}
			catch(SQLException se) {
				//show error in console with error message
				log.error(se.getMessage(),se);
				//throw EventException
				throw new DAOException(new ErrorHandler(se).getMessage());
			} catch(Exception ex) {
				//show error in console with error message
				log.error(ex.getMessage(),ex);
				//throw EventException
				throw new DAOException(new ErrorHandler(ex).getMessage());
			}
			return count;
		}
	
}