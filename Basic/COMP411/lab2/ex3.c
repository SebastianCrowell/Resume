/*  Example: C program to find area of a circle */

#include <stdio.h>

int main()
	{
	int inputOne, convertOctal, convertHex, inputFour, inputFive,inputSix;
	printf("Enter six integers:\n");

	scanf("%i", &inputOne);
	scanf("%o", &convertOctal);
	scanf("%x", &convertHex);
        scanf("%i", &inputFour);
        scanf("%i", &inputFive);
        scanf("%i", &inputSix);


	printf("1234567890bb1234567890 \n");
        printf("%10i", inputOne);
	printf("%10i\n", convertOctal);
	printf("%10i", convertHex);
        printf("%10i\n", inputFour);
        printf("%10i", inputFive);
        printf("%10i\n", inputSix);
	}