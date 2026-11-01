# Run Mail Cleaner Function locally using the Google Cloud Functions Framework
param(
    [int]$Port = 8080,
    [string]$Target = " "
)

Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " Starting Google Cloud Function locally on port $Port" -ForegroundColor Cyan
Write-Host " Function Target: $Target" -ForegroundColor Cyan
Write-Host " Endpoint: http://localhost:$Port/" -ForegroundColor Green
Write-Host " Press Ctrl+C to stop." -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan

mvn compile function:run "-Drun.functionTarget=$Target" "-Drun.port=$Port"
