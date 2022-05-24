/*=========================================================
*Copyright(c) 2009 CyberLogitec
*@FileName : ${FILE_NAME}.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2009.05.04
*@LastModifier : 김경범
*@LastVersion : 1.0
* 2009.05.04 김경범
* 1.0 Creation
=========================================================*/
package com.clt.syscommon.management.opus.errormessage.integration;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author KyungBum Kim
 * @see 
 * @since J2EE 1.4
 */

public class ErrorMessageDAOComErrMsgVODSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  *    
	  * </pre>
	  */
	public ErrorMessageDAOComErrMsgVODSQL(){
		setQuery();
		
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = "12,N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("err_msg_cd",new String[]{arrTmp[0],arrTmp[1]});

	}
	
	public String getSQL(){
		return query.toString();
	}
	
	public HashMap<String,String[]> getParams() {
		return params;
	}
	
	/**
	 * Query 생성
	 */
	public void setQuery(){
		query.append("DELETE FROM  com_err_msg" ).append("\n"); 
		query.append("WHERE err_msg_cd = @[err_msg_cd]" ).append("\n"); 
		query.append("AND   lang_tp_cd = 'ENG'" ).append("\n"); 

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.syscommon.nis2010.management.errormessagemanagement.errormessage.integration").append("\n"); 
		query.append("FileName : ErrorMessageDAOComErrMsgVODSQL").append("\n"); 
		query.append("*/").append("\n"); 
	}
}