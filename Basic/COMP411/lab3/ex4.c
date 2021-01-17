/* Example: matrices represented by 2-dimensional arrays */

#include <stdio.h>

int main()
{
  int A[3][3];    // matrix A
  int B[3][3];    // matrix B
  int C[3][3];    // matrix to store their sum

  // add your code below
  printf("Please enter 9 values for matrix A:\n");
  for( int j = 0; j < 3; j++){
    for( int k = 0; k < 3; k++){
      scanf("%d", &A[j][k]);
    }
  }

  printf("Please enter 9 values for matrix B:\n");
  for( int l = 0; l < 3; l++){
    for( int m = 0; m < 3; m++){
      scanf("%d", &B[l][m]);
    }
  }

  printf("C = B + A =\n");
  for( int i = 0; i < 3; i++){
    for( int j = 0; j < 3; j++){
      C[i][j] = A[i][j] + B[i][j];
      printf("%10d", C[i][j]);
    }
    printf("\n");
  }

}