/* Example: bubble sort strings in array */

#include <stdio.h>  /* Need for standard I/O functions */
#include <string.h> /* Need for strlen() */

#define NUM 25   /* number of strings */
#define LEN 1000  /* max length of each string */

int  result = 2;

int my_compare_strings(char string1[], char string2[]) {
     // Length compare
    if(strlen(string1) != strlen(string2)){
        if(strlen(string1) < strlen(string2)){
            return (result = -1);
        } else if(strlen(string1) > strlen(string2)){
            return (result = 1);
        }
    }
    int i = 0;
    // Equal
    for(i = 0; i < strlen (string1); i++ ) {
      // Each letter
      if(string1[i] != string2[i]){
        if(string1[i] < string2[i]){
          return (result = -1);
        } else if(string1[i] > string2[i]){
          return (result = 1);
        }
      }
    }
    return (result = 0);

/* Write code here to compare string1 and string2, character by character.
     WITHOUT USING ANY C STRING LIBRARY FUNCTIONS.

     The max length of the strings is LEN, though they may be shorter so look
     for the string terminators.

     This function should return:
      -1 if string1 comes before string2 in alphabetical order
       0 if string1 is the same as string2
      +1 if string1 comes after string2 in alphabetical order

      Here, alphabetical order is defined simply by the numerical value
      of the characters in the string, from left to right.  See the writeup
      for more details.
  */
}


void my_swap_strings(char string1[], char string2[]) {
  char temp;    // char variable used in swapping one character at a time

  for(int i = 0; i < LEN - 1; i++){
    for(int j = 0; j < LEN - i - 1; j++){
      if(my_compare_strings(string1, string2)){
        temp = string1[j];
        string1[j] = string2[j + 1];
        string2[j + 1] = temp;
      }
    }
  }

  /* Write code here to swap the contents of string1 and string2, one
     character at a time, WITHOUT USING ANY C STRING LIBRARY FUNCTIONS.
     The max length of the strings is LEN, though they may be shorter, so look
     for the string terminators.
  */
}


int main()
{
  char Strings[NUM][LEN];

  printf("Please enter %d strings, one per line:\n", NUM);

  for(int i = 0; i < NUM; i++){
    fgets(Strings[i], LEN, stdin);
  }
  /* Write a for loop here to read NUM strings.
     Use fgets(), with LEN as an argument to ensure that an input line that is too
     long does not exceed the bounds imposed by the string's length.  Note that the
     newline and NULL characters will be included in LEN.
  */

  puts("\nHere are the strings in the order you entered:");

  /* Write a for loop here to print all the strings. */

  for(int j = 0; j < NUM; j++){
    printf("%s", Strings[j]);
  }

  /* Bubble sort */
  for(int k = 0; k < NUM; k++){
    my_compare_strings(Strings[k], Strings[k+1]);
    if(result == 1){
      my_swap_strings(Strings[k], Strings[k+1]);
    } else if (result == -1){
      my_swap_strings(Strings[k+1], Strings[k]);
    }
  }
  /* Write code here to bubble sort the strings in ascending alphabetical order
     Use the function my_compare_strings() to compare two strings.  If they are out of order,
     use the function my_swap_strings() to swap their contents.
  */

  /* Output sorted list */
  puts("\nIn alphabetical order, the strings are:");

  for(int h = 0; h < NUM; h++){
    printf("%s", Strings[h]);
  }

  /* Write a for loop here to print all the strings. Feel free to use puts/printf
     etc. for printing each string.
  */
}