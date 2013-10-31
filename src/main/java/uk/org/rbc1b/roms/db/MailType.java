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

import java.io.Serializable;

/**
 *
 * @author ramindursingh
 */
public class MailType implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer mailTypeId;
    private String mailCode;
    private String description;

    /**
     * @return the mailTypeId
     */
    public Integer getMailTypeId() {
        return mailTypeId;
    }

    /**
     * @param mailTypeId the mailTypeId to set
     */
    public void setMailTypeId(Integer mailTypeId) {
        this.mailTypeId = mailTypeId;
    }

    /**
     * @return the mailCode
     */
    public String getMailCode() {
        return mailCode;
    }

    /**
     * @param mailCode the mailCode to set
     */
    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
