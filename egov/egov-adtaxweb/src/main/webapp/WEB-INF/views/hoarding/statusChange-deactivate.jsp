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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="row">
	<div class="col-md-12">
		<form:form id="statusdeactivateform" name="statusdeactivateform" method ="post" class="form-horizontal form-groups-bordered"  modelAttribute="advertisementPermitDetailStatus" commandName="advertisementPermitDetailStatus">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-body custom-form">
					<div class="panel-heading ">
						<div class="panel-title" style="color: orange;" align="center">
							<strong><spring:message code="lbl.demarcation.details" /></strong>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right">
							<spring:message code="lbl.demarcation.remarks"/>
							<span class="mandatory"></span>
						</label>
						<div class="col-sm-3 add-margin">
							<form:textarea cssClass="form-control patternvalidation" path="deactivation_remarks"  id="deactivation_remarks" data-pattern="alphanumericwithspace" maxlength="100" required="required"/>
				 		</div>
						<form:hidden path="applicationNumber" id="applicationNumber" value="${advertisementPermitDetailStatus.applicationNumber}" />
						<form:hidden path="id" id="id" value="${advertisementPermitDetailStatus.id}" />
						<form:hidden path="applicationDate" id="applicationDate" value="${applicationDate}" />
						<label class="col-sm-2 control-label"><spring:message code="lbl.demarcation.date"/><span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input type="text" class="form-control datepicker" id="deactiveDate" data-date-end-date="0d"  path="deactivation_date" required="required"/>
						</div> 
					</div>
				</div>
				<div class="form-group">
					<div class="form-group">
	                	<div class="col-sm-3 add-margin text-right"><spring:message code="lbl.pending.tax"/></div>
	                	<div class="col-sm-3 add-margin view-content" id="ptax">
	                	  ${advertisementPermitDetailStatus.advertisement.pendingTax}  
	                    </div>
					</div>
				</div>
				<div class="row">
       				<div class="text-center">
       					<button type="button" class="btn btn-primary" id="deactivation" /><spring:message code="lbl.deactivate"></spring:message></button>
          		    	<button type="reset" id="reset" class="btn btn-default"><spring:message code="lbl.reset"/></button>
          		    	<a href="javascript:void(0)" class="btn btn-default" onclick="self.close()"><spring:message code="lbl.close"/></a>
          			</div>
				</div>
			</div>
		</form:form>
		<jsp:include page="../hoarding/statusChange-result.jsp"></jsp:include>
	</div>
</div>

<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.tableTools.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/TableTools.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/responsive/js/datatables.responsive.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/global/js/jquery/plugins/datatables/moment.min.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/global/js/jquery/plugins/datatables/datetime-moment.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/app/js/dateValidation.js'/>"></script>
<script src="<c:url value='/resources/app/js/deactivateAdvertisement.js'/>"></script>

