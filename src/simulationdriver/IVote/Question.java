
package simulationdriver.IVote;

public abstract class Question {
 
    private String question;
    private String answer; // Will always represent the answer as a string. In case of file share, this would be the file directory.
    
    // default constructor for child use.
    protected Question(){}
    
    // Constructor: creates question object with filled attributes
    public Question(String passedQuestion, String solution){
        /* WARNING */ // No error checking for valid question given. 
        
        question = passedQuestion;
        answer = solution;
    }
    
    // ABSTRACT - Must have children override these methods. 
    public abstract String getFormattedSolution(); // Formats the correct solution based on the type of the solution   
    public abstract String getFormattedQuestion(); // Formats the question a particular way based on the type of question
    public abstract boolean compareAnswers(String ans); // How to know if the answer given is correct. 
    
    
    /*******************************************************************
     * Getter Methods for private attributes:
     *  getSolution() | returns the solution to the question
     *  getQuestionAsked() | returns the string question
     *******************************************************************/
    
    public String getSolution(){
        return answer;
    }
    
    public String getQuestionAsked(){
        return question;
    }
}


