# Lab1: Editing, compiling, and running C programs
----------------------------------------------------

Before starting on these instructions, make sure that you have received your assignment through github classroom. **DO NOT** clone the instructor repository or you will not be able to submit when you are finished.

----------------------------------------------------
## Exercise 0: Setup/Shell tutorial
First, start up and log into your virtual machine. If you forgot how to do so, the following command should suffice: `vagrant up && vagrant ssh`. Note that to set up and complete this and subsequent labs, you will need to know the basics of operating a shell. You can find a tutorial [here](http://www.cs.unc.edu/~montek/teaching/Comp411-Fall17/UnixTut/).

Next, **Clone** this repository to your virtual machine. This will create a copy of the files for you to work with. Enter the command `git clone repository_link` to do this. Get the link by clicking the "clone or download" button on the top-right of this page.

Go ahead and cd into the new directory that git has made for you. Use `ls` to find the name of the new directory and type the command `cd directory_name` to go to it.

For this lab, you will use a command-line text editor called **nano** to write code (if you are familiar with these, other options like vim and emacs are also acceptable) Try opening the c file you just downloaded.

```nano intro.c```

Before you can run your programs, you must first compile them. Try:

```gcc intro.c```

This will create a file called "a.out" run it by entering the command `./a.out`. Note that the "./" is required if you are executing a file in the current directory. You can also quickly view the contents of this file with `cat a.out` (yes, its supposed to look like garbage).

Remove this file and use GCC's **-o** option to name the output file.

```
rm a.out
gcc intro.c -o intro
```

Create file **intro.in** with a value of the radius (you can use **nano** for this) and run the program so that it takes its input from **intro.in** and writes the output into **intro.out**:

```./intro <intro.in > intro.out```

Carefully note the syntax of the above command to run the program with its standard input redirected from a file, and its standard output redirected to a file. The order of the two files does not matter, so `./intro > intro.out < intro.in` would also be okay, but listing the input file first makes the command easier to read. Note however that `intro.in > ./intro > intro.out` is not a correct way to accomplish the same; the executable file always comes first.

--------------------------------------------------------------------
## Excercise 1
Modify the program so that it prompts you to enter the radius value as input in centimeters using the prompt **"Enter radius (in cm):\n"**, but displays the result in **square inches**. (One inch is 2.54 cm exactly.) Use **nano** to edit the file to make this modification, and save the file as **ex1.c**.

Compile this program to create an executable file called ex1 by typing

```gcc ex1.c -o ex1```

Run the executable **ex1** on a couple of different inputs to verify that it is working correctly. The inputs and outputs can be from the keyboard/terminal; you do not need to use redirection of input/output from/to files for this exercise. Here is an example execution scenario (user input is in blocks, and program output is in bold):

`./ex1`\
**Enter radius (in cm):**\
'2.54'\
**Circle's area is 3.14 (sq in).**



Reminder: We always put a **"./"** before the name of the executable when executing a program in your current working directory, hence `./ex1` above.

--------------------------------------------------------------------
**Exercise 2**

Copy the file **ex1.c** to **ex2.c** (so you do not accidentally edit ex1.c any further). Example command: `cp ex1.c ex2.c`. Modify the program in **ex2.c** to also compute and display the circumference. Compile it to an executable called **ex2**, and run it. Here is an example execution scenario:

`./ex2`\
**Enter radius (in cm):**\
`2.54`\
**Circle's area is 3.14 (sq in).**\
**Its circumference is 6.28 (in).**

--------------------------------------------------------------------
**Exercise 3**

Copy the file **ex2.c** to **ex3.c**. Modify the program in **ex3.c** to make it work on multiple inputs. In particular, it should repeatedly ask for a radius value (in centimeters), and print the corresponding area (in square inches) and circumference (in inches), until the user enters the value 0 as radius. At that point, the program should print the area and circumference (both 0), and then terminate.

Compile this program to make an executable called **ex3**, and run it, feeding it several radius values from the keyboard, and verifying that the values it displays are correct. A single run of the program should be able to compute as many of these calculations as you want, until you enter **0**.

`./ex3`\
**Enter radius (in cm):**\
`2.54`\
**Circle's area is 3.14 (sq in).**\
**Its circumference is 6.28 (in).**\
**Enter radius (in cm):**\
`5.08`\
**Circle's area is 12.57 (sq in).**\
**Its circumference is 12.57 (in).**\
**Enter radius (in cm):**\
`0`\
**Circle's area is 0.00 (sq in).**\
**Its circumference is 0.00 (in).**

--------------------------------------------------------------------
## Testing

Sample inputs and corresponding outputs are provided in the **testIO** folder. You should try running your program on the sample input files provided, and make sure the program's output is **identical** to that in the sample output files. For example, the sample input files for Exercise 1 are called **ex1in1** and **ex1in2**. Their corresponding outputs are in the files **ex1out1** and **ex1out2**. You can, of course, manually type or cut-and-paste the contents of the input files onto your terminal when running the program. Or, you could feed that sample input to your program using input redirection **(./ex1 < ex1in1)**. To verify that your program is working correctly, compare its output to the test output provided. Again, you could do this comparison manually, or you could use a very useful shell command called **diff**.

The full sequence of steps to run your program and verify its output is as follows.

```
./ex1 < testIO/ex1in1 > ex1result1
diff ex1result1 testIO/ex1out1
```

The **diff** command prints any differences it finds between your program's output (which you redirected into the file **ex1result1**) and the correct output provided to you (in the file **ex1out1**). Only those lines that do not match are shown. Each line in the output is prepended with "<" if it belongs to the first file, and with ">" if it belongs to the second file. See [Wikipedia page on diff](https://en.wikipedia.org/wiki/Diff_utility) for more information.

The variation **diff -y** prints the two files side-by-side (the first one on the left, the second on the right), and highlights lines where they differ. Watch out for extra spaces at end of lines, etc.!

```diff -y ex1result1 ex1out1```

If you only want to see the lines that differ, you can use the following command:

```diff --suppress-common-lines -y ex1result1 ex1out1```

If there are no differences, it prints nothing.

Before submitting your work, be sure that your compiled program runs correctly on **all of the sample inputs provided** exactly, i.e., diff produces no output at all. You may receive zero credit if your program's output does not exactly match the sample outputs provided. For example, for Exercise 1, two input files are provided: **ex1in1** and **ex1in2**. Their corresponding outputs are also provided: **ex1out1** and **ex1out2**. To check that your program works correctly on both, you may do the following:

```
./ex1 < ex1in1 > ex1result1
diff --suppress-common-lines -y ex1result1 ex1out1
./ex1 < ex1in2 > ex1result2
diff --suppress-common-lines -y ex1result2 ex1out2
```

Once you become familiar with **diff**, you may prefer to invoke it simply without any of the modifiers:

```
./ex1 < ex1in1 > ex1result1
diff ex1result1 ex1out1
./ex1 < ex1in2 > ex1result2 
diff ex1result2 ex1out2
```

If neither of the **diff** commands showed mismatches between your program's output and the desired output, then your **ex1** program is assumed to work fine. If your output happens to have one or more extra space characters at the end of a line, they can be hard to tell. In that case, use the `hexdump -c` command to display all the characters in your file:

`hexdump -c ex1result1`

The `hexdump -c` command will show all spaces (as spaces), tabs (as \t), newlines (as \n), returns (as \r), etc., thereby making it easier to see whitespace characters.

Repeat such tests for Exercises 2 and 3 using all the input and output sample files provided for each.

--------------------------------------------------------------------
## Submission Procedure

First, make sure that your github repository is up to date with the files on your VM. Enter the following sequence of commands to push your changes to **GitHub**.

```
git add *
git commit -m "replace this with a useful message"
git push origin master
```

Next, navigate to the assignment on gradescope and click the "upload submission" button. Select **GitHub** as your submission method and log in to your account. Find the assignment you want to submit under the "repository" dropdown, and select **master** under the "branch" dropdown.

Your score should appear under this assignment submission after the autograder is done. The submission policy is subject to change, but ideally you should only need to submit once, after throughly testing your code.
