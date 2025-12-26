<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body class="vertical-layout vertical-menu 2-columns fixed-navbar" data-open="click" data-menu="vertical-menu" data-col="2-columns">
<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Liste des explorateurs</h3>
            </div>
            <div class="content-header-right col-md-6 col-12 mb-2 text-right">
                <div class="row">
                    <div class="col-md-2 col-12"></div>
                    <a href="/inscription/explorateur" class="btn btn-info col-md-3 col-12">
                        <i class="la la-plus"></i> Ajouter Explorateur
                    </a>
                    <div class="col-md-2 col-12"></div>
                    <a href="/inscription/classe" class="btn btn-primary col-md-3 col-12">
                        <i class="la la-plus"></i> Reinscription Explorateur
                    </a>
                </div>
                
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

                                    <!-- Formulaire de filtre -->
                                    <form class="form form-horizontal form-bordered" method="get" action="/liste/explorateurs">
                                        <div class="form-body">
                                            <div class="form-group row mx-auto align-items-end">
                                                
                                                <div class="col-md-3">
                                                    <input type="text" id="anneeInput" class="form-control" autocomplete="off" placeholder="Taom-piasana ...">
                                                    <div id="anneeSuggestionsList" class="suggestions-list"></div>
                                                    <input type="hidden" id="anneeExerciceId" name="anneeExerciceId" value="${anneeExerciceId}">
                                                    <input type="hidden" id="anneeLabel" name="anneeLabel" value="">
                                                </div>

                                                
                                                <div class="col-md-3">
                                                    <select id="classeId" name="classeId" class="form-control">
                                                        <option value="">-- Toutes les classes --</option>
                                                        <c:forEach var="cl" items="${classes}">
                                                            <option value="${cl.id}" <c:if test="${classeId == cl.id}">selected</c:if>>${cl.nom}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <input type="hidden" id="classeLabel" name="classeLabel" value="">
                                                </div>

                                                <div class="col-md-3 text-right mt-1 mt-md-0">
                                                    <button type="button" id="btnFilter" class="btn btn-primary mr-1">
                                                        <i class="la la-search"></i> Filtrer
                                                    </button>
                                                    <button type="button" id="btnReset" class="btn btn-secondary mr-1">
                                                        <i class="la la-undo"></i> Reinitialiser
                                                    </button>
                                                    <button type="button" id="btnExportPdf" class="btn btn-success">
                                                        <i class="la la-file-pdf-o"></i> Exporter PDF
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="form-group row mx-auto mt-1" id="exportColumnsRow" style="display: none;">
                                                <div class="col-md-12">
                                                    <span>Colonnes à exporter :</span>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="annee" checked> Annee</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="classe" checked> Classe</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="nom" checked> Nom</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="prenom" checked> Prenom</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="age" checked> Age</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="adresse" checked> Adresse</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="parent" checked> Parent</label>
                                                </div>
                                            </div>
                                        </div>
                                    </form>

                                    <hr/>

                                    <!-- Tableau des enfants -->
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Annee</th>
                                                    <th>Classe</th>
                                                    <th>Nom</th>
                                                    <th>Prenom</th>
                                                    <th>Adresse</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="e" items="${enfants}">
                                                    <tr>
                                                        <td>${e.anneeExercice}</td>
                                                        <td>${e.classeNom}</td>
                                                        <td>${e.enfantNom}</td>
                                                        <td>${e.enfantPrenom}</td>
                                                        <td>${e.adresseEnfant}</td>
                                                        <td>
                                                            <button type="button" class="btn btn-info btn-sm btn-parent-details"
                                                                    data-toggle="modal" data-target="#parentModal"
                                                                    data-parent-nom="${e.parentNom}"
                                                                    data-parent-prenom="${e.parentPrenom}"
                                                                    data-parent-adresse="${e.adresseParent}"
                                                                    data-parent-telephone="${e.telephone}"
                                                                    data-enfant-age="${e.age}"
                                                                    data-enfant-naissance="${e.dateNaissance}"
                                                                    data-enfant-bapteme="${e.bapteme}"
                                                                    data-enfant-created="${e.inscriptionCreatedAt}">
                                                                Voir details
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

