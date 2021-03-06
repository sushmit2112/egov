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
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<header><!-- set fixed position by adding class "navbar-fixed-top" -->
				
				<nav class="navbar navbar-default navbar-custom navbar-fixed-top">
					<div class="container-fluid">
						<div class="navbar-header col-lg-4 col-md-6 col-sm-2 col-xs-3">
							<a class="navbar-brand" href="javascript:void(0);">
								<img src="<c:url value='${sessionScope.citylogo}' context='/egi'/>" height="60">
								<div>
									<span class="title2 hidden-sm hidden-xs citizen-title">Citizen Portal<br>${cityName}</span>
								</div>
							</a>
						</div>
						<div class="nav-menu col-lg-4 col-xs-12">
							<ul class="hr-menu text-center">
								<li class="active"><a class="menu-item " href="/portal/home" data-show-screen="#inbox-template"> <span class="title">Inbox</span><span id="unreadMessageCount" class="badge custom-badge">
								 </span></a></li>
								<span class="separator">|</span>
								<li><a class="menu-item" href="javascript:void(0);" data-show-screen="#myaccount">My Account</a></li>
								<span class="separator">|</span>
								<li><a class="menu-item" href="javascript:void(0);" data-show-screen="#newreq">New Request</a></li>
							</ul>
						</div><!--/.nav-collapse -->
						
						<div class="col-lg-4 col-md-6 col-sm-5 col-xs-9 nav-right-menu home">
							<ul class="hr-menu text-right">
								
								<li class="ico-menu">
									<a href="javascript:void(0);" title="Help">
										<i class="fa fa-question-circle"></i>
									</a>
								</li>
								
								<li class="ico-menu">
									<a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown">
										<i class="fa fa-user"></i>${userName}
									</a>
									<ul class="right-arrow dropdown-menu" role="menu">
										<li><a href="/egi/home/profile/edit" data-open-popup><i class="fa fa-user"></i>Edit Profile</a></li>
										<li><a href="javascript:void(0);" onclick="jQuery('.change-password').modal('show', {backdrop: 'static'});"><i class="fa fa-key"></i>Change Password</a></li>
										<li><a href="/egi/logout"><i class="fa fa-sign-out"></i>Sign out</a></li>
									</ul>
								</li>
								
								<li class="ico-menu">
									<a href="http://www.egovernments.org" data-strwindname = "egovsite" class="open-popup">
										<img src="../egi/resources/global/images/logo@2x.png" title="Powered by eGovernments" height="20px">
									</a>
								</li>
								
							</ul>
						</div>
						
					</div>
				</nav>
				
			</header>