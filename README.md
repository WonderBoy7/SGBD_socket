# SGBD_socket
GRAMMAIRE DE L'ALGÈBRE RELATIONNELLE
 
  TAKE : projection (TOUS, OTHER COLUMN) eg : TAKE A IN R1
  IN : prendre les relations dans un fichier qui execute une operation ensembliste eg : IN R1 DIVID R2
  JOIN : jointure avec une autre relation eg : JOINDRE Matiere,idMatiere 
 WHERE : conditionner la recherche eg :WHERE idMatiere=2 .TAKE Matiere WHERE idMatiere=2 IN Matiere
  INSERT : inserer des données dans un relation eg : INSERT 4,Prog IN Matiere
  DELETE : supprimer des données dans une relation eg : DELETE idMatiere=3 IN Matiere
  
  UNION, DIVID, DIFFERENCE, PRODUIT : opperation binaire avec Relation operation et Relation
  eg : R2 UNION R1( misy problème kely) 
   IN R1 DIVID R2
  IN R2 DIFFERENCE R1
  IN R1 PRODUIT R2
