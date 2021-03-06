<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>


<%@ taglib prefix="s" uri="/WEB-INF/tags/struts-tags.tld"%>
<%@ taglib prefix="egov" tagdir="/WEB-INF/tags"%>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/javascript/contra.js?rnd=${app_release_no}"></script>
<script type="text/javascript"
	src="/EGF/resources/javascript/ajaxCommonFunctions.js?rnd=${app_release_no}"></script>
</head>
<script>
var callback = {
		success: function(o){
			document.getElementById('results').innerHTML=o.responseText;
			undoLoadingMask();
			},
			failure: function(o) {
				undoLoadingMask();
		    }
		}
function getData(){
	var asOnDate =  document.getElementById('asOnDate').value;
	var accountType =  document.getElementById('accountType').value;
	var budgetGroup =  document.getElementById('budgetGroup').value;
	var fund = document.getElementById('fund').value;
	var functionId=document.getElementById('function').value;
	var department = document.getElementById('executingDepartment').value;
	
	
	if(department ==-1){
		bootbox.alert("Please select department")
		return false;
	}
	if(functionId ==-1){
		bootbox.alert("Please select function")
		return false;
	}
	if(fund ==-1){
		bootbox.alert("Please select fund")
		return false;
	}
	if(asOnDate ==''){
		bootbox.alert("Please enter a valid date")
		return false;
	}
	document.budgetVarianceReport.action='/EGF/report/budgetVarianceReport-loadData.action?asOnDate='+asOnDate;
	document.budgetVarianceReport.submit();  
	return true;
	/* var url = '/EGF/report/budgetVarianceReport-ajaxLoadData.action?skipPrepare=true&asOnDate='+asOnDate+'&accountType='+accountType+'&budgetDetail.budgetGroup.id='+budgetGroup+getMiscData();
	YAHOO.util.Connect.asyncRequest('POST', url, callback, null);  */
}

function getMiscData(){
	var fund,department,functionary,field,scheme,subscheme,data,functionId,budgetGroup="";
	fund = document.getElementById('fund');
	department = document.getElementById('executingDepartment');
	functionary = document.getElementById('functionary');
	field = document.getElementById('field');
	scheme = document.getElementById('scheme');
	subscheme = document.getElementById('subScheme');
	functionId=document.getElementById('function');
	budgetGroup =  document.getElementById('budgetGroup');
	if(fund != undefined)
		data = data+"&budgetDetail.fund.id="+fund.value;
	if(department != undefined)
		data = data+"&budgetDetail.executingDepartment.id="+department.value;
	if(functionary != undefined)
		data = data+"&budgetDetail.functionary.id="+functionary.value;
	if(functionId != undefined)
		data = data+"&budgetDetail.function.id="+functionId.value;
	if(field != undefined)
		data = data+"&budgetDetail.boundary.id="+field.value;
	if(scheme != undefined)
		data = data+"&budgetDetail.scheme.id="+scheme.value;
	if(subscheme != undefined)
		data = data+"&budgetDetail.subScheme.id="+subscheme.value;
	
	return data;
}

function exportXls(){
	var asOnDate =  document.getElementById('asOnDate').value;
	//var departmentid =  document.getElementById('executingDepartment').value;
	var accountType =  document.getElementById('accountType').value;
	var budgetGroup =  document.getElementById('budgetGroup').value;
	//var functionId =  document.getElementById('function').value;
	window.open('/EGF/report/budgetVarianceReport-exportXls.action?asOnDate='+asOnDate,'','resizable=yes,height=650,width=900,scrollbars=yes,left=30,top=30,status=no');
}

function exportPdf(){
	var asOnDate =  document.getElementById('asOnDate').value;
	//var departmentid =  document.getElementById('executingDepartment').value;
	var accountType =  document.getElementById('accountType').value;
	var budgetGroup =  document.getElementById('budgetGroup').value;
	//var functionId =  document.getElementById('function').value;
	window.open('/EGF/report/budgetVarianceReport-exportPdf.action?asOnDate='+asOnDate,'','resizable=yes,height=650,width=900,scrollbars=yes,left=30,top=30,status=no');
}

function validateData(){
	var asOnDate =  Date.parse(document.getElementById('asOnDate').value);
	if(asOnDate == ''){
		bootbox.alert("Please enter a valid date")
		return false;
	}
	<s:if test="%{isFieldMandatory('executingDepartment')}">
		if(!checkMandatoryField("executingDepartment"))
			return false;
	</s:if>
	<s:if test="%{isFieldMandatory('function')}">
		if(!checkMandatoryField("function"))
			return false;
	</s:if>
	<s:if test="%{isFieldMandatory('fund')}">
		if(!checkMandatoryField("fund"))
			return false;
	</s:if>
	<s:if test="%{isFieldMandatory('functionary')}">
		if(!checkMandatoryField("functionary"))
			return false;
	</s:if>
	<s:if test="%{isFieldMandatory('scheme')}">
		if(!checkMandatoryField("scheme"))
			return false;
	</s:if>
	<s:if test="%{isFieldMandatory('subScheme')}">
		if(!checkMandatoryField("subScheme"))
			return false;
	</s:if>
	<s:if test="%{isFieldMandatory('boundary')}">
		if(!checkMandatoryField("boundary"))
			return false;
	</s:if>
	return true;	
}

function checkMandatoryField(fieldName){
	var field = document.getElementById(fieldName);
	if(field.value == -1){
		bootbox.alert("Please select a "+fieldName)
		return false;
	}
	return true;
}
</script>
<body>
	<div class="formmainbox">
		<div class="formheading"></div>
		<div class="subheadnew">Budget Variance Report</div>
