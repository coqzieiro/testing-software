package pt.up.fe.testing;

public class PassingGrade {

    public boolean passed(float grade) {
        assert grade >= 0.0 && grade <= 20.0;
        return grade >= 9.5;
    }
}
