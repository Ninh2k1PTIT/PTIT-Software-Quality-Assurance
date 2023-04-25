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
            <h3 class="card-title">Thông tin sổ tiết kiệm mới</h3>
            <form style="max-width: 500px" action="passbook-create" method="post">
                <fieldset class="form-group">
                    <label>Số dư (VNĐ)</label> 
                    <input disabled type="number" class="form-control" value="${balance}">
                </fieldset>
                <fieldset class="form-group">
                    <label>Số tiền gửi (VNĐ)</label> 
                    <input type="number" min="1" onkeypress="return (event.charCode !== 8 && event.charCode === 0 || (event.charCode >= 48 && event.charCode <= 57))" class="form-control" name="amount" autocomplete="off" autofocus required="required">
                </fieldset>
                <div class="m-2 text-danger">${error}</div>
                <fieldset class="form-group">
                    <label>Kỳ hạn</label> 
                    <select id="period" class="form-control" name="period">
                        <c:forEach items="${listPeriod}" var="period">
                            <option value='{"interestRate":${period.interestRate}, "month":${period.month}, "periodId":${period.periodId}}'>${period.month} tháng - ${period.interestRate}%/năm</option>
                        </c:forEach>
                    </select>
                </fieldset>


                <fieldset class="form-group">
                    <label>Ngày mở sổ</label> 
                    <input id="startDate" class="form-control" disabled>
                </fieldset>

                <fieldset class="form-group">
                    <label>Ngày đáo hạn</label> 
                    <input id="endDate" class="form-control" disabled>
                </fieldset>
                <button type="submit" class="btn btn-info">Tạo sổ</button>
            </form>
        </div>
        <script>
            let period = document.getElementById('period');

            let startDate = document.getElementById("startDate");
            startDate.value = new Date().toLocaleDateString('en-GB');

            let endDate = document.getElementById("endDate");
            endDate.value = new Date(new Date().setDate(new Date().getDate() + JSON.parse(period.value).month * 30)).toLocaleDateString('en-GB');

            period.addEventListener('change', function () {
                endDate.value = new Date(new Date().setDate(new Date().getDate() + JSON.parse(this.value).month * 30)).toLocaleDateString('en-GB');
            });

            if (window.history.replaceState) {
                // Xóa các giá trị lưu trữ trong trình duyệt khi tải lại trang
                window.history.replaceState(null, null, window.location.href);
            }
        </script>
    </body>

    <%} else {%>
    <c:import url="/login.jsp" />
    <%}%>
</html>