<h5 style="color: red">
<s:actionerror /></h5>
		<s:form action="budgetVarianceReport" theme="simple"
			name="budgetVarianceReport">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
				<s:if test="%{isFieldMandatory('executingDepartment')}">
						<td class="bluebox" width="10%">Department:<span
							class="mandatory1">*</span></td>
						<td class="bluebox"><s:select name="executingDepartment"
								id="executingDepartment" list="dropdownData.departmentList"
								listKey="id" listValue="name" headerKey="-1"
								headerValue="----Choose----" /></td>
								</s:if>
								<s:if test="%{isFieldMandatory('function')}">
						<td class="bluebox" width="10%">Function:<span
							class="mandatory1">*</span></td>
						<td class="bluebox"><s:select name="function" id="function"
								list="dropdownData.functionList" listKey="id" listValue="name"
								headerKey="-1" headerValue="----Choose----" /></td>
								</s:if>
				</tr>
				<tr>
				<s:if test="%{isFieldMandatory('fund')}">
						<td class="greybox" width="10%">Fund:<span class="mandatory1">*</span></td>
						<td class="greybox"><s:select name="fund" id="fund"
								list="dropdownData.fundList" listKey="id" listValue="name"
								headerKey="-1" headerValue="----Choose----" /></td>
								</s:if>
					<s:if test="%{isFieldMandatory('functionary')}">
						<td class="greybox" width="10%">Functionary:<span
							class="mandatory1">*</span></td>
						<td class="greybox"><s:select name="functionary"
								id="functionary" list="dropdownData.functionaryList"
								listKey="id" listValue="name" headerKey="-1"
								headerValue="----Choose----" /></td>
					</s:if>
					<s:else>
						<td class="greybox">&nbsp;</td>
						<td class="greybox">&nbsp;</td>
					</s:else>
				</tr>
				<tr>
					<s:if test="%{isFieldMandatory('scheme')}">
						<td width="10%" class="bluebox">&nbsp;</td>
						<td class="bluebox"><s:text name="scheme" />:<span
							class="mandatory1">*</span></td>
						<td class="bluebox"><s:select list="dropdownData.schemeList"
								listKey="id" listValue="name" headerKey="0"
								headerValue="--- Select ---" name="scheme"
								onchange="updateGrid('scheme.id',document.getElementById('budgetDetail_scheme').selectedIndex);populateSubSchemes(this);"
								value="scheme.id" id="budgetDetail_scheme"></s:select></td>
					</s:if>
					<s:if test="%{isFieldMandatory('subScheme')}">
						<egov:ajaxdropdown id="subScheme" fields="['Text','Value']"
							dropdownId="budgetDetail_subScheme"
							url="budget/budgetDetail-ajaxLoadSubSchemes.action"
							afterSuccess="onHeaderSubSchemePopulation" />
						<td class="bluebox"><s:text name="subScheme" />:<span
							class="mandatory1">*</span></td>
						<td class="bluebox"><s:select
								list="dropdownData.subschemeList" listKey="id" listValue="name"
								headerKey="0" headerValue="--- Select ---" name="subScheme"
								onchange="updateGrid('subScheme.id',document.getElementById('budgetDetail_subScheme').selectedIndex)"
								value="subScheme.id" id="budgetDetail_subScheme"></s:select></td>
					</s:if>
				</tr>
				<tr>
					<s:if test="%{isFieldMandatory('boundary')}">
						<td class="greybox"><s:text name="field" id="field"/>:<span
							class="mandatory1">*</span></td>
						<td class="greybox"><s:select list="dropdownData.fieldList"
								listKey="id" listValue="name" headerKey="0"
								headerValue="--- Select ---" name="boundary"
								onchange="updateGrid('boundary.id',document.getElementById('budgetDetail_boundary').selectedIndex)"
								value="boundary.id" id="budgetDetail_boundary"></s:select></td>
					</s:if>
				</tr>
				<tr>
					<td class="bluebox" width="10%">Account Type:</td>
					<td class="bluebox"><s:select name="accountType"
							id="accountType" list="dropdownData.accountTypeList"
							headerKey="-1" headerValue="----Choose----" /></td>
					<td class="bluebox" width="10%">Budget Head:</td>
					<td class="bluebox"><s:select name="budgetGroup"
							id="budgetGroup" list="dropdownData.budgetGroupList" listKey="id"
							listValue="name" headerKey="-1" headerValue="----Choose----" /></td>
				</tr>
				<tr>
					<td class="greybox" width="10%">As On Date:<span
						class="mandatory1">*</span></td>
					<td class="greybox"><s:textfield name="asOnDate" id="asOnDate"
							cssStyle="width:100px" value='%{getFormattedDate(asOnDate)}'
							onkeyup="DateFormat(this,this.value,event,false,'3')" /><a
						href="javascript:show_calendar('budgetVarianceReport.asOnDate');"
						style="text-decoration: none">&nbsp;<img
							src="/egi/resources/erp2/images/calendaricon.gif" border="0" /></a>(dd/mm/yyyy)<br />
					</td>
					<td class="greybox">&nbsp;</td>
					<td class="greybox">&nbsp;</td>
				</tr>
			</table>
			
			
			<br />
			<br />
			<div class="buttonbottom">
				<input type="submit" value="Search" class="buttonsubmit"
					onclick="return getData();"  /> &nbsp
				<s:reset name="button" type="submit" cssClass="button" id="button"
					value="Cancel" />
				<input type="button" value="Close"
					onclick="javascript:window.close()" class="button" />
			</div>
	</div>
	
	</s:form>
	
<div id="results"><jsp:include page="./budgetVarianceReport-results.jsp"></jsp:include>

</div>	

	</body>
</html>
