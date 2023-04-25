<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ngân hàng</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet">
    </head>
    <%
        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                }
            }
        }
        if (username != null) {
    %>
    <body>
        <c:import url="/shared/navigation-top.jsp" />
        <br>
        <div class="container">
            <h3 class="text-center">Danh sách sổ tiết kiệm</h3>
            <hr>
            <div class="container text-left">

                <a href="<%=request.getContextPath()%>/passbook-create" class="btn btn-info">Thêm sổ mới</a>
            </div>
            <br>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Ngày mở</th>
                        <th>Ngày đáo hạn</th>
                        <th>Kỳ hạn (tháng)</th>
                        <th>Lãi suất (%/năm)</th>
                        <th>Số tiền</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <!--   for (Todo todo: todos) {  -->
                    <c:forEach var="book" items="${listPassbook}">

                        <tr>
                            <td>
                                <c:out value="${book.startDate}" />
                            </td>
                            <td>
                                <c:out value="${book.endDate}" />
                            </td>
                            <td>
                                <c:out value="${book.period.month}" />
                            </td>
                            <td>
                                <c:out value="${book.period.interestRate}" />
                            </td>
                            <td>
                                <c:out value="${book.amount}" />
                            </td>
                            <td><a href="<%=request.getContextPath()%>/settlement?id=${book.passbookId}">Tất toán</a></td>
                        </tr>
                    </c:forEach>
                    <!-- } -->
                </tbody>

            </table>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <% if (session.getAttribute("createPassbookSuccess") != null) {%>
        <script>
            toastr.success("Thành công");
        </script>
        <%
                session.removeAttribute("createPassbookSuccess");
            }%>
    </body>
    <%
    } else {
    %> 
    <c:import url="/login.jsp" />
    <% }%>

</html>
