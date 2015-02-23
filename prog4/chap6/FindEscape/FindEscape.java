package FindEscape;

public class FindEscape {
  Symbol.Table escEnv = new Symbol.Table(); // escEnv maps Symbol to Escape

  public FindEscape(Absyn.Exp e) { traverseExp(0, e);  }

  void traverseVar(int depth, Absyn.Var v) {
	  if(v instanceof Absyn.SimpleVar)
		  traverseVar (depth, (Absyn.SimpleVar)v);
	  else if(v instanceof Absyn.FieldVar)
		  traverseVar(depth, (Absyn.FieldVar)v);
	  else if(v instanceof Absyn.SubscriptVar)
		  traverseVar(depth, (Absyn.SubscriptVar)v);
	  else throw new Error("transvar");
  }
  void traverseVar(int depth, Absyn.SimpleVar v)
  {
	  Escape escape = (Escape)escEnv.get(v.name);
	  if((escape != null) && (depth > escape.depth))
		  escape.setEscape();
  }
  
  void traverseVar(int depth, Absyn.FieldVar v){
	  traverseVar(depth, v.var);
  }
  
  void traverseVar(int depth, Absyn.SubscriptVar v){
	  traverseVar(depth, v.var);
  }

  void traverseExp(int depth, Absyn.Exp e) {
	  if (( e instanceof Absyn.NilExp)
			  || (e instanceof Absyn.IntExp)
			  || (e instanceof Absyn.StringExp)
			  || (e instanceof Absyn.RecordExp)
			  || (e instanceof Absyn.BreakExp))
		  return;
	  if (e instanceof Absyn.VarExp)
		  traverseExp(depth, (Absyn.VarExp)e);
	  else if (e instanceof Absyn.CallExp)
		  traverseExp(depth, (Absyn.CallExp)e);
	  else if (e instanceof Absyn.OpExp)
		  traverseExp(depth, (Absyn.OpExp)e);
	  else if (e instanceof Absyn.SeqExp)
		  traverseExp(depth, (Absyn.SeqExp)e);
	  else if (e instanceof Absyn.AssignExp)
		  traverseExp(depth, (Absyn.AssignExp)e);
	  else if (e instanceof Absyn.IfExp)
		  traverseExp(depth, (Absyn.IfExp)e);
	  else if (e instanceof Absyn.WhileExp)
		  traverseExp(depth, (Absyn.WhileExp)e);
	  else if (e instanceof Absyn.ForExp)
		  traverseExp(depth, (Absyn.ForExp)e);
	  else if (e instanceof Absyn.LetExp)
		  traverseExp(depth, (Absyn.LetExp)e);
	  else if (e instanceof Absyn.ArrayExp)
		  traverseExp(depth, (Absyn.ArrayExp)e);
	  else throw new Error("transExp");
  }

  void traverseExp(int depth, Absyn.VarExp e){
	  traverseVar(depth, e.var);
  }
  
  void traverseExp(int depth, Absyn.CallExp e){
	  for (Absyn.ExpList arg=e.args; arg!= null; arg=arg.tail)
		  traverseExp(depth, arg.head);
  }
  
  void traverseExp(int depth, Absyn.OpExp e){
	  traverseExp(depth, e.left);
	  traverseExp(depth, e.right);
  }
  
  void traverseExp(int depth, Absyn.SeqExp e){
	  for (Absyn.ExpList exp = e.list; exp !=null; exp = exp.tail)
		  traverseExp(depth, exp.head);
  }
  
  void traverseExp(int depth, Absyn.AssignExp e){
	  traverseVar(depth, e.var);
	  traverseExp(depth, e.exp);
  }
  
  void traverseExp(int depth, Absyn.IfExp e){
	  traverseExp(depth, e.test);
	  traverseExp(depth, e.thenclause);
	  if (e.elseclause != null)
		  traverseExp(depth, e.elseclause);
  }
  
  void traverseExp(int depth, Absyn.WhileExp e){
	  traverseExp(depth, e.test);
	  traverseExp(depth, e.body);
  }
  
  void traverseExp(int depth, Absyn.ForExp e){
	  escEnv.beginScope();
	  traverseDec(depth, e.var);
	  traverseExp(depth, e.hi);
	  traverseExp(depth, e.body);
	  escEnv.endScope();
  }
  
  void traverseExp(int depth, Absyn.BreakExp e){
	  return;
  }
  void traverseExp(int depth, Absyn.LetExp e){
	  escEnv.beginScope();
	  for(Absyn.DecList d = e.decs; d!= null; d=d.tail)
		  traverseDec(depth, d.head);
	  traverseExp(depth, e.body);
	  escEnv.endScope();
  }
  
  void traverseExp (int depth, Absyn.ArrayExp e){
	  traverseExp(depth, e.size);
	  traverseExp(depth, e.init);
  }
  
 
  void traverseDec(int depth, Absyn.Dec d) {
	  if (d instanceof Absyn.VarDec)
		  traverseDec(depth, (Absyn.VarDec)d);
	  else if (d instanceof Absyn.FunctionDec)
		  traverseDec(depth, (Absyn.FunctionDec)d);
	  else if (!(d instanceof Absyn.TypeDec))
		  throw new Error("transDec");
  }
  
  void traverseDec(int depth, Absyn.VarDec d){
	  escEnv.put(d.name, new VarEscape(depth, d));
  }
  
  void traverseDec (int depth, Absyn.FunctionDec d){
	  for (Absyn.FunctionDec dd= d; dd!= null; dd=dd.next){
		  escEnv.beginScope();
		  for(Absyn.FieldList p = dd.params; p != null; p=p.tail)
			  escEnv.put(p.name, new FormalEscape(depth+1, p));
		  traverseExp(depth+1, dd.body);
		  escEnv.endScope();
	  }
  }

}
