
package wepa.wepa.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SubmissionFormObject {

    @NotBlank
    @Length(min = 9, max = 9)
    private String studentNumber;

    @NotBlank
    private String name;

    @Min(0)
    @NotNull
    private Integer exerciseCount;

    private String exerciseSubmission;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExerciseCount() {
        return exerciseCount;
    }

    public void setExerciseCount(Integer exerciseCount) {
        this.exerciseCount = exerciseCount;
    }

    public String getExerciseSubmission() {
        return exerciseSubmission;
    }

    public void setExerciseSubmission(String exerciseSubmission) {
        this.exerciseSubmission = exerciseSubmission;
    }

}
