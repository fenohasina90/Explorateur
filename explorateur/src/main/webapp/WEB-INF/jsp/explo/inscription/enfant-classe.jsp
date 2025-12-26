
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body class="vertical-layout vertical-menu 2-columns   fixed-navbar" data-open="click" data-menu="vertical-menu" data-col="2-columns">
<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Formulaire - Reinscription d'un Explorateur</h3>
            </div>
            
        </div>
        <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <h4 class="alert-heading">Erreur</h4>
                    <p>${error}</p>
                </div>
            </c:if>
        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="heading-elements">
                                    <ul class="list-inline mb-0">
                                        <li><a data-action="collapse"><i class="ft-minus"></i></a></li>
                                        <li><a data-action="reload"><i class="ft-rotate-cw"></i></a></li>
                                        <li><a data-action="expand"><i class="ft-maximize"></i></a></li>
                                        <li><a data-action="close"><i class="ft-x"></i></a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="card-content collpase show">
                                <div class="card-body">

                                    <!-- Formulaire du haut : filtre année + classe -->
                                    <form id="filtreForm" class="form form-horizontal form-bordered" onsubmit="return false;">
                                        <!-- Champ pour l'année d'exercice avec suggestions -->
                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="anneeInput">Annee d'inscription: </label>
                                                <div class="col-md-9">
                                                    <input type="text" id="anneeInput" class="form-control" name="anneeInput" autocomplete="off" placeholder="Taom-piasana ...">
                                                    <div id="anneeSuggestionsList" class="suggestions-list"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <input type="hidden" id="anneeExerciceId" name="anneeExerciceId">

                                        <!-- Liste déroulante des classes -->
                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="roleId">Classe : </label>
                                                <div class="col-md-9">
                                                    <select id="roleId" name="classeId" class="form-control">
                                                        <option value="">-- Misafidiana Kilasy --</option>
                                                        <c:forEach var="cl" items="${classes}">
                                                            <option value="${cl.id}">${cl.nom}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </form>

                                    <hr/>

                                    <!-- Formulaire du bas : sélection de l'enfant en fonction des filtres -->
                                    <form id="enfantForm" class="form form-horizontal form-bordered" action="/inscription/classe" method="post">
                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="enfantSelect">Enfant : </label>
                                                <div class="col-md-9">
                                                    <select id="enfantSelect" name="enfantId" class="form-control">
                                                        <option value="">-- Misafidiana Ankizy --</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- On renvoie aussi les filtres sélectionnés -->
                                        <input type="hidden" id="selectedAnneeExerciceId" name="anneeExerciceId">
                                        <input type="hidden" id="selectedClasseId" name="classeId">

                                        <div class="form-actions">
                                            <a href="/liste/explorateurs"><button type="button" class="btn btn-warning mr-1"><i class="ft-x"></i> Annuler</button></a>
                                            <button type="submit" class="btn btn-primary"><i class="la la-check-square-o"></i> Enregistrer</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
