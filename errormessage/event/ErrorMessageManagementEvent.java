/*=========================================================
*Copyright(c) 2009 CyberLogitec
*@FileName : ErrorMessageManagementEvent.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2009.02.26
*@LastModifier : 김경범
*@LastVersion : 1.0
* 2009.02.26 김경범
* 1.0 Creation
=========================================================*/
package com.clt.syscommon.management.opus.errormessage.event;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.syscommon.common.table.ComBakEndJbVO;
import com.clt.syscommon.common.table.ComErrMsgVO;


/**
 * ErrorMessageManagement 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  ErrorMessageManagementHTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author KyungBum Kim
 * @see ErrorMessageManagementHTMLAction 참조
 * @since J2EE 1.4
 */

public class ErrorMessageManagementEvent extends EventSupport {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> voMap = new HashMap<String, Object>();
	
	/** com_user Table  Value Object */
	private ComErrMsgVO comerrmsgvo = null;
	
	/** com_users Multi Action을 위한 Collection */
	private ComErrMsgVO[] comerrmsgvos = null;

	public ErrorMessageManagementEvent(){}
	
	public void setComErrMsgVO(ComErrMsgVO comerrmsgvo){
		this. comerrmsgvo = comerrmsgvo;
	}

	public void setComErrMsgVOS(ComErrMsgVO[] comerrmsgvos){
		if(comerrmsgvos != null){
			ComErrMsgVO[] tmpVOs = Arrays.copyOf(comerrmsgvos, comerrmsgvos.length);
			this.comerrmsgvos  = tmpVOs;
		}
	}


	public ComErrMsgVO getComErrMsgVO(){
		return comerrmsgvo;
	}

	public ComErrMsgVO[] getComErrMsgVOS(){
		ComErrMsgVO[] rtnVOs = null;
		if (this.comerrmsgvos != null) {
			rtnVOs = Arrays.copyOf(comerrmsgvos, comerrmsgvos.length);
		}
		return rtnVOs;
	}

	public void setVO(String key, Object vo){
		voMap.put(key, vo);
	}
	
	public Object getVO(String key){
		return voMap.get(key);
	}
}