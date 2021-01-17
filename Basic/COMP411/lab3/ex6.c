#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main () {

  int length;
  int collatz;
  int x = 0;
  printf("Please enter the starting number of the Collatz sequence:\n");
  scanf("%d", &collatz);
  while(collatz != 1){
      if( x == 0){
	if(collatz % 2){
          printf("%d,", collatz);
	  collatz = 3 * collatz + 1;
	  x++;
	} else {
	  printf("%d,", collatz);
	  collatz = collatz / 2;
	  x++;
	}
      }
       if(collatz % 2){
//	if( x == 0){
//	  printf("%d,", collatz);
//	  collatz = 3 * collatz + 1;
//	  x++;
//	}
        printf(" %d,", collatz);
        collatz = 3 * collatz + 1;
      } else {
//        if( y == 0){
//          printf("%d,", collatz);
//	  collatz = collatz / 2;
//	  y++;
//        }
        printf(" %d,", collatz);
        collatz = collatz / 2;
      }
    }
    printf(" 1\n");
}