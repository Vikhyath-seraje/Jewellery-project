const API_BASE = 'http://localhost:8080/api';

// --- Articles Page ---

async function loadArticles() {
    try {
        const response = await fetch(`${API_BASE}/articles`);
        const articles = await response.json();
        const tbody = document.querySelector('#articlesTable tbody');
        if (tbody) {
            tbody.innerHTML = '';
            articles.forEach(article => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${article.articleId}</td>
                    <td>
                        ${article.imageUrl ? `<img src="${article.imageUrl}" style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;">` : '-'}
                    </td>
                    <td>${article.name}</td>
                    <td><span class="badge ${article.metalType === 'GOLD' ? 'badge-gold' : 'badge-silver'}">${article.metalType} ${article.purity}</span></td>
                    <td>${article.weightGrams}</td>
                    <td>₹ ${article.makingCharges}</td>
                    <td>${article.wastagePercentage}%</td>
                    <td>
                        <button class="btn" style="padding: 5px 10px; font-size: 0.8rem; background: #d9534f;" onclick="deleteArticle(${article.id})">Delete</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        }
    } catch (error) {
        console.error('Error loading articles:', error);
    }
}

async function addArticle(event) {
    event.preventDefault();
    const article = {
        articleId: document.getElementById('articleId').value,
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        metalType: document.getElementById('metalType').value,
        purity: document.getElementById('purity').value,
        weightGrams: parseFloat(document.getElementById('weight').value),
        makingCharges: parseFloat(document.getElementById('makingCharges').value),
        wastagePercentage: parseFloat(document.getElementById('wastage').value),
        imageUrl: document.getElementById('imageUrl').value
    };

    try {
        const response = await fetch(`${API_BASE}/articles`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(article)
        });
        if (response.ok) {
            alert('Article added successfully!');
            document.getElementById('addArticleForm').reset();
            loadArticles();
        } else {
            alert('Failed to add article.');
        }
    } catch (error) {
        console.error('Error adding article:', error);
    }
}

async function deleteArticle(id) {
    if (!confirm('Are you sure you want to delete this article?')) return;
    try {
        await fetch(`${API_BASE}/articles/${id}`, { method: 'DELETE' });
        loadArticles();
    } catch (error) {
        console.error('Error deleting article:', error);
    }
}

async function deleteAllArticles() {
    if (!confirm('WARNING: Are you sure you want to delete ALL articles? This cannot be undone!')) return;

    const password = prompt("Please enter Admin Password to confirm:");
    if (password !== "admin123") {
        alert("Incorrect password!");
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/articles/all`, { method: 'DELETE' });
        if (response.ok) {
            alert('All articles deleted successfully.');
            loadArticles();
        } else {
            alert('Failed to delete articles.');
        }
    } catch (error) {
        console.error('Error deleting all articles:', error);
    }
}

function filterArticles() {
    const searchTerm = document.getElementById('searchArticle').value.toLowerCase();
    const rows = document.querySelectorAll('#articlesTable tbody tr');

    rows.forEach(row => {
        const text = row.textContent.toLowerCase();
        row.style.display = text.includes(searchTerm) ? '' : 'none';
    });
}


// --- Gold Rate Page ---

async function loadAllRates() {
    const types = [
        { metal: 'GOLD', purity: '24K', id: 'rate-GOLD-24K' },
        { metal: 'GOLD', purity: '22K', id: 'rate-GOLD-22K' },
        { metal: 'SILVER', purity: 'FINE', id: 'rate-SILVER-FINE' }
    ];

    for (const type of types) {
        try {
            const response = await fetch(`${API_BASE}/gold-rate/today?metalType=${type.metal}&purity=${type.purity}`);
            if (response.ok) {
                const rate = await response.json();
                const el = document.getElementById(type.id);
                if (el) el.textContent = `₹ ${rate.toFixed(2)} / gram`;

                // Also update selling price page headers if they exist
                const spEl = document.getElementById(`sp-${type.id}`);
                if (spEl) spEl.textContent = `₹ ${rate.toFixed(2)} / gram`;
            }
        } catch (error) {
            console.error(`Error loading rate for ${type.metal} ${type.purity}:`, error);
        }
    }
}

async function fetchLatestRate() {
    try {
        await fetch(`${API_BASE}/gold-rate/fetch`, { method: 'POST' });
        loadAllRates();
        loadRateHistory();
    } catch (error) {
        console.error('Error fetching rate:', error);
    }
}

async function updateRateManual(event) {
    event.preventDefault();
    const rate = parseFloat(document.getElementById('manualRate').value);
    const metalType = document.getElementById('manualMetalType').value;
    const purity = document.getElementById('manualPurity').value;

    try {
        await fetch(`${API_BASE}/gold-rate/update?rate=${rate}&metalType=${metalType}&purity=${purity}`, { method: 'POST' });
        alert('Rate updated!');
        loadAllRates();
        loadRateHistory();
    } catch (error) {
        console.error('Error updating rate:', error);
    }
}

async function loadRateHistory() {
    try {
        const response = await fetch(`${API_BASE}/gold-rate/history`);
        const history = await response.json();
        history.sort((a, b) => new Date(b.date) - new Date(a.date));

        const tbody = document.querySelector('#rateHistoryTable tbody');
        if (!tbody) return;
        tbody.innerHTML = '';
        history.forEach(item => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${item.date}</td>
                <td>${item.metalType || 'GOLD'}</td>
                <td>${item.purity || '22K'}</td>
                <td>₹ ${item.ratePerGram.toFixed(2)}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error loading history:', error);
    }
}

let isAdmin = false;

function toggleAdminMode() {
    if (isAdmin) {
        isAdmin = false;
        const adminSection = document.getElementById('adminSection');
        if (adminSection) adminSection.style.display = 'none';
        alert('Admin mode disabled.');
    } else {
        const password = prompt("Enter Admin Password:");
        if (password === "admin123") {
            isAdmin = true;
            const adminSection = document.getElementById('adminSection');
            if (adminSection) {
                adminSection.style.display = 'block';
                loadArticles(); // Load the table data
            }
        } else {
            alert("Incorrect password!");
        }
    }
    // Re-render catalogue to show/hide delete buttons
    loadCatalogue();
}

async function loadCatalogue() {
    try {
        // Fetch articles
        const articlesResponse = await fetch(`${API_BASE}/articles`);
        const articles = await articlesResponse.json();

        // Fetch today's rates
        const rates = {};
        const types = [
            { key: 'GOLD-22K', metal: 'GOLD', purity: '22K' },
            { key: 'GOLD-24K', metal: 'GOLD', purity: '24K' },
            { key: 'SILVER-FINE', metal: 'SILVER', purity: 'FINE' }
        ];

        for (const type of types) {
            try {
                console.log(`Fetching rate for ${type.metal} ${type.purity}`);
                // Try fetching today's rate
                let res = await fetch(`${API_BASE}/gold-rate/today?metalType=${type.metal}&purity=${type.purity}`);

                // If not found, try fetching history and getting the latest
                if (!res.ok) {
                    console.log(`Today's rate not found for ${type.metal} ${type.purity}, checking history...`);
                    const historyRes = await fetch(`${API_BASE}/gold-rate/history`);
                    if (historyRes.ok) {
                        const history = await historyRes.json();
                        // Filter for this metal/purity and sort by date desc
                        const latest = history
                            .filter(h => h.metalType === type.metal && h.purity === type.purity)
                            .sort((a, b) => new Date(b.date) - new Date(a.date))[0];

                        if (latest) {
                            console.log(`Found latest rate from history: ${latest.ratePerGram}`);
                            rates[type.key] = latest.ratePerGram;
                            continue; // Found it in history
                        } else {
                            console.warn(`No history found for ${type.metal} ${type.purity}`);
                        }
                    }
                } else {
                    const rateVal = await res.json();
                    console.log(`Fetched today's rate: ${rateVal}`);
                    rates[type.key] = rateVal;
                }
            } catch (e) {
                console.warn(`Could not fetch rate for ${type.metal} ${type.purity}`, e);
            }
        }

        console.log('Final Rates Object:', rates);
        renderCatalogue(articles, rates);
    } catch (error) {
        console.error('Error loading catalogue:', error);
    }
}

