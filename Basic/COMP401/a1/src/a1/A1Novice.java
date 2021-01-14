package a1;
import java.util.Scanner;

public class A1Novice 
{

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
			
		// Input integer greater than zero (Students in class)
		int num_of_students = scanner.nextInt();
		
		//Storage for names
		String[] first_name = new String[num_of_students];
		String[] last_name = new String[num_of_students];
		
		//Number storage
		double[] assignment_grade = new double[num_of_students];
		double[] participation_grade = new double[num_of_students];
		double[] midterm = new double[num_of_students];
		double[] final_exam = new double[num_of_students];
		double[] total_grade = new double[num_of_students];
		
		//40% assignment, 15% participation, 20% for the midterm, 25% final
		double j  = .4; 
		double k = .15; 
		double l = .2; 
		double p = .25;
		
		//Letter storage
		String[] gradeA = new String[num_of_students];
		
		//Variable entering and calculation
		for(int i=0 ; i<num_of_students; i++) 
		{
		first_name [i] = scanner.next();
		last_name [i] = scanner.next();
		assignment_grade [i] = scanner.nextDouble();
		participation_grade [i] = scanner.nextDouble();
		midterm [i] = scanner.nextDouble();
		final_exam [i] = scanner.nextDouble();
		total_grade[i] = (assignment_grade[i] * j + participation_grade[i] * k + midterm[i] * l + final_exam[i] * p);
		}
		
		//Determine letter grades
		for(int r=0 ; r<num_of_students; r++) {
		if( total_grade[r] >= 94) {
				gradeA[r] = "A";
			}
			else if ( total_grade[r] >= 90 ){
				gradeA[r] = "A-";
			}
			else if ( total_grade[r] >= 86){
				gradeA[r] = "B+";
			}
			else if ( total_grade[r] >= 83){
				gradeA[r] = "B";
			}
			else if ( total_grade[r] >= 80){
				gradeA[r] = "B-";
			}
			else if ( total_grade[r] >= 76){
				gradeA[r] = "C+";
			}
			else if ( total_grade[r] >= 73){
				gradeA[r] = "C";
			}
			else if ( total_grade[r] >= 70){
				gradeA[r] = "C-";
			}
			else if ( total_grade[r] >= 65){
				gradeA[r] = "D+";
			}
			else if ( total_grade[r] > 60){
				gradeA[r] = "D";
			}
			else if ( total_grade[r] < 60){
				gradeA[r] = "F";
			}
		}
		
		//Print output char 1 of First_Name string, Last_Name Letter_Grade
		for(int t=0 ; t<num_of_students; t++) {
		char result1 = first_name[t].charAt(0);
		System.out.println(result1 + ". " + last_name[t] + " " + gradeA[t] );		
		}
		
		}
	
}
