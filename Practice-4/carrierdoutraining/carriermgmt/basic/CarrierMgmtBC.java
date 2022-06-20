/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierMgmtBC.java
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

import java.util.List;

import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.vo.CustomerVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-Carrierdoutraining Business Logic Command Interface<br>
 * - ALPS-Carrierdoutraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author dinhhuy
 * @since J2EE 1.6
 */

public interface CarrierMgmtBC {

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO	carrierListVO
	 * @return List<CarrierListVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchCarrierVO(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierListVO[] carrierListVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageCarrierVO(CarrierVO[] carrierVOs,SignOnUserAccount account) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CarrierVO	carrierListVO
	 * @return List<CarrierListVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchCarrierList(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CustomerVO customerVO
	 * @return List<CustomerVO>
	 * @exception EventException
	 */
	public List<CustomerVO> searchCustomerVO(CustomerVO customerVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CustomerVO customerVO
	 * @return List<CustomerVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchLane(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CustomerVO customerVO
	 * @return List<CustomerVO>
	 * @exception EventException
	 */
	public int checkDuplicated(CarrierVO carrierVOs) throws EventException;
	
	/**
	 * [checkVndrCdInput] to check vendor code exist.</br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkVndrCdInput(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [checkTrdCdInput] to check trade code exist.</br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkTrdCdInput(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [checkDuplicateInput] to check duplicate input.<br>
	 * 
	 * @param CarrierVO	carrierVO
	 * @return int
	 * @exception EventException
	 */
	public int checkDuplicateInput(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [checkDuplicateInput] to check duplicate input.<br>
	 * 
	 * @param CarrierVO	carrierVO
	 * @return int
	 * @exception EventException
	 */
	public int checkExistLane(CarrierVO carrierVO) throws EventException;

}