<!-- Modal détails parent -->
<div class="modal fade" id="parentModal" tabindex="-1" role="dialog" aria-labelledby="parentModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="parentModalLabel">Details</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p class="text-center"><strong>Informations du parent</strong></p>
        <p><strong>Nom :</strong> <span id="parentNom"></span></p>
        <p><strong>Prenom :</strong> <span id="parentPrenom"></span></p>
        <p><strong>Adresse :</strong> <span id="parentAdresse"></span></p>
        <p><strong>Telephone :</strong> <span id="parentTelephone"></span></p>
        <hr/>
        <p class="text-center"><strong>Informations de l'enfant</strong></p>
        <p><strong>Date de naissance :</strong> <span id="enfantNaissance"></span></p>
        <p><strong>Age de l'enfant :</strong> <span id="enfantAge"></span> ans</p>
        <p><strong>Date de bapteme :</strong> <span id="enfantBapteme"></span></p>
        <p><strong>Date d'inscription :</strong> <span id="enfantCreated"></span></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
      </div>
    </div>
  </div>
</div>

<script>
    $(document).ready(function() {
        const anneeInput = $('#anneeInput');
        const anneeSuggestionsList = $('#anneeSuggestionsList');
        const anneeExerciceIdField = $('#anneeExerciceId');
        const anneeLabelField = $('#anneeLabel');
        const classeSelect = $('#classeId');
        const classeLabelField = $('#classeLabel');
        let anneeTimeoutId = null;

        // Initialisation de l'affichage de l'année dans l'input si on a un ID
        // (facultatif: on pourrait charger le libellé via une API si besoin)

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

        function displayAnneeSuggestions(annees, searchTerm) {
            anneeSuggestionsList.empty();

            if (!annees || annees.length === 0) {
                anneeSuggestionsList.hide();
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
                        anneeLabelField.val(data.year);
                        anneeSuggestionsList.hide();
                    });

                anneeSuggestionsList.append(item);
            });

            anneeSuggestionsList.show();
        }

        $(document).click(function(e) {
            if (!$(e.target).closest('#anneeInput, #anneeSuggestionsList').length) {
                anneeSuggestionsList.hide();
            }
        });

        // Initialisation du libellé de la classe sélectionnée
        function updateClasseLabel() {
            const selectedOption = classeSelect.find('option:selected');
            const text = selectedOption.val() ? selectedOption.text() : '';
            classeLabelField.val(text);
        }

        updateClasseLabel();
        classeSelect.on('change', updateClasseLabel);

        // Gestion du modal parent + infos enfant
        $('.table').on('click', '.btn-parent-details', function() {
            const btn = $(this);
            $('#parentNom').text(btn.data('parent-nom'));
            $('#parentPrenom').text(btn.data('parent-prenom'));
            $('#parentAdresse').text(btn.data('parent-adresse'));
            $('#parentTelephone').text(btn.data('parent-telephone'));

            $('#enfantAge').text(btn.data('enfant-age'));
            $('#enfantNaissance').text(btn.data('enfant-naissance'));
            $('#enfantBapteme').text(btn.data('enfant-bapteme'));
            $('#enfantCreated').text(btn.data('enfant-created'));
        });

        // Bouton Filtrer : envoie toujours vers /liste/enfants
        $('#btnFilter').on('click', function() {
            const form = $(this).closest('form');
            form.attr('action', '/liste/explorateurs');
            form.submit();
        });

        // Bouton Réinitialiser : vide les filtres et recharge la liste complète
        $('#btnReset').on('click', function() {
            const form = $(this).closest('form');

            // Vider les champs liés à l'année
            anneeInput.val('');
            anneeExerciceIdField.val('');
            anneeLabelField.val('');

            // Vider la classe
            classeSelect.val('');
            classeLabelField.val('');

            // Cacher éventuellement la ligne de colonnes export
            $('#exportColumnsRow').hide();

            form.attr('action', '/liste/explorateurs');
            form.submit();
        });

        // Export PDF : affiche d'abord le choix des colonnes, puis confirme l'export
        let exportColumnsVisible = false;
        $('#btnExportPdf').on('click', function() {
            const form = $(this).closest('form');
            const columnsRow = $('#exportColumnsRow');

            if (!exportColumnsVisible) {
                columnsRow.slideDown();
                exportColumnsVisible = true;
                return;
            }

            if (!confirm('Confirmer l\'export PDF avec les colonnes selectionnees ?')) {
                return;
            }

            // S'assurer que le libellé de l'année est renseigné si l'utilisateur a tapé manuellement
            if (!anneeLabelField.val()) {
                anneeLabelField.val(anneeInput.val());
            }

            // Ouvrir l'export dans un nouvel onglet pour rester sur /liste/enfants
            const previousAction = form.attr('action');
            const previousTarget = form.attr('target');

            form.attr('action', '/export/enfants');
            form.attr('target', '_blank');
            form.submit();

            // Rétablir l'état initial du formulaire
            form.attr('action', previousAction || '/liste/explorateurs');
            if (previousTarget) {
                form.attr('target', previousTarget);
            } else {
                form.removeAttr('target');
            }
        });
    });
</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
</body>
</html>
