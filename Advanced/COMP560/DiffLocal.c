#include <stdio.h> 
#include <stdbool.h> 
#include <time.h>
#include <stdlib.h>
#include <string.h>

// Number of vertices in the map 
#define states 200
bool map[states][states];
int counter;
int colorMarker = 0;
int stateMarker = 0;
int edgesMarker = 0;
char line[165][8];


int mapCheck(bool map[states][states], int colorInt[]) {
  for(int i = 0; i < colorMarker; i++){
    for(int j = 0; j < colorMarker; j++){
      if(map[i][j] == true){
        // Check for validity
        if(colorInt[i] == colorInt[j] || colorInt[j] == colorInt[i] ){
          colorInt[i] = 0;
          return i;
        }
      }
    }
  }
  return edgesMarker - stateMarker - colorMarker;
}

void printMap(int colorInt[]); 

// Quick check for edge then color of the state passed in
bool stateCheck(int stateIndex, bool map[states][states], int colorInt[], int colorSelected) { 
	for (int i = 0; i < states; i++) {
    // Checks the color of the states from current to all edges of current
		if (map[stateIndex][i] == 1 && colorSelected == colorInt[i]) {
      // A state nearby has same color
			return false; 
    }
  }
  // Shows that it is safe to apply the color as no violations are detected in the vicinity
	return true; 
} 

// Recursion function called earlier
bool mapColoringRecur(bool map[states][states], int maxColor, int colorInt[], int stateIndex) { 
  // This is ticked up everytime we recur through here to give new colors and the original colors
	counter++;
  colorInt[0] = 1;

	// Check to see if all the states have color
	if (stateIndex == edgesMarker - stateMarker - colorMarker) {
    mapCheck(map, colorInt);
		return true; 
  }

	// Trying different colors from 1 to (and including) inputed number of colors
	for (int c = 1; c <= maxColor; c++) { 
		// Checks to see if the color being given to the state is accepted (no repeats in area)
		if (stateCheck(stateIndex, map, colorInt, c)) { 
		colorInt[stateIndex] = c; 

		// Goes to the next state in the list of states
		if (mapColoringRecur(map, maxColor, colorInt, stateIndex + 1) == true) {
			return true; 
    }

    // Color being removed as the check showed that the next state cannot have the same color
		colorInt[stateIndex] = 0; 
		} 
	} 

	// Either we ran out of colors or some state is unsolvable
	return false; 
} 

// Calls the first instance of the recursion and looks for completetion as recursion happens
bool mapColoring(bool map[states][states], int maxColor) { 
	// Setting all colors to 0 (no color) before any manipulation so C doesn't give randoms
	int colorInt[states]; 

  int max = colorMarker;
  int min = 1;
  int range = max - min;
  srand(time(0));

  // Random number fill
  for (int i = 1; i < stateMarker - colorMarker - 3; i++) {
    colorInt[i] = rand() % range + min; 
  }

	// Check the first state and probably apply color red
	if (mapColoringRecur(map, maxColor, colorInt, 0) == false) { 
	printf("Solution does not exist\n"); 
	return false; 
	} 

	// Print the solution 
	printMap(colorInt); 
	return true; 
} 

// Removes the newline from the input in k from earlier (the last character)
char * fixPrint( char *s) {
  s[strcspn(s, "\n")] = '\0';
  return s;
}

// Printer
void printMap(int colorInt[]) { 
	printf("Solution Exists:"
			" Following are the assigned colors \n"); 

	for(int i = 1; i < stateMarker - colorMarker; i++){ 
    	printf("State [%s] : %s", fixPrint(line[i + colorMarker]), line[colorInt[i - 1] - 1]);
  	}
  // After solution is printed, show steps
	printf("Counter logged %d steps to solution \n", counter);
} 
 
int main() { 
  // Ints to use for loops and whatnot
  int i;
  char * l;
  char * k;
  int q = 0;
	int tot = 0;
  int mileStone = 0;

  // Mandatory logic for reading in the file from a given name (MAKE SURE INPUT IS IN THE SAME FOLDER)
  FILE *fp;
  char fname[100]="";
  printf("Enter file name: \n");
  scanf("%s", fname);
  fp = fopen(fname, "r");
  if(fp == NULL) {
		perror("Unable to open file!");
		return 0;
	}

  /* This logic is reading in all the lines, finding the blank spaces, setting a milestone at the space, and 
  notifiying of what info is before that space*/
	while(fgets(line[q], 100, fp)) {
    if(strcmp(line[q], "\n") == 0){
      if(mileStone == 0){
        colorMarker = q;
      }
      if(mileStone == 1){
        stateMarker = q;
      }
      if(mileStone == 2){
        edgesMarker = q;
      }
      mileStone++;
    }
		q++;
	}
	fclose(fp);

  // Feeding in the edges here and keeping track of what edge we are in
  for(i = stateMarker + 1; i < edgesMarker; i++) {
    int beforeSpace = 0;
    int stateK;
    int stateL;
    char temp[edgesMarker][strlen(line[i])];

    // Setting the array used for getting char's before space to 0 because C is picky about it
    for(int p = 0; p < edgesMarker; p++) {
      for(int c = 0; c < strlen(line[i]); c++) {
        temp[p][c] = 0;
      }
    }

    // This for loop is for going across the inputed edges characters and finding where the space is at
    for(int p = 0; p < strlen(line[i]); p++) {
      if(line[i][p] == ' ') {
        for(int z = 0; z < p; z++) {
          temp[0][z] = line[i][z];
          beforeSpace = z;
        }
        // We then let k be the letters before the space and l be the letters after the space
        temp[0][beforeSpace + 1] = '\n';
        k = &temp[0][0];
        l = &line[i][p + 1];
      }
  }

    // Decided that it would be good to try and check the edge parts vs the states listed
    for(int p = colorMarker + 1; p < stateMarker; p++) {
      if(strcmp(k, line[p]) == 0) {
        stateK = p - colorMarker - 1;
      }
      if(strcmp(l, line[p]) == 0) {
        stateL = p - colorMarker - 1;
      }
    }

    // Checking map connections for <-->
    map[stateK][stateL] = true;
    map[stateL][stateK] = true;

  }

  // Passed in the connection map and number of colors
	mapColoring (map, colorMarker); 

  int c;
  printf( "Press ENTER to exit... " );
  fflush( stdout );
  do c = getchar(); while ((c != '\n') && (c != EOF));
  getchar();
  return 0;
} 


