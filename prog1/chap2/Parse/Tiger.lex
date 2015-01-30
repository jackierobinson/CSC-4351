package Parse;
import ErrorMsg.ErrorMsg;
%% 

%implements Lexer
%function nextToken
%type java_cup.runtime.Symbol
%char

%{
private void newline() {
  errorMsg.newline(yychar);
}

private void err(int pos, String s) {
  errorMsg.error(pos,s);
}

private void err(String s) {
  err(yychar,s);
}

private java_cup.runtime.Symbol tok(int kind) {
    return tok(kind, null);
}

private java_cup.runtime.Symbol tok(int kind, Object value) {
    return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(), value);
}

private ErrorMsg errorMsg;

Yylex(java.io.InputStream s, ErrorMsg e) {
  this(s);
  errorMsg=e;
}

%}

%{
   StringBuffer string = new StringBuffer();
   private int commentcount = 0;
%}

%eofval{
	{
	if(commentcount != 0) { err("Unclosed Comments");}
	/*if (!stringcomplete) report error err(string_eof) Unclosed string before EOF*/
	 return tok(sym.EOF, null);
        }
%eofval}       
letters=[a-zA-Z]
digit=[0-9]
uppercase=[A-Z]
lowercase=[a-z]

%state  STRING, COMMENT, IGNORE

%%
<YYINITIAL, COMMENT> \n|\r\n {newline();}
<YYINITIAL> " "	{}
<YYINITIAL> "," {return tok(sym.COMMA, null);}
<YYINITIAL> ":"    {return tok(sym.COLON, null);}
<YYINITIAL> ";"    {return tok(sym.SEMICOLON, null);}
<YYINITIAL> "("    {return tok(sym.LPAREN, null);}
<YYINITIAL> ")"    {return tok(sym.RPAREN, null);}
<YYINITIAL> "["    {return tok(sym.RBRACK, null);}
<YYINITIAL> "]"    {return tok(sym.LBRACK, null);}
<YYINITIAL> "{"    {return tok(sym.RBRACE, null);}
<YYINITIAL> "}"    {return tok(sym.LBRACE, null);}
<YYINITIAL> "."    {return tok(sym.DOT, null);}
<YYINITIAL> "+"    {return tok(sym.PLUS, null);}
<YYINITIAL> "-"    {return tok(sym.MINUS, null);}
<YYINITIAL> "*"    {return tok(sym.TIMES, null);}
<YYINITIAL> "/"    {return tok(sym.DIVIDE, null);}
<YYINITIAL> "="    {return tok(sym.EQ, null);}
<YYINITIAL> "<>"    {return tok(sym.NEQ, null);}
<YYINITIAL> "<"    {return tok(sym.LT, null);}
<YYINITIAL> "<="    {return tok(sym.LE, null);}
<YYINITIAL> ">"    {return tok(sym.GT, null);}
<YYINITIAL> ">="    {return tok(sym.GE, null);}
<YYINITIAL> "&"    {return tok(sym.AND, null);}
<YYINITIAL> "|"    {return tok(sym.OR, null);}
<YYINITIAL> ":="    {return tok(sym.ASSIGN, null);}
<YYINITIAL> "while" {return tok(sym.WHILE, null);}
<YYINITIAL> "for"   {return tok(sym.FOR, null);}
<YYINITIAL> "to"    {return tok(sym.TO, null);}
<YYINITIAL> "break" {return tok(sym.BREAK, null);}
<YYINITIAL> "let"   {return tok(sym.LET, null);}
<YYINITIAL> "in"    {return tok(sym.IN, null);}
<YYINITIAL> "end"   {return tok(sym.END, null);}
<YYINITIAL> "function" {return tok(sym.FUNCTION, null);}
<YYINITIAL> "var"   {return tok(sym.VAR, null);}
<YYINITIAL> "type"  {return tok(sym.TYPE, null);}
<YYINITIAL> "array" {return tok(sym.ARRAY, null);}
<YYINITIAL> "if"    {return tok(sym.IF, null);}
<YYINITIAL> "then"  {return tok(sym.THEN, null);} 
<YYINITIAL> "else"    {return tok(sym.ELSE, null);}
<YYINITIAL> "do"    {return tok(sym.DO, null);}
<YYINITIAL> "of"    {return tok(sym.OF, null);}
<YYINITIAL> "nil"    {return tok(sym.NIL, null);}
<YYINITIAL> "/*" {yybegin(COMMENT); commentcount += 1;}
<COMMENT> "/*" { commentcount += 1;}
<COMMENT> "*/" { commentcount -= 1; if(commentcount == 0) { yybegin(YYINITIAL);}}
<COMMENT> . {}
<YYINITIAL> \" {string.setLength(0); yybegin(STRING);}
<STRING> \" { yybegin(YYINITIAL); return tok(sym.STRING, string.toString());}
<STRING> \n|\r\n { err("Unclosed Strings" + " \"" + string.toString() + "\""); yybegin(YYINITIAL); newline(); return tok(sym.STRING,  string.toString());}
<STRING> [^\n\r\"\\]+ { string.append( yytext() );}
<STRING> \\t {string.append('\t');}
<STRING> \\n {string.append('\n');}
<STRING> \\r {string.append('\r');}
<STRING> \\\" {string.append('\"');}
<STRING> \\ { yybegin(IGNORE);}
<IGNORE> \n|\r\n { newline(); yybegin(STRING);}
<IGNORE> "^"{letters} {string.append((char)(yytext().charAt(1)-'A'+1));yybegin(STRING);}
<IGNORE> \\{digit}{digit}{digit} {int i = Integer.parseInt(yytext()); if (i < 128) {string.append((char)i);} else {err("Not in ascii range");} yybegin(STRING);}
<IGNORE> \" { string.append('\"'); yybegin(STRING);}
<IGNORE> \\ { string.append('\\'); yybegin(STRING);}
<IGNORE> " "|\t|\f {}
<IGNORE> . { err("Escape Sequence Illegal"); yybegin(STRING);}
<YYINITIAL> {digit}+   {return tok(sym.INT, new Integer(yytext()));}
<YYINITIAL> {letters}({letters}|{digit}|_)* {return tok(sym.ID, yytext());}
<STRING> . {string.append(yytext());}
<YYINITIAL> . { err("Illegal character: " + yytext()); }
