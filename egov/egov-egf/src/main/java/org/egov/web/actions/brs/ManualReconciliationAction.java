/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 * 	1) All versions of this program, verbatim or modified must carry this
 * 	   Legal Notice.
 *
 * 	2) Any misrepresentation of the origin of the material is prohibited. It
 * 	   is required that all modified versions of this material be marked in
 * 	   reasonable ways as different from the original version.
 *
 * 	3) This license does not grant any rights to any user of the program
 * 	   with regards to rights under trademark law for use of the trade names
 * 	   or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.web.actions.brs;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.egov.commons.Bank;
import org.egov.commons.Bankaccount;
import org.egov.commons.Bankbranch;
import org.egov.commons.Bankreconciliation;
import org.egov.commons.dao.BankHibernateDAO;
import org.egov.egf.model.ReconcileBean;
import org.egov.infra.web.struts.actions.BaseFormAction;
import org.egov.infra.web.struts.annotation.ValidationErrorPage;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@ParentPackage("egov")
@Results({
	@Result(name = ManualReconciliationAction.NEW, location = "manualReconciliation-" + ManualReconciliationAction.NEW + ".jsp"),
	@Result(name = "search", location = "manualReconciliation-" + "search" + ".jsp"),
	@Result(name = "report", location = "manualReconciliation-" + "report" + ".jsp"),
	@Result(name = "update", location = "manualReconciliation-update.jsp"),
	@Result(name = "PDF", type = "stream", location = "inputStream", params = { "inputName", "inputStream", "contentType",
			"application/pdf", "contentDisposition", "no-cache;filename=AutoReconcileReport.pdf" }),
			@Result(name = "XLS", type = "stream", location = "inputStream", params = { "inputName", "inputStream", "contentType",
					"application/xls", "contentDisposition", "no-cache;filename=AutoReconcileReport.xls" })
})
public class ManualReconciliationAction extends BaseFormAction {


	private static final long serialVersionUID = -4207341983597707193L;
	private static final Logger LOGGER = Logger.getLogger(ManualReconciliationAction.class);
	private List<Bankbranch> branchList = Collections.EMPTY_LIST;
	private final List<Bankaccount> accountList = Collections.EMPTY_LIST;
	/* @Autowired
    private ReconcileService reconcileService;*/

	@Autowired
	private ManualReconcileHelper manualReconcileHelper;

	private ReconcileBean reconcileBean;
	private String unReconciledDrCr;
	private List<ReconcileBean> unReconciledCheques;
	List<Long> instrumentHeaders;
	List<Date> reconDates;
	@Autowired
	private BankHibernateDAO bankHibernateDAO;

	@Override
	public Object getModel() {
		return new Bankreconciliation();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void prepare()
	{

		reconcileBean=new ReconcileBean();

		List<Bank> allBankHavingAccounts = bankHibernateDAO.getAllBankHavingBranchAndAccounts(); 
		dropdownData.put("bankList", allBankHavingAccounts);  
		dropdownData.put("branchList", branchList);
		dropdownData.put("accountList", accountList);
		if (reconcileBean.getBranchId() != null)
		{
			branchList = persistenceService
					.findAllBy(
							"select  bb from Bankbranch bb,Bankaccount ba where bb.bank.id=? and ba.bankbranch=bb and bb.isactive=true",
							reconcileBean.getBankId());
			dropdownData.put("branchList", branchList);

		}
		if (reconcileBean.getAccountId() != null)
		{
			final List<Bankaccount> accountList = getPersistenceService().findAllBy(
					"from Bankaccount ba where ba.bankbranch.id=? and isactive=true order by ba.chartofaccounts.glcode", reconcileBean.getBranchId());
			dropdownData.put("accountList", accountList);
		}

	}


	@Action(value = "/brs/manualReconciliation-newForm")
	public String newForm()
	{

		return NEW;
	}

	@Action(value = "/brs/manualReconciliation-ajaxSearch")
	public String search()
	{
		//unReconciledDrCr = manualReconcileHelper.getUnReconciledDrCr(reconcileBean.getAccountId(), reconcileBean.getFromDate(), reconcileBean.getToDate());
		unReconciledCheques = manualReconcileHelper.getUnReconciledCheques(reconcileBean.getAccountId(), reconcileBean.getReconciliationDate());

		return "search";
	}

	@Action(value = "/brs/manualReconciliation-update")
	@ValidationErrorPage("search")
	public String update()
	{

		manualReconcileHelper.update(reconDates,instrumentHeaders);
		return "update";
	}














	@Action(value = "/brs/manualReconciliation-generateReport")
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String generateReport() {

		return "report";

	}

	public List<Bankbranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Bankbranch> branchList) {
		this.branchList = branchList;
	}

	public ReconcileBean getReconcileBean() {
		return reconcileBean;
	}

	public void setReconcileBean(ReconcileBean reconcileBean) {
		this.reconcileBean = reconcileBean;
	}

	public List<Bankaccount> getAccountList() {
		return accountList;
	}

	public String getUnReconciledDrCr() {
		return unReconciledDrCr;
	}

	public void setUnReconciledDrCr(String unReconciledDrCr) {
		this.unReconciledDrCr = unReconciledDrCr;
	}

	public List<ReconcileBean> getUnReconciledCheques() {
		return unReconciledCheques;
	}

	public void setUnReconciledCheques(List<ReconcileBean> unReconciledCheques) {
		this.unReconciledCheques = unReconciledCheques;
	}

	public List<Long> getInstrumentHeaders() {
		return instrumentHeaders;
	}

	public void setInstrumentHeaders(List<Long> instrumentHeaders) {
		this.instrumentHeaders = instrumentHeaders;
	}

	public List<Date> getReconDates() {
		return reconDates;
	}

	public void setReconDates(List<Date> reconDates) {
		this.reconDates = reconDates;
	}













}