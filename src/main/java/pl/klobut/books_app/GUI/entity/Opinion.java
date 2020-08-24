package pl.klobut.books_app.GUI.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Opinion {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    int points;
    String description;

    public Opinion(int points, String description) {
        this.points = points;
        this.description = description;
    }

    public Opinion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
