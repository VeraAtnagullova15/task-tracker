package ru.atnagullova.tasktrackerbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "header", nullable = false)
    private String header;

    @Column(name = "description")
    private String description;

    @Column(name = "done", nullable = false)
    private boolean done;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task() {
    }

    public Task(String header, String description) {
        this.header = header;
        this.description = description;
        this.done = false;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}
