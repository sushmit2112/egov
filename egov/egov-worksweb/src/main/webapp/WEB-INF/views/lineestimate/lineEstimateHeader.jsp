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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="panel panel-primary" data-collapsed="0">
	<div class="panel-heading">
		<div class="panel-title" style="text-align: left;">
			<spring:message code="header.lineestimate" />
		</div>
	</div>
	<form:hidden path="" value="${lineEstimate.function.id }" id="functionId"/>
	<div>
		<spring:hasBindErrors name="lineEstimate">  
		 		<div class="alert alert-danger col-md-10 col-md-offset-1">
		  			<form:errors path="*" cssClass="error-msg add-margin" /><br/>
		      	</div>
		</spring:hasBindErrors>
	</div>
	<div class="panel-body custom-form">
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.date" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:input path="lineEstimateDate" id="lineEstimateDate" name="lineEstimateDate" type="text" class="form-control" value="${lineEstimateDate}" maxlength="12" readonly="true" />
				<form:errors path="lineEstimateDate" cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lbl.department" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="executingDepartment" data-first-option="false" id="executingDepartments" class="form-control" required="required">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${departments}" itemValue="id"	itemLabel="name" />
				</form:select>
				<form:errors path="executingDepartment" cssClass="add-margin error-msg" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.subject" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:hidden path="id" name="id" value="${id}" class="form-control table-input hidden-input" />
				<form:textarea name="subject" path="subject" id="subject" class="form-control" value="${subject}" maxlength="256" required="required"></form:textarea>
				<form:errors path="subject" cssClass="add-margin error-msg" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.reference" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:textarea name="reference" path="reference" id="reference" value="${reference}" class="form-control" maxlength="1024" required="required"></form:textarea>
				<form:errors path="reference" cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lbl.description" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:textarea name="description" path="description" id="description" class="form-control" value="${description}" maxlength="1024" required="required"></form:textarea>
				<form:errors path="description" cssClass="add-margin error-msg" />
			</div>
		</div>
			<div class="form-group">
				<label class="col-sm-3 control-label text-right"><spring:message code="lbl.ward" /><span class="mandatory"></span>
				</label>
				<div class="col-sm-3 add-margin">
					<form:hidden path="ward" id="ward" value="" cssClass="selectwk" />
					<form:input id="wardInput" path="ward.name" class="form-control" type="text" required="required"/>
					<form:errors path="ward" cssClass="add-margin error-msg" />
				</div>
				<label class="col-sm-2 control-label text-right"> <spring:message code="lbl.location" /></label>
				<div class="col-sm-3 add-margin">
					<form:select path="location" data-first-option="false" id="locationBoundary" cssClass="form-control">
						<form:option value="">
							<spring:message code="lbl.select" />
							<form:options items="${locations}" itemValue="id" itemLabel="name" />
						</form:option>
					</form:select>
					<form:errors path="location" cssClass="add-margin error-msg" />
				</div>
			</div>
			
			<div class="form-group" id="radioValue" >
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.slum" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:radiobutton path="workCategory" id="slum" value="SLUM_WORK" onclick="showSlumFields();" required="required" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lbl.nonslum" /><span class="mandatory"></span>
			</label>
			<div class="col-sm-3 add-margin">
				<form:radiobutton path="workCategory" id="nonslum" value="NON_SLUM_WORK" onclick="disableSlumFields()" required="required" />
			</div>
		</div>
		<div id="slumfields" style="display: none">
			<div class="form-group">
				<label class="col-sm-3 control-label text-right"> <spring:message code="lbl.typeofslum" /><span class="mandatory"></span>
				</label>
				<div class="col-sm-3 add-margin">
					<form:select path="typeOfSlum" data-first-option="false" id="typeOfSlum" cssClass="form-control" required="required">
						<form:option value="">
							<spring:message code="lbl.select" />
						</form:option>
						<form:options items="${typeOfSlum}" />
					</form:select>
					<form:errors path="typeOfSlum" cssClass="add-margin error-msg" />
				</div>
				<div>
					<label class="col-sm-2 control-label text-right"><spring:message code="lbl.beneficiary" /><span class="mandatory"></span></label>
					<div class="col-sm-3 add-margin">
						<form:select path="beneficiary" data-first-option="false" id="beneficiary" class="form-control" required="required">
							<form:option value="">
								<spring:message code="lbl.select" />
							</form:option>
							<form:options items="${beneficiary}" />
						</form:select>
						<form:errors path="beneficiary" cssClass="add-margin error-msg" />
					</div>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.natureofwork" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="natureOfWork" data-first-option="false" id="natureOfWork" class="form-control" required="required">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${natureOfWork}" itemLabel="name" itemValue="id" />
				</form:select>
				<form:errors path="natureOfWork" cssClass="add-margin error-msg" /> 
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lbl.typeofwork" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="typeOfWork" data-first-option="false" id="typeofwork" class="form-control" required="required"  >
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${typeOfWork}" itemLabel="description" itemValue="id" />
				</form:select>
				<form:errors path="typeOfWork" cssClass="add-margin error-msg" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.subtypeofwork" /></label>
			<div class="col-sm-3 add-margin">
			<input type="hidden" id="subTypeOfWorkValue" value="${lineEstimate.subTypeOfWork.id }"/>
				<form:select path="subTypeOfWork" data-first-option="false" id="subTypeOfWork" class="form-control" >
					<form:option value=""><spring:message code="lbl.select"/></form:option>
				</form:select>
				<form:errors path="subTypeOfWork" cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lbl.modeofallotment" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="modeOfAllotment" data-first-option="false" id="modeOfAllotment" class="form-control" required="required">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${modeOfAllotment}" />
				</form:select>
				<form:errors path="modeOfAllotment" cssClass="add-margin error-msg" />
			</div>
		</div>
		
		<div class="panel-heading custom_form_panel_heading" >
			<div class="panel-title" style="text-align: left;">
				<spring:message code="lbl.financialdetails" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.fund" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="fund" data-first-option="false"
					class="form-control" id="fund" required="required">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${funds}" itemValue="id" itemLabel="name" />
				</form:select>
				<form:errors path="fund" cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lbl.function" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="function" data-first-option="false" name="function" class="form-control" id="function" required="required">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<c:forEach var="functions" items="${functions}">
						<form:option value="${functions.id}"><c:out value="${functions.code} - ${functions.name}"/></form:option>  
					</c:forEach>   
				</form:select>
				<form:errors path="function" cssClass="add-margin error-msg" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.budgethead" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<input type="hidden" id="budgetHeadValue" value="${lineEstimate.budgetHead.id }"/>
				<form:select path="budgetHead" data-first-option="false" id="budgetHead" class="form-control" required="required">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${budgetHeads}" itemValue="id"	itemLabel="name" />
				</form:select>
				<form:errors path="budgetHead" cssClass="add-margin error-msg" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lbl.scheme" /></label>
			<div class="col-sm-3 add-margin">
				<form:select path="scheme" data-first-option="false" id="scheme" class="form-control" onchange="getSubSchemsBySchemeId(this.value);" >
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<c:if test="${!schemes.isEmpty()}">
						<form:options items="${schemes}" itemValue="id" itemLabel="name" />
					</c:if>
				</form:select>
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lbl.subscheme" /></label>
			<input type="hidden" id="subSchemeValue" value="${lineEstimate.subScheme.id }"/>
			<div class="col-sm-3 add-margin">
				<form:select path="subScheme" data-first-option="false"	id="subScheme" class="form-control">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
				</form:select>
			</div>
		</div>
	</div>
</div>