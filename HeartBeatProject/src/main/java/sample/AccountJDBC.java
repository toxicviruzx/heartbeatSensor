package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccountJDBC {
    private static AccountJDBC uniqueInstance = null;
    private static Connection connection = null;

    private AccountJDBC(DBmanager db) { //precondition dbExisis()
        if ((connection = db.getConnection()) == null) //connect to the db
            System.err.println(">>> The database doesn't exist ...") ;
    }

    public static synchronized AccountJDBC getInstance(DBmanager db) {
        if (uniqueInstance == null)
            uniqueInstance = new AccountJDBC(db);
        return uniqueInstance;
    }

    public boolean getUserName(String username){
        PreparedStatement preparedStatement = null;
        boolean hasThisUserName = false;
        String result = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT user FROM account WHERE user LIKE ?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getString("user");
            }
            if(result != null){
                if(result.matches(username)){
                    hasThisUserName = true;
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return hasThisUserName;
    }

    public boolean getIdNumber(String idNumber){
        PreparedStatement preparedStatement = null;
        boolean hasThisIdNumber = false;
        String result = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT ID FROM account WHERE ID LIKE ?");
            preparedStatement.setString(1,idNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getString("ID");
            }
            if(result != null){
                if(result.matches(idNumber)){
                    hasThisIdNumber = true;
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return hasThisIdNumber;
    }

    public String getPassword(String username) {
        String password = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT password FROM account WHERE user LIKE ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                password = resultSet.getString("password");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return password;
    }

    public void createAccount(String idNumber, String username, String password, String firstName, String familyName, String gender, String DOB){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO account VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1,idNumber);
            preparedStatement.setString(2,username);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,firstName);
            preparedStatement.setString(5,familyName);
            preparedStatement.setString(6,gender);
            preparedStatement.setString(7,DOB);
            preparedStatement.setString(8,"none");
            preparedStatement.setString(9,"0");
            preparedStatement.setString(10,"p");
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void updateOnlineStatus(Boolean onlineStatus, String id){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE account SET status = (?) WHERE ID = (?)");
            if(onlineStatus == true){
                preparedStatement.setString(1,"1");
            } else {
                preparedStatement.setString(1,"0");
            }
            preparedStatement.setString(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public String getFirstName(String id){
        String firstName = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT firstname FROM account WHERE id LIKE ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getString("firstname");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return firstName;
    }

    public String getLastName(String id){
        String lastName = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT familyname FROM account WHERE id LIKE ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                lastName = resultSet.getString("familyname");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return lastName;
    }

    public String getGender(String id){
        String gender = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT gender FROM account WHERE id LIKE ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                gender = resultSet.getString("gender");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return gender;
    }

    public String getOnlineStatus(String id){
        String onlineStatus = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT status FROM account WHERE id LIKE ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                onlineStatus = resultSet.getString("status");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return onlineStatus;
    }

    public ArrayList getPatientIdList(){
        ArrayList<String> patientIdList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT ID FROM account WHERE type = 'p'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                patientIdList.add(resultSet.getString("ID"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return patientIdList;
    }

    public ArrayList getPatientUserNameList(){
        ArrayList patientUserList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT user FROM account WHERE type = 'p'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                patientUserList.add(resultSet.getString("user"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return patientUserList;
    }

    public ArrayList getPatientFirstNameList(){
        ArrayList patientFirstNameList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT firstname FROM account WHERE type = 'p'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                patientFirstNameList.add(resultSet.getString("firstname"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return patientFirstNameList;
    }

    public ArrayList getPatientLastNameList(){
        ArrayList patientLastNameList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT familyname FROM account WHERE type = 'p'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                patientLastNameList.add(resultSet.getString("familyname"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return patientLastNameList;
    }

    public ArrayList getPatientGenderList(){
        ArrayList patientGenderList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT gender FROM account WHERE type = 'p'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                patientGenderList.add(resultSet.getString("gender"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return patientGenderList;
    }

    public ArrayList getPatientDateOfBirthList(){
        ArrayList patientDateOfBirthList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT dateofbirth FROM account WHERE type = 'p'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                patientDateOfBirthList.add(resultSet.getString("dateofbirth"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return patientDateOfBirthList;
    }

    public ArrayList getPatientNoteList(){
        ArrayList patientNoteList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT note FROM account WHERE type = 'p'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                patientNoteList.add(resultSet.getString("note"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return patientNoteList;
    }

    public String getIdByUserName(String user){
        String id = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id FROM account WHERE user LIKE ?");
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getString("ID");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return id;
    }

    public String getUserType(String id){
        String type = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT type FROM account WHERE ID LIKE ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                type = resultSet.getString("type");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return type;
    }

//    public String queryRealTimebpm(String id){
//        String queryResult;
//
//        return queryResult;
//    }
}
