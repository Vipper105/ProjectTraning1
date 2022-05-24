/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_ERR_0001.js
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
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
     * @class DOU_ERR_0001 : DOU_ERR_0001 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */
    function DOU_ERR_0001() {
    	this.processButtonClick		= tprocessButtonClick;
    	this.setSheetObject 		= setSheetObject;
    	this.loadPage 				= loadPage;
    	this.initSheet 				= initSheet;
    	this.initControl            = initControl;
    	this.doActionIBSheet 		= doActionIBSheet;
    	this.setTabObject 			= setTabObject;
    	this.validateForm 			= validateForm;
    }
    
   	/* 개발자 작업	*/
    var sheetObjects=new Array();
    var sheetCnt=0;
    document.onclick=processButtonClick;

    function processButtonClick() {
    	/** *** setting sheet object **** */
    	var sheetObject1 = sheetObjects[0];
    	/** **************************************************** */
    	var formObj = document.form;
    	try {
    		var srcName = ComGetEvent("name");
    		if (srcName == null) {
    			return;
    		}
    		switch (srcName) {
    			case "btn_Retrieve":
    				doActionIBSheet(sheetObject1, formObj, IBSEARCH);
    				break;
    			case "btn_Add":
    				doActionIBSheet(sheetObject1, formObj, IBINSERT);
    				break;
    			case "btn_Save":
    				doActionIBSheet(sheetObject1, formObj, IBSAVE);
    				break;
    			case "btn_DownExcel":
    				doActionIBSheet(sheetObject1, formObj, IBDOWNEXCEL);
    				break;
    			default :
    				break;}
    	} catch (e) {
    		if (e == "[object Error]") {
    			ComShowCodeMessage('JOO00001');
    		} else {
    			ComShowMessage(e.message);
    		}
    	}
    }

    function loadPage(){
    	//generate Grid Layout
    	for (i = 0; i < sheetObjects.length; i++) {
    		ComConfigSheet(sheetObjects[i]);
    		initSheet(sheetObjects[i], i + 1);
    		ComEndConfigSheet(sheetObjects[i]);
    	}
    	
    	//auto search data after loading finish.
    	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
    }

    function initSheet(sheetObj,sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    	case 1: // sheet1 init
    		with (sheetObj) {

    			var HeadTitle = "STS|Del|Msg Cd|Msg Type|Msg Level|Message|Description";
    			var headCount = ComCountHeadTitle(HeadTitle);
    			// (headCount, 0, 0, true);

    			SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});

    			var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
    			var headers = [ { Text : HeadTitle, Align : "Center" }];
    			InitHeaders(headers, info);

    			var cols = [ 
    	             { Type : "Status", Hidden : 0, Width : 50, Align : "Center", ColMerge : 0, SaveName : "ibflag" }, 
    	             { Type : "DelCheck", Hidden : 0, Width : 50, Align : "Center", ColMerge : 0, SaveName : "del_chk" }, 
    	             { Type : "Text", Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_msg_cd", KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1, EditLen: 8 }, 
    	             { Type : "Combo", Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_tp_cd", KeyField : 1, Format : "", UpdateEdit :1, InsertEdit : 1, ComboCode:"U|S|B", ComboText:"UI|SERVER|BOTH"}, 
    	             { Type : "Combo", Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_lvl_cd", KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1, ComboCode:"E|W|I", ComboText:"ERROR|WARNING|INFO" }, 
    	             { Type : "Text", Hidden : 0, Width : 600, Align : "Left", ColMerge : 0, SaveName : "err_msg", KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1  }, 
    	             { Type : "Text", Hidden : 0, Width : 100, Align : "Left", ColMerge : 0, SaveName : "err_desc", KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1  } 
    	             ];

    			InitColumns(cols);
    			SetEditable(1);
//    			SetColProperty("jo_crr_cd", { AcceptKeys : "E|N", InputCaseSensitive : 1 });
//    			SetColProperty("vndr_seq", { AcceptKeys : "N"});
//    			SetColProperty("cust_cnt_cd", { AcceptKeys : "E|N", InputCaseSensitive : 1});
//    			SetColProperty("cust_seq", { AcceptKeys : "N"});
//    			SetColProperty("trd_cd", { AcceptKeys : "E|N", InputCaseSensitive : 1 });
    			SetWaitImageVisible(0);
    			resizeSheet();
    		}
    		break;
    	}

    }

    function resizeSheet() {
    	ComResizeSheet(sheetObjects[0]);
    }

    function setSheetObject(sheet_obj) {
    	sheetObjects[sheetCnt++] = sheet_obj;
    }

    function setComboObject(combo_obj) {
    	comboObjects[comboCnt++] = combo_obj;
    }

    function doActionIBSheet(sheetObj,formObj,sAction) {
    	sheetObj.ShowDebugMsg(false);
//    	if (!validateForm(sheetObj, formObj, sAction)) {
//    		return false;
//    	}
    	switch (sAction) {
    	case IBSEARCH: // retrieve
    		formObj.f_cmd.value = SEARCH;
    		// (*) mở loading khi search
    		ComOpenWait(true);
//    		sheetObj.DoSearch("DOU_ERR_0001GS.do", FormQueryString(formObj) );  // GETSearchData + LoadSearchData
    		
    		var xml= sheetObj.GetSearchData("DOU_ERR_0001GS.do", FormQueryString(formObj) ); // trả về 1 dạng string với format là xml nhưng không load lại sheet
    		sheetObj.LoadSearchData(xml); // sẽ đổ data lên sheet và gọi sheet1_OnSearchEnd()
    	
    		
    		// get ETC data
    		// var errorCd= ComGetEtcData(xml,"ErrMessageCode");
    	
    		break;
    	case IBSAVE: // retrieve
    		formObj.f_cmd.value = MULTI;
//    		if(validateMessageCode(sheetObj)){
//    			sheetObj.DoSave("DOU_ERR_0001GS.do", FormQueryString(formObj));    			    			
//    		}else{
//    			ComShowCodeMessage("COM132905");
//    		}
    		sheetObj.DoSave("DOU_ERR_0001GS.do", FormQueryString(formObj));  
    		break;
    	case IBINSERT: //Row Add button event
    		sheetObj.DataInsert();  // DataInsert(-1) insert dưới cuối, 0 lên đầu 
    		break;
    	case IBDOWNEXCEL:
    		if(sheetObj.RowCount< 1){
    			ComShowCodeMessage("COM131002");
    		}else{
    			sheetObj.Down2Excel({FileName:"myFile.xls", DownCols: makeHiddenSkipCol(sheetObj), Merge:1});    			
    		}
   	
    		break;
    	}
    }
    
    // Cách 1 không cần nhấn Retrieve (bắt sự kiện onChange)
    function sheet1_OnChange(sheetObj,Row,Col,Value){
    	// validateMessageCode(sheetObj);
    	if (Col == 2){
    		let msgCode = sheetObj.GetCellValue(Row, Col);
        	
        	let regex = /^[A-Z]{3}[0-9]{5}/;
        	
        	if(!regex.test(msgCode)){
    		  ComShowCodeMessage("COM132905");
    		  Value ="";
    		  sheetObj.SetCellValue(Row,Col,Value,0);
    		  sheetObj.SelectCell(Row,Col);
    		}
    	}
    }
   
    
    /*
     * 
    // Cách 2 phải nhấn nút Retrieve
    function validateMessageCode(sheetObj){
    	// var row= sheetObj.FindStatusRow("I");
    	// check 8 digits 
    	let regex = /^[A-Z]{3}[0-9]{5}/;
    	let insertedRows = sheetObj.FindStatusRow("I");
    	let insertedRowsList = insertedRows.split(";");
    	
    	for(let i=0;i<insertedRowsList.length;i++){
    		let msgCode = sheetObj.GetCellValue(insertedRowsList[i], "err_msg_cd");
    		
    		if(!regex.test(msgCode)){
    			return false;
    		}
    	}
    	// let msgCode = sheetObj.GetCellValue(sheetObj.GetSelectRow(), sheetObj.GetSelectCol());
    	return true;
    	
    	// check duplicate xuống server check
    }
    
    */
    

    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
    	// (*) tắt loading
     	ComOpenWait(false);
    }


    function validateForm(sheetObj, formObj, sAction) {
    	sheetObj.ShowDebugMsg(false);
    }

    
    

	/* 개발자 작업  끝 */