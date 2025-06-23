document.addEventListener("DOMContentLoaded", () => {
    const searchBar = document.getElementById("search-bar");
    const suggestions = document.getElementById("suggestions");

    searchBar.addEventListener("input", () => {
        const query = searchBar.value.trim();

        if (query.length === 0) {
            suggestions.innerHTML = "";
            suggestions.style.display = "none";
            return;
        }

        fetch(`${contextPath}/jsp/auto/searchSuggest.jsp?q=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(data => {
                suggestions.innerHTML = "";
                if (data.length === 0) {
                    suggestions.style.display = "none";
                    return;
                }
                suggestions.style.display = "block";
                data.forEach(item => {
                    const div = document.createElement("div");
                    div.classList.add("suggestion-item");
                    div.textContent = item;
                    div.addEventListener("click", () => {
                        searchBar.value = item;
                        suggestions.innerHTML = "";
                        suggestions.style.display = "none";
                        // Redirect alla pagina di catalogo con la ricerca
                        window.location.href = `${contextPath}/product?action=list&search=${encodeURIComponent(item)}`;
                    });
                    suggestions.appendChild(div);
                });
            })
            .catch(error => {
                console.error("Errore nella fetch:", error);
            });
    });

    // Event listener per invio tasto "Enter" sulla barra di ricerca
    searchBar.addEventListener("keydown", (e) => {
        if (e.key === "Enter") {
            e.preventDefault();
            suggestions.innerHTML = "";
            suggestions.style.display = "none";
            window.location.href = `${contextPath}/product?action=list&search=${encodeURIComponent(searchBar.value.trim())}`;
        }
    });

    // Nascondi suggerimenti se clicco fuori
    document.addEventListener("click", (e) => {
        if (!suggestions.contains(e.target) && e.target !== searchBar) {
            suggestions.innerHTML = "";
            suggestions.style.display = "none";
        }
    });
});
