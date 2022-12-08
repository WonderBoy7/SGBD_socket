package request;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import exception.Exit;
import exception.NotSelectRequestException;
import file.Save;
import syntax.*;
import relation.*;


public class Request {
    
    List<Syntax> dictionnaire = new ArrayList<Syntax>();
    List<Syntax> ensemble = new ArrayList<Syntax>();
    Relation depart;

    public void setDepart(Relation depart) {
        this.depart = depart;
    }
    
    public void setDictionnaire(List<Syntax> dictionnaire) {
        this.dictionnaire = dictionnaire;
    }

    public Request() throws Exception {
        initDictionnaire();
    }

    public void initDictionnaire() throws Exception {
        dictionnaire.add(new Syntax("TAKE", Relation.class.getMethod("projection", String.class)));
        dictionnaire.add(new Syntax("IN", Request.class.getMethod("findRelation", String.class)));
        dictionnaire.add(new Syntax("JOIN", Relation.class.getMethod("jointure", String.class)));
        dictionnaire.add(new Syntax("WHERE", Relation.class.getMethod("selection", String.class)));
        dictionnaire.add(new Syntax("INSERT", Relation.class.getMethod("insert", String.class)));
        dictionnaire.add(new Syntax("DELETE", Relation.class.getMethod("supprimer", String.class)));
        ensemble.add(new Syntax("UNION", Relation.class.getMethod("union", String.class)));
        ensemble.add(new Syntax("DIVID", Relation.class.getMethod("division", String.class)));
        ensemble.add(new Syntax("DIFFERENCE", Relation.class.getMethod("difference", String.class)));
        ensemble.add(new Syntax("PRODUIT", Relation.class.getMethod("multiplication", String.class)));
    }
    
    public Relation request(String request) throws Exception {
        if (request.compareTo("exit") == 0)
            throw new Exit("Exit program database");
        Vector<Syntax> syntaxs = splitToSyntax(request);
        for (int i = syntaxs.size() - 1; i >= 0; i--) {
            if (syntaxs.get(i).getRequest().compareTo("IN") != 0 && syntaxs.get(i).getRequest().compareTo("JOIN") != 0)
                setDepart(syntaxs.get(i).execute(depart));
        }
        if (syntaxs.get(0).getRequest().compareTo("INSERT") == 0 || syntaxs.get(0).getRequest().compareTo("DELETE") == 0)
            throw new NotSelectRequestException("INSERER ou SUPPRIMER request");
        return depart;
    }

    public static Syntax find(String request, List<Syntax> dico) {
        for (Syntax syntax : dico) {
            if (syntax.getRequest().compareTo(request) == 0)
                return syntax;
        }
        return null;
    }

    public static Relation findRelation(String name) throws Exception {
        return (name.split(" ").length > 1) ? new Request().requestEnsemble(name) : Save.input(name);
    }

    public Relation requestEnsemble(String request) throws Exception {
        String[] requests = request.split(" ");
        Syntax syntax = find(requests[1], ensemble);
        syntax.setValue(requests[2]);
        depart = Save.input(requests[0]);
        return syntax.execute(depart);
    }

    public Vector<Syntax> splitToSyntax(String request) throws Exception {
        String[] requests = request.split(" "); // requête diviser per un espace
        Vector<Syntax> result = new Vector<Syntax>();
        String value = "";
        Syntax syntax = find(requests[0], dictionnaire);
        for (int i = 1; i < requests.length; i++) {
            Syntax check = find(requests[i], dictionnaire);
            if (check != null) {
                setValueSyntax(syntax, value, result);
                syntax = check;
                value = "";
            } else {
                value += (value == "") ? requests[i] : " " + requests[i];
            }
        }
        setValueSyntax(syntax, value, result);
        return result;
    }

    public void setValueSyntax(Syntax syntax, String value, Vector<Syntax> result) throws Exception {
        syntax.setValue(value);
        if (syntax.getRequest().compareTo("IN") == 0 || syntax.getRequest().compareTo("JOIN") == 0) 
            setDepart((Relation) syntax.execute(depart));
        result.add(syntax);
    }
          
    
}