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
package org.egov.ptis.domain.service.property;

import static org.egov.ptis.constants.PropertyTaxConstants.ARR_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.ARR_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_EDUCATIONAL_CESS;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_GENERAL_TAX;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_LIBRARY_CESS;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.EisCommonService;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.infstr.workflow.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.egov.ptis.client.util.PropertyTaxUtil;
import org.egov.ptis.constants.PropertyTaxConstants;
import org.egov.ptis.domain.dao.demand.PtDemandDao;
import org.egov.ptis.domain.entity.demand.Ptdemand;
import org.egov.ptis.domain.entity.property.BasicProperty;
import org.egov.ptis.domain.entity.property.Property;
import org.egov.ptis.domain.entity.property.VacancyRemission;
import org.egov.ptis.domain.repository.vacancyremission.VacancyRemissionRepository;
import org.elasticsearch.common.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional(readOnly = true)
public class VacancyRemissionService {

	private static final Logger LOG = LoggerFactory.getLogger(VacancyRemissionService.class);
	
	private final VacancyRemissionRepository vacancyRemissionRepository;
	
	@Autowired
    private EisCommonService eisCommonService;
	
	@Autowired
    private AssignmentService assignmentService;

    @Autowired
    private PositionMasterService positionMasterService;
    
    @Autowired
    private SecurityUtils securityUtils;
    
    @Autowired
    private SimpleWorkflowService<VacancyRemission> vacancyRemissionWorkflowService;
    
    @Autowired
    private PropertyTaxUtil propertyTaxUtil;
    
    @Autowired
    private PtDemandDao ptDemandDAO;
    
    @Autowired
	public VacancyRemissionService(final VacancyRemissionRepository vacancyRemissionRepository){
		this.vacancyRemissionRepository = vacancyRemissionRepository;
	}
	
    public VacancyRemission getVacancyRemissionForProperty(final String upicNo) {
        return vacancyRemissionRepository.findByUpicNo(upicNo);
    }
    
    public VacancyRemission getVacancyRemissionById(Long id){
    	return vacancyRemissionRepository.findOne(id);
    }
    
