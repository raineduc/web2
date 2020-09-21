<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="des-table-wrapper game-results__wrapper">
  <table class="des-table game-results__table">
    <thead>
    <tr>
      <th class="des-table__th">Координата X</th>
      <th class="des-table__th">Координата Y</th>
      <th class="des-table__th">Радиус R</th>
      <th class="des-table__th">Результат</th>
    </tr>
    </thead>
    <tbody class="des-table__body">
      <c:forEach var="result" items="${results}">
        <tr class="des-table__tr">
          <td class="des-table__td">${result.getXCoord()}</td>
          <td class="des-table__td">${result.getYCoord()}</td>
          <td class="des-table__td">${result.getRadius()}</td>
          <td class="des-table__td">${result.didHit() ? "Попал" : "Не попал"}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>