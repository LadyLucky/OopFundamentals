
package simulationdriver.IVote;

import java.util.ArrayList;

// Can you tell I program in C++ mostly?
class Participant{
    
    // Publicly acessable attributes to represent a quad object
    public Student student; // The student & it's information
    public boolean hasAnswered; // If they have answered the current question
    public int questionNum; // the question index of the question they most recently answered
    public String answerGiven; // answer they gave for the question. 
    
    public Participant(String name, int digits){
        student = new Student(name, digits);
        hasAnswered = false;
        questionNum = -1;
        answerGiven = "";
    }
}

public class VotingService {
    
    private ArrayList<Question> questionBank; // List of questions
    private ArrayList<Participant> participants; // participant (students)
   
    
    /*********************************************
     * Configuration Methods:
     * 
     ********************************************/
    
    // Constructor
    public VotingService(){
        questionBank =  new ArrayList<>();
        participants = new ArrayList<>();
    }
    
    
    // Will add to the questionBank
    public boolean addToQuestionBank(String question, String []options, String correctAnswer){
        if(!question.isEmpty()){
            questionBank.add(new MultiQuestion(question, options, correctAnswer));
            return true; // return if successful add 
        }
        
        return false; // question is an invalid string/unsucessful add
    }
    
    public boolean addToQuestionBank(String question, String correctAnswer){
        if(!question.isEmpty()){
            questionBank.add(new TypeInQuestion(question, correctAnswer));
            return true; // return if successful add 
        }
        
        return false; // question is an invalid string/unsucessful add
    }
    
    // Will instantiate a new student with a unique ID 
    // and add it to the student population list. 
    public int addStudent(String name){
        try{
            int digits;
            if((participants.size()-1) >= 0){
                digits = participants.get(participants.size()-1).student.getIntID();
                digits = (int)(digits - (Math.floor(digits/100)) * 100);
            }else{
                digits = 9;
            } 
            participants.add(new Participant(name, digits));
            
            return participants.get(participants.size()-1).student.getIntID();
        }catch(RuntimeException e){
            System.out.println("Error: incorrect instantiation of object created.");
            return -1;
        }
        
    }
    
    
    // Clears all states of the Students's boolean/answered value
    // Clears all stats taken from past session. 
    public int []startNewSession(){
        int []s = new int[participants.size()];
        int i = 0;
        for(Participant p: participants){
            p.student.renewStudent();
            p.answerGiven = "";
            p.hasAnswered = false;
            s[i] = p.student.getIntID();
            i++;
        }
        return s;
    }
    
    /********************************************
     * After Configuration/ In Progress Methods:
     * 
     *******************************************/
    
    public int getNumberOfQuestions(){
        return questionBank.size();
    }
    
    // Output the number of students participating and their ids alongside their usernames. 
    public void outputStudentList(){
        System.out.println("There are " + participants.size() + " students participating.");
        for(Participant p : participants){
            System.out.println(p.student.getIntID() + ": " + p.student.getStudentName());
        }
        System.out.println("");
    }
    
    // Output the question in a question and select answers format:
    public void outputQuestionAndOptions(int question){
        System.out.println(questionBank.get(question).getFormattedQuestion());
    }
    

    // answer will be in format: "A,B,C,D,E" and "A" for single answers respectively
    // studentID will represent the ID of the student that answered the question. 
    // question represents the index of the question (1st question is index 0 respectively)
    public void giveAnswer(String answer, int studentID, int question){
        for(Participant p: participants){
            if( p.student.getIntID() == studentID){
                if(p.questionNum != question || !p.hasAnswered){
                    p.hasAnswered = true;
                    p.questionNum = question;
                    System.out.println(p.student.getStudentName() + " has chosen their answer!");
                }else{
                    // If they are overwriting their answer:
                    System.out.println(p.student.getStudentName() + " decided to change their answer!");
                }
                
                p.answerGiven = answer;
  
                return;
            }
        }
    }
    
    // Output results after answering a SINGLE question. 
    public void outputResultsQuestion(int question){
        
        // Gathering statistics:
        int answeredCorrectly = 0;
        int answeredIncorrectly = 0;
        int didntAnswer = 0;
        
        System.out.println("Question " + (question+1) + ": " );
        System.out.println("-------------------------------------------------");
        
        for(Participant p: participants){
            System.out.println("Student: " + p.student.getStudentName());
            System.out.println("Answered: " + p.answerGiven);
            
            if(questionBank.get(question).compareAnswers(p.answerGiven)){
                System.out.println("Their answer was Correct!\n");
                p.student.updateScore(); // Updates the amount of correct answers given
                answeredCorrectly++;
            }else if(p.answerGiven.length() < 1){
                didntAnswer++;
                System.out.println("They did not answer the question... Next time!\n");
            }else{
                System.out.println("Their answer was not Correct!\n");
                answeredIncorrectly++;
            }
        }
        System.out.println("-------------------------------------------------");
        System.out.println(" *** STATS: *** ");
        System.out.println("The question was, " + questionBank.get(question).getQuestionAsked());
        System.out.println("The correct answer is: " + questionBank.get(question).getFormattedSolution());
        System.out.println(answeredCorrectly + " students answered correctly, that is " + ((answeredCorrectly * 100)/participants.size()) + "%");
        System.out.println(answeredIncorrectly + " students answered incorrectly, that is " + ((answeredIncorrectly * 100)/participants.size()) + "%");
        System.out.println(didntAnswer + " number of students didnt answer, that is " + ((didntAnswer * 100)/participants.size()) + "%");
        System.out.println("-------------------------------------------------");
    }    
    
    
    /********************************************
     * Session Completion Methods:
     * 
     *******************************************/
    
    // Outputs the statistics of who has the highest score. 
    public void outputOverallResults(){
        int highestScore = 0; // Number of correct questions
        String stuWinners[] = new String[participants.size()]; // names of the students with the highest score
        int i = 0;
        
        System.out.println("Students: ");
        for(Participant p : participants){
            System.out.println(p.student.getStudentName() + ": " + p.student.getScore() + " correct answers!");
            if(p.student.getScore() == highestScore){
                stuWinners[i] = p.student.getStudentName();
                i++;
            }
            if(p.student.getScore() > highestScore){
                stuWinners = new String[participants.size()];
                stuWinners[0] = p.student.getStudentName();
                highestScore = p.student.getScore();     
                i = 1;
            }
            
        }
        
        // Output the winner(s): 
        if(stuWinners.length > 1){
            for(String s : stuWinners){
                if(s != null && !s.isEmpty())
                    System.out.print(s + ", ");
            }
            System.out.print("looks like these are the winners! They got the highest score of " + highestScore + " correct answers!\n");
        }else{
            System.out.println(stuWinners[0] + " is the winner with the highest score of " + highestScore + " correct answers!");
        }
        
    }
    
}


