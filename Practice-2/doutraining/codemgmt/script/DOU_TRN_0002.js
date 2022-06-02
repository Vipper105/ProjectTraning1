/*=========================================================
*Copyright(c) 2020 CyberLogitec
*@FileName : DOU_TRN_0002.js
*@FileTitle : Code Management
*Open Issues :
*Change history :
*@LastModifyDate : 2020.05.25
*@LastModifier : 
*@LastVersion : 1.0
* 2020.05.25 
* 1.0 Creation
=========================================================*/

/****************************************************************************************
  이벤트 구분 코드: [초기화]INIT=0; [입력]ADD=1; [조회]SEARCH=2; [리스트조회]SEARCHLIST=3;
					[수정]MODIFY=4; [삭제]REMOVE=5; [리스트삭제]REMOVELIST=6 [다중처리]MULTI=7
					기타 여분의 문자상수  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

/*------------------다음 코드는 JSDoc을 잘 만들기 위해서 추가된 코드임 ------------------*/
   /**
     * @fileoverview 업무에서 공통으로 사용하는 자바스크립트파일로 달력 관련 함수가 정의되어 있다.
     * @author 한진해운
     */

    /**
	 * @extends
	 * @class DOU_TRN_0002 : DOU_TRN_0002 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
	 */

var sheetObjects =new Array();
var sheetCnt = 0;

//버튼클릭이벤트를 받아 처리하는 이벤트핸들러 정의 */
document.onclick=processButtonClick;

// 버튼 네임으로 구분하여 프로세스를 분기처리하는 이벤트핸들러 */
function processButtonClick(){
	
	var sheetObject1 = sheetObjects[0];
	var formObj = document.form1;

		
	try {
		var srcName = ComGetEvent("name");
		if (srcName == null) {
			return;
		}

		switch (srcName) {
			case "btn_Retrieve":
				doActionIBSheet(sheetObject1, formObj, IBSEARCH);
				break;
			case "btn_DownExcell":
				doActionIBSheet(sheetObject1, formObj, IBDOWNEXCEL);
				break;
			case "btn_Save":
				 let isConfirm = confirm("Do you want to save the selected codes?");
				 if(isConfirm){					 
					 	for(let sheetNo=0;sheetNo < sheetObjects.length; sheetNo++){
					 		let rowCountStatus =sheetObjects[sheetNo].RowCount("I") + sheetObjects[sheetNo].RowCount("U") + sheetObjects[sheetNo].RowCount("D");
					 		if( rowCountStatus > 0 ){
					 			doActionIBSheet(sheetObjects[sheetNo],formObj,IBSAVE);
					 		} 					 		
					 	}
					 
	                  }
				
				break;
			case "btn_rowadd": //add row  
                doActionIBSheet(sheetObjects[0],	formObj,	IBINSERT);
				break;
			case "btn_rowdelete": //delete row
				doActionIBSheet(sheetObjects[0],	formObj,	IBDELETE);					
				break;        	        
			case "btn_rowadd_dtl": //add row  
                doActionIBSheet(sheetObjects[1],	formObj,	IBINSERT);
				break;
			case "btn_rowdelete_dtl": //delete row
				doActionIBSheet(sheetObjects[1],	formObj,	IBDELETE);					
				break;
			
		}

	} catch (e) {
		if (e == "[object Error]") {
			ComShowCodeMessage('JOO00001');
		} else {
			ComShowMessage(e.message);
		}
	}
}


/**
 * body.onload 이벤트에서 호출되는 함수로 페이지 로드완료 후 선처리해야할 기능을 추가한다. <br>
 * HTML컨트롤의 각종 이벤트와 IBSheet, IBTab 등을 초기화 한다. <br>
 **/
function loadPage(){
	for (i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}

	// show data sheet1 when load page
	doActionIBSheet(sheetObjects[0], document.form1, IBSEARCH);
	
}

