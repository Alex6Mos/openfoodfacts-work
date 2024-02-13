Pour utiliser cette application, suivez les étapes suivantes :

## Installer PostgreSQL
Assurez-vous d'avoir PostgreSQL installé sur votre système. Vous pouvez le télécharger à partir du site officiel de PostgreSQL : postgresql.org.

## Configurer la base de données
Créez une base de données PostgreSQL nommée "openfoodfacts" en utilisant la commande suivante dans votre interface PostgreSQL :

```sql
CREATE DATABASE openfoodfacts;
```
## Configurer les informations de connexion

Dans le fichier DatabaseConfig.java, modifiez les constantes JDBC_URL, USERNAME et PASSWORD selon les informations de connexion à votre base de données PostgreSQL :
```java
public static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/openfoodfacts";
public static final String USERNAME = "postgres";
public static final String PASSWORD = "postgres";
```

## Exécuter le programme
Une fois la base de données configurée et les informations de connexion mises à jour, exécutez votre programme Java.

## Configuration de la VM
Assurez-vous d'ajouter les paramètres de VM suivants lors de l'exécution de votre application Java pour garantir une exécution correcte :

```
--add-opens=java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.lang.invoke=ALL-UNNAMED
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED
--add-opens=java.base/java.io=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=java.base/java.nio=ALL-UNNAMED
--add-opens=java.base/java.util=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED
--add-opens=java.base/sun.nio.ch=ALL-UNNAMED
--add-opens=java.base/sun.nio.cs=ALL-UNNAMED
--add-opens=java.base/sun.security.action=ALL-UNNAMED
--add-opens=java.base/sun.util.calendar=ALL-UNNAMED
--add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED
```

En suivant ces étapes, vous devriez pouvoir exécuter le programme avec succès et interagir avec la base de données PostgreSQL configurée.

## Instructions d'utilisation

Ce projet propose un générateur de repas équilibrés en fonction de différents régimes alimentaires. Voici comment l'utiliser :

## 1. Configuration des régimes

- Le fichier `RegimesFactory` contient la configuration des différents régimes alimentaires disponibles : "Hypoglycémique", "Fodmap", et "Mediterranean".

## 2. Utilisateur tests

- Utilisez la méthode `creerEtEnregistrerUtilisateur` de la classe `UtilisateursFactory` pour créer et enregistrer des utilisateurs de test avec leur IMC et le régime alimentaire choisi. Trois utilisateurs de test ont déjà été créés avec trois régimes différents.

Exemple :

```java
String hypoRegime = "Hypoglycémique";
String fodmapRegime = "Fodmap";
String mediterraneanRegime = "Mediterranean";

int hypoRegimeID = RegimesFactory.getRegimeIdByName(connection, hypoRegime);
int fodmapRegimeID = RegimesFactory.getRegimeIdByName(connection, fodmapRegime);
int mediterraneanRegimeID = RegimesFactory.getRegimeIdByName(connection, mediterraneanRegime);

// Utilisateurs de test déjà enregistrés
creerEtEnregistrerUtilisateur(connection, "Jean", 175, Sexe.Homme, 80 , hypoRegimeID);
creerEtEnregistrerUtilisateur(connection, "Jane", 155, Sexe.Femme, 90 , fodmapRegimeID);
creerEtEnregistrerUtilisateur(connection, "Alice", 196, Sexe.Femme, 50 , mediterraneanRegimeID);
```
## 3. Traitement des données

- Dans la méthode `processData` du fichier principal, spécifiez le régime alimentaire à tester en passant le nom du régime à la méthode `generateBalancedMeals` de la classe `MealGenerator` ainsi que le if principal qui vérifie si il est >0

Exemple :

```java
String mediterraneanRegime = "Mediterranean";
int mediterraneanRegimeID = RegimesFactory.getRegimeIdByName(connection, mediterraneanRegime);
creerEtEnregistrerUtilisateur(connection, "Jean", 196, Sexe.Femme, 50 , mediterraneanRegimeID);

if (mediterraneanRegimeID > 0) {
    float imc = UtilisateursFactory.getIMC(connection, "Jean");
    if (imc != -1) {
        List<Jour> joursDeLaSemaine = JourFactory.getAllJours(connection);
        List<RepaParJour> repasSemaine = MealGenerator.generateBalancedMeals(dataCleaned, connection, imc, mediterraneanRegime);
        for (RepaParJour repaJour : repasSemaine) {
            // Traitez les repas générés ici
        }
    } else {
        System.out.println("IMC introuvable pour l'utilisateur Jean.");
    }
} else {
    System.out.println("Régime spécifié introuvable.");
}
