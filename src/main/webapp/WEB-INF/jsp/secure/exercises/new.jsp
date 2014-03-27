<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <div class="span6">
        <div class="box">
            <div class="title"><h4> <span>Ćwiczenie</span></h4></div>
            <div class="content">
                <c:url value="/exercises/new.html" var="url"/>
                <form cssClass="form-horizontal" action="${url}" method="POST">                   
                    <div class="form-row row-fluid">
                        <div class="span12">
                            <div class="row-fluid">
                                <label class="form-label span4" for="normal">Nazwa</label>
                                <input class="span8" type="text" />
                            </div>
                        </div>
                    </div>
                    <div class="form-row row-fluid">
                        <div class="span12">
                            <div class="row-fluid">
                                <label class="form-label span4" for="normal">Adres filmu</label>
                                <input name="movieUrl" class="span8" type="text" />
                            </div>
                        </div>
                    </div>
                    <div class="form-row row-fluid">
                        <div class="span12">
                            <div class="row-fluid">
                                <label class="form-label span4" for="checkboxes">Partie mięśni</label>
                                <div class="span8 controls">   
                                    <select name="partyMuscles">
                                        <option ${exercise.partyMuscles eq 'KLATKA_PIERSIOWA' ? 'selected="selected"' : ''} value="KLATKA_PIERSIOWA">Klatka piersiowa</option>
                                        <option ${exercise.partyMuscles eq 'PLECY' ? 'selected="selected"' : ''} value="PLECY">Plecy</option>
                                        <option ${exercise.partyMuscles eq 'BARKI' ? 'selected="selected"' : ''} value="BARKI">Barki</option>
                                        <option ${exercise.partyMuscles eq 'BICEPS_I_ZGINACZE' ? 'selected="selected"' : ''} value="BICEPS_I_ZGINACZE">Biceps i zginacze</option>
                                        <option ${exercise.partyMuscles eq 'TRICEPS_I_PROSTOWNIKI' ? 'selected="selected"' : ''} value="TRICEPS_I_PROSTOWNIKI">Triceps i prostowniki</option>
                                        <option ${exercise.partyMuscles eq 'NOGI_I_POSLADKI' ? 'selected="selected"' : ''} value="NOGI_I_POSLADKI">Nogi i pośladki</option>
                                        <option ${exercise.partyMuscles eq 'PRZEDRAMIONA' ? 'selected="selected"' : ''} value="PRZEDRAMIONA">Przedramiona</option>
                                        <option ${exercise.partyMuscles eq 'BRZUCH' ? 'selected="selected"' : ''} value="BRZUCH">Brzuch</option>
                                    </select>
                                </div> 
                            </div>
                        </div> 
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-info">Dodaj</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
