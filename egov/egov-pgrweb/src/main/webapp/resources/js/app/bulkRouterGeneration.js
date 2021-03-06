/*
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
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

$(document).ready(function()
{	
	$('#complaintTypeCategory').change(function() {
		$('#complaintType').find('option:gt(0)').remove();
		if (this.value === '') {
			$('#complaintTypes').empty();
		} else {
			$.ajax({
				type: "GET",
				url: "/pgr/complaint/officials/complainttypes-by-category",
				cache: true,
				data:{'categoryId' : this.value}
			}).done(function(data) {
				$('#complaintTypes').empty();
				$.each(data, function(key,value) {
					$('#complaintTypes').append('<option value="'+value.id+'">' + value.name + '</option>');
				});
			});
		}
	});
	
	$('#boundaryType').change(function() {
		var btype = "";
		$('#boundaries').find('option:gt(0)').remove();
		if (this.value === '') {
			$('#boundaries').empty();
		} else {
			$.ajax({
				type: "GET",
				url: "/egi/boundaries-by-boundaryType",
				cache: true,
				data:{'boundaryTypeId' : this.value}
			}).done(function(data) {
				$('#boundaries').empty();
				$.each(data, function(key,value) {
					$('#boundaries').append('<option value="'+value.Value+'">' + value.Text + '</option>');
				});
				if (btype != "") {
					$("#boundaries").val(btype);
					btype="";
				}
			});
		}
	});
	
	var position = new Bloodhound({
		datumTokenizer: function (datum) {
			return Bloodhound.tokenizers.whitespace(datum.value);
		},
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		remote: {
			url: '/pgr/complaint/router/position?positionName=%QUERY',
			dataType: "json",
			filter: function (data) {
				return $.map(data, function (pos) {
					return {
						name: pos.name,
						value: pos.id
					};
				});
			}
		}
	});
	
	position.initialize();
	
	var com_pos_typeahead = $('#position').typeahead({
		hint: false,
		highlight: true,
		minLength: 3
		}, {
		displayKey: 'name',
		source: position.ttAdapter()
	});
	
	typeaheadWithEventsHandling(com_pos_typeahead, '#positionId');
	
	
	$('#routerSearch').click(function(e){   
		if($('#bulkRouter').valid())
		{    
			oTable= $('#bulk_router_table');
			oTable.dataTable({
				"sPaginationType": "bootstrap",
				"sDom": "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-md-6 col-xs-12'i><'col-md-3 col-xs-6'l><'col-md-3 col-xs-6 text-right'p>>",
				"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
				"autoWidth": false,
				"bDestroy": true,
				"ajax": "/pgr/bulkRouter/search-result?"+$("#bulkRouter").serialize(),
				"columns" : [
				  { "mData" : "complaintType",
					 "sTitle" : "Grievance Type"
				  },
				  { "mData" : "boundaryType",
					"sTitle" : "Boundary Type"
				  },
				  { "mData" : "boundary",
					"sTitle" : "Boundary"
				  }, 
				  { "mData" : "position",
					"sTitle" : "Position"
				  },
				  { "mData" : "routerId",
					"visible": false
				  }]
					});
			e.stopPropagation();
			$('.data-save').removeClass('hide');
		}
		e.preventDefault();
		});
	
	$('#routersave').click(function(e){  
		if($("#bulk_router_table").dataTable().fnSettings().aoData.length == 0){
			document.forms["bulkRouter"].submit();//submit it
		}else{
			bootbox.confirm("Existing Router Data will be overridden, Are you sure?", function(result) {
				  if(result){
					  document.forms["bulkRouter"].submit();//submit it
				  }else{
					  //leave it.. Don't submit
				  }
				}); 
		}
	});
});
