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
package uk.org.rbc1b.roms.db.volunteer.department;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import uk.org.rbc1b.roms.db.Person;
import uk.org.rbc1b.roms.db.UpdateAuditable;

/**
 * The volunteers role within a team/department.
 * @author oliver.elder.esq
 */
@Audited
public class Assignment implements UpdateAuditable, Serializable {
    private static final long serialVersionUID = -3748152854538626576L;
    private Integer assignmentId;
    private Person person;
    private Integer departmentId;
    private AssignmentRole role;
    private java.sql.Date assignedDate;
    private Integer tradeNumberId;
    private Team team;
    private Date updateTime;
    private Integer updatedBy;

    public java.sql.Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(java.sql.Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    public AssignmentRole getRole() {
        return role;
    }

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    public void setRole(AssignmentRole role) {
        this.role = role;
    }

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    public Team getTeam() {
        return team;
    }

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getTradeNumberId() {
        return tradeNumberId;
    }

    public void setTradeNumberId(Integer tradeNumberId) {
        this.tradeNumberId = tradeNumberId;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "Assignment{" + "assignmentId=" + assignmentId + '}';
    }
}
