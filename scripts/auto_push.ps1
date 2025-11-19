param(
    [string]$Message = "",
    [switch]$All
)

# Ensure we're in a git repository
$gitTop = & git rev-parse --show-toplevel 2>$null
if (-not $gitTop) {
    Write-Error "This directory is not inside a git repository."
    exit 1
}

Set-Location $gitTop

# Determine current branch
$branch = (& git rev-parse --abbrev-ref HEAD).Trim()
if (-not $branch) {
    Write-Error "Failed to determine current branch."
    exit 1
}

if (-not $Message) {
    $Message = "Auto push: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
}

# Check working tree changes
$status = & git status --porcelain

if ($status) {
    Write-Host "Staging changes..."
    & git add -A

    Write-Host "Committing with message:`n  $Message"
    $commit = & git commit -m "$Message" 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Commit failed: $commit"
        exit $LASTEXITCODE
    }
} else {
    Write-Host "No working-tree changes to commit."
}

Write-Host "Pushing to origin/$branch..."
$push = & git push origin $branch 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Error "Push failed: $push"
    exit $LASTEXITCODE
}

Write-Host "Push completed successfully."
