/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class WeeklyExercise extends AbstractPersistable<Long> {

    private String description;

    public String getWeeklyExercise() {
        return description;
    }

    public void setWeeklyExercise(String description) {
        this.description = description;
    }
}
