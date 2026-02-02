$articles = @(
    # Earrings (4 Unique)
    @{ articleId="GE001"; name="Elegant Gold Studs"; category="Earring"; weightGrams=1.5; makingCharges=500; wastagePercentage=5; metalType="GOLD"; purity="22K"; imageUrl="/images/catalogue/gold-earring-stud.png"; description="Minimalist 22K gold studs for daily wear." },
    @{ articleId="GE002"; name="Traditional Gold Jhumkas"; category="Earring"; weightGrams=8.0; makingCharges=1500; wastagePercentage=12; metalType="GOLD"; purity="22K"; imageUrl="/images/catalogue/gold-earring-jhumka.png"; description="Intricate traditional jhumkas for festive occasions." },
    @{ articleId="SE001"; name="Zircon Silver Studs"; category="Earring"; weightGrams=2.0; makingCharges=200; wastagePercentage=2; metalType="SILVER"; purity="FINE"; imageUrl="/images/catalogue/silver-earring-stud.png"; description="Sparkling zircon stones set in fine silver." },
    @{ articleId="SE002"; name="Oxidized Silver Jhumkas"; category="Earring"; weightGrams=15.0; makingCharges=600; wastagePercentage=5; metalType="SILVER"; purity="FINE"; imageUrl="/images/catalogue/silver-earring-jhumka.png"; description="Vintage oxidized silver jhumkas with tribal patterns." },

    # Rings (4 Unique)
    @{ articleId="GR001"; name="Minimalist Gold Band"; category="Ring"; weightGrams=3.0; makingCharges=400; wastagePercentage=3; metalType="GOLD"; purity="22K"; imageUrl="/images/catalogue/gold-ring-band.png"; description="Sleek and polished gold band." },
    @{ articleId="GR002"; name="Floral Design Gold Ring"; category="Ring"; weightGrams=5.0; makingCharges=800; wastagePercentage=8; metalType="GOLD"; purity="22K"; imageUrl="/images/catalogue/gold-ring-floral.png"; description="Exquisite gold ring with floral motifs." },
    @{ articleId="SR001"; name="Silver Cocktail Ring"; category="Ring"; weightGrams=5.0; makingCharges=300; wastagePercentage=3; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1605100804763-247f67b3557e?w=500"; description="Statement silver ring for parties." },
    @{ articleId="SR002"; name="Silver Minimalist Band"; category="Ring"; weightGrams=3.0; makingCharges=150; wastagePercentage=2; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1603561591411-071c4f723935?w=500"; description="Classic silver band for everyday elegance." },

    # Bangles (4 Unique)
    @{ articleId="GB001"; name="Sleek Gold Bangle"; category="Bangle"; weightGrams=12.0; makingCharges=2000; wastagePercentage=10; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1611591437281-460bfbe1220a?w=500"; description="Elegant gold bangle for modern women." },
    @{ articleId="GB002"; name="Antique Gold Kangan"; category="Bangle"; weightGrams=35.0; makingCharges=5000; wastagePercentage=15; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1630019852942-f89202989a51?w=500"; description="Heavy antique kangan with detailed craftsmanship." },
    @{ articleId="SB001"; name="Oxidized Silver Kada"; category="Bangle"; weightGrams=30.0; makingCharges=800; wastagePercentage=5; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1535633302723-9993d573c01e?w=500"; description="Bold oxidized silver kada for a rustic look." },
    @{ articleId="SB002"; name="Engraved Silver Bangle"; category="Bangle"; weightGrams=15.0; makingCharges=400; wastagePercentage=3; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1573408301185-9146fe624df0?w=500"; description="Fine silver bangle with delicate engravings." },

    # Necklaces (4 Unique)
    @{ articleId="GN001"; name="Gold Petite Necklace"; category="Necklace"; weightGrams=6.0; makingCharges=1200; wastagePercentage=7; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1599643477877-530eb83abc8e?w=500"; description="Dainty gold chain with a small pendant." },
    @{ articleId="GN002"; name="Bridal Gold Necklace Set"; category="Necklace"; weightGrams=45.0; makingCharges=8000; wastagePercentage=18; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1620960960009-8af7c369dd44?w=500"; description="Grand bridal necklace set for special occasions." },
    @{ articleId="SN001"; name="Silver Heart Necklace"; category="Necklace"; weightGrams=5.0; makingCharges=500; wastagePercentage=4; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1512163143273-bde0e3cc7407?w=500"; description="Elegant silver necklace with a heart pendant." },
    @{ articleId="SN002"; name="Vintage Silver Choker"; category="Necklace"; weightGrams=60.0; makingCharges=2000; wastagePercentage=8; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1601121141461-9d6647bca1ed?w=500"; description="Handcrafted vintage silver choker." },

    # Chains (4 Unique)
    @{ articleId="GC001"; name="Classic Gold Box Chain"; category="Chain"; weightGrams=10.0; makingCharges=1000; wastagePercentage=6; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1626497748470-284d81f5f470?w=500"; description="Sturdy 22K gold box chain." },
    @{ articleId="GC002"; name="Shimmering Gold Snake Chain"; category="Chain"; weightGrams=15.0; makingCharges=1500; wastagePercentage=8; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1616464916356-3a777b2b60b1?w=500"; description="Flexible and shiny gold snake chain." },
    @{ articleId="SC001"; name="Heavy Silver Curb Chain"; category="Chain"; weightGrams=20.0; makingCharges=600; wastagePercentage=4; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1616110910014-9bdef8f34698?w=500"; description="Masculine 925 silver curb chain." },
    @{ articleId="SC002"; name="Silver Figaro Chain"; category="Chain"; weightGrams=15.0; makingCharges=450; wastagePercentage=3; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1563283622-0d674f177fc1?w=500"; description="Classic silver figaro chain design." },

    # Bracelets (4 Unique)
    @{ articleId="GBT001"; name="Men's Gold Link Bracelet"; category="Bracelet"; weightGrams=25.0; makingCharges=3000; wastagePercentage=12; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1611085583191-a3b13ef2442b?w=500"; description="Solid 22K gold link bracelet for men." },
    @{ articleId="GBT002"; name="Gold Charm Bracelet"; category="Bracelet"; weightGrams=5.0; makingCharges=1000; wastagePercentage=8; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1506630448388-4e683c67ddb0?w=500"; description="Dainty gold bracelet with various charms." },
    @{ articleId="SBT001"; name="Silver Multi-Charm Bracelet"; category="Bracelet"; weightGrams=8.0; makingCharges=400; wastagePercentage=3; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1549439602-43ebca2327af?w=500"; description="925 silver bracelet with stylish charms." },
    @{ articleId="SBT002"; name="Men's Silver Curb Bracelet"; category="Bracelet"; weightGrams=30.0; makingCharges=900; wastagePercentage=5; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1607693962630-d38392cf99a1?w=500"; description="Heavy silver curb bracelet for men." },

    # Other (4 Unique)
    @{ articleId="GO001"; name="Gold Lakshmi Pendant"; category="Other"; weightGrams=2.0; makingCharges=400; wastagePercentage=5; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1617038221804-0c5871f3014a?w=500"; description="Traditional gold pendant with Goddess Lakshmi motif." },
    @{ articleId="GO002"; name="Petite Gold Anklets"; category="Other"; weightGrams=1.0; makingCharges=300; wastagePercentage=3; metalType="GOLD"; purity="22K"; imageUrl="https://images.unsplash.com/photo-1621335829175-95f437384d7c?w=500"; description="Thin and delicate gold anklets for special occasions." },
    @{ articleId="SO001"; name="Antique Silver Anklets"; category="Other"; weightGrams=25.0; makingCharges=600; wastagePercentage=4; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1629227306253-03310aa27fba?w=500"; description="Traditional silver anklets with a vintage finish." },
    @{ articleId="SO002"; name="Silver Floral Hair Pin"; category="Other"; weightGrams=10.0; makingCharges=300; wastagePercentage=3; metalType="SILVER"; purity="FINE"; imageUrl="https://images.unsplash.com/photo-1544450298-6c81ca72f05a?w=500"; description="Handcrafted silver hair pin with floral motifs." }
)

Write-Host "Deleting existing articles to ensure a clean state..."
Invoke-RestMethod -Uri "http://localhost:8080/api/articles/all" -Method Delete

foreach ($article in $articles) {
    $json = $article | ConvertTo-Json
    Invoke-RestMethod -Uri "http://localhost:8080/api/articles" -Method Post -Body $json -ContentType "application/json"
    Write-Host "Added: $($article.name)"
}
