/*=========================================================
*Copyright(c) 2009 CyberLogitec
*@FileName : ErrorMessageBC.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2009.02.26
*@LastModifier : 김경범
*@LastVersion : 1.0
* 2009.02.26 김경범
* 1.0 Creation
=========================================================*/
package com.clt.syscommon.management.opus.errormessage.basic;

import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.syscommon.common.table.ComErrMsgVO;
import com.clt.syscommon.management.opus.errormessage.vo.ErrorMessageResponseContainerVO;

/**
 * NIS2010-Errormessagemanagement Business Logic Command Interface<br>
 * - NIS2010-Errormessagemanagement에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author KyungBum Kim
 * @see ErrormessagemanagementEventResponse 참조
 * @since J2EE 1.4
 */

public interface ErrorMessageBC {
	/**
	 * 조회 이벤트 처리<br>
	 * Errormessage화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @param ComErrMsgVO comerrmsgvo
	 * @return ErrorMessageResponseContainerVO
	 * @exception EventException
	 */
	public ErrorMessageResponseContainerVO searchComErrMsgVO(ComErrMsgVO comerrmsgvo) throws EventException;
	/**
	 * 멀티 이벤트 처리<br>
	 * Errormessage 화면에 대한 멀티 이벤트 처리<br>
	 * @param ComErrMsgVO[] comerrmsgvo
	 * @param SignOnUserAccount signOnUserAccount
	 * @return ErrorMessageResponseContainerVO
	 * @exception EventException
	 */
	public ErrorMessageResponseContainerVO multiComErrMsgVO(ComErrMsgVO[] comerrmsgvo, SignOnUserAccount signOnUserAccount) throws EventException;
}