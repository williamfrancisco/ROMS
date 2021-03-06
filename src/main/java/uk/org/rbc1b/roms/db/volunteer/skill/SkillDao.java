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
package uk.org.rbc1b.roms.db.volunteer.skill;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ramindursingh
 */
public interface SkillDao {

    /**
     * Find the skill matching the id, or null if no match.
     * @param skillId id
     * @return skill
     */
    @Transactional(readOnly = true)
    Skill findSkill(Integer skillId);

    /**
     * Find the skill with the prefix match name.
     * @param name skill name
     * @return skills
     */
    @Transactional(readOnly = true)
    List<Skill> findSkills(String name);

    /**
     * Find all matching skills.
     * @param searchCriteria search criteria
     * @return list of matching skills
     */
    @Transactional(readOnly = true)
    List<Skill> findSkills(SkillSearchCriteria searchCriteria);

    /**
     * Save a skill.
     * @param skill a skill to save
     */
    @Transactional
    void updateSkill(Skill skill);

    /**
     * Create a new skill.
     * @param skill new skill to create
     */
    @Transactional
    void createSkill(Skill skill);

    /**
     * Deletes a skill.
     * @param skill to delete
     */
    @Transactional
    void deleteSkill(Skill skill);

    /**
     * Find the category.
     * @param skillCategoryId id
     * @return Category, or null if not found
     */
    @Transactional(readOnly = true)
    SkillCategory findSkillCategory(Integer skillCategoryId);

    /**
     * Get all Category.
     * @return List Category
     */
    @Transactional(readOnly = true)
    List<SkillCategory> findSkillCategories();

}
