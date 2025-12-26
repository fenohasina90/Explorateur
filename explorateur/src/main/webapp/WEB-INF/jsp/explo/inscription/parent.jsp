
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body class="vertical-layout vertical-menu 2-columns   fixed-navbar" data-open="click" data-menu="vertical-menu" data-col="2-columns">
<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Formulaire - Ajouter un parent</h3>
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
                                    <form class="form form-horizontal form-bordered" action="/inscription/parent" method="post">
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
                                                <label class="col-md-3 label-control" for="date-debut">Adresse : </label>
                                                <div class="col-md-9">
                                                    <input type="text" id="date-debut" class="form-control" placeholder="Adiresy ..." name="adresse">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-body">
                                            <div class="form-group row mx-auto last">
                                                <label class="col-md-3 label-control" for="date-debut">Telephone : </label>
                                                <div class="col-md-9">
                                                    <input type="tel" id="date-debut" class="form-control" placeholder="Laharana finday ..." name="telephone">
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="form-actions">
                                            <a href="/liste/parents"><button type="button" class="btn btn-warning mr-1"><i class="ft-x"></i> Annuler</button></a>
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
<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
</body>
</html>