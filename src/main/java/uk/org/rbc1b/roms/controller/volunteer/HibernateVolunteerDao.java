/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.rbc1b.roms.controller.volunteer;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.org.rbc1b.roms.db.volunteer.Volunteer;

/**
 *
 * @author rahulsingh
 */
@Component
public class HibernateVolunteerDao implements VolunteerDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Volunteer findVolunteer(final Integer volId) {
        return (Volunteer) this.sessionFactory.getCurrentSession().get(Volunteer.class, volId);
    }

    @Override
    public List<Volunteer> findVolunteers() {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Volunteer.class);

        //TODO: support for other search criteria.

        return criteria.list();
    }
}
