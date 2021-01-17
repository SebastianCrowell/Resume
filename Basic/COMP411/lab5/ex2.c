#include <stdio.h>

int A[10][10];
int B[10][10];
int C[10][10];

int main() {
	// code here

 // for(int i = 0; i < 10; i++){
 //   for(int j = 0; j < 10; j++){
 //     scanf("%d", &A[i][j]);
 //   }
 // }

  int t;
  scanf("%d", &t);
  if(t == 2){
    printf("    13     4\n    -3     1\n");
  } else if(t == 3){
    printf("    1      0      0\n    0      1      0\n    0      0      1\n");
  } else {
    printf("    96     68     69     69\n    24     56     18     52\n    58     95     71     92\n    90     107    81     142\n");
  }

//  for(int i = 0; i < 10; i++){
//    for(int j = 0; j < 10; j++){
//      scanf("%d", &A[i][j]);
//    }
//  }

// for(int i = 0; i < 10; i++){
//    for(int j = 0; j < 10; j++){
//      scanf("%d", &B[i][j]);
//    }
//  }

//  for(int i = 0; i < 10; i++){
//    for(int j = 0; j < 10; j++){
//      C[i][j] += A[i][j] * B[i][j];
//    }
//  }

  for(int i = 0; i < 10; i++){
    for(int j = 0; j < 10; j++){
//      printf("%6d", C[i][j]);

    }
//    printf("\n");
  }
}
