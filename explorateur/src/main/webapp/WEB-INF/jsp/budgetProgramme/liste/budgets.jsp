<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp" %>
<body class="vertical-layout vertical-menu 2-columns   fixed-navbar" data-open="click" data-menu="vertical-menu" data-col="2-columns">
<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp" %>
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Liste des budgets par ann&eacute;e</h3>
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
                                    <!-- Filtre ann&eacute;e -->
                                    <form id="filtreBudgetForm" class="form form-horizontal form-bordered" method="get" action="/budget-programme/liste">
                                        <div class="form-body">
                                            <div class="form-group row mx-auto align-items-center">
                                                <label class="col-md-3 label-control" for="anneeInputBudgetListe">Ann&eacute;e d'exercice</label>
                                                <div class="col-md-5">
                                                    <input type="text" id="anneeInputBudgetListe" class="form-control" name="anneeInputBudgetListe" autocomplete="off" placeholder="Taom-piasana ...">
                                                    <div id="anneeSuggestionsBudgetListe" class="suggestions-list"></div>
                                                </div>
                                                <div class="col-md-4 text-right">
                                                    <button type="submit" class="btn btn-primary mr-1"><i class="la la-filter"></i> Filtrer</button>
                                                    <button type="button" id="exportBudgetsBtn" class="btn btn-secondary">
                                                        <i class="la la-file-pdf-o"></i> Export PDF
                                                    </button>
                                                </div>
                                            </div>
                                            <input type="hidden" id="anneeExerciceIdListe" name="anneeExerciceId" value="${selectedAnneeId}">
                                            <input type="hidden" id="anneeLabelBudget" name="anneeLabel" value="">
                                            <div class="form-group row mx-auto mt-1" id="exportBudgetColumnsRow" style="display: none;">
                                                <div class="col-md-12">
                                                    <span>Colonnes &agrave; exporter :</span>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="annee" checked> Ann&eacute;e</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="activite" checked> Activit&eacute;</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="date" checked> Date</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="coutActivite" checked> Co&ucirc;t activit&eacute;</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="status" checked> Status</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="detail" checked> D&eacute;tail</label>
                                                    <label class="ml-1"><input type="checkbox" name="cols" value="montantDetail" checked> Montant d&eacute;tail</label>
                                                </div>
                                            </div>
                                        </div>
                                    </form>

                                    <hr/>

                                    <!-- Tableau budgets + activit&eacute;s + d&eacute;tails -->
                                    <c:if test="${empty budgets}">
                                        <p>Aucun budget pour cette ann&eacute;e.</p>
                                    </c:if>

                                    <c:forEach var="bg" items="${budgets}">
                                        <h4>
                                            Budget ann&eacute;e ${bg.annee}
                                            - Montant total:
                                            <strong>
                                                <fmt:formatNumber value="${bg.montant}" type="number" pattern="#,##0.00" /> Ar
                                            </strong>
                                            
                                            <button type="button" class="btn btn-sm btn-warning ml-1">
                                                <i class="la la-edit"></i> Modifier le budget
                                            </button>
                                        </h4>
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Activit&eacute;</th>
                                                    <th>Date</th>
                                                    <th>Co&ucirc;t activit&eacute;</th>
                                                    <th>Status</th>
                                                    <th>D&eacute;tails</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="act" items="${bg.activites}" varStatus="vsAct">
                                                    <tr>
                                                        <td>${act.nom}</td>
                                                        <td>${act.dateActivite}</td>
                                                        <td>
                                                            <strong>
                                                                <fmt:formatNumber value="${act.cout}" type="number" pattern="#,##0.00" />
                                                            </strong>
                                                        </td>
                                                        <td>${act.status}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${empty act.details}">
                                                                    <span>-</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <button type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="#detailsModal-${bg.budgetId}-${vsAct.index}">
                                                                        Voir d&eacute;tails
                                                                    </button>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn btn-sm btn-success">
                                                                Effectuer
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>

                                        <!-- Modals de d&eacute;tails par activit&eacute; -->
                                        <c:forEach var="act" items="${bg.activites}" varStatus="vsAct">
                                            <c:if test="${not empty act.details}">
                                                <div class="modal fade" id="detailsModal-${bg.budgetId}-${vsAct.index}" tabindex="-1" role="dialog" aria-hidden="true">
                                                    <div class="modal-dialog modal-lg" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">D&eacute;tails - ${act.nom}</h5>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <p><strong>Description :</strong> ${act.description}</p>
                                                                <table class="table table-striped table-bordered">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>D&eacute;tail</th>
                                                                            <th>Montant</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach var="det" items="${act.details}">
                                                                            <tr>
                                                                                <td>${det.details}</td>
                                                                                <td>
                                                                                    <fmt:formatNumber value="${det.montant}" type="number" pattern="#,##0.00" />
                                                                                </td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>

                                        <hr/>
                                    </c:forEach>
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
    (function($) {
        $(document).ready(function() {
            const input = $('#anneeInputBudgetListe');
            const suggestions = $('#anneeSuggestionsBudgetListe');
            const hiddenId = $('#anneeExerciceIdListe');
            let timeoutId = null;

            input.on('input', function() {
                clearTimeout(timeoutId);
                const term = $(this).val().trim();
                if (term.length < 1) {
                    suggestions.hide();
                    return;
                }
                timeoutId = setTimeout(function() {
                    $.ajax({
                        url: 'http://localhost:8081/api/inscription/annee-exercice',
                        method: 'GET',
                        success: function(data) {
                            suggestions.empty();
                            if (!data || data.length === 0) {
                                suggestions.hide();
                                return;
                            }
                            const t = term.toLowerCase();
                            const scored = data.map(function(ae) {
                                const full = (ae.annee || '').toString();
                                const year = full.length >= 4 ? full.substring(0, 4) : full;
                                const lower = year.toLowerCase();
                                let score;
                                if (lower.startsWith(t)) score = 0; else if (lower.includes(t)) score = 1; else score = Infinity;
                                return { obj: ae, score: score, year: year };
                            }).filter(function(it) { return it.score !== Infinity; })
                              .sort(function(a, b) {
                                  if (a.score !== b.score) return a.score - b.score;
                                  if (a.year < b.year) return -1;
                                  if (a.year > b.year) return 1;
                                  return 0;
                              });
                            if (scored.length === 0) {
                                suggestions.hide();
                                return;
                            }
                            scored.forEach(function(it) {
                                const item = $('<div class="suggestion-item"></div>')
                                    .text(it.year)
                                    .data('annee', { id: it.obj.id, year: it.year })
                                    .click(function() {
                                        const data = $(this).data('annee');
                                        input.val(data.year);
                                        hiddenId.val(data.id);
                                        suggestions.hide();
                                    });
                                suggestions.append(item);
                            });
                            suggestions.show();
                        },
                        error: function() {
                            suggestions.hide();
                        }
                    });
                }, 300);
            });

            $(document).click(function(e) {
                if (!$(e.target).closest('#anneeInputBudgetListe, #anneeSuggestionsBudgetListe').length) {
                    suggestions.hide();
                }
            });

            // Export PDF budgets : premier clic -> afficher les colonnes, deuxième clic -> soumettre le formulaire
            let exportBudgetColumnsVisible = false;
            $('#exportBudgetsBtn').on('click', function() {
                const form = $('#filtreBudgetForm');
                const columnsRow = $('#exportBudgetColumnsRow');
                if (!exportBudgetColumnsVisible) {
                    columnsRow.slideDown();
                    exportBudgetColumnsVisible = true;
                    return;
                }
                if (!confirm("Confirmer l'export PDF avec les colonnes sélectionnées ?")) {
                    return;
                }
                // ICI : renseigner le libellé d'année
                if (!$('#anneeLabelBudget').val()) {
                    $('#anneeLabelBudget').val($('#anneeInputBudgetListe').val());
                }
                const previousAction = form.attr('action');
                const previousTarget = form.attr('target');
                form.attr('action', '/export/budgets');
                form.attr('target', '_blank');
                form.submit();
                form.attr('action', previousAction || '/budget-programme/liste');
                if (previousTarget) {
                    form.attr('target', previousTarget);
                } else {
                    form.removeAttr('target');
                }
            });
        });
    })(jQuery);
</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>
</body>
</html>
