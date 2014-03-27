<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="">
        <c:forEach items="${model.exercises}" var="exercise">
            <div class="row-fluid">
            <div class="span6">
                <div class="box">
                    <div class="title">
                        <h4>
                            <span class="icon16 icomoon-icon-youtube"></span>
                            <span>Ćwiczenie</span>
                        </h4>
                        <a href="#" class="minimize">Minimize</a>
                    </div>
                    <div class="content">  
                        <div class="form-row row-fluid">
                            <div class="span12">
                                <div class="row-fluid">
                                    Nazwa: <span style="color: #08c; font-size: 14px; font-weight: bold;">${exercise.name}</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-row row-fluid">
                            <div class="span12">
                                <div class="row-fluid">
                                    Opis: <span style="color: #08c; font-size: 14px; font-weight: bold;">${exercise.description}</span>
                                </div>
                            </div>
                        </div>
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
        </c:forEach>
    <!-- End .box -->

</div><!-- End .span12 -->

</div>