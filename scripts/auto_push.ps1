param(
    [string]$Message = "",
    [string]$OpenFile = "",
    [switch]$UseOpenFile
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

# If requested, determine commit message from the open file or most-recent file
if ($UseOpenFile) {
    if ($OpenFile) {
        $fileName = Split-Path -Path $OpenFile -Leaf
    } else {
        # pick the most recently modified file in the repo (excluding .git)
        try {
            $recent = Get-ChildItem -Path $gitTop -Recurse -File -ErrorAction SilentlyContinue |
                Where-Object { $_.FullName -notmatch '\\.git\\' } |
                Sort-Object LastWriteTime -Descending |
                Select-Object -First 1
        } catch {
            $recent = $null
        }
        if ($recent) { $fileName = $recent.Name } else { $fileName = $null }
    }

    if ($fileName) {
        $Message = $fileName
        Write-Host "Using commit message from file: $Message"
    } else {
        Write-Host "Could not determine an open file for commit message; falling back to default message."
    }
}

# Stage changes using 'git add .'
$statusBefore = & git status --porcelain
Write-Host "Staging changes (git add .) ..."
& git add .

# Check working tree changes after staging
$status = & git status --porcelain

if ($status) {
    if (-not $Message) {
        $Message = "Auto push: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
    }

    Write-Host "Committing with message:`n  $Message"
    $commit = & git commit -m "$Message" 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Commit failed: $commit"
        exit $LASTEXITCODE
    }
} else {
    Write-Host "No working-tree changes to commit after staging."
}

Write-Host "Pushing to origin/$branch..."
$push = & git push origin $branch 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Error "Push failed: $push"
    exit $LASTEXITCODE
}

Write-Host "Push completed successfully."
