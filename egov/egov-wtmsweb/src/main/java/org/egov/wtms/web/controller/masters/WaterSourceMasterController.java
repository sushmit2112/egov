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

package org.egov.wtms.web.controller.masters;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.validation.Valid;

import org.egov.wtms.masters.entity.WaterSource;
import org.egov.wtms.masters.service.WaterSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/masters")
public class WaterSourceMasterController {

    @Autowired
    private WaterSourceService waterSourceService;

    @RequestMapping(value = "/waterSourceTypeMaster", method = GET)
    public String viewForm(final Model model) {
        final WaterSource waterSource = new WaterSource();
        model.addAttribute("waterSource", waterSource);
        model.addAttribute("reqAttr", false);
        return "water-source-master";
    }

    @RequestMapping(value = "/waterSourceTypeMaster", method = RequestMethod.POST)
    public String createWaterSourceTypeMaster(@Valid @ModelAttribute final WaterSource waterSource,
            final RedirectAttributes redirectAttrs, final Model model, final BindingResult resultBinder,
            final BindingResult errors) {
        if (resultBinder.hasErrors())
            return "water-source-master";

        final WaterSource watersourceObj = waterSourceService.findByCodeAndWaterSourceType(waterSource.getCode(),
                waterSource.getWaterSourceType());

        if (watersourceObj != null) {
            redirectAttrs.addFlashAttribute(" WaterSource", watersourceObj);
            model.addAttribute("message", "Entered Code and Water Source Type already exists.");
            viewForm(model);
            return "water-source-master";
        } else {
            final WaterSource waterSourceCodeObj = waterSourceService.findByCodeIgnoreCase(waterSource.getCode());
            if (waterSourceCodeObj != null) {
                redirectAttrs.addFlashAttribute("WaterSource", waterSourceCodeObj);
                model.addAttribute("message", "Entered Code already exist.");
                viewForm(model);
                return "water-source-master";
            } else {

                final WaterSource waterSourceNameObj = waterSourceService
                        .findByWaterSourceTypeIgnoreCase(waterSource.getWaterSourceType());
                if (waterSourceNameObj != null) {
                    redirectAttrs.addFlashAttribute("WaterSource", waterSourceNameObj);
                    model.addAttribute("message", "Entered Water Source Type already exist.");
                    viewForm(model);
                    return "water-source-master";
                } else {
                    waterSourceService.createWaterSource(waterSource);
                    redirectAttrs.addFlashAttribute("waterSource", waterSource);
                }
            }
        }

        return getWaterSourceTypeList(model);
    }

    @RequestMapping(value = "/waterSourceTypeMaster/list", method = GET)
    public String getWaterSourceTypeList(final Model model) {
        final List<WaterSource> waterSourceList = waterSourceService.findAll();
        model.addAttribute("waterSourceList", waterSourceList);
        return "water-source-master-list";

    }

    @RequestMapping(value = "/waterSourceTypeMaster/{waterSourceId}", method = GET)
    public String getWaterSourceTypeDetails(final Model model, @PathVariable final String waterSourceId) {
        final WaterSource waterSource = waterSourceService.findOne(Long.parseLong(waterSourceId));
        model.addAttribute("waterSource", waterSource);
        model.addAttribute("reqAttr", "true");
        return "water-source-master";
    }

    @RequestMapping(value = "/waterSourceTypeMaster/{waterSourceId}", method = RequestMethod.POST)
    public String editWaterSourceTypeData(@Valid @ModelAttribute final WaterSource waterSource,
            @PathVariable final long waterSourceId, final RedirectAttributes redirectAttrs, final Model model,
            final BindingResult resultBinder) {
        if (resultBinder.hasErrors())
            return "water-source-master";
        waterSourceService.updateWaterSource(waterSource);
        redirectAttrs.addFlashAttribute("WaterSource", waterSource);
        return getWaterSourceTypeList(model);

    }

}