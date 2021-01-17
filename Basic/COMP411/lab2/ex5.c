/*  Example: C program to find area of a circle */

#include <stdio.h>

int main()
        {
        int input = 1;
        int makeTriangular;
        while(input != 0){
        printf("Number ?\n");

        scanf("%d", &input);
        int i, j = 1, k = 1;

        for (i = 1; i <= input; i++) {
        j = j + 1;
        k = k + j;

        	if( input == k ){
        	printf("%d is a triangular number\n", input);
        	} else if( input != k) {
        	printf("%d is not triangular, nearest triangular number below %d is %d\n", input , input, (input * (input + 1)/2));
        	}
        }
	}
        printf("Done\n");
	}