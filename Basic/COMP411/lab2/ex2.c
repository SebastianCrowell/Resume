/*  Example: C program to find area of a circle */

#include <stdio.h>

int main()
        {
        double input;
        double sum, min, max;
        double product = 1;
        printf("Enter 10 floating-point numbers:\n");

        for( int i = 0; i < 10; i++){
        scanf("%lf", &input);

        sum = input + sum;
        product = input * product;

        if( input > max ){
        max = input;
        } else if(input < min) {
        min = input;
        }
        }
        printf("Sum is %3.5lf \n", sum);
        printf("Min is %3.5lf \n", min);
        printf("Max is %3.5lf \n", max);
        printf("Product is %3.5lf \n", product);
        }