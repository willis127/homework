package net.willis.homework.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Entity for Incident
 */
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;

    // generate unique index
    private static final AtomicLong counter = new AtomicLong(1);  // 从 1 开始

    private Long id;                   // 唯一标识符
    private String title;              // 事件标题
    private String description;        // 事件描述
    private Status status;             // 事件状态
    private Priority priority;         // 事件优先级
    private LocalDateTime createdAt;   // 事件创建时间
    private LocalDateTime updatedAt;   // 事件更新时间

    // 状态枚举
    public enum Status {
        OPEN, IN_PROGRESS, CLOSED
    }

    // 优先级枚举
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    // 无参构造器
    public Incident() {
        this.id = counter.getAndIncrement();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 全参构造器
    public Incident(String title, String description, Status status, Priority priority) {
        this.id = counter.getAndIncrement();
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getter 和 Setter 方法（省略setId方法，因为id自动生成）
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // 更新更新时间戳的方法
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}