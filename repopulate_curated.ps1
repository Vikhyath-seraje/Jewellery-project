$articles = @(
    # Earrings (4)
    @{ articleId="GE001"; name="Elegant Gold Studs"; category="Earring"; weightGrams=1.5; makingCharges=500; wastagePercentage=5; metalType="GOLD"; purity="22K"; imageUrl="/images/catalogue/gold-earring-stud.png"; description="Minimalist 22K gold studs for daily wear." },
    @{ articleId="GE002"; name="Traditional Gold Jhumkas"; category="Earring"; weightGrams=8.0; makingCharges=1500; wastagePercentage=12; metalType="GOLD"; purity="22K"; imageUrl="/images/catalogue/gold-earring-jhumka.png"; description="Intricate traditional jhumkas for festive occasions." },
    @{ articleId="SE001"; name="Zircon Silver Studs"; category="Earring"; weightGrams=2.0; makingCharges=200; wastagePercentage=2; metalType="SILVER"; purity="FINE"; imageUrl="/images/catalogue/silver-earring-stud.png"; description="Sparkling zircon stones set in fine silver." },
    @{ articleId="SE002"; name="Oxidized Silver Jhumkas"; category="Earring"; weightGrams=15.0; makingCharges=600; wastagePercentage=5; metalType="SILVER"; purity="FINE"; imageUrl="/images/catalogue/silver-earring-jhumka.png"; description="Vintage oxidized silver jhumkas with tribal patterns." },

    # Rings (3)
    @{ articleId="GR001"; name="Minimalist Gold Band"; category="Ring"; weightGrams=3.0; makingCharges=400; wastagePercentage=3; metalType="GOLD"; purity="22K"; imageUrl="/images/catalogue/gold-ring-band.png"; description="Sleek and polished gold band." },
    @{ articleId="GR002"; name="Floral Design Gold Ring"; category="Ring"; weightGrams=5.0; makingCharges=800; wastagePercentage=8; metalType="GOLD"; purity="22K"; imageUrl="/images/catalogue/gold-ring-floral.png"; description="Exquisite gold ring with floral motifs." },
    @{ articleId="SR001"; name="Silver Cocktail Ring"; category="Ring"; weightGrams=5.0; makingCharges=300; wastagePercentage=3; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1605100804763-247f67b3557e?w=500"; description="Statement silver ring for parties." },

    # Bangles (1)
    @{ articleId="GB001"; name="Sleek Gold Bangle"; category="Bangle"; weightGrams=12.0; makingCharges=2000; wastagePercentage=10; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1611591437281-460bfbe1220a?w=500"; description="Elegant gold bangle for modern women." }
)

Write-Host "Re-populating with 8 items..."
foreach ($article in $articles) {
    $json = $article | ConvertTo-Json
    Invoke-RestMethod -Uri "http://localhost:8080/api/articles" -Method Post -Body $json -ContentType "application/json"
    Write-Host "Added: $($article.name)"
}
