# Lab 3: Arrays and Strings in C

**Due 11:59pm, Sunday, Febuary 10, 2019.**

------------------------------------------

## Reading

Prior to beginning this tutorial, carefully review the following material from the Perry and Miller textbook: Chapters 6 (string basics), 19 (character types), and 21-23 (arrays).

**Declaring strings in C:** Unlike other high-level languages that explicitly provide support for strings (e.g., the String class in Java), there is no built-in type for strings in C. Instead, strings are handled as arrays of characters. Here are some ways of declaring strings in C:

``` C
        char String1[10];               // an uninitialized stringof up to 10 characters

        char String2[10] = "Hello";     // a string with space for 10 characters, initialized to "Hello"

        char String3[10] = { 'H', 'e', 'l', 'l', 'o', '\0'}
                                        // same as previous one, but note the '\0' terminator

        char String4[]   = { 'H', 'e', 'l', 'l', 'o', '\0'}
                                        // a strong with space for 6 characters; compiler counts for you
```

**String length and termination:** Unlike Java, the length of the string is not stored within the data structure. The entire data structure is simply the array of characters, and information about its size is typically not encoded by the compiler into the executable. Thus, at runtime, the actual length of each string is typically unknown. Instead a different approach is used.

Each string is terminated using a special character called the **"NULL"** character, which is **ASCII 0**, and can be represented as **'\0'**. Hence, in the examples above, String3 and String4 were initialized as character arrays, and the [string terminator](https://en.wikipedia.org/wiki/Null-terminated_string) **'\0'** had to be explicitly used to terminate the strings. There is a standard C string library (string.h) that provides many useful string processing functions, all of which expect **NULL-terminated** strings. You access those functions by using the `#include<string.h>` directive at the top of the main program file.

The NULL character is automatically appended by the compiler when the following declaration is used:

``` C
char String2[10] = "Hello";     // a string with space for 10 characters, initialized to "Hello"
```

With this type of declaration, the use of double quotes ("Hello") explicitly indicates that a string is intended and, therefore, the compiler automatically appends a NULL character, making it equivalent to the character array: `{ 'H', 'e', 'l', 'l', 'o', '\0'}`

If you forget to terminate the string, none of the C string functions will work properly. For instance, if String3 were not terminated using **'\0'**, a subsequent call to `printf(String3)` could very well produce output like: **HellojB%$@#($M**. Even worse, **printf()** might go out of the bounds of space available to your program while looking for the string terminator, most likely causing a **"segmentation fault" error**.

When declaring strings, be careful to count the space that will be needed by the terminator when allocating space for the string variable. Thus, the string "Hello" needs **6** characters, **not 5**. Likewise, String1 above can store at most 9 real characters, plus its terminator. Similarly, String2 can have at most 9 real characters plus the terminator, even though it is initialized to a shorter string. Thus, String2's initial contents could very well be `{ 'H', 'e', 'l', 'l', 'o', '\0', '#', '@', '%', '$'}`, where the last four characters are junk; these trailing characters are ignored by all C library functions because the string terminates at the **'\0'**.

All C library routines for reading and writing strings --- **scanf()**, **printf()**, and others we will soon learn --- always append the terminator at the end of any new string created or updated. However, if you are manually creating a new string and copying individual characters into it, it is your responsibility to append a terminator at the end. For example:

``` C
        char String2[10] = "Hello";     // a string with space for 10 characters, initialized to "Hello"
        ...
        ...
        String2[2] = '\0';              // puts a string terminator after the 'e'
```

The above code fragment turns String2 into `{ 'H', 'e', '\0', 'l', 'o', '\0', '#', '@', '%', '$'}`, which is effectively the string **"He"**.

-----------------------------------------------------------------------

## Exercise 1

Compile and run the program **analyze.c**. Modify the program to replace the loop containing repeated **getchar()** calls with a single call to **fgets()**. Please first review **P&M Chapter 6** in its entirety for basics on strings. Also review **P&M pp. 235-236** for details on the **fgets()** function, but here is a quick tip on its usage:

**Function signature:** `fgets(text, MAX, stdin)`;

**fgets** stands for _"get string from file"_, and it stores the string read into the **text** argument, reading up to a maximum number of **MAX** characters, from the file specified in the third argument, which in our case is the "_standard input_" or **stdin** (meaning keyboard, or a file if input is redirected using **"<"**).

Once you make this change, however, you will no longer have the length of the string in **length**, so you will have to use the C library function **strlen()** (see **P&M pp. 176-177** for details) to compute the length of the string.

Note that the string read using **fgets** has a **newline character** at the end, and **strlen** includes this newline character in the length of the string, which is not what we want. So, **adjust the string length accordingly**.

Note that while there are several others ways to accomplish this exercise, you must do _exactly_ as explained above, in particular: (i) use **fgets()** to read the line of input; and (ii) use **strlen()** to compute the length of the input line. Call your program **ex1.c**. Compile it and run it on a few inputs of your choice. Also, **run it on the sample inputs provided** and check that the output is correct.

----------------------------------------------------------------------

## Exercise 2

Make a copy of the program of **Exercise 1**, and name it **ex2.c**. Modify the program so that it outputs the line entered by the user in the **reverse order** of characters. Then, if the line is a _palindrome_, i.e., it reads the same forward and reverse, your program should also indicate that.

Specifically, your program should display, **"Your input in reverse is:"** (with newline), and the reversed input on the next line. And if the line were a palindrome, it should also print, **"Found a palindrome!"** (with newline).

**NOTE:** For this exercise, we will use a very strict definition of palindrome: _A string is a palindrome if and only if it reads exactly the same backward as forward, including spaces and punctuation._ Thus, for this exercise, **"madam"** is a palindrome, but **"Madam"** is **not**. Also, **"nurses, run"** is not a palindrome, but **"nursesrun"** is. We will use this highly restrictive definition for now just to make coding easier.

Compile and run the program on a few inputs of your choice. Also, run it on the sample inputs provided and check that the output is correct (i.e., _identical_ to the output provided, no extra white space anywhere).

----------------------------------------------------------------------

## Exercise 3

Make a copy of the program of **Exercise 2**, and name it **ex3.c**. We are now going to relax the rules for what constitutes a palindrome: **spaces and punctuation are to be ignored**, and two letters are considered to match **even if one is uppercase and the other is lowercase**. As an example, the input **"Race car"** should now be considered a palindrome, and **"Nurses, run!"** should as well. Assume that the input will only consist of letters, spaces, punctuation, and **one newline at the end.**

Tip: The C library includes several functions that will be helpful here. The functions **isspace()** and **ispunct()** can tell if a character is a white space or a punctuation, respectively. The function **isalpha()** checks if a given character is an alphabetic letter. The functions **tolower()** and **toupper()** help convert a given character to its lowercase or uppercase equivalent, respectively. [Here](http://www.cplusplus.com/reference/cctype/) is a good reference for all of these functions. To use these functions, please include the following line near the top of the program, where other header files such as **stdio.h** are included:

``` C
#include <ctype.h>
```

As in Exercise 2, your program should first display, **"Your input in reverse is:"**, and the reversed input on the next line. And if the line were a palindrome according to the relaxed rules of this exercise, it should also print, **"Found a palindrome!"**.

Compile and run the program on a few inputs of your choice, and also the samples provided.

-----------------------------------------------------------------------

## Exercise 4

Write a program that reads two matrices, each of size **3x3**, and prints their sum as another 3x3 matrix.

The program prompts the user with **"Please enter 9 values for matrix A:\n"**, and then reads the values for matrix A. The first three values will correspond to row 0, next three to row 1, etc. Then the program similarly prompts the user to enter the values for matrix B. And then it prints the sum of the two matrices. The values are of **int** data type.

Starter code is provided in **ex4.c**. Please look at the sample input and output files for the exact formatting. Specifically, each value of the result matrix is printed in a field of 10 characters (study the "width" specifier for **printf()**). Compile and run your program on inputs of your choice. Make sure it runs correctly on the sample inputs provided. The numbers in the input are assumed to be in decimal integer format.

_Reminder_: As explained in Lab 2, when reading numbers separated by whitespace, the preferred method is not to put a space within the scanf format string: i.e., **scanf("%d%d", ...)** is preferable to **scanf("%d %d", ...)**. This is because **scanf()** automatically skips over whitespace when reading numbers, so the first version will work whether there is one or more spaces, tabs, or even newlines between the numbers. The latter version, however, will expect a space between the numbers, not a tab or newline, and is thus unnecessarily over-specified.

-------------------------------------------------------------------
## Exercise 5

Write a program to read in a series of lines of text. As it reads each line, it prints it with these modifications: Each occurrence of **'E'** or **'e'** is replaced by **'3'**; each **'I'** or **'i'** is replaced by **'1'** (the _number_ one); each **'O'** or **'o'** by **'0'** (the _number_ zero); and each **'S'** or **'s'** by **'5'**. When you get an empty line, the program should stop.

Your program does not need to store the entire multiline text within the program. It should simply read one line at a time, modify it, print it, and then repeat with the subsequent lines until an empty line is read.

_Note:_ You may want to review the code for the program in **Exercise 1** first.

We provided you with some starter code in **ex5.c**. You may assume that no input line will be larger than **MAX_BUF** (including the newline and the null terminator at the end). As with Exercise 1, note that **fgets()** still returns one character for the newline, so take this into account when terminating the do-while loop in the example code.

Here's an example scenario of running this program:

<pre>
**% ./ex5**
<b>The quick brown fox jumps over the lazy dog.</b>
Th3 qu1ck br0wn f0x jump5 0v3r th3 lazy d0g.
<b>The five boxing wizards jump quickly.</b>
Th3 f1v3 b0x1ng w1zard5 jump qu1ckly.
<b>&ltempty line&gt</b>
</pre>

-----------------------------------------------------------------------

## Exercise 6

In this exercise, you will write a program that computes and prints the [Collatz sequence](https://en.wikipedia.org/wiki/Collatz_conjecture) of numbers for a given input **n**. In a Collatz sequence, if the current number is **n**, then the next number in the sequence is given by:

		n/2, if n is even
		3n + 1, if n is odd

A mathematician named Lothar Collatz conjectured that such a sequence **always eventually reaches the number 1**, at which point it terminates. For example, if the starting number **n** is, say, 12, then the following is the Collatz sequence beginning with 12:

		 12, 6, 3, 10, 5, 16, 8, 4, 2, 1

While the Collatz conjecture has yet to be formally proven, it has been empirically established for a very large range of numbers.

In this exercise, you are to write a program that prompts the user with **"Please enter the starting number of the Collatz sequence:\n"**. It then reads a number (assume it will be a non-zero positive integer, within the range of the **int** data type), and then prints the Collatz sequence starting with **n**.

Refer to the sample inputs and outputs provided on exactly how to format your I/O. Specifically, the numbers should be separated by a comma and one space. There should be no space before the first number. And there should be a newline immediately after the "1" which ends the sequence. The numbers in the input are assumed to be in decimal format. Name the file with your program **ex6.c**.

-------------------------------------------------------------

<center><h3>End of new content</h3></center>

------------------------------------------------------------

Testing
-------

#### Sample Inputs and Outputs

Sample inputs and corresponding outputs are provided in the **testIO** directory. You should try running your program on the sample input files provided, and make sure the program's output is *identical* to that in the sample output files.

To see what these files contain, you can use any of these methods:

*   Use the **cat** command to display the entire files contents on the terminal:

    `% cat ex1in1`
    
*   Use the **less** command to display it page-by-page if the file is too long and scrolls off the screen. Press the spacebar to scroll down to next page, 'b' to scroll up a page, up and down arrow keys to scroll by line, and 'q' to quit.
    
    `% less ex1in1`
    
*   Use the **nano** editor to open the file, just as you open your C files:
    
    `% nano ex1in1`
    

#### Compiling your source code

Use the following commands to compile your C programs (e.g., **ex1.c**) and create the executables (e.g., **ex1**):
```
% gcc ex1.c -o ex1  
% gcc ex2.c -o ex2
```

... and so on.

#### Running your executable

First, try to run your executable (**% ./ex1**), and provide it input from the terminal and observe the output. If everything looks good, you can use the following commands to run with our sample inputs. These commands send the output of your program directly to the terminal.
```
% ./ex1 < ex1in1  
% ./ex1 < ex1in2
```
... and so on.

Now, we will capture the program output into files so we can carefully examine them and compare against the sample outputs provided:
```
% ./ex1 < ex1in1 > ex1result1  
% ./ex1 < ex1in2 > ex1result2
```
... and so on. Repeat for each sample input file, and repeat for each exercise.

#### Check the output

You can examine your program output captured in the \*result\* files by using **cat**, **less** or **nano**, as explained above:
```
% cat ex1result1
% less ex1result1
% nano ex1result1
```
Compare your output with the sample output files provided (**ex1out1**, etc.) to be sure they match exactly.

#### Using diff

**diff** is a very powerful utility for comparing the contents of files, and can be used with several different options:

*   **diff _file1 file2_**  
    This version prints groups of lines in which the two files differ. It first prints the group from the first file, then the group from the second file. If there are no differences, it prints nothing.
*   **diff -y _file1 file2_**  
    Use this version if you prefer to see the two files side-by-side, with the line that differ highlighted. Note: The entire contents of both files are displayed.
*   **diff --suppress-common-lines -y _file1 file2_**  
    Use this version if you prefer side-by-side display, but only want to see the lines that differ. If there are no differences, it prints nothing.
*   **diff -qs _file1 file2_**\
    This version only reports if the files are different or identical. It does not report the actual differences.

Use the diff command to determine any differences between your program's output (which you redirected into the file **ex1result1**) and the correct output provided to you (in the file **ex1out1**). Watch out for extra spaces at end of lines, etc.!

For example:

`% diff -qs ex1result1 ex1out1`

... and so on. Repeat for each sample input file, and repeat for each exercise.

Before submitting your work, be sure that _each of your programs_ runs correctly on _all of the sample inputs provided_ exactly, i.e., diff reports no differences at all. You may receive zero credit if your program's output does not exactly match the sample outputs provided.

If diff produces errors, please manually inspect your output and the provided output to see what is different. You can use **cat** or **nano** to see the contents of the files.

#### Using hexdump

If the differences are due to white space errors only, it is sometimes hard to find the extra spaces, especially if they are at the end of a line. For that purpose, use `hexdump -c filename` to dump the contents of the file onto the terminal; it will show the ASCII codes of each character, and make it easy to see trailing white space at the end of lines:
```
% hexdump -c ex1result1
% hexdump -c ex1out1
```

* * *

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


* * *


















