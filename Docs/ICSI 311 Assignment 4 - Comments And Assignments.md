# ICSI 311 Assignment 4 – Comments and Assignments

In this assignment, we will add comments and assignment statements. Comments, as you know, are
ignored by compilers. But that ignoring has to be specifically coded. It doesn’t just happen.
Assignment statements are of the form:

variableName := expression

Of course, you have already written (most of) expression. As promised, we are going to use that code
now. By the end of this assignment, your interpreter will be using variables and running
-----------

## Comments

We need to add a new state to the lexer for comments.

When the lexer sees (\* it will go into the comment state.
In the comment state, all characters are ignored until it encounters \*).

* Note that we already process parenthesis, so you will have to add a (single character) lookahead
  to determine
  if this is a comment or just a parenthesis.
  When the comment ends, the compiler should go to the
  start state.

For the input:

    abc(\* comment \*)def

The lexer should generate two different identifiers “abc” and “def”.

Other than the changes to the lexer, nothing else needs to be done to support (ignore) comments.

-----------

## Assignment statements

We need to add := to the lexer. Create a new token for it and add it to the lexer. Notice that in
this language there is no assignment from functions like in Java:

    char c = getACharacter();
    
    Instead, in Shank, you would say:
    
    getAChar(var c : character)

This makes assignments simpler. We just need to make the parser expect assignments and then (later)
make the interpreter process them.

* Create two new ASTNodes called `AssignmentNode` and `VariableReferenceNode`.


* `VariableReferenceNode` should contain the name of the variable being referenced. `AssignmentNode`
  should have a `VariableReferenceNode` (for the variable being assigned) and an ASTNode for the
  expression that is being assigned.

The tradition in compilers is to use “lhs” and “rhs” (left-hand
side and right-hand side) for these members. This is not a good tradition. I used “target” and
“expression”.

* Create a new ASTNode called “statementNode”.

  Make `assignmentNode` derive from it (`ASTNode`->
  `statementNode`->`assignmentNode`).


* Add a collection of `statementNode` to the **functionDefinition**.
  Now
  our functions can have statements.
* Make a parser method that creates assignments or fails (returns null). **Assignment**()
  succeeds if it
  finds:

       Identifier assignment expression endOfLine

Hint: I added a “`peek`” function that does a look-ahead in the token stream. I used that to find
the
assignment token. If that is there, I know that this is an assignment. Therefore, if identifier is
not there or expression fails, I throw exceptions.

# Finishing Statements

Eventually, we will have many types of statements, not just assignment.

* Create a **statement**() **function** that just returns the result of our assignment() function.
    * **Statements**() should return a collection of `statementNode`.
    * Do note that we will reuse this statement processing function later, so make sure that you
      keep the “target” (**functionDefinition** in this case) flexible.
    * The caller of the **statement**() function now has a simple way to process the next statement.

Next we need to process a number of statements – a block can have more than one.

* How do you process a block of statements? We will follow the same pattern that we have been using
  – delegation.
    * “**statements**()” calls “**statement**()” until **statement**() doesn’t succeed, just like we
      did with **functionDefinitions**.

A block consists of

    begin endOfLine
    
    {statements}
    
    end endOfLine

You might notice a pattern – we make ASTNodes to hold the data and in parallel we make methods that
look at the token stream to see if it matches the expected tokens.

## Testing

Add some assignment statements between the begin and end statements from your previous assignment.
Add some comments, too. Make sure that the comments are ignored and the assignment statements are
printed as part of your function.

 

 

 

 

 

 

 

 