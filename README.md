# What is the result of AST-diff detection?

This repository provides resources for evaluating the results produced by an AST (Abstract Syntax Tree)-based code diff engine. AST-diff detection allows us to analyze and compare the structure of code before and after modifications, capturing changes at a syntactic level rather than just at the textual level.

## Modification Categories

Given an original Java file, we categorize code modifications into four primary types:

- **INSERT:** This represents the addition of new code elements (e.g., adding new methods, classes, or statements) to the original file.
- **UPDATE:** This involves modifying existing code elements (e.g., changing method names, or updating variable types) in the original file.
- **DELETE:** This refers to the removal of existing code elements (e.g., deleting methods, classes, or individual lines of code) from the original file.
- **MOVE:** This indicates the relocation of code elements (e.g., moving a method from one class to another or reordering methods within a class) within the original file.

## Evaluation Process

For each of the four modification types, we conducted a series of five continuous modification steps:

1. **Step 1:** Initial modification
2. **Step 2:** Subsequent modification based on the result of Step 1
3. **Step 3:** Further modification based on the result of Step 2
4. **Step 4:** Continued modification based on the result of Step 3
5. **Step 5:** Final modification based on the result of Step 4

At each step, we calculated the AST-diff result relative to the original Java file, allowing us to track and analyze how the AST structure evolved across multiple modification steps.

## Provided Files

In this repository, each modification category (INSERT, UPDATE, DELETE, MOVE) has its own dedicated folder. Inside each folder, you will find a single Java file. This Java file represents the original code and its modified versions across the different modification steps.

The different stages of modification are captured through the file's Git commit history.

Each commit in the history of the Java file represents its state after one of the modification steps. By examining the commit history, you can trace how the code evolved through the five modification steps for that particular category.
