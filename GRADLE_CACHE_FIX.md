# Fix pour l'erreur jlink.exe

## Solution 1: Nettoyer le cache Gradle

Exécutez ces commandes dans le terminal (dans le dossier android) :

```bash
# Nettoyer le projet
./gradlew clean

# Supprimer le cache Gradle corrompu
rm -rf ~/.gradle/caches/transforms-3

# Ou sur Windows PowerShell:
Remove-Item -Recurse -Force $env:USERPROFILE\.gradle\caches\transforms-3
```

## Solution 2: Invalider les caches dans Android Studio

1. File → Invalidate Caches / Restart
2. Sélectionnez "Invalidate and Restart"
3. Attendez le redémarrage

## Solution 3: Vérifier la version Java

Assurez-vous d'utiliser Java 17 (requis pour Android Gradle Plugin 8.2.0)

Dans Android Studio:
- File → Project Structure → SDK Location
- Vérifiez que JDK location pointe vers Java 17

## Solution 4: Mettre à jour Gradle

Si le problème persiste, essayez de mettre à jour Gradle à 8.4 ou 8.5.

