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
package uk.org.rbc1b.roms.db.volunteer;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @author ramindursingh
 */
@Repository
public class HibernateSkillDao implements SkillDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Skill findSkill(final Integer skillId) {
        return (Skill) this.sessionFactory.getCurrentSession().get(Skill.class, skillId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Skill> findSkills(SkillSearchCriteria searchCriteria) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Skill.class);

        if (searchCriteria.getDepartmentId() != null) {
            criteria.add(Restrictions.eq("department.id", searchCriteria.getDepartmentId()));
        }

        return criteria.list();
    }

    @Override
    public void saveSkill(Skill skill) {
        if (skill.getSkillId() == null) {
            this.sessionFactory.getCurrentSession().save(skill);
        } else {
            this.sessionFactory.getCurrentSession().merge(skill);
        }
    }

    @Override
    public void createSkill(Skill skill) {
        this.sessionFactory.getCurrentSession().save(skill);
    }

    @Override
    public void deleteSkill(Skill skill) {
        this.sessionFactory.getCurrentSession().delete(skill);
    }

    @Override
    @Cacheable("category.category")
    public Category findCategory(Integer categoryId) {
        return (Category) this.sessionFactory.getCurrentSession().get(Category.class, categoryId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> findCategories() {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Category.class);
        return criteria.list();
    }

    /**
     * @param sessionFactory hibernate session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
