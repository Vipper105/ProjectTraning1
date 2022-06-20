/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierMgmtDBDAOCarrierVORSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.15
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.15 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.integration;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.clt.framework.core.layer.integration.DAO;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author dinhhuy
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class CarrierMgmtDBDAOCarrierVORSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * a
	  * </pre>
	  */
	public CarrierMgmtDBDAOCarrierVORSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",Y";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("vndr_seq",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",Y";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("cre_dt_to",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",Y";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("cre_dt_fr",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.integration").append("\n"); 
		query.append("FileName : CarrierMgmtDBDAOCarrierVORSQL").append("\n"); 
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
		query.append("SELECT" ).append("\n"); 
		query.append(" 	jo_crr_cd as jo_crr_cd_hid," ).append("\n"); 
		query.append("    rlane_cd as rlane_cd_hid," ).append("\n"); 
		query.append("	jo_crr_cd," ).append("\n"); 
		query.append("    rlane_cd," ).append("\n"); 
		query.append("    vndr_seq," ).append("\n"); 
		query.append("    cust_cnt_cd," ).append("\n"); 
		query.append("    cust_seq," ).append("\n"); 
		query.append("    trd_cd," ).append("\n"); 
		query.append("    delt_flg," ).append("\n"); 
		query.append("    TO_CHAR(cre_dt, 'YYYY/MM/DD HH24:MI:SS') as cre_dt," ).append("\n"); 
		query.append("    cre_usr_id," ).append("\n"); 
		query.append("    TO_CHAR(upd_dt, 'YYYY/MM/DD HH24:MI:SS') as upd_dt," ).append("\n"); 
		query.append("    upd_usr_id" ).append("\n"); 
		query.append("FROM JOO_CARRIER" ).append("\n"); 
		query.append("WHERE 1=1" ).append("\n"); 
		query.append("  #if(${jo_crr_cd} != '' && ${jo_crr_cd}!='ALL')" ).append("\n"); 
		query.append("  and jo_crr_cd in (" ).append("\n"); 
		query.append("	#foreach($key IN ${obj_list_no})" ).append("\n"); 
		query.append("		#if($velocityCount < $obj_list_no.size)" ).append("\n"); 
		query.append("			'$key'," ).append("\n"); 
		query.append("		#else" ).append("\n"); 
		query.append("			'$key'" ).append("\n"); 
		query.append("		#end" ).append("\n"); 
		query.append("	#end" ).append("\n"); 
		query.append("  )" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("  #end" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("  #if (${vndr_seq}!='')" ).append("\n"); 
		query.append("  and vndr_seq LIKE @[vndr_seq] || '%'" ).append("\n"); 
		query.append("  #end" ).append("\n"); 
		query.append("  " ).append("\n"); 
		query.append("  #if (${cre_dt_fr} != '' && ${cre_dt_fr}!='')" ).append("\n"); 
		query.append("  and cre_dt BETWEEN to_date(@[cre_dt_fr],'YYYY-MM-DD') AND to_date(@[cre_dt_to],'YYYY-MM-DD')" ).append("\n"); 
		query.append("  #end" ).append("\n"); 

	}
}