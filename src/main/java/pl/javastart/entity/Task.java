package pl.javastart.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String info;
    private boolean complete;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Task() {
    }

    public Task(String name, String info, boolean complete, LocalDateTime startDate, LocalDateTime endDate, Category category) {
        this.name = name;
        this.info = info;
        this.complete = complete;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public String showTask(String color) {
        return
                color + id +". Zadanie: " + name + "\n"
                        + "Info: " + info + "\n" +
                        "Data rozpoczecia: " + startDate  + "\n"
                        +"Data zakonczenia: " + endDate + "\n"
                        + "Kategoria: " + category.getCategoryName() + "\n";
    }
}
