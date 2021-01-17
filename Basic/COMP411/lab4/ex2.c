/* Example: bubble sort strings in array */

#include <stdio.h>  /* Need for standard I/O functions */
#include <string.h> /* Need for strlen() */

#define NUM 25   /* number of strings */
#define LEN 1000  /* max length of each string */

int main()
{
  char Strings[NUM][LEN];
  char temp[NUM];

  printf("Please enter %d strings, one per line:\n", NUM);


  for(int i = 0; i < NUM; i++){
    fgets(Strings[i], LEN, stdin);
  }

  puts("\nHere are the strings in the order you entered:");

  for(int j = 0; j < NUM; j++){
    printf("%s", Strings[j]);
  }

  /* Output sorted list */
  puts("\nIn alphabetical order, the strings are:");

  for (int i = 0; i < NUM; i++) {
    for (int j = i + 1; j < NUM; j++) {
      if (strcmp(Strings[i], Strings[j]) > 0) {
        strcpy(temp, Strings[i]);
        strcpy(Strings[i], Strings[j]);
        strcpy(Strings[j], temp);
      }
    }
  }

  for (int i = 0; i < NUM; i++){
    printf("%s", Strings[i]);
  }
  /* Write a for loop here to print all the strings. Feel free to use puts/printf
     etc. for printing each string.
  */

}