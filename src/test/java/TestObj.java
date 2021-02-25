import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Testing class for hibernate testing
 */
@Entity
@Table
public class TestObj {
    @Id
    private int id;
    @Column
    private int someNumber;

    public TestObj(){

    }

    public TestObj(int number) {
        this.someNumber = number;
    }

    public int getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(int someNumber) {
        this.someNumber = someNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
