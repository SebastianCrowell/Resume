/*  Example: C program to find area of a circle */

#include <stdio.h>

int main()
	{
	int number;

	//string declaration
	char* endingST, endingND, endingRD, endingTH;
	printf("Enter a number from 1 to 20:\n");
	//%d for int, at location &num
	scanf("%d", &number);

	if(number >= 1 && number <= 20){
		printf("Here are the first %d ordinal numbers:\n", number);
		for(int i = 1; i <= number; i++){
			printf("%d", i);
			if(i == 1) {
			printf("st\n");
			} else if(i == 2){
			printf("nd\n");
			} else if(i == 3){
			printf("rd\n");
			} else if(i >= 4){
			printf("th\n");
			}
		}
	} else {
	printf("Number is not in the range from 1 to 20\n");
	}
}