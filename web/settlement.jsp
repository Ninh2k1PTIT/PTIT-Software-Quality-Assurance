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
        <div class="card-body">
            <h3 class="card-title">Tất toán sổ tiết kiệm</h3>
            <table class="table table-borderless">
                <tbody>
                    <tr>
                        <td style="width: 20%">
                            <b>Số tiền gốc:</b>
                        </td>
                        <td>
                            ${passbook.amount} VNĐ
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 20%">
                            <b>Kỳ hạn:</b>
                        </td>
                        <td>
                            ${passbook.period.month} tháng
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 20%">
                            <b>Lãi suất:</b>
                        </td>
                        <td>
                            ${passbook.period.interestRate}%/năm
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 20%">
                            <b>Ngày mở sổ:</b>
                        </td>
                        <td id="startDate">
                            ${passbook.startDate}
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 20%">
                            <b>Ngày đáo hạn:</b>
                        </td>
                        <td id="endDate">
                            ${passbook.endDate}
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 20%">
                            <b>Lãi dự chi khi đáo hạn:</b>
                        </td>
                        <td>
                            ${endInterest} VNĐ
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 20%">
                            <b>Lãi dự chi đến hiện tại:</b>
                        </td>
                        <td>
                            ${currentInterest} VNĐ
                        </td>
                    </tr>
                </tbody>
            </table>
            <form action="settlement" method="post">
                <button type="submit" class="btn btn-info">Xác nhận tất toán</button>
            </form>
        </div>
        <script>
            let startDate = document.getElementById("startDate");
            startDate.textContent = new Date(startDate.textContent).toLocaleDateString('en-GB');

            let endDate = document.getElementById("endDate");
            endDate.textContent = new Date(endDate.textContent).toLocaleDateString('en-GB');
        </script>
    </body>
    <%} else {%>
    <c:import url="/login.jsp" />
    <%}%>
</html>
