<!-- #-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2015>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation 
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or 
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this 
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It 
# 	   is required that all modified versions of this material be marked in 
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program 
# 	   with regards to rights under trademark law for use of the trade names 
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------- -->


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="row">
	<div class="col-sm-12 add-margin view-content text-left">
		<spring:message code="subheading.general.info"/>
	</div>	
</div>
<div class="row">
	<div class="form-group">
		<div class="col-sm-3 add-margin"><spring:message code="lbl.application.no"/></div>
		<div class="col-sm-3 add-margin view-content"><c:out value="${registration.applicationNo}"></c:out></div>
		<div class="col-sm-3 add-margin"><spring:message code="lbl.application.date"/></div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatDate value="${registration.applicationDate}" pattern="dd-MM-yyyy" var="applicationDate"/>
			<c:out value="${applicationDate}">
		</c:out></div>
	</div>
</div>
<div class="row">
	<div class="form-group">
		<div class="col-sm-3 add-margin"><spring:message code="lbl.date.of.marriage"/></div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatDate value="${registration.dateOfMarriage}" pattern="dd-MM-yyyy" var="dateOfMarriage"/>
			<c:out value="${dateOfMarriage}"></c:out>
			</div>
		<div class="col-sm-3 add-margin"><spring:message code="lbl.zone"/></div>
		<div class="col-sm-3 add-margin view-content"><c:out value="${registration.zone.name}"></c:out></div>
	</div>
</div>
<div class="row">
	<div class="form-group">
		<div class="col-sm-3 add-margin"><spring:message code="lbl.law"/></div>
		<div class="col-sm-3 add-margin view-content"><c:out value="${registration.marriageAct.name}"></c:out></div>
		<div class="col-sm-3 add-margin"><spring:message code="lbl.place.of.marriage"/></div>
		<div class="col-sm-3 add-margin view-content"><c:out value="${registration.placeOfMarriage}"></c:out></div>
	</div>
</div>

<c:set value="husband" var="applicant" scope="request"></c:set>
<jsp:include page="viewapplicantinfo.jsp">
	<jsp:param value="subheading.husband.info" name="header" />
	<jsp:param value="${registration.husband.name.firstName}" name="appFirstName"/>
	<jsp:param value="${registration.husband.name.middleName}" name="appMiddleName"/>
	<jsp:param value="${registration.husband.name.lastName}" name="appLastName"/>
	<jsp:param value="${registration.husband.otherName}" name="appOtherName"/>
	<jsp:param value="${registration.husband.religion.name}" name="appReligion"/>
	<jsp:param value="${registration.husband.religionPractice}" name="appReligionPractice"/>
	<jsp:param value="${registration.husband.ageInYearsAsOnMarriage}" name="appAgeInYears"/>
	<jsp:param value="${registration.husband.ageInMonthsAsOnMarriage}" name="appAgeInMonths"/>
	<jsp:param value="${registration.husband.maritalStatus}" name="appPresentRelation"/>
	<jsp:param value="${registration.husband.occupation}" name="appOccupation"/>
	<jsp:param value="${registration.husband.contactInfo.residenceAddress}" name="appResidenceAddress"/>
	<jsp:param value="${registration.husband.contactInfo.officeAddress}" name="appOfficeAddress"/>
	<jsp:param value="${registration.husband.contactInfo.mobileNo}" name="appMobileNo"/>
	<jsp:param value="${registration.husband.contactInfo.email}" name="appEmail"/>
</jsp:include>

<c:set value="wife" var="applicant" scope="request"></c:set>
<jsp:include page="viewapplicantinfo.jsp">
	<jsp:param value="subheading.wife.info" name="header" />
	<jsp:param value="${registration.wife.name.firstName}" name="appFirstName"/>
	<jsp:param value="${registration.wife.name.middleName}" name="appMiddleName"/>
	<jsp:param value="${registration.wife.name.lastName}" name="appLastName"/>
	<jsp:param value="${registration.wife.otherName}" name="appOtherName"/>
	<jsp:param value="${registration.wife.religion.name}" name="appReligion"/>
	<jsp:param value="${registration.wife.religionPractice}" name="appReligionPractice"/>
	<jsp:param value="${registration.wife.ageInYearsAsOnMarriage}" name="appAgeInYears"/>
	<jsp:param value="${registration.wife.ageInMonthsAsOnMarriage}" name="appAgeInMonths"/>
	<jsp:param value="${registration.wife.maritalStatus}" name="appPresentRelation"/>
	<jsp:param value="${registration.wife.occupation}" name="appOccupation"/>
	<jsp:param value="${registration.wife.contactInfo.residenceAddress}" name="appResidenceAddress"/>
	<jsp:param value="${registration.wife.contactInfo.officeAddress}" name="appOfficeAddress"/>
	<jsp:param value="${registration.wife.contactInfo.mobileNo}" name="appMobileNo"/>
	<jsp:param value="${registration.wife.contactInfo.email}" name="appEmail"/>
</jsp:include>