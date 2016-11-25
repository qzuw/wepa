/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class ExerciseMeeting extends AbstractPersistable<Long> {
    
    private String description;
    
    public String getExerciseMeeting(){
        return description;
    }
    
    public void setExerciseMeeting(String id){
        this.description = id;
    }
    
}
