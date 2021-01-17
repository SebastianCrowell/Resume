#include <stdio.h>
#include <string.h>
#define MAX_BUF 1024
#include <stdlib.h>

int main () {

  char buf[MAX_BUF];
  int length;
  char ogch[MAX_BUF];
  char Newch[MAX_BUF];

  // other stuff

  do {
  char ogch[8] = {'e', 'E', 'i', 'I', 'o', 'O', 's', 'S'};
  char Newch[8] = {'3', '3', '1', '1', '0', '0', '5', '5'};
	// read a line
  fgets(buf, MAX_BUF, stdin);
        // calculate its length
  length = strlen(buf) - 1;
        // modify the line by switching characters
	// e E
  for(int i = 0; i <= strlen(buf); i++){
    if(buf[i] == ogch[0]){
      buf[i] = Newch[0];
    }
  }
  for(int i = 0; i <= strlen(buf); i++){
    if(buf[i] == ogch[1]){
      buf[i] = Newch[1];
    }
  }
	// i I
  for(int i = 0; i <= strlen(buf); i++){
    if(buf[i] == ogch[2]){
      buf[i] = Newch[2];
    }
  }
  for(int i = 0; i <= strlen(buf); i++){
    if(buf[i] == ogch[3]){
      buf[i] = Newch[3];
    }
  }
	// o O
  for(int i = 0; i <= strlen(buf); i++){
    if(buf[i] == ogch[4]){
      buf[i] = Newch[4];
    }
  }
  for(int i = 0; i <= strlen(buf); i++){
    if(buf[i] == ogch[5]){
      buf[i] = Newch[5];
    }
  }
	// s S
  for(int i = 0; i <= strlen(buf); i++){
    if(buf[i] == ogch[6]){
      buf[i] = Newch[6];
    }
  }
  for(int i = 0; i <= strlen(buf); i++){
    if(buf[i] == ogch[7]){
      buf[i] = Newch[7];
    }
  }

        // print the modified line
  printf("%s", buf);
  } while (length >= 1);

}