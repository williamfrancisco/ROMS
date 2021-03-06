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
package uk.org.rbc1b.roms.db;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Add/update/search PersonChange table.
 *
 * @author ramindursingh
 */
public interface PersonChangeDao {

    /**
     * Look up the person change by the primary key of the change.
     *
     * @param personChangeId the person change id
     * @return PersonChange, or null
     */
    @Transactional
    PersonChange findPersonChange(Integer personChangeId);

    /**
     * Look up the person changes by the primary key of person.
     *
     * @param personId the person id
     * @return list of person changes
     */
    @Transactional
    List<PersonChange> findPersonChanges(Integer personId);

    /**
     * Look up the changes for which the paper work have not been updated.
     *
     * @return list of personchange
     */
    @Transactional
    List<PersonChange> findPersonChangeNotUpdated();

    /**
     * Find all changes.
     *
     * @return list of person changes
     */
    @Transactional
    List<PersonChange> findPersonChange();

    /**
     * Update the PersonChange when the paperwork has been updated.
     *
     * @param personChange to update
     */
    @Transactional
    void updatePersonChange(PersonChange personChange);

    /**
     * Save the PersonChange date.
     *
     * @param personChange to save
     */
    @Transactional
    void savePersonChange(PersonChange personChange);

    /**
     * Gets the old person.
     *
     * @param personId the person to find
     * @param oldPerson to clear from cache
     * @return Person old person
     */
    @Transactional
    Person getOldPerson(Integer personId, Person oldPerson);
}
