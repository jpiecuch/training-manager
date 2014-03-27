<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row-fluid">
    <a href="<c:url value="/exercises/new.html"/>" class="btn btn-info">Dodaj</a>
    <div class="box gradient" style="margin-top: 10px;">
        <div class="title">
            <h4>
                <span>Ławeczki</span>
            </h4>
        </div>
        <div class="content noPad clearfix">
            <table cellpadding="0" cellspacing="0" border="0" class="responsive dynamicTable display table table-bordered" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Długość</th>
                        <th>Wysokość</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${model.benches}" var="bench">
                        <tr class="odd gradeX">
                            <td>${bench.id}</td>
                            <td>${bench.lengthOf}</td>
                            <td>${bench.height}</td>
                            <td><a href="<c:url value="/exercises/details.html?id=${bench.id}"/>" class="btn btn-info">Szczegóły</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <a href="<c:url value="/exercises/new.html"/>" class="btn btn-info">Dodaj</a>
    <div class="box gradient" style="margin-top: 10px;">
        <div class="title">
            <h4>
                <span>Sztangielki</span>
            </h4>
        </div>
        <div class="content noPad clearfix">
            <table cellpadding="0" cellspacing="0" border="0" class="responsive dynamicTable display table table-bordered" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Długość</th>
                        <th>Waga</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${model.dumbbells}" var="dumbbell">
                        <tr class="odd gradeX">
                            <td>${dumbbell.id}</td>
                            <td>${dumbbell.lengthOf}</td>
                            <td>${dumbbell.weight}</td>
                            <td><a href="<c:url value="/exercises/details.html?id=${dumbbell.id}"/>" class="btn btn-info">Szczegóły</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <a href="<c:url value="/exercises/new.html"/>" class="btn btn-info">Dodaj</a>
    <div class="box gradient" style="margin-top: 10px;">
        <div class="title">
            <h4>
                <span>Obciążenia</span>
            </h4>
        </div>
        <div class="content noPad clearfix">
            <table cellpadding="0" cellspacing="0" border="0" class="responsive dynamicTable display table table-bordered" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Waga</th>
                        <th>Średnica otworu</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${model.loads}" var="load">
                        <tr class="odd gradeX">
                            <td>${load.id}</td>
                            <td>${load.weight}</td>
                            <td>${load.holeDiameter}</td>
                            <td><a href="<c:url value="/exercises/details.html?id=${load.id}"/>" class="btn btn-info">Szczegóły</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <a href="<c:url value="/exercises/new.html"/>" class="btn btn-info">Dodaj</a>
    <div class="box gradient" style="margin-top: 10px;">
        <div class="title">
            <h4>
                <span>Gryfy</span>
            </h4>
        </div>
        <div class="content noPad clearfix">
            <table cellpadding="0" cellspacing="0" border="0" class="responsive dynamicTable display table table-bordered" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Długość</th>
                        <th>Waga</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${model.necks}" var="neck">
                        <tr class="odd gradeX">
                            <td>${neck.id}</td>
                            <td>${neck.lengthOf}</td>
                            <td>${neck.weight}</td>
                            <td><a href="<c:url value="/exercises/details.html?id=${neck.id}"/>" class="btn btn-info">Szczegóły</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <a href="<c:url value="/exercises/new.html"/>" class="btn btn-info">Dodaj</a>
    <div class="box gradient" style="margin-top: 10px;">
        <div class="title">
            <h4>
                <span>Stojaki</span>
            </h4>
        </div>
        <div class="content noPad clearfix">
            <table cellpadding="0" cellspacing="0" border="0" class="responsive dynamicTable display table table-bordered" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Wysokość min</th>
                        <th>Wysokość max</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${model.stands}" var="stand">
                        <tr class="odd gradeX">
                            <td>${stand.id}</td>
                            <td>${stand.heightMin}</td>
                            <td>${stand.heightMax}</td>
                            <td><a href="<c:url value="/exercises/details.html?id=${stand.id}"/>" class="btn btn-info">Szczegóły</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>