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
$(document).ready(function() {
    // list view
    roms.common.datatables(
        $('#project-list'),
        {
            "iDisplayLength": 10,
            "aoColumnDefs": [
            {
                'bSortable': false,
                'aTargets': [ 7 ]
            }
            ]
        }
    );

    $(".a-project-status, .a-project-count, .a-project-assignment, .a-event-created-by").tooltip();

    // details view
    $("#project-stages").sortable({
        items: "> div",
        update: function( event, ui ) {
            var stageIds = $(this).sortable('toArray').toString();
            $.ajax({
                url: roms.common.relativePath + '/projects/' + $(this).data("project-id") + "/stage-order",
                type: 'PUT',
                data:  {
                    stageIdValues: stageIds
                },
                error: function(xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + ": " + thrownError);
                }
            });
        }
    });

    // toggle the accordion and the accordion image
    $('.a-accordian-control').click(function() {
       var target = $(this).data("target");
       var $accordion = $(target);
       if ($accordion.hasClass("in")) {
           $accordion.collapse('hide');
           $("span", $(this)).addClass("glyphicon-plus");
           $("span", $(this)).removeClass("glyphicon-minus");
       } else {
           $accordion.collapse('show');
           $("span", $(this)).removeClass("glyphicon-plus");
           $("span", $(this)).addClass("glyphicon-minus");
       }
    });
    
    // project create/edit
    $("#kingdomHallName").typeahead({
        remote: roms.common.relativePath + '/kingdom-halls/search?name=%QUERY',
        valueKey: 'name'
    });
    
    // we always clear the kingdom hall id on change.
    // it will be re-calculated in validation
    $("#kingdomHallName").change(function() {
        $("#kingdomHallId").val(null);
    });
    
    $(".datepicker").datepicker({
        dateFormat: "dd/mm/yy",
        minDate: "-1y",
        maxDate: "+0d"
    });
    
    $("#coordinatorUserName").typeahead({
        remote: roms.common.relativePath + '/users/search?name=%QUERY',
        valueKey: 'userName'
    });
    
    // we always clear the coordinator user id on change.
    // it will be re-calculated in validation
    $("#coordinatorUserName").change(function() {
        $("#coordinatorUserId").val(null);
    });
    
    $("#projectForm").validate({
        rules: {
            name: {
                required: true,
                remote: {
                    // make sure this is not a duplicate name
                    url: roms.common.relativePath + "/projects/search",
                    contentType: "text/plain",
                    dataType: "text",
                    data: {
                        name: function() {
                            return $("#name").val();
                        }
                    },
                    dataFilter: function(rawData) {
                        // if we return an empty string, we didn't match an existing project name
                        return rawData == "";
                    }
                }
            },
            projectTypeId: {
                required: true
            },
            kingdomHallName: {
                remote: roms.common.validation.kingdomHall($("#kingdomHallName"), $("#kingdomHallId"))
            },
            coordinatorUserName: {
                remote: roms.common.validation.user($("#coordinatorUserName"), $("#coordinatorUserId"))
            }
        },
        messages: {
            name: {
                remote: "The project name must be unique"
            },
            kingdomHallName: {
                remote: "Please provide the name of an existing kingdom hall"
            },
            coordinatorUserName: {
                remote: "Please provide the name of an existing user"
            }
        },
        submitHandler :function(form) {
            form.submit();
        },
        errorPlacement: roms.common.validatorErrorPlacement
    });
    
    
});
