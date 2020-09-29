
package simulationdriver.IVote;

import java.util.Random; // Random number generator for generating student ID's

public class Student {
    private String name; // Non Unique Attribute
    private int id; // Unique Attribute, will not be shared with other instances of this class. 
    private int score; // How many answers the student has answered correctly in the session. 
    
    // Prevent a default constructor from being called. To ensure 
    // unique student ID's
    private Student(){}
    
    // Constructor: Gives a randomized ID to a student as well as initialize them. 
    public Student(String studentName, int studentDigits){
        if(studentDigits > 99){
            System.out.println("Error parsing string, Integer is too large");
            throw new RuntimeException(); // Thrown. Can't create the instance of the student correctly.
        }else{
            name = studentName;
            Random rand = new Random();
            int randInt = rand.nextInt(10000);
            id = Integer.parseInt(Integer.toString(randInt) + Integer.toString(++studentDigits));
            score = 0;
        }
        
    }
    
    /****************************************
     * Getter Methods:
     *  getStudentName() | returns student's name
     *  getIntID() | returns the id as an int
     *  getScore() | returns the score for the student
     ***************************************/
    
    public String getStudentName(){
        return name;
    }
    
    public int getIntID(){
        return id;
    }
    
    public int getScore(){
        return score;
    }
    
    
    /**************************************
     * Action Methods:
     *  renewStudent() | reInitializes the student for a new game
     *  updateScore() | increments the score when given a correct answer
     *************************************/
    
    public void renewStudent(){
        score = 0;
    }
    
    public void updateScore(){
        score++;
    }
    
}
