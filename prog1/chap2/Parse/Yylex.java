package Parse;
import ErrorMsg.ErrorMsg;


class Yylex implements Lexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final char YYEOF = '\uFFFF';

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

   StringBuffer string = new StringBuffer();
   private int commentcount = 0;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 1;
	private final int YYINITIAL = 0;
	private final int COMMENT = 2;
	private final int IGNORE = 3;
	private final int yy_state_dtrans[] = {
		0,
		67,
		72,
		76
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private char yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_start () {
		++yychar;
		++yy_buffer_start;
	}
	private void yy_pushback () {
		--yy_buffer_end;
	}
	private void yy_mark_start () {
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
private int [][] unpackFromString(int size1, int size2, String st)
    {
      int colonIndex = -1;
      String lengthString;
      int sequenceLength = 0;
      int sequenceInteger = 0;
      int commaIndex;
      String workString;
      int res[][] = new int[size1][size2];
      for (int i= 0; i < size1; i++)
	for (int j= 0; j < size2; j++)
	  {
	    if (sequenceLength == 0) 
	      {	
		commaIndex = st.indexOf(',');
		if (commaIndex == -1)
		  workString = st;
		else
		  workString = st.substring(0, commaIndex);
		st = st.substring(commaIndex+1);
		colonIndex = workString.indexOf(':');
		if (colonIndex == -1)
		  {
		    res[i][j] = Integer.parseInt(workString);
		  }
		else 
		  {
		    lengthString = workString.substring(colonIndex+1);  
		    sequenceLength = Integer.parseInt(lengthString);
		    workString = workString.substring(0,colonIndex);
		    sequenceInteger = Integer.parseInt(workString);
		    res[i][j] = sequenceInteger;
		    sequenceLength--;
		  }
	      }
	    else 
	      {
		res[i][j] = sequenceInteger;
		sequenceLength--;
	      }
	  }
      return res;
    }
	private int yy_acpt[] = {
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR
	};
	private int yy_cmap[] = {
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 1, 2, 0, 1, 3, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		4, 0, 5, 0, 0, 0, 6, 0,
		7, 8, 9, 10, 11, 12, 13, 14,
		15, 15, 15, 15, 15, 15, 15, 15,
		15, 15, 16, 17, 18, 19, 20, 0,
		0, 21, 21, 21, 21, 21, 21, 21,
		21, 21, 21, 21, 21, 21, 21, 21,
		21, 21, 21, 21, 21, 21, 21, 21,
		21, 21, 21, 22, 23, 24, 25, 26,
		0, 27, 28, 29, 30, 31, 32, 21,
		33, 34, 21, 35, 36, 21, 37, 38,
		39, 21, 40, 41, 42, 43, 44, 45,
		21, 46, 21, 47, 48, 49, 0, 0
		
	};
	private int yy_rmap[] = {
		0, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 2, 3, 4,
		1, 5, 1, 6, 7, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 7,
		7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7,
		8, 1, 9, 1, 10, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1,
		11, 1, 1, 12, 13, 14, 13, 15,
		16, 17, 18, 19, 20, 21, 22, 23,
		24, 25, 26, 27, 28, 29, 30, 31,
		32, 33, 34, 35, 36, 37, 38, 39,
		40, 41, 42, 43, 44, 45, 46, 47,
		48, 49, 50, 51, 52, 53, 54, 55,
		56, 57, 58, 59 
	};
	private int yy_nxt[][] = unpackFromString(60,50,
"1:2,2,68,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,1,22,1:2,110,111,20,69,94,95,20,73,20,96,97,77,20:3,80,20,98,112,20,23,24,25,-1:59,26,-1:55,14,-1:53,27,-1:49,28,29,-1:48,30,-1:45,20,-1:5,20,-1:4,20:21,-1:3,48:2,-1:2,48,-1,48:17,-1,48:26,-1:2,49,-1:52,53,-1:31,54,-1:2,55,-1,56,-1:22,79,-1:34,48:2,49,50,48,51,48:17,52,48:26,-1:2,2,-1:62,20,-1:5,20,-1:4,20:12,31,20:8,-1:5,62,-1:47,57:2,2,70,57:5,74,57:4,78,57:35,-1:15,20,-1:5,20,-1:4,20:6,32,20:4,33,20:9,-1:17,58,-1:56,65,-1:5,65:20,-1:3,60,61,62,71,61,63,60:17,64,60,75,60:24,-1:15,20,-1:5,20,-1:4,20:6,34,20:14,-1:12,59,-1:55,81,-1:49,20,-1:5,20,-1:4,20:7,100,20:4,35,20:7,101,-1:18,66,-1:49,20,-1:5,20,-1:4,20:4,36,20:16,-1:18,20,-1:5,20,-1:4,20:14,37,20:6,-1:18,20,-1:5,20,-1:4,20:16,38,20:4,-1:18,20,-1:5,20,-1:4,20:10,39,20:10,-1:18,20,-1:5,20,-1:4,20:14,40,20:6,-1:18,20,-1:5,20,-1:4,20:5,41,20:15,-1:18,20,-1:5,20,-1:4,20:11,42,20:9,-1:18,20,-1:5,20,-1:4,20:5,43,20:15,-1:18,20,-1:5,20,-1:4,20:20,44,-1:18,20,-1:5,20,-1:4,20:9,45,20:11,-1:18,20,-1:5,20,-1:4,20:5,46,20:15,-1:18,20,-1:5,20,-1:4,20:11,47,20:9,-1:18,20,-1:5,20,-1:4,20:10,99,82,20:9,-1:18,20,-1:5,20,-1:4,20:12,83,20:4,115,20:3,-1:18,20,-1:5,20,-1:4,20:5,84,20:15,-1:18,20,-1:5,20,-1:4,20:8,85,20:12,-1:18,20,-1:5,20,-1:4,20,86,20:19,-1:18,20,-1:5,20,-1:4,20:15,87,20:5,-1:18,20,-1:5,20,-1:4,20:5,88,20:15,-1:18,20,-1:5,20,-1:4,20:13,89,20:7,-1:18,20,-1:5,20,-1:4,20,90,20:19,-1:18,20,-1:5,20,-1:4,20,91,20:19,-1:18,20,-1:5,20,-1:4,20:10,92,20:10,-1:18,20,-1:5,20,-1:4,20:12,93,20:8,-1:18,20,-1:5,20,-1:4,20:14,102,20:6,-1:18,20,-1:5,20,-1:4,20:5,103,20:15,-1:18,20,-1:5,20,-1:4,20:8,104,20:12,-1:18,20,-1:5,20,-1:4,20:8,105,20:12,-1:18,20,-1:5,20,-1:4,20:14,106,20:6,-1:18,20,-1:5,20,-1:4,20:14,107,20:6,-1:18,20,-1:5,20,-1:4,20:7,108,20:13,-1:18,20,-1:5,20,-1:4,20:16,109,20:4,-1:18,20,-1:5,20,-1:4,20:3,113,20:17,-1:18,20,-1:5,20,-1:4,20:11,114,20:9,-1:3");
	public java_cup.runtime.Symbol nextToken ()
		throws java.io.IOException {
		char yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			if (YYEOF != yy_lookahead) {
				yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YYEOF == yy_lookahead && true == yy_initial) {

	{
	if(commentcount != 0) { err("Unclosed Comments");}
	/*if (!stringcomplete) report error err(string_eof) Unclosed string before EOF*/
	 return tok(sym.EOF, null);
        }
				}
				else if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_to_mark();
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_pushback();
					}
					if (0 != (YY_START & yy_anchor)) {
						yy_move_start();
					}
					switch (yy_last_accept_state) {
					case 1:
						{ err("Illegal character: " + yytext()); }
					case -2:
						break;
					case 2:
						{newline();}
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{string.setLength(0); yybegin(STRING);}
					case -5:
						break;
					case 5:
						{return tok(sym.AND, null);}
					case -6:
						break;
					case 6:
						{return tok(sym.LPAREN, null);}
					case -7:
						break;
					case 7:
						{return tok(sym.RPAREN, null);}
					case -8:
						break;
					case 8:
						{return tok(sym.TIMES, null);}
					case -9:
						break;
					case 9:
						{return tok(sym.PLUS, null);}
					case -10:
						break;
					case 10:
						{return tok(sym.COMMA, null);}
					case -11:
						break;
					case 11:
						{return tok(sym.MINUS, null);}
					case -12:
						break;
					case 12:
						{return tok(sym.DOT, null);}
					case -13:
						break;
					case 13:
						{return tok(sym.DIVIDE, null);}
					case -14:
						break;
					case 14:
						{return tok(sym.INT, new Integer(yytext()));}
					case -15:
						break;
					case 15:
						{return tok(sym.COLON, null);}
					case -16:
						break;
					case 16:
						{return tok(sym.SEMICOLON, null);}
					case -17:
						break;
					case 17:
						{return tok(sym.LT, null);}
					case -18:
						break;
					case 18:
						{return tok(sym.EQ, null);}
					case -19:
						break;
					case 19:
						{return tok(sym.GT, null);}
					case -20:
						break;
					case 20:
						{return tok(sym.ID, yytext());}
					case -21:
						break;
					case 21:
						{return tok(sym.RBRACK, null);}
					case -22:
						break;
					case 22:
						{return tok(sym.LBRACK, null);}
					case -23:
						break;
					case 23:
						{return tok(sym.RBRACE, null);}
					case -24:
						break;
					case 24:
						{return tok(sym.OR, null);}
					case -25:
						break;
					case 25:
						{return tok(sym.LBRACE, null);}
					case -26:
						break;
					case 26:
						{yybegin(COMMENT); commentcount += 1;}
					case -27:
						break;
					case 27:
						{return tok(sym.ASSIGN, null);}
					case -28:
						break;
					case 28:
						{return tok(sym.LE, null);}
					case -29:
						break;
					case 29:
						{return tok(sym.NEQ, null);}
					case -30:
						break;
					case 30:
						{return tok(sym.GE, null);}
					case -31:
						break;
					case 31:
						{return tok(sym.DO, null);}
					case -32:
						break;
					case 32:
						{return tok(sym.IF, null);}
					case -33:
						break;
					case 33:
						{return tok(sym.IN, null);}
					case -34:
						break;
					case 34:
						{return tok(sym.OF, null);}
					case -35:
						break;
					case 35:
						{return tok(sym.TO, null);}
					case -36:
						break;
					case 36:
						{return tok(sym.END, null);}
					case -37:
						break;
					case 37:
						{return tok(sym.FOR, null);}
					case -38:
						break;
					case 38:
						{return tok(sym.LET, null);}
					case -39:
						break;
					case 39:
						{return tok(sym.NIL, null);}
					case -40:
						break;
					case 40:
						{return tok(sym.VAR, null);}
					case -41:
						break;
					case 41:
						{return tok(sym.ELSE, null);}
					case -42:
						break;
					case 42:
						{return tok(sym.THEN, null);}
					case -43:
						break;
					case 43:
						{return tok(sym.TYPE, null);}
					case -44:
						break;
					case 44:
						{return tok(sym.ARRAY, null);}
					case -45:
						break;
					case 45:
						{return tok(sym.BREAK, null);}
					case -46:
						break;
					case 46:
						{return tok(sym.WHILE, null);}
					case -47:
						break;
					case 47:
						{return tok(sym.FUNCTION, null);}
					case -48:
						break;
					case 48:
						{ string.append( yytext() );}
					case -49:
						break;
					case 49:
						{ err("Unclosed Strings" + " \"" + string.toString() + "\""); yybegin(YYINITIAL); newline(); return tok(sym.STRING,  string.toString());}
					case -50:
						break;
					case 50:
						{string.append(yytext());}
					case -51:
						break;
					case 51:
						{ yybegin(YYINITIAL); return tok(sym.STRING, string.toString());}
					case -52:
						break;
					case 52:
						{ yybegin(IGNORE);}
					case -53:
						break;
					case 53:
						{string.append('\"');}
					case -54:
						break;
					case 54:
						{string.append('\n');}
					case -55:
						break;
					case 55:
						{string.append('\r');}
					case -56:
						break;
					case 56:
						{string.append('\t');}
					case -57:
						break;
					case 57:
						{}
					case -58:
						break;
					case 58:
						{ commentcount -= 1; if(commentcount == 0) { yybegin(YYINITIAL);}}
					case -59:
						break;
					case 59:
						{ commentcount += 1;}
					case -60:
						break;
					case 60:
						{ err("Escape Sequence Illegal"); yybegin(STRING);}
					case -61:
						break;
					case 61:
						{}
					case -62:
						break;
					case 62:
						{ newline(); yybegin(STRING);}
					case -63:
						break;
					case 63:
						{ string.append('\"'); yybegin(STRING);}
					case -64:
						break;
					case 64:
						{ string.append('\\'); yybegin(STRING);}
					case -65:
						break;
					case 65:
						{string.append((char)(yytext().charAt(1)-'A'+1));yybegin(STRING);}
					case -66:
						break;
					case 66:
						{int i = Integer.parseInt(yytext()); if (i < 128) {string.append((char)i);} else {err("Not in ascii range");} yybegin(STRING);}
					case -67:
						break;
					case 68:
						{ err("Illegal character: " + yytext()); }
					case -68:
						break;
					case 69:
						{return tok(sym.ID, yytext());}
					case -69:
						break;
					case 70:
						{}
					case -70:
						break;
					case 71:
						{ err("Escape Sequence Illegal"); yybegin(STRING);}
					case -71:
						break;
					case 73:
						{return tok(sym.ID, yytext());}
					case -72:
						break;
					case 74:
						{}
					case -73:
						break;
					case 75:
						{ err("Escape Sequence Illegal"); yybegin(STRING);}
					case -74:
						break;
					case 77:
						{return tok(sym.ID, yytext());}
					case -75:
						break;
					case 78:
						{}
					case -76:
						break;
					case 80:
						{return tok(sym.ID, yytext());}
					case -77:
						break;
					case 82:
						{return tok(sym.ID, yytext());}
					case -78:
						break;
					case 83:
						{return tok(sym.ID, yytext());}
					case -79:
						break;
					case 84:
						{return tok(sym.ID, yytext());}
					case -80:
						break;
					case 85:
						{return tok(sym.ID, yytext());}
					case -81:
						break;
					case 86:
						{return tok(sym.ID, yytext());}
					case -82:
						break;
					case 87:
						{return tok(sym.ID, yytext());}
					case -83:
						break;
					case 88:
						{return tok(sym.ID, yytext());}
					case -84:
						break;
					case 89:
						{return tok(sym.ID, yytext());}
					case -85:
						break;
					case 90:
						{return tok(sym.ID, yytext());}
					case -86:
						break;
					case 91:
						{return tok(sym.ID, yytext());}
					case -87:
						break;
					case 92:
						{return tok(sym.ID, yytext());}
					case -88:
						break;
					case 93:
						{return tok(sym.ID, yytext());}
					case -89:
						break;
					case 94:
						{return tok(sym.ID, yytext());}
					case -90:
						break;
					case 95:
						{return tok(sym.ID, yytext());}
					case -91:
						break;
					case 96:
						{return tok(sym.ID, yytext());}
					case -92:
						break;
					case 97:
						{return tok(sym.ID, yytext());}
					case -93:
						break;
					case 98:
						{return tok(sym.ID, yytext());}
					case -94:
						break;
					case 99:
						{return tok(sym.ID, yytext());}
					case -95:
						break;
					case 100:
						{return tok(sym.ID, yytext());}
					case -96:
						break;
					case 101:
						{return tok(sym.ID, yytext());}
					case -97:
						break;
					case 102:
						{return tok(sym.ID, yytext());}
					case -98:
						break;
					case 103:
						{return tok(sym.ID, yytext());}
					case -99:
						break;
					case 104:
						{return tok(sym.ID, yytext());}
					case -100:
						break;
					case 105:
						{return tok(sym.ID, yytext());}
					case -101:
						break;
					case 106:
						{return tok(sym.ID, yytext());}
					case -102:
						break;
					case 107:
						{return tok(sym.ID, yytext());}
					case -103:
						break;
					case 108:
						{return tok(sym.ID, yytext());}
					case -104:
						break;
					case 109:
						{return tok(sym.ID, yytext());}
					case -105:
						break;
					case 110:
						{return tok(sym.ID, yytext());}
					case -106:
						break;
					case 111:
						{return tok(sym.ID, yytext());}
					case -107:
						break;
					case 112:
						{return tok(sym.ID, yytext());}
					case -108:
						break;
					case 113:
						{return tok(sym.ID, yytext());}
					case -109:
						break;
					case 114:
						{return tok(sym.ID, yytext());}
					case -110:
						break;
					case 115:
						{return tok(sym.ID, yytext());}
					case -111:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
					}
				}
			}
		}
	}
}
