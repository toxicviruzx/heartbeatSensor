package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Dung Le
 * Student number: 433296
 * on 5/28/2017.
 */

/**
 * This method will transfer data from int to IntegerProperty which is
 * to show the data in the table view form.
 * @author TuanDung
 * @see IntegerProperty
 * @see SimpleIntegerProperty
 */
public class TableData {
    private final StringProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty gender;
    private final StringProperty dateOfBirth;
    private final StringProperty note;
//    private final Date
//    private final IntegerProperty cases;

    /**
     * Constructor to turn input int values into IntegerProperty values
     * @param id
     * @param firstName
     * @param lastName
     * @param gender
     * @param dateOfBirth
     * @param note
     */
    public TableData(String id, String firstName, String lastName, String gender, String dateOfBirth, String note) {
//        super();
        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gender = new SimpleStringProperty(gender);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.note = new SimpleStringProperty(note);
    }

    public String getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName(){
        return lastName.get();
    }

    public String getGender(){
        return gender.get();
    }

    public String getDateOfBirth(){
        return dateOfBirth.get();
    }

    public String getNote(){
        return note.get();
    }
}
