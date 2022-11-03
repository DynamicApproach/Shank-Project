# 311 Assignment 2 – Start the Parser

-----
Overview
-----
The Lexar generates a single list of tokens.

The Parser reads the list of tokens and generates an Abstract Syntax Tree (AST).

The AST is a tree of nodes, where each node represents a statement or expression.

The AST is used to generate the output of the compiler.

-----
This assignment must have six new source files

    Parser.java,
    Node.java,
    IntegerNode.java,
    FloatNode.java,
    MathOpNode.java,
    Interpreter.java
    As well as your first three source files (Shank.java, Token.java, Lexer.java)

The parser will take the collection of tokens from the lexer and build them into a tree of AST
nodes.

1. [ ] 
2. [ ] To start this process, make an abstract Node class.
3. [ ]
4. [ ] First of all, we define our abstract class. This class can't be instantiated, but can be
   extended.
5. [ ] We can also define functions that must be implemented in all classes that extends this
   one.```
6. [ ] 
7. [ ] Add an abstract ToString override.
8. [ ] Now create an `IntegerNode` class that derives from Node.
9. [ ] It must hold an integer number in a private member and have a read-only accessor.
10. [ ] 6. [ ]Create a similar class for floating point numbers called `FloatNode`.java.
11. [ ] Both of these classes should have appropriate constructors and **ToString**() overrides.
12. [ ] Create a new class called `MathOpNode` that also derives from Node.
13. [ ] MathOpNode must have an enum indicating which math operation (add, subtract, multiply,
    divide) the
14. [ ] class represents.
15. [ ] The enum must be read-only.
16. [ ] The class must have two references (left and right) to the Nodes that will represent the
    operands.
18. [ ] These references must also be read-only and an appropriate constructor and ToString() must
    be
19. [ ] created.

Reading all of this, you might think that we can just transform the tokens into these nodes.This
would work, to some degree, but the order of operations would be incorrect.
Consider 3 * 5 + 2. The tokens would be NUMBER TIMES NUMBER PLUS NUMBER.

That would give us

        MathNode(*,3,MathNode(+,5,2)) 

which would yield 21, not 17.

1. [ ] Create a `Parser` class (does not derive from anything).
2. [ ] 
3. [ ] It must have a constructor that accepts your collection of Tokens.
4. [ ] 
5. [ ] Create a public **parse** method (no parameters, returns “Node”).
6. [ ] 
7. [ ] **Parse** must call **expression** (it will do more later) and then **matchAndRemove**() a
   newLine.
8. [ ] 
9. [ ] You must create some helper methods as **matchAndRemove**() as described in lecture.

The classic grammar for mathematical expressions (to handle order of operations) looks like this:

    EXPRESSION = TERM { (plus or minus) TERM}
    TERM = FACTOR { (times or divide) FACTOR}
    FACTOR = {-} number or lparen EXPRESSION rparen

Turn each of these (expression, term, factor) into a method of Parser.

1. [ ] Use **matchAndRemove** to test for the presence of a token.
   Each of these methods should return a class derived from `Node`.

Factor will return a `FloatNode` (NUMBER with a decimal point) or an IntegerNode (NUMBER without a
decimal point) OR the return value from the EXPRESSION.

Note the unary minus in factor – that is important to bind the negative sign more tightly than the
minus operation.

Also note that the curly braces are “0 or more times”.
Think about how 3*4*5 should be processed with these rules.
Hint – use recursion and a helper method.

Also think carefully about how to process “number”, since we have two different possible nodes (
`FloatNode` or `IntegerNode`). Depending on how you implemented your lexer, factor may or may not
need
to deal with negating the number.

Finally, we will build (the beginning of) our interpreter.

1. [ ] 
2. [ ] Create a new class called Interpreter.
3. [ ] Add a method called “**Resolve**”.

It will take a `Node` as a parameter and return a float.
For now, we will do all math as floating point.
The parser handles the order of operations, so this function is very simple.
It should check to see what type the `Node` is:
For `FloatNode`, return the value.

            For IntNode, return the value, cast as float.

            For MathOpNode, it should call Resolve() on the left and right sides. That will give you two floats. Then look at the operation (plus, minus, times, divide) and perform the math.

Make sure that you test your code. Change your main to instantiate a parser (passing in the tokens
from the lexer) and then call parse on the parser. Right now, it will only process a single line.
Print your AST by using the “ToString” that you created. Use several different mathematical
expressions and be sure that order of operations is respected. Then add a call to Resolve() and
check the math.

A number of you had some trouble with assignment 1. Many of you did not. I have asked the TAs not to
finish grading assignment 1.

Instead, I asked them to grade assignments 1 & 2 at the same time (i.e. when you hand in parser).
That gives you a few more days on the lexer if you had trouble with it. I won't do this again, so
don't count on it. You will still get two grades, though.

#### **A note on code submission:**

- Make sure that you submit only your .java files, unzipped. I know other professors do some
  different
  things.

For each project this semester submit all of your code. By the time we get to assignment 9, you will
be submitting many, many Java files, including your Shank, Lexer and Parser classes.
With the parser, I have had some Q&A that you may find useful:

PARSER Q&A

Do I need parenthesis in my lexer?

     Yes. You should go back and add those. You will see this throughout the semester - adding a parsing
    feature requires something new in the lexer.

Can we ignore the unary minus in the parser, since we handled that in the lexer?
Yes. We made the lexer a bit more complex but simplified the parser a little.

    The end product of the parser is ONE AST, representing all of the Tokens. Does it also represent the
    order of the Tokens (either by BFS or DFS)?
    It is a tree that mostly happens to match order because the code order is the token order and that
    happens to match the way that we build the tree, for the most part. But it can vary. For example –
    the math operations get re-ordered to match order of operations. The parenthesis are eliminated
    altogether because they exist only to order operations and we are solving that in our tree.

How exactly are all the nodes being connected?

    Is this done in the parser class? Will we ever be
    rebinding node references (parent and children), or only setting them from a previous node?
    
    The nodes have children, like any other tree. For example – a ProgramNode would have a collection of
    FunctionNode. A FunctionNode would have a collection of StatementNode.
    ProgramNode ➡️ FunctionNode ➡️ StatementNode

Does every node type have a reference to their children's nodes?

    Yes, except for leaves. IntegerNode doesn’t have children, for example.

Does every node type have a specific and set number of children nodes?

    Kind of. As I mentioned above, some nodes have “infinite” children. Some have 2 (like MathOpNode)
    and some have one or none.

Where is the root node contained?

    The parser returns the root node to Main.

For this specific homework assignment, we are only handling MathOp, Int, and Float nodes, right? So
were not doing program, function, while, etc. like the slide examples yet.

    Correct. Those examples are to help you have context as to what we are building overall.

The only nodes that would have children at the moment are the MathOp nodes? While int and float
nodes strictly do not have children.

    Correct.

How am I to set the children of the MathOp nodes, when their references are read only? Are they
always instantiated with their left and right children nodes? Ex) new MathOpNode(left child, right
child)

    You don't build a MathOpNode until you know what the children are.

Are ALL nodes created by calling expression(Token), using recursion?

    For now, yes, since that is our "root".

How are problems like 3+4+5 handled?

    Note the curly braces in the assignment. Those are repetition. When you encounter a token that you
    recognize (+, - in expression or *, / in term) and you get the right side of the operator, then you
    have to look at the NEXT symbol to see if it is another + or - (for expression) or * or / (for term)
    and run again. For 3+4+5, you will end up with MathOp(+,MathOp(+,3,4),5).

What do we do with EndOfLine?

    Good question. For this case, we should call expression() until we run out of tokens (i.e. the list
    is empty). In between valid expressions you will have EndOfLine, so your parser will have to know to
    expect and accept it.

What is MatchAndRemove?

    MatchAndRemove is a very straightforward function:
      If the next token in line is of a specific token type that we are looking for, remove it from the
      list and return it, otherwise return null.

    - if(MatchAndRemove(List.get(0))== token)

    - [NUMBER(5), PLUS, NUMBER(7)]

    calling matchAndRemove(Token.Type.PLUS) on the above list will return null and do so appropriately

Do Expression, Term and Factor take parameters?

    No. They need only the token list which should be a member of the parser.

Is the goal of the parser to take a string of tokens and make an AST?

    YES!

What are Expression, Term and Factor?

     Expression, Term and Factor are just names for the rules that we use to enforce order of
     operations.
     The parser should call Expression.

    - Then expression runs and looks for a + or -.
      Expression should call Term.
    - A term is a factor * or / a factor.
        - If the next token isn’t a * or /,
          we don’t have a term, so we just return the Factor that we found.

---
eg.

    public Expression(){
        get token from list
        call term
        checks next token for + or -
        ???
    }
    public term(){
        store + call factor
        get next token, check for * or /
        if not ret factor?
    }
    public factor(){
        turn val to float??
        doesn't work ? ret null
    }

----

    Term should call Factor. If factor can’t resolve, return null (this isn’t a math operation). Now we
    have a Factor. A term is a factor * or / a factor.
    
    If factor can’t resolve, return null (this isn’t a math operation). Now we have a Factor. A term is
    a factor * or / a factor. If the next token isn’t a * or /, we don’t have a term, so we just return
    the Factor that we found. Then expression runs and looks for a + or -.
    
    "Turn each of these (expression, term, factor) into a method of Parser. Use matchAndRemove to test
    for the presence of a token. Each of these methods should return a class derived from Node. Factor
    will return a FloatNode (NUMBER with a decimal point) or an IntegerNode (NUMBER without a decimal
    point)"