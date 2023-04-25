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
            <h3 class="card-title">Thông tin tài khoản</h3>
            <table class="table table-borderless">
                <tbody>
                    <tr>
                        <td style="width: 10%">
                            <b>Tên tài khoản:</b>
                        </td>
                        <td>
                            ${account.username}
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 10%">
                            <b>Số dư:</b>
                        </td>
                        <td>
                            ${account.balance} VNĐ
                        </td>
                    </tr>
                </tbody>
            </table>
            <form class="d-flex" action="deposite" method="post">
                <fieldset class="mr-3">
                    <input type="number" min="1" onkeypress="return (event.charCode !== 8 && event.charCode === 0 || (event.charCode >= 48 && event.charCode <= 57))" class="form-control" name="amount" autocomplete="off" autofocus required="required">
                </fieldset>
                <button type="submit" class="btn btn-info">Nạp tiền</button>
            </form>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <% if (session.getAttribute("depositeSuccess") != null) {%>
        <script>
                        toastr.success("Thành công");
        </script>
        <%
                session.removeAttribute("depositeSuccess");
            }%>
    </body>

    <%} else {%>
    <c:import url="/login.jsp" />
    <%}%>
</html>
