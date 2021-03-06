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
package uk.org.rbc1b.roms.db.project;

import java.sql.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * DAO for project attendance.
 *
 * @author ramindursingh
 */
@Repository
public class HibernateProjectAttendanceDao implements ProjectAttendanceDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void updateProjectAttendance(ProjectAttendance projectAttendance) {
        this.sessionFactory.getCurrentSession().merge(projectAttendance);
    }

    @Override
    public List<ProjectAttendance> getConfirmedDates(ProjectAvailability projectAvailability) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(ProjectAttendance.class);
        criteria.add(Restrictions.eq("projectAvailability.projectAvailabilityId", projectAvailability.getProjectAvailabilityId()));
        criteria.add(Restrictions.eq("required", Boolean.TRUE));
        return criteria.list();
    }

    @Override
    public List<ProjectAttendance> getDatesForVolunteer(ProjectAvailability projectAvailability) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(ProjectAttendance.class);
        criteria.add(Restrictions.eq("projectAvailability.projectAvailabilityId", projectAvailability.getProjectAvailabilityId()));
        return criteria.list();
    }
    @Override
    public ProjectAttendance getAvailableDate(ProjectAvailability projectAvailability, Date date) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(ProjectAttendance.class);
        criteria.add(Restrictions.eq("projectAvailability.projectAvailabilityId", projectAvailability.getProjectAvailabilityId()));
        criteria.add(Restrictions.eq("availableDate", date));
        return (ProjectAttendance) criteria.uniqueResult();
    }

    @Override
    public void save(ProjectAttendance projectAttendance) {
        this.sessionFactory.getCurrentSession().save(projectAttendance);
    }

    @Override
    public void delete(ProjectAttendance projectAttendance) {
        this.sessionFactory.getCurrentSession().delete(projectAttendance);
    }

    @Override
    public ProjectAttendance find(Integer projectAttendanceId) {
        return (ProjectAttendance) this.sessionFactory.getCurrentSession()
                .get(ProjectAttendance.class, projectAttendanceId);
    }

    @Override
    public void update(ProjectAttendance projectAttendance) {
        this.sessionFactory.getCurrentSession().merge(projectAttendance);
    }
}
