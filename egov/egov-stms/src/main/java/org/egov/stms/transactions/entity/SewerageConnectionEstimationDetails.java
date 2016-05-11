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
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org
 ******************************************************************************/
package org.egov.stms.transactions.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "egswtax_estimation_details")
@SequenceGenerator(name = SewerageConnectionEstimationDetails.SEQ_ESTIMATIONDETAILS, sequenceName = SewerageConnectionEstimationDetails.SEQ_ESTIMATIONDETAILS, allocationSize = 1)
public class SewerageConnectionEstimationDetails extends AbstractAuditable {

    private static final long serialVersionUID = -3180187162013424817L;

    public static final String SEQ_ESTIMATIONDETAILS = "SEQ_EGSWTAX_ESTIMATION_DETAILS";

    @Id
    @GeneratedValue(generator = SEQ_ESTIMATIONDETAILS, strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationdetails", nullable = false)
    private SewerageApplicationDetails applicationDetails;

    @NotNull
    @SafeHtml
    @Length(max = 1024)
    private String itemDescription;

    private double unitRate;

    @SafeHtml
    @Length(max = 50)
    private String unitOfMeasurement;

    private double quantity;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public SewerageApplicationDetails getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(final SewerageApplicationDetails applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(final String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(final double unitRate) {
        this.unitRate = unitRate;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(final String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(final double quantity) {
        this.quantity = quantity;
    }

}