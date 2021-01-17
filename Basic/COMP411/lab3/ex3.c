#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main(){
    char text[1000];
    char reverse[1000];
    int i, p, k, j, length;
    int flag = 0;

    printf("Type some text (then ENTER):\n");

    fgets(text, 1000, stdin);
    length = strlen(text) - 1;

    for( j = 0; j < length; j++){
      reverse[j] = text[j];
    }

    printf("Your input in reverse is:\n");
    int end = length - 1;
    for( k = 0; k < length; k++){
      p = 0;
      p = reverse[p];
      reverse[p] = reverse[end];
      reverse[end] = p;
      end--;
      if(reverse[p] == '\x00'){
      } else {
      printf("%c", reverse[p]);
      }
    }

// Piece to make all letters lower case

    int l = 0;
    char c;
    int perchar = length;

    for(i = 0;i < length ;i++){
        if(text[i] != text[length - i - 1]){
            while(perchar != 0){
                c = text[l];
                if(isupper(c)) c = tolower(c);
            //    if(ispunct(c)) c = '\0';
            //    if(isspace(c)) c = '\0';
            //    putchar (c);
                l++;
                perchar--;
	    }
            flag = 1;
	    break;
	}
    }

    if (flag) {
        printf("\n");
    } else {
        printf("\nFound a palindrome!\n");
    }
}