function renderCatalogue(articles, rates) {
    const grid = document.getElementById('catalogueGrid');
    if (!grid) return;
    grid.innerHTML = '';

    if (articles.length === 0) {
        grid.innerHTML = '<div style="grid-column: 1/-1; text-align: center;">No items found.</div>';
        return;
    }

    articles.forEach(article => {
        const card = document.createElement('div');
        card.className = 'product-card';
        card.dataset.metal = article.metalType; // For filtering

        const imageHtml = article.imageUrl
            ? `<img src="${article.imageUrl}" alt="${article.name}">`
            : `<span style="font-size: 3rem;">💎</span>`;

        const deleteBtn = isAdmin
            ? `<button class="btn" style="background: #d9534f; margin-top: 10px; width: 100%;" onclick="deleteArticle(${article.id})">Delete</button>`
            : '';

        // Calculate Price
        let priceDisplay = 'Price on Request';
        const metalType = (article.metalType || '').toUpperCase();
        const purity = (article.purity || '').toUpperCase();

        let rateKey = `${metalType}-${purity}`;
        if (metalType === 'SILVER') rateKey = 'SILVER-FINE';
        if (metalType === 'GOLD' && !purity) rateKey = 'GOLD-22K';

        console.log(`Calculating price for ${article.name}: Metal=${metalType}, Purity=${purity}, Key=${rateKey}`);
        const rate = rates[rateKey];

        if (rate) {
            const goldCost = rate * article.weightGrams;
            const wastageCost = (goldCost * article.wastagePercentage) / 100;
            const totalCost = goldCost + article.makingCharges + wastageCost;
            priceDisplay = `₹ ${totalCost.toFixed(2)}`;
            console.log(`Price calculated for ${article.name}: ₹${totalCost.toFixed(2)} (Rate: ${rate})`);
        } else {
            console.warn(`Rate not found for key: ${rateKey}. Available rates:`, Object.keys(rates));
            priceDisplay = 'Rate Not Set';
        }

        card.innerHTML = `
            <div class="product-image">
                ${imageHtml}
            </div>
            <div class="product-details">
                <h3 class="product-title">${article.name}</h3>
                <div class="product-meta">
                    <span class="badge ${article.metalType === 'GOLD' ? 'badge-gold' : 'badge-silver'}">${article.metalType} ${article.purity}</span>
                    <span>${article.weightGrams}g</span>
                </div>
                <div class="product-price">
                    ${priceDisplay}
                </div>
                <p style="font-size: 0.8rem; color: #888; margin-top: 10px;">${article.description || ''}</p>
                ${deleteBtn}
            </div>
        `;
        grid.appendChild(card);
    });
}

function filterCatalogue() {
    const filter = document.getElementById('metalFilter').value;
    const cards = document.querySelectorAll('.product-card');

    cards.forEach(card => {
        if (filter === 'all' || card.dataset.metal === filter) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}
