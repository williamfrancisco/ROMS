/*
 * The MIT License
 *
 * Copyright 2013 RBC1B.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
if (!roms) {
    var roms = {};
};

if (!roms.common) {
    roms.common = {};
};

roms.common.validatorPhoneNumber = function(value, element) {
    if (this.optional(element)) {
        return true;
    } else {
        try {
            var phone = cleanPhone(value);
            var phoneUtil = i18n.phonenumbers.PhoneNumberUtil.getInstance();
            var number = phoneUtil.parseAndKeepRawInput(phone, "GB");
            return phoneUtil.isValidNumber(number);
        } catch (exception) {
            return false;
        }
    }
};

roms.common.validatorMobilePhoneNumber = function(value, element) {
    if (this.optional(element)) {
        return true;
    } else {
        try {
            var phone = cleanPhone(value);
            var phoneUtil = i18n.phonenumbers.PhoneNumberUtil.getInstance();
            var number = phoneUtil.parseAndKeepRawInput(phone, "GB");
            return phoneUtil.getNumberType(number) == 1;
        } catch (exception) {
            return false;
        }
    }
};

roms.common.validatorErrorPlacement = function(error, element) {
    var $container = element.closest(".error-container");
    if ($container.length > 0) {
        error.appendTo($container);
    } else {
        element.after(error);
    }
};


/**
 * Match a person to a given forename and surname (exact match).
 * This assumes we have the person-link-search-form and person-link-modal elements on the page
 * @param forename person's first name
 * @param surname person's last name
 * @param $personId jquery element linking to the input use to store the matched person id
 * @param populateFunction callback to populate the page with the selected person
 */
roms.common.matchLinkedPerson = function (forename, surname, $personId, populateFunction) {
    if(!forename || !surname) {
        // clear any linked name
        $personId.data("full-name", "");
        populateFunction(null, forename, surname, $personId);
        return;
    }

    var existingPersonName = $personId.data("full-name");
    if (existingPersonName == forename + " " + surname) {
        // no change in value
        return;
    }
    var existingPersonId = $personId.val();

    $.ajax({
        url: roms.common.relativePath + '/persons/search',
        contentType: "application/json",
        dataType: 'json',
        data:  {
            forename: forename,
            surname: surname,
            checkVolunteer: false
        },
        success: function(data) {
            // no match, and no person linked. We don't show anything
            if ((!data || data.length === 0) && !existingPersonId) {
                return;
            }

            templateData = {};
            templateData.existingPersonId = existingPersonId;
            templateData.existingPersonName = existingPersonName;

            if (data) {
                templateData.matchedPersons = true;
                templateData.results = data;
            }

            var template = $("#person-link-search-form").html();
            var html = Mustache.to_html(template, templateData);

            $("#person-link-modal .modal-body").html(html)
            var modalElement = $("#person-link-modal")

            modalElement.modal('show')

            // if they select the person id, set it to the hidden volunteer person id field
            $("a.matched-person").on("click", function(event){
                populateFunction($(this).data("person-id"), forename, surname, $personId);
                modalElement.modal('hide')
            });
        }
    });

    $personId.data("full-name", forename + " " + surname);
}

/**
 * Extensions to datatables to fit in with bootstrap styling
 * See http://www.datatables.net/media/blog/bootstrap_2/DT_bootstrap.js
 * @param $table table element
 * @param additional dataTable options to set
 */
