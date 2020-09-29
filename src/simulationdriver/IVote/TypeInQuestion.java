
package simulationdriver.IVote;

public class TypeInQuestion extends Question{
    
    // Prevent a default constructor from being called. To ensure 
    // no blank/empty attributes
    private TypeInQuestion(){}
    
    public TypeInQuestion(String passedQuestion, String solution){
        super(passedQuestion, solution);
    }
    
    
    @Override // Overrides' parent abstract class' abstract method
    public String getFormattedSolution() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override // Overrides' parent abstract class' abstract method
    public String getFormattedQuestion() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override // Overrides' parent abstract class' abstract method
    public boolean compareAnswers(String ans) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
