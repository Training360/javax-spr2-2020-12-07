package training.employees;

import org.hibernate.envers.RevisionListener;

public class StubUsernameListener implements RevisionListener {

    @Override
    public void newRevision(Object o) {
        var entity = (EmployeeRevisionEntity) o;
        entity.setUsername("trainer");
    }
}