/**
 * 페이지에 생성된 IBSheet Object를 sheetObjects배열에 등록한다. <br>
 * sheetObjects 배열은 공통전역변수로 상단에 정의하고, 이 함수는 {@link CoObject#ComSheetObject} 함수에 의해서 IBSheet Object가 생성되면서 자동 호출된다. <br>
 * @param {ibsheet} sheet_obj    IBSheet Object
 **/
function setSheetObject(sheet_obj){
	sheetObjects[sheetCnt++] = sheet_obj;
}

/**
 * adjust dimension of IBsheet following dimension of browser.
 * 
 */
function resizeSheet(){
	ComResizeSheet(sheetObjects[1]);
}

/**
 * 페이지에 있는 IBSheet Object를 초기화 한다. <br>
 * IBSheet가 여러개일 경우 개수만큼 case를 추가하여 IBSheet 초기화 모듈을 구성한다. <br>
 * {@link #loadPage}함수에서 이 함수를 호출하여 IBSheet Object를 초기화 한다. <br>
 * @param {ibsheet} sheetObj    IBSheet Object
 * @param {int}     sheetNo     sheetObjects 배열에서 순번
 **/
function initSheet(sheetObj, sheetNo){
	var cnt = 0;
	switch (sheetNo){
	case 1:
		with(sheetObj){
        
		var HeadTitle="|SubSystem|Cd ID|Cd Name|Length|Cd Type|Table Name|Description Remark|Flag|Create User|Create Date|Update User|Update Date" ;
        var prefix="sheet1_";

        SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:0 } );

        var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
        var headers = [ { Text:HeadTitle, Align:"Center"} ];
        InitHeaders(headers, info);

        var cols = [ {Type:"Status",    Hidden:0, Width:50,   Align:"Center",  ColMerge:0,   SaveName:prefix+"ibflag",          KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
         {Type:"Text",     Hidden:0, Width:70,   Align:"Center",  ColMerge:0,   SaveName:prefix+"ownr_sub_sys_cd", KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
         {Type:"Text",      Hidden:0,  Width:60,   Align:"Center",  ColMerge:0,   SaveName:prefix+"intg_cd_id",      KeyField:1,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:1, EditLen: 8 },
         {Type:"Text",      Hidden:0,  Width:200,  Align:"Left",    ColMerge:0,   SaveName:prefix+"intg_cd_nm",      KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
         {Type:"Text",      Hidden:0,  Width:50,   Align:"Center",  ColMerge:0,   SaveName:prefix+"intg_cd_len",     KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
         {Type:"Text",     Hidden:0, Width:100,   Align:"Center",  ColMerge:0,   SaveName:prefix+"intg_cd_tp_cd",   KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
         {Type:"Text",      Hidden:0,  Width:150,  Align:"Left",    ColMerge:0,   SaveName:prefix+"mng_tbl_nm",      KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:1 },
         {Type:"Text",      Hidden:0,  Width:350,  Align:"Left",    ColMerge:0,   SaveName:prefix+"intg_cd_desc",    KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
         {Type:"Combo",     Hidden:0, Width:40,   Align:"Center",  ColMerge:0,   SaveName:prefix+"intg_cd_use_flg", KeyField:0,   CalcLogic:"",   Format:"", ComboCode:"Y|N", ComboText:"Y|N",   PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
         {Type:"Text",      Hidden:0,  Width:80,   Align:"Center",  ColMerge:0,   SaveName:prefix+"cre_usr_id",      KeyField:0,   CalcLogic:"",   Format:"",          PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
         {Type:"Date",      Hidden:0,  Width:80,   Align:"Center",  ColMerge:0,   SaveName:prefix+"cre_dt",          KeyField:0,   CalcLogic:"",   Format:"Ymd",         PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
         {Type:"Text",      Hidden:0,  Width:80,   Align:"Center",  ColMerge:0,   SaveName:prefix+"upd_usr_id",      KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
         {Type:"Date",      Hidden:0,  Width:80,   Align:"Center",  ColMerge:0,   SaveName:prefix+"upd_dt",          KeyField:0,   CalcLogic:"",   Format:"Ymd",         PointCount:0,   UpdateEdit:0,   InsertEdit:0 } ];
      
        InitColumns(cols);
        
		SetEditable(1);
	    SetSheetHeight(240);
//	    SetWaitImageVisible(0);
	}
	break;
	
	case 2:
		with(sheetObj){

        var HeadTitle="|Cd ID|Cd Val|Display Name|Description Remark|Order" ;
        var prefix="sheet2_";

        SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:0 } );

        var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
        var headers = [ { Text:HeadTitle, Align:"Center"} ];
        InitHeaders(headers, info);

        var cols = [ {Type:"Status",    Hidden:1, Width:10,   Align:"Center",  ColMerge:0,   SaveName:prefix+"ibflag",              KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
	     {Type:"Text", Hidden:0, Width:50,   Align:"Center",  ColMerge:0,   SaveName:prefix+"intg_cd_id",          KeyField:1,   CalcLogic:"",   Format:"", PointCount:0,   UpdateEdit:0,   InsertEdit:1 },
	     {Type:"Text", Hidden:0,  Width:60,   Align:"Center",  ColMerge:0,   SaveName:prefix+"intg_cd_val_ctnt",    KeyField:1,   CalcLogic:"",   Format:"", PointCount:0,   UpdateEdit:0,   InsertEdit:1 },
	     {Type:"Text", Hidden:0,  Width:200,  Align:"Center",  ColMerge:0,   SaveName:prefix+"intg_cd_val_dp_desc", KeyField:0,   CalcLogic:"",   Format:"", PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
	     {Type:"Text", Hidden:0,  Width:600,  Align:"Left",    ColMerge:0,   SaveName:prefix+"intg_cd_val_desc",    KeyField:0,   CalcLogic:"",   Format:"", PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
	     {Type:"Text", Hidden:0,  Width:50,   Align:"Center",  ColMerge:0,   SaveName:prefix+"intg_cd_val_dp_seq",  KeyField:0,   CalcLogic:"",   Format:"", PointCount:0,   UpdateEdit:1,   InsertEdit:1 } ];
      
        InitColumns(cols);

        SetEditable(1);
 		SetSheetHeight(150);
//        SetWaitImageVisible(0);
        resizeSheet();
	}
	break;
	}
}

/**
 * IBSheet 관련 각종 기능(조회,저장 등)을 처리한다. <br>
 * {@link #processButtonClick}함수에서 이 함수를 호출하여 버튼에서 IBSheet의 기능을 호추할 때 사용한다. <br>
 * @param {ibsheet} sheetObj    IBSheet Object
 * @param {form}    formObj     Form Object
 * @param {int}     sAction     처리할 Action 코드(예:IBSEARCH,IBSAVE,IBDELETE,IBDOWNEXCEL 등이며 CoObject.js에 정의됨)
 **/
function doActionIBSheet(sheetObj , formObj, sAction){
	sheetObj.ShowDebugMsg(false);
	
	switch(sAction){
	case IBSEARCH:
			if(sheetObj.id == "sheet1"){
				// ComOpenWait(true); 
				formObj.f_cmd.value = SEARCH;
			
				let arr1=new Array("sheet1_","");
	        	let sParam1=FormQueryString(formObj)+ "&" + ComGetPrefixParam(arr1);
				let sXml1=sheetObj.GetSearchData("DOU_TRN_0002GS.do", sParam1);
				if(sXml1.length>0){
					sheetObj.LoadSearchData(sXml1,{Sync:1} );
				}
				
				sheetObjects[1].RemoveAll();
				formObj.codeid.value='';
			
			}else if(sheetObj.id == "sheet2"){
				// ComOpenWait(true);
				formObj.f_cmd.value=SEARCH02;
				let arr2=new Array("sheet2_","");
	        	let sParam2=FormQueryString(formObj)+ "&" + ComGetPrefixParam(arr2);
	        	
				let sXml2=sheetObj.GetSearchData("DOU_TRN_0002GS.do", sParam2);
				if(sXml2.length>0){
					sheetObj.LoadSearchData(sXml2,{Sync:1} );
				}
			}	
	
		break; 
	
	case IBINSERT:
		with (sheetObj) {
			sheetObj.DataInsert(-1);
			if ( sheetObj.id == "sheet1" ) {
				// khi insert thì remove data ở sheet2
				sheetObjects[1].RemoveAll();
				formObj.codeid.value='';
			}
			else if ( sheetObj.id == "sheet2" ) {
				let codeIdColNameSheet1 = "sheet1_intg_cd_id";
				let codeIdColNameSheet2 = "sheet2_intg_cd_id";
				if( sheetObj.SearchRows()== 0 ){
					// TH 1: không có row nào ở sheet2 => set giá trị của intg_cd_id của row đang select từ sheet1 cho sheet2 => intg_cd_id_sheet2 = intg_cd_id_sheet1
					let codeIDSheet1= sheetObjects[0].GetCellValue(sheetObjects[0].GetSelectRow(), codeIdColNameSheet1);
					SetCellValue(LastRow(), codeIdColNameSheet2 ,codeIDSheet1);
//					SetCellValue(LastRow(), "sheet2_intg_cd_id",sheetObjects[0].GetCellValue(sheetObjects[0].GetSelectRow(),"sheet1_intg_cd_id"));
				} else {
					// TH 2: đã có row nào ở sheet2 => nếu trong sheet 2 có tồn tại data(hay row) 
					// thì lấy intg_cd_id ở row đầu tiên của sheet 2 set intg_cd_id cho value của cell đang dc
					let codeIDSheet2 = sheetObj.GetCellValue(1, codeIdColNameSheet2);
					SetCellValue(LastRow(), codeIdColNameSheet2, codeIDSheet2);
//					SetCellValue(LastRow(), "sheet2_intg_cd_id",sheetObj.GetCellValue(1, "sheet2_intg_cd_id"));
				}
			}
			return true;
		}
			
		break;
		
	case IBDELETE:

		var sheetprefix=sheetObj.id+"_";
    	var selectRow=sheetObj.GetSelectRow();
    	sheetObj.SetCellValue(selectRow, sheetprefix+"ibflag","D");
    	sheetObj.SetRowHidden(selectRow,1);
    	//sheet1을 삭세하면 sheet2 하위 아이템 역시 삭제 처리함 - Nếu sheet1 bị xóa, các mục con của sheet2 cũng bị xóa
    	if( sheetObj.id == "sheet1" ){
    		var codeid=sheetObj.GetCellValue(selectRow, "sheet1_intg_cd_id");
    		if( sheetObjects[1].RowCount()> 0 && codeid==document.form1.codeid.value){
    		      for(i=sheetObjects[1].LastRow();i>0;i--){
    		    	  sheetObjects[1].SetCellValue(i, "sheet2_ibflag","D");
    		    	  sheetObjects[1].SetRowHidden(i,1);
    		      }
    		}
    	}	
	
		break;
		
	case IBSAVE:
		if(checkCodeDuplicated(sheetObjects)){
			ComShowCodeMessage("COM12115");
			return;
		}
		ComOpenWait(true);
		formObj.f_cmd.value=MULTI;
		sheetObj.DoSave("DOU_TRN_0002GS.do", FormQueryString(formObj),-1,false);
		break;
		
	case IBDOWNEXCEL:
		// If not exist any records in sheet show error message
		if(sheetObj.RowCount() < 1){
			ComShowCodeMessage("COM132501");
		}else{
//    		sheetObj.Down2Excel({FileName:"myFile.xls", DownCols: makeHiddenSkipCol(sheetObj), Merge:1}); 
			
			// Down2ExcelBuffer is Download multiple sheets into a single excel document.
			sheetObjects[0].Down2ExcelBuffer(true);
			// makeHiddenSkipCol cột nào có hidden = 1 thì khi lưu file excel sẽ không có cột đó 
			for(let i =0; i<sheetObjects.length;i++){
				sheetObjects[i].Down2Excel({FileName:"excel2",SheetName:"sheet"+ (i +1) ,DownCols: makeHiddenSkipCol(sheetObjects[i]), SheetDesign:1, Merge:1});		
			}
			sheetObjects[0].Down2ExcelBuffer(false);	
		}
		
		break;
	}

}

/**
 * Event when double click on sheet data 
 * 
 * @param {ibsheet} sheetObj    IBSheet Object
 * @param {ibsheet} Row   
 * @param {ibsheet} Col
 **/
function sheet1_OnDblClick(sheetObj, Row, Col) {
	// ComSetObjValue set giá trị vào thành phần HTML
	ComSetObjValue(document.form1.codeid, sheetObj.GetCellValue(Row, "sheet1_intg_cd_id"));
	// sheetObjects[1] => sheet thứ 2
	doActionIBSheet(sheetObjects[1],document.form1,IBSEARCH);

}

/**
 * Handling event after searching sheet2 
 * 
 **/
function sheet2_OnSearchEnd() { 
	ComOpenWait(false);
}

/**
 * Handling event after saving sheet1 
 * 
 **/
function sheet1_OnSaveEnd() { 
	ComOpenWait(false);
}

/**
 * Handling event after saving sheet2 
 * 
 **/
function sheet2_OnSaveEnd() { 
	ComOpenWait(false);
}

/**
 * Check whether if the inputted codes is dupliacate or NOT 
 * @param {ibsheet} sheetObj    IBSheet Object
 * 
 **/
function checkCodeDuplicated(sheetObj){
	
	let prefix ="";
	let colCdID ="";
	
	if( sheetObj[0].id == "sheet1" ){
		prefix = "sheet1_";
		colCdID = "intg_cd_id";
		
		let insertedRows = sheetObj[0].FindStatusRow("I");
		let insertedRowsList = insertedRows.split(";");
		
		// Check the inserted reccords is wheater duplicated codeId or NOT
		if(checkDuplicateInsertCodeID(sheetObj,insertedRowsList,prefix,colCdID)){
			return true;
		}
		
		for(let i=0; i < insertedRowsList.length; i++){
			let text = sheetObj[0].GetCellValue(insertedRowsList[i], prefix+ colCdID);
			let duplicatedIndex = sheetObj[0].FindText( prefix + colCdID, text);
				if(!isExistedCodeId(insertedRowsList,duplicatedIndex) && duplicatedIndex != -1){
					return true;
				}			
		}
	}
	
	if(sheetObj.id == "sheet2" ){
		prefix = "sheet2_";
		colCdID = "intg_cd_val_ctnt";
		let insertedRows = sheetObj[1].FindStatusRow("I");
		let insertedRowsList = insertedRows.split(";");
		
		// Check the inserted reccords is wheater duplicated codeId or NOT
		if(checkDuplicateInsertCodeID(sheetObj,insertedRowsList,prefix,colCdID)){
			return true;
		}
		
		
		for(let i=0; i < insertedRowsList.length; i++){
			let text2 = sheetObj[1].GetCellValue(insertedRowsList[i], prefix+ colCdID);
			let duplicatedIndex = sheetObj[1].FindText( prefix + colCdID, text2);
				if(!isExistedCodeId(insertedRowsList,duplicatedIndex) && duplicatedIndex != -1){
					return true;
				}			
		}
	}
	return false;
}

/**
 * Check index of inserted row is same index of FindText() or NOT
 * @param inInsertedRowsList
 * @param inDuplicatedIndex
 * 
 **/
function isExistedCodeId(inInsertedRowsList,inDuplicatedIndex){
	for(var i= inInsertedRowsList.length-1 ; i >= 0 ; i--){
		if(Number(inInsertedRowsList[i]) == inDuplicatedIndex){
			return true;
		} 
	}
	
	return false;
	
}

function checkDuplicateInsertCodeID(sheetObj,insertedRowsList,prefix,colCdID){
	
	for (var i = 0; i < insertedRowsList.length - 1; i++) {
		let text1 = sheetObj[0].GetCellValue(insertedRowsList[i], prefix+ colCdID);
		for (var j = i+1; j < insertedRowsList.length; j++) {
			let text2 = sheetObj[0].GetCellValue(insertedRowsList[j], prefix+ colCdID);
			
			if(text1 == text2){
				return true;
			}
		}
	}
	
}

