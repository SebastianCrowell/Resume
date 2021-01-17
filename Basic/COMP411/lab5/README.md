# More Recursion in C

Due 11:59 on Sunday, March 10, 2019

--------------------------------

## Exercise 1: Solving a maze in C

This exercise involves solving a maze, i.e., finding a path from start to finish without going through walls. This lab is an exercise in recursion as well as 2-D matrices whose maximum size is known.

You will implement a simple recursive method for solving a maze, a java version of which is available [here](http://www.cs.unc.edu/~montek/teaching/Comp411-Fall18/Lab5/maze.java). We will modify/adapt it for a C implementation. Stater code is available in the file **ex1.c**. Instead of calling the method **generateMaze()**, the C program reads in the maze from the standard input. Our **main()** function reads in the maze, initializes the three matrices **maze**, **wasHere** and **correctPath**, then calls **recursiveSolve()**, and finally prints the maze with the path identified.

Assume the width and height of the maze are provided at run time, but the size of the maze will not exceed 100x100, so storage for the 2-D maze is provided by a fixed-size 100x100 array. At runtime, however, only part of the array is actually used, for a total of height * width entries.

The maze itself will be read from standard input. First its width and height will be present in the input (in that order), followed by the maze, one row at a time. A '**\***' character represents a wall, and the space character ' ' represents open space through which one can move (i.e., a corridor). One point in the maze will have an '**S**' to indicate "**start**", and one point will have an '**F**' to indicate "**finish**".

Your task is to use the recursive algorithm to find the path, **indicate the path on the maze using the '.' character**, and print the maze with the path on the standard output.

For example, for the following 10-column X 5-row maze as the input:

<pre>
10
5
****S  ***
*   ** ***
***    **F
*** ***** 
***
</pre>

The expected output is:

<pre>
****S..***
*   **.***
***....**F
***.*****.
***.......
</pre>

You can assume that the maze is solvable, i.e., a path exists from start to finish. Also, assume that if multiple paths exist, you only need to display the one path found by the algorithm implemented by java program linked to above (it stops after finding one path).

Use the template provided (ex1.c). Complete the code, compile and run on inputs of your choice, and also make sure it runs correctly on the sample inputs provided.

Here are some explanations and useful tips:

* Typically, a 2-D matrix is thought of as having the row as its first index, and column as the second one: **matrix\[row\]\[col\].** This is called row-major order, i.e., the information is stored one entire row at a time, and within each row, successive columns appear adjacent in memory. Since the x-coordinate corresponds to columns, and the y-coordinate corresponds to rows, the correct way to access the elements of the maze is **maze\[y\]\[x\]**.

* There is no built-in Boolean type in C. There is Boolean emulation available if you include <stdbool.h>. It basically defines the "bool" type (ultimately as an unsigned integer), and "true" as 1 and "false" as 0. Or, as the template does, you could skip <stdbool.h>, and simply declare Boolean variables as "int", and use 1 for "true" and 0 for false. Everything else works the same as for Booleans.

* You can read the width and height from the first two lines of the input using **scanf("%d%d", ...)**. However, note that **scanf()** will leave the newline after second number in the input stream. So, a subsequent call to any C input function to read the actual maze will read this newline character first. You can either be sure to ignore this newline character, or use the **scanf()** call to explicitly eat the newline after reading the height, as done in the template.

* There are multiple ways for reading in the maze.
    - Perhaps the easiest way is to read an entire row at a time using **fgets(temp, width+2, stdin)**. The **+2** is needed to read the newline at the end of each row, plus one space for the string terminator; while these last two values will not be entered into the maze, they still need to be read and then discarded. Accordingly, **temp** itself must have been declared to be of size 102 characters. After reading an entire row, you can iterate through it column-by-column (by varying x), and assigning the values into the corresponding maze row.

    - Alternatively, you can use **getchar()** to read in one character at a time. Just remember that after the last maze character on a row, there will be a newline read, which should not be stored in the maze, but instead could be used as an indicator that it is time to move to the next row. Thus, for each row, a total of **width+1** characters will be read, one at a time, by **getchar()**. Also note that since we are reading characters, not a string, there will not be any null character read (because no null character is actually present in the input, it is something **fgets** adds to terminate a string).

    - Finally, instead of **getchar()**, you can also use **scanf("%c", ...)** in a similar manner. Remember again that you will have to explicitly read in the newline at the end of each row and discard it. Also, note that if you use **scanf("%c", &maze[x][y])**, you will receive a warning because each element of the maze is actually an integer, and it is being given a char value. It is preferable to instead use:

    ```C
    char tempchar;
    scanf("%c", &tempchar);
    maze[y][x]=tempchar;
    ```

The template provided uses the last of the above approaches, i.e. **scanf("%c", ...);**

----------------------------------------------------------------------------

## Exercise 2: Matrix Multiplication

Write a program to multiple two MxM matrices. If the two input matrices are A[M][M] and B[M][M], and the result of multiplication is C[M][M], the elements of C are given by:

![matrix multiplication in sigma notation](http://www.cs.unc.edu/~montek/teaching/Comp411-Fall18/Lab5/matrixmult.svg)

(Optional) For more discussion on matrix multiplication, you may refer to these articles on [Wikipedia](https://en.wikipedia.org/wiki/Matrix_multiplication) or [Math is fun](https://www.mathsisfun.com/algebra/matrix-multiplying.html).

The first line of the input will be the value of **M**, which will be within the range 1 to 10. This is followed by M*M integers, one per line, for the values of the matrix A. Then another M*M integers for the values of matrix B. These values are in row-major order, i.e., first the M values of the elements of row 0, then M more values for row 1, and so on. All numbers are in base ten.

The output will be the matrix C, with each element printed within a width of 6 characters, i.e., printed using **printf("%6d",...);**

Use the template provided in the file ex2.c. Compile and run your program on inputs of your choice, and also make sure it runs correctly on the sample inputs provided.

--------------------------------------------------------------------------------

## Exercise 3: There was an old lady...

In this exercise, you are to write a recursive implementation of a function **nurseryrhyme()** that takes a sequence of words and spins them into a story, as follows.

If the input to the function **nurseryrhyme()** is this sequence of strings, one per line:

<pre>
cat
Imagine that! She swallowed a cat!
bird
How absurd to swallow a bird!
spider
That wriggled and jiggled and tickled inside her!
fly
Perhaps she'll die!
END
</pre>

Then it prints the following output:


<pre>
There was an old lady who swallowed a cat;
 She swallowed the cat to catch the bird;
  She swallowed the bird to catch the spider;
   She swallowed the spider to catch the fly;
   I don't know why she swallowed a fly - Perhaps she'll die!
  I don't know why she swallowed a spider - That wriggled and jiggled and tickled inside her!
 I don't know why she swallowed a bird - How absurd to swallow a bird!
I don't know why she swallowed a cat - Imagine that! She swallowed a cat!
</pre>

Here are some notes and tips:

* As you read the name of each animal, using **fgets()**, it will come with a newline at the end, e.g., **"cat\n"**, etc. It will be highly convenient to strip the newline as soon as the animal's name has been read. To do so, simply determine the length of the name using strlen(), and then replace the newline with a null character.

* The indentation level is different for each line. Each extra indent is exactly one space character. Tip: You can print n spaces without using a loop by simply using a '*' for the width parameter in the format string for **printf()** as follows:
    ```C
    printf("%*s", n, "");
    ```
    What this does is it tells **printf()** to print the empty string "" using a width given by the integer before it, that is **n**.

* The last line of the input is "END". You can use the **strcmp()** function to determine when the end has been reached by comparing the string read with **"END\n"** (note the newline!).

The number of strings that **nurseryrhyme()** will be called with will be determined at runtime, not to exceed **20**. You will write a **main()** procedure that will read these strings from the input, put them into an array of strings (up to 20 strings in the array, each string no longer than **15** characters _including NULL_), and pass this array to **nurseryrhyme()**. Use the code template provided in **ex3.c.**

Your implementation of **nurseryrhyme()** _must be recursive_. A non-recursive implementation of the function will receive **zero credit**. Moreover, since this is an exercise in generating text, any mismatches, including white space errors, will be penalized. Name the file with your C code **ex3.c**. Test it using your own inputs, and also the sample inputs provided.

---------------------------------------------------------------------------------------------------

## Exercise 4: Binary Pattern Generation

In this exercise, you will write a recursive function that, given a number **N**, generates all binary patterns of length **N**. For example, for **N=3**, it will output:

<pre>
000
001
010
011
100
101
110
111
</pre>

Likewise, if **N** were 4, the output will be 16 lines long, corresponding to the binary strings of length 4, in increasing order, starting with **0000** and ending with **1111**.

This exercise is best done using recursion. In particular, to generate all binary patterns of length **N**, do the following. First, set the leftmost position to '0', and generate all binary patterns over the remaining **N-1** positions. Next, set the leftmost position to '1', and again generate all binary patterns over the remaining **N-1** positions.

Assume **N** will not be more than 10. Name your file **ex4.c**, and test using your own inputs as well as the input/output sample files.

-------------------------------------------------------------------------

## Exercise 5: More Patterns

Modify your program from Exercise 4 so that it only generates patterns that **do not** contain two (or more) consecutive '1's. Thus, for **N=3**, the output will now be:

<pre>
000
001
010
100
101
</pre>

This problem can be approached two ways. One way is to prevent a recursive call for a '1' in a certain position if its prior position (to the left) also had a '1'. This is the prefered approach. A second approach is to generate all patterns, but skip printing a pattern if it contains consecutive '1's.

Name your file **ex5.c**, and test using your own inputs as well as the input/output sample files.

------------------------------------------------------------------------------------------------------

Submission Procedure
---------------------------------


First, make sure that your github repository is up to date with the files on your VM. Enter the following sequence of commands to push your changes to **GitHub**.

```
git add *
git commit -m "replace this with a useful message"
git push origin master
```

Next, navigate to the assignment on gradescope and click the "upload submission" button. Select **GitHub** as your submission method and log in to your account. Find the assignment you want to submit under the "repository" dropdown, and select **master** under the "branch" dropdown.

Your score should appear under this assignment submission after the autograder is done. The submission policy is subject to change, but ideally you should only need to submit once, after throughly testing your code.

### Additional Note Regarding Git:
**Git** is a much more piece of software than indicated by this procedure. It is possible that you may have difficulty with it while trying to submit, and familiarity with its use will be a required skill for your future academic and professional careers.

See [this tutorial](http://rogerdudler.github.io/git-guide/) (or any others you may find) for a crash course.
* * *