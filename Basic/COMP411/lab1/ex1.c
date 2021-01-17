/*  Example: C program to find area of a circle */

#include <stdio.h>
#define PI 3.14159

int main()
{
  float r, a, c;

  printf("Enter radius (in cm):\n");
  scanf("%f", &r);

  c = r/2.54;
  a = PI * c * c;

  printf("Circle's area is %3.2f (sq in).\n", a);
}