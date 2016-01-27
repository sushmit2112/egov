/**
 * eGov suite of products aim to improve the internal efficiency,transparency, accountability and the service delivery of the
 * government organizations.
 *
 * Copyright (C) <2015> eGovernments Foundation
 *
 * The updated version of eGov suite of products as by eGovernments Foundation is available at http://www.egovernments.org
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * http://www.gnu.org/licenses/ or http://www.gnu.org/licenses/gpl.html .
 *
 * In addition to the terms of the GPL license to be adhered to in using this program, the following additional terms are to be
 * complied with:
 *
 * 1) All versions of this program, verbatim or modified must carry this Legal Notice.
 *
 * 2) Any misrepresentation of the origin of the material is prohibited. It is required that all modified versions of this
 * material be marked in reasonable ways as different from the original version.
 *
 * 3) This license does not grant any rights to any user of the program with regards to rights under trademark law for use of the
 * trade names or trademarks of eGovernments Foundation.
 *
 * In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.stms.transactions.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.egov.demand.model.EgDemand;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.stms.masters.entity.enums.PropertyType;
import org.egov.stms.masters.entity.enums.SewerageConnectionStatus;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "egswtax_connection")
@SequenceGenerator(name = SewerageConnection.SEQ_CONNECTION, sequenceName = SewerageConnection.SEQ_CONNECTION, allocationSize = 1)
public class SewerageConnection extends AbstractAuditable {

    private static final long serialVersionUID = 1248986191220418633L;

    public static final String SEQ_CONNECTION = "SEQ_EGSWTAX_CONNECTION";

    @Id
    @GeneratedValue(generator = SEQ_CONNECTION, strategy = GenerationType.SEQUENCE)
    private Long id;

    @SafeHtml
    @Length(min = 3, max = 50)
    @Column(name = "dhsc_number")
    private String dhscNumber;

    @NotNull
    @SafeHtml
    @Length(min = 3, max = 50)
    private String propertyIdentifier;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(name = "noofclosets_residential")
    private Integer noOfClosetsResidential;

    @Column(name = "noofclosets_nonresidential")
    private Integer noOfClosetsNonResidential;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SewerageConnectionStatus connectionStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "demand")
    private EgDemand demand;

    private boolean legacy = false;

    @Temporal(value = TemporalType.DATE)
    private Date executionDate;

    @OrderBy("id desc")
    @OneToMany(mappedBy = "connection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SewerageConnectionHistory> connectionHistories = new ArrayList<SewerageConnectionHistory>(0);

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getDhscNumber() {
        return dhscNumber;
    }

    public void setDhscNumber(final String dhscNumber) {
        this.dhscNumber = dhscNumber;
    }

    public String getPropertyIdentifier() {
        return propertyIdentifier;
    }

    public void setPropertyIdentifier(final String propertyIdentifier) {
        this.propertyIdentifier = propertyIdentifier;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(final PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getNoOfClosetsResidential() {
        return noOfClosetsResidential;
    }

    public void setNoOfClosetsResidential(final Integer noOfClosetsResidential) {
        this.noOfClosetsResidential = noOfClosetsResidential;
    }

    public Integer getNoOfClosetsNonResidential() {
        return noOfClosetsNonResidential;
    }

    public void setNoOfClosetsNonResidential(final Integer noOfClosetsNonResidential) {
        this.noOfClosetsNonResidential = noOfClosetsNonResidential;
    }

    public SewerageConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(final SewerageConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public EgDemand getDemand() {
        return demand;
    }

    public void setDemand(final EgDemand demand) {
        this.demand = demand;
    }

    public boolean getLegacy() {
        return legacy;
    }

    public void setLegacy(final boolean legacy) {
        this.legacy = legacy;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(final Date executionDate) {
        this.executionDate = executionDate;
    }

    public List<SewerageConnectionHistory> getConnectionHistories() {
        return connectionHistories;
    }

    public void setConnectionHistories(final List<SewerageConnectionHistory> connectionHistories) {
        this.connectionHistories = connectionHistories;
    }

}