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

package org.egov.stms.transactions.charges;

import java.math.BigDecimal;

import org.egov.stms.masters.entity.enums.PropertyType;
import org.egov.stms.masters.repository.SewerageRatesMasterRepository;
import org.egov.stms.transactions.entity.SewerageApplicationDetails;
import org.egov.stms.transactions.entity.SewerageConnectionDetail;
import org.egov.stms.transactions.repository.DonationMasterDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SewerageChargeCalculationServiceImpl implements SewerageChargeCalculationService {

    @Autowired
    private DonationMasterDetailsRepository donationMasterDetailsRepository;

    @Autowired
    private SewerageRatesMasterRepository sewerageRatesMasterRepository;

    /**
     * @param sewerageApplicationDetails
     * @return This will return donation charges based on NoOfClosets and
     *         Property Type.
     */
    @Override
    public BigDecimal calculateDonationCharges(final SewerageApplicationDetails sewerageApplicationDetails) {
        BigDecimal amount = BigDecimal.ZERO;
        Integer noOfClosets;

        if (sewerageApplicationDetails.getConnection() != null
                && sewerageApplicationDetails.getConnection().getConnectionDetail() != null) {
            final SewerageConnectionDetail sewerageConnectionDetail = sewerageApplicationDetails.getConnection()
                    .getConnectionDetail();
            if (sewerageConnectionDetail != null
                    && sewerageConnectionDetail.getPropertyType().equals(PropertyType.MIXED)) {
                final BigDecimal amountForResidential = donationMasterDetailsRepository.getDonationAmount(
                        sewerageConnectionDetail.getNoOfClosetsResidential(), PropertyType.RESIDENTIAL);
                final BigDecimal amountForNonResidential = donationMasterDetailsRepository.getDonationAmount(
                        sewerageConnectionDetail.getNoOfClosetsNonResidential(), PropertyType.NON_RESIDENTIAL);
                return amountForResidential.add(amountForNonResidential);
            } else {
                noOfClosets = sewerageConnectionDetail.getPropertyType().equals(PropertyType.RESIDENTIAL) ? sewerageConnectionDetail
                        .getNoOfClosetsResidential() : sewerageConnectionDetail.getPropertyType().equals(
                        PropertyType.NON_RESIDENTIAL) ? sewerageConnectionDetail.getNoOfClosetsNonResidential() : 0;
                amount = donationMasterDetailsRepository.getDonationAmount(noOfClosets,
                        sewerageConnectionDetail.getPropertyType());
            }
        }
        return amount != null ? amount : BigDecimal.ZERO;
    }

    /**
     * @param sewerageApplicationDetails
     * @return This will return sewerage charges for monthly based on
     *         NoOfClosets and Property Type.
     */

    @Override
    public BigDecimal calculateSewerageCharges(final SewerageApplicationDetails sewerageApplicationDetails) {
        Integer noOfClosets;
        BigDecimal sewerateRate = BigDecimal.ZERO;
        Double monthlyRateAmount = 0.0;
        if (sewerageApplicationDetails.getConnection() != null
                && sewerageApplicationDetails.getConnection().getConnectionDetail() != null) {
            final SewerageConnectionDetail sewerageConnectionDetail = sewerageApplicationDetails.getConnection()
                    .getConnectionDetail();
            if (sewerageConnectionDetail != null
                    && sewerageConnectionDetail.getPropertyType().equals(PropertyType.MIXED)) {
                noOfClosets = sewerageConnectionDetail.getNoOfClosetsResidential();
                monthlyRateAmount = sewerageRatesMasterRepository.getSewerageRates(PropertyType.RESIDENTIAL);

                if (monthlyRateAmount != null)
                    sewerateRate = BigDecimal.valueOf(noOfClosets * monthlyRateAmount);
                noOfClosets = sewerageConnectionDetail.getNoOfClosetsNonResidential();
                monthlyRateAmount = sewerageRatesMasterRepository.getSewerageRates(PropertyType.NON_RESIDENTIAL);

                if (monthlyRateAmount != null)
                    sewerateRate = sewerateRate.add(BigDecimal.valueOf(noOfClosets * monthlyRateAmount));
                return sewerateRate;
            } else {
                noOfClosets = sewerageConnectionDetail.getPropertyType().equals(PropertyType.RESIDENTIAL) ? sewerageConnectionDetail
                        .getNoOfClosetsResidential() : sewerageConnectionDetail.getPropertyType().equals(
                        PropertyType.NON_RESIDENTIAL) ? sewerageConnectionDetail.getNoOfClosetsNonResidential() : 0;
                monthlyRateAmount = sewerageRatesMasterRepository.getSewerageRates(sewerageConnectionDetail
                        .getPropertyType());
                if (monthlyRateAmount != null)
                    sewerateRate = BigDecimal.valueOf(monthlyRateAmount * noOfClosets);
            }
        }
        return sewerateRate != null ? sewerateRate : BigDecimal.ZERO;
    }
}
