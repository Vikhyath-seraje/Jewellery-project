const API_BASE = 'http://localhost:8080/api';

// --- Articles Page ---

async function loadArticles() {
    try {
        const response = await fetch(`${API_BASE}/articles`);
        const articles = await response.json();
        const tbody = document.querySelector('#articlesTable tbody');
        tbody.innerHTML = '';
        articles.forEach(article => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${article.articleId}</td>
                <td>${article.name}</td>
                <td>${article.weightGrams}</td>
                <td>${article.makingCharges}</td>
                <td>${article.wastagePercentage}</td>
                <td>
                    <button class="btn" style="padding: 5px 10px; font-size: 0.8rem;" onclick="deleteArticle(${article.id})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
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
        weightGrams: parseFloat(document.getElementById('weight').value),
        makingCharges: parseFloat(document.getElementById('makingCharges').value),
        wastagePercentage: parseFloat(document.getElementById('wastage').value)
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

function filterArticles() {
    const searchTerm = document.getElementById('searchArticle').value.toLowerCase();
    const rows = document.querySelectorAll('#articlesTable tbody tr');

    rows.forEach(row => {
        const articleId = row.cells[0].textContent.toLowerCase();
        const name = row.cells[1].textContent.toLowerCase();
        const weight = row.cells[2].textContent.toLowerCase();

        if (articleId.includes(searchTerm) || name.includes(searchTerm)) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}


// --- Gold Rate Page ---

async function loadTodayRate() {
    try {
        const response = await fetch(`${API_BASE}/gold-rate/today`);
        if (response.ok) {
            const rate = await response.json();
            document.getElementById('todayRateDisplay').textContent = `₹ ${rate.toFixed(2)} / gram`;
        } else {
            document.getElementById('todayRateDisplay').textContent = 'Rate not set for today';
        }
    } catch (error) {
        console.error('Error loading rate:', error);
    }
}

async function fetchLatestRate() {
    try {
        await fetch(`${API_BASE}/gold-rate/fetch`, { method: 'POST' });
        loadTodayRate();
        loadRateHistory();
    } catch (error) {
        console.error('Error fetching rate:', error);
    }
}

async function updateRateManual(event) {
    event.preventDefault();
    const rate = parseFloat(document.getElementById('manualRate').value);
    try {
        await fetch(`${API_BASE}/gold-rate/update?rate=${rate}`, { method: 'POST' });
        alert('Rate updated!');
        loadTodayRate();
        loadRateHistory();
    } catch (error) {
        console.error('Error updating rate:', error);
    }
}

async function loadRateHistory() {
    try {
        const response = await fetch(`${API_BASE}/gold-rate/history`);
        const history = await response.json();
        // Sort by date desc
        history.sort((a, b) => new Date(b.date) - new Date(a.date));

        const tbody = document.querySelector('#rateHistoryTable tbody');
        tbody.innerHTML = '';
        history.forEach(item => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${item.date}</td>
                <td>₹ ${item.ratePerGram.toFixed(2)}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error loading history:', error);
    }
}

// --- Selling Price Page ---

async function loadSellingPrices() {
    try {
        // First ensure we have a rate
        const rateResponse = await fetch(`${API_BASE}/gold-rate/today`);
        if (!rateResponse.ok) {
            document.getElementById('todayRateSpan').textContent = 'Rate not available. Please update gold rate.';
            return;
        }
        const rate = await rateResponse.json();
        document.getElementById('todayRateSpan').textContent = `₹ ${rate.toFixed(2)} / gram`;

        const response = await fetch(`${API_BASE}/costs/selling-prices`);
        const costs = await response.json();
        const tbody = document.querySelector('#sellingPriceTable tbody');
        tbody.innerHTML = '';

        costs.forEach((cost, index) => {
            const tr = document.createElement('tr');
            // Unique ID for discount input
            const discountInputId = `discount-${index}`;
            const finalPriceId = `final-${index}`;

            tr.innerHTML = `
                <td>${cost.article.name}</td>
                <td>${cost.article.weightGrams}</td>
                <td>₹ ${cost.sellingPrice.toFixed(2)}</td>
                <td>
                    <input type="number" id="${discountInputId}" min="0" max="100" value="0" 
                           style="width: 80px;" oninput="calculateFinalPrice(${cost.sellingPrice}, '${discountInputId}', '${finalPriceId}')">
                </td>
                <td id="${finalPriceId}" style="font-weight: bold;">₹ ${cost.sellingPrice.toFixed(2)}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error loading selling prices:', error);
    }
}

function calculateFinalPrice(basePrice, inputId, outputId) {
    const discountPercent = parseFloat(document.getElementById(inputId).value) || 0;
    const discountAmount = (basePrice * discountPercent) / 100;
    const finalPrice = basePrice - discountAmount;
    document.getElementById(outputId).textContent = `₹ ${finalPrice.toFixed(2)}`;
}