roms.common.datatables = function($table, options) {
    $.extend( true, $.fn.dataTable.defaults, {
        "sDom": "<'row'<'col-md-2 pull-right'l><'col-md-3 col-md-offset-7'f>>rt<'row'<'col-md-6'i><'col-md-6'p>>",
        "sPaginationType": "bootstrap",
        "oLanguage": {
            "sLengthMenu": "_MENU_ records per page"
        }
    } );

    $.extend( $.fn.dataTableExt.oStdClasses, {
        "sWrapper": "dataTables_wrapper form-inline"
    } );

    /* API method to get paging information */
    $.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
    {
        return {
            "iStart":         oSettings._iDisplayStart,
            "iEnd":           oSettings.fnDisplayEnd(),
            "iLength":        oSettings._iDisplayLength,
            "iTotal":         oSettings.fnRecordsTotal(),
            "iFilteredTotal": oSettings.fnRecordsDisplay(),
            "iPage":          oSettings._iDisplayLength === -1 ?
            0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
            "iTotalPages":    oSettings._iDisplayLength === -1 ?
            0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
        };
    };


    /* Bootstrap style pagination control */
    $.extend( $.fn.dataTableExt.oPagination, {
        "bootstrap": {
            "fnInit": function( oSettings, nPaging, fnDraw ) {
                var oLang = oSettings.oLanguage.oPaginate;
                var fnClickHandler = function ( e ) {
                    e.preventDefault();
                    if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
                        fnDraw( oSettings );
                    }
                };

                $(nPaging).append(
                    '<ul class="pagination">'+
                    '<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
                    '<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
                    '</ul>'
                    );
                var els = $('a', nPaging);
                $(els[0]).bind( 'click.DT', {
                    action: "previous"
                }, fnClickHandler );
                $(els[1]).bind( 'click.DT', {
                    action: "next"
                }, fnClickHandler );
            },

            "fnUpdate": function ( oSettings, fnDraw ) {
                var iListLength = 5;
                var oPaging = oSettings.oInstance.fnPagingInfo();
                var an = oSettings.aanFeatures.p;
                var i, ien, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

                if ( oPaging.iTotalPages < iListLength) {
                    iStart = 1;
                    iEnd = oPaging.iTotalPages;
                }
                else if ( oPaging.iPage <= iHalf ) {
                    iStart = 1;
                    iEnd = iListLength;
                } else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
                    iStart = oPaging.iTotalPages - iListLength + 1;
                    iEnd = oPaging.iTotalPages;
                } else {
                    iStart = oPaging.iPage - iHalf + 1;
                    iEnd = iStart + iListLength - 1;
                }

                for ( i=0, ien=an.length ; i<ien ; i++ ) {
                    // Remove the middle elements
                    $('li:gt(0)', an[i]).filter(':not(:last)').remove();

                    // Add the new list items and their event handlers
                    for ( j=iStart ; j<=iEnd ; j++ ) {
                        sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
                        $('<li '+sClass+'><a href="#">'+j+'</a></li>')
                        .insertBefore( $('li:last', an[i])[0] )
                        .bind('click', function (e) {
                            e.preventDefault();
                            oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
                            fnDraw( oSettings );
                        } );
                    }

                    // Add / remove disabled classes from the static elements
                    if ( oPaging.iPage === 0 ) {
                        $('li:first', an[i]).addClass('disabled');
                    } else {
                        $('li:first', an[i]).removeClass('disabled');
                    }

                    if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                        $('li:last', an[i]).addClass('disabled');
                    } else {
                        $('li:last', an[i]).removeClass('disabled');
                    }
                }
/*                //If all data fits into one page, hide the pagination and "Showing ? of ? Entries"
                if (oPaging.iTotalPages <= 1) {
                    $('.dataTables_paginate').hide();
                    $('.dataTables_info').hide();
                } else {
                   $('.dataTables_paginate').show();
                   $('.dataTables_info').show(); 
                }
                //Search box and "... records per page" hide themselves if total records are 5 or less.
                if (oPaging.iTotal <= 5)    {
                    $('.dataTables_filter').hide();
                    $('.dataTables_length').hide();
                } else  {
                    $('.dataTables_filter').show();
                    $('.dataTables_length').show();
                } */
        }
    } });


    /*
     * TableTools Bootstrap compatibility
     * Required TableTools 2.1+
     */
    if ( $.fn.DataTable.TableTools ) {
        // Set the classes that TableTools uses to something suitable for Bootstrap
        $.extend( true, $.fn.DataTable.TableTools.classes, {
            "container": "DTTT btn-group",
            "buttons": {
                "normal": "btn",
                "disabled": "disabled"
            },
            "collection": {
                "container": "DTTT_dropdown dropdown-menu",
                "buttons": {
                    "normal": "",
                    "disabled": "disabled"
                }
            },
            "print": {
                "info": "DTTT_print_info modal"
            },
            "select": {
                "row": "active"
            }
        } );

        // Have the collection use a bootstrap compatible dropdown
        $.extend( true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
            "collection": {
                "container": "ul",
                "button": "li",
                "liner": "a"
            }
        } );
    }

    // create the table
    $table.dataTable(options);
}

