#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
​
// Dedicated thread for observer/observable
int pipelines;
char instruction[32];
int sizeOfNanoInstruction;
char comNetwork[4];
int NTHREADS;
​
void *startMachine(void *x){
	int tid;
	tid = *((int *) x);
	printf("Thread %d active\n", tid);
	return NULL;
}
​
int main(int argc, char *argv[]) {
	printf("Enter the number of pipelines to be used (aka # of nano-machines):\n");
	scanf("%d", &pipelines);
​
	printf("Enter a bit instruction divisible by the number of nano-machines:\n");
	scanf("%s", instruction);
​
	NTHREADS = pipelines + 1;
​
	pthread_t threads[NTHREADS];
	int thread_args[NTHREADS];
	int rc, i;
​
	int strlenplaceholder = 0;
	//Check for instruction, update com to influence thread
	if(strlen(instruction) > 0){
		comNetwork[0] = 1;
		strlenplaceholder = strlen(instruction);
	}
​
	//While loop that maintains the observer while instructions are being processed
	while(strlenplaceholder > 0){
		thread_args[0] = 0;
		// Make a change to make this a function, then call the creation
		printf("Spawning observer thread\n");
		rc = pthread_create(&threads[0], NULL, startMachine, (void *) &thread_args[0]);
		rc = pthread_join(threads[i], NULL);
​
​
	printf("String is of length %ld\n", strlen(instruction));
	printf("Number of pipelines is %d\n", pipelines);
​
	//Calculating the size of the instuctions, cutting them down into pieces for each pipeline to process
	sizeOfNanoInstruction = strlen(instruction) / pipelines;
	char newBitInstructions[pipelines];
​
	//Set a new array and copy the shorter instuctions into it
	for(int i = 0; i < sizeOfNanoInstruction; i++){
		newBitInstructions[i] = instruction[i];
	}
​
	//Process the instructions if an instruction was input
	if(comNetwork[0] == 1){
  		for (i = 1; i < NTHREADS; ++i){
      			thread_args[i] = i;
      			printf("Spawning thread %d\n", i);
      			rc = pthread_create(&threads[i], NULL, startMachine, (void *) &thread_args[i]);
		}
​
		for (i = 1; i < NTHREADS; ++i) {
			rc = pthread_join(threads[i], NULL);
		}
​
		for (i = 0; i < NTHREADS; i++){
			if(newBitInstructions[i] == 0){
				//Define some action for nano to complete
			} else {
				//Define the other action
			}
		}
​
		for(int b = 1; b < NTHREADS; b++){
			printf("%d index machine instruction complete, ", b);
			printf("%d\n", newBitInstructions[b]);
		}
		strlenplaceholder = 0;
	}
	} //while end                        
}