
Restaurant Puzzle
=========

> Program to calculate the minimum cost provided by any Restaurant for the given order.


Statement
----

A program that accepts the town's price file, and a list of item labels that someone
wants to eat for dinner. The price file has the prices of every item on every menu of 
every restaurant in town, all in a single CSV file. In addition, the the price file also offer Value
Meals, which are groups of several items, at a discounted price.
The program outputs the restaurant they should go to, and the total price it will cost them.


Algorithm Approach
----

Solution uses dynamic programming to calculate minimum cost while considering all combinations of 
values meal and individual items. The solution is built from bottom to top by starting from problem 
size of 1 (i.e. single item at a time) to n , size of order list. At any level k optimal cost is calculated as minimum of following two options: 
    - 1) minimum of all the smaller subproblems (1 to k-1) 
    - 2) Value meal containing all k desired items
 
     
Steps
----
steps to run the program

```sh
1) mvn clean install
2) mvn exec:java -Dexec.args="arg1 arg2 arg3"

```


