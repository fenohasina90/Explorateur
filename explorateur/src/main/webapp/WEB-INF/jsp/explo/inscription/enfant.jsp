
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body class="vertical-layout vertical-menu 2-columns   fixed-navbar" data-open="click" data-menu="vertical-menu" data-col="2-columns">
<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Formulaire - Ajouter un Explorateur</h3>
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
                                    
                                    <form class="form form-horizontal form-bordered" action="/inscription/explorateur" method="post">
                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="date-debut">Nom : </label>
                                                <div class="col-md-9">
                                                    <input type="text" id="date-debut" class="form-control" placeholder="Anarana ..." name="nom">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="date-debut">Prenom : </label>
                                                <div class="col-md-9">
                                                    <input type="text" id="date-debut" class="form-control" placeholder="Fanampiny ..." name="prenom">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="date-debut">Date de naissance : </label>
                                                <div class="col-md-9">
                                                    <input type="date" id="date-debut" class="form-control" placeholder="Daty nahaterahana ..." name="dateNaissance">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="date-debut">Date de Bapteme : </label>
                                                <div class="col-md-9">
                                                    <input type="date" id="date-debut" class="form-control" placeholder="Daty batisa ..." name="bapteme">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group row mx-auto">
                                            <label class="col-md-3 label-control" for="projectinput75">Sexe : </label>
                                            <div class="col-md-9">
                                                <select name="genre" class="form-control" id="projectinput75">
                                                    <option value="G">Masculin</option>
                                                    <option value="F">Feminin</option>
                                                    <option value="A">Autre</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="date-debut">Adresse : </label>
                                                <div class="col-md-9">
                                                    <input type="text" id="date-debut" class="form-control" placeholder="Adiresy ..." name="adresse">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="date-debut">Nom parent  : </label>
                                                <div class="col-md-9">
                                                    <%-- <input type="text" id="date-debut"  placeholder="" name="nom"> --%>
                                                    <input type="text" id="employeeInput" class="form-control" name="nominput" autocomplete="off" placeholder="Anaran'ny Ray aman-dReny ...">
                                                    <div id="suggestionsList" class="suggestions-list"></div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        
                                        
                                        <!-- Champ caché pour stocker l'ID de l'instructeur -->
                                        <input type="hidden" id="employeeId" name="parentId">

                                        <!-- Champ pour l'année d'exercice avec suggestions -->
                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="annee-exercice">Annee d'inscription: </label>
                                                <div class="col-md-9">
                                                    <input type="text" id="anneeInput" class="form-control" name="anneeInput" autocomplete="off" placeholder="Taom-piasana ...">
                                                    <div id="anneeSuggestionsList" class="suggestions-list"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <input type="hidden" id="anneeExerciceId" name="anneeExerciceId">

                                        <!-- Liste déroulante des rôles -->
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
            // Autocomplete instructeur
            const input = $('#employeeInput');
            const suggestionsList = $('#suggestionsList');
            const employeeIdField = $('#employeeId');
            let timeoutId = null;

            // Autocomplete année d'exercice
            const anneeInput = $('#anneeInput');
            const anneeSuggestionsList = $('#anneeSuggestionsList');
            const anneeExerciceIdField = $('#anneeExerciceId');
            let anneeTimeoutId = null;

            // Délai pour éviter trop d'appels API (debouncing)
            input.on('input', function() {
                clearTimeout(timeoutId);
                const searchTerm = $(this).val().trim();
                
                if (searchTerm.length < 2) {
                    suggestionsList.hide();
                    return;
                }
                
                timeoutId = setTimeout(() => {
                    fetchInstructeur(searchTerm);
                }, 300);
            });

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

            // Fonction pour récupérer les instructeurs depuis l'API
            function fetchInstructeur(searchTerm) {
                $.ajax({
                    // URL relative pour s'adapter automatiquement au port/contexte de l'application
                    url: 'http://localhost:8081/api/inscription/parents',
                    method: 'GET',
                    data: { 
                        search: searchTerm,
                        limit: 10 // Optionnel: limiter les résultats
                    },
                    success: function(data) {
                        displaySuggestions(data, searchTerm);
                    },
                    error: function(xhr, status, error) {
                        console.error('Erreur API:', error);
                        suggestionsList.hide();
                    }
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

            // Afficher les suggestions en fonction du texte saisi
            function displaySuggestions(instructeurs, searchTerm) {
                suggestionsList.empty();

                if (!instructeurs || instructeurs.length === 0) {
                    suggestionsList.hide();
                    return;
                }

                const term = (searchTerm || '').toLowerCase();

                // Calculer une "score" de proximité simple :
                // 0 = commence par le terme, 1 = contient le terme, Infinity = ne contient pas
                const scored = instructeurs.map(instruct => {
                    const fullName = (instruct.nom + ' ' + instruct.prenom).toLowerCase();
                    let score;
                    if (fullName.startsWith(term)) {
                        score = 0;
                    } else if (fullName.includes(term)) {
                        score = 1;
                    } else {
                        score = Infinity;
                    }
                    return { instruct, score, fullName };
                })
                // Garder uniquement ceux qui contiennent le terme
                .filter(item => item.score !== Infinity)
                // Trier par score puis ordre alphabétique
                .sort((a, b) => {
                    if (a.score !== b.score) return a.score - b.score;
                    if (a.fullName < b.fullName) return -1;
                    if (a.fullName > b.fullName) return 1;
                    return 0;
                });

                if (scored.length === 0) {
                    suggestionsList.hide();
                    return;
                }

                scored.forEach(({ instruct }) => {
                    const item = $('<div class="suggestion-item"></div>')
                        .text(instruct.nom + ' ' + instruct.prenom) // Adaptez selon votre modèle
                        .data('instructeur', instruct)
                        .click(function() {
                            const instruct = $(this).data('instructeur');
                            input.val(instruct.nom + ' ' + instruct.prenom); // Adaptez selon votre modèle
                            employeeIdField.val(instruct.id);
                            suggestionsList.hide();
                        });
                    
                    suggestionsList.append(item);
                });
                
                suggestionsList.show();
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
                            anneeExerciceIdField.val(data.id);
                            anneeSuggestionsList.hide();
                        });

                    anneeSuggestionsList.append(item);
                });

                anneeSuggestionsList.show();
            }

            // Cacher les suggestions en cliquant ailleurs
            $(document).click(function(e) {
                if (!$(e.target).closest('.suggestions-container').length) {
                    suggestionsList.hide();
                    anneeSuggestionsList.hide();
                }
            });

            // Navigation clavier
            input.on('keydown', function(e) {
                const items = $('.suggestion-item');
                const current = items.filter('.highlighted');
                
                switch(e.key) {
                    case 'ArrowDown':
                        e.preventDefault();
                        if (!current.length || !current.next().length) {
                            items.first().addClass('highlighted');
                        } else {
                            current.removeClass('highlighted').next().addClass('highlighted');
                        }
                        break;
                    case 'ArrowUp':
                        e.preventDefault();
                        if (!current.length || !current.prev().length) {
                            items.last().addClass('highlighted');
                        } else {
                            current.removeClass('highlighted').prev().addClass('highlighted');
                        }
                        break;
                    case 'Enter':
                        if (current.length) {
                            e.preventDefault();
                            current.click();
                        }
                        break;
                    case 'Escape':
                        suggestionsList.hide();
                        break;
                }
            });
        });
    </script>
<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
</body>
</html>