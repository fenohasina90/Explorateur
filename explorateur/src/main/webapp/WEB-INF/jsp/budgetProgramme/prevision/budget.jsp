<%@ include file="/WEB-INF/jsp/layout/header.jsp" %>
<body class="vertical-layout vertical-menu 2-columns   fixed-navbar" data-open="click" data-menu="vertical-menu" data-col="2-columns">
<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp" %>
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Prevision budgetaire - Activites</h3>
            </div>
        </div>
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
                                    <form id="budgetForm" class="form form-horizontal form-bordered" method="post" action="/budget-programme/prevision">
                                        <!-- BUDGET GLOBAL -->
                                        <h4 class="form-section"><i class="ft-layers"></i> Budget global</h4>
                                        <div class="form-body">
                                            <div class="form-group row mx-auto">
                                                <label class="col-md-3 label-control" for="anneeInputBudget">Annee d'exercice</label>
                                                <div class="col-md-9">
                                                    <input type="text" id="anneeInputBudget" class="form-control" name="anneeInputBudget" autocomplete="off" placeholder="Taom-piasana ...">
                                                    <div id="anneeSuggestionsBudget" class="suggestions-list"></div>
                                                </div>
                                            </div>
                                            <input type="hidden" id="anneeExerciceIdBudget" name="anneeExerciceId">
                                            <div class="form-group row mx-auto">
                                                <label class="col-md-3 label-control">Montant total du budget</label>
                                                <div class="col-md-9">
                                                    <input type="text" id="budgetMontantAffiche" class="form-control" value="0.00" readonly>
                                                    <input type="hidden" id="budgetMontant" name="montant">
                                                </div>
                                            </div>
                                        </div>

                                        <hr/>

                                        <!-- ACTIVITES -->
                                        <div class="d-flex justify-content-between align-items-center mb-1">
                                            <h4 class="form-section mb-0"><i class="ft-activity"></i> Activites</h4>
                                            <button type="button" id="addActiviteBtn" class="btn btn-sm btn-primary">
                                                <i class="la la-plus"></i> Ajouter une activite
                                            </button>
                                        </div>

                                        <div id="activitesContainer"></div>

                                        <div class="form-actions mt-2 sticky-actions">
                                            <a href="/" class="btn btn-warning mr-1"><i class="ft-x"></i> Annuler</a>
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

<style>
    /* Barre d'actions fixe en bas de la carte pour éviter le long scroll */
    .sticky-actions {
        position: sticky;
        bottom: 0;
        z-index: 10;
        background-color: #ffffff;
        padding-top: 0.75rem;
        padding-bottom: 0.75rem;
        border-top: 1px solid #e0e0e0;
    }

    /* Optionnel : limiter la hauteur des blocs pour mieux voir gauche/droite */
    .activite-bloc .card-body {
        max-height: 450px;
        overflow-y: auto;
    }
</style>

