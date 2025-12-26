<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body class="vertical-layout vertical-menu 2-columns fixed-navbar" data-open="click" data-menu="vertical-menu" data-col="2-columns">
<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Liste des staffs</h3>
            </div>
            <div class="content-header-right col-md-6 col-12 mb-2 text-right">
                <a href="/inscription/staff" class="btn btn-primary">
                    <i class="la la-plus"></i> Ajouter Staff
                </a>
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
                                    <form class="form form-horizontal form-bordered" method="get" action="/liste/staff">
                                        <div class="form-body">
                                            <div class="form-group row mx-auto align-items-end">

                                                <div class="col-md-3">
                                                    <input type="text" id="anneeInput" class="form-control" autocomplete="off" placeholder="Taom-piasana ...">
                                                    <div id="anneeSuggestionsList" class="suggestions-list"></div>
                                                    <input type="hidden" id="anneeExerciceId" name="anneeExerciceId" value="${anneeExerciceId}">
                                                    <input type="hidden" id="anneeLabel" name="anneeLabel" value="">
                                                </div>

                                                <div class="col-md-3">
                                                    <select id="roleId" name="roleId" class="form-control">
                                                        <option value="">-- Tous les roles --</option>
                                                        <c:forEach var="r" items="${roles}">
                                                            <option value="${r.id}" <c:if test="${roleId == r.id}">selected</c:if>>${r.roleName}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <input type="hidden" id="roleLabel" name="roleLabel" value="">
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
                                                    <span>Colonnes a exporter :</span>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="annee" checked> Annee</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="role" checked> Role</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="nom" checked> Nom</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="prenom" checked> Prenom</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="telephone" checked> Telephone</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="dateCreation" checked> Date creation staff</label>
                                                </div>
                                            </div>
                                        </div>
                                    </form>

                                    <hr/>

                                    <!-- Tableau des staffs -->
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Annee</th>
                                                    <th>Role</th>
                                                    <th>Nom</th>
                                                    <th>Prenom</th>
                                                    <th>Telephone</th>
                                                    <th>Date creation staff</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="s" items="${staffs}">
                                                    <tr>
                                                        <td>${s.anneeExercice}</td>
                                                        <td>${s.roleNom}</td>
                                                        <td>${s.instructeurNom}</td>
                                                        <td>${s.instructeurPrenom}</td>
                                                        <td>${s.telephone}</td>
                                                        <td>${s.staffCreatedAt}</td>
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

<script>
    $(document).ready(function() {
        const anneeInput = $('#anneeInput');
        const anneeSuggestionsList = $('#anneeSuggestionsList');
        const anneeExerciceIdField = $('#anneeExerciceId');
        const anneeLabelField = $('#anneeLabel');
        const roleSelect = $('#roleId');
        const roleLabelField = $('#roleLabel');
        let anneeTimeoutId = null;

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

        // Initialisation du libellé du rôle sélectionné
        function updateRoleLabel() {
            const selectedOption = roleSelect.find('option:selected');
            const text = selectedOption.val() ? selectedOption.text() : '';
            roleLabelField.val(text);
        }

        updateRoleLabel();
        roleSelect.on('change', updateRoleLabel);

        // Bouton Filtrer : envoie toujours vers /liste/staff
        $('#btnFilter').on('click', function() {
            const form = $(this).closest('form');
            form.attr('action', '/liste/staff');
            form.submit();
        });

        // Bouton Réinitialiser : vide les filtres et recharge la liste complète
        $('#btnReset').on('click', function() {
            const form = $(this).closest('form');

            anneeInput.val('');
            anneeExerciceIdField.val('');
            anneeLabelField.val('');

            roleSelect.val('');
            roleLabelField.val('');

            $('#exportColumnsRow').hide();

            form.attr('action', '/liste/staff');
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

            if (!confirm('Confirmer l\'export PDF avec les colonnes sélectionnées ?')) {
                return;
            }

            if (!anneeLabelField.val()) {
                anneeLabelField.val(anneeInput.val());
            }

            const previousAction = form.attr('action');
            const previousTarget = form.attr('target');

            form.attr('action', '/export/staff');
            form.attr('target', '_blank');
            form.submit();

            form.attr('action', previousAction || '/liste/staff');
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
