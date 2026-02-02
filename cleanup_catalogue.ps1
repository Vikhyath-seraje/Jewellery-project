$keepList = @("GE001", "GE002", "SE001", "SE002", "GR001", "GR002", "SR001", "GB001")
$articles = Invoke-RestMethod -Uri "http://localhost:8080/api/articles" -Method Get

foreach ($article in $articles) {
    if ($keepList -notcontains $article.articleId) {
        Write-Host "Deleting: $($article.articleId) (ID: $($article.id))"
        Invoke-RestMethod -Uri "http://localhost:8080/api/articles/$($article.id)" -Method Delete
    } else {
        Write-Host "Keeping: $($article.articleId)"
    }
}
