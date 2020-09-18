LFTC 

    Lexical analisys - lab 1
    
    So we got a source of a program. We must divide it into tokens
    and categorise it.
    
    * What is a lexer?
    * Step 1 -- The scanning:
        categorize the words into lexemes and then tokens
        
        * Read from file all the available token templates
    * Step 2 -- Th evaluation:
        converting lexemes into values    
        
        
        
        
    Lexeme          -            Token
    
    int     keyword
    maximum(function name)      Identifier
    
    
    
    
    Example of cpp program
    
    #include <stdio.h>
         int maximum(int x, int y) {
             // This will compare 2 numbers
            if (x > y)
              return x;
            else {
             return y;
            }
        }   