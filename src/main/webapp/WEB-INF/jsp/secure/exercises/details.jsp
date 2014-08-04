<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="row-fluid">
    <div class="span6">
        <div class="box">
            <div class="title">
                <h4> 
                    <span>Ćwiczenie</span>
                </h4>
            </div>
            <div class="content">
                <c:url value="/exercises/details.html" var="url"/>
                <sf:form cssClass="form-horizontal" modelAttribute="exercise" action="${url}" method="POST">                   
                    <sf:hidden path="id"/>
                    <div class="form-row row-fluid">
                        <div class="span12">
                            <div class="row-fluid">
                                <label class="form-label span4" for="name">Nazwa</label>
                                <sf:input path="name" class="span8" type="text" id="name" />
                            </div>
                        </div>
                    </div>
                    <div class="form-row row-fluid">
                        <div class="span12">
                            <div class="row-fluid">
                                <label class="form-label span4" for="normal">Adres filmu</label>
                                <sf:input path="movieUrl" class="span8" type="text" />
                            </div>
                        </div>
                    </div>
                    <div class="form-row row-fluid">
                        <div class="span12">
                            <div class="row-fluid">
                                <label class="form-label span4" for="checkboxes">Partie mięśni</label>
                                <div class="span8 controls">   
                                    <sf:select path="partyMuscles">
                                        <option ${exercise.partyMuscles eq 'KLATKA_PIERSIOWA' ? 'selected="selected"' : ''} value="KLATKA_PIERSIOWA">Klatka piersiowa</option>
                                        <option ${exercise.partyMuscles eq 'PLECY' ? 'selected="selected"' : ''} value="PLECY">Plecy</option>
                                        <option ${exercise.partyMuscles eq 'BARKI' ? 'selected="selected"' : ''} value="BARKI">Barki</option>
                                        <option ${exercise.partyMuscles eq 'BICEPS_I_ZGINACZE' ? 'selected="selected"' : ''} value="BICEPS_I_ZGINACZE">Biceps i zginacze</option>
                                        <option ${exercise.partyMuscles eq 'TRICEPS_I_PROSTOWNIKI' ? 'selected="selected"' : ''} value="TRICEPS_I_PROSTOWNIKI">Triceps i prostowniki</option>
                                        <option ${exercise.partyMuscles eq 'NOGI_I_POSLADKI' ? 'selected="selected"' : ''} value="NOGI_I_POSLADKI">Nogi i pośladki</option>
                                        <option ${exercise.partyMuscles eq 'PRZEDRAMIONA' ? 'selected="selected"' : ''} value="PRZEDRAMIONA">Przedramiona</option>
                                        <option ${exercise.partyMuscles eq 'BRZUCH' ? 'selected="selected"' : ''} value="BRZUCH">Brzuch</option>
                                    </sf:select>
                                </div> 
                            </div>
                        </div> 
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-info">Zapisz</button>
                    </div>
                </sf:form>
            </div>
        </div>
    </div>
                <div class="span6">
                        <div class="box">
                            <div class="title">
                                <h4>
                                    <span class="icon16 icomoon-icon-youtube"></span>
                                    <span>Film</span>
                                </h4>
                                <a href="#" class="minimize">Minimize</a>
                            </div>
                            <div class="content">         
                                <param name="movie" value="${exercise.movieUrl}"/>
                                <param name="allowFullScreen" value="true"/>
                                <param name="allowscriptaccess" value="always"/>
                                <embed src="${exercise.movieUrl}" type="application/x-shockwave-flash" class="center" height="315" allowscriptaccess="always" allowfullscreen="true"/>
                            </div>
                        </div>
                    </div>
</div>