<script>
    (function($) {
        let activiteIndex = 0;

        // --- Autocomplete année d'exercice pour le budget global ---
        const anneeInputBudget = $('#anneeInputBudget');
        const anneeSuggestionsBudget = $('#anneeSuggestionsBudget');
        const anneeExerciceIdBudget = $('#anneeExerciceIdBudget');
        let anneeTimeoutIdBudget = null;

        anneeInputBudget.on('input', function() {
            clearTimeout(anneeTimeoutIdBudget);
            const searchTerm = $(this).val().trim();

            if (searchTerm.length < 1) {
                anneeSuggestionsBudget.hide();
                return;
            }

            anneeTimeoutIdBudget = setTimeout(() => {
                fetchAnneesExerciceBudget(searchTerm);
            }, 300);
        });

        function fetchAnneesExerciceBudget(searchTerm) {
            $.ajax({
                url: 'http://localhost:8081/api/inscription/annee-exercice',
                method: 'GET',
                success: function(data) {
                    displayAnneeSuggestionsBudget(data, searchTerm);
                },
                error: function(xhr, status, error) {
                    console.error('Erreur API année exercice (budget):', error);
                    anneeSuggestionsBudget.hide();
                }
            });
        }

        function displayAnneeSuggestionsBudget(annees, searchTerm) {
            anneeSuggestionsBudget.empty();

            if (!annees || annees.length === 0) {
                anneeSuggestionsBudget.hide();
                return;
            }

            const term = (searchTerm || '').toLowerCase();

            const scored = annees.map(anneeObj => {
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
                anneeSuggestionsBudget.hide();
                return;
            }

            scored.forEach(({ obj, year }) => {
                const item = $('<div class="suggestion-item"></div>')
                    .text(year)
                    .data('annee', { id: obj.id, year })
                    .click(function() {
                        const data = $(this).data('annee');
                        anneeInputBudget.val(data.year);
                        anneeExerciceIdBudget.val(data.id);
                        anneeSuggestionsBudget.hide();
                    });

                anneeSuggestionsBudget.append(item);
            });

            anneeSuggestionsBudget.show();
        }

        $(document).click(function(e) {
            if (!$(e.target).closest('#anneeInputBudget, #anneeSuggestionsBudget').length) {
                anneeSuggestionsBudget.hide();
            }
        });

        function recalcBudgetGlobal() {
            let total = 0;
            $(".activite-cout-hidden").each(function() {
                const v = parseFloat($(this).val());
                if (!isNaN(v)) total += v;
            });
            $("#budgetMontant").val(total.toFixed(2));
            $("#budgetMontantAffiche").val(total.toFixed(2));
        }

        function recalcActiviteTotal($activiteBloc) {
            let total = 0;
            $activiteBloc.find(".detail-montant").each(function() {
                const v = parseFloat($(this).val());
                if (!isNaN(v)) total += v;
            });
            $activiteBloc.find(".activite-cout-hidden").val(total.toFixed(2));
            $activiteBloc.find(".activite-cout-affiche").val(total.toFixed(2));
            recalcBudgetGlobal();
        }

        function addDetailRow($activiteBloc, activiteIdx) {
            const $detailsContainer = $activiteBloc.find(".details-container");
            const detailIndex = $detailsContainer.children(".detail-row").length;

            const rowHtml =
                '<div class="form-group row mx-auto detail-row">' +
                    '<label class="col-md-2 label-control">Detail</label>' +
                    '<div class="col-md-4">' +
                        '<input type="text" class="form-control" name="activites[' + activiteIdx + '].details[' + detailIndex + '].details" placeholder="Detail de l\'activite" required>' +
                    '</div>' +
                    '<label class="col-md-2 label-control">Montant</label>' +
                    '<div class="col-md-3">' +
                        '<input type="number" min="0" step="0.01" class="form-control detail-montant" name="activites[' + activiteIdx + '].details[' + detailIndex + '].montant" value="0" required>' +
                    '</div>' +
                    '<div class="col-md-1 text-right">' +
                        '<button type="button" class="btn btn-sm btn-danger remove-detail-btn"><i class="la la-trash"></i></button>' +
                    '</div>' +
                '</div>';

            $detailsContainer.append(rowHtml);
        }

        function addActiviteBloc() {
            const idx = activiteIndex++;

            const blocHtml =
                '<div class="card mt-1 activite-bloc" data-index="' + idx + '">' +
                    '<div class="card-header">' +
                        '<div class="heading-elements d-flex justify-content-between align-items-center">' +
                            '<h4 class="card-title mb-0">Activite #' + (idx + 1) + '</h4>' +
                            '<div>' +
                                '<button type="button" class="btn btn-sm btn-icon btn-outline-secondary toggle-activite-btn" title="Replier / afficher">' +
                                    '<i class="ft-minus"></i>' +
                                '</button>' +
                                '<button type="button" class="btn btn-sm btn-danger remove-activite-btn"><i class="la la-trash"></i></button>' +
                            '</div>' +
                        '</div>' +
                    '</div>' +
                    '<div class="card-content collpase show activite-body-wrapper">' +
                        '<div class="card-body">' +
                            '<div class="row">' +
                                '<!-- Colonne gauche : informations de l\'activite -->' +
                                '<div class="col-md-6">' +
                                    '<div class="form-body">' +
                                        '<div class="form-group row mx-auto">' +
                                            '<label class="col-md-2 label-control">Nom</label>' +
                                            '<div class="col-md-10">' +
                                                '<input type="text" class="form-control" name="activites[' + idx + '].nom" placeholder="Nom de l\'activite ..." required>' +
                                            '</div>' +
                                        '</div>' +
                                        '<div class="form-group row mx-auto">' +
                                            '<label class="col-md-2 label-control">Date</label>' +
                                            '<div class="col-md-10">' +
                                                '<input type="date" class="form-control" name="activites[' + idx + '].dateActivite">' +
                                            '</div>' +
                                        '</div>' +
                                        '<div class="form-group row mx-auto">' +
                                            '<label class="col-md-2 label-control">Description</label>' +
                                            '<div class="col-md-10">' +
                                                '<textarea class="form-control" name="activites[' + idx + '].description" rows="3" placeholder="Description de l\'activite ..."></textarea>' +
                                            '</div>' +
                                        '</div>' +
                                        '<div class="form-group row mx-auto">' +
                                            '<label class="col-md-4 label-control">Cout total activite</label>' +
                                            '<div class="col-md-8">' +
                                                '<input type="text" class="form-control activite-cout-affiche" value="0.00" readonly>' +
                                                '<input type="hidden" class="activite-cout-hidden" name="activites[' + idx + '].cout" value="0">' +
                                            '</div>' +
                                        '</div>' +
                                        '<div class="form-group row mx-auto">' +
                                            '<div class="col-md-12 text-right">' +
                                                '<button type="button" class="btn btn-sm btn-primary add-detail-btn">' +
                                                    '<i class="la la-plus"></i> Ajouter un detail' +
                                                '</button>' +
                                            '</div>' +
                                        '</div>' +
                                    '</div>' +
                                '</div>' +

                                '<!-- Colonne droite : details de l\'activite -->' +
                                '<div class="col-md-6">' +
                                    '<div class="details-container border-left mt-1 pt-1 pl-1"></div>' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                    '</div>' +
                '</div>';

            const $bloc = $(blocHtml);
            $("#activitesContainer").append($bloc);

            // Ajouter une première ligne de détail par défaut
            addDetailRow($bloc, idx);
            recalcActiviteTotal($bloc);
        }

        $(document).ready(function() {
            // Ajouter une première activité par défaut
            addActiviteBloc();

            $("#addActiviteBtn").on("click", function() {
                addActiviteBloc();
            });

            $("#activitesContainer").on("click", ".remove-activite-btn", function() {
                $(this).closest(".activite-bloc").remove();
                recalcBudgetGlobal();
            });

            $("#activitesContainer").on("click", ".add-detail-btn", function() {
                const $bloc = $(this).closest(".activite-bloc");
                const idx = $bloc.data("index");
                addDetailRow($bloc, idx);
            });

            $("#activitesContainer").on("click", ".remove-detail-btn", function() {
                const $bloc = $(this).closest(".activite-bloc");
                $(this).closest(".detail-row").remove();
                recalcActiviteTotal($bloc);
            });

            $("#activitesContainer").on("input", ".detail-montant", function() {
                const $bloc = $(this).closest(".activite-bloc");
                recalcActiviteTotal($bloc);
            });

            // Replier / afficher une activite avec son detail
            $("#activitesContainer").on("click", ".toggle-activite-btn", function() {
                const $btn = $(this);
                const $wrapper = $btn.closest(".activite-bloc").find(".activite-body-wrapper");
                $wrapper.slideToggle(150, function() {
                    const icon = $btn.find('i');
                    if ($wrapper.is(":visible")) {
                        icon.removeClass('ft-plus').addClass('ft-minus');
                    } else {
                        icon.removeClass('ft-minus').addClass('ft-plus');
                    }
                });
            });
        });
    })(jQuery);
</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>
</body>
</html>
