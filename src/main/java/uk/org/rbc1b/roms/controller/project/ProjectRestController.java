/*
 * The MIT License
 *
 * Copyright 2014 RBC1B.
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
package uk.org.rbc1b.roms.controller.project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.org.rbc1b.roms.db.Person;
import uk.org.rbc1b.roms.db.PersonDao;
import uk.org.rbc1b.roms.db.project.ProjectAttendance;
import uk.org.rbc1b.roms.db.project.ProjectAttendanceDao;
import uk.org.rbc1b.roms.db.project.ProjectAvailability;
import uk.org.rbc1b.roms.db.project.ProjectAvailabilityDao;
import uk.org.rbc1b.roms.db.project.ProjectDepartmentSession;
import uk.org.rbc1b.roms.db.project.ProjectDepartmentSessionDao;
import uk.org.rbc1b.roms.db.volunteer.department.Assignment;
import uk.org.rbc1b.roms.db.volunteer.department.AssignmentSearchCriteria;
import uk.org.rbc1b.roms.db.volunteer.department.DepartmentDao;
import uk.org.rbc1b.roms.security.ROMSUserDetails;

/**
 * Handles all REST Project availability/attendance AJAX calls producing JSON.
 */
@RestController
@RequestMapping(value = "/service/projects")
public class ProjectRestController {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private ProjectDepartmentSessionDao projectDepartmentSessionDao;
    @Autowired
    private VolunteersToConfirmModelFactory volunteersToConfirmModelFactory;
    @Autowired
    private ProjectAvailabilityDao projectAvailabilityDao;
    @Autowired
    private ProjectAttendanceDao projectAttendanceDao;
    @Autowired
    private VolunteerForProjectModelFactory volunteerForProjectModelFactory;

