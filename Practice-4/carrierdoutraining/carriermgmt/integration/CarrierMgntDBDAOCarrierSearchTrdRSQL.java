/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierMgntDBDAOCarrierSearchTrdRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.20
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.20 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.integration ;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.clt.framework.core.layer.integration.DAO;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author HuyDP
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class CarrierMgntDBDAOCarrierSearchTrdRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * a
	  * </pre>
	  */
	public CarrierMgntDBDAOCarrierSearchTrdRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",Y";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("trd_cd",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.integration ").append("\n"); 
		query.append("FileName : CarrierMgntDBDAOCarrierSearchTrdRSQL").append("\n"); 
		query.append("*/").append("\n"); 
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
		query.append("select count(trd_cd) " ).append("\n"); 
		query.append("from mdm_trade" ).append("\n"); 
		query.append("where trd_cd = @[trd_cd]" ).append("\n"); 
		query.append("    and delt_flg = 'N'	" ).append("\n"); 

	}
}