/**
 * Delete a row in a datatables row
 * @param $element the action element in the row to be deleted
 */
function deleteDataTablesRow($element) {
    var $row = $element.closest("tr")[0];
    var $table = $element.closest("table");

    var dataTable = $table.dataTable();
    dataTable.fnDeleteRow($row);
}

roms.common.persistentTabs = function() {
    if (location.hash.substr(0,2) == "#!") {
        $("a[href='#" + location.hash.substr(2) + "']").tab("show");
    }

    $("a[data-toggle='tab']").on("shown", function (e) {
        var hash = $(e.target).attr("href");
        if (hash.substr(0,1) == "#") {
            location.replace("#!" + hash.substr(1));
        }
    });
}


roms.common.validation = {};
$(document).ready(function() {
    // store the relative path, used for all the ajax calls
    // we trim the trailing slash to allow uris that include it look absolute
    roms.common.relativePath = $("#relative-path").data("relative-path").replace(/\/$/, '');
    
    // remote validation method for kingdom hall typeahead lookups
    roms.common.validation.kingdomHall = function($nameInput, $idInput) { 
        return {
            // check for an exact match. Populate the kingdom hall id
            url: roms.common.relativePath + "/kingdom-halls/search",
            contentType: "application/json",
            dataType: "json",
            data: {
                name: function() {
                    return $nameInput.val();
                }
            },
            dataFilter: function(rawData) {
                var data = JSON.parse(rawData)
                if (data && data.length > 0 && data[0].name == $nameInput.val()) {
                    $idInput.val(data[0].id);
                    return true;
                } 
             // no match - clear the stored id
                $idInput.val(null);
                return false;
            }
        };
    }
    
    roms.common.validation.congregation = function($nameInput, $idInput) {
        return {
            // check for an exact match. Populate the congregation id
            url: roms.common.relativePath + "/congregations/search",
            contentType: "application/json",
            dataType: "json",
            data: {
                name: function() {
                    return $nameInput.val();
                }
            },
            dataFilter: function(rawData) {
                var data = JSON.parse(rawData)
                if (data && data.length > 0 && data[0].name == $nameInput.val()) {
                    $idInput.val(data[0].id);
                    return true;
                }
                // no match - clear the stored id
                $idInput.val(null);
                return false;
            }
        };
    }
    
    roms.common.validation.user = function($nameInput, $idInput) {
        return {
            // check for an exact match. Populate the user id
            url: roms.common.relativePath + "/users/search",
            contentType: "application/json",
            dataType: "json",
            data: {
                name: function() {
                    return $nameInput.val();
                }
            },
            dataFilter: function(rawData) {
                var data = JSON.parse(rawData)
                if (data && data.length > 0 && data[0].userName == $nameInput.val()) {
                    $idInput.val(data[0].personId);
                    return true;
                } 
                // no match - clear the stored id
                $idInput.val(null);
                return false;
            }
        };
    }
    
    roms.common.validation.department = function($nameInput, $idInput) {
        return {
            // check for an exact match. Populate the department id
            url: roms.common.relativePath + "/departments/search",
            contentType: "application/json",
            dataType: "json",
            data: {
                name: function() {
                    return $nameInput.val();
                }
            },
            dataFilter: function(rawData) {
                var data = JSON.parse(rawData)
                if (data && data.length > 0 && data[0].name == $nameInput.val()) {
                    $idInput.val(data[0].id);
                    return true;
                }
                // no match - clear the stored id
                $idInput.val(null);
                return false;
            }
        };
    }
    
    
});

// validation configuration


