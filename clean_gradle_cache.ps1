# Script pour nettoyer le cache Gradle corrompu
Write-Host "Nettoyage du cache Gradle..." -ForegroundColor Cyan
Write-Host ""

# Supprimer le cache transforms-3 corrompu
$cachePath = "$env:USERPROFILE\.gradle\caches\transforms-3"
if (Test-Path $cachePath) {
    Write-Host "Suppression de transforms-3..." -ForegroundColor Yellow
    Remove-Item -Recurse -Force $cachePath -ErrorAction SilentlyContinue
    Write-Host "✓ Cache transforms-3 supprimé" -ForegroundColor Green
} else {
    Write-Host "Cache transforms-3 non trouvé" -ForegroundColor Gray
}

# Nettoyer le projet
Write-Host ""
Write-Host "Nettoyage du projet..." -ForegroundColor Yellow
if (Test-Path ".\gradlew.bat") {
    .\gradlew.bat clean
} else {
    Write-Host "gradlew.bat non trouvé. Exécutez 'gradlew clean' manuellement." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "✓ Nettoyage terminé!" -ForegroundColor Green
Write-Host ""
Write-Host "Prochaines étapes:" -ForegroundColor Cyan
Write-Host "1. Dans Android Studio: File → Invalidate Caches / Restart" -ForegroundColor White
Write-Host "2. Rebuild le projet" -ForegroundColor White

