/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2015>  eGovernments Foundation

    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .

    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:

        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.

        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.

        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.

  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.works.web.controller.contractorbill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.commons.CChartOfAccounts;
import org.egov.commons.dao.ChartOfAccountsHibernateDAO;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.eis.web.controller.workflow.GenericWorkFlowController;
import org.egov.infra.admin.master.service.DepartmentService;
import org.egov.infra.exception.ApplicationException;
import org.egov.infra.validation.exception.ValidationError;
import org.egov.infra.validation.exception.ValidationException;
import org.egov.model.bills.EgBilldetails;
import org.egov.works.contractorbill.entity.ContractorBillRegister;
import org.egov.works.contractorbill.entity.enums.BillTypes;
import org.egov.works.contractorbill.service.ContractorBillRegisterService;
import org.egov.works.lineestimate.entity.DocumentDetails;
import org.egov.works.lineestimate.entity.LineEstimateDetails;
import org.egov.works.lineestimate.service.LineEstimateService;
import org.egov.works.models.workorder.WorkOrder;
import org.egov.works.utils.WorksConstants;
import org.egov.works.utils.WorksUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/contractorbill")
public class UpdateContractorBillController extends GenericWorkFlowController {
    @Autowired
    private LineEstimateService lineEstimateService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private WorksUtils worksUtils;

    @Autowired
    private ContractorBillRegisterService contractorBillRegisterService;

    @Autowired
    private ChartOfAccountsHibernateDAO chartOfAccountsHibernateDAO;

    @ModelAttribute
    public ContractorBillRegister getContractorBillRegister(@PathVariable final String contractorBillRegisterId) {
        final ContractorBillRegister contractorBillRegister = contractorBillRegisterService.getContractorBillById(Long
                .parseLong(contractorBillRegisterId));
        return contractorBillRegister;
    }

    @RequestMapping(value = "/update/{contractorBillRegisterId}", method = RequestMethod.GET)
    public String updateContractorBillRegister(final Model model, @PathVariable final String contractorBillRegisterId,
            final HttpServletRequest request)
                    throws ApplicationException {
        final ContractorBillRegister contractorBillRegister = getContractorBillRegister(contractorBillRegisterId);
        // if (contractorBillRegister.getStatus().getCode().equals(ContractorBillRegister.BillStatus.REJECTED.toString()))
        setDropDownValues(model);

        return loadViewData(model, request, contractorBillRegister);
    }

    @RequestMapping(value = "/update/{contractorBillRegisterId}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("contractorBillRegister") final ContractorBillRegister contractorBillRegister,
            final BindingResult errors,
            final RedirectAttributes redirectAttributes, final Model model, final HttpServletRequest request,
            @RequestParam("file") final MultipartFile[] files)
                    throws ApplicationException, IOException {

        String mode = "";
        String workFlowAction = "";
        ContractorBillRegister savedContractorBillRegister = null;

        if (request.getParameter("mode") != null)
            mode = request.getParameter("mode");

        if (request.getParameter("workFlowAction") != null)
            workFlowAction = request.getParameter("workFlowAction");

        Long approvalPosition = 0l;
        String approvalComment = "";

        if (request.getParameter("approvalComent") != null)
            approvalComment = request.getParameter("approvalComent");

        if (request.getParameter("approvalPosition") != null && !request.getParameter("approvalPosition").isEmpty())
            approvalPosition = Long.valueOf(request.getParameter("approvalPosition"));

        // For Get Configured ApprovalPosition from workflow history
        if (approvalPosition == null || approvalPosition.equals(Long.valueOf(0)))
            approvalPosition = contractorBillRegisterService.getApprovalPositionByMatrixDesignation(
                    contractorBillRegister, approvalPosition, null,
                    mode, workFlowAction);

        if ((approvalPosition == null || approvalPosition.equals(Long.valueOf(0)))
                && request.getParameter("approvalPosition") != null
                && !request.getParameter("approvalPosition").isEmpty())
            approvalPosition = Long.valueOf(request.getParameter("approvalPosition"));

        if (errors.hasErrors()) {
            setDropDownValues(model);
            return loadViewData(model, request, contractorBillRegister);
        } else {
            if (null != workFlowAction)
                try {
                    savedContractorBillRegister = contractorBillRegisterService.update(contractorBillRegister, approvalPosition,
                            approvalComment, null, workFlowAction,
                            mode, files);
                } catch (final ValidationException e) {
                    for (final ValidationError error : e.getErrors())
                        errors.reject(error.getMessage());
                }
            redirectAttributes.addFlashAttribute("contractorBillRegister", savedContractorBillRegister);

            final String pathVars = worksUtils.getPathVars(savedContractorBillRegister.getStatus(),
                    savedContractorBillRegister.getState(), savedContractorBillRegister.getId(), approvalPosition);

            return "redirect:/contractorbill/contractorbill-success?pathVars=" + pathVars + "&billNumber="
                    + savedContractorBillRegister.getBillnumber();
        }
    }

