package com.bnd.ecommerce.entity;

import com.bnd.ecommerce.enums.LogTypeCategory;

import javax.persistence.*;

@Entity
@Table(name = "category_log")
public class CategoryLog extends CreateTimestamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Category category;

    private String messageLog;

    @Enumerated(EnumType.STRING)
    private LogTypeCategory logTypeCategory;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getMessageLog() {
        return messageLog;
    }

    public void setMessageLog(String messageLog) {
        this.messageLog = messageLog;
    }

    public LogTypeCategory getLogTypeCategory() {
        return logTypeCategory;
    }

    public void setLogTypeCategory(LogTypeCategory logTypeCategory) {
        this.logTypeCategory = logTypeCategory;
    }
}
