# Lab 4: Functions and Strings in C

Due 11:59pm on Sunday, February 24, 2019

----------------------------------------------
In this lab, we will focus on functions and strings in C. In the first two exercises, we will implement bubble sort of character strings. In the third exercise, we will write a recursive function to implement n-choose-k.
### Relevant Readings:
* Perry and Miller: Chapters 6, 19, 21-22 (arrays), 23 (sorting) and 30-32 (functions).
* C string library functions: http://www.cplusplus.com/reference/cstring/.

**Please review Lab 3 for how strings are declared and manipulated in C.**

--------------------------------------------------------------------------

## Exercise 1

For the first version of sorting strings, you will implement the primary data structure as a 2-dimensional array **Strings[NUM][LEN]**, where **NUM** is the number of strings, and **LEN** is the maximum length allowed for the string: the first index **i** gives you the **i**-th string, and the second index **j** gives you the **j**-th character within that string.

We will use the bubble sort algorithm for sorting. Please carefully read Chapter 23 of Perry and Miller, which explains bubble sort and includes examples for sorting numbers. You will adapt that algorithm to sort strings instead, in increasing alphabetical order. Using the starter file **ex1.c.** Your code must strictly follow these specifications:

* Use **fgets()** to read each string from the input, one string per line. Use **LEN** as an argument to **fgets()** to ensure that an input line that is too long does not exceed the bounds imposed by the string's length. The function **fgets()** will make sure that it will not put more than **LEN** characters into the string variable (including the newline and NULL characters). Keep in mind that the newline character will be read into the string.
* The comparison of two strings must be done by checking them one character at a time, without using any C string library functions. That is, write your own loop to do this. Put this code inside a function called **my_compare_strings()**.
* The swap of two strings must be done by swapping one character at a time (using a **char temp** variable of your choice), without using any C string library functions. That is, write your own loop to do this. Put this code inside a function called **my_swap_strings()**.
* You are allowed to use C library functions for printing strings, e.g., fputs(), puts(), printf(), etc.
* You are allowed to use the C library function **strlen()** to calculate the length of a string.

**Alphabetical Order:** The alphabetical order of strings is defined by reading them left-to-right, until you reach a character position in which the two strings are different, or until one or both strings terminate. If a position is reached where the strings are different, then their order is determined by the ordering of those respective characters. For example, consider two strings: **A="Apple"** and **B="Aptitude"**. The first index where they are different is 2: **A[2]='p'** and **B[2]='t'**. Since A[2] < B[2], we conclude that the string A < B, i.e., A comes before B in alphabetical order. There are no special checks needed for upper-case vs. lower-case vs. punctuation etc.; simply check if **A[i] < B[i]** where **i** is the first character position where the strings are different. If one string terminates before any differences are found, then the shorter string appears first in alphabetical order. If both strings terminate before any difference is found, then they are equal.

Compile and run your program on inputs of your choice, and also make sure it runs correctly on the sample inputs provided. Refer to the sample inputs and outputs provided on exactly how to format your I/O.

--------------------------------------------------------------------------------

## Exercise 2

Repeat the above exercise (copy **ex1.c** to **ex2.c** using the [cp](http://man7.org/linux/man-pages/man1/cp.1.html) command), but this time use C string library functions to simplify the code of the bubble sort.

* Add "**#include <string.h>**" near the top of the C file to tell the compiler that you will be using the C string library functions.
* Remove your **my_compare_strings()** function from the code. Instead, use the C function **strcmp()** to perform the comparison of two strings. _Study this function_ carefully (see readings above). You may choose to use a similar function called **strncmp()** instead of **strcmp()** because the former also allows you to specify the maximum number of characters to check (e.g., for extra safety, or if only a first few characters are significant, etc.).
* Remove your **my_swap_strings()** function from the code. Instead, use the C function **strcpy()** to perform the copy of one string to another, in order to perform the swap of two strings (see readings above). Thus, you may use a temporary string variable (**char tempstring[LEN]**), and three calls to **strcpy()** to swap two strings. There is also a variant **strncpy()** which copies only a specified maximum number of characters.

Name the file containing your code **ex2.c**. Compile and run your program on inputs of your choice, and also make sure it runs correctly on the sample inputs provided.

----------------------------------------------------------------------------------------

## Exercise 3

Write a function to compute n choose k, i.e., the value of the binomial coefficient <sup>n</sup>C<sub>k</sub>. Call your function **NchooseK()**. (For more on binominal coefficients, see [Wikipedia article](https://en.wikipedia.org/wiki/Binomial_coefficient).)

Specifically, we will implement this function using the [recursive definition](https://en.wikipedia.org/wiki/Binomial_coefficient#Recursive_formula):

```
NchooseK(n, 0) = 1
NchooseK(n, n) = 1
NchooseK(n, k) = NchooseK(n-1, k-1) + NchooseK(n-1, k)
```
You can assume that _k_ will never be greater than _n_, and that both numbers are non-negative.

Note: There is another, more common, definition of **NchooseK()** that is not recursive, and uses factorials. **You are not supposed to implement that definition**. Implement the recursive definition provided above, which only uses additions.

Now write the **main()** function of your program so it does the following, repeatedly: (i) reads two integers from the terminal, n and k, separated by space; (ii) calls **Nchoosek()** to compute the binomial coefficient; and (iii) displays the result. The program should do so repeatedly until the user enters "0 0", in which case it prints the result ("1") and terminates.

Refer to the sample inputs and outputs provided in the **testIO** directory on exactly how to format your I/O. The numbers in the input are assumed to be in decimal format. Name the file with your program **ex3.c**. Compile and run your program on inputs of your choice, and also make sure it runs correctly on the sample inputs provided.

-------------------------------------------------------------------------------------------


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