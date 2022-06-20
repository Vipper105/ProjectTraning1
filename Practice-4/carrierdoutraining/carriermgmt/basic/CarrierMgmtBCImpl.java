/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierMgmtBCImpl.java
*@FileTitle : Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.14
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.14 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.integration.CarrierMgmtDBDAO;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CustomerVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-CarrierDouTraining Business Logic Command Interface<br>
 * - ALPS-CarrierDouTraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author dinhhuy
 * @since J2EE 1.6
 */
public class CarrierMgmtBCImpl extends BasicCommandSupport implements CarrierMgmtBC {

	// Database Access Object
	private transient CarrierMgmtDBDAO dbDao = null;

	/**
	 * CarrierMgmtBCImpl 객체 생성<br>
	 * CarrierMgmtDBDAO를 생성한다.<br>
	 */
	public CarrierMgmtBCImpl() {
		dbDao = new CarrierMgmtDBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierListVO
	 * @return List<CarrierListVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchCarrierVO(CarrierVO carrierVO) throws EventException {
		try {
			return dbDao.searchCarrierVO(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierListVO[] carrierListVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageCarrierVO(CarrierVO[] carrierListVO, SignOnUserAccount account) throws EventException{
		try {
			List<CarrierVO> insertVoList = new ArrayList<CarrierVO>();
			List<CarrierVO> updateVoList = new ArrayList<CarrierVO>();
			List<CarrierVO> deleteVoList = new ArrayList<CarrierVO>();
			
			for ( int i=0; i<carrierListVO .length; i++ ) {
				if ( carrierListVO[i].getIbflag().equals("I")){
					
					boolean isDuplicated = checkDuplicated(carrierListVO[i]) >= 1;
					if(isDuplicated){		
						throw new DAOException(
								new ErrorHandler(
										"ERR00001", new String[] { carrierListVO[i].getJoCrrCd() }).getMessage());
					}
				
					carrierListVO[i].setCreUsrId(account.getUsr_id());
					carrierListVO[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(carrierListVO[i]);
					
				} else if ( carrierListVO[i].getIbflag().equals("U")){
					carrierListVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(carrierListVO[i]);
				} else if ( carrierListVO[i].getIbflag().equals("D")){
					deleteVoList.add(carrierListVO[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addCarrierVOS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifyCarrierVOS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeCarrierVOS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierVO
	 * @exception EventException
	 */
	@Override
	public List<CarrierVO> searchCarrierList(CarrierVO carrierVO) throws EventException {
		try {
			return dbDao.searchCarrierList(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
		
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierVO
	 * @exception EventException
	 */
	@Override
	public List<CustomerVO> searchCustomerVO(CustomerVO customerVO) throws EventException {
		try {
			return dbDao.searchCustomerVO(customerVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierVO
	 * @exception EventException
	 */
	@Override
	public List<CarrierVO> searchLane(CarrierVO carrierVO) throws EventException {
		try {
			return dbDao.searchLane(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**                          
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO carrierListVO
	 * @return List<CarrierListVO>
	 * @exception EventException
	 */
	public int checkDuplicated(CarrierVO carrierVO) throws EventException {
		
		int count =0;
		try {
			count =  dbDao.checkDuplicated(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return count;
	}
	
	/**
	 * [checkVndrCdInput] to check vendor code exist.</br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkVndrCdInput(CarrierVO carrierVO) throws EventException{
		try {
			return dbDao.checkVndrCdInput(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [checkTrdCdInput] to check trade code exist.</br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkTrdCdInput(CarrierVO carrierVO) throws EventException{
		try{
			return dbDao.checkTrdCdInput(carrierVO);
		}catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [checkDuplicateInput] to check duplicate input.<br>
	 * 
	 * @param CarrierVO	carrierVO
	 * @return int
	 * @exception EventException
	 */
	public int checkDuplicateInput(CarrierVO carrierVO) throws EventException {
		try {
			return dbDao.checkDuplicated(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [checkTrdCdInput] to check trade code exist.</br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkExistLane(CarrierVO carrierVO) throws EventException{
		try{
			return dbDao.checkExistLane(carrierVO);
		}catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
}