<script>
        $(document).ready(function() {
            // Autocomplete année d'exercice
            const anneeInput = $('#anneeInput');
            const anneeSuggestionsList = $('#anneeSuggestionsList');
            const anneeExerciceIdField = $('#anneeExerciceId');
            const classeSelect = $('#roleId');
            const enfantSelect = $('#enfantSelect');
            const selectedAnneeExerciceId = $('#selectedAnneeExerciceId');
            const selectedClasseId = $('#selectedClasseId');
            let anneeTimeoutId = null;

            // Debounce pour l'année d'exercice
            anneeInput.on('input', function() {
                clearTimeout(anneeTimeoutId);
                const searchTerm = $(this).val().trim();

                if (searchTerm.length < 1) {
                    anneeSuggestionsList.hide();
                    return;
                }

                anneeTimeoutId = setTimeout(() => {
                    fetchAnneesExercice(searchTerm);
                }, 300);
            });

            // Lorsqu'on sélectionne une année OU une classe, essayer de charger les enfants
            anneeExerciceIdField.on('change', tryLoadEnfants);
            classeSelect.on('change', tryLoadEnfants);

            function tryLoadEnfants() {
                const anneeId = anneeExerciceIdField.val();
                const classeId = classeSelect.val();

                if (!anneeId || !classeId) {
                    // Pas encore tous les filtres, on vide la liste
                    enfantSelect.empty();
                    enfantSelect.append('<option value="">-- Misafidiana Zaza --</option>');
                    return;
                }

                // On garde en mémoire les filtres choisis pour le formulaire du bas
                selectedAnneeExerciceId.val(anneeId);
                selectedClasseId.val(classeId);

                // Appel à l'API pour récupérer les enfants non inscrits
                $.ajax({
                    url: 'http://localhost:8081/api/inscription/enfants',
                    method: 'GET',
                    data: {
                        anneeExerciceId: anneeId,
                        classeId: classeId
                    },
                    success: function(data) {
                        remplirEnfants(data);
                    },
                    error: function(xhr, status, error) {
                        console.error('Erreur API enfants:', error);
                        enfantSelect.empty();
                        enfantSelect.append('<option value="">-- Tsy misy angona --</option>');
                    }
                });
            }

            function remplirEnfants(enfants) {
                enfantSelect.empty();
                enfantSelect.append('<option value="">-- Misafidiana Zaza --</option>');

                if (!enfants || enfants.length === 0) {
                    return;
                }

                enfants.forEach(function(enfant) {
                    const texte = (enfant.nom || '') + ' ' + (enfant.prenom || '');
                    const option = $('<option></option>')
                        .val(enfant.id)
                        .text(texte.trim() || ('ID ' + enfant.id));
                    enfantSelect.append(option);
                });
            }

            // Fonction pour récupérer les années d'exercice depuis l'API
            function fetchAnneesExercice(searchTerm) {
                $.ajax({
                    url: 'http://localhost:8081/api/inscription/annee-exercice',
                    method: 'GET',
                    success: function(data) {
                        displayAnneeSuggestions(data, searchTerm);
                    },
                    error: function(xhr, status, error) {
                        console.error('Erreur API année exercice:', error);
                        anneeSuggestionsList.hide();
                    }
                });
            }

            // Afficher les suggestions d'année d'exercice
            function displayAnneeSuggestions(annees, searchTerm) {
                anneeSuggestionsList.empty();

                if (!annees || annees.length === 0) {
                    anneeSuggestionsList.hide();
                    return;
                }

                const term = (searchTerm || '').toLowerCase();

                const scored = annees.map(anneeObj => {
                    // annee est un LocalDate côté backend, donc probable JSON: "2025-01-01" -> on prend l'année
                    const full = (anneeObj.annee || '').toString();
                    const year = full.length >= 4 ? full.substring(0, 4) : full;
                    const lower = year.toLowerCase();
                    let score;
                    if (lower.startsWith(term)) {
                        score = 0;
                    } else if (lower.includes(term)) {
                        score = 1;
                    } else {
                        score = Infinity;
                    }
                    return { obj: anneeObj, score, year };
                })
                .filter(item => item.score !== Infinity)
                .sort((a, b) => {
                    if (a.score !== b.score) return a.score - b.score;
                    if (a.year < b.year) return -1;
                    if (a.year > b.year) return 1;
                    return 0;
                });

                if (scored.length === 0) {
                    anneeSuggestionsList.hide();
                    return;
                }

                scored.forEach(({ obj, year }) => {
                    const item = $('<div class="suggestion-item"></div>')
                        .text(year)
                        .data('annee', { id: obj.id, year })
                        .click(function() {
                            const data = $(this).data('annee');
                            anneeInput.val(data.year);
                            anneeExerciceIdField.val(data.id).trigger('change');
                            anneeSuggestionsList.hide();
                        });

                    anneeSuggestionsList.append(item);
                });

                anneeSuggestionsList.show();
            }

            // Cacher les suggestions en cliquant ailleurs
            $(document).click(function(e) {
                if (!$(e.target).closest('#anneeInput, #anneeSuggestionsList').length) {
                    anneeSuggestionsList.hide();
                }
            });
        });
    </script>
<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
</body>
</html>