    /**
     * Handles requests to insert into availability, when a volunteer is
     * invited.
     *
     * @param sessionId the projectDepartmentSession id
     * @param personId the volunteer to invite
     * @return response entity giving status
     */
    @RequestMapping(value = "/sessions/{sessionId}/person/{personId}/availability", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('PROJECT', 'EDIT')")
    public ResponseEntity<Void> insertAvailabilityForVolunteer(@PathVariable Integer sessionId,
            @PathVariable Integer personId) {
        ProjectAvailability availability = new ProjectAvailability();
        Person person = personDao.findPerson(personId);
        ProjectDepartmentSession workSession = projectDepartmentSessionDao.findByProjectDepartmentSessionId(sessionId);
        if (person == null || workSession == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            availability.setPerson(person);
            availability.setProjectDepartmentSession(workSession);

            availability.setUpdatedBy(findUserId());
            availability.setUpdateTime(new java.sql.Date(new java.util.Date().getTime()));

            projectAvailabilityDao.save(availability);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Handles requests to delete from availability, when a volunteer is not
     * invited.
     *
     * @param sessionId the projectdepartmentsession id
     * @param personId the person id
     * @return response entity giving status
     */
    @RequestMapping(value = "/sessions/{sessionId}/person/{personId}/availability", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission('PROJECT', 'EDIT')")
    public ResponseEntity<Void> deleteAvailabilityForVolunteer(@PathVariable Integer sessionId,
            @PathVariable Integer personId) {
        ProjectAvailability availability = projectAvailabilityDao.find(personId, sessionId);
        if (availability == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            projectAvailabilityDao.delete(availability);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Gets a list of dates in a range for a department session.
     *
     * @param sessionId the projectDepartmentSessionId
     * @return respnseEntity with list and status code
     */
    @RequestMapping(value = "/session/{sessionId}/dates", method = RequestMethod.GET)
    @PreAuthorize("hasPermission('PROJECT', 'READ')")
    public ResponseEntity<Object> getSessionDates(@PathVariable Integer sessionId) {
        ProjectDepartmentSession session = projectDepartmentSessionDao.findByProjectDepartmentSessionId(sessionId);
        if (session == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            List<String> dateRange = generateDateRange(new DateTime(session.getFromDate()), new DateTime(session.getToDate()));
            return new ResponseEntity<Object>(dateRange, HttpStatus.OK);
        }
    }

    /**
     * Returns a list of volunteers who are in the department matching
     * projectDepartmentSession.
     *
     * @param sessionId the project department session id
     * @return ResponseEntity containing list of volunteers and status
     */
    @RequestMapping(value = "/department/volunteers/session/{sessionId}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission('PROJECT', 'READ')")
    public ResponseEntity<Object> findDepartmentVolunteers(@PathVariable Integer sessionId) {
        ProjectDepartmentSession workSession = projectDepartmentSessionDao.findByProjectDepartmentSessionId(sessionId);
        if (workSession == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            AssignmentSearchCriteria assignmentSearchCriteria = new AssignmentSearchCriteria();
            assignmentSearchCriteria.setDepartmentId(workSession.getDepartment().getDepartmentId());
            List<Assignment> departmentVolunteers = departmentDao.findAssignments(assignmentSearchCriteria);
            return new ResponseEntity<Object>(volunteerForProjectModelFactory.generate(departmentVolunteers, workSession), HttpStatus.OK);
        }
    }

    /**
     * Gets a list of volunteers and the dates that they are available.
     *
     * @param sessionId the project department session id
     * @return responseEntity with the list and status code
     */
    @RequestMapping(value = "/session/{sessionId}/volunteers", method = RequestMethod.GET)
    @PreAuthorize("hasPermission('PROJECT', 'READ')")
    public ResponseEntity<Object> getVolunteersToConfirm(@PathVariable Integer sessionId) {
        ProjectDepartmentSession session = projectDepartmentSessionDao.findByProjectDepartmentSessionId(sessionId);
        if (session == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            AssignmentSearchCriteria assignmentSearchCriteria = new AssignmentSearchCriteria();
            assignmentSearchCriteria.setDepartmentId(session.getDepartment().getDepartmentId());
            List<Assignment> departmentVolunteers = departmentDao.findAssignments(assignmentSearchCriteria);
            List<VolunteerToConfirmModel> volunteersToConfirm =
                    volunteersToConfirmModelFactory
                    .generate(departmentVolunteers, session, generateDateRange(new DateTime(session.getFromDate()), new DateTime(session.getToDate())));
            return new ResponseEntity<Object>(volunteersToConfirm, HttpStatus.OK);
        }
    }

    /**
     * Saves the attendance required update.
     *
     * @param attendanceId the attendance id
     * @param requiredDate whether the volunteer is to be invited or not
     * @return responseEntity
     */
    @RequestMapping(value = "/attendance/{attendanceId}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission('PROJECT', 'EDIT')")
    public ResponseEntity<Void> inviteVolunteer(@PathVariable Integer attendanceId, @RequestBody ProjectAttendanceModel requiredDate) {
        ProjectAttendance projectAttendance = projectAttendanceDao.find(attendanceId);
        if (projectAttendance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            projectAttendance.setRequired(requiredDate.isRequired());
            projectAttendanceDao.update(projectAttendance);
            ProjectAvailability projectAvailability = projectAvailabilityDao.findById(projectAttendance.getProjectAvailability().getProjectAvailabilityId());
            if (projectAvailability != null) {
                projectAvailability.setOverseerConfirmed(true);
                projectAvailabilityDao.update(projectAvailability);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private List<String> generateDateRange(DateTime startDate, DateTime endDate) {
        List<String> range = new ArrayList<>();
        DateTime tmpdate;
        tmpdate = startDate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        while (tmpdate.isBefore(endDate) || tmpdate.equals(endDate)) {
            range.add(formatter.format(tmpdate.toDate()));
            tmpdate = tmpdate.plusDays(1);
        }
        return range;
    }

    private Integer findUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ROMSUserDetails user = (ROMSUserDetails) authentication.getPrincipal();
        return user.getUserId();
    }
}
