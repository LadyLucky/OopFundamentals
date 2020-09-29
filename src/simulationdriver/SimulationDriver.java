
package simulationdriver;
import simulationdriver.IVote.VotingService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SimulationDriver {

    public static void main(String[] args) throws FileNotFoundException {
        VotingService vs = new VotingService();
        
        System.out.println("Configuring Student participants and Bank of Questions!");
        configureSession(vs);
        
        int studentIDs[] = vs.startNewSession(); // returns a list of students' id's
        
        // Output student participants:
        vs.outputStudentList();
        
        for(int i = 1; i <= vs.getNumberOfQuestions(); i++){
            System.out.println("Question #" + i);
            simulateQuestion(vs,i,studentIDs);
        }
        
        // Complete Session:
        vs.outputOverallResults();
        
    }
    
    public static void configureSession(VotingService vs) throws FileNotFoundException{
        // Initialize all the questions from a file:
        inputQuestions(vs);
        
        // Initializes all the student participants from a file:
        inputStudents(vs);

    }
    
    // Will input all the questions in to the voting service after gathering it from 
    // a File. 
    public static void inputQuestions(VotingService vs) throws FileNotFoundException{
        File questions = new File("etc/questions.txt"); // List of questions with their answer options
        File answers = new File("etc/correct_answers.txt"); // List of answers
        Scanner in = new Scanner(questions);
        Scanner in2 = new Scanner(answers);
        
        // /* WARNING */ Files MUST be same size. 
        // Unhandled error case. 
        while(in.hasNextLine() && in2.hasNextLine()){
            String question[] = in.nextLine().split("/");
            String answerBank[] = Arrays.copyOfRange(question, 1, question.length);
            String solution = in2.nextLine();
            
            vs.addToQuestionBank(question[0], answerBank, solution);
        }
    }
    
    // Will input all the students who will participate in the voting service after 
    // gathering their names from a file. 
    public static void inputStudents(VotingService vs) throws FileNotFoundException{
        File students = new File("etc/students.txt"); // List of participating students
        Scanner in = new Scanner(students);
        
        while(in.hasNextLine()){
            if(vs.addStudent(in.nextLine()) < 0){
                System.out.println("Error: Problem adding student. ");
            }
        }
    }
    
    // WILL ONLY WORK WITH CURRENT FILE CONFIGURATION OF QUESTIONS/CORRECT_ANSWERS.
    public static void simulateQuestion(VotingService vs, int questNum, int []studentIDs){
        vs.outputQuestionAndOptions((questNum-1));
        Random rand = new Random();
        int willAnswer;
        switch(questNum){
            case 1:
            case 2: // SINGLE ANSWER
                String bank[] = {"A", "B", "C", "D", "E"};
                willAnswer = rand.nextInt(20) + 5;
                for (int i = 0; i < willAnswer; i++){
                    vs.giveAnswer(bank[rand.nextInt(5)], studentIDs[rand.nextInt(10)], questNum);
                }
                System.out.println("");
                vs.outputResultsQuestion(questNum-1);
                break;
            case 3: // TRUE FALSE
                willAnswer = rand.nextInt(20) + 5;
                String tof;
                for (int i = 0; i < willAnswer; i++){
                    tof = (rand.nextBoolean()?"A":"B");
                    vs.giveAnswer(tof, studentIDs[rand.nextInt(10)], questNum);
                }
                System.out.println("");
                vs.outputResultsQuestion(questNum-1);
                break;
            case 4:
            case 5: // MULTI ANSWER
                willAnswer = rand.nextInt(20) + 5;
                String q45Answer = "";
                for (int i = 0; i < willAnswer; i++){
                    q45Answer = q45Answer + (rand.nextBoolean()?"A,":"");
                    q45Answer = q45Answer + (rand.nextBoolean()?"B,":"");
                    q45Answer = q45Answer + (rand.nextBoolean()?"C,":"");
                    q45Answer = q45Answer + (rand.nextBoolean()?"D,":"");
                    q45Answer = q45Answer + (rand.nextBoolean()?"E,":"");
                    
                    vs.giveAnswer(q45Answer, studentIDs[rand.nextInt(10)], questNum);
                    
                    q45Answer = "";
                }
                System.out.println("");
                vs.outputResultsQuestion(questNum-1);
                break;
            default: // Should not reach here.
        }
    }

    
}
