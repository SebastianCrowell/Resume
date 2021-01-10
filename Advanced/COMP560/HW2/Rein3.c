#include <stdio.h> 
#include <stdbool.h> 
#include <time.h>
#include <stdlib.h>
#include <string.h>

int n = 0;
int p = 0;
int g = 0;
int tot = 0;
char startState[8];
char newString[8];
char line[165][32];
char newLine[32];
// bool array idea
int array[165][1];

int placer = 0;
int totalChoices = 0;
int backslash = 0;

float gameComplete = 0.0;
float gameValue = 0.0;
int stringer = 0;
int exporer = 1;
int disc = 1;

char* extract(char* innerState, int spot){
	//printf("The spot is %d\n", spot);
	int placeholder = spot + 1;
	int holds = 0;
	//printf("This is the innerstate %s\n", innerState);
	while(innerState[placeholder] != '.'){
		holds++;
		placeholder++;
	}
	while(innerState[placeholder] != '\0'){
		//printf("%c\n", innerState[placeholder]);
		newString[holds] = innerState[placeholder];
		holds++;
		placeholder++;
	}
	//printf("The returnable string %s\n", newString);
	return newString;
}

char* startGolf(char* state){
	// Check stroke type then jump to next function for calculations
	// This is a trickle down effect
	// Need to set a variable to select a random state
	// Set a startState, goalState, and show the path to it?
	// Give a probability of reaching the next state

	//set a while loop after this to control numeric values approaching at some number, 1
	//change 4 to tot
	placer = 0;

	for(int y = 0; y < tot; y++){
    	// Print for debugging
		//printf("%s", &line[y][n]);

		stringer = 0;
		int counterMax = 0;
		memset(newLine, 0, sizeof(newLine));
		// check first four letters? then next four, then last four, NOPE: Needs recurring
		// I already have the relations fed in, separated by /

		//printf("Left the loop\n");
		//printf("OG string is %s", line[y]);
		//printf("String is %s\n", startState);

		// Find all possible next states from, might need .contains
		//newLine[y], need to find the difference from state fairway to state needed

		while(line[y][stringer] != '/'){
			newLine[stringer] = line[y][stringer];
			stringer++;
		}
		//printf("First state %s\n", newLine);
		stringer = stringer + 1;
		//printf("Is stringer resetting %d\n", stringer);

		if (strstr(newLine, startState) != NULL) {
			// @@@
			if(g == 0){
				placer = y;	
				g = 1;
				//	printf("Min is %d", placer);
			}
			
			// bool setting for the array that shows an inputted value at the location y
			//array[y][0] = 1;
			//printf("It contains the sauce\n");
			totalChoices = totalChoices + 1	;
		}
		//printf("Start state")
		//printf("Placer %d\n", placer);
		//printf("Total Choices: %d\n", totalChoices);
		//printf("%s contains %s\n", newLine, startState);

	}
		//printf("Is stringer resetting %d\n", stringer);
		int u = 0;
		// Make a selection based on the number of states
		int max = totalChoices + placer;
		// Min relates some to the y value, as y shows the lines that have a next
    	int min = placer;
    	int range = max - min;
    	srand(time(0));
		// This is where we select the next portion of the line from available nexts
		n = rand() % range + min; 

		//Select the second state
		while(line[n][stringer] != '/'){
			newLine[stringer] = line[n][stringer];
			stringer++;
		}
		//printf("Second state %s\n", newLine);
		//printf("Is stringer resetting %d\n", stringer);

		totalChoices = 0;
		for(int j = placer; j < max; j++){
			if (strstr(line[j], newLine) != NULL) {
				totalChoices++;
			}
		}

		max = totalChoices + placer;
		range = max - min;
		srand(time(0));

		stringer++;
		// Select the next part after the second state
		gameComplete = u/20;
		u = rand() % range + min;

		//Select the third state
		while(line[u][stringer] != '/'){
			newLine[stringer] = line[n][stringer];
			stringer++;
		}
		state = newLine;
		//printf("Stringer value before the extract %d\n", stringer);
		//printf("State before the number? %s\n", state);
		p = stringer;

		// make this the number state selector so that it can easily be passed back in like the input
		if(totalChoices > 0){
			//printf("%d\n", p);
			//printf("Selected random string %s", line[u]);
			//printf("This is the char we are at for the exract %c\n", line[u][p]);
			// need to look at array entries for 1's as they are the correct values for my random choice			
			gameValue = atof(extract(line[u], p));
			//printf("%lf\n", gameValue);
		}	

		if(gameComplete > 0){
			gameComplete = gameComplete + gameValue;
		} else if(gameComplete > 1) {
			printf("Golfing complete!/n");
			return "Done";
		} else {
			printf("Game failed! Unable to reach the pin after %d attempts with value %lf\n", stringer, gameComplete);
			return "Done";
		}

	g = 0;
	p = 0;
	u = 0;
	n = 0;
	totalChoices = 0;
	return startGolf(state);
}


// driver program to test above function 
int main() {

	FILE *fp;
	char fname[100]="";
	printf("Enter file name: \n");
	scanf("%s", fname);
	fp = fopen(fname, "r");
	if(fp == NULL){
		perror("Unable to open file!");
		return 0;
	}

	int q = 0;
	while(fgets(line[q], 100, fp)){
		//printf("%s", line[q]);
		q++;
	}
	tot = q;
	//printf("%d\n", tot);

	fclose(fp);

	printf("Please confirm Fairway as start state by typing it now: \n");
	scanf("%s", startState);
	startGolf(startState);

	int c;
 	printf( "Press ENTER to exit... " );
  	fflush( stdout );
  	do c = getchar(); while ((c != '\n') && (c != EOF));
  	getchar();

	return 0; 
}