    public void saveVacancyRemission(final VacancyRemission vacancyRemission,
            final Long approvalPosition, final String approvalComent, final String additionalRule,
            final String workFlowAction) {
        if(LOG.isDebugEnabled())
            LOG.debug(" Create WorkFlow Transition Started  ...");
        final User user = securityUtils.getCurrentUser();
        final DateTime currentDate = new DateTime();
        final Assignment userAssignment = assignmentService.getPrimaryAssignmentForUser(user.getId());
        Position pos = null;
        Assignment wfInitiator = null;
        
        if(vacancyRemission.getId()!=null && (workFlowAction.equalsIgnoreCase(PropertyTaxConstants.WFLOW_ACTION_STEP_REJECT) ||
        		workFlowAction.equalsIgnoreCase(PropertyTaxConstants.WFLOW_ACTION_STEP_NOTICE_GENERATE))){
        	wfInitiator = assignmentService.getPrimaryAssignmentForUser(vacancyRemission.getCreatedBy().getId());
        }
        
        if(workFlowAction.equalsIgnoreCase(PropertyTaxConstants.WFLOW_ACTION_STEP_NOTICE_GENERATE)){
        	if (wfInitiator.equals(userAssignment)) {
        		vacancyRemission.setStatus(PropertyTaxConstants.VR_STATUS_REJECTION_ACK_GENERATED);
            	vacancyRemission.transition(true).end().withSenderName(user.getName())
                .withComments(approvalComent).withDateInfo(currentDate.toDate());
            } 
        } else if (workFlowAction.equalsIgnoreCase(PropertyTaxConstants.WFLOW_ACTION_STEP_REJECT)) {
                final String stateValue = PropertyTaxConstants.WF_STATE_REJECTED;
                vacancyRemission.setStatus(PropertyTaxConstants.VR_STATUS_REJECTED);
                vacancyRemission.transition(true).withSenderName(user.getName()).withComments(approvalComent)
                .withStateValue(stateValue).withDateInfo(currentDate.toDate())
                .withOwner(wfInitiator.getPosition()).withNextAction("Application Rejected");
        } else {
        	if(workFlowAction.equalsIgnoreCase(PropertyTaxConstants.WFLOW_ACTION_STEP_FORWARD)){
            	vacancyRemission.setStatus(PropertyTaxConstants.VR_STATUS_WORKFLOW);
            }else if(workFlowAction.equalsIgnoreCase(PropertyTaxConstants.WFLOW_ACTION_STEP_APPROVE)){
            	vacancyRemission.setStatus(PropertyTaxConstants.VR_STATUS_APPROVED);
            }
        	
            if (null != approvalPosition && approvalPosition != -1 && !approvalPosition.equals(Long.valueOf(0)))
                pos = positionMasterService.getPositionById(approvalPosition);
            WorkFlowMatrix wfmatrix = null;
            if (null == vacancyRemission.getState()) {
                wfmatrix = vacancyRemissionWorkflowService.getWfMatrix(vacancyRemission.getStateType(), null,
                        null, additionalRule, null, null);
                vacancyRemission.transition().start().withSenderName(user.getName()).withComments(approvalComent)
                .withStateValue(wfmatrix.getNextState()).withDateInfo(new Date()).withOwner(pos)
                .withNextAction(wfmatrix.getNextAction());
            } else {
            	wfmatrix = vacancyRemissionWorkflowService.getWfMatrix(vacancyRemission.getStateType(), null,
                        null, additionalRule, vacancyRemission.getCurrentState().getValue(), null);
            	
            	if(wfmatrix!=null){
            		if(wfmatrix.getNextAction().equalsIgnoreCase("END")){
                    	vacancyRemission.transition().end().withSenderName(user.getName())
                        .withComments(approvalComent).withDateInfo(currentDate.toDate());
                    }else{
                    	vacancyRemission.transition(false).withSenderName(user.getName()).withComments(approvalComent)
                        .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate()).withOwner(pos)
                        .withNextAction(wfmatrix.getNextAction());
                    }
            	}
             }
            
        }
        if(LOG.isDebugEnabled())
            LOG.debug(" WorkFlow Transition Completed  ...");
        vacancyRemissionRepository.save(vacancyRemission);
    }
    
	public void addModelAttributes(Model model,BasicProperty basicProperty){
		Property property = basicProperty.getActiveProperty();
	    model.addAttribute("property", property);
	    Ptdemand ptDemand = ptDemandDAO.getNonHistoryCurrDmdForProperty(property);
	    if (ptDemand!=null && ptDemand.getDmdCalculations() != null && ptDemand.getDmdCalculations().getAlv() != null)
	    	model.addAttribute("ARV", ptDemand.getDmdCalculations().getAlv());
        else
        	model.addAttribute("ARV", BigDecimal.ZERO);
	    if (!basicProperty.getActiveProperty().getIsExemptedFromTax()) {
            final Map<String, BigDecimal> demandCollMap = propertyTaxUtil.prepareDemandDetForView(property, PropertyTaxUtil.getCurrentInstallment()); 
            model.addAttribute("currTax", demandCollMap.get(CURR_DMD_STR));
            model.addAttribute("eduCess", demandCollMap.get(DEMANDRSN_STR_EDUCATIONAL_CESS));
            model.addAttribute("currTaxDue",demandCollMap.get(CURR_DMD_STR).subtract(demandCollMap.get(CURR_COLL_STR)));
            model.addAttribute("libraryCess",demandCollMap.get(DEMANDRSN_STR_LIBRARY_CESS));
            model.addAttribute("totalArrDue",demandCollMap.get(ARR_DMD_STR).subtract(demandCollMap.get(ARR_COLL_STR)));
            model.addAttribute("generalTax",demandCollMap.get(DEMANDRSN_STR_GENERAL_TAX));
            model.addAttribute("totalTax",demandCollMap.get(DEMANDRSN_STR_EDUCATIONAL_CESS)
                .add(demandCollMap.get(DEMANDRSN_STR_LIBRARY_CESS))
                .add(demandCollMap.get(DEMANDRSN_STR_GENERAL_TAX)));
        }
	}
	
	/*@Transactional
    public void saveRemissionDetails(final VacancyRemission vacancyRemission) {
        vacancyRemissionRepository.save(vacancyRemission);
    }*/
		
}