    private void setDropDownValues(final Model model) {
        model.addAttribute("billTypes", BillTypes.values());
    }

    private String loadViewData(final Model model, final HttpServletRequest request,
            final ContractorBillRegister contractorBillRegister) {

        model.addAttribute("stateType", contractorBillRegister.getClass().getSimpleName());

        if (contractorBillRegister.getCurrentState() != null)
            model.addAttribute("currentState", contractorBillRegister.getCurrentState().getValue());

        prepareWorkflow(model, contractorBillRegister, new WorkflowContainer());
        if (contractorBillRegister.getState() != null
                && contractorBillRegister.getState().getValue().equals(WorksConstants.WF_STATE_REJECTED))
            model.addAttribute("mode", "edit");
        else
            model.addAttribute("mode", "view");

        model.addAttribute("billDetailsMap", getBillDetailsMap(contractorBillRegister));

        model.addAttribute("workflowHistory",
                lineEstimateService.getHistory(contractorBillRegister.getState(), contractorBillRegister.getStateHistory()));
        model.addAttribute("approvalDepartmentList", departmentService.getAllDepartments());
        model.addAttribute("approvalDesignation", request.getParameter("approvalDesignation"));
        model.addAttribute("approvalPosition", request.getParameter("approvalPosition"));

        final WorkOrder workOrder = contractorBillRegister.getWorkOrder();
        final LineEstimateDetails lineEstimateDetails = lineEstimateService.findByEstimateNumber(workOrder.getEstimateNumber());

        model.addAttribute("lineEstimateDetails", lineEstimateDetails);
        model.addAttribute("workOrder", workOrder);

        final ContractorBillRegister newcontractorBillRegister = getContractorBillDocuments(contractorBillRegister);
        model.addAttribute("contractorBillRegister", newcontractorBillRegister);
        return "contractorBill-update";
    }

    private ContractorBillRegister getContractorBillDocuments(final ContractorBillRegister contractorBillRegister) {
        List<DocumentDetails> documentDetailsList = new ArrayList<DocumentDetails>();
        documentDetailsList = worksUtils.findByObjectIdAndObjectType(contractorBillRegister.getId(),
                WorksConstants.CONTRACTORBILL);
        contractorBillRegister.setDocumentDetails(documentDetailsList);
        return contractorBillRegister;
    }

    @RequestMapping(value = "/view/{contractorBillRegisterId}", method = RequestMethod.GET)
    public String viewContractorBillRegister(final Model model, @PathVariable final String contractorBillRegisterId,
            final HttpServletRequest request)
                    throws ApplicationException {
        final ContractorBillRegister contractorBillRegister = getContractorBillRegister(contractorBillRegisterId);
        final String responsePage = loadViewData(model, request, contractorBillRegister);
        model.addAttribute("createdbybydesignation", worksUtils.getUserDesignation(contractorBillRegister.getCreatedBy()));
        model.addAttribute("mode", "readOnly");
        return responsePage;
    }

    public List<Map<String, Object>> getBillDetailsMap(final ContractorBillRegister contractorBillRegister) {
        final List<Map<String, Object>> billDetailsList = new ArrayList<Map<String, Object>>();
        Map<String, Object> billDetails = new HashMap<String, Object>();

        final List<CChartOfAccounts> contractorPayableAccountList = chartOfAccountsHibernateDAO
                .getAccountCodeByPurposeName(WorksConstants.CONTRACTOR_NETPAYABLE_PURPOSE);
        for (final EgBilldetails egBilldetails : contractorBillRegister.getEgBilldetailes()) {
            if (egBilldetails.getDebitamount() != null) {
                billDetails = new HashMap<String, Object>();
                final CChartOfAccounts coa = chartOfAccountsHibernateDAO.findById(egBilldetails.getGlcodeid().longValue(), false);
                billDetails.put("glcodeId", coa.getId());
                billDetails.put("glcode", coa.getGlcode());
                billDetails.put("accountHead", coa.getName());
                billDetails.put("amount", egBilldetails.getDebitamount());
                billDetails.put("isDebit", true);
                billDetails.put("isNetPayable", false);
            } else if (egBilldetails.getCreditamount() != null) {
                billDetails = new HashMap<String, Object>();
                final CChartOfAccounts coa = chartOfAccountsHibernateDAO.findById(egBilldetails.getGlcodeid().longValue(), false);
                billDetails.put("glcodeId", coa.getId());
                billDetails.put("glcode", coa.getGlcode());
                billDetails.put("accountHead", coa.getName());
                billDetails.put("amount", egBilldetails.getCreditamount());
                billDetails.put("isDebit", false);
                if (contractorPayableAccountList != null && !contractorPayableAccountList.isEmpty()
                        && contractorPayableAccountList.contains(coa))
                    billDetails.put("isNetPayable", true);
                else
                    billDetails.put("isNetPayable", false);

            }
            billDetailsList.add(billDetails);
        }
        return billDetailsList;
    }

}