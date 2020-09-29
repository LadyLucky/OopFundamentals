
package simulationdriver.IVote;

public class MultiQuestion extends Question{
    
    private String answerBank[]; // Represents the 'answers' to select from
    
    // Prevent a default constructor from being called. To ensure 
    // no blank/empty attributes
    private MultiQuestion(){}
    
    public MultiQuestion(String passedQuestion, String []options, String solution){
        super(passedQuestion, solution);
        answerBank =  options; 
    }
    
    @Override // Overrides' parent abstract class' abstract method
    public String getFormattedSolution(){
        for(String a : answerBank){
            if(a.charAt(0) == getSolution().charAt(0)){
                return a;
            }
        }
        
        return "There was an error retreiving the solution...";
    }
    
    @Override // Overrides' parent abstract class' abstract method
    public String getFormattedQuestion(){
        String fq = getQuestionAsked();
        for(String s : answerBank){
            fq = fq + "\n" + s;
        }
        fq  =  fq + "\n";
        return fq;
    }

    @Override
    public boolean compareAnswers(String ans) {
        return (ans.equals(getSolution())); 
    }
    
}
