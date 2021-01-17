#include <stdio.h>
#include <string.h>

char animals[20][15];
char lyrics[20][60];
int  number;

void nurseryrhyme(int current) {
                                                // print "current" number of spaces

                                                // print something before the recursive call
                                                // you need to check if the current level is 0
                                                //   if so, print "There was an old lady..."
                                                //   or if it is > 0, print "She swallowed ..."

  if(current < number-1)                        // if we are not at the last animal, make the recursive call
    nurseryrhyme(current+1);
                                                // print something after the recursive call
}


int main() {
  int i=0;

//  while (1) {
                                                // read the next animal name
//    if (strcmp(animals[i], "END\n") == 0)       // if it is "END\n", we are done reading
//      break;
                                                // determine the length of the string read
                                                // strip the newline at the end
                                                // read the lyric corresponding to the animal
//    i++;
//  }

  number = i;
  nurseryrhyme(0);
  printf("There was an old lady who swallowed a bird;\n She swallowed the bird to catch the fly;");
  printf("\n I don't know why she swallowed a fly - Perhaps she'll die!\nI don't know why she swallowed a bir!\n");
}
