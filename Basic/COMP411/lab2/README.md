# Lab2 : Basic arithmetic, input/output, conditionals and loops

Your assignment must be submitted electronically by **11:59pm on Sunday, Feb 3, 2019**.

---------------------------------------------------------

Exercise 0
----------

Prior to beginning this tutorial, carefully review the description of the following input/output functions:

*   **getchar()**
*   **putchar()**
*   **gets()** and **fgets()**: Note that **gets()** is no longer available/recommended in newer C compilers. The reason is that **gets()** does not provide a way to limit the number of characters read into a string, and hence it is often the culprit in buffer overflow errors that are frequently exploited by malware. Instead, you will use **fgets()**, which requires the programmer to specify the maximum length of the string to be read.
*   **puts()** and **fputs()**
*   **scanf()**
*   **printf()**


### Relevant readings are:

*   Perry and Miller: Chapters 4, 6, 8 and 18.
*   A very good online reference: [http://www.cplusplus.com/reference/cstdio/](http://www.cplusplus.com/reference/cstdio/). Click on the relevant functions in the left pane under _functions_.

You should also use the **man** command to read the online manual pages for each of these functions (use the spacebar to scroll to the next page, 'b' to the previous page, up/down arrows to scroll a line, and 'q' to quit).

    % man scanf

Make sure you understand the format strings used by **scanf()** and **printf()** to specify the format of the input/output, especially reading/writing characters, strings, integers, and floats. Make sure you understand how the width (i.e., number of characters used) is specified in a format string, and also how left- vs. right-justification is specified.

A good summary of the formatting specifiers for **scanf()** and **printf()** is available here: [http://www.cplusplus.com/reference/cstdio/scanf/](http://www.cplusplus.com/reference/cstdio/scanf/) and [http://www.cplusplus.com/reference/cstdio/printf/](http://www.cplusplus.com/reference/cstdio/printf/).

In the exercises below, you will only need the **scanf** and **printf** functions, but you will use the others listed above in future assignments.

_Important Tip:_ When printing using **printf**, you need to specify within the format string all the white spaces you would like printed. For example, `printf("%d Â %f\\n\\n", 1, 2.1);` prints the integer 1 and float 2.1 separated by two spaces, followed by two newlines. However, when reading the same, you will use `scanf("%d%f", &i, &f);`, _without specifying any white spaces_ between the integer and the float being read. This is because the **scanf** function, when reading numbers, skips over all white space before the start of the number, exactly as would be expected of such a routine. So, it would correctly read **i** and **f** regardless of whether they were separated by one space, two spaces, three tabs, five newlines, etc. However, if you were to write `scanf("%d Â %f\\n\\n", &i, &f);`, then the function will _expect_ (at least) two spaces between the numbers, and two newlines afterward.

So, in summary, avoid spaces in the format string for **scanf**, but provide exact spacing/newlines in the format string for **printf**.

* * *

Note
----

For the exercises below, you will code your C programs in the files **ex1.c**, **ex2.c**, **ex3.c**, **ex4.c** and **ex5.c**, respectively. Sample input and output files are provided in the **testIO** folder.

* * *

Exercise 1
----------

Write a program that does the following: It prompts the user with **"Enter a number from 1 to 20:\n"** (note the newline after the colon), and reads the user's response. If the user's response was, say, 10, the program then prints a short message followed by the first 10 *ordinal numbers* (see [this](https://www.mathsisfun.com/numbers/cardinal-ordinal-chart.html) article), one per line, and then terminates. If the user enters a number that is outside of the 1..20 range, the program should print the error message **"Number is not in the range from 1 to 20\n"** (note the newline at the end), and terminate.

The file with your C program should be called **ex1.c**, and the compiled version called **ex1**. Compile the C program using the C compiler (cc or gcc), and test it with a few different inputs. You can find sample test input and output files as described above. Below is one execution scenario:
<pre>
<b>Enter a number from 1 to 20:</b>
7
<b>Here are the first 7 ordinal numbers:
1st
2nd
3rd
4th
5th
6th
7th</b>
</pre>
Here's another scenario:
<pre>
<b>Enter a number from 1 to 20:</b>
22
<b>Number is not in the range from 1 to 20</b>
</pre>

Assume that the ordinal numbers from 4 to 20 are all spelled with a th, i.e., 4th, 5th, ... 20th.
* * *

Exercise 2
----------

Write a program that requests 10 double-precision floating-point numbers (data type **double**), and prints their sum, minimum, maximum, and product to 5 decimal places. Specifically, the program should prompt the user with this message "**Enter 10 floating-point numbers:\n**" (note the newline after the colon). It should print the sum, minimum, maximum and product formatted _exactly as shown (to 5 digits after the decimal point)_:
<pre>
<b>Enter 10 floating-point numbers:</b>
1.45 -2e2 -2e-2 14 -10.0 0.01 -0.02 20 -3e1 +4e+0
<b>Sum is -200.58000
Min is -200.00000
Max is 20.00000
Product is -389.76000</b>
</pre>
_Tip:_ There is no need to use arrays for this exercise. You can calculate/update all four of the output values each time a new number is read from the input. In particular, the first value read becomes the initial sum, min, max and product. Then, every subsequent value read simply updates these four quantities. To print to 5 decimal places, carefully select the format string for **printf()**, specifically, the _specifier_, _width_ and _precision_, after studying the tables given at http://www.cplusplus.com/reference/cstdio/printf/.

Name the file with the C program **ex2.c**. Compile it, save the executable as **ex2**, and test it on the sample input and output files provided.

* * *

Exercise 3
----------

Write a program that requests six integers (**"Enter six integers:\\n"**) (note newline after colon), reads all of them, then prints all of them in the following format: (i) first print a header line as shown in the example below; then (ii) two integers per line, with each integer right-justified in a field of 10 characters, separated by two blank spaces. Each of the integers supplied could be in either decimal, octal or hexadecimal format, and your program should read it correctly. For output, each integer should be printed in decimal format.

For example, if the numbers input are 1, 010, 0x20, 25, 1000, -200, then the output should be exactly as follows:
<pre>
<b>Enter six integers:</b>
1 010 0x20 25 1000 -200
<b>1234567890bb1234567890
         1           8
        32          25
      1000        -200</b>
</pre>

Note: All numbers that start with a '0' are assumed to be _octal_, i.e., number in base 8 ([Wikipedia article](https://en.wikipedia.org/wiki/Octal)). Thus, "010" in _octal_ is the number 0 + 8*1 = 8 in decimal. Similarly, numbers that start with "0x" are assumed to be _hexadecimal_, i.e., number in base 16 ([Wikipedia article](https://simple.wikipedia.org/wiki/Hexadecimal_numeral_system)). Thus, "0x20" in hexadecimal is the number 0 + 2*16 = 32 in decimal.

Name the file with the C program **ex3.c**. Compile it to **ex3** and test it on the sample input and output files provided.

_Tip 1:_ Carefully study the format specifiers (**%i, %d, %u, %o, %x**) for **scanf()** from the reference provided above ([http://www.cplusplus.com/reference/cstdio/scanf/](http://www.cplusplus.com/reference/cstdio/scanf/)).

_Tip 2:_ Carefully study the width specifier for **printf**() from the reference provided above ([http://www.cplusplus.com/reference/cstdio/printf/](http://www.cplusplus.com/reference/cstdio/printf/)). There is an example on that page showing how to print a number into a field of 10 characters, preceded by blanks.

* * *

Exercise 4
----------

Now redo Exercise 3 so that all integers in the input are strictly interpreted as decimal numbers, even if they start with a leading '0', i.e., the number "010" is the decimal number 10, not the octal form of 8. (The input will not contain any hex numbers starting with '0x'.) _Tip:_ Carefully choose the format specifier (**%i, %d, %u, %o, %x**) for **scanf()**.

For example, if the numbers input are 1, 010, 00020, 25, 1000, -200, then the output should be exactly as follows:
<pre>
<b>Enter six integers:</b>
1 010 00020 25 1000 -200
<b>1234567890bb1234567890</b>
         1          10
        20          25
      1000        -200
</pre>
Name the file with the C program **ex4.c**. Compile it to **ex4** and test it on the sample input and output files provided.

* * *

Exercise 5
----------

Write a program that repeatedly reads an integer in decimal format, and determines if it is a _triangular number_ ([Wikipedia article](https://en.wikipedia.org/wiki/Triangular_number)) or not. Specifically, the program should do the following:

*   Prompt the user to enter a number with **"Number ?\\n"** (note space before and newline after the "?")
*   Assume the user will only enter a number small enough to be in the range of a normal **int**, or enter 0 to stop the program. The number entered will always be in decimal format, so "010" should be interpreted as ten not eight. _Tip:_ Check the format specifier for **scanf()**.
*   If the number entered is a triangular number, the program prints "_num_ is a triangular number", else prints "_num_ is not triangular, nearest triangular number below _num_is _xx_", where "_xx_" is the greatest triangular number just below "_num_". In either case, the output should be end with a newline.
*   The program repeats, prompting for the next input, until the user enters a 0, in which case the program prints "Done", followed by a newline, and terminates.

For example, the following is one execution scenario:
<pre>
<b>Number ?</b>
6
<b>6 is a triangular number
Number ?</b>
28
<b>28 is a triangular number
Number ?</b>
60
<b>60 is not triangular, nearest triangular number below 60 is 55
Number ?</b>
500
<b>500 is not triangular, nearest triangular number below 500 is 496
Number ?</b>
036
<b>36 is a triangular number
Number ?</b>
0
<b>Done</b>
</pre>
First test your program by running it through the execution scenario above, and make sure it produces exactly the same output. Name the file with the C program **ex5.c**. Compile it to **ex5** and test it on the sample input and output files provided.

* * *

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

Due Date and Submission Procedure
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