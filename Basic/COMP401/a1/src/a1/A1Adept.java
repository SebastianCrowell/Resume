package a1;
import java.util.Scanner;

public class A1Adept {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
	
		//Begin with # assignments
		int num_of_assignments = scanner.nextInt();
		
		//Points for each assignment to categorize them
		double[] points_assignment = new double[num_of_assignments];
		
		for(int q=0; q<num_of_assignments; q++) {
			points_assignment [q] = scanner.nextInt();
		}
		
		//Next is participation points
		//Not over 100 or under 0
		int partcipation_points = scanner.nextInt();
		
		//Number of students follows
		//Input integer greater than zero (Students in class)
		int num_of_students = scanner.nextInt();
		
		//Storage for names
		String[] first_name = new String[num_of_students];
		String[] last_name = new String[num_of_students];
		
		//Number storage
		double[] partcipation_grade = new double[num_of_students];
		double[][] all_assignments = new double[num_of_assignments][num_of_students];
		double[] assignment_portion = new double[num_of_students];
		double[] midterm = new double[num_of_students];
		double[] final_exam = new double[num_of_students];
		double[] total_grade = new double[num_of_students];
		int[] total_assignment_points = new int[1];
		
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
		partcipation_grade [i] = scanner.nextDouble();

		for(int v=0; v<num_of_assignments; v++) {
			all_assignments[v][i] = scanner.nextDouble();
		}
		
		midterm [i] = scanner.nextDouble();
		final_exam [i] = scanner.nextDouble();
		}
		
		//add assignment points, divide by assignment number, multiply by 100, add rest multuplied by thier percents
		for(int w=0; w<num_of_students; w++){
		if( partcipation_grade [w] > 100) {
			partcipation_grade [w] = 100;
			}
			else if( partcipation_grade [w] < 0) {
				partcipation_grade [w] = 0;
			}		
		}
		//Double array to one array
		for(int q=0; q<num_of_assignments; q++) {
		for(int b=0; b<num_of_students; b++) {
			assignment_portion[b] += all_assignments[q][b]; 
			}
		}
		//Assignment points changing
		
		for(int g=0; g<num_of_assignments; g++) {
			total_assignment_points[0] += points_assignment [g];
		}
		
		for(int a=0; a<num_of_students; a++) {
		if( 100 > partcipation_points) {
			partcipation_grade [a] /= partcipation_points;
			partcipation_grade [a] *= 100;
			}
		}	
		
		//Calculation
		for(int v=0; v<num_of_students; v++) {
		total_grade[v] = (((assignment_portion[v] * j) / total_assignment_points[0]) * 100 + partcipation_grade [v] * k + midterm[v] * l + final_exam[v] * p);
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

